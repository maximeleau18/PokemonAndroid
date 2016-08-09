package com.maximeleau.harmony.android.pokemon.view.combatmanager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.data.CombatManagerWebServiceClientAdapter;
import com.maximeleau.harmony.android.pokemon.entity.Attaque;
import com.maximeleau.harmony.android.pokemon.entity.CombatManager;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by Maxime Léau on 04/08/2016.
 */
public class CombatManagerAttackFragment extends Fragment {
    private Attaque attack;
    private LinearLayout attackContainer;
    private TextView attackNameText;
    private TextView attackTypeNameText;
    private TextView attackPowerText;
    private TextView attackDammageText;
    private CombatManager combatManager;
    private Dresseur dresseurConnected;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.attack = (Attaque) getArguments().getSerializable("attaque");
        View view = inflater.inflate(R.layout.fragment_combat_manager_attack, container, false);
        initializeComponent(view);
        return view;
    }

    private void initializeComponent(View view) {
        CombatManagerShowActivity parentActivity = (CombatManagerShowActivity) CombatManagerAttackFragment.this.getActivity();
        this.combatManager = parentActivity.getCombatManager();
        this.dresseurConnected = parentActivity.getDresseurConnected();

        this.attackContainer = (LinearLayout) view.findViewById(R.id.combat_manager_attack_ll);
        this.attackContainer.setClickable(true);
        this.attackContainer.setOnClickListener(onClickAttack());
        if(this.combatManager.getCombat().getDresseur1().getId() == dresseurConnected.getId()){
            this.attackContainer.setEnabled((this.combatManager.getDresseurActualTurnId() == this.combatManager.getCombat().getDresseur1().getId()));
        }else if(this.combatManager.getCombat().getDresseur2().getId() == dresseurConnected.getId()){
            this.attackContainer.setEnabled((this.combatManager.getDresseurActualTurnId() == this.combatManager.getCombat().getDresseur2().getId()));
        }

        this.attackNameText = (TextView) view.findViewById(R.id.combat_manager_attack_name);
        this.attackTypeNameText = (TextView) view.findViewById(R.id.combat_manager_attack_type_value);
        this.attackPowerText = (TextView) view.findViewById(R.id.combat_manager_attack_power_value);
        this.attackDammageText = (TextView) view.findViewById(R.id.combat_manager_attack_dammage_value);

        this.attackNameText.setText(this.attack.getNom());
        this.attackTypeNameText.setText(this.attack.getTypeAttaque().getNom());
        this.attackPowerText.setText(String.format(Locale.FRANCE, "%d", this.attack.getPuissance()));
        this.attackDammageText.setText(String.format(Locale.FRANCE, "%d", this.attack.getDegats()));
    }

    public View.OnClickListener onClickAttack(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch attack against the opponent
                // Display message in console
                Context ctx = v.getContext();
                CombatManagerShowActivity parentActivity = (CombatManagerShowActivity) CombatManagerAttackFragment.this.getActivity();
                TextView console = parentActivity.getConsole();
                CombatManager combatManager = parentActivity.getCombatManager();
                Dresseur dresseurConnected = parentActivity.getDresseurConnected();

                // Update console messages
                console.setText(combatManager.getConsole());

                if (dresseurConnected.getId() == combatManager.getCombat().getDresseur1().getId()){
                    // Update dresseur which is attacked
                    combatManager.setDresseur(combatManager.getCombat().getDresseur2());
                    combatManager.setAttaque(CombatManagerAttackFragment.this.attack);
                    CombatManagerOpponentFragment fragmentOpponent = (CombatManagerOpponentFragment) parentActivity.getSupportFragmentManager().findFragmentById(
                                                    R.id.fragment_combat_manager_opponent);
                    combatManager.setActualPv(fragmentOpponent.getActualPv());
                }else{
                    // Update dresseur which is attacked
                    combatManager.setDresseur(combatManager.getCombat().getDresseur1());
                    combatManager.setAttaque(CombatManagerAttackFragment.this.attack);CombatManagerOpponentFragment fragmentOpponent = (CombatManagerOpponentFragment) parentActivity.getSupportFragmentManager().findFragmentById(
                            R.id.fragment_combat_manager_opponent);
                    combatManager.setActualPv(fragmentOpponent.getActualPv());
                }
                // Call launch attack function api
                new LaunchAttackTask(CombatManagerAttackFragment.this, CombatManagerAttackFragment.this.combatManager).execute();

                // Disabled all attack button while datapush is not received
                CombatManagerAttackFragment fragmentAttack1 = parentActivity.getFragmentAttack1();
                fragmentAttack1.getAttackContainer().setEnabled(false);
                CombatManagerAttackFragment fragmentAttack2 = parentActivity.getFragmentAttack2();
                fragmentAttack2.getAttackContainer().setEnabled(false);
                CombatManagerAttackFragment fragmentAttack3 = parentActivity.getFragmentAttack3();
                fragmentAttack3.getAttackContainer().setEnabled(false);
                CombatManagerAttackFragment fragmentAttack4 = parentActivity.getFragmentAttack4();
                fragmentAttack4.getAttackContainer().setEnabled(false);
            }
        };
    }

    public static class LaunchAttackTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to update. */
        private final CombatManager combatManager;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param combatManager The entity to insert in the DB
         * @param fragment The parent fragment from where the aSyncTask is
         * called
         */
        public LaunchAttackTask(final CombatManagerAttackFragment fragment,
                                    final CombatManager combatManager) {
            super();
            this.ctx = fragment.getActivity();
            this.combatManager = combatManager;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.combat_manager_launch_attack_title),
                    this.ctx.getString(
                            R.string.combat_manager_launch_attack_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = -1;

            try {
                CombatManagerWebServiceClientAdapter webService = new CombatManagerWebServiceClientAdapter(this.ctx);
                result = webService.launchAttack(this.combatManager);
            } catch (Exception e) {
                android.util.Log.e("CombatManagerAttackFragment", e.getMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if (result < 0) {

                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(this.ctx);
                builder.setIcon(0);
                builder.setMessage(
                        this.ctx.getString(
                                R.string.combat_manager_launch_attack_error_message));
                builder.setPositiveButton(
                        this.ctx.getString(android.R.string.yes),
                        new Dialog.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                            }
                        });
                builder.show();
            } else {
                // Waiting for databpush received
                // To start activity with intent
            }

            this.progress.dismiss();
        }
    }

    public LinearLayout getAttackContainer(){
        return this.attackContainer;
    }
}
