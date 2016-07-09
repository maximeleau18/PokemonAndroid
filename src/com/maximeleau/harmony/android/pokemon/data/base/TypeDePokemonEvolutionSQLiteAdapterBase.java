
/**************************************************************************
 * TypeDePokemonEvolutionSQLiteAdapterBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonEvolutionSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonEvolutionContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonEvolution;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;


import com.maximeleau.harmony.android.pokemon.PokemonApplication;



/** TypeDePokemonEvolution adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit TypeDePokemonEvolutionAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class TypeDePokemonEvolutionSQLiteAdapterBase
                        extends SQLiteAdapter<TypeDePokemonEvolution> {

    /** TAG for debug purpose. */
    protected static final String TAG = "TypeDePokemonEvolutionDBAdapter";


    /**
     * Get the table name used in DB for your TypeDePokemonEvolution entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return TypeDePokemonEvolutionContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your TypeDePokemonEvolution entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = TypeDePokemonEvolutionContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the TypeDePokemonEvolution entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return TypeDePokemonEvolutionContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + TypeDePokemonEvolutionContract.TABLE_NAME    + " ("
        
         + TypeDePokemonEvolutionContract.COL_EVOLUEEN_ID    + " INTEGER NOT NULL,"
         + TypeDePokemonEvolutionContract.COL_ESTEVOLUEEN_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + TypeDePokemonEvolutionContract.COL_EVOLUEEN_ID + ") REFERENCES " 
             + TypeDePokemonContract.TABLE_NAME 
                + " (" + TypeDePokemonContract.COL_ID + "),"
         + "FOREIGN KEY(" + TypeDePokemonEvolutionContract.COL_ESTEVOLUEEN_ID + ") REFERENCES " 
             + TypeDePokemonContract.TABLE_NAME 
                + " (" + TypeDePokemonContract.COL_ID + ")"
        + ", PRIMARY KEY (" + TypeDePokemonEvolutionContract.COL_EVOLUEEN_ID + "," + TypeDePokemonEvolutionContract.COL_ESTEVOLUEEN_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public TypeDePokemonEvolutionSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert TypeDePokemonEvolution entity to Content Values for database.
     * @param item TypeDePokemonEvolution entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final TypeDePokemonEvolution item) {
        return TypeDePokemonEvolutionContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to TypeDePokemonEvolution entity.
     * @param cursor android.database.Cursor object
     * @return TypeDePokemonEvolution entity
     */
    public TypeDePokemonEvolution cursorToItem(final android.database.Cursor cursor) {
        return TypeDePokemonEvolutionContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to TypeDePokemonEvolution entity.
     * @param cursor android.database.Cursor object
     * @param result TypeDePokemonEvolution entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final TypeDePokemonEvolution result) {
        TypeDePokemonEvolutionContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read TypeDePokemonEvolution by id in database.
     *
     * @param id Identify of TypeDePokemonEvolution
     * @return TypeDePokemonEvolution entity
     */
    public TypeDePokemonEvolution getByID(final TypeDePokemon evolueEn
                            ,final TypeDePokemon estEvolueEn) {
        final android.database.Cursor cursor = this.getSingleCursor(evolueEn,
                                        estEvolueEn);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final TypeDePokemonEvolution result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getEvolueEn() != null) {
            final TypeDePokemonSQLiteAdapter evolueEnAdapter =
                    new TypeDePokemonSQLiteAdapter(this.ctx);
            evolueEnAdapter.open(this.mDatabase);

            result.setEvolueEn(evolueEnAdapter.getByID(
                            result.getEvolueEn().getId()));
        }
        if (result.getEstEvolueEn() != null) {
            final TypeDePokemonSQLiteAdapter estEvolueEnAdapter =
                    new TypeDePokemonSQLiteAdapter(this.ctx);
            estEvolueEnAdapter.open(this.mDatabase);

            result.setEstEvolueEn(estEvolueEnAdapter.getByID(
                            result.getEstEvolueEn().getId()));
        }
        return result;
    }

    /**
     * Find & read TypeDePokemonEvolution by evolueEn.
     * @param evolueenId evolueenId
     * @param orderBy Order by string (can be null)
     * @return List of TypeDePokemonEvolution entities
     */
     public android.database.Cursor getByEvolueEn(final int evolueenId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = TypeDePokemonEvolutionContract.COL_EVOLUEEN_ID + "= ?";
        String idSelectionArgs = String.valueOf(evolueenId);
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
     * Find & read TypeDePokemonEvolution by estEvolueEn.
     * @param estevolueenId estevolueenId
     * @param orderBy Order by string (can be null)
     * @return List of TypeDePokemonEvolution entities
     */
     public android.database.Cursor getByEstEvolueEn(final int estevolueenId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = TypeDePokemonEvolutionContract.COL_ESTEVOLUEEN_ID + "= ?";
        String idSelectionArgs = String.valueOf(estevolueenId);
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
     * Read All TypeDePokemonEvolutions entities.
     *
     * @return List of TypeDePokemonEvolution entities
     */
    public ArrayList<TypeDePokemonEvolution> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<TypeDePokemonEvolution> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a TypeDePokemonEvolution entity into database.
     *
     * @param item The TypeDePokemonEvolution entity to persist
     * @return Id of the TypeDePokemonEvolution entity
     */
    public long insert(final TypeDePokemonEvolution item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + TypeDePokemonEvolutionContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypeDePokemonEvolutionContract.itemToContentValues(item);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    TypeDePokemonEvolutionContract.COL_EVOLUEEN_ID,
                    values);
        }
        return insertResult;
    }

    /**
     * Either insert or update a TypeDePokemonEvolution entity into database whether.
     * it already exists or not.
     *
     * @param item The TypeDePokemonEvolution entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final TypeDePokemonEvolution item) {
        int result = 0;
        if (this.getByID(item.getEvolueEn(), item.getEstEvolueEn()) != null) {
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
     * Update a TypeDePokemonEvolution entity into database.
     *
     * @param item The TypeDePokemonEvolution entity to persist
     * @return count of updated entities
     */
    public int update(final TypeDePokemonEvolution item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + TypeDePokemonEvolutionContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                TypeDePokemonEvolutionContract.itemToContentValues(item);
        final String whereClause =
                 TypeDePokemonEvolutionContract.COL_EVOLUEEN_ID
                 + " = ?"
                 + " AND "
                 + TypeDePokemonEvolutionContract.COL_ESTEVOLUEEN_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getEvolueEn()) ,
                              String.valueOf(item.getEstEvolueEn()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a TypeDePokemonEvolution entity of database.
     *
     * @param evolueEn evolueEn
* @param estEvolueEn estEvolueEn
     * @return count of updated entities
     */
    public int remove(final TypeDePokemon evolueEn
            ,final TypeDePokemon estEvolueEn) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + TypeDePokemonEvolutionContract.TABLE_NAME
                    + ")"
                    + " id : "+ evolueEn.getId()
                    + " id : "+ estEvolueEn.getId());
        }

        final String whereClause =
                TypeDePokemonEvolutionContract.COL_EVOLUEEN_ID
                + " = ? AND "
                + TypeDePokemonEvolutionContract.COL_ESTEVOLUEEN_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(evolueEn.getId()),
                    String.valueOf(estEvolueEn.getId())};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param typeDePokemonEvolution The entity to delete
     * @return count of updated entities
     */
    public int delete(final TypeDePokemonEvolution typeDePokemonEvolution) {
        return this.remove(
                typeDePokemonEvolution.getEvolueEn(),
                typeDePokemonEvolution.getEstEvolueEn());
    }

    /**
     *  Internal android.database.Cursor.
     * @param evolueEn evolueEn
* @param estEvolueEn estEvolueEn
     *  @return A android.database.Cursor pointing to the TypeDePokemonEvolution corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(
                        final TypeDePokemon evolueEn,
                        final TypeDePokemon estEvolueEn) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + evolueEn.getId()
                     + " id : " + estEvolueEn.getId());
        }

        final String whereClause =
                TypeDePokemonEvolutionContract.ALIASED_COL_EVOLUEEN_ID
                + " = ? AND "
                + TypeDePokemonEvolutionContract.ALIASED_COL_ESTEVOLUEEN_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                String.valueOf(evolueEn.getId()),
                String.valueOf(estEvolueEn.getId())};

        return this.query(
                TypeDePokemonEvolutionContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a TypeDePokemonEvolution entity.
     *
     * @param evolueEn The evolueEn of the entity to get from the DB
     * @param estEvolueEn The estEvolueEn of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final TypeDePokemon evolueEn,
                final TypeDePokemon estEvolueEn) {

        String selection = TypeDePokemonEvolutionContract.ALIASED_COL_EVOLUEEN_ID + " = ?";
        selection += " AND ";
        selection += TypeDePokemonEvolutionContract.ALIASED_COL_ESTEVOLUEEN_ID + " = ?";
        

        String[] selectionArgs = new String[2];
        selectionArgs[0] = String.valueOf(evolueEn.getId());
        selectionArgs[1] = String.valueOf(estEvolueEn.getId());

        return this.query(
                TypeDePokemonEvolutionContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

