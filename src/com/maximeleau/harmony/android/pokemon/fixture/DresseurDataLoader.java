/**************************************************************************
 * DresseurDataLoader.java, pokemon Android
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




import com.maximeleau.harmony.android.pokemon.entity.Dresseur;


/**
 * DresseurDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class DresseurDataLoader
                        extends FixtureBase<Dresseur> {
    /** DresseurDataLoader name. */
    private static final String FILE_NAME = "Dresseur";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for prenom. */
    private static final String PRENOM = "prenom";
    /** Constant field for login. */
    private static final String LOGIN = "login";
    /** Constant field for motDePasse. */
    private static final String MOTDEPASSE = "motDePasse";
    /** Constant field for personnageNonJoueur. */
    private static final String PERSONNAGENONJOUEUR = "personnageNonJoueur";


    /** DresseurDataLoader instance (Singleton). */
    private static DresseurDataLoader instance;

    /**
     * Get the DresseurDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static DresseurDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new DresseurDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private DresseurDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Dresseur extractItem(final Map<?, ?> columns) {
        final Dresseur dresseur =
                new Dresseur();

        return this.extractItem(columns, dresseur);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param dresseur Entity to extract
     * @return A Dresseur entity
     */
    protected Dresseur extractItem(final Map<?, ?> columns,
                Dresseur dresseur) {
        dresseur.setId(this.parseIntField(columns, ID));
        dresseur.setNom(this.parseField(columns, NOM, String.class));
        dresseur.setPrenom(this.parseField(columns, PRENOM, String.class));
        dresseur.setLogin(this.parseField(columns, LOGIN, String.class));
        dresseur.setMotDePasse(this.parseField(columns, MOTDEPASSE, String.class));
        dresseur.setPersonnageNonJoueur(this.parseSimpleRelationField(columns, PERSONNAGENONJOUEUR, PersonnageNonJoueurDataLoader.getInstance(this.ctx)));

        return dresseur;
    }
    /**
     * Loads Dresseurs into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Dresseur dresseur : this.items.values()) {
            int id = dataManager.persist(dresseur);
            dresseur.setId(id);

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
    protected Dresseur get(final String key) {
        final Dresseur result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
