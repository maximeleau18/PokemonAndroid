
/**************************************************************************
 * PersonnageNonJoueurSQLiteAdapterBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.ProfessionSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.ObjetSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.DresseurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.AreneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.data.PokemonSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ProfessionContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ObjetContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.entity.Profession;
import com.maximeleau.harmony.android.pokemon.entity.Objet;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;


import com.maximeleau.harmony.android.pokemon.PokemonApplication;



/** PersonnageNonJoueur adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit PersonnageNonJoueurAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
public abstract class PersonnageNonJoueurSQLiteAdapterBase
                        extends SQLiteAdapter<PersonnageNonJoueur> {

    /** TAG for debug purpose. */
    protected static final String TAG = "PersonnageNonJoueurDBAdapter";


    /**
     * Get the table name used in DB for your PersonnageNonJoueur entity.
     * @return A String showing the table name
     */
    public String getTableName() {
        return PersonnageNonJoueurContract.TABLE_NAME;
    }

    /**
     * Get the joined table name used in DB for your PersonnageNonJoueur entity
     * and its parents.
     * @return A String showing the joined table name
     */
    public String getJoinedTableName() {
        String result = PersonnageNonJoueurContract.TABLE_NAME;
        return result;
    }

    /**
     * Get the column names from the PersonnageNonJoueur entity table.
     * @return An array of String representing the columns
     */
    public String[] getCols() {
        return PersonnageNonJoueurContract.ALIASED_COLS;
    }

    /**
     * Generate Entity Table Schema.
     * @return "SQL query : CREATE TABLE..."
     */
    public static String getSchema() {
        return "CREATE TABLE "
        + PersonnageNonJoueurContract.TABLE_NAME    + " ("
        
         + PersonnageNonJoueurContract.COL_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT,"
         + PersonnageNonJoueurContract.COL_NOM    + " VARCHAR NOT NULL,"
         + PersonnageNonJoueurContract.COL_DESCRIPTION    + " VARCHAR NOT NULL,"
         + PersonnageNonJoueurContract.COL_URLIMAGE    + " VARCHAR,"
         + PersonnageNonJoueurContract.COL_PROFESSION_ID    + " INTEGER NOT NULL,"

        
         + "FOREIGN KEY(" + PersonnageNonJoueurContract.COL_PROFESSION_ID + ") REFERENCES " 
             + ProfessionContract.TABLE_NAME 
                + " (" + ProfessionContract.COL_ID + ")"
        + ");"
;
    }

    /**
     * Constructor.
     * @param ctx context
     */
    public PersonnageNonJoueurSQLiteAdapterBase(final android.content.Context ctx) {
        super(ctx);
    }

    // Converters

    /**
     * Convert PersonnageNonJoueur entity to Content Values for database.
     * @param item PersonnageNonJoueur entity object
     * @return ContentValues object
     */
    public ContentValues itemToContentValues(final PersonnageNonJoueur item) {
        return PersonnageNonJoueurContract.itemToContentValues(item);
    }

    /**
     * Convert android.database.Cursor of database to PersonnageNonJoueur entity.
     * @param cursor android.database.Cursor object
     * @return PersonnageNonJoueur entity
     */
    public PersonnageNonJoueur cursorToItem(final android.database.Cursor cursor) {
        return PersonnageNonJoueurContract.cursorToItem(cursor);
    }

    /**
     * Convert android.database.Cursor of database to PersonnageNonJoueur entity.
     * @param cursor android.database.Cursor object
     * @param result PersonnageNonJoueur entity
     */
    public void cursorToItem(final android.database.Cursor cursor, final PersonnageNonJoueur result) {
        PersonnageNonJoueurContract.cursorToItem(cursor, result);
    }

    //// CRUD Entity ////
    /**
     * Find & read PersonnageNonJoueur by id in database.
     *
     * @param id Identify of PersonnageNonJoueur
     * @return PersonnageNonJoueur entity
     */
    public PersonnageNonJoueur getByID(final int id) {
        final android.database.Cursor cursor = this.getSingleCursor(id);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
        }

        final PersonnageNonJoueur result = this.cursorToItem(cursor);
        cursor.close();

        if (result.getProfession() != null) {
            final ProfessionSQLiteAdapter professionAdapter =
                    new ProfessionSQLiteAdapter(this.ctx);
            professionAdapter.open(this.mDatabase);

            result.setProfession(professionAdapter.getByID(
                            result.getProfession().getId()));
        }
        final ObjetSQLiteAdapter objetsAdapter =
                new ObjetSQLiteAdapter(this.ctx);
        objetsAdapter.open(this.mDatabase);
        android.database.Cursor objetsCursor = objetsAdapter
                    .getByPersonnageNonJoueur(
                            result.getId(),
                            ObjetContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setObjets(objetsAdapter.cursorToItems(objetsCursor));

        objetsCursor.close();
        final DresseurSQLiteAdapter dresseursAdapter =
                new DresseurSQLiteAdapter(this.ctx);
        dresseursAdapter.open(this.mDatabase);
        android.database.Cursor dresseursCursor = dresseursAdapter
                    .getByPersonnageNonJoueur(
                            result.getId(),
                            DresseurContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setDresseurs(dresseursAdapter.cursorToItems(dresseursCursor));

        dresseursCursor.close();
        final AreneSQLiteAdapter arenesAdapter =
                new AreneSQLiteAdapter(this.ctx);
        arenesAdapter.open(this.mDatabase);
        android.database.Cursor arenesCursor = arenesAdapter
                    .getByMaitre(
                            result.getId(),
                            AreneContract.ALIASED_COLS,
                            null,
                            null,
                            null);
        result.setArenes(arenesAdapter.cursorToItems(arenesCursor));

        arenesCursor.close();
        final PokemonSQLiteAdapter pokemonsAdapter =
                new PokemonSQLiteAdapter(this.ctx);
        pokemonsAdapter.open(this.mDatabase);
        android.database.Cursor pokemonsCursor = pokemonsAdapter
                    .getByPersonnageNonJoueur(
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
     * Find & read PersonnageNonJoueur by profession.
     * @param professionId professionId
     * @param orderBy Order by string (can be null)
     * @return List of PersonnageNonJoueur entities
     */
     public android.database.Cursor getByProfession(final int professionId, String[] projection, String selection, String[] selectionArgs, String orderBy) {
        String idSelection = PersonnageNonJoueurContract.COL_PROFESSION_ID + "= ?";
        String idSelectionArgs = String.valueOf(professionId);
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
     * Read All PersonnageNonJoueurs entities.
     *
     * @return List of PersonnageNonJoueur entities
     */
    public ArrayList<PersonnageNonJoueur> getAll() {
        final android.database.Cursor cursor = this.getAllCursor();
        final ArrayList<PersonnageNonJoueur> result = this.cursorToItems(cursor);
        cursor.close();

        return result;
    }



    /**
     * Insert a PersonnageNonJoueur entity into database.
     *
     * @param item The PersonnageNonJoueur entity to persist
     * @return Id of the PersonnageNonJoueur entity
     */
    public long insert(final PersonnageNonJoueur item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Insert DB(" + PersonnageNonJoueurContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PersonnageNonJoueurContract.itemToContentValues(item);
        values.remove(PersonnageNonJoueurContract.COL_ID);
        int insertResult;
        if (values.size() != 0) {
            insertResult = (int) this.insert(
                    null,
                    values);
        } else {
            insertResult = (int) this.insert(
                    PersonnageNonJoueurContract.COL_ID,
                    values);
        }
        item.setId(insertResult);
        if (item.getObjets() != null) {
            ObjetSQLiteAdapterBase objetsAdapter =
                    new ObjetSQLiteAdapter(this.ctx);
            objetsAdapter.open(this.mDatabase);
            for (Objet objet
                        : item.getObjets()) {
                objet.setPersonnageNonJoueur(item);
                objetsAdapter.insertOrUpdate(objet);
            }
        }
        if (item.getDresseurs() != null) {
            DresseurSQLiteAdapterBase dresseursAdapter =
                    new DresseurSQLiteAdapter(this.ctx);
            dresseursAdapter.open(this.mDatabase);
            for (Dresseur dresseur
                        : item.getDresseurs()) {
                dresseur.setPersonnageNonJoueur(item);
                dresseursAdapter.insertOrUpdate(dresseur);
            }
        }
        if (item.getArenes() != null) {
            AreneSQLiteAdapterBase arenesAdapter =
                    new AreneSQLiteAdapter(this.ctx);
            arenesAdapter.open(this.mDatabase);
            for (Arene arene
                        : item.getArenes()) {
                arene.setMaitre(item);
                arenesAdapter.insertOrUpdate(arene);
            }
        }
        if (item.getPokemons() != null) {
            PokemonSQLiteAdapterBase pokemonsAdapter =
                    new PokemonSQLiteAdapter(this.ctx);
            pokemonsAdapter.open(this.mDatabase);
            for (Pokemon pokemon
                        : item.getPokemons()) {
                pokemon.setPersonnageNonJoueur(item);
                pokemonsAdapter.insertOrUpdate(pokemon);
            }
        }
        return insertResult;
    }

    /**
     * Either insert or update a PersonnageNonJoueur entity into database whether.
     * it already exists or not.
     *
     * @param item The PersonnageNonJoueur entity to persist
     * @return 1 if everything went well, 0 otherwise
     */
    public int insertOrUpdate(final PersonnageNonJoueur item) {
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
     * Update a PersonnageNonJoueur entity into database.
     *
     * @param item The PersonnageNonJoueur entity to persist
     * @return count of updated entities
     */
    public int update(final PersonnageNonJoueur item) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Update DB(" + PersonnageNonJoueurContract.TABLE_NAME + ")");
        }

        final ContentValues values =
                PersonnageNonJoueurContract.itemToContentValues(item);
        final String whereClause =
                 PersonnageNonJoueurContract.COL_ID
                 + " = ?";
        final String[] whereArgs =
                new String[] {String.valueOf(item.getId()) };

        return this.update(
                values,
                whereClause,
                whereArgs);
    }


    /**
     * Delete a PersonnageNonJoueur entity of database.
     *
     * @param id id
     * @return count of updated entities
     */
    public int remove(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(
                TAG,
                "Delete DB("
                    + PersonnageNonJoueurContract.TABLE_NAME
                    + ")"
                    + " id : "+ id);
        }

        final String whereClause =
                PersonnageNonJoueurContract.COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {
                    String.valueOf(id)};

        return this.delete(
                whereClause,
                whereArgs);
    }

    /**
     * Deletes the given entity.
     * @param personnageNonJoueur The entity to delete
     * @return count of updated entities
     */
    public int delete(final PersonnageNonJoueur personnageNonJoueur) {
        return this.remove(personnageNonJoueur.getId());
    }

    /**
     *  Internal android.database.Cursor.
     * @param id id
     *  @return A android.database.Cursor pointing to the PersonnageNonJoueur corresponding
     *        to the given id.
     */
    protected android.database.Cursor getSingleCursor(final int id) {
        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Get entities id : " + id);
        }

        final String whereClause =
                PersonnageNonJoueurContract.ALIASED_COL_ID
                + " = ?";
        final String[] whereArgs = new String[] {String.valueOf(id)};

        return this.query(
                PersonnageNonJoueurContract.ALIASED_COLS,
                whereClause,
                whereArgs,
                null,
                null,
                null);
    }


    /**
     * Query the DB to find a PersonnageNonJoueur entity.
     *
     * @param id The id of the entity to get from the DB
     *
     * @return The cursor pointing to the query's result
     */
    public android.database.Cursor query(final int id) {

        String selection = PersonnageNonJoueurContract.ALIASED_COL_ID + " = ?";
        

        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(id);

        return this.query(
                PersonnageNonJoueurContract.ALIASED_COLS,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }




}

