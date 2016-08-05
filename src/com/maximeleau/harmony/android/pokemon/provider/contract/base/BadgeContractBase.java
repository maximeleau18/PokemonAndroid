/**************************************************************************
 * BadgeContractBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Aug 5, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import com.maximeleau.harmony.android.pokemon.entity.Badge;



import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class BadgeContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            BadgeContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            BadgeContract.TABLE_NAME + "." + COL_NOM;

    /** ZonebadgesInternal_id. */
    public static final String COL_ZONEBADGESINTERNAL_ID =
            "Zone_badges_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_ZONEBADGESINTERNAL_ID =
            BadgeContract.TABLE_NAME + "." + COL_ZONEBADGESINTERNAL_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Badge";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Badge";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        BadgeContract.COL_ID,
        
        BadgeContract.COL_NOM,
        
        BadgeContract.COL_ZONEBADGESINTERNAL_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        BadgeContract.ALIASED_COL_ID,
        
        BadgeContract.ALIASED_COL_NOM,
        
        BadgeContract.ALIASED_COL_ZONEBADGESINTERNAL_ID
    };

    /** Convert Badge entity to Content Values for database.
     *
     * @param item Badge entity object
     * @param zoneId zone id
     * @return ContentValues object
     */
    public static ContentValues itemToContentValues(final Badge item,
                final int zonebadgesInternalId) {
        final ContentValues result = BadgeContract.itemToContentValues(item);
        result.put(BadgeContract.COL_ZONEBADGESINTERNAL_ID,
                String.valueOf(zonebadgesInternalId));
        return result;
    }

    /**
     * Converts a Badge into a content values.
     *
     * @param item The Badge to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Badge item) {
        final ContentValues result = new ContentValues();

             result.put(BadgeContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(BadgeContract.COL_NOM,
                    item.getNom());
            }

 
        return result;
    }

    /**
     * Converts a Cursor into a Badge.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Badge
     */
    public static Badge cursorToItem(final android.database.Cursor cursor) {
        Badge result = new Badge();
        BadgeContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Badge entity.
     * @param cursor Cursor object
     * @param result Badge entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Badge result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(BadgeContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(BadgeContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of Badge entity.
     * @param cursor Cursor object
     * @return Array of Badge entity
     */
    public static ArrayList<Badge> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Badge> result = new ArrayList<Badge>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Badge item;
            do {
                item = BadgeContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
