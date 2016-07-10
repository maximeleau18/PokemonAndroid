/**************************************************************************
 * PokemonSQLiteAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.data;

import com.maximeleau.harmony.android.pokemon.data.base.PokemonSQLiteAdapterBase;


/**
 * Pokemon adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
public class PokemonSQLiteAdapter extends PokemonSQLiteAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public PokemonSQLiteAdapter(final android.content.Context ctx) {
        super(ctx);
    }
}
