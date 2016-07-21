/**************************************************************************
 * BadgeProviderAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.provider;

import com.maximeleau.harmony.android.pokemon.provider.base.BadgeProviderAdapterBase;
import com.maximeleau.harmony.android.pokemon.provider.base.PokemonProviderBase;

/**
 * BadgeProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class BadgeProviderAdapter
                    extends BadgeProviderAdapterBase {

    /**
     * Constructor.
     * @param ctx context
     */
    public BadgeProviderAdapter(
            final PokemonProviderBase provider) {
        super(provider);
    }
}

