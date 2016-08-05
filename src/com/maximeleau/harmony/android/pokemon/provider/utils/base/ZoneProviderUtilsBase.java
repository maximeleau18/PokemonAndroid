/**************************************************************************
 * ZoneProviderUtilsBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.criterias.base.Criterion;
import com.maximeleau.harmony.android.pokemon.criterias.base.Criterion.Type;
import com.maximeleau.harmony.android.pokemon.criterias.base.value.ArrayValue;
import com.maximeleau.harmony.android.pokemon.criterias.base.CriteriaExpression;
import com.maximeleau.harmony.android.pokemon.criterias.base.CriteriaExpression.GroupType;

import com.maximeleau.harmony.android.pokemon.entity.Zone;
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.entity.Badge;
import com.maximeleau.harmony.android.pokemon.entity.Position;

import com.maximeleau.harmony.android.pokemon.provider.ZoneProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.AreneProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.BadgeProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PositionProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PositionContract;

/**
 * Zone Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class ZoneProviderUtilsBase
            extends ProviderUtils<Zone> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "ZoneProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public ZoneProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final Zone item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = ZoneContract.itemToContentValues(item);
        itemValues.remove(ZoneContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                ZoneProviderAdapter.ZONE_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getArenes() != null && item.getArenes().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(AreneContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getArenes().size(); i++) {
                inValue.addValue(String.valueOf(item.getArenes().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(AreneProviderAdapter.ARENE_URI)
                    .withValueBackReference(
                            AreneContract
                                    .COL_ZONEARENESINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getBadges() != null && item.getBadges().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(BadgeContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getBadges().size(); i++) {
                inValue.addValue(String.valueOf(item.getBadges().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(BadgeProviderAdapter.BADGE_URI)
                    .withValueBackReference(
                            BadgeContract
                                    .COL_ZONEBADGESINTERNAL_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getPositions() != null && item.getPositions().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PositionContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getPositions().size(); i++) {
                inValue.addValue(String.valueOf(item.getPositions().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PositionProviderAdapter.POSITION_URI)
                    .withValueBackReference(
                            PositionContract
                                    .COL_ZONEPOSITIONSINTERNAL_ID,
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
     * @param item Zone
     * @return number of row affected
     */
    public int delete(final Zone item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = ZoneProviderAdapter.ZONE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return Zone
     */
    public Zone query(final Zone item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return Zone
     */
    public Zone query(final int id) {
        Zone result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(ZoneContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            ZoneProviderAdapter.ZONE_URI,
            ZoneContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = ZoneContract.cursorToItem(cursor);

            result.setArenes(
                this.getAssociateArenes(result));
            result.setBadges(
                this.getAssociateBadges(result));
            result.setPositions(
                this.getAssociatePositions(result));
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<Zone>
     */
    public ArrayList<Zone> queryAll() {
        ArrayList<Zone> result =
                    new ArrayList<Zone>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                ZoneProviderAdapter.ZONE_URI,
                ZoneContract.ALIASED_COLS,
                null,
                null,
                null);

        result = ZoneContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<Zone>
     */
    public ArrayList<Zone> query(CriteriaExpression expression) {
        ArrayList<Zone> result =
                    new ArrayList<Zone>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                ZoneProviderAdapter.ZONE_URI,
                ZoneContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = ZoneContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item Zone
     
     * @return number of rows updated
     */
    public int update(final Zone item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = ZoneContract.itemToContentValues(
                item);

        Uri uri = ZoneProviderAdapter.ZONE_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getArenes() != null && item.getArenes().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new arenes for Zone
            CriteriaExpression arenesCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(AreneContract.COL_ID);
            crit.addValue(values);
            arenesCrit.add(crit);


            for (Arene arenes : item.getArenes()) {
                values.addValue(
                    String.valueOf(arenes.getId()));
            }
            selection = arenesCrit.toSQLiteSelection();
            selectionArgs = arenesCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    AreneProviderAdapter.ARENE_URI)
                    .withValue(
                            AreneContract.COL_ZONEARENESINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated arenes
            crit.setType(Type.NOT_IN);
            arenesCrit.add(AreneContract.COL_ZONEARENESINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    AreneProviderAdapter.ARENE_URI)
                    .withValue(
                            AreneContract.COL_ZONEARENESINTERNAL_ID,
                            null)
                    .withSelection(
                            arenesCrit.toSQLiteSelection(),
                            arenesCrit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getBadges() != null && item.getBadges().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new badges for Zone
            CriteriaExpression badgesCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(BadgeContract.COL_ID);
            crit.addValue(values);
            badgesCrit.add(crit);


            for (Badge badges : item.getBadges()) {
                values.addValue(
                    String.valueOf(badges.getId()));
            }
            selection = badgesCrit.toSQLiteSelection();
            selectionArgs = badgesCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    BadgeProviderAdapter.BADGE_URI)
                    .withValue(
                            BadgeContract.COL_ZONEBADGESINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated badges
            crit.setType(Type.NOT_IN);
            badgesCrit.add(BadgeContract.COL_ZONEBADGESINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    BadgeProviderAdapter.BADGE_URI)
                    .withValue(
                            BadgeContract.COL_ZONEBADGESINTERNAL_ID,
                            null)
                    .withSelection(
                            badgesCrit.toSQLiteSelection(),
                            badgesCrit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getPositions() != null && item.getPositions().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new positions for Zone
            CriteriaExpression positionsCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PositionContract.COL_ID);
            crit.addValue(values);
            positionsCrit.add(crit);


            for (Position positions : item.getPositions()) {
                values.addValue(
                    String.valueOf(positions.getId()));
            }
            selection = positionsCrit.toSQLiteSelection();
            selectionArgs = positionsCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PositionProviderAdapter.POSITION_URI)
                    .withValue(
                            PositionContract.COL_ZONEPOSITIONSINTERNAL_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated positions
            crit.setType(Type.NOT_IN);
            positionsCrit.add(PositionContract.COL_ZONEPOSITIONSINTERNAL_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PositionProviderAdapter.POSITION_URI)
                    .withValue(
                            PositionContract.COL_ZONEPOSITIONSINTERNAL_ID,
                            null)
                    .withSelection(
                            positionsCrit.toSQLiteSelection(),
                            positionsCrit.toSQLiteSelectionArgs())
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
     * Get associate Arenes.
     * @param item Zone
     * @return Arene
     */
    public ArrayList<Arene> getAssociateArenes(
            final Zone item) {
        ArrayList<Arene> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor areneCursor = prov.query(
                AreneProviderAdapter.ARENE_URI,
                AreneContract.ALIASED_COLS,
                AreneContract.ALIASED_COL_ZONEARENESINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = AreneContract.cursorToItems(
                        areneCursor);
        areneCursor.close();

        return result;
    }

    /**
     * Get associate Badges.
     * @param item Zone
     * @return Badge
     */
    public ArrayList<Badge> getAssociateBadges(
            final Zone item) {
        ArrayList<Badge> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor badgeCursor = prov.query(
                BadgeProviderAdapter.BADGE_URI,
                BadgeContract.ALIASED_COLS,
                BadgeContract.ALIASED_COL_ZONEBADGESINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = BadgeContract.cursorToItems(
                        badgeCursor);
        badgeCursor.close();

        return result;
    }

    /**
     * Get associate Positions.
     * @param item Zone
     * @return Position
     */
    public ArrayList<Position> getAssociatePositions(
            final Zone item) {
        ArrayList<Position> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor positionCursor = prov.query(
                PositionProviderAdapter.POSITION_URI,
                PositionContract.ALIASED_COLS,
                PositionContract.ALIASED_COL_ZONEPOSITIONSINTERNAL_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = PositionContract.cursorToItems(
                        positionCursor);
        positionCursor.close();

        return result;
    }

}
