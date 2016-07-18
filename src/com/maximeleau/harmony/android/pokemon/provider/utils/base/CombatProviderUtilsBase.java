/**************************************************************************
 * CombatProviderUtilsBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.entity.Combat;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;

import com.maximeleau.harmony.android.pokemon.provider.CombatProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.DresseurProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.CombatContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;

/**
 * Combat Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class CombatProviderUtilsBase
            extends ProviderUtils<Combat> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "CombatProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public CombatProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Combat item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = CombatContract.itemToContentValues(item);
        itemValues.remove(CombatContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                CombatProviderAdapter.COMBAT_URI)
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
     * @param item Combat
     * @return number of row affected
     */
    public int delete(final Combat item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = CombatProviderAdapter.COMBAT_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Combat
     */
    public Combat query(final Combat item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Combat
     */
    public Combat query(final int id) {
        Combat result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(CombatContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            CombatProviderAdapter.COMBAT_URI,
            CombatContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = CombatContract.cursorToItem(cursor);

            if (result.getPokemon1() != null) {
                result.setPokemon1(
                    this.getAssociatePokemon1(result));
            }
            if (result.getPokemon2() != null) {
                result.setPokemon2(
                    this.getAssociatePokemon2(result));
            }
            if (result.getDresseur1() != null) {
                result.setDresseur1(
                    this.getAssociateDresseur1(result));
            }
            if (result.getDresseur2() != null) {
                result.setDresseur2(
                    this.getAssociateDresseur2(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Combat>
     */
    public ArrayList<Combat> queryAll() {
        ArrayList<Combat> result =
                    new ArrayList<Combat>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                CombatProviderAdapter.COMBAT_URI,
                CombatContract.ALIASED_COLS,
                null,
                null,
                null);

        result = CombatContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Combat>
     */
    public ArrayList<Combat> query(CriteriaExpression expression) {
        ArrayList<Combat> result =
                    new ArrayList<Combat>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                CombatProviderAdapter.COMBAT_URI,
                CombatContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = CombatContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Combat
     
     * @return number of rows updated
     */
    public int update(final Combat item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = CombatContract.itemToContentValues(
                item);

        Uri uri = CombatProviderAdapter.COMBAT_URI;
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
     * Get associate Pokemon1.
     * @param item Combat
     * @return Pokemon
     */
    public Pokemon getAssociatePokemon1(
            final Combat item) {
        Pokemon result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokemonCursor = prov.query(
                PokemonProviderAdapter.POKEMON_URI,
                PokemonContract.ALIASED_COLS,
                PokemonContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getPokemon1().getId())},
                null);

        if (pokemonCursor.getCount() > 0) {
            pokemonCursor.moveToFirst();
            result = PokemonContract.cursorToItem(pokemonCursor);
        } else {
            result = null;
        }
        pokemonCursor.close();

        return result;
    }

    /**
     * Get associate Pokemon2.
     * @param item Combat
     * @return Pokemon
     */
    public Pokemon getAssociatePokemon2(
            final Combat item) {
        Pokemon result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokemonCursor = prov.query(
                PokemonProviderAdapter.POKEMON_URI,
                PokemonContract.ALIASED_COLS,
                PokemonContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getPokemon2().getId())},
                null);

        if (pokemonCursor.getCount() > 0) {
            pokemonCursor.moveToFirst();
            result = PokemonContract.cursorToItem(pokemonCursor);
        } else {
            result = null;
        }
        pokemonCursor.close();

        return result;
    }

    /**
     * Get associate Dresseur1.
     * @param item Combat
     * @return Dresseur
     */
    public Dresseur getAssociateDresseur1(
            final Combat item) {
        Dresseur result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor dresseurCursor = prov.query(
                DresseurProviderAdapter.DRESSEUR_URI,
                DresseurContract.ALIASED_COLS,
                DresseurContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getDresseur1().getId())},
                null);

        if (dresseurCursor.getCount() > 0) {
            dresseurCursor.moveToFirst();
            result = DresseurContract.cursorToItem(dresseurCursor);
        } else {
            result = null;
        }
        dresseurCursor.close();

        return result;
    }

    /**
     * Get associate Dresseur2.
     * @param item Combat
     * @return Dresseur
     */
    public Dresseur getAssociateDresseur2(
            final Combat item) {
        Dresseur result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor dresseurCursor = prov.query(
                DresseurProviderAdapter.DRESSEUR_URI,
                DresseurContract.ALIASED_COLS,
                DresseurContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getDresseur2().getId())},
                null);

        if (dresseurCursor.getCount() > 0) {
            dresseurCursor.moveToFirst();
            result = DresseurContract.cursorToItem(dresseurCursor);
        } else {
            result = null;
        }
        dresseurCursor.close();

        return result;
    }

}
