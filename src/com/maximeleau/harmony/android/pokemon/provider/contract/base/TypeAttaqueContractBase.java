/**************************************************************************
 * TypeAttaqueContractBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.entity.TypeAttaque;



import com.maximeleau.harmony.android.pokemon.provider.contract.TypeAttaqueContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class TypeAttaqueContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            TypeAttaqueContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            TypeAttaqueContract.TABLE_NAME + "." + COL_NOM;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "TypeAttaque";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "TypeAttaque";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        TypeAttaqueContract.COL_ID,
        
        TypeAttaqueContract.COL_NOM
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        TypeAttaqueContract.ALIASED_COL_ID,
        
        TypeAttaqueContract.ALIASED_COL_NOM
    };


    /**
     * Converts a TypeAttaque into a content values.
     *
     * @param item The TypeAttaque to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final TypeAttaque item) {
        final ContentValues result = new ContentValues();

             result.put(TypeAttaqueContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(TypeAttaqueContract.COL_NOM,
                    item.getNom());
            }


        return result;
    }

    /**
     * Converts a Cursor into a TypeAttaque.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted TypeAttaque
     */
    public static TypeAttaque cursorToItem(final android.database.Cursor cursor) {
        TypeAttaque result = new TypeAttaque();
        TypeAttaqueContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to TypeAttaque entity.
     * @param cursor Cursor object
     * @param result TypeAttaque entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final TypeAttaque result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(TypeAttaqueContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(TypeAttaqueContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }

        }
    }

    /**
     * Convert Cursor of database to Array of TypeAttaque entity.
     * @param cursor Cursor object
     * @return Array of TypeAttaque entity
     */
    public static ArrayList<TypeAttaque> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<TypeAttaque> result = new ArrayList<TypeAttaque>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            TypeAttaque item;
            do {
                item = TypeAttaqueContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
