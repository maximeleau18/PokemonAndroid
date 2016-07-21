
/**************************************************************************
 * PokemonSQLiteOpenHelperBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.data.base;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.maximeleau.harmony.android.pokemon.data.PokemonSQLiteOpenHelper;
import com.maximeleau.harmony.android.pokemon.data.ObjetSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.ObjetContract;
import com.maximeleau.harmony.android.pokemon.data.AreneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;
import com.maximeleau.harmony.android.pokemon.data.PositionSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.PositionContract;
import com.maximeleau.harmony.android.pokemon.data.ZoneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;
import com.maximeleau.harmony.android.pokemon.data.TypeAttaqueSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeAttaqueContract;
import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;
import com.maximeleau.harmony.android.pokemon.data.BadgeSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;
import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonEvolutionSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonEvolutionContract;
import com.maximeleau.harmony.android.pokemon.data.DresseurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;
import com.maximeleau.harmony.android.pokemon.data.TypeObjetSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeObjetContract;
import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurBadgeSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurBadgeContract;
import com.maximeleau.harmony.android.pokemon.data.ProfessionSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.ProfessionContract;
import com.maximeleau.harmony.android.pokemon.data.AttaqueSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.AttaqueContract;
import com.maximeleau.harmony.android.pokemon.data.CombatSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.CombatContract;
import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonZoneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonZoneContract;
import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;
import com.maximeleau.harmony.android.pokemon.data.PokemonSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;
import com.maximeleau.harmony.android.pokemon.PokemonApplication;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


import com.maximeleau.harmony.android.pokemon.fixture.DataLoader;


/**
 * This class makes it easy for ContentProvider implementations to defer <br />
 * opening and upgrading the database until first use, to avoid blocking <br />
 * application startup with long-running database upgrades.
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class PokemonSQLiteOpenHelperBase extends SQLiteOpenHelper {
    /** TAG for debug purpose. */
    protected static final String TAG = "DatabaseHelper";
    /** Context. */
    protected android.content.Context ctx;

    /** Android's default system path of the database. */
    private static String DB_PATH;
    /** database name. */
    private static String DB_NAME;
    /** is assets exist.*/
    private static boolean assetsExist;
    /** Are we in a JUnit context ?*/
    public static boolean isJUnit = false;

    /**
     * Constructor.
     * @param ctx Context
     * @param name name
     * @param factory factory
     * @param version version
     */
    public PokemonSQLiteOpenHelperBase(final android.content.Context ctx,
           final String name, final CursorFactory factory, final int version) {
        super(ctx, name, factory, version);
        this.ctx = ctx;
        DB_NAME = name;
        DB_PATH = ctx.getDatabasePath(DB_NAME).getAbsolutePath();

        try {
            this.ctx.getAssets().open(DB_NAME);
            assetsExist = true;
        } catch (IOException e) {
            assetsExist = false;
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // Activation of SQLiteConstraints
        //db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        android.util.Log.i(TAG, "Create database..");

        if (!assetsExist) {
            /// Create Schema

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Objet");
            }
            db.execSQL(ObjetSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Arene");
            }
            db.execSQL(AreneSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Position");
            }
            db.execSQL(PositionSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Zone");
            }
            db.execSQL(ZoneSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : TypeAttaque");
            }
            db.execSQL(TypeAttaqueSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : TypeDePokemon");
            }
            db.execSQL(TypeDePokemonSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Badge");
            }
            db.execSQL(BadgeSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : TypeDePokemonEvolution");
            }
            db.execSQL(TypeDePokemonEvolutionSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Dresseur");
            }
            db.execSQL(DresseurSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : TypeObjet");
            }
            db.execSQL(TypeObjetSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : PersonnageNonJoueurBadge");
            }
            db.execSQL(PersonnageNonJoueurBadgeSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Profession");
            }
            db.execSQL(ProfessionSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Attaque");
            }
            db.execSQL(AttaqueSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Combat");
            }
            db.execSQL(CombatSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : TypeDePokemonZone");
            }
            db.execSQL(TypeDePokemonZoneSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : PersonnageNonJoueur");
            }
            db.execSQL(PersonnageNonJoueurSQLiteAdapter.getSchema());

            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, "Creating schema : Pokemon");
            }
            db.execSQL(PokemonSQLiteAdapter.getSchema());
            db.execSQL("PRAGMA foreign_keys = ON;");
            if (!PokemonSQLiteOpenHelper.isJUnit) {
                this.loadData(db);
            }
        }

    }

    /**
     * Clear the database given in parameters.
     * @param db The database to clear
     */
    public static void clearDatabase(final SQLiteDatabase db) {
        android.util.Log.i(TAG, "Clearing database...");

        db.delete(ObjetContract.TABLE_NAME,
                null,
                null);
        db.delete(AreneContract.TABLE_NAME,
                null,
                null);
        db.delete(PositionContract.TABLE_NAME,
                null,
                null);
        db.delete(ZoneContract.TABLE_NAME,
                null,
                null);
        db.delete(TypeAttaqueContract.TABLE_NAME,
                null,
                null);
        db.delete(TypeDePokemonContract.TABLE_NAME,
                null,
                null);
        db.delete(BadgeContract.TABLE_NAME,
                null,
                null);
        db.delete(TypeDePokemonEvolutionContract.TABLE_NAME,
                null,
                null);
        db.delete(DresseurContract.TABLE_NAME,
                null,
                null);
        db.delete(TypeObjetContract.TABLE_NAME,
                null,
                null);
        db.delete(PersonnageNonJoueurBadgeContract.TABLE_NAME,
                null,
                null);
        db.delete(ProfessionContract.TABLE_NAME,
                null,
                null);
        db.delete(AttaqueContract.TABLE_NAME,
                null,
                null);
        db.delete(CombatContract.TABLE_NAME,
                null,
                null);
        db.delete(TypeDePokemonZoneContract.TABLE_NAME,
                null,
                null);
        db.delete(PersonnageNonJoueurContract.TABLE_NAME,
                null,
                null);
        db.delete(PokemonContract.TABLE_NAME,
                null,
                null);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
            final int newVersion) {
        android.util.Log.i(TAG, "Update database..");

        if (PokemonApplication.DEBUG) {
            android.util.Log.d(TAG, "Upgrading database from version " + oldVersion
                       + " to " + newVersion);
        }

        // TODO : Upgrade your tables !
    }

    /**
     * Loads data from the fixture files.
     * @param db The database to populate with fixtures
     */
    private void loadData(final SQLiteDatabase db) {
        final DataLoader dataLoader = new DataLoader(this.ctx);
        dataLoader.clean();
        int mode = DataLoader.MODE_APP;
        if (PokemonApplication.DEBUG) {
            mode = DataLoader.MODE_APP | DataLoader.MODE_DEBUG;
        }
        dataLoader.loadData(db, mode);
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     * @throws IOException if error has occured while copying files
     */
    public void createDataBase() throws IOException {
        if (assetsExist && !checkDataBase()) {
            // By calling this method and empty database will be created into
            // the default system path
            // so we're gonna be able to overwrite that database with ours
            this.getReadableDatabase();

            try {
                copyDataBase();

            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        boolean result;

        SQLiteDatabase checkDB = null;
        try {
            final String myPath = DB_PATH + DB_NAME;
            // NOTE : the system throw error message : "Database is locked"
            // when the Database is not found (incorrect path)
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
            result = true;
        } catch (SQLiteException e) {
            // database doesn't exist yet.
            result = false;
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return result;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     * @throws IOException if error has occured while copying files
     * */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        final InputStream myInput = this.ctx.getAssets().open(DB_NAME);

        // Path to the just created empty db
        final String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        final OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        final byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while (length > 0) {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
}
