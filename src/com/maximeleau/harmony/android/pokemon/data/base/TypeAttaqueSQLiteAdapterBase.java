
/**************************************************************************
 * TypeAttaqueSQLiteAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.data.base;

import java.util.ArrayList;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.maximeleau.harmony.android.pokemon.data.SQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.TypeAttaqueSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeAttaqueContract;
import com.maximeleau.harmony.android.pokemon.entity.TypeAttaque;


import com.maximeleau.harmony.android.pokemon.PokemonApplication;



/** TypeAttaque adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit TypeAttaqueAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class TypeAttaqueSQLiteAdapterBase
                        extends SQLiteAdapter<TypeAttaque> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypeAttaqueDBAdapter";


    /**
     * Get the table name used in DB for your TypeAttaque entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return TypeAttaqueContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your TypeAttaque entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = TypeAttaqueContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the TypeAttaque entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return TypeAttaqueContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + TypeAttaqueContract.TABLE_NAME    + " ("
        
         + TypeAttaqueContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + TypeAttaqueContract.COL_NOM    + " VARCHAR NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public TypeAttaqueSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert TypeAttaque entity to Content Values for database.
     * @param item TypeAttaque entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final TypeAttaque item) {
        return TypeAttaqueContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to TypeAttaque entity.
     * @param cursor android.database.Cursor object
     * @return TypeAttaque entity
     */
    public TypeAttaque cursorToItem(final android.database.Cursor cursor) {
        return TypeAttaqueContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to TypeAttaque entity.
     * @param cursor android.database.Cursor object
     * @param result TypeAttaque entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final TypeAttaque result) {
        TypeAttaqueContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read TypeAttaque by id in database.
     *
     * @param id Identify of TypeAttaque
     * @return TypeAttaque entity
     */
    public TypeAttaque getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final TypeAttaque result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }


    /**
     * Read All TypeAttaques entities.
     *
     * @return List of TypeAttaque entities
     */
    public ArrayList<TypeAttaque> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<TypeAttaque> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a TypeAttaque entity into database.
     *
     * @param item The TypeAttaque entity to persist
     * @return Id of the TypeAttaque entity
     */
    public long insert(final TypeAttaque item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + TypeAttaqueContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypeAttaqueContract.itemToContentValues(item);
        values.remove(TypeAttaqueContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    TypeAttaqueContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a TypeAttaque entity into database whether.
     * it already exists or not.
     *
     * @param item The TypeAttaque entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final TypeAttaque item) {
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
     * Update a TypeAttaque entity into database.
     *
     * @param item The TypeAttaque entity to persist
     * @return count of updated entities
     */
    public int update(final TypeAttaque item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + TypeAttaqueContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypeAttaqueContract.itemToContentValues(item);
        final String whereClause =
                 TypeAttaqueContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a TypeAttaque entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + TypeAttaqueContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                TypeAttaqueContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param typeAttaque The entity to delete
     * @return count of updated entities
     */
    public int delete(final TypeAttaque typeAttaque) {
        return this.remove(typeAttaque.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the TypeAttaque corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                TypeAttaqueContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                TypeAttaqueContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a TypeAttaque entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = TypeAttaqueContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                TypeAttaqueContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

