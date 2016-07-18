
/**************************************************************************
 * AttaqueSQLiteAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 18, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.data.base;

import java.util.ArrayList;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.maximeleau.harmony.android.pokemon.data.SQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.AttaqueSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.TypeAttaqueSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.AttaqueContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeAttaqueContract;
import com.maximeleau.harmony.android.pokemon.entity.Attaque;
import com.maximeleau.harmony.android.pokemon.entity.TypeAttaque;


import com.maximeleau.harmony.android.pokemon.PokemonApplication;



/** Attaque adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit AttaqueAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class AttaqueSQLiteAdapterBase
                        extends SQLiteAdapter<Attaque> {

    /** TAG for debug purpose. */
    protected static final String TAG = "AttaqueDBAdapter";


    /**
     * Get the table name used in DB for your Attaque entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return AttaqueContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Attaque entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = AttaqueContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Attaque entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return AttaqueContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + AttaqueContract.TABLE_NAME    + " ("
        
         + AttaqueContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + AttaqueContract.COL_NOM    + " VARCHAR NOT NULL,"
         + AttaqueContract.COL_PUISSANCE    + " INTEGER NOT NULL,"
         + AttaqueContract.COL_DEGATS    + " INTEGER NOT NULL,"
         + AttaqueContract.COL_TYPEATTAQUE_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + AttaqueContract.COL_TYPEATTAQUE_ID + ") REFERENCES " 
             + TypeAttaqueContract.TABLE_NAME 
                + " (" + TypeAttaqueContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public AttaqueSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Attaque entity to Content Values for database.
     * @param item Attaque entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Attaque item) {
        return AttaqueContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Attaque entity.
     * @param cursor android.database.Cursor object
     * @return Attaque entity
     */
    public Attaque cursorToItem(final android.database.Cursor cursor) {
        return AttaqueContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Attaque entity.
     * @param cursor android.database.Cursor object
     * @param result Attaque entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Attaque result) {
        AttaqueContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Attaque by id in database.
     *
     * @param id Identify of Attaque
     * @return Attaque entity
     */
    public Attaque getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Attaque result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getTypeAttaque() != null) {
            final TypeAttaqueSQLiteAdapter typeAttaqueAdapter =
                    new TypeAttaqueSQLiteAdapter(this.ctx);
            typeAttaqueAdapter.open(this.mDatabase);

            result.setTypeAttaque(typeAttaqueAdapter.getByID(
                            result.getTypeAttaque().getId()));
        }
        return result;
    }

    /**
     * Find & read Attaque by typeAttaque.
     * @param typeattaqueId typeattaqueId
     * @param orderBy Order by string (can be null)
     * @return List of Attaque entities
     */
     public android.database.Cursor getByTypeAttaque(final int typeattaqueId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = AttaqueContract.COL_TYPEATTAQUE_ID + "= ?";
        String idSelectionArgs = String.valueOf(typeattaqueId);
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
     * Read All Attaques entities.
     *
     * @return List of Attaque entities
     */
    public ArrayList<Attaque> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Attaque> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Attaque entity into database.
     *
     * @param item The Attaque entity to persist
     * @return Id of the Attaque entity
     */
    public long insert(final Attaque item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + AttaqueContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                AttaqueContract.itemToContentValues(item);
        values.remove(AttaqueContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    AttaqueContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Attaque entity into database whether.
     * it already exists or not.
     *
     * @param item The Attaque entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Attaque item) {
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
     * Update a Attaque entity into database.
     *
     * @param item The Attaque entity to persist
     * @return count of updated entities
     */
    public int update(final Attaque item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + AttaqueContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                AttaqueContract.itemToContentValues(item);
        final String whereClause =
                 AttaqueContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Attaque entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + AttaqueContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                AttaqueContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param attaque The entity to delete
     * @return count of updated entities
     */
    public int delete(final Attaque attaque) {
        return this.remove(attaque.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Attaque corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                AttaqueContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                AttaqueContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Attaque entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = AttaqueContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                AttaqueContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

