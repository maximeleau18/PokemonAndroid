/**************************************************************************
 * TypeDePokemonShowActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.typedepokemon;

import com.maximeleau.harmony.android.pokemon.R;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.view.typedepokemon.TypeDePokemonShowFragment.DeleteCallback;
import android.os.Bundle;

/** TypeDePokemon show Activity.
 *
 * This only contains a TypeDePokemonShowFragment.
 *
 * @see android.app.Activity
 */
public class TypeDePokemonShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_typedepokemon_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
