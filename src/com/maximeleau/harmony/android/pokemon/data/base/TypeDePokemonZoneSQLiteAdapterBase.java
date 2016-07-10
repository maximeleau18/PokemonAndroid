
/**************************************************************************
 * TypeDePokemonZoneSQLiteAdapterBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonZoneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.ZoneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonZoneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonZone;
import com.maximeleau.harmony.android.pokemon.entity.Zone;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;


import com.maximeleau.harmony.android.pokemon.PokemonApplication;



/** TypeDePokemonZone adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit TypeDePokemonZoneAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class TypeDePokemonZoneSQLiteAdapterBase
                        extends SQLiteAdapter<TypeDePokemonZone> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypeDePokemonZoneDBAdapter";


    /**
     * Get the table name used in DB for your TypeDePokemonZone entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return TypeDePokemonZoneContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your TypeDePokemonZone entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = TypeDePokemonZoneContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the TypeDePokemonZone entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return TypeDePokemonZoneContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + TypeDePokemonZoneContract.TABLE_NAME    + " ("
        
         + TypeDePokemonZoneContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + TypeDePokemonZoneContract.COL_ZONE_ID    + " INTEGER NOT NULL,"
         + TypeDePokemonZoneContract.COL_TYPEDEPOKEMON_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + TypeDePokemonZoneContract.COL_ZONE_ID + ") REFERENCES " 
             + ZoneContract.TABLE_NAME 
                + " (" + ZoneContract.COL_ID + "),"
         + "FOREIGN KEY(" + TypeDePokemonZoneContract.COL_TYPEDEPOKEMON_ID + ") REFERENCES " 
             + TypeDePokemonContract.TABLE_NAME 
                + " (" + TypeDePokemonContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public TypeDePokemonZoneSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert TypeDePokemonZone entity to Content Values for database.
     * @param item TypeDePokemonZone entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final TypeDePokemonZone item) {
        return TypeDePokemonZoneContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to TypeDePokemonZone entity.
     * @param cursor android.database.Cursor object
     * @return TypeDePokemonZone entity
     */
    public TypeDePokemonZone cursorToItem(final android.database.Cursor cursor) {
        return TypeDePokemonZoneContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to TypeDePokemonZone entity.
     * @param cursor android.database.Cursor object
     * @param result TypeDePokemonZone entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final TypeDePokemonZone result) {
        TypeDePokemonZoneContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read TypeDePokemonZone by id in database.
     *
     * @param id Identify of TypeDePokemonZone
     * @return TypeDePokemonZone entity
     */
    public TypeDePokemonZone getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final TypeDePokemonZone result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getZone() != null) {
            final ZoneSQLiteAdapter zoneAdapter =
                    new ZoneSQLiteAdapter(this.ctx);
            zoneAdapter.open(this.mDatabase);

            result.setZone(zoneAdapter.getByID(
                            result.getZone().getId()));
        }
        if (result.getTypeDePokemon() != null) {
            final TypeDePokemonSQLiteAdapter typeDePokemonAdapter =
                    new TypeDePokemonSQLiteAdapter(this.ctx);
            typeDePokemonAdapter.open(this.mDatabase);

            result.setTypeDePokemon(typeDePokemonAdapter.getByID(
                            result.getTypeDePokemon().getId()));
        }
        return result;
    }

    /**
     * Find & read TypeDePokemonZone by zone.
     * @param zoneId zoneId
     * @param orderBy Order by string (can be null)
     * @return List of TypeDePokemonZone entities
     */
     public android.database.Cursor getByZone(final int zoneId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = TypeDePokemonZoneContract.COL_ZONE_ID + "= ?";
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
     * Find & read TypeDePokemonZone by typeDePokemon.
     * @param typedepokemonId typedepokemonId
     * @param orderBy Order by string (can be null)
     * @return List of TypeDePokemonZone entities
     */
     public android.database.Cursor getByTypeDePokemon(final int typedepokemonId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = TypeDePokemonZoneContract.COL_TYPEDEPOKEMON_ID + "= ?";
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
     * Read All TypeDePokemonZones entities.
     *
     * @return List of TypeDePokemonZone entities
     */
    public ArrayList<TypeDePokemonZone> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<TypeDePokemonZone> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a TypeDePokemonZone entity into database.
     *
     * @param item The TypeDePokemonZone entity to persist
     * @return Id of the TypeDePokemonZone entity
     */
    public long insert(final TypeDePokemonZone item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + TypeDePokemonZoneContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypeDePokemonZoneContract.itemToContentValues(item);
        values.remove(TypeDePokemonZoneContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    TypeDePokemonZoneContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a TypeDePokemonZone entity into database whether.
     * it already exists or not.
     *
     * @param item The TypeDePokemonZone entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final TypeDePokemonZone item) {
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
     * Update a TypeDePokemonZone entity into database.
     *
     * @param item The TypeDePokemonZone entity to persist
     * @return count of updated entities
     */
    public int update(final TypeDePokemonZone item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + TypeDePokemonZoneContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypeDePokemonZoneContract.itemToContentValues(item);
        final String whereClause =
                 TypeDePokemonZoneContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a TypeDePokemonZone entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + TypeDePokemonZoneContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                TypeDePokemonZoneContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param typeDePokemonZone The entity to delete
     * @return count of updated entities
     */
    public int delete(final TypeDePokemonZone typeDePokemonZone) {
        return this.remove(typeDePokemonZone.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the TypeDePokemonZone corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                TypeDePokemonZoneContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                TypeDePokemonZoneContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a TypeDePokemonZone entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = TypeDePokemonZoneContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                TypeDePokemonZoneContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

