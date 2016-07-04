/**************************************************************************
 * TypeDePokemonZoneProviderAdapterBase.java, pokemon Android
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



import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonZone;
import com.maximeleau.harmony.android.pokemon.provider.ProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonZoneContract;
import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonZoneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.ZoneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonSQLiteAdapter;

/**
 * TypeDePokemonZoneProviderAdapterBase.
 */
public abstract class TypeDePokemonZoneProviderAdapterBase
                extends ProviderAdapter<TypeDePokemonZone> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypeDePokemonZoneProviderAdapter";

    /** TYPEDEPOKEMONZONE_URI. */
    public      static Uri TYPEDEPOKEMONZONE_URI;

    /** typeDePokemonZone type. */
    protected static final String typeDePokemonZoneType =
            "typedepokemonzone";

    /** TYPEDEPOKEMONZONE_ALL. */
    protected static final int TYPEDEPOKEMONZONE_ALL =
            1753443676;
    /** TYPEDEPOKEMONZONE_ONE. */
    protected static final int TYPEDEPOKEMONZONE_ONE =
            1753443677;

    /** TYPEDEPOKEMONZONE_ZONE. */
    protected static final int TYPEDEPOKEMONZONE_ZONE =
            1753443678;
    /** TYPEDEPOKEMONZONE_TYPEDEPOKEMON. */
    protected static final int TYPEDEPOKEMONZONE_TYPEDEPOKEMON =
            1753443679;

    /**
     * Static constructor.
     */
    static {
        TYPEDEPOKEMONZONE_URI =
                PokemonProvider.generateUri(
                        typeDePokemonZoneType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonZoneType,
                TYPEDEPOKEMONZONE_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonZoneType + "/#",
                TYPEDEPOKEMONZONE_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonZoneType + "/#" + "/zone",
                TYPEDEPOKEMONZONE_ZONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonZoneType + "/#" + "/typedepokemon",
                TYPEDEPOKEMONZONE_TYPEDEPOKEMON);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public TypeDePokemonZoneProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new TypeDePokemonZoneSQLiteAdapter(provider.getContext()));

        this.uriIds.add(TYPEDEPOKEMONZONE_ALL);
        this.uriIds.add(TYPEDEPOKEMONZONE_ONE);
        this.uriIds.add(TYPEDEPOKEMONZONE_ZONE);
        this.uriIds.add(TYPEDEPOKEMONZONE_TYPEDEPOKEMON);
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
            case TYPEDEPOKEMONZONE_ALL:
                result = collection + "typedepokemonzone";
                break;
            case TYPEDEPOKEMONZONE_ONE:
                result = single + "typedepokemonzone";
                break;
            case TYPEDEPOKEMONZONE_ZONE:
                result = single + "typedepokemonzone";
                break;
            case TYPEDEPOKEMONZONE_TYPEDEPOKEMON:
                result = single + "typedepokemonzone";
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
            case TYPEDEPOKEMONZONE_ONE:
                String id = uri.getPathSegments().get(1);
                selection = TypeDePokemonZoneContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case TYPEDEPOKEMONZONE_ALL:
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
            case TYPEDEPOKEMONZONE_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(TypeDePokemonZoneContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            TYPEDEPOKEMONZONE_URI,
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
        android.database.Cursor typeDePokemonZoneCursor;

        switch (matchedUri) {

            case TYPEDEPOKEMONZONE_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case TYPEDEPOKEMONZONE_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case TYPEDEPOKEMONZONE_ZONE:
                typeDePokemonZoneCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (typeDePokemonZoneCursor.getCount() > 0) {
                    typeDePokemonZoneCursor.moveToFirst();
                    int zoneId = typeDePokemonZoneCursor.getInt(
                            typeDePokemonZoneCursor.getColumnIndex(
                                    TypeDePokemonZoneContract.COL_ZONE_ID));

                    ZoneSQLiteAdapter zoneAdapter = new ZoneSQLiteAdapter(this.ctx);
                    zoneAdapter.open(this.getDb());
                    result = zoneAdapter.query(zoneId);
                }
                break;

            case TYPEDEPOKEMONZONE_TYPEDEPOKEMON:
                typeDePokemonZoneCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (typeDePokemonZoneCursor.getCount() > 0) {
                    typeDePokemonZoneCursor.moveToFirst();
                    int typeDePokemonId = typeDePokemonZoneCursor.getInt(
                            typeDePokemonZoneCursor.getColumnIndex(
                                    TypeDePokemonZoneContract.COL_TYPEDEPOKEMON_ID));

                    TypeDePokemonSQLiteAdapter typeDePokemonAdapter = new TypeDePokemonSQLiteAdapter(this.ctx);
                    typeDePokemonAdapter.open(this.getDb());
                    result = typeDePokemonAdapter.query(typeDePokemonId);
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
            case TYPEDEPOKEMONZONE_ONE:
                selectionArgs = new String[1];
                selection = TypeDePokemonZoneContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case TYPEDEPOKEMONZONE_ALL:
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
        return TYPEDEPOKEMONZONE_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = TypeDePokemonZoneContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    TypeDePokemonZoneContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

