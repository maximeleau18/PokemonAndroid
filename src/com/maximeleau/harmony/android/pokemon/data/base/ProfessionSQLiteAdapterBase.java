
/**************************************************************************
 * ProfessionSQLiteAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.data.base;

import java.util.ArrayList;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.maximeleau.harmony.android.pokemon.data.SQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.ProfessionSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.ProfessionContract;
import com.maximeleau.harmony.android.pokemon.entity.Profession;


import com.maximeleau.harmony.android.pokemon.PokemonApplication;



/** Profession adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ProfessionAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ProfessionSQLiteAdapterBase
                        extends SQLiteAdapter<Profession> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ProfessionDBAdapter";


    /**
     * Get the table name used in DB for your Profession entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return ProfessionContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Profession entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = ProfessionContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Profession entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return ProfessionContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + ProfessionContract.TABLE_NAME    + " ("
        
         + ProfessionContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + ProfessionContract.COL_NOM    + " VARCHAR NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public ProfessionSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Profession entity to Content Values for database.
     * @param item Profession entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Profession item) {
        return ProfessionContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Profession entity.
     * @param cursor android.database.Cursor object
     * @return Profession entity
     */
    public Profession cursorToItem(final android.database.Cursor cursor) {
        return ProfessionContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Profession entity.
     * @param cursor android.database.Cursor object
     * @param result Profession entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Profession result) {
        ProfessionContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Profession by id in database.
     *
     * @param id Identify of Profession
     * @return Profession entity
     */
    public Profession getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Profession result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }


    /**
     * Read All Professions entities.
     *
     * @return List of Profession entities
     */
    public ArrayList<Profession> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Profession> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Profession entity into database.
     *
     * @param item The Profession entity to persist
     * @return Id of the Profession entity
     */
    public long insert(final Profession item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + ProfessionContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ProfessionContract.itemToContentValues(item);
        values.remove(ProfessionContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    ProfessionContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Profession entity into database whether.
     * it already exists or not.
     *
     * @param item The Profession entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Profession item) {
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
     * Update a Profession entity into database.
     *
     * @param item The Profession entity to persist
     * @return count of updated entities
     */
    public int update(final Profession item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + ProfessionContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ProfessionContract.itemToContentValues(item);
        final String whereClause =
                 ProfessionContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Profession entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + ProfessionContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                ProfessionContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param profession The entity to delete
     * @return count of updated entities
     */
    public int delete(final Profession profession) {
        return this.remove(profession.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Profession corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                ProfessionContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                ProfessionContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Profession entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = ProfessionContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                ProfessionContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

