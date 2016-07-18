/**************************************************************************
 * CombatContractBase.java, pokemon Android
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

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import com.maximeleau.harmony.android.pokemon.entity.Combat;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;



import com.maximeleau.harmony.android.pokemon.provider.contract.CombatContract;
import com.maximeleau.harmony.android.pokemon.harmony.util.DateUtils;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class CombatContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            CombatContract.TABLE_NAME + "." + COL_ID;

    /** lanceLe. */
    public static final String COL_LANCELE =
            "lanceLe";
    /** Alias. */
    public static final String ALIASED_COL_LANCELE =
            CombatContract.TABLE_NAME + "." + COL_LANCELE;

    /** duree. */
    public static final String COL_DUREE =
            "duree";
    /** Alias. */
    public static final String ALIASED_COL_DUREE =
            CombatContract.TABLE_NAME + "." + COL_DUREE;

    /** dresseur1Vainqueur. */
    public static final String COL_DRESSEUR1VAINQUEUR =
            "dresseur1Vainqueur";
    /** Alias. */
    public static final String ALIASED_COL_DRESSEUR1VAINQUEUR =
            CombatContract.TABLE_NAME + "." + COL_DRESSEUR1VAINQUEUR;

    /** dresseur2Vainqueur. */
    public static final String COL_DRESSEUR2VAINQUEUR =
            "dresseur2Vainqueur";
    /** Alias. */
    public static final String ALIASED_COL_DRESSEUR2VAINQUEUR =
            CombatContract.TABLE_NAME + "." + COL_DRESSEUR2VAINQUEUR;

    /** pokemon1Vainqueur. */
    public static final String COL_POKEMON1VAINQUEUR =
            "pokemon1Vainqueur";
    /** Alias. */
    public static final String ALIASED_COL_POKEMON1VAINQUEUR =
            CombatContract.TABLE_NAME + "." + COL_POKEMON1VAINQUEUR;

    /** pokemon2Vainqueur. */
    public static final String COL_POKEMON2VAINQUEUR =
            "pokemon2Vainqueur";
    /** Alias. */
    public static final String ALIASED_COL_POKEMON2VAINQUEUR =
            CombatContract.TABLE_NAME + "." + COL_POKEMON2VAINQUEUR;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Combat";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Combat";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        CombatContract.COL_ID,
        
        CombatContract.COL_LANCELE,
        
        CombatContract.COL_DUREE,
        
        CombatContract.COL_DRESSEUR1VAINQUEUR,
        
        CombatContract.COL_DRESSEUR2VAINQUEUR,
        
        CombatContract.COL_POKEMON1VAINQUEUR,
        
        CombatContract.COL_POKEMON2VAINQUEUR
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        CombatContract.ALIASED_COL_ID,
        
        CombatContract.ALIASED_COL_LANCELE,
        
        CombatContract.ALIASED_COL_DUREE,
        
        
        
        
        
        CombatContract.ALIASED_COL_DRESSEUR1VAINQUEUR,
        
        CombatContract.ALIASED_COL_DRESSEUR2VAINQUEUR,
        
        CombatContract.ALIASED_COL_POKEMON1VAINQUEUR,
        
        CombatContract.ALIASED_COL_POKEMON2VAINQUEUR
    };


    /**
     * Converts a Combat into a content values.
     *
     * @param item The Combat to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Combat item) {
        final ContentValues result = new ContentValues();

             result.put(CombatContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getLanceLe() != null) {
                result.put(CombatContract.COL_LANCELE,
                    item.getLanceLe().toString(ISODateTimeFormat.dateTime()));
            }

             result.put(CombatContract.COL_DUREE,
                String.valueOf(item.getDuree()));

                 result.put(CombatContract.COL_DRESSEUR1VAINQUEUR,
                item.isDresseur1Vainqueur() ? 1 : 0);

             result.put(CombatContract.COL_DRESSEUR2VAINQUEUR,
                item.isDresseur2Vainqueur() ? 1 : 0);

             result.put(CombatContract.COL_POKEMON1VAINQUEUR,
                item.isPokemon1Vainqueur() ? 1 : 0);

             result.put(CombatContract.COL_POKEMON2VAINQUEUR,
                item.isPokemon2Vainqueur() ? 1 : 0);


        return result;
    }

    /**
     * Converts a Cursor into a Combat.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Combat
     */
    public static Combat cursorToItem(final android.database.Cursor cursor) {
        Combat result = new Combat();
        CombatContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Combat entity.
     * @param cursor Cursor object
     * @param result Combat entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Combat result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(CombatContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(CombatContract.COL_LANCELE);

            if (index > -1) {
                final DateTime dtLanceLe =
                        DateUtils.formatISOStringToDateTime(cursor.getString(index));
                if (dtLanceLe != null) {
                        result.setLanceLe(dtLanceLe);
                } else {
                    result.setLanceLe(new DateTime());
                }
            }
            index = cursor.getColumnIndex(CombatContract.COL_DUREE);

            if (index > -1) {
            if (!cursor.isNull(index)) {
                    result.setDuree(cursor.getInt(index));
            }
            }
            index = cursor.getColumnIndex(CombatContract.COL_DRESSEUR1VAINQUEUR);

            if (index > -1) {
                result.setDresseur1Vainqueur(cursor.getInt(index) == 1);
            }
            index = cursor.getColumnIndex(CombatContract.COL_DRESSEUR2VAINQUEUR);

            if (index > -1) {
                result.setDresseur2Vainqueur(cursor.getInt(index) == 1);
            }
            index = cursor.getColumnIndex(CombatContract.COL_POKEMON1VAINQUEUR);

            if (index > -1) {
                result.setPokemon1Vainqueur(cursor.getInt(index) == 1);
            }
            index = cursor.getColumnIndex(CombatContract.COL_POKEMON2VAINQUEUR);

            if (index > -1) {
                result.setPokemon2Vainqueur(cursor.getInt(index) == 1);
            }

        }
    }

    /**
     * Convert Cursor of database to Array of Combat entity.
     * @param cursor Cursor object
     * @return Array of Combat entity
     */
    public static ArrayList<Combat> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Combat> result = new ArrayList<Combat>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Combat item;
            do {
                item = CombatContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
