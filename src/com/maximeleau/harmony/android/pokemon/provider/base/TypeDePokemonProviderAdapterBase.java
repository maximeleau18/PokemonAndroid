/**************************************************************************
 * TypeDePokemonProviderAdapterBase.java, pokemon Android
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



import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;
import com.maximeleau.harmony.android.pokemon.provider.ProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;
import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PokemonSQLiteAdapter;

/**
 * TypeDePokemonProviderAdapterBase.
 */
public abstract class TypeDePokemonProviderAdapterBase
                extends ProviderAdapter<TypeDePokemon> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypeDePokemonProviderAdapter";

    /** TYPEDEPOKEMON_URI. */
    public      static Uri TYPEDEPOKEMON_URI;

    /** typeDePokemon type. */
    protected static final String typeDePokemonType =
            "typedepokemon";

    /** TYPEDEPOKEMON_ALL. */
    protected static final int TYPEDEPOKEMON_ALL =
            342959832;
    /** TYPEDEPOKEMON_ONE. */
    protected static final int TYPEDEPOKEMON_ONE =
            342959833;

    /** TYPEDEPOKEMON_POKEMONS. */
    protected static final int TYPEDEPOKEMON_POKEMONS =
            342959834;

    /**
     * Static constructor.
     */
    static {
        TYPEDEPOKEMON_URI =
                PokemonProvider.generateUri(
                        typeDePokemonType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonType,
                TYPEDEPOKEMON_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonType + "/#",
                TYPEDEPOKEMON_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonType + "/#" + "/pokemons",
                TYPEDEPOKEMON_POKEMONS);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public TypeDePokemonProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new TypeDePokemonSQLiteAdapter(provider.getContext()));

        this.uriIds.add(TYPEDEPOKEMON_ALL);
        this.uriIds.add(TYPEDEPOKEMON_ONE);
        this.uriIds.add(TYPEDEPOKEMON_POKEMONS);
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
            case TYPEDEPOKEMON_ALL:
                result = collection + "typedepokemon";
                break;
            case TYPEDEPOKEMON_ONE:
                result = single + "typedepokemon";
                break;
            case TYPEDEPOKEMON_POKEMONS:
                result = collection + "typedepokemon";
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
            case TYPEDEPOKEMON_ONE:
                String id = uri.getPathSegments().get(1);
                selection = TypeDePokemonContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case TYPEDEPOKEMON_ALL:
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
            case TYPEDEPOKEMON_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(TypeDePokemonContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            TYPEDEPOKEMON_URI,
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
        int typedepokemonId;

        switch (matchedUri) {

            case TYPEDEPOKEMON_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case TYPEDEPOKEMON_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case TYPEDEPOKEMON_POKEMONS:
                typedepokemonId = Integer.parseInt(uri.getPathSegments().get(1));
                PokemonSQLiteAdapter pokemonsAdapter = new PokemonSQLiteAdapter(this.ctx);
                pokemonsAdapter.open(this.getDb());
                result = pokemonsAdapter.getByTypeDePokemon(typedepokemonId, PokemonContract.ALIASED_COLS, selection, selectionArgs, null);
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
            case TYPEDEPOKEMON_ONE:
                selectionArgs = new String[1];
                selection = TypeDePokemonContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case TYPEDEPOKEMON_ALL:
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
        return TYPEDEPOKEMON_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = TypeDePokemonContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    TypeDePokemonContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

