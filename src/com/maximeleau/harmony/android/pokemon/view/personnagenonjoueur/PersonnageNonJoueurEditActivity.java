/**************************************************************************
 * PersonnageNonJoueurEditActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.personnagenonjoueur;

import com.maximeleau.harmony.android.pokemon.R;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** PersonnageNonJoueur edit Activity.
 *
 * This only contains a PersonnageNonJoueurEditFragment.
 *
 * @see android.app.Activity
 */
public class PersonnageNonJoueurEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_personnagenonjoueur_edit;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
