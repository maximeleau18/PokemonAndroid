/**************************************************************************
 * TypeDePokemonEvolutionContractBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonEvolution;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;



import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonEvolutionContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class TypeDePokemonEvolutionContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            TypeDePokemonEvolutionContract.TABLE_NAME + "." + COL_ID;

    /** evolueEn_id. */
    public static final String COL_EVOLUEEN_ID =
            "evolueEn_id";
    /** Alias. */
    public static final String ALIASED_COL_EVOLUEEN_ID =
            TypeDePokemonEvolutionContract.TABLE_NAME + "." + COL_EVOLUEEN_ID;

    /** estEvolueEn_id. */
    public static final String COL_ESTEVOLUEEN_ID =
            "estEvolueEn_id";
    /** Alias. */
    public static final String ALIASED_COL_ESTEVOLUEEN_ID =
            TypeDePokemonEvolutionContract.TABLE_NAME + "." + COL_ESTEVOLUEEN_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "TypeDePokemonEvolution";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "TypeDePokemonEvolution";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        TypeDePokemonEvolutionContract.COL_ID,
        
        TypeDePokemonEvolutionContract.COL_EVOLUEEN_ID,
        
        TypeDePokemonEvolutionContract.COL_ESTEVOLUEEN_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        TypeDePokemonEvolutionContract.ALIASED_COL_ID,
        
        TypeDePokemonEvolutionContract.ALIASED_COL_EVOLUEEN_ID,
        
        TypeDePokemonEvolutionContract.ALIASED_COL_ESTEVOLUEEN_ID
    };


    /**
     * Converts a TypeDePokemonEvolution into a content values.
     *
     * @param item The TypeDePokemonEvolution to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final TypeDePokemonEvolution item) {
        final ContentValues result = new ContentValues();

             result.put(TypeDePokemonEvolutionContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getEvolueEn() != null) {
                result.put(TypeDePokemonEvolutionContract.COL_EVOLUEEN_ID,
                    item.getEvolueEn().getId());
            }

             if (item.getEstEvolueEn() != null) {
                result.put(TypeDePokemonEvolutionContract.COL_ESTEVOLUEEN_ID,
                    item.getEstEvolueEn().getId());
            }


        return result;
    }

    /**
     * Converts a Cursor into a TypeDePokemonEvolution.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted TypeDePokemonEvolution
     */
    public static TypeDePokemonEvolution cursorToItem(final android.database.Cursor cursor) {
        TypeDePokemonEvolution result = new TypeDePokemonEvolution();
        TypeDePokemonEvolutionContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to TypeDePokemonEvolution entity.
     * @param cursor Cursor object
     * @param result TypeDePokemonEvolution entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final TypeDePokemonEvolution result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(TypeDePokemonEvolutionContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            if (result.getEvolueEn() == null) {
                final TypeDePokemon evolueEn = new TypeDePokemon();
                index = cursor.getColumnIndex(TypeDePokemonEvolutionContract.COL_EVOLUEEN_ID);

                if (index > -1) {
                    evolueEn.setId(cursor.getInt(index));
                    result.setEvolueEn(evolueEn);
                }

            }
            if (result.getEstEvolueEn() == null) {
                final TypeDePokemon estEvolueEn = new TypeDePokemon();
                index = cursor.getColumnIndex(TypeDePokemonEvolutionContract.COL_ESTEVOLUEEN_ID);

                if (index > -1) {
                    estEvolueEn.setId(cursor.getInt(index));
                    result.setEstEvolueEn(estEvolueEn);
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of TypeDePokemonEvolution entity.
     * @param cursor Cursor object
     * @return Array of TypeDePokemonEvolution entity
     */
    public static ArrayList<TypeDePokemonEvolution> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<TypeDePokemonEvolution> result = new ArrayList<TypeDePokemonEvolution>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            TypeDePokemonEvolution item;
            do {
                item = TypeDePokemonEvolutionContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
