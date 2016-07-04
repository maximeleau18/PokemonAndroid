/**************************************************************************
 * TypeDePokemonZoneEditActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.typedepokemonzone;

import com.maximeleau.harmony.android.pokemon.R;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** TypeDePokemonZone edit Activity.
 *
 * This only contains a TypeDePokemonZoneEditFragment.
 *
 * @see android.app.Activity
 */
public class TypeDePokemonZoneEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_typedepokemonzone_edit;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
