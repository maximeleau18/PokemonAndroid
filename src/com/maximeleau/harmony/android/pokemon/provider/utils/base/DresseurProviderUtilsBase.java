/**************************************************************************
 * DresseurProviderUtilsBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;

import com.maximeleau.harmony.android.pokemon.provider.DresseurProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PersonnageNonJoueurProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;

/**
 * Dresseur Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class DresseurProviderUtilsBase
            extends ProviderUtils<Dresseur> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "DresseurProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public DresseurProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Dresseur item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = DresseurContract.itemToContentValues(item);
        itemValues.remove(DresseurContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                DresseurProviderAdapter.DRESSEUR_URI)
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
     * @param item Dresseur
     * @return number of row affected
     */
    public int delete(final Dresseur item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = DresseurProviderAdapter.DRESSEUR_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Dresseur
     */
    public Dresseur query(final Dresseur item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Dresseur
     */
    public Dresseur query(final int id) {
        Dresseur result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(DresseurContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            DresseurProviderAdapter.DRESSEUR_URI,
            DresseurContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = DresseurContract.cursorToItem(cursor);

            if (result.getPersonnageNonJoueur() != null) {
                result.setPersonnageNonJoueur(
                    this.getAssociatePersonnageNonJoueur(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Dresseur>
     */
    public ArrayList<Dresseur> queryAll() {
        ArrayList<Dresseur> result =
                    new ArrayList<Dresseur>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                DresseurProviderAdapter.DRESSEUR_URI,
                DresseurContract.ALIASED_COLS,
                null,
                null,
                null);

        result = DresseurContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Dresseur>
     */
    public ArrayList<Dresseur> query(CriteriaExpression expression) {
        ArrayList<Dresseur> result =
                    new ArrayList<Dresseur>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                DresseurProviderAdapter.DRESSEUR_URI,
                DresseurContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = DresseurContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Dresseur
     
     * @return number of rows updated
     */
    public int update(final Dresseur item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = DresseurContract.itemToContentValues(
                item);

        Uri uri = DresseurProviderAdapter.DRESSEUR_URI;
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
     * @param item Dresseur
     * @return PersonnageNonJoueur
     */
    public PersonnageNonJoueur getAssociatePersonnageNonJoueur(
            final Dresseur item) {
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

}
