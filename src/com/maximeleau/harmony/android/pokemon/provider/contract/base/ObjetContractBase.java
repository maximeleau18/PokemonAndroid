/**************************************************************************
 * ObjetContractBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.entity.Objet;
import com.maximeleau.harmony.android.pokemon.entity.TypeObjet;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;



import com.maximeleau.harmony.android.pokemon.provider.contract.ObjetContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class ObjetContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            ObjetContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            ObjetContract.TABLE_NAME + "." + COL_NOM;

    /** quantite. */
    public static final String COL_QUANTITE =
            "quantite";
    /** Alias. */
    public static final String ALIASED_COL_QUANTITE =
            ObjetContract.TABLE_NAME + "." + COL_QUANTITE;

    /** typeObjet_id. */
    public static final String COL_TYPEOBJET_ID =
            "typeObjet_id";
    /** Alias. */
    public static final String ALIASED_COL_TYPEOBJET_ID =
            ObjetContract.TABLE_NAME + "." + COL_TYPEOBJET_ID;

    /** personnageNonJoueur_id. */
    public static final String COL_PERSONNAGENONJOUEUR_ID =
            "personnageNonJoueur_id";
    /** Alias. */
    public static final String ALIASED_COL_PERSONNAGENONJOUEUR_ID =
            ObjetContract.TABLE_NAME + "." + COL_PERSONNAGENONJOUEUR_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Objet";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Objet";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        ObjetContract.COL_ID,
        
        ObjetContract.COL_NOM,
        
        ObjetContract.COL_QUANTITE,
        
        ObjetContract.COL_TYPEOBJET_ID,
        
        ObjetContract.COL_PERSONNAGENONJOUEUR_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        ObjetContract.ALIASED_COL_ID,
        
        ObjetContract.ALIASED_COL_NOM,
        
        ObjetContract.ALIASED_COL_QUANTITE,
        
        ObjetContract.ALIASED_COL_TYPEOBJET_ID,
        
        ObjetContract.ALIASED_COL_PERSONNAGENONJOUEUR_ID
    };


    /**
     * Converts a Objet into a content values.
     *
     * @param item The Objet to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Objet item) {
        final ContentValues result = new ContentValues();

             result.put(ObjetContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(ObjetContract.COL_NOM,
                    item.getNom());
            }

             result.put(ObjetContract.COL_QUANTITE,
                String.valueOf(item.getQuantite()));

             if (item.getTypeObjet() != null) {
                result.put(ObjetContract.COL_TYPEOBJET_ID,
                    item.getTypeObjet().getId());
            }

             if (item.getPersonnageNonJoueur() != null) {
                result.put(ObjetContract.COL_PERSONNAGENONJOUEUR_ID,
                    item.getPersonnageNonJoueur().getId());
            } else {
                result.put(ObjetContract.COL_PERSONNAGENONJOUEUR_ID, (String) null);
            }


        return result;
    }

    /**
     * Converts a Cursor into a Objet.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Objet
     */
    public static Objet cursorToItem(final android.database.Cursor cursor) {
        Objet result = new Objet();
        ObjetContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Objet entity.
     * @param cursor Cursor object
     * @param result Objet entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Objet result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(ObjetContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(ObjetContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(ObjetContract.COL_QUANTITE);

            if (index > -1) {
                result.setQuantite(cursor.getInt(index));
            }
            if (result.getTypeObjet() == null) {
                final TypeObjet typeObjet = new TypeObjet();
                index = cursor.getColumnIndex(ObjetContract.COL_TYPEOBJET_ID);

                if (index > -1) {
                    typeObjet.setId(cursor.getInt(index));
                    result.setTypeObjet(typeObjet);
                }

            }
            if (result.getPersonnageNonJoueur() == null) {
                final PersonnageNonJoueur personnageNonJoueur = new PersonnageNonJoueur();
                index = cursor.getColumnIndex(ObjetContract.COL_PERSONNAGENONJOUEUR_ID);

                if (index > -1) {
                    if (!cursor.isNull(index)) {
                        personnageNonJoueur.setId(cursor.getInt(index));
                        result.setPersonnageNonJoueur(personnageNonJoueur);
                    }
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of Objet entity.
     * @param cursor Cursor object
     * @return Array of Objet entity
     */
    public static ArrayList<Objet> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Objet> result = new ArrayList<Objet>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Objet item;
            do {
                item = ObjetContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
