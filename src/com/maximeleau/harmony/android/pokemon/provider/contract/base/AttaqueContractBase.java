/**************************************************************************
 * AttaqueContractBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.entity.Attaque;
import com.maximeleau.harmony.android.pokemon.entity.TypeAttaque;



import com.maximeleau.harmony.android.pokemon.provider.contract.AttaqueContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class AttaqueContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            AttaqueContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            AttaqueContract.TABLE_NAME + "." + COL_NOM;

    /** puissance. */
    public static final String COL_PUISSANCE =
            "puissance";
    /** Alias. */
    public static final String ALIASED_COL_PUISSANCE =
            AttaqueContract.TABLE_NAME + "." + COL_PUISSANCE;

    /** degats. */
    public static final String COL_DEGATS =
            "degats";
    /** Alias. */
    public static final String ALIASED_COL_DEGATS =
            AttaqueContract.TABLE_NAME + "." + COL_DEGATS;

    /** typeAttaque_id. */
    public static final String COL_TYPEATTAQUE_ID =
            "typeAttaque_id";
    /** Alias. */
    public static final String ALIASED_COL_TYPEATTAQUE_ID =
            AttaqueContract.TABLE_NAME + "." + COL_TYPEATTAQUE_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Attaque";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Attaque";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        AttaqueContract.COL_ID,
        
        AttaqueContract.COL_NOM,
        
        AttaqueContract.COL_PUISSANCE,
        
        AttaqueContract.COL_DEGATS,
        
        AttaqueContract.COL_TYPEATTAQUE_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        AttaqueContract.ALIASED_COL_ID,
        
        AttaqueContract.ALIASED_COL_NOM,
        
        AttaqueContract.ALIASED_COL_PUISSANCE,
        
        AttaqueContract.ALIASED_COL_DEGATS,
        
        AttaqueContract.ALIASED_COL_TYPEATTAQUE_ID
    };


    /**
     * Converts a Attaque into a content values.
     *
     * @param item The Attaque to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Attaque item) {
        final ContentValues result = new ContentValues();

             result.put(AttaqueContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(AttaqueContract.COL_NOM,
                    item.getNom());
            }

             result.put(AttaqueContract.COL_PUISSANCE,
                String.valueOf(item.getPuissance()));

             result.put(AttaqueContract.COL_DEGATS,
                String.valueOf(item.getDegats()));

             if (item.getTypeAttaque() != null) {
                result.put(AttaqueContract.COL_TYPEATTAQUE_ID,
                    item.getTypeAttaque().getId());
            }


        return result;
    }

    /**
     * Converts a Cursor into a Attaque.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Attaque
     */
    public static Attaque cursorToItem(final android.database.Cursor cursor) {
        Attaque result = new Attaque();
        AttaqueContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Attaque entity.
     * @param cursor Cursor object
     * @param result Attaque entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Attaque result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(AttaqueContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(AttaqueContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(AttaqueContract.COL_PUISSANCE);

            if (index > -1) {
                result.setPuissance(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(AttaqueContract.COL_DEGATS);

            if (index > -1) {
                result.setDegats(cursor.getInt(index));
            }
            if (result.getTypeAttaque() == null) {
                final TypeAttaque typeAttaque = new TypeAttaque();
                index = cursor.getColumnIndex(AttaqueContract.COL_TYPEATTAQUE_ID);

                if (index > -1) {
                    typeAttaque.setId(cursor.getInt(index));
                    result.setTypeAttaque(typeAttaque);
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of Attaque entity.
     * @param cursor Cursor object
     * @return Array of Attaque entity
     */
    public static ArrayList<Attaque> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Attaque> result = new ArrayList<Attaque>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Attaque item;
            do {
                item = AttaqueContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
