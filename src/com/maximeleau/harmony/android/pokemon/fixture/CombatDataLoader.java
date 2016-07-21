/**************************************************************************
 * CombatDataLoader.java, pokemon Android
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




import com.maximeleau.harmony.android.pokemon.entity.Combat;


/**
 * CombatDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class CombatDataLoader
                        extends FixtureBase<Combat> {
    /** CombatDataLoader name. */
    private static final String FILE_NAME = "Combat";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for lanceLe. */
    private static final String LANCELE = "lanceLe";
    /** Constant field for duree. */
    private static final String DUREE = "duree";
    /** Constant field for pokemon1. */
    private static final String POKEMON1 = "pokemon1";
    /** Constant field for pokemon2. */
    private static final String POKEMON2 = "pokemon2";
    /** Constant field for dresseur1. */
    private static final String DRESSEUR1 = "dresseur1";
    /** Constant field for dresseur2. */
    private static final String DRESSEUR2 = "dresseur2";
    /** Constant field for dresseur1Vainqueur. */
    private static final String DRESSEUR1VAINQUEUR = "dresseur1Vainqueur";
    /** Constant field for dresseur2Vainqueur. */
    private static final String DRESSEUR2VAINQUEUR = "dresseur2Vainqueur";
    /** Constant field for pokemon1Vainqueur. */
    private static final String POKEMON1VAINQUEUR = "pokemon1Vainqueur";
    /** Constant field for pokemon2Vainqueur. */
    private static final String POKEMON2VAINQUEUR = "pokemon2Vainqueur";


    /** CombatDataLoader instance (Singleton). */
    private static CombatDataLoader instance;

    /**
     * Get the CombatDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static CombatDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new CombatDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private CombatDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Combat extractItem(final Map<?, ?> columns) {
        final Combat combat =
                new Combat();

        return this.extractItem(columns, combat);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param combat Entity to extract
     * @return A Combat entity
     */
    protected Combat extractItem(final Map<?, ?> columns,
                Combat combat) {
        combat.setId(this.parseIntField(columns, ID));
        combat.setLanceLe(this.parseDateTimeField(columns, LANCELE));
        combat.setDuree(this.parseIntField(columns, DUREE));
        combat.setPokemon1(this.parseSimpleRelationField(columns, POKEMON1, PokemonDataLoader.getInstance(this.ctx)));
        combat.setPokemon2(this.parseSimpleRelationField(columns, POKEMON2, PokemonDataLoader.getInstance(this.ctx)));
        combat.setDresseur1(this.parseSimpleRelationField(columns, DRESSEUR1, DresseurDataLoader.getInstance(this.ctx)));
        combat.setDresseur2(this.parseSimpleRelationField(columns, DRESSEUR2, DresseurDataLoader.getInstance(this.ctx)));
        combat.setDresseur1Vainqueur(this.parseBooleanField(columns, DRESSEUR1VAINQUEUR));
        combat.setDresseur2Vainqueur(this.parseBooleanField(columns, DRESSEUR2VAINQUEUR));
        combat.setPokemon1Vainqueur(this.parseBooleanField(columns, POKEMON1VAINQUEUR));
        combat.setPokemon2Vainqueur(this.parseBooleanField(columns, POKEMON2VAINQUEUR));

        return combat;
    }
    /**
     * Loads Combats into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Combat combat : this.items.values()) {
            int id = dataManager.persist(combat);
            combat.setId(id);

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
    protected Combat get(final String key) {
        final Combat result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
