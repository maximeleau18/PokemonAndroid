/**************************************************************************
 * TypeDePokemonZoneShowActivity.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.view.typedepokemonzone.TypeDePokemonZoneShowFragment.DeleteCallback;
import android.os.Bundle;

/** TypeDePokemonZone show Activity.
 *
 * This only contains a TypeDePokemonZoneShowFragment.
 *
 * @see android.app.Activity
 */
public class TypeDePokemonZoneShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_typedepokemonzone_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
