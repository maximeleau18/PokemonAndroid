/**************************************************************************
 * PersonnageNonJoueurBadgeDataLoader.java, pokemon Android
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




import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueurBadge;


/**
 * PersonnageNonJoueurBadgeDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PersonnageNonJoueurBadgeDataLoader
                        extends FixtureBase<PersonnageNonJoueurBadge> {
    /** PersonnageNonJoueurBadgeDataLoader name. */
    private static final String FILE_NAME = "PersonnageNonJoueurBadge";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for personnageNonJoueur. */
    private static final String PERSONNAGENONJOUEUR = "personnageNonJoueur";
    /** Constant field for badge. */
    private static final String BADGE = "badge";


    /** PersonnageNonJoueurBadgeDataLoader instance (Singleton). */
    private static PersonnageNonJoueurBadgeDataLoader instance;

    /**
     * Get the PersonnageNonJoueurBadgeDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PersonnageNonJoueurBadgeDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PersonnageNonJoueurBadgeDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PersonnageNonJoueurBadgeDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected PersonnageNonJoueurBadge extractItem(final Map<?, ?> columns) {
        final PersonnageNonJoueurBadge personnageNonJoueurBadge =
                new PersonnageNonJoueurBadge();

        return this.extractItem(columns, personnageNonJoueurBadge);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param personnageNonJoueurBadge Entity to extract
     * @return A PersonnageNonJoueurBadge entity
     */
    protected PersonnageNonJoueurBadge extractItem(final Map<?, ?> columns,
                PersonnageNonJoueurBadge personnageNonJoueurBadge) {
        personnageNonJoueurBadge.setId(this.parseIntField(columns, ID));
        personnageNonJoueurBadge.setPersonnageNonJoueur(this.parseSimpleRelationField(columns, PERSONNAGENONJOUEUR, PersonnageNonJoueurDataLoader.getInstance(this.ctx)));
        personnageNonJoueurBadge.setBadge(this.parseSimpleRelationField(columns, BADGE, BadgeDataLoader.getInstance(this.ctx)));

        return personnageNonJoueurBadge;
    }
    /**
     * Loads PersonnageNonJoueurBadges into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final PersonnageNonJoueurBadge personnageNonJoueurBadge : this.items.values()) {
            int id = dataManager.persist(personnageNonJoueurBadge);
            personnageNonJoueurBadge.setId(id);

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
    protected PersonnageNonJoueurBadge get(final String key) {
        final PersonnageNonJoueurBadge result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
