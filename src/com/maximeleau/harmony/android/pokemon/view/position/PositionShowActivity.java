/**************************************************************************
 * PositionShowActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.position;

import com.maximeleau.harmony.android.pokemon.R;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.view.position.PositionShowFragment.DeleteCallback;
import android.os.Bundle;

/** Position show Activity.
 *
 * This only contains a PositionShowFragment.
 *
 * @see android.app.Activity
 */
public class PositionShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_position_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
