/**************************************************************************
 * PokemonProviderAdapterBase.java, pokemon Android
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



import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.provider.ProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;
import com.maximeleau.harmony.android.pokemon.data.PokemonSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.AttaqueSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurSQLiteAdapter;

/**
 * PokemonProviderAdapterBase.
 */
public abstract class PokemonProviderAdapterBase
                extends ProviderAdapter<Pokemon> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokemonProviderAdapter";

    /** POKEMON_URI. */
    public      static Uri POKEMON_URI;

    /** pokemon type. */
    protected static final String pokemonType =
            "pokemon";

    /** POKEMON_ALL. */
    protected static final int POKEMON_ALL =
            1265620147;
    /** POKEMON_ONE. */
    protected static final int POKEMON_ONE =
            1265620148;

    /** POKEMON_ATTAQUE1. */
    protected static final int POKEMON_ATTAQUE1 =
            1265620149;
    /** POKEMON_ATTAQUE2. */
    protected static final int POKEMON_ATTAQUE2 =
            1265620150;
    /** POKEMON_ATTAQUE3. */
    protected static final int POKEMON_ATTAQUE3 =
            1265620151;
    /** POKEMON_ATTAQUE4. */
    protected static final int POKEMON_ATTAQUE4 =
            1265620152;
    /** POKEMON_TYPEDEPOKEMON. */
    protected static final int POKEMON_TYPEDEPOKEMON =
            1265620153;
    /** POKEMON_PERSONNAGENONJOUEUR. */
    protected static final int POKEMON_PERSONNAGENONJOUEUR =
            1265620154;

    /**
     * Static constructor.
     */
    static {
        POKEMON_URI =
                PokemonProvider.generateUri(
                        pokemonType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                pokemonType,
                POKEMON_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                pokemonType + "/#",
                POKEMON_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                pokemonType + "/#" + "/attaque1",
                POKEMON_ATTAQUE1);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                pokemonType + "/#" + "/attaque2",
                POKEMON_ATTAQUE2);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                pokemonType + "/#" + "/attaque3",
                POKEMON_ATTAQUE3);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                pokemonType + "/#" + "/attaque4",
                POKEMON_ATTAQUE4);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                pokemonType + "/#" + "/typedepokemon",
                POKEMON_TYPEDEPOKEMON);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                pokemonType + "/#" + "/personnagenonjoueur",
                POKEMON_PERSONNAGENONJOUEUR);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PokemonProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new PokemonSQLiteAdapter(provider.getContext()));

        this.uriIds.add(POKEMON_ALL);
        this.uriIds.add(POKEMON_ONE);
        this.uriIds.add(POKEMON_ATTAQUE1);
        this.uriIds.add(POKEMON_ATTAQUE2);
        this.uriIds.add(POKEMON_ATTAQUE3);
        this.uriIds.add(POKEMON_ATTAQUE4);
        this.uriIds.add(POKEMON_TYPEDEPOKEMON);
        this.uriIds.add(POKEMON_PERSONNAGENONJOUEUR);
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
            case POKEMON_ALL:
                result = collection + "pokemon";
                break;
            case POKEMON_ONE:
                result = single + "pokemon";
                break;
            case POKEMON_ATTAQUE1:
                result = single + "pokemon";
                break;
            case POKEMON_ATTAQUE2:
                result = single + "pokemon";
                break;
            case POKEMON_ATTAQUE3:
                result = single + "pokemon";
                break;
            case POKEMON_ATTAQUE4:
                result = single + "pokemon";
                break;
            case POKEMON_TYPEDEPOKEMON:
                result = single + "pokemon";
                break;
            case POKEMON_PERSONNAGENONJOUEUR:
                result = single + "pokemon";
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
            case POKEMON_ONE:
                String id = uri.getPathSegments().get(1);
                selection = PokemonContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case POKEMON_ALL:
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
            case POKEMON_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PokemonContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            POKEMON_URI,
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
        android.database.Cursor pokemonCursor;

        switch (matchedUri) {

            case POKEMON_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case POKEMON_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case POKEMON_ATTAQUE1:
                pokemonCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokemonCursor.getCount() > 0) {
                    pokemonCursor.moveToFirst();
                    int attaque1Id = pokemonCursor.getInt(
                            pokemonCursor.getColumnIndex(
                                    PokemonContract.COL_ATTAQUE1_ID));

                    AttaqueSQLiteAdapter attaqueAdapter = new AttaqueSQLiteAdapter(this.ctx);
                    attaqueAdapter.open(this.getDb());
                    result = attaqueAdapter.query(attaque1Id);
                }
                break;

            case POKEMON_ATTAQUE2:
                pokemonCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokemonCursor.getCount() > 0) {
                    pokemonCursor.moveToFirst();
                    int attaque2Id = pokemonCursor.getInt(
                            pokemonCursor.getColumnIndex(
                                    PokemonContract.COL_ATTAQUE2_ID));

                    AttaqueSQLiteAdapter attaqueAdapter = new AttaqueSQLiteAdapter(this.ctx);
                    attaqueAdapter.open(this.getDb());
                    result = attaqueAdapter.query(attaque2Id);
                }
                break;

            case POKEMON_ATTAQUE3:
                pokemonCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokemonCursor.getCount() > 0) {
                    pokemonCursor.moveToFirst();
                    int attaque3Id = pokemonCursor.getInt(
                            pokemonCursor.getColumnIndex(
                                    PokemonContract.COL_ATTAQUE3_ID));

                    AttaqueSQLiteAdapter attaqueAdapter = new AttaqueSQLiteAdapter(this.ctx);
                    attaqueAdapter.open(this.getDb());
                    result = attaqueAdapter.query(attaque3Id);
                }
                break;

            case POKEMON_ATTAQUE4:
                pokemonCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokemonCursor.getCount() > 0) {
                    pokemonCursor.moveToFirst();
                    int attaque4Id = pokemonCursor.getInt(
                            pokemonCursor.getColumnIndex(
                                    PokemonContract.COL_ATTAQUE4_ID));

                    AttaqueSQLiteAdapter attaqueAdapter = new AttaqueSQLiteAdapter(this.ctx);
                    attaqueAdapter.open(this.getDb());
                    result = attaqueAdapter.query(attaque4Id);
                }
                break;

            case POKEMON_TYPEDEPOKEMON:
                pokemonCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokemonCursor.getCount() > 0) {
                    pokemonCursor.moveToFirst();
                    int typeDePokemonId = pokemonCursor.getInt(
                            pokemonCursor.getColumnIndex(
                                    PokemonContract.COL_TYPEDEPOKEMON_ID));

                    TypeDePokemonSQLiteAdapter typeDePokemonAdapter = new TypeDePokemonSQLiteAdapter(this.ctx);
                    typeDePokemonAdapter.open(this.getDb());
                    result = typeDePokemonAdapter.query(typeDePokemonId);
                }
                break;

            case POKEMON_PERSONNAGENONJOUEUR:
                pokemonCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (pokemonCursor.getCount() > 0) {
                    pokemonCursor.moveToFirst();
                    int personnageNonJoueurId = pokemonCursor.getInt(
                            pokemonCursor.getColumnIndex(
                                    PokemonContract.COL_PERSONNAGENONJOUEUR_ID));

                    PersonnageNonJoueurSQLiteAdapter personnageNonJoueurAdapter = new PersonnageNonJoueurSQLiteAdapter(this.ctx);
                    personnageNonJoueurAdapter.open(this.getDb());
                    result = personnageNonJoueurAdapter.query(personnageNonJoueurId);
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
            case POKEMON_ONE:
                selectionArgs = new String[1];
                selection = PokemonContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case POKEMON_ALL:
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
        return POKEMON_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = PokemonContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    PokemonContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

