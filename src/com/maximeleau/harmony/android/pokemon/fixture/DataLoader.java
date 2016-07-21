/**************************************************************************
 * DataLoader.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.fixture;

import java.util.ArrayList;
import java.util.List;


import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;


import com.maximeleau.harmony.android.pokemon.PokemonApplication;

/**
 * DataLoader for fixture purpose.
 * 
 * The Dataloader is a useful class to wrap the fixture loading 
 * for various entities.
 * The order in which the fixtures are loaded are the order in which
 * they are put in the "dataLoaders" list.
 */
public class DataLoader {
    /** TAG for debug purpose. */
    protected static final String TAG = "DataLoader";

    /** Test mode. */
    public static final int MODE_TEST     = Integer.parseInt("0001", 2);
    /** Application mode. */
    public static final int MODE_APP     = Integer.parseInt("0010", 2);
    /** Debug mode. */
    public static final int MODE_DEBUG     = Integer.parseInt("0100", 2);
    /** List of DataLoaders. */
    private List<FixtureBase<?>> dataLoaders;
    /** List of Fixture folders. */
    private static SparseArray<String> fixtureFolders;
    /** android.content.Context. */
    private android.content.Context ctx;
    /** Has the fixtures been loaded yet ? */
    public static boolean hasFixturesBeenLoaded = false;

    /**
     * Static constructor.
     */
    static {
        fixtureFolders = new SparseArray<String>();

        // Add your own folder and mode here for new fixture cases
        fixtureFolders.put(MODE_APP, "app/");
        fixtureFolders.put(MODE_DEBUG, "debug/");
        fixtureFolders.put(MODE_TEST, "test/");
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    public DataLoader(final android.content.Context ctx) {
        this.ctx = ctx;
        this.dataLoaders = new ArrayList<FixtureBase<?>>();
        this.dataLoaders.add(
                TypeAttaqueDataLoader.getInstance(this.ctx));
        this.dataLoaders.add(
                TypeObjetDataLoader.getInstance(this.ctx));
        this.dataLoaders.add(
                ProfessionDataLoader.getInstance(this.ctx));
        this.dataLoaders.add(
                ObjetDataLoader.getInstance(this.ctx));
        this.dataLoaders.add(
                ZoneDataLoader.getInstance(this.ctx));
        this.dataLoaders.add(
                TypeDePokemonDataLoader.getInstance(this.ctx));
        this.dataLoaders.add(
                BadgeDataLoader.getInstance(this.ctx));
        this.dataLoaders.add(
                TypeDePokemonEvolutionDataLoader.getInstance(this.ctx));
        this.dataLoaders.add(
                AttaqueDataLoader.getInstance(this.ctx));
        this.dataLoaders.add(
                TypeDePokemonZoneDataLoader.getInstance(this.ctx));
        this.dataLoaders.add(
                PersonnageNonJoueurDataLoader.getInstance(this.ctx));
        this.dataLoaders.add(
                PokemonDataLoader.getInstance(this.ctx));
        this.dataLoaders.add(
                PositionDataLoader.getInstance(this.ctx));
        this.dataLoaders.add(
                DresseurDataLoader.getInstance(this.ctx));
        this.dataLoaders.add(
                PersonnageNonJoueurBadgeDataLoader.getInstance(this.ctx));
        this.dataLoaders.add(
                CombatDataLoader.getInstance(this.ctx));
        this.dataLoaders.add(
                AreneDataLoader.getInstance(this.ctx));
    }

    /**
     * LoadData from fixtures.
     * @param db The DB to work in
     * @param modes Mode
     */
    public void loadData(final SQLiteDatabase db, final int modes) {
        android.util.Log.i(TAG, "Initializing fixtures.");
        final DataManager manager = new DataManager(this.ctx, db);
        for (final FixtureBase<?> dataLoader : this.dataLoaders) {
            if (PokemonApplication.DEBUG) {
                android.util.Log.d(TAG, String.format(
                        "Loading %s fixtures",
                        dataLoader.getFixtureFileName()));
            }

            if (this.isType(modes, MODE_APP)) {
                if (PokemonApplication.DEBUG) {
                    android.util.Log.d(TAG, "Loading APP fixtures");
                }

                dataLoader.getModelFixtures(MODE_APP);
            }
            if (this.isType(modes, MODE_DEBUG)) {
                if (PokemonApplication.DEBUG) {
                    android.util.Log.d(TAG, "Loading DEBUG fixtures");
                }

                dataLoader.getModelFixtures(MODE_DEBUG);
            }
            if (this.isType(modes, MODE_TEST)) {
                if (PokemonApplication.DEBUG) {
                    android.util.Log.d(TAG, "Loading TEST fixtures");
                }

                dataLoader.getModelFixtures(MODE_TEST);
            }
            dataLoader.load(manager);
        }

        // After getting all the informations from the fixture,
        // serialize the datas.
        for (final FixtureBase<?> dataLoader : this.dataLoaders) {
            dataLoader.backup();
        }
        hasFixturesBeenLoaded = true;
    }

    /**
     * isType.
     * @param modes Modes
     * @param mode Mode
     * @return true if mode
     */
    private boolean isType(final int modes, final int mode) {
        boolean result;

        if ((modes & mode) == mode) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    /**
     * Get path to fixtures.
     * @param mode Mode
     * @return A String representing the path to fixtures
     */
    public static String getPathToFixtures(final int mode) {
        return fixtureFolders.get(mode);
    }

    /**
     * Clean dataLoaders.
     */
    public void clean() {
        for (FixtureBase<?> dataLoader: this.dataLoaders) {
            dataLoader.items.clear();
        }
    }
}
