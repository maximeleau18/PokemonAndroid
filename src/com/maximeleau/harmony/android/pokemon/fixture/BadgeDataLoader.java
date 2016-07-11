/**************************************************************************
 * BadgeDataLoader.java, pokemon Android
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
import java.util.ArrayList;



import com.maximeleau.harmony.android.pokemon.entity.Badge;


/**
 * BadgeDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class BadgeDataLoader
                        extends FixtureBase<Badge> {
    /** BadgeDataLoader name. */
    private static final String FILE_NAME = "Badge";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";


    /** BadgeDataLoader instance (Singleton). */
    private static BadgeDataLoader instance;

    /**
     * Get the BadgeDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static BadgeDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new BadgeDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private BadgeDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Badge extractItem(final Map<?, ?> columns) {
        final Badge badge =
                new Badge();

        return this.extractItem(columns, badge);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param badge Entity to extract
     * @return A Badge entity
     */
    protected Badge extractItem(final Map<?, ?> columns,
                Badge badge) {
        badge.setId(this.parseIntField(columns, ID));
        badge.setNom(this.parseField(columns, NOM, String.class));

        return badge;
    }
    /**
     * Loads Badges into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Badge badge : this.items.values()) {
            int id = dataManager.persist(badge);
            badge.setId(id);

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
    protected Badge get(final String key) {
        final Badge result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
