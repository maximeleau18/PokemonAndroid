package com.maximeleau.harmony.android.pokemon.view.choosepokemon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.Attaque;

import java.io.Serializable;
import java.util.Locale;


/**
 * Created by Maxime Léau on 02/08/2016.
 */
public class ChoosePokemonAttackFragment extends Fragment {
    private Attaque attack;
    private TextView attackNameText;
    private TextView attackTypeNameText;
    private TextView attackPowerText;
    private TextView attackDammageText;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.attack = (Attaque) getArguments().getSerializable("attaque");
        View view = inflater.inflate(R.layout.fragment_choose_pokemon_attack, container, false);
        initializeComponent(view);
        return view;
    }

    private void initializeComponent(View view) {
        this.attackNameText = (TextView) view.findViewById(R.id.choose_pokemon_attack_name);
        this.attackTypeNameText = (TextView) view.findViewById(R.id.choose_pokemon_attack_type_value);
        this.attackPowerText = (TextView) view.findViewById(R.id.choose_pokemon_attack_power_value);
        this.attackDammageText = (TextView) view.findViewById(R.id.choose_pokemon_attack_dammage_value);

        this.attackNameText.setText(this.attack.getNom());
        this.attackTypeNameText.setText(this.attack.getTypeAttaque().getNom());
        this.attackPowerText.setText(String.format(Locale.FRANCE, "%d", this.attack.getPuissance()));
        this.attackDammageText.setText(String.format(Locale.FRANCE, "%d", this.attack.getDegats()));
    }


}
