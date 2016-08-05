
/**************************************************************************
 * TypeDePokemonSQLiteAdapterBase.java, pokemon Android
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


import com.maximeleau.harmony.android.pokemon.data.SQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PokemonSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;


import com.maximeleau.harmony.android.pokemon.PokemonApplication;



/** TypeDePokemon adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit TypeDePokemonAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class TypeDePokemonSQLiteAdapterBase
                        extends SQLiteAdapter<TypeDePokemon> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypeDePokemonDBAdapter";


    /**
     * Get the table name used in DB for your TypeDePokemon entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return TypeDePokemonContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your TypeDePokemon entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = TypeDePokemonContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the TypeDePokemon entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return TypeDePokemonContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + TypeDePokemonContract.TABLE_NAME    + " ("
        
         + TypeDePokemonContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + TypeDePokemonContract.COL_NOM    + " VARCHAR NOT NULL,"
         + TypeDePokemonContract.COL_ATTAQUE    + " INTEGER NOT NULL,"
         + TypeDePokemonContract.COL_DEFENSE    + " INTEGER NOT NULL,"
         + TypeDePokemonContract.COL_PV    + " INTEGER NOT NULL,"
         + TypeDePokemonContract.COL_NUMPOKEDEX    + " INTEGER NOT NULL,"
         + TypeDePokemonContract.COL_URLIMAGE    + " VARCHAR"

        
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public TypeDePokemonSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert TypeDePokemon entity to Content Values for database.
     * @param item TypeDePokemon entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final TypeDePokemon item) {
        return TypeDePokemonContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to TypeDePokemon entity.
     * @param cursor android.database.Cursor object
     * @return TypeDePokemon entity
     */
    public TypeDePokemon cursorToItem(final android.database.Cursor cursor) {
        return TypeDePokemonContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to TypeDePokemon entity.
     * @param cursor android.database.Cursor object
     * @param result TypeDePokemon entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final TypeDePokemon result) {
        TypeDePokemonContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read TypeDePokemon by id in database.
     *
     * @param id Identify of TypeDePokemon
     * @return TypeDePokemon entity
     */
    public TypeDePokemon getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final TypeDePokemon result = this.cursorToItem(cursor);
        cursor.close();

        final PokemonSQLiteAdapter pokemonsAdapter =
                new PokemonSQLiteAdapter(this.ctx);
        pokemonsAdapter.open(this.mDatabase);
        android.database.Cursor pokemonsCursor = pokemonsAdapter
                    .getByTypeDePokemon(
                            result.getId(),
                            PokemonContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setPokemons(pokemonsAdapter.cursorToItems(pokemonsCursor));

        pokemonsCursor.close();
        return result;
    }


    /**
     * Read All TypeDePokemons entities.
     *
     * @return List of TypeDePokemon entities
     */
    public ArrayList<TypeDePokemon> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<TypeDePokemon> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a TypeDePokemon entity into database.
     *
     * @param item The TypeDePokemon entity to persist
     * @return Id of the TypeDePokemon entity
     */
    public long insert(final TypeDePokemon item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + TypeDePokemonContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypeDePokemonContract.itemToContentValues(item);
        values.remove(TypeDePokemonContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    TypeDePokemonContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getPokemons() != null) {
            PokemonSQLiteAdapterBase pokemonsAdapter =
                    new PokemonSQLiteAdapter(this.ctx);
            pokemonsAdapter.open(this.mDatabase);
            for (Pokemon pokemon
                        : item.getPokemons()) {
                pokemon.setTypeDePokemon(item);
                pokemonsAdapter.insertOrUpdate(pokemon);
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a TypeDePokemon entity into database whether.
     * it already exists or not.
     *
     * @param item The TypeDePokemon entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final TypeDePokemon item) {
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
     * Update a TypeDePokemon entity into database.
     *
     * @param item The TypeDePokemon entity to persist
     * @return count of updated entities
     */
    public int update(final TypeDePokemon item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + TypeDePokemonContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypeDePokemonContract.itemToContentValues(item);
        final String whereClause =
                 TypeDePokemonContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a TypeDePokemon entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + TypeDePokemonContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                TypeDePokemonContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param typeDePokemon The entity to delete
     * @return count of updated entities
     */
    public int delete(final TypeDePokemon typeDePokemon) {
        return this.remove(typeDePokemon.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the TypeDePokemon corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                TypeDePokemonContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                TypeDePokemonContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a TypeDePokemon entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = TypeDePokemonContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                TypeDePokemonContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

