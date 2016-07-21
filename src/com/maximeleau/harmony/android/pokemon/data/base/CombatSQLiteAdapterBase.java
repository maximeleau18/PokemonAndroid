
/**************************************************************************
 * CombatSQLiteAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
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
import com.maximeleau.harmony.android.pokemon.data.CombatSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PokemonSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.DresseurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.CombatContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;
import com.maximeleau.harmony.android.pokemon.entity.Combat;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;

import com.maximeleau.harmony.android.pokemon.harmony.util.DateUtils;
import com.maximeleau.harmony.android.pokemon.PokemonApplication;



/** Combat adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit CombatAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class CombatSQLiteAdapterBase
                        extends SQLiteAdapter<Combat> {

    /** TAG for debug purpose. */
    protected static final String TAG = "CombatDBAdapter";


    /**
     * Get the table name used in DB for your Combat entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return CombatContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Combat entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = CombatContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Combat entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return CombatContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + CombatContract.TABLE_NAME    + " ("
        
         + CombatContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + CombatContract.COL_LANCELE    + " DATETIME NOT NULL,"
         + CombatContract.COL_DUREE    + " INTEGER,"
         + CombatContract.COL_POKEMON1_ID    + " INTEGER NOT NULL,"
         + CombatContract.COL_POKEMON2_ID    + " INTEGER NOT NULL,"
         + CombatContract.COL_DRESSEUR1_ID    + " INTEGER NOT NULL,"
         + CombatContract.COL_DRESSEUR2_ID    + " INTEGER NOT NULL,"
         + CombatContract.COL_DRESSEUR1VAINQUEUR    + " BOOLEAN NOT NULL,"
         + CombatContract.COL_DRESSEUR2VAINQUEUR    + " BOOLEAN NOT NULL,"
         + CombatContract.COL_POKEMON1VAINQUEUR    + " BOOLEAN NOT NULL,"
         + CombatContract.COL_POKEMON2VAINQUEUR    + " BOOLEAN NOT NULL,"

        
         + "FOREIGN KEY(" + CombatContract.COL_POKEMON1_ID + ") REFERENCES " 
             + PokemonContract.TABLE_NAME 
                + " (" + PokemonContract.COL_ID + "),"
         + "FOREIGN KEY(" + CombatContract.COL_POKEMON2_ID + ") REFERENCES " 
             + PokemonContract.TABLE_NAME 
                + " (" + PokemonContract.COL_ID + "),"
         + "FOREIGN KEY(" + CombatContract.COL_DRESSEUR1_ID + ") REFERENCES " 
             + DresseurContract.TABLE_NAME 
                + " (" + DresseurContract.COL_ID + "),"
         + "FOREIGN KEY(" + CombatContract.COL_DRESSEUR2_ID + ") REFERENCES " 
             + DresseurContract.TABLE_NAME 
                + " (" + DresseurContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public CombatSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Combat entity to Content Values for database.
     * @param item Combat entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Combat item) {
        return CombatContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Combat entity.
     * @param cursor android.database.Cursor object
     * @return Combat entity
     */
    public Combat cursorToItem(final android.database.Cursor cursor) {
        return CombatContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Combat entity.
     * @param cursor android.database.Cursor object
     * @param result Combat entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Combat result) {
        CombatContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Combat by id in database.
     *
     * @param id Identify of Combat
     * @return Combat entity
     */
    public Combat getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Combat result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getPokemon1() != null) {
            final PokemonSQLiteAdapter pokemon1Adapter =
                    new PokemonSQLiteAdapter(this.ctx);
            pokemon1Adapter.open(this.mDatabase);

            result.setPokemon1(pokemon1Adapter.getByID(
                            result.getPokemon1().getId()));
        }
        if (result.getPokemon2() != null) {
            final PokemonSQLiteAdapter pokemon2Adapter =
                    new PokemonSQLiteAdapter(this.ctx);
            pokemon2Adapter.open(this.mDatabase);

            result.setPokemon2(pokemon2Adapter.getByID(
                            result.getPokemon2().getId()));
        }
        if (result.getDresseur1() != null) {
            final DresseurSQLiteAdapter dresseur1Adapter =
                    new DresseurSQLiteAdapter(this.ctx);
            dresseur1Adapter.open(this.mDatabase);

            result.setDresseur1(dresseur1Adapter.getByID(
                            result.getDresseur1().getId()));
        }
        if (result.getDresseur2() != null) {
            final DresseurSQLiteAdapter dresseur2Adapter =
                    new DresseurSQLiteAdapter(this.ctx);
            dresseur2Adapter.open(this.mDatabase);

            result.setDresseur2(dresseur2Adapter.getByID(
                            result.getDresseur2().getId()));
        }
        return result;
    }

    /**
     * Find & read Combat by pokemon1.
     * @param pokemon1Id pokemon1Id
     * @param orderBy Order by string (can be null)
     * @return List of Combat entities
     */
     public android.database.Cursor getByPokemon1(final int pokemon1Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = CombatContract.COL_POKEMON1_ID + "= ?";
        String idSelectionArgs = String.valueOf(pokemon1Id);
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
     * Find & read Combat by pokemon2.
     * @param pokemon2Id pokemon2Id
     * @param orderBy Order by string (can be null)
     * @return List of Combat entities
     */
     public android.database.Cursor getByPokemon2(final int pokemon2Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = CombatContract.COL_POKEMON2_ID + "= ?";
        String idSelectionArgs = String.valueOf(pokemon2Id);
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
     * Find & read Combat by dresseur1.
     * @param dresseur1Id dresseur1Id
     * @param orderBy Order by string (can be null)
     * @return List of Combat entities
     */
     public android.database.Cursor getByDresseur1(final int dresseur1Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = CombatContract.COL_DRESSEUR1_ID + "= ?";
        String idSelectionArgs = String.valueOf(dresseur1Id);
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
     * Find & read Combat by dresseur2.
     * @param dresseur2Id dresseur2Id
     * @param orderBy Order by string (can be null)
     * @return List of Combat entities
     */
     public android.database.Cursor getByDresseur2(final int dresseur2Id, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = CombatContract.COL_DRESSEUR2_ID + "= ?";
        String idSelectionArgs = String.valueOf(dresseur2Id);
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
     * Read All Combats entities.
     *
     * @return List of Combat entities
     */
    public ArrayList<Combat> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Combat> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Combat entity into database.
     *
     * @param item The Combat entity to persist
     * @return Id of the Combat entity
     */
    public long insert(final Combat item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + CombatContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                CombatContract.itemToContentValues(item);
        values.remove(CombatContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    CombatContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Combat entity into database whether.
     * it already exists or not.
     *
     * @param item The Combat entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Combat item) {
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
     * Update a Combat entity into database.
     *
     * @param item The Combat entity to persist
     * @return count of updated entities
     */
    public int update(final Combat item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + CombatContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                CombatContract.itemToContentValues(item);
        final String whereClause =
                 CombatContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Combat entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + CombatContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                CombatContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param combat The entity to delete
     * @return count of updated entities
     */
    public int delete(final Combat combat) {
        return this.remove(combat.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Combat corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                CombatContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                CombatContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Combat entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = CombatContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                CombatContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

