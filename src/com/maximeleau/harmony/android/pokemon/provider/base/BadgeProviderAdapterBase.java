/**************************************************************************
 * BadgeProviderAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.provider.base;

import android.content.ContentUris;
import android.content.ContentValues;


import com.google.common.collect.ObjectArrays;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;



import com.maximeleau.harmony.android.pokemon.entity.Badge;
import com.maximeleau.harmony.android.pokemon.provider.ProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;
import com.maximeleau.harmony.android.pokemon.data.BadgeSQLiteAdapter;

/**
 * BadgeProviderAdapterBase.
 */
public abstract class BadgeProviderAdapterBase
                extends ProviderAdapter<Badge> {

    /** TAG for debug purpose. */
    protected static final String TAG = "BadgeProviderAdapter";

    /** BADGE_URI. */
    public      static Uri BADGE_URI;

    /** badge type. */
    protected static final String badgeType =
            "badge";

    /** BADGE_ALL. */
    protected static final int BADGE_ALL =
            63941507;
    /** BADGE_ONE. */
    protected static final int BADGE_ONE =
            63941508;


    /**
     * Static constructor.
     */
    static {
        BADGE_URI =
                PokemonProvider.generateUri(
                        badgeType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                badgeType,
                BADGE_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                badgeType + "/#",
                BADGE_ONE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public BadgeProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new BadgeSQLiteAdapter(provider.getContext()));

        this.uriIds.add(BADGE_ALL);
        this.uriIds.add(BADGE_ONE);
    }

    @Override
    public String getType(final Uri uri) {
        String result;
        final String single =
                "vnc.android.cursor.item/"
                    + PokemonProvider.authority + ".";
        final String collection =
                "vnc.android.cursor.collection/"
                    + PokemonProvider.authority + ".";

        int matchedUri = PokemonProviderBase
                .getUriMatcher().match(uri);

        switch (matchedUri) {
            case BADGE_ALL:
                result = collection + "badge";
                break;
            case BADGE_ONE:
                result = single + "badge";
                break;
            default:
                result = null;
                break;
        }

        return result;
    }

    @Override
    public int delete(
            final Uri uri,
            String selection,
            String[] selectionArgs) {
        int matchedUri = PokemonProviderBase
                    .getUriMatcher().match(uri);
        int result = -1;
        switch (matchedUri) {
            case BADGE_ONE:
                String id = uri.getPathSegments().get(1);
                selection = BadgeContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case BADGE_ALL:
                result = this.adapter.delete(
                            selection,
                            selectionArgs);
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }

    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        int matchedUri = PokemonProviderBase
                .getUriMatcher().match(uri);
                Uri result = null;
        int id = 0;
        switch (matchedUri) {
            case BADGE_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(BadgeContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            BADGE_URI,
                            String.valueOf(id));
                }
                break;
            default:
                result = null;
                break;
        }
        return result;
    }

    @Override
    public android.database.Cursor query(final Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        final String sortOrder) {

        int matchedUri = PokemonProviderBase.getUriMatcher()
                .match(uri);
        android.database.Cursor result = null;
        android.database.Cursor badgeCursor;

        switch (matchedUri) {

            case BADGE_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case BADGE_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            default:
                result = null;
                break;
        }

        return result;
    }

    @Override
    public int update(
            final Uri uri,
            final ContentValues values,
            String selection,
            String[] selectionArgs) {

        
        int matchedUri = PokemonProviderBase.getUriMatcher()
                .match(uri);
        int result = -1;
        switch (matchedUri) {
            case BADGE_ONE:
                selectionArgs = new String[1];
                selection = BadgeContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case BADGE_ALL:
                result = this.adapter.update(
                            values,
                            selection,
                            selectionArgs);
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }



    /**
     * Get the entity URI.
     * @return The URI
     */
    @Override
    public Uri getUri() {
        return BADGE_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = BadgeContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    BadgeContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

