
/**************************************************************************
 * DresseurSQLiteAdapterBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.data.DresseurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;


import com.maximeleau.harmony.android.pokemon.PokemonApplication;



/** Dresseur adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit DresseurAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class DresseurSQLiteAdapterBase
                        extends SQLiteAdapter<Dresseur> {

    /** TAG for debug purpose. */
    protected static final String TAG = "DresseurDBAdapter";


    /**
     * Get the table name used in DB for your Dresseur entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return DresseurContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Dresseur entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = DresseurContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Dresseur entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return DresseurContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + DresseurContract.TABLE_NAME    + " ("
        
         + DresseurContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + DresseurContract.COL_NOM    + " VARCHAR NOT NULL,"
         + DresseurContract.COL_PRENOM    + " VARCHAR NOT NULL,"
         + DresseurContract.COL_LOGIN    + " VARCHAR NOT NULL,"
         + DresseurContract.COL_MOTDEPASSE    + " VARCHAR NOT NULL,"
         + DresseurContract.COL_PERSONNAGENONJOUEUR_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + DresseurContract.COL_PERSONNAGENONJOUEUR_ID + ") REFERENCES " 
             + PersonnageNonJoueurContract.TABLE_NAME 
                + " (" + PersonnageNonJoueurContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public DresseurSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Dresseur entity to Content Values for database.
     * @param item Dresseur entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Dresseur item) {
        return DresseurContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Dresseur entity.
     * @param cursor android.database.Cursor object
     * @return Dresseur entity
     */
    public Dresseur cursorToItem(final android.database.Cursor cursor) {
        return DresseurContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Dresseur entity.
     * @param cursor android.database.Cursor object
     * @param result Dresseur entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Dresseur result) {
        DresseurContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Dresseur by id in database.
     *
     * @param id Identify of Dresseur
     * @return Dresseur entity
     */
    public Dresseur getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Dresseur result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getPersonnageNonJoueur() != null) {
            final PersonnageNonJoueurSQLiteAdapter personnageNonJoueurAdapter =
                    new PersonnageNonJoueurSQLiteAdapter(this.ctx);
            personnageNonJoueurAdapter.open(this.mDatabase);

            result.setPersonnageNonJoueur(personnageNonJoueurAdapter.getByID(
                            result.getPersonnageNonJoueur().getId()));
        }
        return result;
    }

    /**
     * Find & read Dresseur by personnageNonJoueur.
     * @param personnagenonjoueurId personnagenonjoueurId
     * @param orderBy Order by string (can be null)
     * @return List of Dresseur entities
     */
     public android.database.Cursor getByPersonnageNonJoueur(final int personnagenonjoueurId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = DresseurContract.COL_PERSONNAGENONJOUEUR_ID + "= ?";
        String idSelectionArgs = String.valueOf(personnagenonjoueurId);
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
     * Read All Dresseurs entities.
     *
     * @return List of Dresseur entities
     */
    public ArrayList<Dresseur> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Dresseur> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Dresseur entity into database.
     *
     * @param item The Dresseur entity to persist
     * @return Id of the Dresseur entity
     */
    public long insert(final Dresseur item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + DresseurContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                DresseurContract.itemToContentValues(item);
        values.remove(DresseurContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    DresseurContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Dresseur entity into database whether.
     * it already exists or not.
     *
     * @param item The Dresseur entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Dresseur item) {
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
     * Update a Dresseur entity into database.
     *
     * @param item The Dresseur entity to persist
     * @return count of updated entities
     */
    public int update(final Dresseur item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + DresseurContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                DresseurContract.itemToContentValues(item);
        final String whereClause =
                 DresseurContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Dresseur entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + DresseurContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                DresseurContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param dresseur The entity to delete
     * @return count of updated entities
     */
    public int delete(final Dresseur dresseur) {
        return this.remove(dresseur.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Dresseur corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                DresseurContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                DresseurContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Dresseur entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = DresseurContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                DresseurContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

