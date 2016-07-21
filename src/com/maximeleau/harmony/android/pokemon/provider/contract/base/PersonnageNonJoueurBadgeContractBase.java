/**************************************************************************
 * PersonnageNonJoueurBadgeContractBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueurBadge;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.entity.Badge;



import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurBadgeContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PersonnageNonJoueurBadgeContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PersonnageNonJoueurBadgeContract.TABLE_NAME + "." + COL_ID;

    /** personnageNonJoueur_id. */
    public static final String COL_PERSONNAGENONJOUEUR_ID =
            "personnageNonJoueur_id";
    /** Alias. */
    public static final String ALIASED_COL_PERSONNAGENONJOUEUR_ID =
            PersonnageNonJoueurBadgeContract.TABLE_NAME + "." + COL_PERSONNAGENONJOUEUR_ID;

    /** badge_id. */
    public static final String COL_BADGE_ID =
            "badge_id";
    /** Alias. */
    public static final String ALIASED_COL_BADGE_ID =
            PersonnageNonJoueurBadgeContract.TABLE_NAME + "." + COL_BADGE_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "PersonnageNonJoueurBadge";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "PersonnageNonJoueurBadge";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PersonnageNonJoueurBadgeContract.COL_ID,
        
        PersonnageNonJoueurBadgeContract.COL_PERSONNAGENONJOUEUR_ID,
        
        PersonnageNonJoueurBadgeContract.COL_BADGE_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PersonnageNonJoueurBadgeContract.ALIASED_COL_ID,
        
        PersonnageNonJoueurBadgeContract.ALIASED_COL_PERSONNAGENONJOUEUR_ID,
        
        PersonnageNonJoueurBadgeContract.ALIASED_COL_BADGE_ID
    };


    /**
     * Converts a PersonnageNonJoueurBadge into a content values.
     *
     * @param item The PersonnageNonJoueurBadge to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final PersonnageNonJoueurBadge item) {
        final ContentValues result = new ContentValues();

             result.put(PersonnageNonJoueurBadgeContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getPersonnageNonJoueur() != null) {
                result.put(PersonnageNonJoueurBadgeContract.COL_PERSONNAGENONJOUEUR_ID,
                    item.getPersonnageNonJoueur().getId());
            }

             if (item.getBadge() != null) {
                result.put(PersonnageNonJoueurBadgeContract.COL_BADGE_ID,
                    item.getBadge().getId());
            }


        return result;
    }

    /**
     * Converts a Cursor into a PersonnageNonJoueurBadge.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted PersonnageNonJoueurBadge
     */
    public static PersonnageNonJoueurBadge cursorToItem(final android.database.Cursor cursor) {
        PersonnageNonJoueurBadge result = new PersonnageNonJoueurBadge();
        PersonnageNonJoueurBadgeContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to PersonnageNonJoueurBadge entity.
     * @param cursor Cursor object
     * @param result PersonnageNonJoueurBadge entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final PersonnageNonJoueurBadge result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PersonnageNonJoueurBadgeContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            if (result.getPersonnageNonJoueur() == null) {
                final PersonnageNonJoueur personnageNonJoueur = new PersonnageNonJoueur();
                index = cursor.getColumnIndex(PersonnageNonJoueurBadgeContract.COL_PERSONNAGENONJOUEUR_ID);

                if (index > -1) {
                    personnageNonJoueur.setId(cursor.getInt(index));
                    result.setPersonnageNonJoueur(personnageNonJoueur);
                }

            }
            if (result.getBadge() == null) {
                final Badge badge = new Badge();
                index = cursor.getColumnIndex(PersonnageNonJoueurBadgeContract.COL_BADGE_ID);

                if (index > -1) {
                    badge.setId(cursor.getInt(index));
                    result.setBadge(badge);
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of PersonnageNonJoueurBadge entity.
     * @param cursor Cursor object
     * @return Array of PersonnageNonJoueurBadge entity
     */
    public static ArrayList<PersonnageNonJoueurBadge> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<PersonnageNonJoueurBadge> result = new ArrayList<PersonnageNonJoueurBadge>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            PersonnageNonJoueurBadge item;
            do {
                item = PersonnageNonJoueurBadgeContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
