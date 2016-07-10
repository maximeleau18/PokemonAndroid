/**************************************************************************
 * AreneContractBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.entity.Badge;
import com.maximeleau.harmony.android.pokemon.entity.Position;



import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class AreneContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            AreneContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            AreneContract.TABLE_NAME + "." + COL_NOM;

    /** maitre_id. */
    public static final String COL_MAITRE_ID =
            "maitre_id";
    /** Alias. */
    public static final String ALIASED_COL_MAITRE_ID =
            AreneContract.TABLE_NAME + "." + COL_MAITRE_ID;

    /** badge_id. */
    public static final String COL_BADGE_ID =
            "badge_id";
    /** Alias. */
    public static final String ALIASED_COL_BADGE_ID =
            AreneContract.TABLE_NAME + "." + COL_BADGE_ID;

    /** position_id. */
    public static final String COL_POSITION_ID =
            "position_id";
    /** Alias. */
    public static final String ALIASED_COL_POSITION_ID =
            AreneContract.TABLE_NAME + "." + COL_POSITION_ID;

    /** ZonearenesInternal_id. */
    public static final String COL_ZONEARENESINTERNAL_ID =
            "Zone_arenes_internal_id";
    /** Alias. */
    public static final String ALIASED_COL_ZONEARENESINTERNAL_ID =
            AreneContract.TABLE_NAME + "." + COL_ZONEARENESINTERNAL_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Arene";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Arene";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        AreneContract.COL_ID,
        
        AreneContract.COL_NOM,
        
        AreneContract.COL_MAITRE_ID,
        
        AreneContract.COL_BADGE_ID,
        
        AreneContract.COL_POSITION_ID,
        
        AreneContract.COL_ZONEARENESINTERNAL_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        AreneContract.ALIASED_COL_ID,
        
        AreneContract.ALIASED_COL_NOM,
        
        AreneContract.ALIASED_COL_MAITRE_ID,
        
        AreneContract.ALIASED_COL_BADGE_ID,
        
        AreneContract.ALIASED_COL_POSITION_ID,
        
        AreneContract.ALIASED_COL_ZONEARENESINTERNAL_ID
    };

    /** Convert Arene entity to Content Values for database.
     *
     * @param item Arene entity object
     * @param zoneId zone id
     * @return ContentValues object
     */
    public static ContentValues itemToContentValues(final Arene item,
                final int zonearenesInternalId) {
        final ContentValues result = AreneContract.itemToContentValues(item);
        result.put(AreneContract.COL_ZONEARENESINTERNAL_ID,
                String.valueOf(zonearenesInternalId));
        return result;
    }

    /**
     * Converts a Arene into a content values.
     *
     * @param item The Arene to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Arene item) {
        final ContentValues result = new ContentValues();

             result.put(AreneContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(AreneContract.COL_NOM,
                    item.getNom());
            }

             if (item.getMaitre() != null) {
                result.put(AreneContract.COL_MAITRE_ID,
                    item.getMaitre().getId());
            }

             if (item.getBadge() != null) {
                result.put(AreneContract.COL_BADGE_ID,
                    item.getBadge().getId());
            }

             if (item.getPosition() != null) {
                result.put(AreneContract.COL_POSITION_ID,
                    item.getPosition().getId());
            }

 
        return result;
    }

    /**
     * Converts a Cursor into a Arene.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Arene
     */
    public static Arene cursorToItem(final android.database.Cursor cursor) {
        Arene result = new Arene();
        AreneContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Arene entity.
     * @param cursor Cursor object
     * @param result Arene entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Arene result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(AreneContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(AreneContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            if (result.getMaitre() == null) {
                final PersonnageNonJoueur maitre = new PersonnageNonJoueur();
                index = cursor.getColumnIndex(AreneContract.COL_MAITRE_ID);

                if (index > -1) {
                    maitre.setId(cursor.getInt(index));
                    result.setMaitre(maitre);
                }

            }
            if (result.getBadge() == null) {
                final Badge badge = new Badge();
                index = cursor.getColumnIndex(AreneContract.COL_BADGE_ID);

                if (index > -1) {
                    badge.setId(cursor.getInt(index));
                    result.setBadge(badge);
                }

            }
            if (result.getPosition() == null) {
                final Position position = new Position();
                index = cursor.getColumnIndex(AreneContract.COL_POSITION_ID);

                if (index > -1) {
                    position.setId(cursor.getInt(index));
                    result.setPosition(position);
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of Arene entity.
     * @param cursor Cursor object
     * @return Array of Arene entity
     */
    public static ArrayList<Arene> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Arene> result = new ArrayList<Arene>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Arene item;
            do {
                item = AreneContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
