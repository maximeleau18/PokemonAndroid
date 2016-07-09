/**************************************************************************
 * DresseurSQLiteAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.data;

import com.maximeleau.harmony.android.pokemon.data.base.DresseurSQLiteAdapterBase;


/**
 * Dresseur adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class DresseurSQLiteAdapter extends DresseurSQLiteAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public DresseurSQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }
}
