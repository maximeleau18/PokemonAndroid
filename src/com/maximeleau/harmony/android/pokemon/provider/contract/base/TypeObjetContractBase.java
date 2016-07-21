/**************************************************************************
 * TypeObjetContractBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.entity.TypeObjet;



import com.maximeleau.harmony.android.pokemon.provider.contract.TypeObjetContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class TypeObjetContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            TypeObjetContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            TypeObjetContract.TABLE_NAME + "." + COL_NOM;

    /** urlImage. */
    public static final String COL_URLIMAGE =
            "urlImage";
    /** Alias. */
    public static final String ALIASED_COL_URLIMAGE =
            TypeObjetContract.TABLE_NAME + "." + COL_URLIMAGE;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "TypeObjet";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "TypeObjet";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        TypeObjetContract.COL_ID,
        
        TypeObjetContract.COL_NOM,
        
        TypeObjetContract.COL_URLIMAGE
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        TypeObjetContract.ALIASED_COL_ID,
        
        TypeObjetContract.ALIASED_COL_NOM,
        
        TypeObjetContract.ALIASED_COL_URLIMAGE
    };


    /**
     * Converts a TypeObjet into a content values.
     *
     * @param item The TypeObjet to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final TypeObjet item) {
        final ContentValues result = new ContentValues();

             result.put(TypeObjetContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(TypeObjetContract.COL_NOM,
                    item.getNom());
            }

             if (item.getUrlImage() != null) {
                result.put(TypeObjetContract.COL_URLIMAGE,
                    item.getUrlImage());
            } else {
                result.put(TypeObjetContract.COL_URLIMAGE, (String) null);
            }


        return result;
    }

    /**
     * Converts a Cursor into a TypeObjet.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted TypeObjet
     */
    public static TypeObjet cursorToItem(final android.database.Cursor cursor) {
        TypeObjet result = new TypeObjet();
        TypeObjetContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to TypeObjet entity.
     * @param cursor Cursor object
     * @param result TypeObjet entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final TypeObjet result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(TypeObjetContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(TypeObjetContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(TypeObjetContract.COL_URLIMAGE);

            if (index > -1) {
            if (!cursor.isNull(index)) {
                    result.setUrlImage(cursor.getString(index));
            }
            }

        }
    }

    /**
     * Convert Cursor of database to Array of TypeObjet entity.
     * @param cursor Cursor object
     * @return Array of TypeObjet entity
     */
    public static ArrayList<TypeObjet> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<TypeObjet> result = new ArrayList<TypeObjet>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            TypeObjet item;
            do {
                item = TypeObjetContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
