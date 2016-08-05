package com.maximeleau.harmony.android.pokemon.view.combatmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.CombatManager;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;

import java.util.Locale;

/**
 * Created by Maxime Léau on 04/08/2016.
 */
public class CombatManagerOpponentFragment extends Fragment {
    private Context context;
    private CombatManager combatManager;
    private Dresseur dresseurConnected;
    private ImageView pokemonImage;
    private TextView pokemonTypeName;
    private TextView pokemonActualPv;
    private TextView pokemonMaxPv;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_combat_manager_opponent, container, false);
        // Set context
        this.context = view.getContext();
        // Get data from activity
        CombatManagerShowActivity parent = (CombatManagerShowActivity) this.getActivity();
        this.combatManager = parent.getCombatManager();
        this.dresseurConnected = parent.getDresseurConnected();

        initializeComponent(view);
        return view;

    }

    private void initializeComponent(View view) {
        this.pokemonImage = (ImageView) view.findViewById(R.id.combat_manager_opponent_pokemon_iv);
        this.pokemonTypeName = (TextView) view.findViewById(R.id.combat_manager_opponent_pokemon_type_name);
        this.pokemonActualPv = (TextView) view.findViewById(R.id.combat_manager_opponent_pokemon_actual_pv);
        this.pokemonMaxPv = (TextView) view.findViewById(R.id.combat_manager_opponent_pokemon_max_pv);

        if(this.combatManager.getCombat().getDresseur2().getId() == this.dresseurConnected.getId()) {
            int drawableResourceId = getResources().getIdentifier(
                    "pokemon_front_" + String.format(Locale.FRANCE, "%d", this.combatManager.getCombat().getPokemon1().getTypeDePokemon().getNumPokedex()),
                    "drawable", this.context.getPackageName());

            this.pokemonImage.setImageResource(drawableResourceId);

            this.pokemonTypeName.setText(this.combatManager.getCombat().getPokemon1().getTypeDePokemon().getNom());

            this.pokemonActualPv.setText(String.format(Locale.FRANCE, "%d", this.combatManager.getCombat().getPokemon1().getTypeDePokemon().getPv()));

            this.pokemonMaxPv.setText(String.format(Locale.FRANCE, "%d", this.combatManager.getCombat().getPokemon1().getTypeDePokemon().getPv()));
        }else{
            int drawableResourceId = getResources().getIdentifier(
                    "pokemon_front_" + String.format(Locale.FRANCE, "%d", this.combatManager.getCombat().getPokemon2().getTypeDePokemon().getNumPokedex()),
                    "drawable", this.context.getPackageName());

            this.pokemonImage.setImageResource(drawableResourceId);

            this.pokemonTypeName.setText(this.combatManager.getCombat().getPokemon2().getTypeDePokemon().getNom());

            this.pokemonActualPv.setText(String.format(Locale.FRANCE, "%d", this.combatManager.getCombat().getPokemon2().getTypeDePokemon().getPv()));

            this.pokemonMaxPv.setText(String.format(Locale.FRANCE, "%d", this.combatManager.getCombat().getPokemon2().getTypeDePokemon().getPv()));
        }
    }
}
