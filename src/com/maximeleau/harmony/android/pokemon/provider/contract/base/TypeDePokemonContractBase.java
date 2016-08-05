/**************************************************************************
 * TypeDePokemonContractBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;



import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class TypeDePokemonContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            TypeDePokemonContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            TypeDePokemonContract.TABLE_NAME + "." + COL_NOM;

    /** attaque. */
    public static final String COL_ATTAQUE =
            "attaque";
    /** Alias. */
    public static final String ALIASED_COL_ATTAQUE =
            TypeDePokemonContract.TABLE_NAME + "." + COL_ATTAQUE;

    /** defense. */
    public static final String COL_DEFENSE =
            "defense";
    /** Alias. */
    public static final String ALIASED_COL_DEFENSE =
            TypeDePokemonContract.TABLE_NAME + "." + COL_DEFENSE;

    /** pv. */
    public static final String COL_PV =
            "pv";
    /** Alias. */
    public static final String ALIASED_COL_PV =
            TypeDePokemonContract.TABLE_NAME + "." + COL_PV;

    /** numPokedex. */
    public static final String COL_NUMPOKEDEX =
            "numPokedex";
    /** Alias. */
    public static final String ALIASED_COL_NUMPOKEDEX =
            TypeDePokemonContract.TABLE_NAME + "." + COL_NUMPOKEDEX;

    /** urlImage. */
    public static final String COL_URLIMAGE =
            "urlImage";
    /** Alias. */
    public static final String ALIASED_COL_URLIMAGE =
            TypeDePokemonContract.TABLE_NAME + "." + COL_URLIMAGE;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "TypeDePokemon";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "TypeDePokemon";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        TypeDePokemonContract.COL_ID,
        
        TypeDePokemonContract.COL_NOM,
        
        TypeDePokemonContract.COL_ATTAQUE,
        
        TypeDePokemonContract.COL_DEFENSE,
        
        TypeDePokemonContract.COL_PV,
        
        TypeDePokemonContract.COL_NUMPOKEDEX,
        
        TypeDePokemonContract.COL_URLIMAGE,
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        TypeDePokemonContract.ALIASED_COL_ID,
        
        TypeDePokemonContract.ALIASED_COL_NOM,
        
        TypeDePokemonContract.ALIASED_COL_ATTAQUE,
        
        TypeDePokemonContract.ALIASED_COL_DEFENSE,
        
        TypeDePokemonContract.ALIASED_COL_PV,
        
        TypeDePokemonContract.ALIASED_COL_NUMPOKEDEX,
        
        TypeDePokemonContract.ALIASED_COL_URLIMAGE,
        
    };


    /**
     * Converts a TypeDePokemon into a content values.
     *
     * @param item The TypeDePokemon to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final TypeDePokemon item) {
        final ContentValues result = new ContentValues();

             result.put(TypeDePokemonContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(TypeDePokemonContract.COL_NOM,
                    item.getNom());
            }

             result.put(TypeDePokemonContract.COL_ATTAQUE,
                String.valueOf(item.getAttaque()));

             result.put(TypeDePokemonContract.COL_DEFENSE,
                String.valueOf(item.getDefense()));

             result.put(TypeDePokemonContract.COL_PV,
                String.valueOf(item.getPv()));

             result.put(TypeDePokemonContract.COL_NUMPOKEDEX,
                String.valueOf(item.getNumPokedex()));

             if (item.getUrlImage() != null) {
                result.put(TypeDePokemonContract.COL_URLIMAGE,
                    item.getUrlImage());
            } else {
                result.put(TypeDePokemonContract.COL_URLIMAGE, (String) null);
            }

 
        return result;
    }

    /**
     * Converts a Cursor into a TypeDePokemon.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted TypeDePokemon
     */
    public static TypeDePokemon cursorToItem(final android.database.Cursor cursor) {
        TypeDePokemon result = new TypeDePokemon();
        TypeDePokemonContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to TypeDePokemon entity.
     * @param cursor Cursor object
     * @param result TypeDePokemon entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final TypeDePokemon result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(TypeDePokemonContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(TypeDePokemonContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(TypeDePokemonContract.COL_ATTAQUE);

            if (index > -1) {
                result.setAttaque(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(TypeDePokemonContract.COL_DEFENSE);

            if (index > -1) {
                result.setDefense(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(TypeDePokemonContract.COL_PV);

            if (index > -1) {
                result.setPv(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(TypeDePokemonContract.COL_NUMPOKEDEX);

            if (index > -1) {
                result.setNumPokedex(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(TypeDePokemonContract.COL_URLIMAGE);

            if (index > -1) {
            if (!cursor.isNull(index)) {
                    result.setUrlImage(cursor.getString(index));
            }
            }

        }
    }

    /**
     * Convert Cursor of database to Array of TypeDePokemon entity.
     * @param cursor Cursor object
     * @return Array of TypeDePokemon entity
     */
    public static ArrayList<TypeDePokemon> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<TypeDePokemon> result = new ArrayList<TypeDePokemon>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            TypeDePokemon item;
            do {
                item = TypeDePokemonContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
