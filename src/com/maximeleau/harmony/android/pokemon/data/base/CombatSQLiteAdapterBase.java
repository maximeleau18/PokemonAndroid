
/**************************************************************************
 * CombatSQLiteAdapterBase.java, pokemon Android
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
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


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
         + CombatContract.COL_DRESSEUR1VAINQUEUR    + " BOOLEAN NOT NULL,"
         + CombatContract.COL_DRESSEUR2VAINQUEUR    + " BOOLEAN NOT NULL,"
         + CombatContract.COL_POKEMON1VAINQUEUR    + " BOOLEAN NOT NULL,"
         + CombatContract.COL_POKEMON2VAINQUEUR    + " BOOLEAN NOT NULL"

        
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

        final PokemonSQLiteAdapter pokemon1Adapter =
                new PokemonSQLiteAdapter(this.ctx);
        pokemon1Adapter.open(this.mDatabase);
        android.database.Cursor pokemon1Cursor = pokemon1Adapter
                    .getByCombatpokemon1Internal(
                            result.getId(),
                            PokemonContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setPokemon1(pokemon1Adapter.cursorToItems(pokemon1Cursor));

        pokemon1Cursor.close();
        final PokemonSQLiteAdapter pokemon2Adapter =
                new PokemonSQLiteAdapter(this.ctx);
        pokemon2Adapter.open(this.mDatabase);
        android.database.Cursor pokemon2Cursor = pokemon2Adapter
                    .getByCombatpokemon2Internal(
                            result.getId(),
                            PokemonContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setPokemon2(pokemon2Adapter.cursorToItems(pokemon2Cursor));

        pokemon2Cursor.close();
        final DresseurSQLiteAdapter dresseur1Adapter =
                new DresseurSQLiteAdapter(this.ctx);
        dresseur1Adapter.open(this.mDatabase);
        android.database.Cursor dresseur1Cursor = dresseur1Adapter
                    .getByCombatdresseur1Internal(
                            result.getId(),
                            DresseurContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setDresseur1(dresseur1Adapter.cursorToItems(dresseur1Cursor));

        dresseur1Cursor.close();
        final DresseurSQLiteAdapter dresseur2Adapter =
                new DresseurSQLiteAdapter(this.ctx);
        dresseur2Adapter.open(this.mDatabase);
        android.database.Cursor dresseur2Cursor = dresseur2Adapter
                    .getByCombatdresseur2Internal(
                            result.getId(),
                            DresseurContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setDresseur2(dresseur2Adapter.cursorToItems(dresseur2Cursor));

        dresseur2Cursor.close();
        return result;
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
        if (item.getPokemon1() != null) {
            PokemonSQLiteAdapterBase pokemon1Adapter =
                    new PokemonSQLiteAdapter(this.ctx);
            pokemon1Adapter.open(this.mDatabase);
            for (Pokemon pokemon
                        : item.getPokemon1()) {
                pokemon1Adapter.insertOrUpdateWithCombatPokemon1(
                                    pokemon,
                                    insertResult);
            }
        }
        if (item.getPokemon2() != null) {
            PokemonSQLiteAdapterBase pokemon2Adapter =
                    new PokemonSQLiteAdapter(this.ctx);
            pokemon2Adapter.open(this.mDatabase);
            for (Pokemon pokemon
                        : item.getPokemon2()) {
                pokemon2Adapter.insertOrUpdateWithCombatPokemon2(
                                    pokemon,
                                    insertResult);
            }
        }
        if (item.getDresseur1() != null) {
            DresseurSQLiteAdapterBase dresseur1Adapter =
                    new DresseurSQLiteAdapter(this.ctx);
            dresseur1Adapter.open(this.mDatabase);
            for (Dresseur dresseur
                        : item.getDresseur1()) {
                dresseur1Adapter.insertOrUpdateWithCombatDresseur1(
                                    dresseur,
                                    insertResult);
            }
        }
        if (item.getDresseur2() != null) {
            DresseurSQLiteAdapterBase dresseur2Adapter =
                    new DresseurSQLiteAdapter(this.ctx);
            dresseur2Adapter.open(this.mDatabase);
            for (Dresseur dresseur
                        : item.getDresseur2()) {
                dresseur2Adapter.insertOrUpdateWithCombatDresseur2(
                                    dresseur,
                                    insertResult);
            }
        }
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

