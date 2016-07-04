/**************************************************************************
 * AreneProviderAdapterBase.java, pokemon Android
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



import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.provider.ProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;
import com.maximeleau.harmony.android.pokemon.data.AreneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.BadgeSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PositionSQLiteAdapter;

/**
 * AreneProviderAdapterBase.
 */
public abstract class AreneProviderAdapterBase
                extends ProviderAdapter<Arene> {

    /** TAG for debug purpose. */
    protected static final String TAG = "AreneProviderAdapter";

    /** ARENE_URI. */
    public      static Uri ARENE_URI;

    /** arene type. */
    protected static final String areneType =
            "arene";

    /** ARENE_ALL. */
    protected static final int ARENE_ALL =
            63525611;
    /** ARENE_ONE. */
    protected static final int ARENE_ONE =
            63525612;

    /** ARENE_MAITRE. */
    protected static final int ARENE_MAITRE =
            63525613;
    /** ARENE_BADGE. */
    protected static final int ARENE_BADGE =
            63525614;
    /** ARENE_POSITION. */
    protected static final int ARENE_POSITION =
            63525615;

    /**
     * Static constructor.
     */
    static {
        ARENE_URI =
                PokemonProvider.generateUri(
                        areneType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                areneType,
                ARENE_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                areneType + "/#",
                ARENE_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                areneType + "/#" + "/maitre",
                ARENE_MAITRE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                areneType + "/#" + "/badge",
                ARENE_BADGE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                areneType + "/#" + "/position",
                ARENE_POSITION);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public AreneProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new AreneSQLiteAdapter(provider.getContext()));

        this.uriIds.add(ARENE_ALL);
        this.uriIds.add(ARENE_ONE);
        this.uriIds.add(ARENE_MAITRE);
        this.uriIds.add(ARENE_BADGE);
        this.uriIds.add(ARENE_POSITION);
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
            case ARENE_ALL:
                result = collection + "arene";
                break;
            case ARENE_ONE:
                result = single + "arene";
                break;
            case ARENE_MAITRE:
                result = single + "arene";
                break;
            case ARENE_BADGE:
                result = single + "arene";
                break;
            case ARENE_POSITION:
                result = single + "arene";
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
            case ARENE_ONE:
                String id = uri.getPathSegments().get(1);
                selection = AreneContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case ARENE_ALL:
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
            case ARENE_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(AreneContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            ARENE_URI,
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
        android.database.Cursor areneCursor;

        switch (matchedUri) {

            case ARENE_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case ARENE_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case ARENE_MAITRE:
                areneCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (areneCursor.getCount() > 0) {
                    areneCursor.moveToFirst();
                    int maitreId = areneCursor.getInt(
                            areneCursor.getColumnIndex(
                                    AreneContract.COL_MAITRE_ID));

                    PersonnageNonJoueurSQLiteAdapter personnageNonJoueurAdapter = new PersonnageNonJoueurSQLiteAdapter(this.ctx);
                    personnageNonJoueurAdapter.open(this.getDb());
                    result = personnageNonJoueurAdapter.query(maitreId);
                }
                break;

            case ARENE_BADGE:
                areneCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (areneCursor.getCount() > 0) {
                    areneCursor.moveToFirst();
                    int badgeId = areneCursor.getInt(
                            areneCursor.getColumnIndex(
                                    AreneContract.COL_BADGE_ID));

                    BadgeSQLiteAdapter badgeAdapter = new BadgeSQLiteAdapter(this.ctx);
                    badgeAdapter.open(this.getDb());
                    result = badgeAdapter.query(badgeId);
                }
                break;

            case ARENE_POSITION:
                areneCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (areneCursor.getCount() > 0) {
                    areneCursor.moveToFirst();
                    int positionId = areneCursor.getInt(
                            areneCursor.getColumnIndex(
                                    AreneContract.COL_POSITION_ID));

                    PositionSQLiteAdapter positionAdapter = new PositionSQLiteAdapter(this.ctx);
                    positionAdapter.open(this.getDb());
                    result = positionAdapter.query(positionId);
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
            case ARENE_ONE:
                selectionArgs = new String[1];
                selection = AreneContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case ARENE_ALL:
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
        return ARENE_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = AreneContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    AreneContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

