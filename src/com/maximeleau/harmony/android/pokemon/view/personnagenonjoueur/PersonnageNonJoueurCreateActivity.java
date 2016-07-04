/**************************************************************************
 * PersonnageNonJoueurCreateActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.personnagenonjoueur;

import com.maximeleau.harmony.android.pokemon.R;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** 
 * PersonnageNonJoueur create Activity.
 *
 * This only contains a PersonnageNonJoueurCreateFragment.
 *
 * @see android.app.Activity
 */
public class PersonnageNonJoueurCreateActivity extends HarmonyFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_personnagenonjoueur_create;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
