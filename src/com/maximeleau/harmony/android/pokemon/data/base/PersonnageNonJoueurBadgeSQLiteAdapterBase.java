
/**************************************************************************
 * PersonnageNonJoueurBadgeSQLiteAdapterBase.java, pokemon Android
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
import android.content.ContentValues;


import android.database.sqlite.SQLiteDatabase;


import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.maximeleau.harmony.android.pokemon.data.SQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurBadgeSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.BadgeSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurBadgeContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueurBadge;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.entity.Badge;


import com.maximeleau.harmony.android.pokemon.PokemonApplication;



/** PersonnageNonJoueurBadge adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PersonnageNonJoueurBadgeAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PersonnageNonJoueurBadgeSQLiteAdapterBase
                        extends SQLiteAdapter<PersonnageNonJoueurBadge> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PersonnageNonJoueurBadgeDBAdapter";


    /**
     * Get the table name used in DB for your PersonnageNonJoueurBadge entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PersonnageNonJoueurBadgeContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your PersonnageNonJoueurBadge entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PersonnageNonJoueurBadgeContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the PersonnageNonJoueurBadge entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PersonnageNonJoueurBadgeContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PersonnageNonJoueurBadgeContract.TABLE_NAME    + " ("
        
         + PersonnageNonJoueurBadgeContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PersonnageNonJoueurBadgeContract.COL_PERSONNAGENONJOUEUR_ID    + " INTEGER NOT NULL,"
         + PersonnageNonJoueurBadgeContract.COL_BADGE_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + PersonnageNonJoueurBadgeContract.COL_PERSONNAGENONJOUEUR_ID + ") REFERENCES " 
             + PersonnageNonJoueurContract.TABLE_NAME 
                + " (" + PersonnageNonJoueurContract.COL_ID + "),"
         + "FOREIGN KEY(" + PersonnageNonJoueurBadgeContract.COL_BADGE_ID + ") REFERENCES " 
             + BadgeContract.TABLE_NAME 
                + " (" + BadgeContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PersonnageNonJoueurBadgeSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert PersonnageNonJoueurBadge entity to Content Values for database.
     * @param item PersonnageNonJoueurBadge entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final PersonnageNonJoueurBadge item) {
        return PersonnageNonJoueurBadgeContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to PersonnageNonJoueurBadge entity.
     * @param cursor android.database.Cursor object
     * @return PersonnageNonJoueurBadge entity
     */
    public PersonnageNonJoueurBadge cursorToItem(final android.database.Cursor cursor) {
        return PersonnageNonJoueurBadgeContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to PersonnageNonJoueurBadge entity.
     * @param cursor android.database.Cursor object
     * @param result PersonnageNonJoueurBadge entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final PersonnageNonJoueurBadge result) {
        PersonnageNonJoueurBadgeContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read PersonnageNonJoueurBadge by id in database.
     *
     * @param id Identify of PersonnageNonJoueurBadge
     * @return PersonnageNonJoueurBadge entity
     */
    public PersonnageNonJoueurBadge getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final PersonnageNonJoueurBadge result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getPersonnageNonJoueur() != null) {
            final PersonnageNonJoueurSQLiteAdapter personnageNonJoueurAdapter =
                    new PersonnageNonJoueurSQLiteAdapter(this.ctx);
            personnageNonJoueurAdapter.open(this.mDatabase);

            result.setPersonnageNonJoueur(personnageNonJoueurAdapter.getByID(
                            result.getPersonnageNonJoueur().getId()));
        }
        if (result.getBadge() != null) {
            final BadgeSQLiteAdapter badgeAdapter =
                    new BadgeSQLiteAdapter(this.ctx);
            badgeAdapter.open(this.mDatabase);

            result.setBadge(badgeAdapter.getByID(
                            result.getBadge().getId()));
        }
        return result;
    }

    /**
     * Find & read PersonnageNonJoueurBadge by personnageNonJoueur.
     * @param personnagenonjoueurId personnagenonjoueurId
     * @param orderBy Order by string (can be null)
     * @return List of PersonnageNonJoueurBadge entities
     */
     public android.database.Cursor getByPersonnageNonJoueur(final int personnagenonjoueurId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PersonnageNonJoueurBadgeContract.COL_PERSONNAGENONJOUEUR_ID + "= ?";
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
     * Find & read PersonnageNonJoueurBadge by badge.
     * @param badgeId badgeId
     * @param orderBy Order by string (can be null)
     * @return List of PersonnageNonJoueurBadge entities
     */
     public android.database.Cursor getByBadge(final int badgeId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PersonnageNonJoueurBadgeContract.COL_BADGE_ID + "= ?";
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
     * Read All PersonnageNonJoueurBadges entities.
     *
     * @return List of PersonnageNonJoueurBadge entities
     */
    public ArrayList<PersonnageNonJoueurBadge> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<PersonnageNonJoueurBadge> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a PersonnageNonJoueurBadge entity into database.
     *
     * @param item The PersonnageNonJoueurBadge entity to persist
     * @return Id of the PersonnageNonJoueurBadge entity
     */
    public long insert(final PersonnageNonJoueurBadge item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PersonnageNonJoueurBadgeContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PersonnageNonJoueurBadgeContract.itemToContentValues(item);
        values.remove(PersonnageNonJoueurBadgeContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PersonnageNonJoueurBadgeContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        return insertResult;
    }

    /**
     * Either insert or update a PersonnageNonJoueurBadge entity into database whether.
     * it already exists or not.
     *
     * @param item The PersonnageNonJoueurBadge entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final PersonnageNonJoueurBadge item) {
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
     * Update a PersonnageNonJoueurBadge entity into database.
     *
     * @param item The PersonnageNonJoueurBadge entity to persist
     * @return count of updated entities
     */
    public int update(final PersonnageNonJoueurBadge item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PersonnageNonJoueurBadgeContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PersonnageNonJoueurBadgeContract.itemToContentValues(item);
        final String whereClause =
                 PersonnageNonJoueurBadgeContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a PersonnageNonJoueurBadge entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PersonnageNonJoueurBadgeContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PersonnageNonJoueurBadgeContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param personnageNonJoueurBadge The entity to delete
     * @return count of updated entities
     */
    public int delete(final PersonnageNonJoueurBadge personnageNonJoueurBadge) {
        return this.remove(personnageNonJoueurBadge.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the PersonnageNonJoueurBadge corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PersonnageNonJoueurBadgeContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PersonnageNonJoueurBadgeContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a PersonnageNonJoueurBadge entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PersonnageNonJoueurBadgeContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PersonnageNonJoueurBadgeContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

