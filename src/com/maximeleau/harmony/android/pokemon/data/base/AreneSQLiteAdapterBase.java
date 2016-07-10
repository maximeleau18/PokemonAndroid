
/**************************************************************************
 * AreneSQLiteAdapterBase.java, pokemon Android
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


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.maximeleau.harmony.android.pokemon.data.SQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.AreneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.BadgeSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PositionSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PositionContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.entity.Badge;
import com.maximeleau.harmony.android.pokemon.entity.Position;


import com.maximeleau.harmony.android.pokemon.PokemonApplication;



/** Arene adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit AreneAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class AreneSQLiteAdapterBase
                        extends SQLiteAdapter<Arene> {

    /** TAG for debug purpose. */
    protected static final String TAG = "AreneDBAdapter";


    /**
     * Get the table name used in DB for your Arene entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return AreneContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Arene entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = AreneContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Arene entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return AreneContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + AreneContract.TABLE_NAME    + " ("
        
         + AreneContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + AreneContract.COL_NOM    + " VARCHAR NOT NULL,"
         + AreneContract.COL_MAITRE_ID    + " INTEGER NOT NULL,"
         + AreneContract.COL_BADGE_ID    + " INTEGER NOT NULL,"
         + AreneContract.COL_POSITION_ID    + " INTEGER NOT NULL,"
         + AreneContract.COL_ZONEARENESINTERNAL_ID    + " INTEGER,"

        
         + "FOREIGN KEY(" + AreneContract.COL_MAITRE_ID + ") REFERENCES " 
             + PersonnageNonJoueurContract.TABLE_NAME 
                + " (" + PersonnageNonJoueurContract.COL_ID + "),"
         + "FOREIGN KEY(" + AreneContract.COL_BADGE_ID + ") REFERENCES " 
             + BadgeContract.TABLE_NAME 
                + " (" + BadgeContract.COL_ID + "),"
         + "FOREIGN KEY(" + AreneContract.COL_POSITION_ID + ") REFERENCES " 
             + PositionContract.TABLE_NAME 
                + " (" + PositionContract.COL_ID + "),"
         + "FOREIGN KEY(" + AreneContract.COL_ZONEARENESINTERNAL_ID + ") REFERENCES " 
             + ZoneContract.TABLE_NAME 
                + " (" + ZoneContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public AreneSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Arene entity to Content Values for database.
     * @param item Arene entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Arene item) {
        return AreneContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Arene entity.
     * @param cursor android.database.Cursor object
     * @return Arene entity
     */
    public Arene cursorToItem(final android.database.Cursor cursor) {
        return AreneContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Arene entity.
     * @param cursor android.database.Cursor object
     * @param result Arene entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Arene result) {
        AreneContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Arene by id in database.
     *
     * @param id Identify of Arene
     * @return Arene entity
     */
    public Arene getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Arene result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getMaitre() != null) {
            final PersonnageNonJoueurSQLiteAdapter maitreAdapter =
                    new PersonnageNonJoueurSQLiteAdapter(this.ctx);
            maitreAdapter.open(this.mDatabase);

            result.setMaitre(maitreAdapter.getByID(
                            result.getMaitre().getId()));
        }
        if (result.getBadge() != null) {
            final BadgeSQLiteAdapter badgeAdapter =
                    new BadgeSQLiteAdapter(this.ctx);
            badgeAdapter.open(this.mDatabase);

            result.setBadge(badgeAdapter.getByID(
                            result.getBadge().getId()));
        }
        if (result.getPosition() != null) {
            final PositionSQLiteAdapter positionAdapter =
                    new PositionSQLiteAdapter(this.ctx);
            positionAdapter.open(this.mDatabase);

            result.setPosition(positionAdapter.getByID(
                            result.getPosition().getId()));
        }
        return result;
    }

    /**
     * Find & read Arene by maitre.
     * @param maitreId maitreId
     * @param orderBy Order by string (can be null)
     * @return List of Arene entities
     */
     public android.database.Cursor getByMaitre(final int maitreId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = AreneContract.COL_MAITRE_ID + "= ?";
        String idSelectionArgs = String.valueOf(maitreId);
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
     * Find & read Arene by badge.
     * @param badgeId badgeId
     * @param orderBy Order by string (can be null)
     * @return List of Arene entities
     */
     public android.database.Cursor getByBadge(final int badgeId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = AreneContract.COL_BADGE_ID + "= ?";
        String idSelectionArgs = String.valueOf(badgeId);
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
     * Find & read Arene by position.
     * @param positionId positionId
     * @param orderBy Order by string (can be null)
     * @return List of Arene entities
     */
     public android.database.Cursor getByPosition(final int positionId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = AreneContract.COL_POSITION_ID + "= ?";
        String idSelectionArgs = String.valueOf(positionId);
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
     * Find & read Arene by ZonearenesInternal.
     * @param zonearenesinternalId zonearenesinternalId
     * @param orderBy Order by string (can be null)
     * @return List of Arene entities
     */
     public android.database.Cursor getByZonearenesInternal(final int zonearenesinternalId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = AreneContract.COL_ZONEARENESINTERNAL_ID + "= ?";
        String idSelectionArgs = String.valueOf(zonearenesinternalId);
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
     * Read All Arenes entities.
     *
     * @return List of Arene entities
     */
    public ArrayList<Arene> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Arene> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Arene entity into database.
     *
     * @param item The Arene entity to persist
     * @return Id of the Arene entity
     */
    public long insert(final Arene item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + AreneContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                AreneContract.itemToContentValues(item, 0);
        values.remove(AreneContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    AreneContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Arene entity into database whether.
     * it already exists or not.
     *
     * @param item The Arene entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Arene item) {
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
     * Update a Arene entity into database.
     *
     * @param item The Arene entity to persist
     * @return count of updated entities
     */
    public int update(final Arene item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + AreneContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                AreneContract.itemToContentValues(item, 0);
        final String whereClause =
                 AreneContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Update a Arene entity into database.
     *
     * @param item The Arene entity to persist
     * @param zoneId The zone id
     * @return count of updated entities
     */
    public int updateWithZoneArenes(
                    Arene item,
                    int zonearenesInternalId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + AreneContract.TABLE_NAME + ")");
        }

        ContentValues values =
                AreneContract.itemToContentValues(item);
        values.put(
                AreneContract.COL_ZONEARENESINTERNAL_ID,
                zonearenesInternalId);
        String whereClause =
                 AreneContract.COL_ID
                 + "=?";
        String[] whereArgs =
                new String[] {String.valueOf(item.getId())};

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Either insert or update a Arene entity into database whether.
     * it already exists or not.
     *
     * @param item The Arene entity to persist
     * @param zoneId The zone id
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdateWithZoneArenes(
            Arene item, int zoneId) {
        int result = 0;
        if (this.getByID(item.getId()) != null) {
            // Item already exists => update it
            result = this.updateWithZoneArenes(item,
                    zoneId);
        } else {
            // Item doesn't exist => create it
            long id = this.insertWithZoneArenes(item,
                    zoneId);
            if (id != 0) {
                result = 1;
            }
        }

        return result;
    }


    /**
     * Insert a Arene entity into database.
     *
     * @param item The Arene entity to persist
     * @param zoneId The zone id
     * @return Id of the Arene entity
     */
    public long insertWithZoneArenes(
            Arene item, int zoneId) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + AreneContract.TABLE_NAME + ")");
        }

        ContentValues values = AreneContract.itemToContentValues(item,
                zoneId);
        values.remove(AreneContract.COL_ID);
        int newid = (int) this.insert(
            null,
            values);


        return newid;
    }


    /**
     * Delete a Arene entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + AreneContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                AreneContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param arene The entity to delete
     * @return count of updated entities
     */
    public int delete(final Arene arene) {
        return this.remove(arene.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Arene corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                AreneContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                AreneContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Arene entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = AreneContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                AreneContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

