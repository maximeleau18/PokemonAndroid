/**************************************************************************
 * TypeAttaqueDataLoader.java, pokemon Android
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




import com.maximeleau.harmony.android.pokemon.entity.TypeAttaque;


/**
 * TypeAttaqueDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class TypeAttaqueDataLoader
                        extends FixtureBase<TypeAttaque> {
    /** TypeAttaqueDataLoader name. */
    private static final String FILE_NAME = "TypeAttaque";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";


    /** TypeAttaqueDataLoader instance (Singleton). */
    private static TypeAttaqueDataLoader instance;

    /**
     * Get the TypeAttaqueDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static TypeAttaqueDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new TypeAttaqueDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private TypeAttaqueDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected TypeAttaque extractItem(final Map<?, ?> columns) {
        final TypeAttaque typeAttaque =
                new TypeAttaque();

        return this.extractItem(columns, typeAttaque);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param typeAttaque Entity to extract
     * @return A TypeAttaque entity
     */
    protected TypeAttaque extractItem(final Map<?, ?> columns,
                TypeAttaque typeAttaque) {
        typeAttaque.setId(this.parseIntField(columns, ID));
        typeAttaque.setNom(this.parseField(columns, NOM, String.class));

        return typeAttaque;
    }
    /**
     * Loads TypeAttaques into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final TypeAttaque typeAttaque : this.items.values()) {
            int id = dataManager.persist(typeAttaque);
            typeAttaque.setId(id);

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
    protected TypeAttaque get(final String key) {
        final TypeAttaque result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
