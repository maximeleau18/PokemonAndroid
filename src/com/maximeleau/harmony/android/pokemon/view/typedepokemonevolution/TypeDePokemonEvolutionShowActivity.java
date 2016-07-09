/**************************************************************************
 * TypeDePokemonEvolutionShowActivity.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.view.typedepokemonevolution.TypeDePokemonEvolutionShowFragment.DeleteCallback;
import android.os.Bundle;

/** TypeDePokemonEvolution show Activity.
 *
 * This only contains a TypeDePokemonEvolutionShowFragment.
 *
 * @see android.app.Activity
 */
public class TypeDePokemonEvolutionShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_typedepokemonevolution_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
