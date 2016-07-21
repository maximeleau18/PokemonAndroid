/**************************************************************************
 * TypeDePokemonZoneContractBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonZone;
import com.maximeleau.harmony.android.pokemon.entity.Zone;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;



import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonZoneContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class TypeDePokemonZoneContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            TypeDePokemonZoneContract.TABLE_NAME + "." + COL_ID;

    /** zone_id. */
    public static final String COL_ZONE_ID =
            "zone_id";
    /** Alias. */
    public static final String ALIASED_COL_ZONE_ID =
            TypeDePokemonZoneContract.TABLE_NAME + "." + COL_ZONE_ID;

    /** typeDePokemon_id. */
    public static final String COL_TYPEDEPOKEMON_ID =
            "typeDePokemon_id";
    /** Alias. */
    public static final String ALIASED_COL_TYPEDEPOKEMON_ID =
            TypeDePokemonZoneContract.TABLE_NAME + "." + COL_TYPEDEPOKEMON_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "TypeDePokemonZone";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "TypeDePokemonZone";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        TypeDePokemonZoneContract.COL_ID,
        
        TypeDePokemonZoneContract.COL_ZONE_ID,
        
        TypeDePokemonZoneContract.COL_TYPEDEPOKEMON_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        TypeDePokemonZoneContract.ALIASED_COL_ID,
        
        TypeDePokemonZoneContract.ALIASED_COL_ZONE_ID,
        
        TypeDePokemonZoneContract.ALIASED_COL_TYPEDEPOKEMON_ID
    };


    /**
     * Converts a TypeDePokemonZone into a content values.
     *
     * @param item The TypeDePokemonZone to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final TypeDePokemonZone item) {
        final ContentValues result = new ContentValues();

             result.put(TypeDePokemonZoneContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getZone() != null) {
                result.put(TypeDePokemonZoneContract.COL_ZONE_ID,
                    item.getZone().getId());
            }

             if (item.getTypeDePokemon() != null) {
                result.put(TypeDePokemonZoneContract.COL_TYPEDEPOKEMON_ID,
                    item.getTypeDePokemon().getId());
            }


        return result;
    }

    /**
     * Converts a Cursor into a TypeDePokemonZone.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted TypeDePokemonZone
     */
    public static TypeDePokemonZone cursorToItem(final android.database.Cursor cursor) {
        TypeDePokemonZone result = new TypeDePokemonZone();
        TypeDePokemonZoneContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to TypeDePokemonZone entity.
     * @param cursor Cursor object
     * @param result TypeDePokemonZone entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final TypeDePokemonZone result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(TypeDePokemonZoneContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            if (result.getZone() == null) {
                final Zone zone = new Zone();
                index = cursor.getColumnIndex(TypeDePokemonZoneContract.COL_ZONE_ID);

                if (index > -1) {
                    zone.setId(cursor.getInt(index));
                    result.setZone(zone);
                }

            }
            if (result.getTypeDePokemon() == null) {
                final TypeDePokemon typeDePokemon = new TypeDePokemon();
                index = cursor.getColumnIndex(TypeDePokemonZoneContract.COL_TYPEDEPOKEMON_ID);

                if (index > -1) {
                    typeDePokemon.setId(cursor.getInt(index));
                    result.setTypeDePokemon(typeDePokemon);
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of TypeDePokemonZone entity.
     * @param cursor Cursor object
     * @return Array of TypeDePokemonZone entity
     */
    public static ArrayList<TypeDePokemonZone> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<TypeDePokemonZone> result = new ArrayList<TypeDePokemonZone>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            TypeDePokemonZone item;
            do {
                item = TypeDePokemonZoneContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
