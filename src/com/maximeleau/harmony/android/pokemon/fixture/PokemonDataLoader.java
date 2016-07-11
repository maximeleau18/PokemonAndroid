/**************************************************************************
 * PokemonDataLoader.java, pokemon Android
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



import com.maximeleau.harmony.android.pokemon.entity.Pokemon;


/**
 * PokemonDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PokemonDataLoader
                        extends FixtureBase<Pokemon> {
    /** PokemonDataLoader name. */
    private static final String FILE_NAME = "Pokemon";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for surnom. */
    private static final String SURNOM = "surnom";
    /** Constant field for niveau. */
    private static final String NIVEAU = "niveau";
    /** Constant field for captureLe. */
    private static final String CAPTURELE = "captureLe";
    /** Constant field for attaque1. */
    private static final String ATTAQUE1 = "attaque1";
    /** Constant field for attaque2. */
    private static final String ATTAQUE2 = "attaque2";
    /** Constant field for attaque3. */
    private static final String ATTAQUE3 = "attaque3";
    /** Constant field for attaque4. */
    private static final String ATTAQUE4 = "attaque4";
    /** Constant field for typeDePokemon. */
    private static final String TYPEDEPOKEMON = "typeDePokemon";
    /** Constant field for personnageNonJoueur. */
    private static final String PERSONNAGENONJOUEUR = "personnageNonJoueur";


    /** PokemonDataLoader instance (Singleton). */
    private static PokemonDataLoader instance;

    /**
     * Get the PokemonDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PokemonDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PokemonDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PokemonDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Pokemon extractItem(final Map<?, ?> columns) {
        final Pokemon pokemon =
                new Pokemon();

        return this.extractItem(columns, pokemon);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param pokemon Entity to extract
     * @return A Pokemon entity
     */
    protected Pokemon extractItem(final Map<?, ?> columns,
                Pokemon pokemon) {
        pokemon.setId(this.parseIntField(columns, ID));
        pokemon.setSurnom(this.parseField(columns, SURNOM, String.class));
        pokemon.setNiveau(this.parseIntField(columns, NIVEAU));
        pokemon.setCaptureLe(this.parseDateTimeField(columns, CAPTURELE));
        pokemon.setAttaque1(this.parseSimpleRelationField(columns, ATTAQUE1, AttaqueDataLoader.getInstance(this.ctx)));
        pokemon.setAttaque2(this.parseSimpleRelationField(columns, ATTAQUE2, AttaqueDataLoader.getInstance(this.ctx)));
        pokemon.setAttaque3(this.parseSimpleRelationField(columns, ATTAQUE3, AttaqueDataLoader.getInstance(this.ctx)));
        pokemon.setAttaque4(this.parseSimpleRelationField(columns, ATTAQUE4, AttaqueDataLoader.getInstance(this.ctx)));
        pokemon.setTypeDePokemon(this.parseSimpleRelationField(columns, TYPEDEPOKEMON, TypeDePokemonDataLoader.getInstance(this.ctx)));
        if (pokemon.getTypeDePokemon() != null) {
            if (pokemon.getTypeDePokemon().getPokemons() == null) {
                pokemon.getTypeDePokemon().setPokemons(
                        new ArrayList<Pokemon>());
            }
            
            pokemon.getTypeDePokemon().getPokemons().add(pokemon);
        }
        pokemon.setPersonnageNonJoueur(this.parseSimpleRelationField(columns, PERSONNAGENONJOUEUR, PersonnageNonJoueurDataLoader.getInstance(this.ctx)));
        if (pokemon.getPersonnageNonJoueur() != null) {
            if (pokemon.getPersonnageNonJoueur().getPokemons() == null) {
                pokemon.getPersonnageNonJoueur().setPokemons(
                        new ArrayList<Pokemon>());
            }
            
            pokemon.getPersonnageNonJoueur().getPokemons().add(pokemon);
        }

        return pokemon;
    }
    /**
     * Loads Pokemons into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Pokemon pokemon : this.items.values()) {
            int id = dataManager.persist(pokemon);
            pokemon.setId(id);

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
    protected Pokemon get(final String key) {
        final Pokemon result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
