package com.maximeleau.harmony.android.pokemon.view.combatmanager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.data.CombatWebServiceClientAdapter;
import com.maximeleau.harmony.android.pokemon.data.base.CombatManagerWebServiceClientAdapterBase;
import com.maximeleau.harmony.android.pokemon.entity.Combat;
import com.maximeleau.harmony.android.pokemon.entity.CombatManager;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.view.chooseaction.ChooseActionActivity;
import com.microsoft.azure.engagement.activity.EngagementFragmentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by Maxime Léau on 03/08/2016.
 */
public class CombatManagerShowActivity extends EngagementFragmentActivity {

    private CombatManager combatManager;
    private Dresseur dresseurConnected;
    private TextView console;
    private Context context;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.combatManager = (CombatManager) intent.getSerializableExtra("combatManager");
        this.dresseurConnected = (Dresseur) intent.getSerializableExtra("dresseur");
        // Set context
        this.context = this;
        setContentView(R.layout.activity_combat_manager_show);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (this.combatManager.getCombat().getDresseur1().getId() == this.dresseurConnected.getId()){
            // Add attacks fragments binding with pokemon1
            CombatManagerAttackFragment fragmentAttack1 = new CombatManagerAttackFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("attaque", this.combatManager.getCombat().getPokemon1().getAttaque1());
            fragmentAttack1.setArguments(bundle1);
            fragmentTransaction.add(R.id.fragment_combat_manager_attack1, fragmentAttack1);

            CombatManagerAttackFragment fragmentAttack2 = new CombatManagerAttackFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("attaque", this.combatManager.getCombat().getPokemon1().getAttaque2());
            fragmentAttack2.setArguments(bundle2);
            fragmentTransaction.add(R.id.fragment_combat_manager_attack2, fragmentAttack2);

            CombatManagerAttackFragment fragmentAttack3 = new CombatManagerAttackFragment();
            Bundle bundle3 = new Bundle();
            bundle3.putSerializable("attaque", this.combatManager.getCombat().getPokemon1().getAttaque3());
            fragmentAttack3.setArguments(bundle3);
            fragmentTransaction.add(R.id.fragment_combat_manager_attack3, fragmentAttack3);

            CombatManagerAttackFragment fragmentAttack4 = new CombatManagerAttackFragment();
            Bundle bundle4 = new Bundle();
            bundle4.putSerializable("attaque", this.combatManager.getCombat().getPokemon1().getAttaque4());
            fragmentAttack4.setArguments(bundle4);
            fragmentTransaction.add(R.id.fragment_combat_manager_attack4, fragmentAttack4);
        }else{
            // Add attacks fragments binding with pokemon2
            CombatManagerAttackFragment fragmentAttack1 = new CombatManagerAttackFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("attaque", this.combatManager.getCombat().getPokemon2().getAttaque1());
            fragmentAttack1.setArguments(bundle1);
            fragmentTransaction.add(R.id.fragment_combat_manager_attack1, fragmentAttack1);

            CombatManagerAttackFragment fragmentAttack2 = new CombatManagerAttackFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("attaque", this.combatManager.getCombat().getPokemon2().getAttaque2());
            fragmentAttack2.setArguments(bundle2);
            fragmentTransaction.add(R.id.fragment_combat_manager_attack2, fragmentAttack2);

            CombatManagerAttackFragment fragmentAttack3 = new CombatManagerAttackFragment();
            Bundle bundle3 = new Bundle();
            bundle3.putSerializable("attaque", this.combatManager.getCombat().getPokemon2().getAttaque3());
            fragmentAttack3.setArguments(bundle3);
            fragmentTransaction.add(R.id.fragment_combat_manager_attack3, fragmentAttack3);

            CombatManagerAttackFragment fragmentAttack4 = new CombatManagerAttackFragment();
            Bundle bundle4 = new Bundle();
            bundle4.putSerializable("attaque", this.combatManager.getCombat().getPokemon2().getAttaque4());
            fragmentAttack4.setArguments(bundle4);
            fragmentTransaction.add(R.id.fragment_combat_manager_attack4, fragmentAttack4);
        }

        // Commit changes
        fragmentTransaction.commit();

