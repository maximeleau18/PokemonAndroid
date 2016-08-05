package com.maximeleau.harmony.android.pokemon.view.combatmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.CombatManager;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.microsoft.azure.engagement.activity.EngagementFragmentActivity;

/**
 * Created by Maxime Léau on 03/08/2016.
 */
public class CombatManagerShowActivity extends EngagementFragmentActivity {

    private CombatManager combatManager;
    private Dresseur dresseurConnected;
    private TextView console;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.combatManager = (CombatManager) intent.getSerializableExtra("combatManager");
        this.dresseurConnected = (Dresseur) intent.getSerializableExtra("dresseur");
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
}
