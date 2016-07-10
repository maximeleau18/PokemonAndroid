/**************************************************************************
 * TypeDePokemonEvolutionProviderAdapterBase.java, pokemon Android
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



import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonEvolution;
import com.maximeleau.harmony.android.pokemon.provider.ProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonEvolutionContract;
import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonEvolutionSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonSQLiteAdapter;

/**
 * TypeDePokemonEvolutionProviderAdapterBase.
 */
public abstract class TypeDePokemonEvolutionProviderAdapterBase
                extends ProviderAdapter<TypeDePokemonEvolution> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypeDePokemonEvolutionProviderAdapter";

    /** TYPEDEPOKEMONEVOLUTION_URI. */
    public      static Uri TYPEDEPOKEMONEVOLUTION_URI;

    /** typeDePokemonEvolution type. */
    protected static final String typeDePokemonEvolutionType =
            "typedepokemonevolution";

    /** TYPEDEPOKEMONEVOLUTION_ALL. */
    protected static final int TYPEDEPOKEMONEVOLUTION_ALL =
            428618877;
    /** TYPEDEPOKEMONEVOLUTION_ONE. */
    protected static final int TYPEDEPOKEMONEVOLUTION_ONE =
            428618878;

    /** TYPEDEPOKEMONEVOLUTION_EVOLUEEN. */
    protected static final int TYPEDEPOKEMONEVOLUTION_EVOLUEEN =
            428618879;
    /** TYPEDEPOKEMONEVOLUTION_ESTEVOLUEEN. */
    protected static final int TYPEDEPOKEMONEVOLUTION_ESTEVOLUEEN =
            428618880;

    /**
     * Static constructor.
     */
    static {
        TYPEDEPOKEMONEVOLUTION_URI =
                PokemonProvider.generateUri(
                        typeDePokemonEvolutionType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonEvolutionType,
                TYPEDEPOKEMONEVOLUTION_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonEvolutionType + "/#",
                TYPEDEPOKEMONEVOLUTION_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonEvolutionType + "/#" + "/evolueen",
                TYPEDEPOKEMONEVOLUTION_EVOLUEEN);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                typeDePokemonEvolutionType + "/#" + "/estevolueen",
                TYPEDEPOKEMONEVOLUTION_ESTEVOLUEEN);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public TypeDePokemonEvolutionProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new TypeDePokemonEvolutionSQLiteAdapter(provider.getContext()));

        this.uriIds.add(TYPEDEPOKEMONEVOLUTION_ALL);
        this.uriIds.add(TYPEDEPOKEMONEVOLUTION_ONE);
        this.uriIds.add(TYPEDEPOKEMONEVOLUTION_EVOLUEEN);
        this.uriIds.add(TYPEDEPOKEMONEVOLUTION_ESTEVOLUEEN);
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
            case TYPEDEPOKEMONEVOLUTION_ALL:
                result = collection + "typedepokemonevolution";
                break;
            case TYPEDEPOKEMONEVOLUTION_ONE:
                result = single + "typedepokemonevolution";
                break;
            case TYPEDEPOKEMONEVOLUTION_EVOLUEEN:
                result = single + "typedepokemonevolution";
                break;
            case TYPEDEPOKEMONEVOLUTION_ESTEVOLUEEN:
                result = single + "typedepokemonevolution";
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
            case TYPEDEPOKEMONEVOLUTION_ONE:
                String id = uri.getPathSegments().get(1);
                selection = TypeDePokemonEvolutionContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case TYPEDEPOKEMONEVOLUTION_ALL:
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
            case TYPEDEPOKEMONEVOLUTION_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(TypeDePokemonEvolutionContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            TYPEDEPOKEMONEVOLUTION_URI,
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
        android.database.Cursor typeDePokemonEvolutionCursor;

        switch (matchedUri) {

            case TYPEDEPOKEMONEVOLUTION_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case TYPEDEPOKEMONEVOLUTION_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case TYPEDEPOKEMONEVOLUTION_EVOLUEEN:
                typeDePokemonEvolutionCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (typeDePokemonEvolutionCursor.getCount() > 0) {
                    typeDePokemonEvolutionCursor.moveToFirst();
                    int evolueEnId = typeDePokemonEvolutionCursor.getInt(
                            typeDePokemonEvolutionCursor.getColumnIndex(
                                    TypeDePokemonEvolutionContract.COL_EVOLUEEN_ID));

                    TypeDePokemonSQLiteAdapter typeDePokemonAdapter = new TypeDePokemonSQLiteAdapter(this.ctx);
                    typeDePokemonAdapter.open(this.getDb());
                    result = typeDePokemonAdapter.query(evolueEnId);
                }
                break;

            case TYPEDEPOKEMONEVOLUTION_ESTEVOLUEEN:
                typeDePokemonEvolutionCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (typeDePokemonEvolutionCursor.getCount() > 0) {
                    typeDePokemonEvolutionCursor.moveToFirst();
                    int estEvolueEnId = typeDePokemonEvolutionCursor.getInt(
                            typeDePokemonEvolutionCursor.getColumnIndex(
                                    TypeDePokemonEvolutionContract.COL_ESTEVOLUEEN_ID));

                    TypeDePokemonSQLiteAdapter typeDePokemonAdapter = new TypeDePokemonSQLiteAdapter(this.ctx);
                    typeDePokemonAdapter.open(this.getDb());
                    result = typeDePokemonAdapter.query(estEvolueEnId);
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
            case TYPEDEPOKEMONEVOLUTION_ONE:
                selectionArgs = new String[1];
                selection = TypeDePokemonEvolutionContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case TYPEDEPOKEMONEVOLUTION_ALL:
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
        return TYPEDEPOKEMONEVOLUTION_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = TypeDePokemonEvolutionContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    TypeDePokemonEvolutionContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

