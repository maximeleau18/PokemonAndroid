
/**************************************************************************
 * ZoneSQLiteAdapterBase.java, pokemon Android
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


import com.maximeleau.harmony.android.pokemon.data.SQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.ZoneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.AreneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.BadgeSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PositionSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PositionContract;
import com.maximeleau.harmony.android.pokemon.entity.Zone;
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.entity.Badge;
import com.maximeleau.harmony.android.pokemon.entity.Position;


import com.maximeleau.harmony.android.pokemon.PokemonApplication;



/** Zone adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ZoneAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ZoneSQLiteAdapterBase
                        extends SQLiteAdapter<Zone> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ZoneDBAdapter";


    /**
     * Get the table name used in DB for your Zone entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return ZoneContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Zone entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = ZoneContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Zone entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return ZoneContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + ZoneContract.TABLE_NAME    + " ("
        
         + ZoneContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + ZoneContract.COL_NOM    + " VARCHAR NOT NULL"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public ZoneSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Zone entity to Content Values for database.
     * @param item Zone entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Zone item) {
        return ZoneContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Zone entity.
     * @param cursor android.database.Cursor object
     * @return Zone entity
     */
    public Zone cursorToItem(final android.database.Cursor cursor) {
        return ZoneContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Zone entity.
     * @param cursor android.database.Cursor object
     * @param result Zone entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Zone result) {
        ZoneContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Zone by id in database.
     *
     * @param id Identify of Zone
     * @return Zone entity
     */
    public Zone getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Zone result = this.cursorToItem(cursor);
        cursor.close();

        final AreneSQLiteAdapter arenesAdapter =
                new AreneSQLiteAdapter(this.ctx);
        arenesAdapter.open(this.mDatabase);
        android.database.Cursor arenesCursor = arenesAdapter
                    .getByZonearenesInternal(
                            result.getId(),
                            AreneContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setArenes(arenesAdapter.cursorToItems(arenesCursor));

        arenesCursor.close();
        final BadgeSQLiteAdapter badgesAdapter =
                new BadgeSQLiteAdapter(this.ctx);
        badgesAdapter.open(this.mDatabase);
        android.database.Cursor badgesCursor = badgesAdapter
                    .getByZonebadgesInternal(
                            result.getId(),
                            BadgeContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setBadges(badgesAdapter.cursorToItems(badgesCursor));

        badgesCursor.close();
        final PositionSQLiteAdapter positionsAdapter =
                new PositionSQLiteAdapter(this.ctx);
        positionsAdapter.open(this.mDatabase);
        android.database.Cursor positionsCursor = positionsAdapter
                    .getByZonepositionsInternal(
                            result.getId(),
                            PositionContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setPositions(positionsAdapter.cursorToItems(positionsCursor));

        positionsCursor.close();
        return result;
    }


    /**
     * Read All Zones entities.
     *
     * @return List of Zone entities
     */
    public ArrayList<Zone> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Zone> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Zone entity into database.
     *
     * @param item The Zone entity to persist
     * @return Id of the Zone entity
     */
    public long insert(final Zone item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + ZoneContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ZoneContract.itemToContentValues(item);
        values.remove(ZoneContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    ZoneContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getArenes() != null) {
            AreneSQLiteAdapterBase arenesAdapter =
                    new AreneSQLiteAdapter(this.ctx);
            arenesAdapter.open(this.mDatabase);
            for (Arene arene
                        : item.getArenes()) {
                arenesAdapter.insertOrUpdateWithZoneArenes(
                                    arene,
                                    insertResult);
            }
        }
        if (item.getBadges() != null) {
            BadgeSQLiteAdapterBase badgesAdapter =
                    new BadgeSQLiteAdapter(this.ctx);
            badgesAdapter.open(this.mDatabase);
            for (Badge badge
                        : item.getBadges()) {
                badgesAdapter.insertOrUpdateWithZoneBadges(
                                    badge,
                                    insertResult);
            }
        }
        if (item.getPositions() != null) {
            PositionSQLiteAdapterBase positionsAdapter =
                    new PositionSQLiteAdapter(this.ctx);
            positionsAdapter.open(this.mDatabase);
            for (Position position
                        : item.getPositions()) {
                positionsAdapter.insertOrUpdateWithZonePositions(
                                    position,
                                    insertResult);
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a Zone entity into database whether.
     * it already exists or not.
     *
     * @param item The Zone entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Zone item) {
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
     * Update a Zone entity into database.
     *
     * @param item The Zone entity to persist
     * @return count of updated entities
     */
    public int update(final Zone item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + ZoneContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ZoneContract.itemToContentValues(item);
        final String whereClause =
                 ZoneContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Zone entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + ZoneContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                ZoneContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param zone The entity to delete
     * @return count of updated entities
     */
    public int delete(final Zone zone) {
        return this.remove(zone.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Zone corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                ZoneContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                ZoneContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Zone entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = ZoneContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                ZoneContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

