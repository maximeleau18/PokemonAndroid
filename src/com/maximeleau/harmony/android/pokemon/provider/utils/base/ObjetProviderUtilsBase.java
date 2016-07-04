/**************************************************************************
 * ObjetProviderUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
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

import com.maximeleau.harmony.android.pokemon.entity.Objet;
import com.maximeleau.harmony.android.pokemon.entity.TypeObjet;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;

import com.maximeleau.harmony.android.pokemon.provider.ObjetProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.TypeObjetProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PersonnageNonJoueurProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.ObjetContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeObjetContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;

/**
 * Objet Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class ObjetProviderUtilsBase
            extends ProviderUtils<Objet> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "ObjetProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public ObjetProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Objet item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = ObjetContract.itemToContentValues(item);
        itemValues.remove(ObjetContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                ObjetProviderAdapter.OBJET_URI)
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
     * @param item Objet
     * @return number of row affected
     */
    public int delete(final Objet item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = ObjetProviderAdapter.OBJET_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Objet
     */
    public Objet query(final Objet item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Objet
     */
    public Objet query(final int id) {
        Objet result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(ObjetContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            ObjetProviderAdapter.OBJET_URI,
            ObjetContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = ObjetContract.cursorToItem(cursor);

            if (result.getTypeObjet() != null) {
                result.setTypeObjet(
                    this.getAssociateTypeObjet(result));
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
     * @return ArrayList<Objet>
     */
    public ArrayList<Objet> queryAll() {
        ArrayList<Objet> result =
                    new ArrayList<Objet>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                ObjetProviderAdapter.OBJET_URI,
                ObjetContract.ALIASED_COLS,
                null,
                null,
                null);

        result = ObjetContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Objet>
     */
    public ArrayList<Objet> query(CriteriaExpression expression) {
        ArrayList<Objet> result =
                    new ArrayList<Objet>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                ObjetProviderAdapter.OBJET_URI,
                ObjetContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = ObjetContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Objet
     
     * @return number of rows updated
     */
    public int update(final Objet item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = ObjetContract.itemToContentValues(
                item);

        Uri uri = ObjetProviderAdapter.OBJET_URI;
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
     * Get associate TypeObjet.
     * @param item Objet
     * @return TypeObjet
     */
    public TypeObjet getAssociateTypeObjet(
            final Objet item) {
        TypeObjet result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor typeObjetCursor = prov.query(
                TypeObjetProviderAdapter.TYPEOBJET_URI,
                TypeObjetContract.ALIASED_COLS,
                TypeObjetContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getTypeObjet().getId())},
                null);

        if (typeObjetCursor.getCount() > 0) {
            typeObjetCursor.moveToFirst();
            result = TypeObjetContract.cursorToItem(typeObjetCursor);
        } else {
            result = null;
        }
        typeObjetCursor.close();

        return result;
    }

    /**
     * Get associate PersonnageNonJoueur.
     * @param item Objet
     * @return PersonnageNonJoueur
     */
    public PersonnageNonJoueur getAssociatePersonnageNonJoueur(
            final Objet item) {
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
