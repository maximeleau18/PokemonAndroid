/**************************************************************************
 * TypeDePokemonProviderUtilsBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.criterias.base.Criterion;
import com.maximeleau.harmony.android.pokemon.criterias.base.Criterion.Type;
import com.maximeleau.harmony.android.pokemon.criterias.base.value.ArrayValue;
import com.maximeleau.harmony.android.pokemon.criterias.base.CriteriaExpression;
import com.maximeleau.harmony.android.pokemon.criterias.base.CriteriaExpression.GroupType;

import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;

import com.maximeleau.harmony.android.pokemon.provider.TypeDePokemonProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;

/**
 * TypeDePokemon Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class TypeDePokemonProviderUtilsBase
            extends ProviderUtils<TypeDePokemon> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "TypeDePokemonProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public TypeDePokemonProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final TypeDePokemon item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = TypeDePokemonContract.itemToContentValues(item);
        itemValues.remove(TypeDePokemonContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                TypeDePokemonProviderAdapter.TYPEDEPOKEMON_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getPokemons() != null && item.getPokemons().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokemonContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getPokemons().size(); i++) {
                inValue.addValue(String.valueOf(item.getPokemons().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokemonProviderAdapter.POKEMON_URI)
                    .withValueBackReference(
                            PokemonContract
                                    .COL_TYPEDEPOKEMON_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }

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
     * @param item TypeDePokemon
     * @return number of row affected
     */
    public int delete(final TypeDePokemon item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = TypeDePokemonProviderAdapter.TYPEDEPOKEMON_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return TypeDePokemon
     */
    public TypeDePokemon query(final TypeDePokemon item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return TypeDePokemon
     */
    public TypeDePokemon query(final int id) {
        TypeDePokemon result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(TypeDePokemonContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            TypeDePokemonProviderAdapter.TYPEDEPOKEMON_URI,
            TypeDePokemonContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = TypeDePokemonContract.cursorToItem(cursor);

            result.setPokemons(
                this.getAssociatePokemons(result));
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<TypeDePokemon>
     */
    public ArrayList<TypeDePokemon> queryAll() {
        ArrayList<TypeDePokemon> result =
                    new ArrayList<TypeDePokemon>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                TypeDePokemonProviderAdapter.TYPEDEPOKEMON_URI,
                TypeDePokemonContract.ALIASED_COLS,
                null,
                null,
                null);

        result = TypeDePokemonContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<TypeDePokemon>
     */
    public ArrayList<TypeDePokemon> query(CriteriaExpression expression) {
        ArrayList<TypeDePokemon> result =
                    new ArrayList<TypeDePokemon>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                TypeDePokemonProviderAdapter.TYPEDEPOKEMON_URI,
                TypeDePokemonContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = TypeDePokemonContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item TypeDePokemon
     
     * @return number of rows updated
     */
    public int update(final TypeDePokemon item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = TypeDePokemonContract.itemToContentValues(
                item);

        Uri uri = TypeDePokemonProviderAdapter.TYPEDEPOKEMON_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getPokemons() != null && item.getPokemons().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new pokemons for TypeDePokemon
            CriteriaExpression pokemonsCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokemonContract.COL_ID);
            crit.addValue(values);
            pokemonsCrit.add(crit);


            for (Pokemon pokemons : item.getPokemons()) {
                values.addValue(
                    String.valueOf(pokemons.getId()));
            }
            selection = pokemonsCrit.toSQLiteSelection();
            selectionArgs = pokemonsCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokemonProviderAdapter.POKEMON_URI)
                    .withValue(
                            PokemonContract.COL_TYPEDEPOKEMON_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated pokemons
            crit.setType(Type.NOT_IN);
            pokemonsCrit.add(PokemonContract.COL_TYPEDEPOKEMON_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokemonProviderAdapter.POKEMON_URI)
                    .withValue(
                            PokemonContract.COL_TYPEDEPOKEMON_ID,
                            null)
                    .withSelection(
                            pokemonsCrit.toSQLiteSelection(),
                            pokemonsCrit.toSQLiteSelectionArgs())
                    .build());
        }


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
     * Get associate Pokemons.
     * @param item TypeDePokemon
     * @return Pokemon
     */
    public ArrayList<Pokemon> getAssociatePokemons(
            final TypeDePokemon item) {
        ArrayList<Pokemon> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokemonCursor = prov.query(
                PokemonProviderAdapter.POKEMON_URI,
                PokemonContract.ALIASED_COLS,
                PokemonContract.ALIASED_COL_TYPEDEPOKEMON_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = PokemonContract.cursorToItems(
                        pokemonCursor);
        pokemonCursor.close();

        return result;
    }

}
