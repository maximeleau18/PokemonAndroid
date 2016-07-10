/**************************************************************************
 * TestContextIsolated.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.test;

import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;

import com.maximeleau.harmony.android.pokemon.test.base.TestContextIsolatedBase;

/** Context isolated test class. */
public class TestContextIsolated extends TestContextIsolatedBase {

    /**
     * Constructor.
     * @param mockContentResolver {@link MockContentResolver}
     * @param targetContextWrapper {@link RenamingDelegatingContext}
     */
    public TestContextIsolated(MockContentResolver mockContentResolver,
            RenamingDelegatingContext targetContextWrapper) {
        super(mockContentResolver, targetContextWrapper);
    }

}
