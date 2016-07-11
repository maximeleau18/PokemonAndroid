/**************************************************************************
 * PositionDataLoader.java, pokemon Android
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



import com.maximeleau.harmony.android.pokemon.entity.Position;


/**
 * PositionDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PositionDataLoader
                        extends FixtureBase<Position> {
    /** PositionDataLoader name. */
    private static final String FILE_NAME = "Position";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for x. */
    private static final String X = "x";
    /** Constant field for y. */
    private static final String Y = "y";
    /** Constant field for zone. */
    private static final String ZONE = "zone";


    /** PositionDataLoader instance (Singleton). */
    private static PositionDataLoader instance;

    /**
     * Get the PositionDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PositionDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PositionDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PositionDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Position extractItem(final Map<?, ?> columns) {
        final Position position =
                new Position();

        return this.extractItem(columns, position);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param position Entity to extract
     * @return A Position entity
     */
    protected Position extractItem(final Map<?, ?> columns,
                Position position) {
        position.setId(this.parseIntField(columns, ID));
        position.setX(this.parseIntField(columns, X));
        position.setY(this.parseIntField(columns, Y));
        position.setZone(this.parseSimpleRelationField(columns, ZONE, ZoneDataLoader.getInstance(this.ctx)));

        return position;
    }
    /**
     * Loads Positions into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Position position : this.items.values()) {
            int id = dataManager.persist(position);
            position.setId(id);

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
    protected Position get(final String key) {
        final Position result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
