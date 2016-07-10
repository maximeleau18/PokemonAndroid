/**************************************************************************
 * DresseurProviderAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.provider;

import com.maximeleau.harmony.android.pokemon.provider.base.DresseurProviderAdapterBase;
import com.maximeleau.harmony.android.pokemon.provider.base.PokemonProviderBase;

/**
 * DresseurProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class DresseurProviderAdapter
                    extends DresseurProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public DresseurProviderAdapter(
            final PokemonProviderBase provider) {
        super(provider);
    }
}

