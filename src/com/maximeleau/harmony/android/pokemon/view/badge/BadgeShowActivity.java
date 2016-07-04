/**************************************************************************
 * BadgeShowActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.badge;

import com.maximeleau.harmony.android.pokemon.R;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.view.badge.BadgeShowFragment.DeleteCallback;
import android.os.Bundle;

/** Badge show Activity.
 *
 * This only contains a BadgeShowFragment.
 *
 * @see android.app.Activity
 */
public class BadgeShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_badge_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
