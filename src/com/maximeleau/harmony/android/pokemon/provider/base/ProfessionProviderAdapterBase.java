/**************************************************************************
 * ProfessionProviderAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Aug 5, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.provider.base;

import android.content.ContentUris;
import android.content.ContentValues;


import com.google.common.collect.ObjectArrays;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;



import com.maximeleau.harmony.android.pokemon.entity.Profession;
import com.maximeleau.harmony.android.pokemon.provider.ProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.ProfessionContract;
import com.maximeleau.harmony.android.pokemon.data.ProfessionSQLiteAdapter;

/**
 * ProfessionProviderAdapterBase.
 */
public abstract class ProfessionProviderAdapterBase
                extends ProviderAdapter<Profession> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ProfessionProviderAdapter";

    /** PROFESSION_URI. */
    public      static Uri PROFESSION_URI;

    /** profession type. */
    protected static final String professionType =
            "profession";

    /** PROFESSION_ALL. */
    protected static final int PROFESSION_ALL =
            783203612;
    /** PROFESSION_ONE. */
    protected static final int PROFESSION_ONE =
            783203613;


    /**
     * Static constructor.
     */
    static {
        PROFESSION_URI =
                PokemonProvider.generateUri(
                        professionType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                professionType,
                PROFESSION_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                professionType + "/#",
                PROFESSION_ONE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public ProfessionProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new ProfessionSQLiteAdapter(provider.getContext()));

        this.uriIds.add(PROFESSION_ALL);
        this.uriIds.add(PROFESSION_ONE);
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
            case PROFESSION_ALL:
                result = collection + "profession";
                break;
            case PROFESSION_ONE:
                result = single + "profession";
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
            case PROFESSION_ONE:
                String id = uri.getPathSegments().get(1);
                selection = ProfessionContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case PROFESSION_ALL:
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
            case PROFESSION_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(ProfessionContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            PROFESSION_URI,
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

        switch (matchedUri) {

            case PROFESSION_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case PROFESSION_ONE:
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
            case PROFESSION_ONE:
                selectionArgs = new String[1];
                selection = ProfessionContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case PROFESSION_ALL:
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
        return PROFESSION_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = ProfessionContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    ProfessionContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

