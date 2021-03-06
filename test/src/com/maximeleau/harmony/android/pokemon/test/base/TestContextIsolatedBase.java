/**************************************************************************
 * TestContextIsolatedBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Aug 5, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.test.base;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

import android.test.IsolatedContext;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;

/** Context isolated base test class. */
public class TestContextIsolatedBase extends IsolatedContext {

    /**
     * Constructor.
     * @param mockContentResolver {@link MockContentResolver}
     * @param targetContextWrapper {@link RenamingDelegatingContext}
     */
    public TestContextIsolatedBase(MockContentResolver mockContentResolver,
            RenamingDelegatingContext targetContextWrapper) {
        super(mockContentResolver, targetContextWrapper);
    }
    
    @Override
    public Object getSystemService(String name) {
        return this.getBaseContext().getSystemService(name);
    }
    
    @Override
    public void sendOrderedBroadcast(
            Intent intent, String receiverPermission) {
        this.getBaseContext().sendOrderedBroadcast(intent, receiverPermission);
    }
    
    @Override
    public Intent registerReceiver(
            BroadcastReceiver receiver,
            IntentFilter filter) {
        return this.getBaseContext().registerReceiver(receiver, filter);
    }
}
