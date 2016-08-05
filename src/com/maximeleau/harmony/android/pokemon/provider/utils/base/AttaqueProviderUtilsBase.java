/**************************************************************************
 * AttaqueProviderUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Aug 5, 2016
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

import com.maximeleau.harmony.android.pokemon.entity.Attaque;
import com.maximeleau.harmony.android.pokemon.entity.TypeAttaque;

import com.maximeleau.harmony.android.pokemon.provider.AttaqueProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.TypeAttaqueProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.AttaqueContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeAttaqueContract;

/**
 * Attaque Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class AttaqueProviderUtilsBase
            extends ProviderUtils<Attaque> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "AttaqueProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public AttaqueProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Attaque item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = AttaqueContract.itemToContentValues(item);
        itemValues.remove(AttaqueContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                AttaqueProviderAdapter.ATTAQUE_URI)
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
     * @param item Attaque
     * @return number of row affected
     */
    public int delete(final Attaque item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = AttaqueProviderAdapter.ATTAQUE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Attaque
     */
    public Attaque query(final Attaque item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Attaque
     */
    public Attaque query(final int id) {
        Attaque result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(AttaqueContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            AttaqueProviderAdapter.ATTAQUE_URI,
            AttaqueContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = AttaqueContract.cursorToItem(cursor);

            if (result.getTypeAttaque() != null) {
                result.setTypeAttaque(
                    this.getAssociateTypeAttaque(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Attaque>
     */
    public ArrayList<Attaque> queryAll() {
        ArrayList<Attaque> result =
                    new ArrayList<Attaque>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                AttaqueProviderAdapter.ATTAQUE_URI,
                AttaqueContract.ALIASED_COLS,
                null,
                null,
                null);

        result = AttaqueContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Attaque>
     */
    public ArrayList<Attaque> query(CriteriaExpression expression) {
        ArrayList<Attaque> result =
                    new ArrayList<Attaque>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                AttaqueProviderAdapter.ATTAQUE_URI,
                AttaqueContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = AttaqueContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Attaque
     
     * @return number of rows updated
     */
    public int update(final Attaque item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = AttaqueContract.itemToContentValues(
                item);

        Uri uri = AttaqueProviderAdapter.ATTAQUE_URI;
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
     * Get associate TypeAttaque.
     * @param item Attaque
     * @return TypeAttaque
     */
    public TypeAttaque getAssociateTypeAttaque(
            final Attaque item) {
        TypeAttaque result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor typeAttaqueCursor = prov.query(
                TypeAttaqueProviderAdapter.TYPEATTAQUE_URI,
                TypeAttaqueContract.ALIASED_COLS,
                TypeAttaqueContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getTypeAttaque().getId())},
                null);

        if (typeAttaqueCursor.getCount() > 0) {
            typeAttaqueCursor.moveToFirst();
            result = TypeAttaqueContract.cursorToItem(typeAttaqueCursor);
        } else {
            result = null;
        }
        typeAttaqueCursor.close();

        return result;
    }

}
