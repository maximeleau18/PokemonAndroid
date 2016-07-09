/**************************************************************************
 * TypeObjetShowActivity.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.typeobjet;

import com.maximeleau.harmony.android.pokemon.R;

import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyFragmentActivity;
import com.maximeleau.harmony.android.pokemon.view.typeobjet.TypeObjetShowFragment.DeleteCallback;
import android.os.Bundle;

/** TypeObjet show Activity.
 *
 * This only contains a TypeObjetShowFragment.
 *
 * @see android.app.Activity
 */
public class TypeObjetShowActivity 
        extends HarmonyFragmentActivity 
        implements DeleteCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setNavigationBack(true);
    }
    
    @Override
    protected int getContentView() {
        return R.layout.activity_typeobjet_show;
    }

    @Override
    public void onItemDeleted() {
        this.finish();
    }
}
