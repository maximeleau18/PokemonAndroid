/**************************************************************************
 * ZoneDataLoader.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.fixture;

import java.util.Map;




import com.maximeleau.harmony.android.pokemon.entity.Zone;


/**
 * ZoneDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class ZoneDataLoader
                        extends FixtureBase<Zone> {
    /** ZoneDataLoader name. */
    private static final String FILE_NAME = "Zone";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for arenes. */
    private static final String ARENES = "arenes";
    /** Constant field for badges. */
    private static final String BADGES = "badges";
    /** Constant field for positions. */
    private static final String POSITIONS = "positions";


    /** ZoneDataLoader instance (Singleton). */
    private static ZoneDataLoader instance;

    /**
     * Get the ZoneDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static ZoneDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new ZoneDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private ZoneDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Zone extractItem(final Map<?, ?> columns) {
        final Zone zone =
                new Zone();

        return this.extractItem(columns, zone);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param zone Entity to extract
     * @return A Zone entity
     */
    protected Zone extractItem(final Map<?, ?> columns,
                Zone zone) {
        zone.setId(this.parseIntField(columns, ID));
        zone.setNom(this.parseField(columns, NOM, String.class));
        zone.setArenes(this.parseMultiRelationField(columns, ARENES, AreneDataLoader.getInstance(this.ctx)));
        zone.setBadges(this.parseMultiRelationField(columns, BADGES, BadgeDataLoader.getInstance(this.ctx)));
        zone.setPositions(this.parseMultiRelationField(columns, POSITIONS, PositionDataLoader.getInstance(this.ctx)));

        return zone;
    }
    /**
     * Loads Zones into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Zone zone : this.items.values()) {
            int id = dataManager.persist(zone);
            zone.setId(id);

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
    protected Zone get(final String key) {
        final Zone result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
