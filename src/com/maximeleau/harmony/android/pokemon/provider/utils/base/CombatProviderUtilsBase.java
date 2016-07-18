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
import com.maximeleau.harmony.android.pokemon.criterias.base.Criterion;
import com.maximeleau.harmony.android.pokemon.criterias.base.Criterion.Type;
import com.maximeleau.harmony.android.pokemon.criterias.base.value.ArrayValue;
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

        if (item.getPokemon1() != null && item.getPokemon1().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokemonContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getPokemon1().size(); i++) {
                inValue.addValue(String.valueOf(item.getPokemon1().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokemonProviderAdapter.POKEMON_URI)
                    .withValueBackReference(
                            PokemonContract
                                    .COL_COMBATPOKEMON1INTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getPokemon2() != null && item.getPokemon2().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokemonContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getPokemon2().size(); i++) {
                inValue.addValue(String.valueOf(item.getPokemon2().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokemonProviderAdapter.POKEMON_URI)
                    .withValueBackReference(
                            PokemonContract
                                    .COL_COMBATPOKEMON2INTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getDresseur1() != null && item.getDresseur1().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(DresseurContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getDresseur1().size(); i++) {
                inValue.addValue(String.valueOf(item.getDresseur1().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(DresseurProviderAdapter.DRESSEUR_URI)
                    .withValueBackReference(
                            DresseurContract
                                    .COL_COMBATDRESSEUR1INTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getDresseur2() != null && item.getDresseur2().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(DresseurContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getDresseur2().size(); i++) {
                inValue.addValue(String.valueOf(item.getDresseur2().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(DresseurProviderAdapter.DRESSEUR_URI)
                    .withValueBackReference(
                            DresseurContract
                                    .COL_COMBATDRESSEUR2INTERNAL_ID,
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

            result.setPokemon1(
                this.getAssociatePokemon1(result));
            result.setPokemon2(
                this.getAssociatePokemon2(result));
            result.setDresseur1(
                this.getAssociateDresseur1(result));
            result.setDresseur2(
                this.getAssociateDresseur2(result));
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


        if (item.getPokemon1() != null && item.getPokemon1().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new pokemon1 for Combat
            CriteriaExpression pokemon1Crit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokemonContract.COL_ID);
            crit.addValue(values);
            pokemon1Crit.add(crit);


            for (Pokemon pokemon1 : item.getPokemon1()) {
                values.addValue(
                    String.valueOf(pokemon1.getId()));
            }
            selection = pokemon1Crit.toSQLiteSelection();
            selectionArgs = pokemon1Crit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokemonProviderAdapter.POKEMON_URI)
                    .withValue(
                            PokemonContract.COL_COMBATPOKEMON1INTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated pokemon1
            crit.setType(Type.NOT_IN);
            pokemon1Crit.add(PokemonContract.COL_COMBATPOKEMON1INTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokemonProviderAdapter.POKEMON_URI)
                    .withValue(
                            PokemonContract.COL_COMBATPOKEMON1INTERNAL_ID,
                            null)
                    .withSelection(
                            pokemon1Crit.toSQLiteSelection(),
                            pokemon1Crit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getPokemon2() != null && item.getPokemon2().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new pokemon2 for Combat
            CriteriaExpression pokemon2Crit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokemonContract.COL_ID);
            crit.addValue(values);
            pokemon2Crit.add(crit);


            for (Pokemon pokemon2 : item.getPokemon2()) {
                values.addValue(
                    String.valueOf(pokemon2.getId()));
            }
            selection = pokemon2Crit.toSQLiteSelection();
            selectionArgs = pokemon2Crit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokemonProviderAdapter.POKEMON_URI)
                    .withValue(
                            PokemonContract.COL_COMBATPOKEMON2INTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated pokemon2
            crit.setType(Type.NOT_IN);
            pokemon2Crit.add(PokemonContract.COL_COMBATPOKEMON2INTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokemonProviderAdapter.POKEMON_URI)
                    .withValue(
                            PokemonContract.COL_COMBATPOKEMON2INTERNAL_ID,
                            null)
                    .withSelection(
                            pokemon2Crit.toSQLiteSelection(),
                            pokemon2Crit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getDresseur1() != null && item.getDresseur1().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new dresseur1 for Combat
            CriteriaExpression dresseur1Crit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(DresseurContract.COL_ID);
            crit.addValue(values);
            dresseur1Crit.add(crit);


            for (Dresseur dresseur1 : item.getDresseur1()) {
                values.addValue(
                    String.valueOf(dresseur1.getId()));
            }
            selection = dresseur1Crit.toSQLiteSelection();
            selectionArgs = dresseur1Crit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    DresseurProviderAdapter.DRESSEUR_URI)
                    .withValue(
                            DresseurContract.COL_COMBATDRESSEUR1INTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated dresseur1
            crit.setType(Type.NOT_IN);
            dresseur1Crit.add(DresseurContract.COL_COMBATDRESSEUR1INTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    DresseurProviderAdapter.DRESSEUR_URI)
                    .withValue(
                            DresseurContract.COL_COMBATDRESSEUR1INTERNAL_ID,
                            null)
                    .withSelection(
                            dresseur1Crit.toSQLiteSelection(),
                            dresseur1Crit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getDresseur2() != null && item.getDresseur2().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new dresseur2 for Combat
            CriteriaExpression dresseur2Crit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(DresseurContract.COL_ID);
            crit.addValue(values);
            dresseur2Crit.add(crit);


            for (Dresseur dresseur2 : item.getDresseur2()) {
                values.addValue(
                    String.valueOf(dresseur2.getId()));
            }
            selection = dresseur2Crit.toSQLiteSelection();
            selectionArgs = dresseur2Crit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    DresseurProviderAdapter.DRESSEUR_URI)
                    .withValue(
                            DresseurContract.COL_COMBATDRESSEUR2INTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated dresseur2
            crit.setType(Type.NOT_IN);
            dresseur2Crit.add(DresseurContract.COL_COMBATDRESSEUR2INTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    DresseurProviderAdapter.DRESSEUR_URI)
                    .withValue(
                            DresseurContract.COL_COMBATDRESSEUR2INTERNAL_ID,
                            null)
                    .withSelection(
                            dresseur2Crit.toSQLiteSelection(),
                            dresseur2Crit.toSQLiteSelectionArgs())
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
     * Get associate Pokemon1.
     * @param item Combat
     * @return Pokemon
     */
    public ArrayList<Pokemon> getAssociatePokemon1(
            final Combat item) {
        ArrayList<Pokemon> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokemonCursor = prov.query(
                PokemonProviderAdapter.POKEMON_URI,
                PokemonContract.ALIASED_COLS,
                PokemonContract.ALIASED_COL_COMBATPOKEMON1INTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = PokemonContract.cursorToItems(
                        pokemonCursor);
        pokemonCursor.close();

        return result;
    }

    /**
     * Get associate Pokemon2.
     * @param item Combat
     * @return Pokemon
     */
    public ArrayList<Pokemon> getAssociatePokemon2(
            final Combat item) {
        ArrayList<Pokemon> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokemonCursor = prov.query(
                PokemonProviderAdapter.POKEMON_URI,
                PokemonContract.ALIASED_COLS,
                PokemonContract.ALIASED_COL_COMBATPOKEMON2INTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = PokemonContract.cursorToItems(
                        pokemonCursor);
        pokemonCursor.close();

        return result;
    }

    /**
     * Get associate Dresseur1.
     * @param item Combat
     * @return Dresseur
     */
    public ArrayList<Dresseur> getAssociateDresseur1(
            final Combat item) {
        ArrayList<Dresseur> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor dresseurCursor = prov.query(
                DresseurProviderAdapter.DRESSEUR_URI,
                DresseurContract.ALIASED_COLS,
                DresseurContract.ALIASED_COL_COMBATDRESSEUR1INTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = DresseurContract.cursorToItems(
                        dresseurCursor);
        dresseurCursor.close();

        return result;
    }

    /**
     * Get associate Dresseur2.
     * @param item Combat
     * @return Dresseur
     */
    public ArrayList<Dresseur> getAssociateDresseur2(
            final Combat item) {
        ArrayList<Dresseur> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor dresseurCursor = prov.query(
                DresseurProviderAdapter.DRESSEUR_URI,
                DresseurContract.ALIASED_COLS,
                DresseurContract.ALIASED_COL_COMBATDRESSEUR2INTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = DresseurContract.cursorToItems(
                        dresseurCursor);
        dresseurCursor.close();

        return result;
    }

}
