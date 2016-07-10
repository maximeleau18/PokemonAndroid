/**************************************************************************
 * TypeDePokemonEvolutionProviderUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
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

import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonEvolution;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;

import com.maximeleau.harmony.android.pokemon.provider.TypeDePokemonEvolutionProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.TypeDePokemonProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonEvolutionContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;

/**
 * TypeDePokemonEvolution Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class TypeDePokemonEvolutionProviderUtilsBase
            extends ProviderUtils<TypeDePokemonEvolution> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "TypeDePokemonEvolutionProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public TypeDePokemonEvolutionProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final TypeDePokemonEvolution item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = TypeDePokemonEvolutionContract.itemToContentValues(item);
        itemValues.remove(TypeDePokemonEvolutionContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                TypeDePokemonEvolutionProviderAdapter.TYPEDEPOKEMONEVOLUTION_URI)
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
     * @param item TypeDePokemonEvolution
     * @return number of row affected
     */
    public int delete(final TypeDePokemonEvolution item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = TypeDePokemonEvolutionProviderAdapter.TYPEDEPOKEMONEVOLUTION_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return TypeDePokemonEvolution
     */
    public TypeDePokemonEvolution query(final TypeDePokemonEvolution item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return TypeDePokemonEvolution
     */
    public TypeDePokemonEvolution query(final int id) {
        TypeDePokemonEvolution result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(TypeDePokemonEvolutionContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            TypeDePokemonEvolutionProviderAdapter.TYPEDEPOKEMONEVOLUTION_URI,
            TypeDePokemonEvolutionContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = TypeDePokemonEvolutionContract.cursorToItem(cursor);

            if (result.getEvolueEn() != null) {
                result.setEvolueEn(
                    this.getAssociateEvolueEn(result));
            }
            if (result.getEstEvolueEn() != null) {
                result.setEstEvolueEn(
                    this.getAssociateEstEvolueEn(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<TypeDePokemonEvolution>
     */
    public ArrayList<TypeDePokemonEvolution> queryAll() {
        ArrayList<TypeDePokemonEvolution> result =
                    new ArrayList<TypeDePokemonEvolution>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                TypeDePokemonEvolutionProviderAdapter.TYPEDEPOKEMONEVOLUTION_URI,
                TypeDePokemonEvolutionContract.ALIASED_COLS,
                null,
                null,
                null);

        result = TypeDePokemonEvolutionContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<TypeDePokemonEvolution>
     */
    public ArrayList<TypeDePokemonEvolution> query(CriteriaExpression expression) {
        ArrayList<TypeDePokemonEvolution> result =
                    new ArrayList<TypeDePokemonEvolution>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                TypeDePokemonEvolutionProviderAdapter.TYPEDEPOKEMONEVOLUTION_URI,
                TypeDePokemonEvolutionContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = TypeDePokemonEvolutionContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item TypeDePokemonEvolution
     
     * @return number of rows updated
     */
    public int update(final TypeDePokemonEvolution item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = TypeDePokemonEvolutionContract.itemToContentValues(
                item);

        Uri uri = TypeDePokemonEvolutionProviderAdapter.TYPEDEPOKEMONEVOLUTION_URI;
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
     * Get associate EvolueEn.
     * @param item TypeDePokemonEvolution
     * @return TypeDePokemon
     */
    public TypeDePokemon getAssociateEvolueEn(
            final TypeDePokemonEvolution item) {
        TypeDePokemon result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor typeDePokemonCursor = prov.query(
                TypeDePokemonProviderAdapter.TYPEDEPOKEMON_URI,
                TypeDePokemonContract.ALIASED_COLS,
                TypeDePokemonContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getEvolueEn().getId())},
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

    /**
     * Get associate EstEvolueEn.
     * @param item TypeDePokemonEvolution
     * @return TypeDePokemon
     */
    public TypeDePokemon getAssociateEstEvolueEn(
            final TypeDePokemonEvolution item) {
        TypeDePokemon result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor typeDePokemonCursor = prov.query(
                TypeDePokemonProviderAdapter.TYPEDEPOKEMON_URI,
                TypeDePokemonContract.ALIASED_COLS,
                TypeDePokemonContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getEstEvolueEn().getId())},
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
