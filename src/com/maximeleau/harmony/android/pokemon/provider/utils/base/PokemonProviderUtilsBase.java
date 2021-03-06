/**************************************************************************
 * PokemonProviderUtilsBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.entity.Attaque;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;

import com.maximeleau.harmony.android.pokemon.provider.PokemonProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.AttaqueProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.TypeDePokemonProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PersonnageNonJoueurProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.AttaqueContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;

/**
 * Pokemon Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PokemonProviderUtilsBase
            extends ProviderUtils<Pokemon> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PokemonProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PokemonProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Pokemon item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PokemonContract.itemToContentValues(item);
        itemValues.remove(PokemonContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PokemonProviderAdapter.POKEMON_URI)
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
     * @param item Pokemon
     * @return number of row affected
     */
    public int delete(final Pokemon item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PokemonProviderAdapter.POKEMON_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Pokemon
     */
    public Pokemon query(final Pokemon item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Pokemon
     */
    public Pokemon query(final int id) {
        Pokemon result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PokemonContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            PokemonProviderAdapter.POKEMON_URI,
            PokemonContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PokemonContract.cursorToItem(cursor);

            if (result.getAttaque1() != null) {
                result.setAttaque1(
                    this.getAssociateAttaque1(result));
            }
            if (result.getAttaque2() != null) {
                result.setAttaque2(
                    this.getAssociateAttaque2(result));
            }
            if (result.getAttaque3() != null) {
                result.setAttaque3(
                    this.getAssociateAttaque3(result));
            }
            if (result.getAttaque4() != null) {
                result.setAttaque4(
                    this.getAssociateAttaque4(result));
            }
            if (result.getTypeDePokemon() != null) {
                result.setTypeDePokemon(
                    this.getAssociateTypeDePokemon(result));
            }
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
     * @return ArrayList<Pokemon>
     */
    public ArrayList<Pokemon> queryAll() {
        ArrayList<Pokemon> result =
                    new ArrayList<Pokemon>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokemonProviderAdapter.POKEMON_URI,
                PokemonContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PokemonContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Pokemon>
     */
    public ArrayList<Pokemon> query(CriteriaExpression expression) {
        ArrayList<Pokemon> result =
                    new ArrayList<Pokemon>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PokemonProviderAdapter.POKEMON_URI,
                PokemonContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PokemonContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Pokemon
     
     * @return number of rows updated
     */
    public int update(final Pokemon item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PokemonContract.itemToContentValues(
                item);

        Uri uri = PokemonProviderAdapter.POKEMON_URI;
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
     * Get associate Attaque1.
     * @param item Pokemon
     * @return Attaque
     */
    public Attaque getAssociateAttaque1(
            final Pokemon item) {
        Attaque result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor attaqueCursor = prov.query(
                AttaqueProviderAdapter.ATTAQUE_URI,
                AttaqueContract.ALIASED_COLS,
                AttaqueContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getAttaque1().getId())},
                null);

        if (attaqueCursor.getCount() > 0) {
            attaqueCursor.moveToFirst();
            result = AttaqueContract.cursorToItem(attaqueCursor);
        } else {
            result = null;
        }
        attaqueCursor.close();

        return result;
    }

    /**
     * Get associate Attaque2.
     * @param item Pokemon
     * @return Attaque
     */
    public Attaque getAssociateAttaque2(
            final Pokemon item) {
        Attaque result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor attaqueCursor = prov.query(
                AttaqueProviderAdapter.ATTAQUE_URI,
                AttaqueContract.ALIASED_COLS,
                AttaqueContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getAttaque2().getId())},
                null);

        if (attaqueCursor.getCount() > 0) {
            attaqueCursor.moveToFirst();
            result = AttaqueContract.cursorToItem(attaqueCursor);
        } else {
            result = null;
        }
        attaqueCursor.close();

        return result;
    }

    /**
     * Get associate Attaque3.
     * @param item Pokemon
     * @return Attaque
     */
    public Attaque getAssociateAttaque3(
            final Pokemon item) {
        Attaque result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor attaqueCursor = prov.query(
                AttaqueProviderAdapter.ATTAQUE_URI,
                AttaqueContract.ALIASED_COLS,
                AttaqueContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getAttaque3().getId())},
                null);

        if (attaqueCursor.getCount() > 0) {
            attaqueCursor.moveToFirst();
            result = AttaqueContract.cursorToItem(attaqueCursor);
        } else {
            result = null;
        }
        attaqueCursor.close();

        return result;
    }

    /**
     * Get associate Attaque4.
     * @param item Pokemon
     * @return Attaque
     */
    public Attaque getAssociateAttaque4(
            final Pokemon item) {
        Attaque result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor attaqueCursor = prov.query(
                AttaqueProviderAdapter.ATTAQUE_URI,
                AttaqueContract.ALIASED_COLS,
                AttaqueContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getAttaque4().getId())},
                null);

        if (attaqueCursor.getCount() > 0) {
            attaqueCursor.moveToFirst();
            result = AttaqueContract.cursorToItem(attaqueCursor);
        } else {
            result = null;
        }
        attaqueCursor.close();

        return result;
    }

    /**
     * Get associate TypeDePokemon.
     * @param item Pokemon
     * @return TypeDePokemon
     */
    public TypeDePokemon getAssociateTypeDePokemon(
            final Pokemon item) {
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

    /**
     * Get associate PersonnageNonJoueur.
     * @param item Pokemon
     * @return PersonnageNonJoueur
     */
    public PersonnageNonJoueur getAssociatePersonnageNonJoueur(
            final Pokemon item) {
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
