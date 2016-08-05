/**************************************************************************
 * PositionProviderUtilsBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.entity.Position;
import com.maximeleau.harmony.android.pokemon.entity.Zone;

import com.maximeleau.harmony.android.pokemon.provider.PositionProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.ZoneProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.PositionContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;

/**
 * Position Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PositionProviderUtilsBase
            extends ProviderUtils<Position> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PositionProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PositionProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Position item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PositionContract.itemToContentValues(item);
        itemValues.remove(PositionContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PositionProviderAdapter.POSITION_URI)
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
     * Insert into DB.
     * @param item Position to insert
     * @param zonepositionsInternalId zonepositionsInternal Id
     * @return number of rows affected
     */
    public Uri insert(final Position item,
                             final int zonepositionsInternalId) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();

        ContentValues itemValues = PositionContract.itemToContentValues(item,
                    zonepositionsInternalId);
        itemValues.remove(PositionContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PositionProviderAdapter.POSITION_URI)
                    .withValues(itemValues)
                    .build());



        try {
            ContentProviderResult[] results =
                prov.applyBatch(PokemonProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setId(Integer.parseInt(result.getLastPathSegment()));
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
     * @param item Position
     * @return number of row affected
     */
    public int delete(final Position item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PositionProviderAdapter.POSITION_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Position
     */
    public Position query(final Position item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Position
     */
    public Position query(final int id) {
        Position result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PositionContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            PositionProviderAdapter.POSITION_URI,
            PositionContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PositionContract.cursorToItem(cursor);

            if (result.getZone() != null) {
                result.setZone(
                    this.getAssociateZone(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Position>
     */
    public ArrayList<Position> queryAll() {
        ArrayList<Position> result =
                    new ArrayList<Position>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PositionProviderAdapter.POSITION_URI,
                PositionContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PositionContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Position>
     */
    public ArrayList<Position> query(CriteriaExpression expression) {
        ArrayList<Position> result =
                    new ArrayList<Position>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PositionProviderAdapter.POSITION_URI,
                PositionContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PositionContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Position
     * @return number of rows updated
     */
    public int update(final Position item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PositionContract.itemToContentValues(item);

        Uri uri = PositionProviderAdapter.POSITION_URI;
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

    /**
     * Updates the DB.
     * @param item Position
     * @param zonepositionsInternalId zonepositionsInternal Id
     * @return number of rows updated
     */
    public int update(final Position item,
                             final int zonepositionsInternalId) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PositionContract.itemToContentValues(
                item,
                zonepositionsInternalId);

        Uri uri = PositionProviderAdapter.POSITION_URI;
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
     * Get associate Zone.
     * @param item Position
     * @return Zone
     */
    public Zone getAssociateZone(
            final Position item) {
        Zone result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor zoneCursor = prov.query(
                ZoneProviderAdapter.ZONE_URI,
                ZoneContract.ALIASED_COLS,
                ZoneContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getZone().getId())},
                null);

        if (zoneCursor.getCount() > 0) {
            zoneCursor.moveToFirst();
            result = ZoneContract.cursorToItem(zoneCursor);
        } else {
            result = null;
        }
        zoneCursor.close();

        return result;
    }

}
