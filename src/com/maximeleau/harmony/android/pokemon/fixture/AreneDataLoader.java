/**************************************************************************
 * AreneDataLoader.java, pokemon Android
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
import java.util.ArrayList;



import com.maximeleau.harmony.android.pokemon.entity.Arene;


/**
 * AreneDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class AreneDataLoader
                        extends FixtureBase<Arene> {
    /** AreneDataLoader name. */
    private static final String FILE_NAME = "Arene";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for maitre. */
    private static final String MAITRE = "maitre";
    /** Constant field for badge. */
    private static final String BADGE = "badge";
    /** Constant field for position. */
    private static final String POSITION = "position";


    /** AreneDataLoader instance (Singleton). */
    private static AreneDataLoader instance;

    /**
     * Get the AreneDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static AreneDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new AreneDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private AreneDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Arene extractItem(final Map<?, ?> columns) {
        final Arene arene =
                new Arene();

        return this.extractItem(columns, arene);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param arene Entity to extract
     * @return A Arene entity
     */
    protected Arene extractItem(final Map<?, ?> columns,
                Arene arene) {
        arene.setId(this.parseIntField(columns, ID));
        arene.setNom(this.parseField(columns, NOM, String.class));
        arene.setMaitre(this.parseSimpleRelationField(columns, MAITRE, PersonnageNonJoueurDataLoader.getInstance(this.ctx)));
        if (arene.getMaitre() != null) {
            if (arene.getMaitre().getArenes() == null) {
                arene.getMaitre().setArenes(
                        new ArrayList<Arene>());
            }
            
            arene.getMaitre().getArenes().add(arene);
        }
        arene.setBadge(this.parseSimpleRelationField(columns, BADGE, BadgeDataLoader.getInstance(this.ctx)));
        arene.setPosition(this.parseSimpleRelationField(columns, POSITION, PositionDataLoader.getInstance(this.ctx)));

        return arene;
    }
    /**
     * Loads Arenes into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Arene arene : this.items.values()) {
            int id = dataManager.persist(arene);
            arene.setId(id);

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
    protected Arene get(final String key) {
        final Arene result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
