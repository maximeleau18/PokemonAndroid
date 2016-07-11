/**************************************************************************
 * ProfessionDataLoader.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 10, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.fixture;

import java.util.Map;




import com.maximeleau.harmony.android.pokemon.entity.Profession;


/**
 * ProfessionDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class ProfessionDataLoader
                        extends FixtureBase<Profession> {
    /** ProfessionDataLoader name. */
    private static final String FILE_NAME = "Profession";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";


    /** ProfessionDataLoader instance (Singleton). */
    private static ProfessionDataLoader instance;

    /**
     * Get the ProfessionDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static ProfessionDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new ProfessionDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private ProfessionDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Profession extractItem(final Map<?, ?> columns) {
        final Profession profession =
                new Profession();

        return this.extractItem(columns, profession);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param profession Entity to extract
     * @return A Profession entity
     */
    protected Profession extractItem(final Map<?, ?> columns,
                Profession profession) {
        profession.setId(this.parseIntField(columns, ID));
        profession.setNom(this.parseField(columns, NOM, String.class));

        return profession;
    }
    /**
     * Loads Professions into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Profession profession : this.items.values()) {
            int id = dataManager.persist(profession);
            profession.setId(id);

        }
        dataManager.flush();
    }

    /**
     * Give priority for fixtures insertion in database.
     * 0 is the first.
     * @return The order
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * Get the fixture file name.
     * @return A String representing the file name
     */
    @Override
    public String getFixtureFileName() {
        return FILE_NAME;
    }

    @Override
    protected Profession get(final String key) {
        final Profession result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
