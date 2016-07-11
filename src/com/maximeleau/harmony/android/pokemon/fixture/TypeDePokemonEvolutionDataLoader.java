/**************************************************************************
 * TypeDePokemonEvolutionDataLoader.java, pokemon Android
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




import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonEvolution;


/**
 * TypeDePokemonEvolutionDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class TypeDePokemonEvolutionDataLoader
                        extends FixtureBase<TypeDePokemonEvolution> {
    /** TypeDePokemonEvolutionDataLoader name. */
    private static final String FILE_NAME = "TypeDePokemonEvolution";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for evolueEn. */
    private static final String EVOLUEEN = "evolueEn";
    /** Constant field for estEvolueEn. */
    private static final String ESTEVOLUEEN = "estEvolueEn";


    /** TypeDePokemonEvolutionDataLoader instance (Singleton). */
    private static TypeDePokemonEvolutionDataLoader instance;

    /**
     * Get the TypeDePokemonEvolutionDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static TypeDePokemonEvolutionDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new TypeDePokemonEvolutionDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private TypeDePokemonEvolutionDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected TypeDePokemonEvolution extractItem(final Map<?, ?> columns) {
        final TypeDePokemonEvolution typeDePokemonEvolution =
                new TypeDePokemonEvolution();

        return this.extractItem(columns, typeDePokemonEvolution);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param typeDePokemonEvolution Entity to extract
     * @return A TypeDePokemonEvolution entity
     */
    protected TypeDePokemonEvolution extractItem(final Map<?, ?> columns,
                TypeDePokemonEvolution typeDePokemonEvolution) {
        typeDePokemonEvolution.setId(this.parseIntField(columns, ID));
        typeDePokemonEvolution.setEvolueEn(this.parseSimpleRelationField(columns, EVOLUEEN, TypeDePokemonDataLoader.getInstance(this.ctx)));
        typeDePokemonEvolution.setEstEvolueEn(this.parseSimpleRelationField(columns, ESTEVOLUEEN, TypeDePokemonDataLoader.getInstance(this.ctx)));

        return typeDePokemonEvolution;
    }
    /**
     * Loads TypeDePokemonEvolutions into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final TypeDePokemonEvolution typeDePokemonEvolution : this.items.values()) {
            int id = dataManager.persist(typeDePokemonEvolution);
            typeDePokemonEvolution.setId(id);

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
    protected TypeDePokemonEvolution get(final String key) {
        final TypeDePokemonEvolution result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
