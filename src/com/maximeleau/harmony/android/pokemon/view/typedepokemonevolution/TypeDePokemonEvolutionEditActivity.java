/**************************************************************************
 * TypeDePokemonEvolutionEditActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.typedepokemonevolution;

import com.maximeleau.harmony.android.pokemon.R;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** TypeDePokemonEvolution edit Activity.
 *
 * This only contains a TypeDePokemonEvolutionEditFragment.
 *
 * @see android.app.Activity
 */
public class TypeDePokemonEvolutionEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_typedepokemonevolution_edit;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
