/**************************************************************************
 * DresseurShowActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.dresseur;

import com.maximeleau.harmony.android.pokemon.R;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.view.dresseur.DresseurShowFragment.DeleteCallback;
import android.os.Bundle;

/** Dresseur show Activity.
 *
 * This only contains a DresseurShowFragment.
 *
 * @see android.app.Activity
 */
public class DresseurShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_dresseur_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
