/**************************************************************************
 * AreneProviderUtilsBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.entity.Badge;
import com.maximeleau.harmony.android.pokemon.entity.Position;

import com.maximeleau.harmony.android.pokemon.provider.AreneProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PersonnageNonJoueurProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.BadgeProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PositionProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PositionContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;

/**
 * Arene Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class AreneProviderUtilsBase
            extends ProviderUtils<Arene> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "AreneProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public AreneProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Arene item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = AreneContract.itemToContentValues(item);
        itemValues.remove(AreneContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                AreneProviderAdapter.ARENE_URI)
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
     * @param item Arene to insert
     * @param zonearenesInternalId zonearenesInternal Id
     * @return number of rows affected
     */
    public Uri insert(final Arene item,
                             final int zonearenesInternalId) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();

        ContentValues itemValues = AreneContract.itemToContentValues(item,
                    zonearenesInternalId);
        itemValues.remove(AreneContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                AreneProviderAdapter.ARENE_URI)
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
     * @param item Arene
     * @return number of row affected
     */
    public int delete(final Arene item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = AreneProviderAdapter.ARENE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Arene
     */
    public Arene query(final Arene item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Arene
     */
    public Arene query(final int id) {
        Arene result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(AreneContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            AreneProviderAdapter.ARENE_URI,
            AreneContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = AreneContract.cursorToItem(cursor);

            if (result.getMaitre() != null) {
                result.setMaitre(
                    this.getAssociateMaitre(result));
            }
            if (result.getBadge() != null) {
                result.setBadge(
                    this.getAssociateBadge(result));
            }
            if (result.getPosition() != null) {
                result.setPosition(
                    this.getAssociatePosition(result));
            }
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Arene>
     */
    public ArrayList<Arene> queryAll() {
        ArrayList<Arene> result =
                    new ArrayList<Arene>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                AreneProviderAdapter.ARENE_URI,
                AreneContract.ALIASED_COLS,
                null,
                null,
                null);

        result = AreneContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Arene>
     */
    public ArrayList<Arene> query(CriteriaExpression expression) {
        ArrayList<Arene> result =
                    new ArrayList<Arene>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                AreneProviderAdapter.ARENE_URI,
                AreneContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = AreneContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Arene
     * @return number of rows updated
     */
    public int update(final Arene item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = AreneContract.itemToContentValues(item);

        Uri uri = AreneProviderAdapter.ARENE_URI;
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
     * @param item Arene
     * @param zonearenesInternalId zonearenesInternal Id
     * @return number of rows updated
     */
    public int update(final Arene item,
                             final int zonearenesInternalId) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = AreneContract.itemToContentValues(
                item,
                zonearenesInternalId);

        Uri uri = AreneProviderAdapter.ARENE_URI;
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
     * Get associate Maitre.
     * @param item Arene
     * @return PersonnageNonJoueur
     */
    public PersonnageNonJoueur getAssociateMaitre(
            final Arene item) {
        PersonnageNonJoueur result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor personnageNonJoueurCursor = prov.query(
                PersonnageNonJoueurProviderAdapter.PERSONNAGENONJOUEUR_URI,
                PersonnageNonJoueurContract.ALIASED_COLS,
                PersonnageNonJoueurContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getMaitre().getId())},
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

    /**
     * Get associate Badge.
     * @param item Arene
     * @return Badge
     */
    public Badge getAssociateBadge(
            final Arene item) {
        Badge result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor badgeCursor = prov.query(
                BadgeProviderAdapter.BADGE_URI,
                BadgeContract.ALIASED_COLS,
                BadgeContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getBadge().getId())},
                null);

        if (badgeCursor.getCount() > 0) {
            badgeCursor.moveToFirst();
            result = BadgeContract.cursorToItem(badgeCursor);
        } else {
            result = null;
        }
        badgeCursor.close();

        return result;
    }

    /**
     * Get associate Position.
     * @param item Arene
     * @return Position
     */
    public Position getAssociatePosition(
            final Arene item) {
        Position result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor positionCursor = prov.query(
                PositionProviderAdapter.POSITION_URI,
                PositionContract.ALIASED_COLS,
                PositionContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getPosition().getId())},
                null);

        if (positionCursor.getCount() > 0) {
            positionCursor.moveToFirst();
            result = PositionContract.cursorToItem(positionCursor);
        } else {
            result = null;
        }
        positionCursor.close();

        return result;
    }

}
