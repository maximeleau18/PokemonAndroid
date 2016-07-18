
/**************************************************************************
 * BadgeSQLiteAdapterBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.data.BadgeSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;
import com.maximeleau.harmony.android.pokemon.entity.Badge;


import com.maximeleau.harmony.android.pokemon.PokemonApplication;



/** Badge adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit BadgeAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class BadgeSQLiteAdapterBase
                        extends SQLiteAdapter<Badge> {

    /** TAG for debug purpose. */
    protected static final String TAG = "BadgeDBAdapter";


    /**
     * Get the table name used in DB for your Badge entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return BadgeContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Badge entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = BadgeContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Badge entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return BadgeContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + BadgeContract.TABLE_NAME    + " ("
        
         + BadgeContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + BadgeContract.COL_NOM    + " VARCHAR NOT NULL,"
         + BadgeContract.COL_ZONEBADGESINTERNAL_ID    + " INTEGER,"

        
         + "FOREIGN KEY(" + BadgeContract.COL_ZONEBADGESINTERNAL_ID + ") REFERENCES " 
             + ZoneContract.TABLE_NAME 
                + " (" + ZoneContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public BadgeSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Badge entity to Content Values for database.
     * @param item Badge entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Badge item) {
        return BadgeContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Badge entity.
     * @param cursor android.database.Cursor object
     * @return Badge entity
     */
    public Badge cursorToItem(final android.database.Cursor cursor) {
        return BadgeContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Badge entity.
     * @param cursor android.database.Cursor object
     * @param result Badge entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Badge result) {
        BadgeContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Badge by id in database.
     *
     * @param id Identify of Badge
     * @return Badge entity
     */
    public Badge getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Badge result = this.cursorToItem(cursor);
        cursor.close();

        return result;
    }

    /**
     * Find & read Badge by ZonebadgesInternal.
     * @param zonebadgesinternalId zonebadgesinternalId
     * @param orderBy Order by string (can be null)
     * @return List of Badge entities
     */
     public android.database.Cursor getByZonebadgesInternal(final int zonebadgesinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = BadgeContract.COL_ZONEBADGESINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(zonebadgesinternalId);
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
     * Read All Badges entities.
     *
     * @return List of Badge entities
     */
    public ArrayList<Badge> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Badge> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Badge entity into database.
     *
     * @param item The Badge entity to persist
     * @return Id of the Badge entity
     */
    public long insert(final Badge item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + BadgeContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                BadgeContract.itemToContentValues(item, 0);
        values.remove(BadgeContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    BadgeContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Badge entity into database whether.
     * it already exists or not.
     *
     * @param item The Badge entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Badge item) {
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
     * Update a Badge entity into database.
     *
     * @param item The Badge entity to persist
     * @return count of updated entities
     */
    public int update(final Badge item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + BadgeContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                BadgeContract.itemToContentValues(item, 0);
        final String whereClause =
                 BadgeContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Update a Badge entity into database.
     *
     * @param item The Badge entity to persist
     * @param zoneId The zone id
     * @return count of updated entities
     */
    public int updateWithZoneBadges(
                    Badge item,
                    int zonebadgesInternalId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + BadgeContract.TABLE_NAME + ")");
        }

        ContentValues values =
                BadgeContract.itemToContentValues(item);
        values.put(
                BadgeContract.COL_ZONEBADGESINTERNAL_ID,
                zonebadgesInternalId);
        String whereClause =
                 BadgeContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a Badge entity into database whether.
     * it already exists or not.
     *
     * @param item The Badge entity to persist
     * @param zoneId The zone id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithZoneBadges(
            Badge item, int zoneId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithZoneBadges(item,
                    zoneId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithZoneBadges(item,
                    zoneId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a Badge entity into database.
     *
     * @param item The Badge entity to persist
     * @param zoneId The zone id
     * @return Id of the Badge entity
     */
    public long insertWithZoneBadges(
            Badge item, int zoneId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + BadgeContract.TABLE_NAME + ")");
        }

        ContentValues values = BadgeContract.itemToContentValues(item,
                zoneId);
        values.remove(BadgeContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);


        return newid;
    }


    /**
     * Delete a Badge entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + BadgeContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                BadgeContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param badge The entity to delete
     * @return count of updated entities
     */
    public int delete(final Badge badge) {
        return this.remove(badge.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Badge corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                BadgeContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                BadgeContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Badge entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = BadgeContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                BadgeContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

