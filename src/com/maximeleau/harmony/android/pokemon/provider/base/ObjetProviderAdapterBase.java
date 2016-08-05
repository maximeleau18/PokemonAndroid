/**************************************************************************
 * ObjetProviderAdapterBase.java, pokemon Android
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



import com.maximeleau.harmony.android.pokemon.entity.Objet;
import com.maximeleau.harmony.android.pokemon.provider.ProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.ObjetContract;
import com.maximeleau.harmony.android.pokemon.data.ObjetSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.TypeObjetSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurSQLiteAdapter;

/**
 * ObjetProviderAdapterBase.
 */
public abstract class ObjetProviderAdapterBase
                extends ProviderAdapter<Objet> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ObjetProviderAdapter";

    /** OBJET_URI. */
    public      static Uri OBJET_URI;

    /** objet type. */
    protected static final String objetType =
            "objet";

    /** OBJET_ALL. */
    protected static final int OBJET_ALL =
            75982790;
    /** OBJET_ONE. */
    protected static final int OBJET_ONE =
            75982791;

    /** OBJET_TYPEOBJET. */
    protected static final int OBJET_TYPEOBJET =
            75982792;
    /** OBJET_PERSONNAGENONJOUEUR. */
    protected static final int OBJET_PERSONNAGENONJOUEUR =
            75982793;

    /**
     * Static constructor.
     */
    static {
        OBJET_URI =
                PokemonProvider.generateUri(
                        objetType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                objetType,
                OBJET_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                objetType + "/#",
                OBJET_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                objetType + "/#" + "/typeobjet",
                OBJET_TYPEOBJET);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                objetType + "/#" + "/personnagenonjoueur",
                OBJET_PERSONNAGENONJOUEUR);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public ObjetProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new ObjetSQLiteAdapter(provider.getContext()));

        this.uriIds.add(OBJET_ALL);
        this.uriIds.add(OBJET_ONE);
        this.uriIds.add(OBJET_TYPEOBJET);
        this.uriIds.add(OBJET_PERSONNAGENONJOUEUR);
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
            case OBJET_ALL:
                result = collection + "objet";
                break;
            case OBJET_ONE:
                result = single + "objet";
                break;
            case OBJET_TYPEOBJET:
                result = single + "objet";
                break;
            case OBJET_PERSONNAGENONJOUEUR:
                result = single + "objet";
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
            case OBJET_ONE:
                String id = uri.getPathSegments().get(1);
                selection = ObjetContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case OBJET_ALL:
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
            case OBJET_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(ObjetContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            OBJET_URI,
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
        android.database.Cursor objetCursor;

        switch (matchedUri) {

            case OBJET_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case OBJET_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case OBJET_TYPEOBJET:
                objetCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (objetCursor.getCount() > 0) {
                    objetCursor.moveToFirst();
                    int typeObjetId = objetCursor.getInt(
                            objetCursor.getColumnIndex(
                                    ObjetContract.COL_TYPEOBJET_ID));

                    TypeObjetSQLiteAdapter typeObjetAdapter = new TypeObjetSQLiteAdapter(this.ctx);
                    typeObjetAdapter.open(this.getDb());
                    result = typeObjetAdapter.query(typeObjetId);
                }
                break;

            case OBJET_PERSONNAGENONJOUEUR:
                objetCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (objetCursor.getCount() > 0) {
                    objetCursor.moveToFirst();
                    int personnageNonJoueurId = objetCursor.getInt(
                            objetCursor.getColumnIndex(
                                    ObjetContract.COL_PERSONNAGENONJOUEUR_ID));

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
            case OBJET_ONE:
                selectionArgs = new String[1];
                selection = ObjetContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case OBJET_ALL:
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
        return OBJET_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = ObjetContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    ObjetContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

