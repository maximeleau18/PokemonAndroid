/**************************************************************************
 * TypeDePokemonZoneDataLoader.java, pokemon Android
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




import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonZone;


/**
 * TypeDePokemonZoneDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class TypeDePokemonZoneDataLoader
                        extends FixtureBase<TypeDePokemonZone> {
    /** TypeDePokemonZoneDataLoader name. */
    private static final String FILE_NAME = "TypeDePokemonZone";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for zone. */
    private static final String ZONE = "zone";
    /** Constant field for typeDePokemon. */
    private static final String TYPEDEPOKEMON = "typeDePokemon";


    /** TypeDePokemonZoneDataLoader instance (Singleton). */
    private static TypeDePokemonZoneDataLoader instance;

    /**
     * Get the TypeDePokemonZoneDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static TypeDePokemonZoneDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new TypeDePokemonZoneDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private TypeDePokemonZoneDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected TypeDePokemonZone extractItem(final Map<?, ?> columns) {
        final TypeDePokemonZone typeDePokemonZone =
                new TypeDePokemonZone();

        return this.extractItem(columns, typeDePokemonZone);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param typeDePokemonZone Entity to extract
     * @return A TypeDePokemonZone entity
     */
    protected TypeDePokemonZone extractItem(final Map<?, ?> columns,
                TypeDePokemonZone typeDePokemonZone) {
        typeDePokemonZone.setId(this.parseIntField(columns, ID));
        typeDePokemonZone.setZone(this.parseSimpleRelationField(columns, ZONE, ZoneDataLoader.getInstance(this.ctx)));
        typeDePokemonZone.setTypeDePokemon(this.parseSimpleRelationField(columns, TYPEDEPOKEMON, TypeDePokemonDataLoader.getInstance(this.ctx)));

        return typeDePokemonZone;
    }
    /**
     * Loads TypeDePokemonZones into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final TypeDePokemonZone typeDePokemonZone : this.items.values()) {
            int id = dataManager.persist(typeDePokemonZone);
            typeDePokemonZone.setId(id);

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
    protected TypeDePokemonZone get(final String key) {
        final TypeDePokemonZone result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
