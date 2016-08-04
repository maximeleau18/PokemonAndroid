package com.maximeleau.harmony.android.pokemon.view.combatmanager;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.CombatManager;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;

import java.util.Locale;

/**
 * Created by Maxime Léau on 03/08/2016.
 */
public class CombatManagerShowFragment extends Fragment {
    private Context context;
    private CombatManager combatManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_combat_manager_show, container, false);
        // Set context
        this.context = view.getContext();
        // Get data from activity
        CombatManagerShowActivity parent = (CombatManagerShowActivity) this.getActivity();
        this.combatManager = parent.getData();

        initializeComponent(view);
        return view;

    }

    private void initializeComponent(View view) {
        ImageView pokemonImage = (ImageView) view.findViewById(R.id.combat_manager_opponent_pokemon_iv);
        int drawableResourceId = getResources().getIdentifier(
                "pokemon_front_" + String.format(Locale.FRANCE, "%d" , this.combatManager.getCombat().getPokemon2().getTypeDePokemon().getNumPokedex()),
                "drawable", this.context.getPackageName());

        pokemonImage.setImageResource(drawableResourceId);
    }
}
