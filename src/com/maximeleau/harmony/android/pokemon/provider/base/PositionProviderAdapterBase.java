/**************************************************************************
 * PositionProviderAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.provider.base;

import android.content.ContentUris;
import android.content.ContentValues;


import com.google.common.collect.ObjectArrays;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;



import com.maximeleau.harmony.android.pokemon.entity.Position;
import com.maximeleau.harmony.android.pokemon.provider.ProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.PositionContract;
import com.maximeleau.harmony.android.pokemon.data.PositionSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.ZoneSQLiteAdapter;

/**
 * PositionProviderAdapterBase.
 */
public abstract class PositionProviderAdapterBase
                extends ProviderAdapter<Position> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PositionProviderAdapter";

    /** POSITION_URI. */
    public      static Uri POSITION_URI;

    /** position type. */
    protected static final String positionType =
            "position";

    /** POSITION_ALL. */
    protected static final int POSITION_ALL =
            812449097;
    /** POSITION_ONE. */
    protected static final int POSITION_ONE =
            812449098;

    /** POSITION_ZONE. */
    protected static final int POSITION_ZONE =
            812449099;

    /**
     * Static constructor.
     */
    static {
        POSITION_URI =
                PokemonProvider.generateUri(
                        positionType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                positionType,
                POSITION_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                positionType + "/#",
                POSITION_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                positionType + "/#" + "/zone",
                POSITION_ZONE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PositionProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new PositionSQLiteAdapter(provider.getContext()));

        this.uriIds.add(POSITION_ALL);
        this.uriIds.add(POSITION_ONE);
        this.uriIds.add(POSITION_ZONE);
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
            case POSITION_ALL:
                result = collection + "position";
                break;
            case POSITION_ONE:
                result = single + "position";
                break;
            case POSITION_ZONE:
                result = single + "position";
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
            case POSITION_ONE:
                String id = uri.getPathSegments().get(1);
                selection = PositionContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case POSITION_ALL:
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
            case POSITION_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PositionContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            POSITION_URI,
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
        android.database.Cursor positionCursor;

        switch (matchedUri) {

            case POSITION_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case POSITION_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case POSITION_ZONE:
                positionCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (positionCursor.getCount() > 0) {
                    positionCursor.moveToFirst();
                    int zoneId = positionCursor.getInt(
                            positionCursor.getColumnIndex(
                                    PositionContract.COL_ZONE_ID));

                    ZoneSQLiteAdapter zoneAdapter = new ZoneSQLiteAdapter(this.ctx);
                    zoneAdapter.open(this.getDb());
                    result = zoneAdapter.query(zoneId);
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
            case POSITION_ONE:
                selectionArgs = new String[1];
                selection = PositionContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case POSITION_ALL:
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
        return POSITION_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = PositionContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    PositionContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

