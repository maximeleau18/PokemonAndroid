/**************************************************************************
 * PersonnageNonJoueurBadgeProviderUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.provider.utils.base;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;

import android.content.ContentResolver;
import android.content.ContentValues;

import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;


import com.maximeleau.harmony.android.pokemon.provider.utils.ProviderUtils;
import com.maximeleau.harmony.android.pokemon.criterias.base.CriteriaExpression;
import com.maximeleau.harmony.android.pokemon.criterias.base.CriteriaExpression.GroupType;

import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueurBadge;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.entity.Badge;

import com.maximeleau.harmony.android.pokemon.provider.PersonnageNonJoueurBadgeProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PersonnageNonJoueurProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.BadgeProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurBadgeContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;

/**
 * PersonnageNonJoueurBadge Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PersonnageNonJoueurBadgeProviderUtilsBase
            extends ProviderUtils<PersonnageNonJoueurBadge> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PersonnageNonJoueurBadgeProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PersonnageNonJoueurBadgeProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final PersonnageNonJoueurBadge item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PersonnageNonJoueurBadgeContract.itemToContentValues(item);
        itemValues.remove(PersonnageNonJoueurBadgeContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PersonnageNonJoueurBadgeProviderAdapter.PERSONNAGENONJOUEURBADGE_URI)
                        .withValues(itemValues)
                        .build());


        try {
            ContentProviderResult[] results =
                    prov.applyBatch(PokemonProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setId(Integer.parseInt(result.getPathSegments().get(1)));
            }
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }


    /**
     * Delete from DB.
     * @param item PersonnageNonJoueurBadge
     * @return number of row affected
     */
    public int delete(final PersonnageNonJoueurBadge item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PersonnageNonJoueurBadgeProviderAdapter.PERSONNAGENONJOUEURBADGE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return PersonnageNonJoueurBadge
     */
    public PersonnageNonJoueurBadge query(final PersonnageNonJoueurBadge item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return PersonnageNonJoueurBadge
     */
    public PersonnageNonJoueurBadge query(final int id) {
        PersonnageNonJoueurBadge result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PersonnageNonJoueurBadgeContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            PersonnageNonJoueurBadgeProviderAdapter.PERSONNAGENONJOUEURBADGE_URI,
            PersonnageNonJoueurBadgeContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PersonnageNonJoueurBadgeContract.cursorToItem(cursor);

            if (result.getPersonnageNonJoueur() != null) {
                result.setPersonnageNonJoueur(
                    this.getAssociatePersonnageNonJoueur(result));
            }
            if (result.getBadge() != null) {
                result.setBadge(
                    this.getAssociateBadge(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<PersonnageNonJoueurBadge>
     */
    public ArrayList<PersonnageNonJoueurBadge> queryAll() {
        ArrayList<PersonnageNonJoueurBadge> result =
                    new ArrayList<PersonnageNonJoueurBadge>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PersonnageNonJoueurBadgeProviderAdapter.PERSONNAGENONJOUEURBADGE_URI,
                PersonnageNonJoueurBadgeContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PersonnageNonJoueurBadgeContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<PersonnageNonJoueurBadge>
     */
    public ArrayList<PersonnageNonJoueurBadge> query(CriteriaExpression expression) {
        ArrayList<PersonnageNonJoueurBadge> result =
                    new ArrayList<PersonnageNonJoueurBadge>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PersonnageNonJoueurBadgeProviderAdapter.PERSONNAGENONJOUEURBADGE_URI,
                PersonnageNonJoueurBadgeContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PersonnageNonJoueurBadgeContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item PersonnageNonJoueurBadge
     
     * @return number of rows updated
     */
    public int update(final PersonnageNonJoueurBadge item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PersonnageNonJoueurBadgeContract.itemToContentValues(
                item);

        Uri uri = PersonnageNonJoueurBadgeProviderAdapter.PERSONNAGENONJOUEURBADGE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());



        try {
            ContentProviderResult[] results = prov.applyBatch(PokemonProvider.authority, operations);
            result = results[0].count;
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /** Relations operations. */
    /**
     * Get associate PersonnageNonJoueur.
     * @param item PersonnageNonJoueurBadge
     * @return PersonnageNonJoueur
     */
    public PersonnageNonJoueur getAssociatePersonnageNonJoueur(
            final PersonnageNonJoueurBadge item) {
        PersonnageNonJoueur result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor personnageNonJoueurCursor = prov.query(
                PersonnageNonJoueurProviderAdapter.PERSONNAGENONJOUEUR_URI,
                PersonnageNonJoueurContract.ALIASED_COLS,
                PersonnageNonJoueurContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getPersonnageNonJoueur().getId())},
                null);

        if (personnageNonJoueurCursor.getCount() > 0) {
            personnageNonJoueurCursor.moveToFirst();
            result = PersonnageNonJoueurContract.cursorToItem(personnageNonJoueurCursor);
        } else {
            result = null;
        }
        personnageNonJoueurCursor.close();

        return result;
    }

    /**
     * Get associate Badge.
     * @param item PersonnageNonJoueurBadge
     * @return Badge
     */
    public Badge getAssociateBadge(
            final PersonnageNonJoueurBadge item) {
        Badge result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor badgeCursor = prov.query(
                BadgeProviderAdapter.BADGE_URI,
                BadgeContract.ALIASED_COLS,
                BadgeContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getBadge().getId())},
                null);

        if (badgeCursor.getCount() > 0) {
            badgeCursor.moveToFirst();
            result = BadgeContract.cursorToItem(badgeCursor);
        } else {
            result = null;
        }
        badgeCursor.close();

        return result;
    }

}
