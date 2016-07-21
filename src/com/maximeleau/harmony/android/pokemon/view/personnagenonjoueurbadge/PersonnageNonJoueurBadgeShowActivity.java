/**************************************************************************
 * PersonnageNonJoueurBadgeShowActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.personnagenonjoueurbadge;

import com.maximeleau.harmony.android.pokemon.R;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.view.personnagenonjoueurbadge.PersonnageNonJoueurBadgeShowFragment.DeleteCallback;
import android.os.Bundle;

/** PersonnageNonJoueurBadge show Activity.
 *
 * This only contains a PersonnageNonJoueurBadgeShowFragment.
 *
 * @see android.app.Activity
 */
public class PersonnageNonJoueurBadgeShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_personnagenonjoueurbadge_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
