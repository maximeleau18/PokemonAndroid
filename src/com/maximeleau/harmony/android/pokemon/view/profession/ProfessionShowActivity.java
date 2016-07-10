/**************************************************************************
 * ProfessionShowActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.profession;

import com.maximeleau.harmony.android.pokemon.R;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.view.profession.ProfessionShowFragment.DeleteCallback;
import android.os.Bundle;

/** Profession show Activity.
 *
 * This only contains a ProfessionShowFragment.
 *
 * @see android.app.Activity
 */
public class ProfessionShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_profession_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
