/**************************************************************************
 * PersonnageNonJoueurBadgeEditActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.personnagenonjoueurbadge;

import com.maximeleau.harmony.android.pokemon.R;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;

import android.os.Bundle;

/** PersonnageNonJoueurBadge edit Activity.
 *
 * This only contains a PersonnageNonJoueurBadgeEditFragment.
 *
 * @see android.app.Activity
 */
public class PersonnageNonJoueurBadgeEditActivity extends HarmonyFragmentActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_personnagenonjoueurbadge_edit;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
