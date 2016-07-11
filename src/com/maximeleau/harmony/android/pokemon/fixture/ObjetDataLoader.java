/**************************************************************************
 * ObjetDataLoader.java, pokemon Android
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



import com.maximeleau.harmony.android.pokemon.entity.Objet;


/**
 * ObjetDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class ObjetDataLoader
                        extends FixtureBase<Objet> {
    /** ObjetDataLoader name. */
    private static final String FILE_NAME = "Objet";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for quantite. */
    private static final String QUANTITE = "quantite";
    /** Constant field for typeObjet. */
    private static final String TYPEOBJET = "typeObjet";
    /** Constant field for personnageNonJoueur. */
    private static final String PERSONNAGENONJOUEUR = "personnageNonJoueur";


    /** ObjetDataLoader instance (Singleton). */
    private static ObjetDataLoader instance;

    /**
     * Get the ObjetDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static ObjetDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new ObjetDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private ObjetDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected Objet extractItem(final Map<?, ?> columns) {
        final Objet objet =
                new Objet();

        return this.extractItem(columns, objet);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param objet Entity to extract
     * @return A Objet entity
     */
    protected Objet extractItem(final Map<?, ?> columns,
                Objet objet) {
        objet.setId(this.parseIntField(columns, ID));
        objet.setNom(this.parseField(columns, NOM, String.class));
        objet.setQuantite(this.parseIntField(columns, QUANTITE));
        objet.setTypeObjet(this.parseSimpleRelationField(columns, TYPEOBJET, TypeObjetDataLoader.getInstance(this.ctx)));
        objet.setPersonnageNonJoueur(this.parseSimpleRelationField(columns, PERSONNAGENONJOUEUR, PersonnageNonJoueurDataLoader.getInstance(this.ctx)));
        if (objet.getPersonnageNonJoueur() != null) {
            if (objet.getPersonnageNonJoueur().getObjets() == null) {
                objet.getPersonnageNonJoueur().setObjets(
                        new ArrayList<Objet>());
            }
            
            objet.getPersonnageNonJoueur().getObjets().add(objet);
        }

        return objet;
    }
    /**
     * Loads Objets into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final Objet objet : this.items.values()) {
            int id = dataManager.persist(objet);
            objet.setId(id);

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
    protected Objet get(final String key) {
        final Objet result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
