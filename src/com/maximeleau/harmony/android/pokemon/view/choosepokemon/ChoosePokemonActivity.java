package com.maximeleau.harmony.android.pokemon.view.choosepokemon;

import android.content.Intent;
import android.os.Bundle;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.view.pokemon.PokemonListFragment;
import com.microsoft.azure.engagement.activity.EngagementFragmentActivity;

/**
 * Created by Maxime Léau on 02/08/2016.
 */
public class ChoosePokemonActivity extends EngagementFragmentActivity {
    private Dresseur dresseur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.dresseur = (Dresseur) intent.getSerializableExtra("dresseur");

        setContentView(R.layout.activity_choose_pokemon);
    }

    public Dresseur getDresseur(){
        return this.dresseur;
    }



}
