/**************************************************************************
 * TypeDePokemonDataLoader.java, pokemon Android
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




import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;


/**
 * TypeDePokemonDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class TypeDePokemonDataLoader
                        extends FixtureBase<TypeDePokemon> {
    /** TypeDePokemonDataLoader name. */
    private static final String FILE_NAME = "TypeDePokemon";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for attaque. */
    private static final String ATTAQUE = "attaque";
    /** Constant field for defense. */
    private static final String DEFENSE = "defense";
    /** Constant field for pv. */
    private static final String PV = "pv";
    /** Constant field for numPokedex. */
    private static final String NUMPOKEDEX = "numPokedex";
    /** Constant field for pokemons. */
    private static final String POKEMONS = "pokemons";


    /** TypeDePokemonDataLoader instance (Singleton). */
    private static TypeDePokemonDataLoader instance;

    /**
     * Get the TypeDePokemonDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static TypeDePokemonDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new TypeDePokemonDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private TypeDePokemonDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected TypeDePokemon extractItem(final Map<?, ?> columns) {
        final TypeDePokemon typeDePokemon =
                new TypeDePokemon();

        return this.extractItem(columns, typeDePokemon);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param typeDePokemon Entity to extract
     * @return A TypeDePokemon entity
     */
    protected TypeDePokemon extractItem(final Map<?, ?> columns,
                TypeDePokemon typeDePokemon) {
        typeDePokemon.setId(this.parseIntField(columns, ID));
        typeDePokemon.setNom(this.parseField(columns, NOM, String.class));
        typeDePokemon.setAttaque(this.parseIntField(columns, ATTAQUE));
        typeDePokemon.setDefense(this.parseIntField(columns, DEFENSE));
        typeDePokemon.setPv(this.parseIntField(columns, PV));
        typeDePokemon.setNumPokedex(this.parseIntField(columns, NUMPOKEDEX));
        typeDePokemon.setPokemons(this.parseMultiRelationField(columns, POKEMONS, PokemonDataLoader.getInstance(this.ctx)));
        if (typeDePokemon.getPokemons() != null) {
            for (Pokemon related : typeDePokemon.getPokemons()) {
                related.setTypeDePokemon(typeDePokemon);
            }
        }

        return typeDePokemon;
    }
    /**
     * Loads TypeDePokemons into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final TypeDePokemon typeDePokemon : this.items.values()) {
            int id = dataManager.persist(typeDePokemon);
            typeDePokemon.setId(id);

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
    protected TypeDePokemon get(final String key) {
        final TypeDePokemon result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
