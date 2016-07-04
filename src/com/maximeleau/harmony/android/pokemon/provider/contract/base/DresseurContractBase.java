/**************************************************************************
 * DresseurContractBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.provider.contract.base;

import android.content.ContentValues;


import java.util.ArrayList;

import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;



import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class DresseurContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            DresseurContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            DresseurContract.TABLE_NAME + "." + COL_NOM;

    /** prenom. */
    public static final String COL_PRENOM =
            "prenom";
    /** Alias. */
    public static final String ALIASED_COL_PRENOM =
            DresseurContract.TABLE_NAME + "." + COL_PRENOM;

    /** login. */
    public static final String COL_LOGIN =
            "login";
    /** Alias. */
    public static final String ALIASED_COL_LOGIN =
            DresseurContract.TABLE_NAME + "." + COL_LOGIN;

    /** motDePasse. */
    public static final String COL_MOTDEPASSE =
            "motDePasse";
    /** Alias. */
    public static final String ALIASED_COL_MOTDEPASSE =
            DresseurContract.TABLE_NAME + "." + COL_MOTDEPASSE;

    /** personnageNonJoueur_id. */
    public static final String COL_PERSONNAGENONJOUEUR_ID =
            "personnageNonJoueur_id";
    /** Alias. */
    public static final String ALIASED_COL_PERSONNAGENONJOUEUR_ID =
            DresseurContract.TABLE_NAME + "." + COL_PERSONNAGENONJOUEUR_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Dresseur";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Dresseur";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        DresseurContract.COL_ID,
        
        DresseurContract.COL_NOM,
        
        DresseurContract.COL_PRENOM,
        
        DresseurContract.COL_LOGIN,
        
        DresseurContract.COL_MOTDEPASSE,
        
        DresseurContract.COL_PERSONNAGENONJOUEUR_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        DresseurContract.ALIASED_COL_ID,
        
        DresseurContract.ALIASED_COL_NOM,
        
        DresseurContract.ALIASED_COL_PRENOM,
        
        DresseurContract.ALIASED_COL_LOGIN,
        
        DresseurContract.ALIASED_COL_MOTDEPASSE,
        
        DresseurContract.ALIASED_COL_PERSONNAGENONJOUEUR_ID
    };


    /**
     * Converts a Dresseur into a content values.
     *
     * @param item The Dresseur to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Dresseur item) {
        final ContentValues result = new ContentValues();

             result.put(DresseurContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(DresseurContract.COL_NOM,
                    item.getNom());
            }

             if (item.getPrenom() != null) {
                result.put(DresseurContract.COL_PRENOM,
                    item.getPrenom());
            }

             if (item.getLogin() != null) {
                result.put(DresseurContract.COL_LOGIN,
                    item.getLogin());
            }

             if (item.getMotDePasse() != null) {
                result.put(DresseurContract.COL_MOTDEPASSE,
                    item.getMotDePasse());
            }

             if (item.getPersonnageNonJoueur() != null) {
                result.put(DresseurContract.COL_PERSONNAGENONJOUEUR_ID,
                    item.getPersonnageNonJoueur().getId());
            }


        return result;
    }

    /**
     * Converts a Cursor into a Dresseur.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Dresseur
     */
    public static Dresseur cursorToItem(final android.database.Cursor cursor) {
        Dresseur result = new Dresseur();
        DresseurContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Dresseur entity.
     * @param cursor Cursor object
     * @param result Dresseur entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Dresseur result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(DresseurContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(DresseurContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(DresseurContract.COL_PRENOM);

            if (index > -1) {
                result.setPrenom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(DresseurContract.COL_LOGIN);

            if (index > -1) {
                result.setLogin(cursor.getString(index));
            }
            index = cursor.getColumnIndex(DresseurContract.COL_MOTDEPASSE);

            if (index > -1) {
                result.setMotDePasse(cursor.getString(index));
            }
            if (result.getPersonnageNonJoueur() == null) {
                final PersonnageNonJoueur personnageNonJoueur = new PersonnageNonJoueur();
                index = cursor.getColumnIndex(DresseurContract.COL_PERSONNAGENONJOUEUR_ID);

                if (index > -1) {
                    personnageNonJoueur.setId(cursor.getInt(index));
                    result.setPersonnageNonJoueur(personnageNonJoueur);
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of Dresseur entity.
     * @param cursor Cursor object
     * @return Array of Dresseur entity
     */
    public static ArrayList<Dresseur> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Dresseur> result = new ArrayList<Dresseur>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Dresseur item;
            do {
                item = DresseurContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
