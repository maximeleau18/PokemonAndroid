/**************************************************************************
 * TypeDePokemonEvolutionCreateActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.typedepokemonevolution;

import com.maximeleau.harmony.android.pokemon.R;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * TypeDePokemonEvolution create Activity.
 *
 * This only contains a TypeDePokemonEvolutionCreateFragment.
 *
 * @see android.app.Activity
 */
public class TypeDePokemonEvolutionCreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_typedepokemonevolution_create;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
