/**************************************************************************
 * PersonnageNonJoueurBadgeProviderAdapterBase.java, pokemon Android
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



import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueurBadge;
import com.maximeleau.harmony.android.pokemon.provider.ProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurBadgeContract;
import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurBadgeSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.BadgeSQLiteAdapter;

/**
 * PersonnageNonJoueurBadgeProviderAdapterBase.
 */
public abstract class PersonnageNonJoueurBadgeProviderAdapterBase
                extends ProviderAdapter<PersonnageNonJoueurBadge> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PersonnageNonJoueurBadgeProviderAdapter";

    /** PERSONNAGENONJOUEURBADGE_URI. */
    public      static Uri PERSONNAGENONJOUEURBADGE_URI;

    /** personnageNonJoueurBadge type. */
    protected static final String personnageNonJoueurBadgeType =
            "personnagenonjoueurbadge";

    /** PERSONNAGENONJOUEURBADGE_ALL. */
    protected static final int PERSONNAGENONJOUEURBADGE_ALL =
            732194262;
    /** PERSONNAGENONJOUEURBADGE_ONE. */
    protected static final int PERSONNAGENONJOUEURBADGE_ONE =
            732194263;

    /** PERSONNAGENONJOUEURBADGE_PERSONNAGENONJOUEUR. */
    protected static final int PERSONNAGENONJOUEURBADGE_PERSONNAGENONJOUEUR =
            732194264;
    /** PERSONNAGENONJOUEURBADGE_BADGE. */
    protected static final int PERSONNAGENONJOUEURBADGE_BADGE =
            732194265;

    /**
     * Static constructor.
     */
    static {
        PERSONNAGENONJOUEURBADGE_URI =
                PokemonProvider.generateUri(
                        personnageNonJoueurBadgeType);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                personnageNonJoueurBadgeType,
                PERSONNAGENONJOUEURBADGE_ALL);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                personnageNonJoueurBadgeType + "/#",
                PERSONNAGENONJOUEURBADGE_ONE);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                personnageNonJoueurBadgeType + "/#" + "/personnagenonjoueur",
                PERSONNAGENONJOUEURBADGE_PERSONNAGENONJOUEUR);
        PokemonProvider.getUriMatcher().addURI(
                PokemonProvider.authority,
                personnageNonJoueurBadgeType + "/#" + "/badge",
                PERSONNAGENONJOUEURBADGE_BADGE);
    }

    /**
     * Constructor.
     * @param ctx context
     * @param db database
     */
    public PersonnageNonJoueurBadgeProviderAdapterBase(
            PokemonProviderBase provider) {
        super(
            provider,
            new PersonnageNonJoueurBadgeSQLiteAdapter(provider.getContext()));

        this.uriIds.add(PERSONNAGENONJOUEURBADGE_ALL);
        this.uriIds.add(PERSONNAGENONJOUEURBADGE_ONE);
        this.uriIds.add(PERSONNAGENONJOUEURBADGE_PERSONNAGENONJOUEUR);
        this.uriIds.add(PERSONNAGENONJOUEURBADGE_BADGE);
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
            case PERSONNAGENONJOUEURBADGE_ALL:
                result = collection + "personnagenonjoueurbadge";
                break;
            case PERSONNAGENONJOUEURBADGE_ONE:
                result = single + "personnagenonjoueurbadge";
                break;
            case PERSONNAGENONJOUEURBADGE_PERSONNAGENONJOUEUR:
                result = single + "personnagenonjoueurbadge";
                break;
            case PERSONNAGENONJOUEURBADGE_BADGE:
                result = single + "personnagenonjoueurbadge";
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
            case PERSONNAGENONJOUEURBADGE_ONE:
                String id = uri.getPathSegments().get(1);
                selection = PersonnageNonJoueurBadgeContract.COL_ID
                        + " = ?";
                selectionArgs = new String[1];
                selectionArgs[0] = id;
                result = this.adapter.delete(
                        selection,
                        selectionArgs);
                break;
            case PERSONNAGENONJOUEURBADGE_ALL:
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
            case PERSONNAGENONJOUEURBADGE_ALL:
                if (values.size() > 0) {
                    id = (int) this.adapter.insert(null, values);
                } else {
                    id = (int) this.adapter.insert(PersonnageNonJoueurBadgeContract.COL_ID, values);
                }
                if (id > 0) {
                    result = Uri.withAppendedPath(
                            PERSONNAGENONJOUEURBADGE_URI,
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
        android.database.Cursor personnageNonJoueurBadgeCursor;

        switch (matchedUri) {

            case PERSONNAGENONJOUEURBADGE_ALL:
                result = this.adapter.query(
                            projection,
                            selection,
                            selectionArgs,
                            null,
                            null,
                            sortOrder);
                break;
            case PERSONNAGENONJOUEURBADGE_ONE:
                result = this.queryById(uri.getPathSegments().get(1));
                break;

            case PERSONNAGENONJOUEURBADGE_PERSONNAGENONJOUEUR:
                personnageNonJoueurBadgeCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (personnageNonJoueurBadgeCursor.getCount() > 0) {
                    personnageNonJoueurBadgeCursor.moveToFirst();
                    int personnageNonJoueurId = personnageNonJoueurBadgeCursor.getInt(
                            personnageNonJoueurBadgeCursor.getColumnIndex(
                                    PersonnageNonJoueurBadgeContract.COL_PERSONNAGENONJOUEUR_ID));

                    PersonnageNonJoueurSQLiteAdapter personnageNonJoueurAdapter = new PersonnageNonJoueurSQLiteAdapter(this.ctx);
                    personnageNonJoueurAdapter.open(this.getDb());
                    result = personnageNonJoueurAdapter.query(personnageNonJoueurId);
                }
                break;

            case PERSONNAGENONJOUEURBADGE_BADGE:
                personnageNonJoueurBadgeCursor = this.queryById(
                        uri.getPathSegments().get(1));

                if (personnageNonJoueurBadgeCursor.getCount() > 0) {
                    personnageNonJoueurBadgeCursor.moveToFirst();
                    int badgeId = personnageNonJoueurBadgeCursor.getInt(
                            personnageNonJoueurBadgeCursor.getColumnIndex(
                                    PersonnageNonJoueurBadgeContract.COL_BADGE_ID));

                    BadgeSQLiteAdapter badgeAdapter = new BadgeSQLiteAdapter(this.ctx);
                    badgeAdapter.open(this.getDb());
                    result = badgeAdapter.query(badgeId);
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
            case PERSONNAGENONJOUEURBADGE_ONE:
                selectionArgs = new String[1];
                selection = PersonnageNonJoueurBadgeContract.COL_ID + " = ?";
                selectionArgs[0] = uri.getPathSegments().get(1);
                result = this.adapter.update(
                        values,
                        selection,
                        selectionArgs);
                break;
            case PERSONNAGENONJOUEURBADGE_ALL:
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
        return PERSONNAGENONJOUEURBADGE_URI;
    }

    /**
     * Query by ID.
     *
     * @param id The id of the entity to retrieve
     * @return The cursor
     */
    private android.database.Cursor queryById(String id) {
        android.database.Cursor result = null;
        String selection = PersonnageNonJoueurBadgeContract.ALIASED_COL_ID
                        + " = ?";

        String[] selectionArgs = new String[1];
        selectionArgs[0] = id;
        
        

        result = this.adapter.query(
                    PersonnageNonJoueurBadgeContract.ALIASED_COLS,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);
        return result;
    }
}

