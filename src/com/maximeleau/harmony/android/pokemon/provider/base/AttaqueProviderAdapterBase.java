/**************************************************************************
 * AttaqueProviderAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.provider.base;

import android.content.ContentUris;
import android.content.ContentValues;


import com.google.common.collect.ObjectArrays;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;



import com.maximeleau.harmony.android.pokemon.entity.Attaque;
import com.maximeleau.harmony.android.pokemon.provider.ProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.AttaqueContract;
import com.maximeleau.harmony.android.pokemon.data.AttaqueSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.TypeAttaqueSQLiteAdapter;

/**
 * AttaqueProviderAdapterBase.
 */
public abstract class AttaqueProviderAdapterBase
                extends ProviderAdapter<Attaque> {

    /** TAG for debug purpose. */
    protected static final String TAG = "AttaqueProviderAdapter";

    /** ATTAQUE_URI. */
    public      static Uri ATTAQUE_URI;

    /** attaque type. */
    protected static final String attaqueType =
            "attaque";

    /** ATTAQUE_ALL. */
    protected static final int ATTAQUE_ALL =
            989309121;
    /** ATTAQUE_ONE. */
    protected static final int ATTAQUE_ONE =
            989309122;

    /** ATTAQUE_TYPEATTAQUE. */
    protected static final int ATTAQUE_TYPEATTAQUE =
            989309123;

    /**
     * Static constructor.
     */
    static {
        ATTAQUE_URI =
                PokemonProvider.generateUri(
                        attaqueType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                attaqueType,
                ATTAQUE_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                attaqueType + "/#",
                ATTAQUE_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                attaqueType + "/#" + "/typeattaque",
                ATTAQUE_TYPEATTAQUE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public AttaqueProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new AttaqueSQLiteAdapter(provider.getContext()));

        this.uriIds.add(ATTAQUE_ALL);
        this.uriIds.add(ATTAQUE_ONE);
        this.uriIds.add(ATTAQUE_TYPEATTAQUE);
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
            case ATTAQUE_ALL:
                result = collection + "attaque";
                break;
            case ATTAQUE_ONE:
                result = single + "attaque";
                break;
            case ATTAQUE_TYPEATTAQUE:
                result = single + "attaque";
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
            case ATTAQUE_ONE:
                String id = uri.getPathSegments().get(1);
                selection = AttaqueContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case ATTAQUE_ALL:
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
            case ATTAQUE_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(AttaqueContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            ATTAQUE_URI,
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
        android.database.Cursor attaqueCursor;

        switch (matchedUri) {

            case ATTAQUE_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case ATTAQUE_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case ATTAQUE_TYPEATTAQUE:
                attaqueCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (attaqueCursor.getCount() > 0) {
                    attaqueCursor.moveToFirst();
                    int typeAttaqueId = attaqueCursor.getInt(
                            attaqueCursor.getColumnIndex(
                                    AttaqueContract.COL_TYPEATTAQUE_ID));

                    TypeAttaqueSQLiteAdapter typeAttaqueAdapter = new TypeAttaqueSQLiteAdapter(this.ctx);
                    typeAttaqueAdapter.open(this.getDb());
                    result = typeAttaqueAdapter.query(typeAttaqueId);
                }
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
            case ATTAQUE_ONE:
                selectionArgs = new String[1];
                selection = AttaqueContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case ATTAQUE_ALL:
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
        return ATTAQUE_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = AttaqueContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    AttaqueContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

