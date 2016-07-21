/**************************************************************************
 * PokemonContractBase.java, pokemon Android
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

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.entity.Attaque;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;



import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;
import com.maximeleau.harmony.android.pokemon.harmony.util.DateUtils;

/** Pokemon contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
public abstract class PokemonContractBase {


        /** id. */
    public static final String COL_ID =
            "id";
    /** Alias. */
    public static final String ALIASED_COL_ID =
            PokemonContract.TABLE_NAME + "." + COL_ID;

    /** surnom. */
    public static final String COL_SURNOM =
            "surnom";
    /** Alias. */
    public static final String ALIASED_COL_SURNOM =
            PokemonContract.TABLE_NAME + "." + COL_SURNOM;

    /** niveau. */
    public static final String COL_NIVEAU =
            "niveau";
    /** Alias. */
    public static final String ALIASED_COL_NIVEAU =
            PokemonContract.TABLE_NAME + "." + COL_NIVEAU;

    /** captureLe. */
    public static final String COL_CAPTURELE =
            "captureLe";
    /** Alias. */
    public static final String ALIASED_COL_CAPTURELE =
            PokemonContract.TABLE_NAME + "." + COL_CAPTURELE;

    /** attaque1_id. */
    public static final String COL_ATTAQUE1_ID =
            "attaque1_id";
    /** Alias. */
    public static final String ALIASED_COL_ATTAQUE1_ID =
            PokemonContract.TABLE_NAME + "." + COL_ATTAQUE1_ID;

    /** attaque2_id. */
    public static final String COL_ATTAQUE2_ID =
            "attaque2_id";
    /** Alias. */
    public static final String ALIASED_COL_ATTAQUE2_ID =
            PokemonContract.TABLE_NAME + "." + COL_ATTAQUE2_ID;

    /** attaque3_id. */
    public static final String COL_ATTAQUE3_ID =
            "attaque3_id";
    /** Alias. */
    public static final String ALIASED_COL_ATTAQUE3_ID =
            PokemonContract.TABLE_NAME + "." + COL_ATTAQUE3_ID;

    /** attaque4_id. */
    public static final String COL_ATTAQUE4_ID =
            "attaque4_id";
    /** Alias. */
    public static final String ALIASED_COL_ATTAQUE4_ID =
            PokemonContract.TABLE_NAME + "." + COL_ATTAQUE4_ID;

    /** typeDePokemon_id. */
    public static final String COL_TYPEDEPOKEMON_ID =
            "typeDePokemon_id";
    /** Alias. */
    public static final String ALIASED_COL_TYPEDEPOKEMON_ID =
            PokemonContract.TABLE_NAME + "." + COL_TYPEDEPOKEMON_ID;

    /** personnageNonJoueur_id. */
    public static final String COL_PERSONNAGENONJOUEUR_ID =
            "personnageNonJoueur_id";
    /** Alias. */
    public static final String ALIASED_COL_PERSONNAGENONJOUEUR_ID =
            PokemonContract.TABLE_NAME + "." + COL_PERSONNAGENONJOUEUR_ID;




    /** Constant for parcelisation/serialization. */
    public static final String PARCEL = "Pokemon";
    /** Table name of SQLite database. */
    public static final String TABLE_NAME = "Pokemon";
    /** Global Fields. */
    public static final String[] COLS = new String[] {

        
        PokemonContract.COL_ID,
        
        PokemonContract.COL_SURNOM,
        
        PokemonContract.COL_NIVEAU,
        
        PokemonContract.COL_CAPTURELE,
        
        PokemonContract.COL_ATTAQUE1_ID,
        
        PokemonContract.COL_ATTAQUE2_ID,
        
        PokemonContract.COL_ATTAQUE3_ID,
        
        PokemonContract.COL_ATTAQUE4_ID,
        
        PokemonContract.COL_TYPEDEPOKEMON_ID,
        
        PokemonContract.COL_PERSONNAGENONJOUEUR_ID
    };

    /** Global Fields. */
    public static final String[] ALIASED_COLS = new String[] {
        
        PokemonContract.ALIASED_COL_ID,
        
        PokemonContract.ALIASED_COL_SURNOM,
        
        PokemonContract.ALIASED_COL_NIVEAU,
        
        PokemonContract.ALIASED_COL_CAPTURELE,
        
        PokemonContract.ALIASED_COL_ATTAQUE1_ID,
        
        PokemonContract.ALIASED_COL_ATTAQUE2_ID,
        
        PokemonContract.ALIASED_COL_ATTAQUE3_ID,
        
        PokemonContract.ALIASED_COL_ATTAQUE4_ID,
        
        PokemonContract.ALIASED_COL_TYPEDEPOKEMON_ID,
        
        PokemonContract.ALIASED_COL_PERSONNAGENONJOUEUR_ID
    };


    /**
     * Converts a Pokemon into a content values.
     *
     * @param item The Pokemon to convert
     *
     * @return The content values
     */
    public static ContentValues itemToContentValues(final Pokemon item) {
        final ContentValues result = new ContentValues();

             result.put(PokemonContract.COL_ID,
                String.valueOf(item.getId()));

             if (item.getSurnom() != null) {
                result.put(PokemonContract.COL_SURNOM,
                    item.getSurnom());
            }

             result.put(PokemonContract.COL_NIVEAU,
                String.valueOf(item.getNiveau()));

             if (item.getCaptureLe() != null) {
                result.put(PokemonContract.COL_CAPTURELE,
                    item.getCaptureLe().toString(ISODateTimeFormat.dateTime()));
            }

             if (item.getAttaque1() != null) {
                result.put(PokemonContract.COL_ATTAQUE1_ID,
                    item.getAttaque1().getId());
            } else {
                result.put(PokemonContract.COL_ATTAQUE1_ID, (String) null);
            }

             if (item.getAttaque2() != null) {
                result.put(PokemonContract.COL_ATTAQUE2_ID,
                    item.getAttaque2().getId());
            } else {
                result.put(PokemonContract.COL_ATTAQUE2_ID, (String) null);
            }

             if (item.getAttaque3() != null) {
                result.put(PokemonContract.COL_ATTAQUE3_ID,
                    item.getAttaque3().getId());
            } else {
                result.put(PokemonContract.COL_ATTAQUE3_ID, (String) null);
            }

             if (item.getAttaque4() != null) {
                result.put(PokemonContract.COL_ATTAQUE4_ID,
                    item.getAttaque4().getId());
            } else {
                result.put(PokemonContract.COL_ATTAQUE4_ID, (String) null);
            }

             if (item.getTypeDePokemon() != null) {
                result.put(PokemonContract.COL_TYPEDEPOKEMON_ID,
                    item.getTypeDePokemon().getId());
            }

             if (item.getPersonnageNonJoueur() != null) {
                result.put(PokemonContract.COL_PERSONNAGENONJOUEUR_ID,
                    item.getPersonnageNonJoueur().getId());
            } else {
                result.put(PokemonContract.COL_PERSONNAGENONJOUEUR_ID, (String) null);
            }


        return result;
    }

    /**
     * Converts a Cursor into a Pokemon.
     *
     * @param cursor The cursor to convert
     *
     * @return The extracted Pokemon
     */
    public static Pokemon cursorToItem(final android.database.Cursor cursor) {
        Pokemon result = new Pokemon();
        PokemonContract.cursorToItem(cursor, result);
        return result;
    }

    /**
     * Convert Cursor of database to Pokemon entity.
     * @param cursor Cursor object
     * @param result Pokemon entity
     */
    public static void cursorToItem(final android.database.Cursor cursor, final Pokemon result) {
        if (cursor.getCount() != 0) {
            int index;

            index = cursor.getColumnIndex(PokemonContract.COL_ID);

            if (index > -1) {
                result.setId(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokemonContract.COL_SURNOM);

            if (index > -1) {
                result.setSurnom(cursor.getString(index));
            }
            index = cursor.getColumnIndex(PokemonContract.COL_NIVEAU);

            if (index > -1) {
                result.setNiveau(cursor.getInt(index));
            }
            index = cursor.getColumnIndex(PokemonContract.COL_CAPTURELE);

            if (index > -1) {
                final DateTime dtCaptureLe =
                        DateUtils.formatISOStringToDateTime(cursor.getString(index));
                if (dtCaptureLe != null) {
                        result.setCaptureLe(dtCaptureLe);
                } else {
                    result.setCaptureLe(new DateTime());
                }
            }
            if (result.getAttaque1() == null) {
                final Attaque attaque1 = new Attaque();
                index = cursor.getColumnIndex(PokemonContract.COL_ATTAQUE1_ID);

                if (index > -1) {
                    if (!cursor.isNull(index)) {
                        attaque1.setId(cursor.getInt(index));
                        result.setAttaque1(attaque1);
                    }
                }

            }
            if (result.getAttaque2() == null) {
                final Attaque attaque2 = new Attaque();
                index = cursor.getColumnIndex(PokemonContract.COL_ATTAQUE2_ID);

                if (index > -1) {
                    if (!cursor.isNull(index)) {
                        attaque2.setId(cursor.getInt(index));
                        result.setAttaque2(attaque2);
                    }
                }

            }
            if (result.getAttaque3() == null) {
                final Attaque attaque3 = new Attaque();
                index = cursor.getColumnIndex(PokemonContract.COL_ATTAQUE3_ID);

                if (index > -1) {
                    if (!cursor.isNull(index)) {
                        attaque3.setId(cursor.getInt(index));
                        result.setAttaque3(attaque3);
                    }
                }

            }
            if (result.getAttaque4() == null) {
                final Attaque attaque4 = new Attaque();
                index = cursor.getColumnIndex(PokemonContract.COL_ATTAQUE4_ID);

                if (index > -1) {
                    if (!cursor.isNull(index)) {
                        attaque4.setId(cursor.getInt(index));
                        result.setAttaque4(attaque4);
                    }
                }

            }
            if (result.getTypeDePokemon() == null) {
                final TypeDePokemon typeDePokemon = new TypeDePokemon();
                index = cursor.getColumnIndex(PokemonContract.COL_TYPEDEPOKEMON_ID);

                if (index > -1) {
                    typeDePokemon.setId(cursor.getInt(index));
                    result.setTypeDePokemon(typeDePokemon);
                }

            }
            if (result.getPersonnageNonJoueur() == null) {
                final PersonnageNonJoueur personnageNonJoueur = new PersonnageNonJoueur();
                index = cursor.getColumnIndex(PokemonContract.COL_PERSONNAGENONJOUEUR_ID);

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
     * Convert Cursor of database to Array of Pokemon entity.
     * @param cursor Cursor object
     * @return Array of Pokemon entity
     */
    public static ArrayList<Pokemon> cursorToItems(final android.database.Cursor cursor) {
        final ArrayList<Pokemon> result = new ArrayList<Pokemon>(cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            Pokemon item;
            do {
                item = PokemonContract.cursorToItem(cursor);
                result.add(item);
            } while (cursor.moveToNext());
        }

        return result;
    }
}
