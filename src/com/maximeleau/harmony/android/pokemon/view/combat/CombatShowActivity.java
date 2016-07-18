/**************************************************************************
 * CombatShowActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 18, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.combat;

import com.maximeleau.harmony.android.pokemon.R;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.view.combat.CombatShowFragment.DeleteCallback;
import android.os.Bundle;

/** Combat show Activity.
 *
 * This only contains a CombatShowFragment.
 *
 * @see android.app.Activity
 */
public class CombatShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_combat_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
