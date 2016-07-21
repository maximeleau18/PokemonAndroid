/**************************************************************************
 * PositionSQLiteAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.data;

import com.maximeleau.harmony.android.pokemon.data.base.PositionSQLiteAdapterBase;


/**
 * Position adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class PositionSQLiteAdapter extends PositionSQLiteAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public PositionSQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }
}
