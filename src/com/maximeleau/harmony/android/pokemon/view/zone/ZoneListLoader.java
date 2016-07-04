/**************************************************************************
 * ZoneListLoader.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.zone;


import android.net.Uri;
import android.support.v4.content.CursorLoader;

import com.maximeleau.harmony.android.pokemon.criterias.base.CriteriaExpression;

/**
 * Zone Loader.
 */
public class ZoneListLoader
                extends CursorLoader {

    /**
     * Constructor.
     * @param ctx context
     */
    public ZoneListLoader(
            final android.content.Context ctx) {
        super(ctx);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param uri The URI associated with this loader
     * @param projection The projection to use
     * @param selection The selection
     * @param selectionArgs The selection Args
     * @param sortOrder The sort order
     */
    public ZoneListLoader(
                    android.content.Context ctx,
                    Uri uri,
                    String[] projection,
                    String selection,
                    String[] selectionArgs,
                    String sortOrder) {
        super(ctx,
                uri,
                projection,
                selection,
                selectionArgs,
                sortOrder);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param uri The URI associated with this loader
     * @param projection The projection to use
     * @param expression The CriteriaExpression
     * @param sortOrder The sort order
     */
    public ZoneListLoader(
                    android.content.Context ctx,
                    Uri uri,
                    String[] projection,
                    CriteriaExpression expression,
                    String sortOrder) {
        super(ctx,
                uri,
                projection,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                sortOrder);
    }
}
