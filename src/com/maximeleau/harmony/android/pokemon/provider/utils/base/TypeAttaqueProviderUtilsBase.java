/**************************************************************************
 * TypeAttaqueProviderUtilsBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.entity.TypeAttaque;

import com.maximeleau.harmony.android.pokemon.provider.TypeAttaqueProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeAttaqueContract;

/**
 * TypeAttaque Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class TypeAttaqueProviderUtilsBase
            extends ProviderUtils<TypeAttaque> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "TypeAttaqueProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public TypeAttaqueProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final TypeAttaque item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = TypeAttaqueContract.itemToContentValues(item);
        itemValues.remove(TypeAttaqueContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                TypeAttaqueProviderAdapter.TYPEATTAQUE_URI)
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
     * @param item TypeAttaque
     * @return number of row affected
     */
    public int delete(final TypeAttaque item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = TypeAttaqueProviderAdapter.TYPEATTAQUE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return TypeAttaque
     */
    public TypeAttaque query(final TypeAttaque item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return TypeAttaque
     */
    public TypeAttaque query(final int id) {
        TypeAttaque result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(TypeAttaqueContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            TypeAttaqueProviderAdapter.TYPEATTAQUE_URI,
            TypeAttaqueContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = TypeAttaqueContract.cursorToItem(cursor);

        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<TypeAttaque>
     */
    public ArrayList<TypeAttaque> queryAll() {
        ArrayList<TypeAttaque> result =
                    new ArrayList<TypeAttaque>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                TypeAttaqueProviderAdapter.TYPEATTAQUE_URI,
                TypeAttaqueContract.ALIASED_COLS,
                null,
                null,
                null);

        result = TypeAttaqueContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<TypeAttaque>
     */
    public ArrayList<TypeAttaque> query(CriteriaExpression expression) {
        ArrayList<TypeAttaque> result =
                    new ArrayList<TypeAttaque>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                TypeAttaqueProviderAdapter.TYPEATTAQUE_URI,
                TypeAttaqueContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = TypeAttaqueContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item TypeAttaque
     
     * @return number of rows updated
     */
    public int update(final TypeAttaque item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = TypeAttaqueContract.itemToContentValues(
                item);

        Uri uri = TypeAttaqueProviderAdapter.TYPEATTAQUE_URI;
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

    
}
