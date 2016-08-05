/**************************************************************************
 * RestResource.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Aug 5, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.entity.base;

import com.maximeleau.harmony.android.pokemon.entity.base.Resource;

import java.io.Serializable;

import org.joda.time.DateTime;

public interface RestResource extends Resource {
    /**
     * @return the local path
     */
    String getLocalPath();

    /**
     * @param value the local path to set
     */
    void setLocalPath(final String value);
}