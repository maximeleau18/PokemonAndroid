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
import android.widget.ScrollView;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.data.CombatWebServiceClientAdapter;
import com.maximeleau.harmony.android.pokemon.data.base.CombatManagerWebServiceClientAdapterBase;
import com.maximeleau.harmony.android.pokemon.entity.Combat;
import com.maximeleau.harmony.android.pokemon.entity.CombatManager;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.view.connectedchooseaction.ConnectedChooseActionActivity;
import com.microsoft.azure.engagement.activity.EngagementFragmentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by Maxime Léau on 03/08/2016.
 */
public class CombatManagerShowActivity extends EngagementFragmentActivity {

    private CombatManager combatManager;
    private Dresseur dresseurConnected;
    private TextView console;
    private ScrollView consoleSV;
    private Context context;
    private BroadcastReceiver receiver;
    private CombatManagerAttackFragment fragmentAttack1;
    private CombatManagerAttackFragment fragmentAttack2;
    private CombatManagerAttackFragment fragmentAttack3;
    private CombatManagerAttackFragment fragmentAttack4;

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
            this.fragmentAttack1 = new CombatManagerAttackFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("attaque", this.combatManager.getCombat().getPokemon1().getAttaque1());
            this.fragmentAttack1.setArguments(bundle1);
            fragmentTransaction.add(R.id.fragment_combat_manager_attack1, this.fragmentAttack1);

            this.fragmentAttack2 = new CombatManagerAttackFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("attaque", this.combatManager.getCombat().getPokemon1().getAttaque2());
            this.fragmentAttack2.setArguments(bundle2);
            fragmentTransaction.add(R.id.fragment_combat_manager_attack2, this.fragmentAttack2);

            this.fragmentAttack3 = new CombatManagerAttackFragment();
            Bundle bundle3 = new Bundle();
            bundle3.putSerializable("attaque", this.combatManager.getCombat().getPokemon1().getAttaque3());
            this.fragmentAttack3.setArguments(bundle3);
            fragmentTransaction.add(R.id.fragment_combat_manager_attack3, this.fragmentAttack3);

