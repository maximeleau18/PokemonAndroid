/**************************************************************************
 * TypeAttaqueProviderAdapterBase.java, pokemon Android
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



import com.maximeleau.harmony.android.pokemon.entity.TypeAttaque;
import com.maximeleau.harmony.android.pokemon.provider.ProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeAttaqueContract;
import com.maximeleau.harmony.android.pokemon.data.TypeAttaqueSQLiteAdapter;

/**
 * TypeAttaqueProviderAdapterBase.
 */
public abstract class TypeAttaqueProviderAdapterBase
                extends ProviderAdapter<TypeAttaque> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypeAttaqueProviderAdapter";

    /** TYPEATTAQUE_URI. */
    public      static Uri TYPEATTAQUE_URI;

    /** typeAttaque type. */
    protected static final String typeAttaqueType =
            "typeattaque";

    /** TYPEATTAQUE_ALL. */
    protected static final int TYPEATTAQUE_ALL =
            1719667751;
    /** TYPEATTAQUE_ONE. */
    protected static final int TYPEATTAQUE_ONE =
            1719667752;


    /**
     * Static constructor.
     */
    static {
        TYPEATTAQUE_URI =
                PokemonProvider.generateUri(
                        typeAttaqueType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeAttaqueType,
                TYPEATTAQUE_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeAttaqueType + "/#",
                TYPEATTAQUE_ONE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public TypeAttaqueProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new TypeAttaqueSQLiteAdapter(provider.getContext()));

        this.uriIds.add(TYPEATTAQUE_ALL);
        this.uriIds.add(TYPEATTAQUE_ONE);
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
            case TYPEATTAQUE_ALL:
                result = collection + "typeattaque";
                break;
            case TYPEATTAQUE_ONE:
                result = single + "typeattaque";
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
            case TYPEATTAQUE_ONE:
                String id = uri.getPathSegments().get(1);
                selection = TypeAttaqueContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case TYPEATTAQUE_ALL:
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
            case TYPEATTAQUE_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(TypeAttaqueContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            TYPEATTAQUE_URI,
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

            case TYPEATTAQUE_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case TYPEATTAQUE_ONE:
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
            case TYPEATTAQUE_ONE:
                selectionArgs = new String[1];
                selection = TypeAttaqueContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case TYPEATTAQUE_ALL:
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
        return TYPEATTAQUE_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = TypeAttaqueContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    TypeAttaqueContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

