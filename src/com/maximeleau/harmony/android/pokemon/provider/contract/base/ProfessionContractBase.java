/**************************************************************************
 * ProfessionContractBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 18, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import com.maximeleau.harmony.android.pokemon.entity.Profession;



import com.maximeleau.harmony.android.pokemon.provider.contract.ProfessionContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class ProfessionContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            ProfessionContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            ProfessionContract.TABLE_NAME + "." + COL_NOM;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Profession";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Profession";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        ProfessionContract.COL_ID,
        
        ProfessionContract.COL_NOM
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        ProfessionContract.ALIASED_COL_ID,
        
        ProfessionContract.ALIASED_COL_NOM
    };


    /**
     * Converts a Profession into a content values.
     *
     * @param item The Profession to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Profession item) {
        final ContentValues result = new ContentValues();

             result.put(ProfessionContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(ProfessionContract.COL_NOM,
                    item.getNom());
            }


        return result;
    }

    /**
     * Converts a Cursor into a Profession.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Profession
     */
    public static Profession cursorToItem(final android.database.Cursor cursor) {
        Profession result = new Profession();
        ProfessionContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Profession entity.
     * @param cursor Cursor object
     * @param result Profession entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Profession result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(ProfessionContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(ProfessionContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of Profession entity.
     * @param cursor Cursor object
     * @return Array of Profession entity
     */
    public static ArrayList<Profession> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Profession> result = new ArrayList<Profession>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Profession item;
            do {
                item = ProfessionContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
