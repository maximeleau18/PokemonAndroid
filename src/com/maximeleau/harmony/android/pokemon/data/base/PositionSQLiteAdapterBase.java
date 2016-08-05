
/**************************************************************************
 * PositionSQLiteAdapterBase.java, pokemon Android
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
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.maximeleau.harmony.android.pokemon.data.SQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PositionSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.ZoneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.PositionContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;
import com.maximeleau.harmony.android.pokemon.entity.Position;
import com.maximeleau.harmony.android.pokemon.entity.Zone;


import com.maximeleau.harmony.android.pokemon.PokemonApplication;



/** Position adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PositionAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PositionSQLiteAdapterBase
                        extends SQLiteAdapter<Position> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PositionDBAdapter";


    /**
     * Get the table name used in DB for your Position entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PositionContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Position entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PositionContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Position entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PositionContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PositionContract.TABLE_NAME    + " ("
        
         + PositionContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PositionContract.COL_X    + " INTEGER NOT NULL,"
         + PositionContract.COL_Y    + " INTEGER NOT NULL,"
         + PositionContract.COL_ZONE_ID    + " INTEGER NOT NULL,"
         + PositionContract.COL_ZONEPOSITIONSINTERNAL_ID    + " INTEGER,"

        
         + "FOREIGN KEY(" + PositionContract.COL_ZONE_ID + ") REFERENCES " 
             + ZoneContract.TABLE_NAME 
                + " (" + ZoneContract.COL_ID + "),"
         + "FOREIGN KEY(" + PositionContract.COL_ZONEPOSITIONSINTERNAL_ID + ") REFERENCES " 
             + ZoneContract.TABLE_NAME 
                + " (" + ZoneContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PositionSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Position entity to Content Values for database.
     * @param item Position entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Position item) {
        return PositionContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Position entity.
     * @param cursor android.database.Cursor object
     * @return Position entity
     */
    public Position cursorToItem(final android.database.Cursor cursor) {
        return PositionContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Position entity.
     * @param cursor android.database.Cursor object
     * @param result Position entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Position result) {
        PositionContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Position by id in database.
     *
     * @param id Identify of Position
     * @return Position entity
     */
    public Position getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Position result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getZone() != null) {
            final ZoneSQLiteAdapter zoneAdapter =
                    new ZoneSQLiteAdapter(this.ctx);
            zoneAdapter.open(this.mDatabase);

            result.setZone(zoneAdapter.getByID(
                            result.getZone().getId()));
        }
        return result;
    }

    /**
     * Find & read Position by zone.
     * @param zoneId zoneId
     * @param orderBy Order by string (can be null)
     * @return List of Position entities
     */
     public android.database.Cursor getByZone(final int zoneId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PositionContract.COL_ZONE_ID + "= ?";
        String idSelectionArgs = String.valueOf(zoneId);
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
     * Find & read Position by ZonepositionsInternal.
     * @param zonepositionsinternalId zonepositionsinternalId
     * @param orderBy Order by string (can be null)
     * @return List of Position entities
     */
     public android.database.Cursor getByZonepositionsInternal(final int zonepositionsinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PositionContract.COL_ZONEPOSITIONSINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(zonepositionsinternalId);
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
     * Read All Positions entities.
     *
     * @return List of Position entities
     */
    public ArrayList<Position> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Position> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Position entity into database.
     *
     * @param item The Position entity to persist
     * @return Id of the Position entity
     */
    public long insert(final Position item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PositionContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PositionContract.itemToContentValues(item, 0);
        values.remove(PositionContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PositionContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Position entity into database whether.
     * it already exists or not.
     *
     * @param item The Position entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Position item) {
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
     * Update a Position entity into database.
     *
     * @param item The Position entity to persist
     * @return count of updated entities
     */
    public int update(final Position item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PositionContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PositionContract.itemToContentValues(item, 0);
        final String whereClause =
                 PositionContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Update a Position entity into database.
     *
     * @param item The Position entity to persist
     * @param zoneId The zone id
     * @return count of updated entities
     */
    public int updateWithZonePositions(
                    Position item,
                    int zonepositionsInternalId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PositionContract.TABLE_NAME + ")");
        }

        ContentValues values =
                PositionContract.itemToContentValues(item);
        values.put(
                PositionContract.COL_ZONEPOSITIONSINTERNAL_ID,
                zonepositionsInternalId);
        String whereClause =
                 PositionContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a Position entity into database whether.
     * it already exists or not.
     *
     * @param item The Position entity to persist
     * @param zoneId The zone id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithZonePositions(
            Position item, int zoneId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithZonePositions(item,
                    zoneId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithZonePositions(item,
                    zoneId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a Position entity into database.
     *
     * @param item The Position entity to persist
     * @param zoneId The zone id
     * @return Id of the Position entity
     */
    public long insertWithZonePositions(
            Position item, int zoneId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PositionContract.TABLE_NAME + ")");
        }

        ContentValues values = PositionContract.itemToContentValues(item,
                zoneId);
        values.remove(PositionContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);


        return newid;
    }


    /**
     * Delete a Position entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PositionContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PositionContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param position The entity to delete
     * @return count of updated entities
     */
    public int delete(final Position position) {
        return this.remove(position.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Position corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PositionContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PositionContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Position entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PositionContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PositionContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