        // Get console fragment
        CombatManagerConsoleFragment fragmentConsole = (CombatManagerConsoleFragment)fragmentManager.findFragmentById(R.id.fragment_combat_manager_console);
        this.console = fragmentConsole.getConsole();
    }

    public CombatManager getCombatManager(){
        return this.combatManager;
    }

    public Dresseur getDresseurConnected(){ return this.dresseurConnected; }

    public TextView getConsole(){ return this.console; }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("received_data_push");

        this.receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                CombatManager combatManager = new CombatManager();

                try {
                    JSONObject json = new JSONObject(intent.getStringExtra("combatManagerJson"));
                    CombatManagerWebServiceClientAdapterBase webservice = new CombatManagerWebServiceClientAdapterBase(context) {
                    };
                    webservice.extract(json, combatManager);
                } catch (JSONException e) {
                    Log.e("Error", "Not be able to parse" + intent.getStringExtra("combatManagerJson") + " to combatManager.");
                }

                if (combatManager.getCombat().isDresseur1Vainqueur() || combatManager.getCombat().isDresseur2Vainqueur()) {
                    new FinishFightTask(CombatManagerShowActivity.this, combatManager.getCombat(), dresseurConnected).execute();
                }else{
                    CombatManagerShowActivity.this.combatManager = combatManager;

                    // Update data in fragment
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    if (CombatManagerShowActivity.this.combatManager.getCombat().getDresseur1().getId() == CombatManagerShowActivity.this.dresseurConnected.getId()) {
                        // Add attacks fragments binding with pokemon1
                        CombatManagerAttackFragment fragmentAttack1 = new CombatManagerAttackFragment();
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("attaque", CombatManagerShowActivity.this.combatManager.getCombat().getPokemon1().getAttaque1());
                        fragmentAttack1.setArguments(bundle1);
                        fragmentTransaction.add(R.id.fragment_combat_manager_attack1, fragmentAttack1);

                        CombatManagerAttackFragment fragmentAttack2 = new CombatManagerAttackFragment();
                        Bundle bundle2 = new Bundle();
                        bundle2.putSerializable("attaque", CombatManagerShowActivity.this.combatManager.getCombat().getPokemon1().getAttaque2());
                        fragmentAttack2.setArguments(bundle2);
                        fragmentTransaction.add(R.id.fragment_combat_manager_attack2, fragmentAttack2);

                        CombatManagerAttackFragment fragmentAttack3 = new CombatManagerAttackFragment();
                        Bundle bundle3 = new Bundle();
                        bundle3.putSerializable("attaque", CombatManagerShowActivity.this.combatManager.getCombat().getPokemon1().getAttaque3());
                        fragmentAttack3.setArguments(bundle3);
                        fragmentTransaction.add(R.id.fragment_combat_manager_attack3, fragmentAttack3);

                        CombatManagerAttackFragment fragmentAttack4 = new CombatManagerAttackFragment();
                        Bundle bundle4 = new Bundle();
                        bundle4.putSerializable("attaque", CombatManagerShowActivity.this.combatManager.getCombat().getPokemon1().getAttaque4());
                        fragmentAttack4.setArguments(bundle4);
                        fragmentTransaction.add(R.id.fragment_combat_manager_attack4, fragmentAttack4);
                    } else {
                        // Add attacks fragments binding with pokemon2
                        CombatManagerAttackFragment fragmentAttack1 = new CombatManagerAttackFragment();
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("attaque", CombatManagerShowActivity.this.combatManager.getCombat().getPokemon2().getAttaque1());
                        fragmentAttack1.setArguments(bundle1);
                        fragmentTransaction.add(R.id.fragment_combat_manager_attack1, fragmentAttack1);

                        CombatManagerAttackFragment fragmentAttack2 = new CombatManagerAttackFragment();
                        Bundle bundle2 = new Bundle();
                        bundle2.putSerializable("attaque", CombatManagerShowActivity.this.combatManager.getCombat().getPokemon2().getAttaque2());
                        fragmentAttack2.setArguments(bundle2);
                        fragmentTransaction.add(R.id.fragment_combat_manager_attack2, fragmentAttack2);

                        CombatManagerAttackFragment fragmentAttack3 = new CombatManagerAttackFragment();
                        Bundle bundle3 = new Bundle();
                        bundle3.putSerializable("attaque", CombatManagerShowActivity.this.combatManager.getCombat().getPokemon2().getAttaque3());
                        fragmentAttack3.setArguments(bundle3);
                        fragmentTransaction.add(R.id.fragment_combat_manager_attack3, fragmentAttack3);

                        CombatManagerAttackFragment fragmentAttack4 = new CombatManagerAttackFragment();
                        Bundle bundle4 = new Bundle();
                        bundle4.putSerializable("attaque", CombatManagerShowActivity.this.combatManager.getCombat().getPokemon2().getAttaque4());
                        fragmentAttack4.setArguments(bundle4);
                        fragmentTransaction.add(R.id.fragment_combat_manager_attack4, fragmentAttack4);
                    }

                    // Commit changes
                    fragmentTransaction.commit();

                    // Set console fragment
                    CombatManagerConsoleFragment fragmentConsole = (CombatManagerConsoleFragment) fragmentManager.findFragmentById(R.id.fragment_combat_manager_console);
                    fragmentConsole.initializeComponent(fragmentConsole.getView());
                    // Set opponent view
                    CombatManagerOpponentFragment fragmentOpponent = (CombatManagerOpponentFragment) fragmentManager.findFragmentById(R.id.fragment_combat_manager_opponent);
                    fragmentOpponent.initializeComponent(fragmentOpponent.getView());
                    // Set player view
                    CombatManagerPlayerFragment fragmentPlayer = (CombatManagerPlayerFragment) fragmentManager.findFragmentById(R.id.fragment_combat_manager_player);
                    fragmentPlayer.initializeComponent(fragmentPlayer.getView());
                }
            }
        };

        //registering our receiver
        this.registerReceiver(this.receiver, intentFilter);
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        this.context.unregisterReceiver(this.receiver);
    }


    public static class FinishFightTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to update. */
        private final Combat combat;
        /** Dresseur winner **/
        private final Dresseur winner;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param combat The entity to insert in the DB
         * @param context The context from where the aSyncTask is
         * called
         */
        public FinishFightTask(final Context context,
                                final Combat combat, final Dresseur winner) {
            super();
            this.ctx = context;
            this.combat = combat;
            this.winner = winner;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progress = ProgressDialog.show(this.ctx,
                    this.ctx.getString(
                            R.string.combat_manager_finish_fight_title),
                    this.ctx.getString(
                            R.string.combat_manager_finish_fight_message));
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = 0;
            // Fight is still saved and campaign updated from windowsdevice
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
                                R.string.combat_manager_finish_fight_error_message));
                builder.setPositiveButton(
                        this.ctx.getString(android.R.string.yes),
                        new Dialog.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        });
                builder.show();
            } else {
                if(this.winner.getId() == this.combat.getDresseur1().getId()){
                    if (this.combat.isDresseur1Vainqueur()){
                        // You Win
                        final AlertDialog.Builder builder =
                                new AlertDialog.Builder(this.ctx);
                        builder.setIcon(0);
                        builder.setMessage(
                                this.ctx.getString(
                                        R.string.combat_manager_finish_fight_winner));
                        builder.setPositiveButton(
                                this.ctx.getString(android.R.string.yes),
                                new Dialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Intent myIntent = new Intent(((Dialog) dialog).getContext(), ChooseActionActivity.class);
                                        ((Dialog) dialog).getContext().startActivity(myIntent);
                                        return;
                                    }
                                });
                        builder.show();
                    }else{
                        // You loose
                        final AlertDialog.Builder builder =
                                new AlertDialog.Builder(this.ctx);
                        builder.setIcon(0);
                        builder.setMessage(
                                this.ctx.getString(
                                        R.string.combat_manager_finish_fight_looser));
                        builder.setPositiveButton(
                                this.ctx.getString(android.R.string.yes),
                                new Dialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Intent myIntent = new Intent(((Dialog) dialog).getContext(), ChooseActionActivity.class);
                                        ((Dialog) dialog).getContext().startActivity(myIntent);
                                        return;
                                    }
                                });
                        builder.show();
                    }
                }else if(this.combat.isDresseur2Vainqueur()){
                    // You Win
                    final AlertDialog.Builder builder =
                            new AlertDialog.Builder(this.ctx);
                    builder.setIcon(0);
                    builder.setMessage(
                            this.ctx.getString(
                                    R.string.combat_manager_finish_fight_winner));
                    builder.setPositiveButton(
                            this.ctx.getString(android.R.string.yes),
                            new Dialog.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent myIntent = new Intent(((Dialog) dialog).getContext(), ChooseActionActivity.class);
                                    ((Dialog) dialog).getContext().startActivity(myIntent);
                                    return;
                                }
                            });
                    builder.show();
                }else{
                    // You loose
                    final AlertDialog.Builder builder =
                            new AlertDialog.Builder(this.ctx);
                    builder.setIcon(0);
                    builder.setMessage(
                            this.ctx.getString(
                                    R.string.combat_manager_finish_fight_looser));
                    builder.setPositiveButton(
                            this.ctx.getString(android.R.string.yes),
                            new Dialog.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent myIntent = new Intent(((Dialog) dialog).getContext(), ChooseActionActivity.class);
                                    ((Dialog) dialog).getContext().startActivity(myIntent);
                                    return;
                                }
                            });
                    builder.show();
                }
            }

            this.progress.dismiss();

        }
    }
}
