/**************************************************************************
 * ZoneContractBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.entity.Zone;
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.entity.Badge;
import com.maximeleau.harmony.android.pokemon.entity.Position;



import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class ZoneContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            ZoneContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            ZoneContract.TABLE_NAME + "." + COL_NOM;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Zone";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Zone";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        ZoneContract.COL_ID,
        
        ZoneContract.COL_NOM,
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        ZoneContract.ALIASED_COL_ID,
        
        ZoneContract.ALIASED_COL_NOM,
        
        
        
    };


    /**
     * Converts a Zone into a content values.
     *
     * @param item The Zone to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Zone item) {
        final ContentValues result = new ContentValues();

             result.put(ZoneContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(ZoneContract.COL_NOM,
                    item.getNom());
            }

   
        return result;
    }

    /**
     * Converts a Cursor into a Zone.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Zone
     */
    public static Zone cursorToItem(final android.database.Cursor cursor) {
        Zone result = new Zone();
        ZoneContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Zone entity.
     * @param cursor Cursor object
     * @param result Zone entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Zone result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(ZoneContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(ZoneContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of Zone entity.
     * @param cursor Cursor object
     * @return Array of Zone entity
     */
    public static ArrayList<Zone> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Zone> result = new ArrayList<Zone>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Zone item;
            do {
                item = ZoneContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
