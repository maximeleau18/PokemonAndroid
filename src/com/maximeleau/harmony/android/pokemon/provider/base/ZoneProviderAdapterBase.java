/**************************************************************************
 * ZoneProviderAdapterBase.java, pokemon Android
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



import com.maximeleau.harmony.android.pokemon.entity.Zone;
import com.maximeleau.harmony.android.pokemon.provider.ProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PositionContract;
import com.maximeleau.harmony.android.pokemon.data.ZoneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.AreneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.BadgeSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PositionSQLiteAdapter;

/**
 * ZoneProviderAdapterBase.
 */
public abstract class ZoneProviderAdapterBase
                extends ProviderAdapter<Zone> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ZoneProviderAdapter";

    /** ZONE_URI. */
    public      static Uri ZONE_URI;

    /** zone type. */
    protected static final String zoneType =
            "zone";

    /** ZONE_ALL. */
    protected static final int ZONE_ALL =
            2791372;
    /** ZONE_ONE. */
    protected static final int ZONE_ONE =
            2791373;

    /** ZONE_ARENES. */
    protected static final int ZONE_ARENES =
            2791374;
    /** ZONE_BADGES. */
    protected static final int ZONE_BADGES =
            2791375;
    /** ZONE_POSITIONS. */
    protected static final int ZONE_POSITIONS =
            2791376;

    /**
     * Static constructor.
     */
    static {
        ZONE_URI =
                PokemonProvider.generateUri(
                        zoneType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                zoneType,
                ZONE_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                zoneType + "/#",
                ZONE_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                zoneType + "/#" + "/arenes",
                ZONE_ARENES);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                zoneType + "/#" + "/badges",
                ZONE_BADGES);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                zoneType + "/#" + "/positions",
                ZONE_POSITIONS);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public ZoneProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new ZoneSQLiteAdapter(provider.getContext()));

        this.uriIds.add(ZONE_ALL);
        this.uriIds.add(ZONE_ONE);
        this.uriIds.add(ZONE_ARENES);
        this.uriIds.add(ZONE_BADGES);
        this.uriIds.add(ZONE_POSITIONS);
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
            case ZONE_ALL:
                result = collection + "zone";
                break;
            case ZONE_ONE:
                result = single + "zone";
                break;
            case ZONE_ARENES:
                result = collection + "zone";
                break;
            case ZONE_BADGES:
                result = collection + "zone";
                break;
            case ZONE_POSITIONS:
                result = collection + "zone";
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
            case ZONE_ONE:
                String id = uri.getPathSegments().get(1);
                selection = ZoneContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case ZONE_ALL:
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
            case ZONE_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(ZoneContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            ZONE_URI,
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
        int zoneId;

        switch (matchedUri) {

            case ZONE_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case ZONE_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case ZONE_ARENES:
                zoneId = Integer.parseInt(uri.getPathSegments().get(1));
                AreneSQLiteAdapter arenesAdapter = new AreneSQLiteAdapter(this.ctx);
                arenesAdapter.open(this.getDb());
                result = arenesAdapter.getByZonearenesInternal(zoneId, AreneContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case ZONE_BADGES:
                zoneId = Integer.parseInt(uri.getPathSegments().get(1));
                BadgeSQLiteAdapter badgesAdapter = new BadgeSQLiteAdapter(this.ctx);
                badgesAdapter.open(this.getDb());
                result = badgesAdapter.getByZonebadgesInternal(zoneId, BadgeContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case ZONE_POSITIONS:
                zoneId = Integer.parseInt(uri.getPathSegments().get(1));
                PositionSQLiteAdapter positionsAdapter = new PositionSQLiteAdapter(this.ctx);
                positionsAdapter.open(this.getDb());
                result = positionsAdapter.getByZonepositionsInternal(zoneId, PositionContract.ALIASED_COLS, selection, selectionArgs, null);
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
            case ZONE_ONE:
                selectionArgs = new String[1];
                selection = ZoneContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case ZONE_ALL:
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
        return ZONE_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = ZoneContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    ZoneContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

