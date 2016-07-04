/**************************************************************************
 * AttaqueEditActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.attaque;

import com.maximeleau.harmony.android.pokemon.R;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** Attaque edit Activity.
 *
 * This only contains a AttaqueEditFragment.
 *
 * @see android.app.Activity
 */
public class AttaqueEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_attaque_edit;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
