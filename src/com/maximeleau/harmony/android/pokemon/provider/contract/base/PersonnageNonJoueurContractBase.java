/**************************************************************************
 * PersonnageNonJoueurContractBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.entity.Profession;
import com.maximeleau.harmony.android.pokemon.entity.Objet;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;



import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PersonnageNonJoueurContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PersonnageNonJoueurContract.TABLE_NAME + "." + COL_ID;

    /** nom. */
    public static final String COL_NOM =
            "nom";
    /** Alias. */
    public static final String ALIASED_COL_NOM =
            PersonnageNonJoueurContract.TABLE_NAME + "." + COL_NOM;

    /** description. */
    public static final String COL_DESCRIPTION =
            "description";
    /** Alias. */
    public static final String ALIASED_COL_DESCRIPTION =
            PersonnageNonJoueurContract.TABLE_NAME + "." + COL_DESCRIPTION;

    /** urlImage. */
    public static final String COL_URLIMAGE =
            "urlImage";
    /** Alias. */
    public static final String ALIASED_COL_URLIMAGE =
            PersonnageNonJoueurContract.TABLE_NAME + "." + COL_URLIMAGE;

    /** profession_id. */
    public static final String COL_PROFESSION_ID =
            "profession_id";
    /** Alias. */
    public static final String ALIASED_COL_PROFESSION_ID =
            PersonnageNonJoueurContract.TABLE_NAME + "." + COL_PROFESSION_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "PersonnageNonJoueur";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "PersonnageNonJoueur";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PersonnageNonJoueurContract.COL_ID,
        
        PersonnageNonJoueurContract.COL_NOM,
        
        PersonnageNonJoueurContract.COL_DESCRIPTION,
        
        PersonnageNonJoueurContract.COL_URLIMAGE,
        
        PersonnageNonJoueurContract.COL_PROFESSION_ID,
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PersonnageNonJoueurContract.ALIASED_COL_ID,
        
        PersonnageNonJoueurContract.ALIASED_COL_NOM,
        
        PersonnageNonJoueurContract.ALIASED_COL_DESCRIPTION,
        
        PersonnageNonJoueurContract.ALIASED_COL_URLIMAGE,
        
        PersonnageNonJoueurContract.ALIASED_COL_PROFESSION_ID,
        
        
        
        
    };


    /**
     * Converts a PersonnageNonJoueur into a content values.
     *
     * @param item The PersonnageNonJoueur to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final PersonnageNonJoueur item) {
        final ContentValues result = new ContentValues();

             result.put(PersonnageNonJoueurContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getNom() != null) {
                result.put(PersonnageNonJoueurContract.COL_NOM,
                    item.getNom());
            }

             if (item.getDescription() != null) {
                result.put(PersonnageNonJoueurContract.COL_DESCRIPTION,
                    item.getDescription());
            }

             if (item.getUrlImage() != null) {
                result.put(PersonnageNonJoueurContract.COL_URLIMAGE,
                    item.getUrlImage());
            } else {
                result.put(PersonnageNonJoueurContract.COL_URLIMAGE, (String) null);
            }

             if (item.getProfession() != null) {
                result.put(PersonnageNonJoueurContract.COL_PROFESSION_ID,
                    item.getProfession().getId());
            }

    
        return result;
    }

    /**
     * Converts a Cursor into a PersonnageNonJoueur.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted PersonnageNonJoueur
     */
    public static PersonnageNonJoueur cursorToItem(final android.database.Cursor cursor) {
        PersonnageNonJoueur result = new PersonnageNonJoueur();
        PersonnageNonJoueurContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to PersonnageNonJoueur entity.
     * @param cursor Cursor object
     * @param result PersonnageNonJoueur entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final PersonnageNonJoueur result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PersonnageNonJoueurContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PersonnageNonJoueurContract.COL_NOM);

            if (index > -1) {
                result.setNom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(PersonnageNonJoueurContract.COL_DESCRIPTION);

            if (index > -1) {
                result.setDescription(cursor.getString(index));
            }
            index = cursor.getColumnIndex(PersonnageNonJoueurContract.COL_URLIMAGE);

            if (index > -1) {
            if (!cursor.isNull(index)) {
                    result.setUrlImage(cursor.getString(index));
            }
            }
            if (result.getProfession() == null) {
                final Profession profession = new Profession();
                index = cursor.getColumnIndex(PersonnageNonJoueurContract.COL_PROFESSION_ID);

                if (index > -1) {
                    profession.setId(cursor.getInt(index));
                    result.setProfession(profession);
                }

            }

        }
    }

    /**
     * Convert Cursor of database to Array of PersonnageNonJoueur entity.
     * @param cursor Cursor object
     * @return Array of PersonnageNonJoueur entity
     */
    public static ArrayList<PersonnageNonJoueur> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<PersonnageNonJoueur> result = new ArrayList<PersonnageNonJoueur>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            PersonnageNonJoueur item;
            do {
                item = PersonnageNonJoueurContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
