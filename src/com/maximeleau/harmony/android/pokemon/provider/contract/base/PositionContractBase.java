/**************************************************************************
 * PositionContractBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import com.maximeleau.harmony.android.pokemon.entity.Position;
import com.maximeleau.harmony.android.pokemon.entity.Zone;



import com.maximeleau.harmony.android.pokemon.provider.contract.PositionContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PositionContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PositionContract.TABLE_NAME + "." + COL_ID;

    /** x. */
    public static final String COL_X =
            "x";
    /** Alias. */
    public static final String ALIASED_COL_X =
            PositionContract.TABLE_NAME + "." + COL_X;

    /** y. */
    public static final String COL_Y =
            "y";
    /** Alias. */
    public static final String ALIASED_COL_Y =
            PositionContract.TABLE_NAME + "." + COL_Y;

    /** zone_id. */
    public static final String COL_ZONE_ID =
            "zone_id";
    /** Alias. */
    public static final String ALIASED_COL_ZONE_ID =
            PositionContract.TABLE_NAME + "." + COL_ZONE_ID;

    /** ZonepositionsInternal_id. */
    public static final String COL_ZONEPOSITIONSINTERNAL_ID =
            "Zone_positions_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_ZONEPOSITIONSINTERNAL_ID =
            PositionContract.TABLE_NAME + "." + COL_ZONEPOSITIONSINTERNAL_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Position";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Position";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PositionContract.COL_ID,
        
        PositionContract.COL_X,
        
        PositionContract.COL_Y,
        
        PositionContract.COL_ZONE_ID,
        
        PositionContract.COL_ZONEPOSITIONSINTERNAL_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PositionContract.ALIASED_COL_ID,
        
        PositionContract.ALIASED_COL_X,
        
        PositionContract.ALIASED_COL_Y,
        
        PositionContract.ALIASED_COL_ZONE_ID,
        
        PositionContract.ALIASED_COL_ZONEPOSITIONSINTERNAL_ID
    };

    /** Convert Position entity to Content Values for database.
     *
     * @param item Position entity object
     * @param zoneId zone id
     * @return ContentValues object
     */
    public static ContentValues itemToContentValues(final Position item,
                final int zonepositionsInternalId) {
        final ContentValues result = PositionContract.itemToContentValues(item);
        result.put(PositionContract.COL_ZONEPOSITIONSINTERNAL_ID,
                String.valueOf(zonepositionsInternalId));
        return result;
    }

    /**
     * Converts a Position into a content values.
     *
     * @param item The Position to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Position item) {
        final ContentValues result = new ContentValues();

             result.put(PositionContract.COL_ID,
                String.valueOf(item.getId()));

             result.put(PositionContract.COL_X,
                String.valueOf(item.getX()));

             result.put(PositionContract.COL_Y,
                String.valueOf(item.getY()));

             if (item.getZone() != null) {
                result.put(PositionContract.COL_ZONE_ID,
                    item.getZone().getId());
            }

 
        return result;
    }

    /**
     * Converts a Cursor into a Position.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Position
     */
    public static Position cursorToItem(final android.database.Cursor cursor) {
        Position result = new Position();
        PositionContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Position entity.
     * @param cursor Cursor object
     * @param result Position entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Position result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PositionContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PositionContract.COL_X);

            if (index > -1) {
                result.setX(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PositionContract.COL_Y);

            if (index > -1) {
                result.setY(cursor.getInt(index));
            }
            if (result.getZone() == null) {
                final Zone zone = new Zone();
                index = cursor.getColumnIndex(PositionContract.COL_ZONE_ID);

                if (index > -1) {
                    zone.setId(cursor.getInt(index));
                    result.setZone(zone);
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of Position entity.
     * @param cursor Cursor object
     * @return Array of Position entity
     */
    public static ArrayList<Position> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Position> result = new ArrayList<Position>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Position item;
            do {
                item = PositionContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
