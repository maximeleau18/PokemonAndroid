/**************************************************************************
 * BadgeProviderUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 18, 2016
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

import com.maximeleau.harmony.android.pokemon.entity.Badge;

import com.maximeleau.harmony.android.pokemon.provider.BadgeProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;

/**
 * Badge Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class BadgeProviderUtilsBase
            extends ProviderUtils<Badge> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "BadgeProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public BadgeProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Badge item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = BadgeContract.itemToContentValues(item);
        itemValues.remove(BadgeContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                BadgeProviderAdapter.BADGE_URI)
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
     * @param item Badge to insert
     * @param zonebadgesInternalId zonebadgesInternal Id
     * @return number of rows affected
     */
    public Uri insert(final Badge item,
                             final int zonebadgesInternalId) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();

        ContentValues itemValues = BadgeContract.itemToContentValues(item,
                    zonebadgesInternalId);
        itemValues.remove(BadgeContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                BadgeProviderAdapter.BADGE_URI)
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
     * @param item Badge
     * @return number of row affected
     */
    public int delete(final Badge item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = BadgeProviderAdapter.BADGE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Badge
     */
    public Badge query(final Badge item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Badge
     */
    public Badge query(final int id) {
        Badge result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(BadgeContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            BadgeProviderAdapter.BADGE_URI,
            BadgeContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = BadgeContract.cursorToItem(cursor);

        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Badge>
     */
    public ArrayList<Badge> queryAll() {
        ArrayList<Badge> result =
                    new ArrayList<Badge>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                BadgeProviderAdapter.BADGE_URI,
                BadgeContract.ALIASED_COLS,
                null,
                null,
                null);

        result = BadgeContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Badge>
     */
    public ArrayList<Badge> query(CriteriaExpression expression) {
        ArrayList<Badge> result =
                    new ArrayList<Badge>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                BadgeProviderAdapter.BADGE_URI,
                BadgeContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = BadgeContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Badge
     * @return number of rows updated
     */
    public int update(final Badge item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = BadgeContract.itemToContentValues(item);

        Uri uri = BadgeProviderAdapter.BADGE_URI;
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
     * @param item Badge
     * @param zonebadgesInternalId zonebadgesInternal Id
     * @return number of rows updated
     */
    public int update(final Badge item,
                             final int zonebadgesInternalId) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = BadgeContract.itemToContentValues(
                item,
                zonebadgesInternalId);

        Uri uri = BadgeProviderAdapter.BADGE_URI;
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
}
