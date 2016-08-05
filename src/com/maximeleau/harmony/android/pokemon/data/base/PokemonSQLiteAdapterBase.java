
/**************************************************************************
 * PokemonSQLiteAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Aug 5, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.data.base;

import java.util.ArrayList;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.maximeleau.harmony.android.pokemon.data.SQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PokemonSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.AttaqueSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.AttaqueContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.entity.Attaque;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;

import com.maximeleau.harmony.android.pokemon.harmony.util.DateUtils;
import com.maximeleau.harmony.android.pokemon.PokemonApplication;



/** Pokemon adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PokemonAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PokemonSQLiteAdapterBase
                        extends SQLiteAdapter<Pokemon> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PokemonDBAdapter";


    /**
     * Get the table name used in DB for your Pokemon entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PokemonContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Pokemon entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PokemonContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Pokemon entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PokemonContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PokemonContract.TABLE_NAME    + " ("
        
         + PokemonContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PokemonContract.COL_SURNOM    + " VARCHAR NOT NULL,"
         + PokemonContract.COL_NIVEAU    + " INTEGER NOT NULL,"
         + PokemonContract.COL_CAPTURELE    + " DATETIME NOT NULL,"
         + PokemonContract.COL_ATTAQUE1_ID    + " INTEGER,"
         + PokemonContract.COL_ATTAQUE2_ID    + " INTEGER,"
         + PokemonContract.COL_ATTAQUE3_ID    + " INTEGER,"
         + PokemonContract.COL_ATTAQUE4_ID    + " INTEGER,"
         + PokemonContract.COL_TYPEDEPOKEMON_ID    + " INTEGER NOT NULL,"
         + PokemonContract.COL_PERSONNAGENONJOUEUR_ID    + " INTEGER,"

        
         + "FOREIGN KEY(" + PokemonContract.COL_ATTAQUE1_ID + ") REFERENCES " 
             + AttaqueContract.TABLE_NAME 
                + " (" + AttaqueContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokemonContract.COL_ATTAQUE2_ID + ") REFERENCES " 
             + AttaqueContract.TABLE_NAME 
                + " (" + AttaqueContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokemonContract.COL_ATTAQUE3_ID + ") REFERENCES " 
             + AttaqueContract.TABLE_NAME 
                + " (" + AttaqueContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokemonContract.COL_ATTAQUE4_ID + ") REFERENCES " 
             + AttaqueContract.TABLE_NAME 
                + " (" + AttaqueContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokemonContract.COL_TYPEDEPOKEMON_ID + ") REFERENCES " 
             + TypeDePokemonContract.TABLE_NAME 
                + " (" + TypeDePokemonContract.COL_ID + "),"
         + "FOREIGN KEY(" + PokemonContract.COL_PERSONNAGENONJOUEUR_ID + ") REFERENCES " 
             + PersonnageNonJoueurContract.TABLE_NAME 
                + " (" + PersonnageNonJoueurContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PokemonSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Pokemon entity to Content Values for database.
     * @param item Pokemon entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Pokemon item) {
        return PokemonContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Pokemon entity.
     * @param cursor android.database.Cursor object
     * @return Pokemon entity
     */
    public Pokemon cursorToItem(final android.database.Cursor cursor) {
        return PokemonContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Pokemon entity.
     * @param cursor android.database.Cursor object
     * @param result Pokemon entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Pokemon result) {
        PokemonContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Pokemon by id in database.
     *
     * @param id Identify of Pokemon
     * @return Pokemon entity
     */
    public Pokemon getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Pokemon result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getAttaque1() != null) {
            final AttaqueSQLiteAdapter attaque1Adapter =
                    new AttaqueSQLiteAdapter(this.ctx);
            attaque1Adapter.open(this.mDatabase);

            result.setAttaque1(attaque1Adapter.getByID(
                            result.getAttaque1().getId()));
        }
        if (result.getAttaque2() != null) {
            final AttaqueSQLiteAdapter attaque2Adapter =
                    new AttaqueSQLiteAdapter(this.ctx);
            attaque2Adapter.open(this.mDatabase);

            result.setAttaque2(attaque2Adapter.getByID(
                            result.getAttaque2().getId()));
        }
        if (result.getAttaque3() != null) {
            final AttaqueSQLiteAdapter attaque3Adapter =
                    new AttaqueSQLiteAdapter(this.ctx);
            attaque3Adapter.open(this.mDatabase);

            result.setAttaque3(attaque3Adapter.getByID(
                            result.getAttaque3().getId()));
        }
        if (result.getAttaque4() != null) {
            final AttaqueSQLiteAdapter attaque4Adapter =
                    new AttaqueSQLiteAdapter(this.ctx);
            attaque4Adapter.open(this.mDatabase);

            result.setAttaque4(attaque4Adapter.getByID(
                            result.getAttaque4().getId()));
        }
        if (result.getTypeDePokemon() != null) {
            final TypeDePokemonSQLiteAdapter typeDePokemonAdapter =
                    new TypeDePokemonSQLiteAdapter(this.ctx);
            typeDePokemonAdapter.open(this.mDatabase);

            result.setTypeDePokemon(typeDePokemonAdapter.getByID(
                            result.getTypeDePokemon().getId()));
        }
        if (result.getPersonnageNonJoueur() != null) {
            final PersonnageNonJoueurSQLiteAdapter personnageNonJoueurAdapter =
                    new PersonnageNonJoueurSQLiteAdapter(this.ctx);
            personnageNonJoueurAdapter.open(this.mDatabase);

            result.setPersonnageNonJoueur(personnageNonJoueurAdapter.getByID(
                            result.getPersonnageNonJoueur().getId()));
        }
        return result;
    }

    /**
     * Find & read Pokemon by attaque1.
     * @param attaque1Id attaque1Id
     * @param orderBy Order by string (can be null)
     * @return List of Pokemon entities
     */
     public android.database.Cursor getByAttaque1(final int attaque1Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokemonContract.COL_ATTAQUE1_ID + "= ?";
        String idSelectionArgs = String.valueOf(attaque1Id);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }
    /**
     * Find & read Pokemon by attaque2.
     * @param attaque2Id attaque2Id
     * @param orderBy Order by string (can be null)
     * @return List of Pokemon entities
     */
     public android.database.Cursor getByAttaque2(final int attaque2Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokemonContract.COL_ATTAQUE2_ID + "= ?";
        String idSelectionArgs = String.valueOf(attaque2Id);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }
    /**
     * Find & read Pokemon by attaque3.
     * @param attaque3Id attaque3Id
     * @param orderBy Order by string (can be null)
     * @return List of Pokemon entities
     */
     public android.database.Cursor getByAttaque3(final int attaque3Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokemonContract.COL_ATTAQUE3_ID + "= ?";
        String idSelectionArgs = String.valueOf(attaque3Id);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }
    /**
     * Find & read Pokemon by attaque4.
     * @param attaque4Id attaque4Id
     * @param orderBy Order by string (can be null)
     * @return List of Pokemon entities
     */
     public android.database.Cursor getByAttaque4(final int attaque4Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokemonContract.COL_ATTAQUE4_ID + "= ?";
        String idSelectionArgs = String.valueOf(attaque4Id);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }
    /**
     * Find & read Pokemon by typeDePokemon.
     * @param typedepokemonId typedepokemonId
     * @param orderBy Order by string (can be null)
     * @return List of Pokemon entities
     */
     public android.database.Cursor getByTypeDePokemon(final int typedepokemonId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokemonContract.COL_TYPEDEPOKEMON_ID + "= ?";
        String idSelectionArgs = String.valueOf(typedepokemonId);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }
    /**
     * Find & read Pokemon by personnageNonJoueur.
     * @param personnagenonjoueurId personnagenonjoueurId
     * @param orderBy Order by string (can be null)
     * @return List of Pokemon entities
     */
     public android.database.Cursor getByPersonnageNonJoueur(final int personnagenonjoueurId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PokemonContract.COL_PERSONNAGENONJOUEUR_ID + "= ?";
        String idSelectionArgs = String.valueOf(personnagenonjoueurId);
        if (!Strings.isNullOrEmpty(selection)) {
            selection += " AND " + idSelection;
            selectionArgs = ObjectArrays.concat(selectionArgs, idSelectionArgs);
        } else {
            selection = idSelection;
            selectionArgs = new String[]{idSelectionArgs};
        }
        final android.database.Cursor cursor = this.query(
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy);

        return cursor;
     }

    /**
     * Read All Pokemons entities.
     *
     * @return List of Pokemon entities
     */
    public ArrayList<Pokemon> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Pokemon> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Pokemon entity into database.
     *
     * @param item The Pokemon entity to persist
     * @return Id of the Pokemon entity
     */
    public long insert(final Pokemon item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PokemonContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokemonContract.itemToContentValues(item);
        values.remove(PokemonContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PokemonContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Pokemon entity into database whether.
     * it already exists or not.
     *
     * @param item The Pokemon entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Pokemon item) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.update(item);
        } else {
            // Item doesn't exist => create it
            final long id = this.insert(item);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }

    /**
     * Update a Pokemon entity into database.
     *
     * @param item The Pokemon entity to persist
     * @return count of updated entities
     */
    public int update(final Pokemon item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PokemonContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PokemonContract.itemToContentValues(item);
        final String whereClause =
                 PokemonContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Pokemon entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PokemonContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PokemonContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param pokemon The entity to delete
     * @return count of updated entities
     */
    public int delete(final Pokemon pokemon) {
        return this.remove(pokemon.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Pokemon corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PokemonContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PokemonContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Pokemon entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PokemonContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PokemonContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

