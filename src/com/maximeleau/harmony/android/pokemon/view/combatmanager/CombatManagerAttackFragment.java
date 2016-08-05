package com.maximeleau.harmony.android.pokemon.view.combatmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.Attaque;
import com.maximeleau.harmony.android.pokemon.entity.CombatManager;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;

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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.attack = (Attaque) getArguments().getSerializable("attaque");
        View view = inflater.inflate(R.layout.fragment_combat_manager_attack, container, false);
        initializeComponent(view);
        return view;
    }

    private void initializeComponent(View view) {
        this.attackContainer = (LinearLayout) view.findViewById(R.id.combat_manager_attack_ll);
        this.attackContainer.setClickable(true);
        this.attackContainer.setOnClickListener(onClickAttack());

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

                if (dresseurConnected.getId() == combatManager.getCombat().getDresseur1().getId()){
                    console.setText(String.format(Locale.FRANCE, "%s %s %s", combatManager.getCombat().getPokemon1().getTypeDePokemon().getNom(),
                            ctx.getResources().getString(R.string.combat_manager_console_launch_attack),
                            CombatManagerAttackFragment.this.attack.getNom()));
                }else{
                    console.setText(String.format(Locale.FRANCE, "%s %s %s", combatManager.getCombat().getPokemon2().getTypeDePokemon().getNom(),
                            ctx.getResources().getString(R.string.combat_manager_console_launch_attack),
                            CombatManagerAttackFragment.this.attack.getNom()));
                }
            }
        };
    }
}
