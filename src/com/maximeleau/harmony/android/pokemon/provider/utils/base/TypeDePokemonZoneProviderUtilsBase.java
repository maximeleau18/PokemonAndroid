/**************************************************************************
 * TypeDePokemonZoneProviderUtilsBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonZone;
import com.maximeleau.harmony.android.pokemon.entity.Zone;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;

import com.maximeleau.harmony.android.pokemon.provider.TypeDePokemonZoneProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.ZoneProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.TypeDePokemonProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonZoneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;

/**
 * TypeDePokemonZone Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class TypeDePokemonZoneProviderUtilsBase
            extends ProviderUtils<TypeDePokemonZone> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "TypeDePokemonZoneProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public TypeDePokemonZoneProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final TypeDePokemonZone item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = TypeDePokemonZoneContract.itemToContentValues(item);
        itemValues.remove(TypeDePokemonZoneContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                TypeDePokemonZoneProviderAdapter.TYPEDEPOKEMONZONE_URI)
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
     * @param item TypeDePokemonZone
     * @return number of row affected
     */
    public int delete(final TypeDePokemonZone item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = TypeDePokemonZoneProviderAdapter.TYPEDEPOKEMONZONE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return TypeDePokemonZone
     */
    public TypeDePokemonZone query(final TypeDePokemonZone item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return TypeDePokemonZone
     */
    public TypeDePokemonZone query(final int id) {
        TypeDePokemonZone result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(TypeDePokemonZoneContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            TypeDePokemonZoneProviderAdapter.TYPEDEPOKEMONZONE_URI,
            TypeDePokemonZoneContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = TypeDePokemonZoneContract.cursorToItem(cursor);

            if (result.getZone() != null) {
                result.setZone(
                    this.getAssociateZone(result));
            }
            if (result.getTypeDePokemon() != null) {
                result.setTypeDePokemon(
                    this.getAssociateTypeDePokemon(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<TypeDePokemonZone>
     */
    public ArrayList<TypeDePokemonZone> queryAll() {
        ArrayList<TypeDePokemonZone> result =
                    new ArrayList<TypeDePokemonZone>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                TypeDePokemonZoneProviderAdapter.TYPEDEPOKEMONZONE_URI,
                TypeDePokemonZoneContract.ALIASED_COLS,
                null,
                null,
                null);

        result = TypeDePokemonZoneContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<TypeDePokemonZone>
     */
    public ArrayList<TypeDePokemonZone> query(CriteriaExpression expression) {
        ArrayList<TypeDePokemonZone> result =
                    new ArrayList<TypeDePokemonZone>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                TypeDePokemonZoneProviderAdapter.TYPEDEPOKEMONZONE_URI,
                TypeDePokemonZoneContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = TypeDePokemonZoneContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item TypeDePokemonZone
     
     * @return number of rows updated
     */
    public int update(final TypeDePokemonZone item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = TypeDePokemonZoneContract.itemToContentValues(
                item);

        Uri uri = TypeDePokemonZoneProviderAdapter.TYPEDEPOKEMONZONE_URI;
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
     * @param item TypeDePokemonZone
     * @return Zone
     */
    public Zone getAssociateZone(
            final TypeDePokemonZone item) {
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

    /**
     * Get associate TypeDePokemon.
     * @param item TypeDePokemonZone
     * @return TypeDePokemon
     */
    public TypeDePokemon getAssociateTypeDePokemon(
            final TypeDePokemonZone item) {
        TypeDePokemon result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor typeDePokemonCursor = prov.query(
                TypeDePokemonProviderAdapter.TYPEDEPOKEMON_URI,
                TypeDePokemonContract.ALIASED_COLS,
                TypeDePokemonContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getTypeDePokemon().getId())},
                null);

        if (typeDePokemonCursor.getCount() > 0) {
            typeDePokemonCursor.moveToFirst();
            result = TypeDePokemonContract.cursorToItem(typeDePokemonCursor);
        } else {
            result = null;
        }
        typeDePokemonCursor.close();

        return result;
    }

}
