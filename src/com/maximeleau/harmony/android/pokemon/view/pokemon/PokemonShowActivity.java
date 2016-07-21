/**************************************************************************
 * PokemonShowActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.pokemon;

import com.maximeleau.harmony.android.pokemon.R;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.view.pokemon.PokemonShowFragment.DeleteCallback;
import android.os.Bundle;

/** Pokemon show Activity.
 *
 * This only contains a PokemonShowFragment.
 *
 * @see android.app.Activity
 */
public class PokemonShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_pokemon_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
