/**************************************************************************
 * PersonnageNonJoueurProviderAdapterBase.java, pokemon Android
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



import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.provider.ProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ObjetContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;
import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.ProfessionSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.ObjetSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.DresseurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.AreneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PokemonSQLiteAdapter;

/**
 * PersonnageNonJoueurProviderAdapterBase.
 */
public abstract class PersonnageNonJoueurProviderAdapterBase
                extends ProviderAdapter<PersonnageNonJoueur> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PersonnageNonJoueurProviderAdapter";

    /** PERSONNAGENONJOUEUR_URI. */
    public      static Uri PERSONNAGENONJOUEUR_URI;

    /** personnageNonJoueur type. */
    protected static final String personnageNonJoueurType =
            "personnagenonjoueur";

    /** PERSONNAGENONJOUEUR_ALL. */
    protected static final int PERSONNAGENONJOUEUR_ALL =
            1023755513;
    /** PERSONNAGENONJOUEUR_ONE. */
    protected static final int PERSONNAGENONJOUEUR_ONE =
            1023755514;

    /** PERSONNAGENONJOUEUR_PROFESSION. */
    protected static final int PERSONNAGENONJOUEUR_PROFESSION =
            1023755515;
    /** PERSONNAGENONJOUEUR_OBJETS. */
    protected static final int PERSONNAGENONJOUEUR_OBJETS =
            1023755516;
    /** PERSONNAGENONJOUEUR_DRESSEURS. */
    protected static final int PERSONNAGENONJOUEUR_DRESSEURS =
            1023755517;
    /** PERSONNAGENONJOUEUR_ARENES. */
    protected static final int PERSONNAGENONJOUEUR_ARENES =
            1023755518;
    /** PERSONNAGENONJOUEUR_POKEMONS. */
    protected static final int PERSONNAGENONJOUEUR_POKEMONS =
            1023755519;

    /**
     * Static constructor.
     */
    static {
        PERSONNAGENONJOUEUR_URI =
                PokemonProvider.generateUri(
                        personnageNonJoueurType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                personnageNonJoueurType,
                PERSONNAGENONJOUEUR_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                personnageNonJoueurType + "/#",
                PERSONNAGENONJOUEUR_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                personnageNonJoueurType + "/#" + "/profession",
                PERSONNAGENONJOUEUR_PROFESSION);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                personnageNonJoueurType + "/#" + "/objets",
                PERSONNAGENONJOUEUR_OBJETS);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                personnageNonJoueurType + "/#" + "/dresseurs",
                PERSONNAGENONJOUEUR_DRESSEURS);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                personnageNonJoueurType + "/#" + "/arenes",
                PERSONNAGENONJOUEUR_ARENES);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                personnageNonJoueurType + "/#" + "/pokemons",
                PERSONNAGENONJOUEUR_POKEMONS);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PersonnageNonJoueurProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new PersonnageNonJoueurSQLiteAdapter(provider.getContext()));

        this.uriIds.add(PERSONNAGENONJOUEUR_ALL);
        this.uriIds.add(PERSONNAGENONJOUEUR_ONE);
        this.uriIds.add(PERSONNAGENONJOUEUR_PROFESSION);
        this.uriIds.add(PERSONNAGENONJOUEUR_OBJETS);
        this.uriIds.add(PERSONNAGENONJOUEUR_DRESSEURS);
        this.uriIds.add(PERSONNAGENONJOUEUR_ARENES);
        this.uriIds.add(PERSONNAGENONJOUEUR_POKEMONS);
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
            case PERSONNAGENONJOUEUR_ALL:
                result = collection + "personnagenonjoueur";
                break;
            case PERSONNAGENONJOUEUR_ONE:
                result = single + "personnagenonjoueur";
                break;
            case PERSONNAGENONJOUEUR_PROFESSION:
                result = single + "personnagenonjoueur";
                break;
            case PERSONNAGENONJOUEUR_OBJETS:
                result = collection + "personnagenonjoueur";
                break;
            case PERSONNAGENONJOUEUR_DRESSEURS:
                result = collection + "personnagenonjoueur";
                break;
            case PERSONNAGENONJOUEUR_ARENES:
                result = collection + "personnagenonjoueur";
                break;
            case PERSONNAGENONJOUEUR_POKEMONS:
                result = collection + "personnagenonjoueur";
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
            case PERSONNAGENONJOUEUR_ONE:
                String id = uri.getPathSegments().get(1);
                selection = PersonnageNonJoueurContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case PERSONNAGENONJOUEUR_ALL:
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
            case PERSONNAGENONJOUEUR_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PersonnageNonJoueurContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            PERSONNAGENONJOUEUR_URI,
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
        android.database.Cursor personnageNonJoueurCursor;
        int personnagenonjoueurId;

        switch (matchedUri) {

            case PERSONNAGENONJOUEUR_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case PERSONNAGENONJOUEUR_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case PERSONNAGENONJOUEUR_PROFESSION:
                personnageNonJoueurCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (personnageNonJoueurCursor.getCount() > 0) {
                    personnageNonJoueurCursor.moveToFirst();
                    int professionId = personnageNonJoueurCursor.getInt(
                            personnageNonJoueurCursor.getColumnIndex(
                                    PersonnageNonJoueurContract.COL_PROFESSION_ID));

                    ProfessionSQLiteAdapter professionAdapter = new ProfessionSQLiteAdapter(this.ctx);
                    professionAdapter.open(this.getDb());
                    result = professionAdapter.query(professionId);
                }
                break;

            case PERSONNAGENONJOUEUR_OBJETS:
                personnagenonjoueurId = Integer.parseInt(uri.getPathSegments().get(1));
                ObjetSQLiteAdapter objetsAdapter = new ObjetSQLiteAdapter(this.ctx);
                objetsAdapter.open(this.getDb());
                result = objetsAdapter.getByPersonnageNonJoueur(personnagenonjoueurId, ObjetContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case PERSONNAGENONJOUEUR_DRESSEURS:
                personnagenonjoueurId = Integer.parseInt(uri.getPathSegments().get(1));
                DresseurSQLiteAdapter dresseursAdapter = new DresseurSQLiteAdapter(this.ctx);
                dresseursAdapter.open(this.getDb());
                result = dresseursAdapter.getByPersonnageNonJoueur(personnagenonjoueurId, DresseurContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case PERSONNAGENONJOUEUR_ARENES:
                personnagenonjoueurId = Integer.parseInt(uri.getPathSegments().get(1));
                AreneSQLiteAdapter arenesAdapter = new AreneSQLiteAdapter(this.ctx);
                arenesAdapter.open(this.getDb());
                result = arenesAdapter.getByMaitre(personnagenonjoueurId, AreneContract.ALIASED_COLS, selection, selectionArgs, null);
                break;

            case PERSONNAGENONJOUEUR_POKEMONS:
                personnagenonjoueurId = Integer.parseInt(uri.getPathSegments().get(1));
                PokemonSQLiteAdapter pokemonsAdapter = new PokemonSQLiteAdapter(this.ctx);
                pokemonsAdapter.open(this.getDb());
                result = pokemonsAdapter.getByPersonnageNonJoueur(personnagenonjoueurId, PokemonContract.ALIASED_COLS, selection, selectionArgs, null);
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
            case PERSONNAGENONJOUEUR_ONE:
                selectionArgs = new String[1];
                selection = PersonnageNonJoueurContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case PERSONNAGENONJOUEUR_ALL:
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
        return PERSONNAGENONJOUEUR_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = PersonnageNonJoueurContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    PersonnageNonJoueurContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