            this.fragmentAttack4 = new CombatManagerAttackFragment();
            Bundle bundle4 = new Bundle();
            bundle4.putSerializable("attaque", this.combatManager.getCombat().getPokemon1().getAttaque4());
            this.fragmentAttack4.setArguments(bundle4);
            fragmentTransaction.add(R.id.fragment_combat_manager_attack4, this.fragmentAttack4);
        }else{
            // Add attacks fragments binding with pokemon2
            this.fragmentAttack1 = new CombatManagerAttackFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("attaque", this.combatManager.getCombat().getPokemon2().getAttaque1());
            this.fragmentAttack1.setArguments(bundle1);
            fragmentTransaction.add(R.id.fragment_combat_manager_attack1, this.fragmentAttack1);

            this.fragmentAttack2 = new CombatManagerAttackFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable("attaque", this.combatManager.getCombat().getPokemon2().getAttaque2());
            this.fragmentAttack2.setArguments(bundle2);
            fragmentTransaction.add(R.id.fragment_combat_manager_attack2, this.fragmentAttack2);

            this.fragmentAttack3 = new CombatManagerAttackFragment();
            Bundle bundle3 = new Bundle();
            bundle3.putSerializable("attaque", this.combatManager.getCombat().getPokemon2().getAttaque3());
            this.fragmentAttack3.setArguments(bundle3);
            fragmentTransaction.add(R.id.fragment_combat_manager_attack3, this.fragmentAttack3);

            this.fragmentAttack4 = new CombatManagerAttackFragment();
            Bundle bundle4 = new Bundle();
            bundle4.putSerializable("attaque", this.combatManager.getCombat().getPokemon2().getAttaque4());
            this.fragmentAttack4.setArguments(bundle4);
            fragmentTransaction.add(R.id.fragment_combat_manager_attack4, this.fragmentAttack4);
        }

        // Commit changes
        fragmentTransaction.commit();

        // Get console fragment
        CombatManagerConsoleFragment fragmentConsole = (CombatManagerConsoleFragment)fragmentManager.findFragmentById(R.id.fragment_combat_manager_console);
        this.console = fragmentConsole.getConsole();
        this.consoleSV = fragmentConsole.getConsoleSV();

        // Update messages in console
        this.console.setText(this.combatManager.getConsole());
        this.consoleSV.post(new Runnable() {
            @Override
            public void run() {
                CombatManagerShowActivity.this.consoleSV.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public CombatManager getCombatManager(){
        return this.combatManager;
    }

    public Dresseur getDresseurConnected(){ return this.dresseurConnected; }

    public TextView getConsole(){ return this.console; }

    public CombatManagerAttackFragment getFragmentAttack1(){
        return this.fragmentAttack1;
    }

    public CombatManagerAttackFragment getFragmentAttack2(){
        return this.fragmentAttack2;
    }

    public CombatManagerAttackFragment getFragmentAttack3(){
        return this.fragmentAttack3;
    }

    public CombatManagerAttackFragment getFragmentAttack4(){
        return this.fragmentAttack4;
    }

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
                // Update messages in console
                CombatManagerShowActivity.this.console.setText(combatManager.getConsole());
                CombatManagerShowActivity.this.consoleSV.post(new Runnable() {
                    @Override
                    public void run() {
                        CombatManagerShowActivity.this.consoleSV.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });

                if (combatManager.getCombat().isDresseur1Vainqueur() || combatManager.getCombat().isDresseur2Vainqueur()) {
                    CombatManagerShowActivity.this.fragmentAttack1.getAttackContainer().setEnabled(false);
                    CombatManagerShowActivity.this.fragmentAttack2.getAttackContainer().setEnabled(false);
                    CombatManagerShowActivity.this.fragmentAttack3.getAttackContainer().setEnabled(false);
                    CombatManagerShowActivity.this.fragmentAttack4.getAttackContainer().setEnabled(false);

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    // Set opponent view
                    CombatManagerOpponentFragment fragmentOpponent = (CombatManagerOpponentFragment) fragmentManager.findFragmentById(R.id.fragment_combat_manager_opponent);
                    fragmentOpponent.initializeComponent(fragmentOpponent.getView());
                    // Set player view
                    CombatManagerPlayerFragment fragmentPlayer = (CombatManagerPlayerFragment) fragmentManager.findFragmentById(R.id.fragment_combat_manager_player);
                    fragmentPlayer.initializeComponent(fragmentPlayer.getView());

                    new FinishFightTask(CombatManagerShowActivity.this, combatManager.getCombat(), dresseurConnected).execute();
                }else{
                    CombatManagerShowActivity.this.combatManager = combatManager;

                    // Update data in fragment
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    if (CombatManagerShowActivity.this.combatManager.getCombat().getDresseur1().getId() == CombatManagerShowActivity.this.dresseurConnected.getId()) {
                        // Add attacks fragments binding with pokemon1
                        CombatManagerShowActivity.this.fragmentAttack1 = new CombatManagerAttackFragment();
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("attaque", CombatManagerShowActivity.this.combatManager.getCombat().getPokemon1().getAttaque1());
                        CombatManagerShowActivity.this.fragmentAttack1.setArguments(bundle1);
                        fragmentTransaction.add(R.id.fragment_combat_manager_attack1, CombatManagerShowActivity.this.fragmentAttack1);

                        CombatManagerShowActivity.this.fragmentAttack2 = new CombatManagerAttackFragment();
                        Bundle bundle2 = new Bundle();
                        bundle2.putSerializable("attaque", CombatManagerShowActivity.this.combatManager.getCombat().getPokemon1().getAttaque2());
                        CombatManagerShowActivity.this.fragmentAttack2.setArguments(bundle2);
                        fragmentTransaction.add(R.id.fragment_combat_manager_attack2, CombatManagerShowActivity.this.fragmentAttack2);

                        CombatManagerShowActivity.this.fragmentAttack3 = new CombatManagerAttackFragment();
                        Bundle bundle3 = new Bundle();
                        bundle3.putSerializable("attaque", CombatManagerShowActivity.this.combatManager.getCombat().getPokemon1().getAttaque3());
                        CombatManagerShowActivity.this.fragmentAttack3.setArguments(bundle3);
                        fragmentTransaction.add(R.id.fragment_combat_manager_attack3, CombatManagerShowActivity.this.fragmentAttack3);

                        CombatManagerShowActivity.this.fragmentAttack4 = new CombatManagerAttackFragment();
                        Bundle bundle4 = new Bundle();
                        bundle4.putSerializable("attaque", CombatManagerShowActivity.this.combatManager.getCombat().getPokemon1().getAttaque4());
                        CombatManagerShowActivity.this.fragmentAttack4.setArguments(bundle4);
                        fragmentTransaction.add(R.id.fragment_combat_manager_attack4, CombatManagerShowActivity.this.fragmentAttack4);
                    } else if (CombatManagerShowActivity.this.combatManager.getCombat().getDresseur2().getId() == CombatManagerShowActivity.this.dresseurConnected.getId()) {
                        // Add attacks fragments binding with pokemon2
                        CombatManagerShowActivity.this.fragmentAttack1 = new CombatManagerAttackFragment();
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("attaque", CombatManagerShowActivity.this.combatManager.getCombat().getPokemon2().getAttaque1());
                        CombatManagerShowActivity.this.fragmentAttack1.setArguments(bundle1);
                        fragmentTransaction.add(R.id.fragment_combat_manager_attack1, CombatManagerShowActivity.this.fragmentAttack1);

                        CombatManagerShowActivity.this.fragmentAttack2 = new CombatManagerAttackFragment();
                        Bundle bundle2 = new Bundle();
                        bundle2.putSerializable("attaque", CombatManagerShowActivity.this.combatManager.getCombat().getPokemon2().getAttaque2());
                        CombatManagerShowActivity.this.fragmentAttack2.setArguments(bundle2);
                        fragmentTransaction.add(R.id.fragment_combat_manager_attack2, CombatManagerShowActivity.this.fragmentAttack2);

                        CombatManagerShowActivity.this.fragmentAttack3 = new CombatManagerAttackFragment();
                        Bundle bundle3 = new Bundle();
                        bundle3.putSerializable("attaque", CombatManagerShowActivity.this.combatManager.getCombat().getPokemon2().getAttaque3());
                        CombatManagerShowActivity.this.fragmentAttack3.setArguments(bundle3);
                        fragmentTransaction.add(R.id.fragment_combat_manager_attack3, CombatManagerShowActivity.this.fragmentAttack3);

                        CombatManagerShowActivity.this.fragmentAttack4 = new CombatManagerAttackFragment();
                        Bundle bundle4 = new Bundle();
                        bundle4.putSerializable("attaque", CombatManagerShowActivity.this.combatManager.getCombat().getPokemon2().getAttaque4());
                        CombatManagerShowActivity.this.fragmentAttack4.setArguments(bundle4);
                        fragmentTransaction.add(R.id.fragment_combat_manager_attack4, CombatManagerShowActivity.this.fragmentAttack4);
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

        // Register receiver
        this.registerReceiver(this.receiver, intentFilter);
    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        this.context.unregisterReceiver(this.receiver);
    }

    @Override
    public void onBackPressed() {
        // Display a message to user that explain he can not perform this action without loose fight
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setIcon(0);
        builder.setTitle(R.string.combat_manager_desertion_fight_title);
        builder.setMessage(
                this.getString(
                        R.string.combat_manager_desertion_fight_message));
        builder.setPositiveButton(
                this.getString(android.R.string.yes),
                new Dialog.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        // Updated the fight
                        if (CombatManagerShowActivity.this.combatManager.getCombat().getDresseur1().getId() == CombatManagerShowActivity.this.dresseurConnected.getId()){
                            CombatManagerShowActivity.this.combatManager.getCombat().setDresseur2Vainqueur(true);
                            CombatManagerShowActivity.this.combatManager.getCombat().setPokemon2Vainqueur(true);
                            CombatManagerShowActivity.this.combatManager.getCombat().setDresseur1Vainqueur(false);
                            CombatManagerShowActivity.this.combatManager.getCombat().setPokemon1Vainqueur(false);
                        }else if (CombatManagerShowActivity.this.combatManager.getCombat().getDresseur2().getId() == CombatManagerShowActivity.this.dresseurConnected.getId()){
                            CombatManagerShowActivity.this.combatManager.getCombat().setDresseur2Vainqueur(false);
                            CombatManagerShowActivity.this.combatManager.getCombat().setPokemon2Vainqueur(false);
                            CombatManagerShowActivity.this.combatManager.getCombat().setDresseur1Vainqueur(true);
                            CombatManagerShowActivity.this.combatManager.getCombat().setPokemon1Vainqueur(true);
                        }

                        // Finish fight
                        new DesertionFightTask(((Dialog) dialog).getContext(), CombatManagerShowActivity.this.combatManager,
                                CombatManagerShowActivity.this.dresseurConnected).execute();
                        return;
                    }
                });
        builder.setNegativeButton(
                this.getString(android.R.string.no),
                new Dialog.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });
        builder.show();
    }

    public static class FinishFightTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to update. */
        private final Combat combat;
        /** Dresseur winner **/
        private final Dresseur dresseurConnected;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param combat The entity to insert in the DB
         * @param context The context from where the aSyncTask is
         * called
         */
        public FinishFightTask(final Context context,
                                final Combat combat, final Dresseur dresseurConnected) {
            super();
            this.ctx = context;
            this.combat = combat;
            this.dresseurConnected = dresseurConnected;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = 0;
            // Finish task is launched on api when launch attack
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
                if(this.dresseurConnected.getId() == this.combat.getDresseur1().getId()){
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
                                        Intent myIntent = new Intent(((Dialog) dialog).getContext(), ConnectedChooseActionActivity.class);
                                        myIntent.putExtra("dresseur", (Serializable) FinishFightTask.this.dresseurConnected);
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
                                        Intent myIntent = new Intent(((Dialog) dialog).getContext(), ConnectedChooseActionActivity.class);
                                        myIntent.putExtra("dresseur", (Serializable) FinishFightTask.this.dresseurConnected);
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
                                    Intent myIntent = new Intent(((Dialog) dialog).getContext(), ConnectedChooseActionActivity.class);
                                    myIntent.putExtra("dresseur", (Serializable) FinishFightTask.this.dresseurConnected);
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
                                    Intent myIntent = new Intent(((Dialog) dialog).getContext(), ConnectedChooseActionActivity.class);
                                    myIntent.putExtra("dresseur", (Serializable) FinishFightTask.this.dresseurConnected);
                                    ((Dialog) dialog).getContext().startActivity(myIntent);
                                    return;
                                }
                            });
                    builder.show();
                }
            }

            //this.progress.dismiss();

        }
    }

    public static class DesertionFightTask extends AsyncTask<Void, Void, Integer> {
        /** AsyncTask's context. */
        private final android.content.Context ctx;
        /** Entity to update. */
        private final CombatManager combatManager;
        /** Dresseur winner **/
        private final Dresseur dresseurConnected;
        /** Progress Dialog. */
        private ProgressDialog progress;

        /**
         * Constructor of the task.
         * @param combatManager The entity which contains an entity to insert in the DB
         * @param context The context from where the aSyncTask is
         * called
         */
        public DesertionFightTask(final Context context,
                               final CombatManager combatManager, final Dresseur dresseurConnected) {
            super();
            this.ctx = context;
            this.combatManager = combatManager;
            this.dresseurConnected = dresseurConnected;
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
            Integer result = -1;

            try {
                CombatWebServiceClientAdapter webService = new CombatWebServiceClientAdapter(this.ctx);
                result = webService.updateFromCombatManager(this.combatManager);
            } catch (Exception e) {
                android.util.Log.e("CombatManagerShowActivity", e.getMessage());
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
                                R.string.combat_manager_finish_fight_error_message));
                builder.setPositiveButton(
                        this.ctx.getString(android.R.string.yes),
                        new Dialog.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        });
                builder.show();
            }

            this.progress.dismiss();

        }
    }
}
