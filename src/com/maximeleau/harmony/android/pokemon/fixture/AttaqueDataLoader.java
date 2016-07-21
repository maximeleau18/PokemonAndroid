/**************************************************************************
 * AttaqueDataLoader.java, pokemon Android
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




import com.maximeleau.harmony.android.pokemon.entity.Attaque;


/**
 * AttaqueDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class AttaqueDataLoader
                        extends FixtureBase<Attaque> {
    /** AttaqueDataLoader name. */
    private static final String FILE_NAME = "Attaque";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for puissance. */
    private static final String PUISSANCE = "puissance";
    /** Constant field for degats. */
    private static final String DEGATS = "degats";
    /** Constant field for typeAttaque. */
    private static final String TYPEATTAQUE = "typeAttaque";


    /** AttaqueDataLoader instance (Singleton). */
    private static AttaqueDataLoader instance;

    /**
     * Get the AttaqueDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static AttaqueDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new AttaqueDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private AttaqueDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Attaque extractItem(final Map<?, ?> columns) {
        final Attaque attaque =
                new Attaque();

        return this.extractItem(columns, attaque);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param attaque Entity to extract
     * @return A Attaque entity
     */
    protected Attaque extractItem(final Map<?, ?> columns,
                Attaque attaque) {
        attaque.setId(this.parseIntField(columns, ID));
        attaque.setNom(this.parseField(columns, NOM, String.class));
        attaque.setPuissance(this.parseIntField(columns, PUISSANCE));
        attaque.setDegats(this.parseIntField(columns, DEGATS));
        attaque.setTypeAttaque(this.parseSimpleRelationField(columns, TYPEATTAQUE, TypeAttaqueDataLoader.getInstance(this.ctx)));

        return attaque;
    }
    /**
     * Loads Attaques into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Attaque attaque : this.items.values()) {
            int id = dataManager.persist(attaque);
            attaque.setId(id);

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
    protected Attaque get(final String key) {
        final Attaque result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
