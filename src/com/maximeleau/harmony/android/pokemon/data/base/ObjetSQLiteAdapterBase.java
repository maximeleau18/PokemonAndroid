
/**************************************************************************
 * ObjetSQLiteAdapterBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.data.ObjetSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.TypeObjetSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.ObjetContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeObjetContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;
import com.maximeleau.harmony.android.pokemon.entity.Objet;
import com.maximeleau.harmony.android.pokemon.entity.TypeObjet;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;


import com.maximeleau.harmony.android.pokemon.PokemonApplication;



/** Objet adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ObjetAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class ObjetSQLiteAdapterBase
                        extends SQLiteAdapter<Objet> {

    /** TAG for debug purpose. */
    protected static final String TAG = "ObjetDBAdapter";


    /**
     * Get the table name used in DB for your Objet entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return ObjetContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your Objet entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = ObjetContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the Objet entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return ObjetContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + ObjetContract.TABLE_NAME    + " ("
        
         + ObjetContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + ObjetContract.COL_NOM    + " VARCHAR NOT NULL,"
         + ObjetContract.COL_QUANTITE    + " INTEGER NOT NULL,"
         + ObjetContract.COL_URLIMAGE    + " VARCHAR,"
         + ObjetContract.COL_TYPEOBJET_ID    + " INTEGER NOT NULL,"
         + ObjetContract.COL_PERSONNAGENONJOUEUR_ID    + " INTEGER,"

        
         + "FOREIGN KEY(" + ObjetContract.COL_TYPEOBJET_ID + ") REFERENCES " 
             + TypeObjetContract.TABLE_NAME 
                + " (" + TypeObjetContract.COL_ID + "),"
         + "FOREIGN KEY(" + ObjetContract.COL_PERSONNAGENONJOUEUR_ID + ") REFERENCES " 
             + PersonnageNonJoueurContract.TABLE_NAME 
                + " (" + PersonnageNonJoueurContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public ObjetSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert Objet entity to Content Values for database.
     * @param item Objet entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final Objet item) {
        return ObjetContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to Objet entity.
     * @param cursor android.database.Cursor object
     * @return Objet entity
     */
    public Objet cursorToItem(final android.database.Cursor cursor) {
        return ObjetContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to Objet entity.
     * @param cursor android.database.Cursor object
     * @param result Objet entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final Objet result) {
        ObjetContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read Objet by id in database.
     *
     * @param id Identify of Objet
     * @return Objet entity
     */
    public Objet getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final Objet result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getTypeObjet() != null) {
            final TypeObjetSQLiteAdapter typeObjetAdapter =
                    new TypeObjetSQLiteAdapter(this.ctx);
            typeObjetAdapter.open(this.mDatabase);

            result.setTypeObjet(typeObjetAdapter.getByID(
                            result.getTypeObjet().getId()));
        }
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
     * Find & read Objet by typeObjet.
     * @param typeobjetId typeobjetId
     * @param orderBy Order by string (can be null)
     * @return List of Objet entities
     */
     public android.database.Cursor getByTypeObjet(final int typeobjetId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = ObjetContract.COL_TYPEOBJET_ID + "= ?";
        String idSelectionArgs = String.valueOf(typeobjetId);
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
     * Find & read Objet by personnageNonJoueur.
     * @param personnagenonjoueurId personnagenonjoueurId
     * @param orderBy Order by string (can be null)
     * @return List of Objet entities
     */
     public android.database.Cursor getByPersonnageNonJoueur(final int personnagenonjoueurId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = ObjetContract.COL_PERSONNAGENONJOUEUR_ID + "= ?";
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
     * Read All Objets entities.
     *
     * @return List of Objet entities
     */
    public ArrayList<Objet> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<Objet> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a Objet entity into database.
     *
     * @param item The Objet entity to persist
     * @return Id of the Objet entity
     */
    public long insert(final Objet item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + ObjetContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ObjetContract.itemToContentValues(item);
        values.remove(ObjetContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    ObjetContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a Objet entity into database whether.
     * it already exists or not.
     *
     * @param item The Objet entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final Objet item) {
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
     * Update a Objet entity into database.
     *
     * @param item The Objet entity to persist
     * @return count of updated entities
     */
    public int update(final Objet item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + ObjetContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                ObjetContract.itemToContentValues(item);
        final String whereClause =
                 ObjetContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a Objet entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + ObjetContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                ObjetContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param objet The entity to delete
     * @return count of updated entities
     */
    public int delete(final Objet objet) {
        return this.remove(objet.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the Objet corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                ObjetContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                ObjetContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a Objet entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = ObjetContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                ObjetContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

