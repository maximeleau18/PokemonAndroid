/**************************************************************************
 * DresseurProviderAdapterBase.java, pokemon Android
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



import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.provider.ProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;
import com.maximeleau.harmony.android.pokemon.data.DresseurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurSQLiteAdapter;

/**
 * DresseurProviderAdapterBase.
 */
public abstract class DresseurProviderAdapterBase
                extends ProviderAdapter<Dresseur> {

    /** TAG for debug purpose. */
    protected static final String TAG = "DresseurProviderAdapter";

    /** DRESSEUR_URI. */
    public      static Uri DRESSEUR_URI;

    /** dresseur type. */
    protected static final String dresseurType =
            "dresseur";

    /** DRESSEUR_ALL. */
    protected static final int DRESSEUR_ALL =
            650501045;
    /** DRESSEUR_ONE. */
    protected static final int DRESSEUR_ONE =
            650501046;

    /** DRESSEUR_PERSONNAGENONJOUEUR. */
    protected static final int DRESSEUR_PERSONNAGENONJOUEUR =
            650501047;

    /**
     * Static constructor.
     */
    static {
        DRESSEUR_URI =
                PokemonProvider.generateUri(
                        dresseurType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                dresseurType,
                DRESSEUR_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                dresseurType + "/#",
                DRESSEUR_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                dresseurType + "/#" + "/personnagenonjoueur",
                DRESSEUR_PERSONNAGENONJOUEUR);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public DresseurProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new DresseurSQLiteAdapter(provider.getContext()));

        this.uriIds.add(DRESSEUR_ALL);
        this.uriIds.add(DRESSEUR_ONE);
        this.uriIds.add(DRESSEUR_PERSONNAGENONJOUEUR);
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
            case DRESSEUR_ALL:
                result = collection + "dresseur";
                break;
            case DRESSEUR_ONE:
                result = single + "dresseur";
                break;
            case DRESSEUR_PERSONNAGENONJOUEUR:
                result = single + "dresseur";
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
            case DRESSEUR_ONE:
                String id = uri.getPathSegments().get(1);
                selection = DresseurContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case DRESSEUR_ALL:
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
            case DRESSEUR_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(DresseurContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            DRESSEUR_URI,
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
        android.database.Cursor dresseurCursor;

        switch (matchedUri) {

            case DRESSEUR_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case DRESSEUR_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case DRESSEUR_PERSONNAGENONJOUEUR:
                dresseurCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (dresseurCursor.getCount() > 0) {
                    dresseurCursor.moveToFirst();
                    int personnageNonJoueurId = dresseurCursor.getInt(
                            dresseurCursor.getColumnIndex(
                                    DresseurContract.COL_PERSONNAGENONJOUEUR_ID));

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
            case DRESSEUR_ONE:
                selectionArgs = new String[1];
                selection = DresseurContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case DRESSEUR_ALL:
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
        return DRESSEUR_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = DresseurContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    DresseurContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

