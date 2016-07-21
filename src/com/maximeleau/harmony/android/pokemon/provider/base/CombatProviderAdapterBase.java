/**************************************************************************
 * CombatProviderAdapterBase.java, pokemon Android
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



import com.maximeleau.harmony.android.pokemon.entity.Combat;
import com.maximeleau.harmony.android.pokemon.provider.ProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.CombatContract;
import com.maximeleau.harmony.android.pokemon.data.CombatSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PokemonSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.DresseurSQLiteAdapter;

/**
 * CombatProviderAdapterBase.
 */
public abstract class CombatProviderAdapterBase
                extends ProviderAdapter<Combat> {

    /** TAG for debug purpose. */
    protected static final String TAG = "CombatProviderAdapter";

    /** COMBAT_URI. */
    public      static Uri COMBAT_URI;

    /** combat type. */
    protected static final String combatType =
            "combat";

    /** COMBAT_ALL. */
    protected static final int COMBAT_ALL =
            2024008468;
    /** COMBAT_ONE. */
    protected static final int COMBAT_ONE =
            2024008469;

    /** COMBAT_POKEMON1. */
    protected static final int COMBAT_POKEMON1 =
            2024008470;
    /** COMBAT_POKEMON2. */
    protected static final int COMBAT_POKEMON2 =
            2024008471;
    /** COMBAT_DRESSEUR1. */
    protected static final int COMBAT_DRESSEUR1 =
            2024008472;
    /** COMBAT_DRESSEUR2. */
    protected static final int COMBAT_DRESSEUR2 =
            2024008473;

    /**
     * Static constructor.
     */
    static {
        COMBAT_URI =
                PokemonProvider.generateUri(
                        combatType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                combatType,
                COMBAT_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                combatType + "/#",
                COMBAT_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                combatType + "/#" + "/pokemon1",
                COMBAT_POKEMON1);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                combatType + "/#" + "/pokemon2",
                COMBAT_POKEMON2);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                combatType + "/#" + "/dresseur1",
                COMBAT_DRESSEUR1);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                combatType + "/#" + "/dresseur2",
                COMBAT_DRESSEUR2);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public CombatProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new CombatSQLiteAdapter(provider.getContext()));

        this.uriIds.add(COMBAT_ALL);
        this.uriIds.add(COMBAT_ONE);
        this.uriIds.add(COMBAT_POKEMON1);
        this.uriIds.add(COMBAT_POKEMON2);
        this.uriIds.add(COMBAT_DRESSEUR1);
        this.uriIds.add(COMBAT_DRESSEUR2);
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
            case COMBAT_ALL:
                result = collection + "combat";
                break;
            case COMBAT_ONE:
                result = single + "combat";
                break;
            case COMBAT_POKEMON1:
                result = single + "combat";
                break;
            case COMBAT_POKEMON2:
                result = single + "combat";
                break;
            case COMBAT_DRESSEUR1:
                result = single + "combat";
                break;
            case COMBAT_DRESSEUR2:
                result = single + "combat";
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
            case COMBAT_ONE:
                String id = uri.getPathSegments().get(1);
                selection = CombatContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case COMBAT_ALL:
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
            case COMBAT_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(CombatContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            COMBAT_URI,
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
        android.database.Cursor combatCursor;

        switch (matchedUri) {

            case COMBAT_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case COMBAT_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case COMBAT_POKEMON1:
                combatCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (combatCursor.getCount() > 0) {
                    combatCursor.moveToFirst();
                    int pokemon1Id = combatCursor.getInt(
                            combatCursor.getColumnIndex(
                                    CombatContract.COL_POKEMON1_ID));

                    PokemonSQLiteAdapter pokemonAdapter = new PokemonSQLiteAdapter(this.ctx);
                    pokemonAdapter.open(this.getDb());
                    result = pokemonAdapter.query(pokemon1Id);
                }
                break;

            case COMBAT_POKEMON2:
                combatCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (combatCursor.getCount() > 0) {
                    combatCursor.moveToFirst();
                    int pokemon2Id = combatCursor.getInt(
                            combatCursor.getColumnIndex(
                                    CombatContract.COL_POKEMON2_ID));

                    PokemonSQLiteAdapter pokemonAdapter = new PokemonSQLiteAdapter(this.ctx);
                    pokemonAdapter.open(this.getDb());
                    result = pokemonAdapter.query(pokemon2Id);
                }
                break;

            case COMBAT_DRESSEUR1:
                combatCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (combatCursor.getCount() > 0) {
                    combatCursor.moveToFirst();
                    int dresseur1Id = combatCursor.getInt(
                            combatCursor.getColumnIndex(
                                    CombatContract.COL_DRESSEUR1_ID));

                    DresseurSQLiteAdapter dresseurAdapter = new DresseurSQLiteAdapter(this.ctx);
                    dresseurAdapter.open(this.getDb());
                    result = dresseurAdapter.query(dresseur1Id);
                }
                break;

            case COMBAT_DRESSEUR2:
                combatCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (combatCursor.getCount() > 0) {
                    combatCursor.moveToFirst();
                    int dresseur2Id = combatCursor.getInt(
                            combatCursor.getColumnIndex(
                                    CombatContract.COL_DRESSEUR2_ID));

                    DresseurSQLiteAdapter dresseurAdapter = new DresseurSQLiteAdapter(this.ctx);
                    dresseurAdapter.open(this.getDb());
                    result = dresseurAdapter.query(dresseur2Id);
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
            case COMBAT_ONE:
                selectionArgs = new String[1];
                selection = CombatContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case COMBAT_ALL:
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
        return COMBAT_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = CombatContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    CombatContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

