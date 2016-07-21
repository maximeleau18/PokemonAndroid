/**************************************************************************
 * PersonnageNonJoueurDataLoader.java, pokemon Android
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




import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.entity.Objet;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;


/**
 * PersonnageNonJoueurDataLoader.
 *
 * This dataloader implements the parsing method needed while reading
 * the fixtures files.
 */
public final class PersonnageNonJoueurDataLoader
                        extends FixtureBase<PersonnageNonJoueur> {
    /** PersonnageNonJoueurDataLoader name. */
    private static final String FILE_NAME = "PersonnageNonJoueur";

    /** Constant field for id. */
    private static final String ID = "id";
    /** Constant field for nom. */
    private static final String NOM = "nom";
    /** Constant field for description. */
    private static final String DESCRIPTION = "description";
    /** Constant field for urlImage. */
    private static final String URLIMAGE = "urlImage";
    /** Constant field for profession. */
    private static final String PROFESSION = "profession";
    /** Constant field for objets. */
    private static final String OBJETS = "objets";
    /** Constant field for dresseurs. */
    private static final String DRESSEURS = "dresseurs";
    /** Constant field for arenes. */
    private static final String ARENES = "arenes";
    /** Constant field for pokemons. */
    private static final String POKEMONS = "pokemons";


    /** PersonnageNonJoueurDataLoader instance (Singleton). */
    private static PersonnageNonJoueurDataLoader instance;

    /**
     * Get the PersonnageNonJoueurDataLoader singleton.
     * @param ctx The context
     * @return The dataloader instance
     */
    public static PersonnageNonJoueurDataLoader getInstance(
                                            final android.content.Context ctx) {
        if (instance == null) {
            instance = new PersonnageNonJoueurDataLoader(ctx);
        }
        return instance;
    }

    /**
     * Constructor.
     * @param ctx The context
     */
    private PersonnageNonJoueurDataLoader(final android.content.Context ctx) {
        super(ctx);
    }


    @Override
    protected PersonnageNonJoueur extractItem(final Map<?, ?> columns) {
        final PersonnageNonJoueur personnageNonJoueur =
                new PersonnageNonJoueur();

        return this.extractItem(columns, personnageNonJoueur);
    }
    /**
     * Extract an entity from a fixture element (YML).
     * @param columns Columns to extract
     * @param personnageNonJoueur Entity to extract
     * @return A PersonnageNonJoueur entity
     */
    protected PersonnageNonJoueur extractItem(final Map<?, ?> columns,
                PersonnageNonJoueur personnageNonJoueur) {
        personnageNonJoueur.setId(this.parseIntField(columns, ID));
        personnageNonJoueur.setNom(this.parseField(columns, NOM, String.class));
        personnageNonJoueur.setDescription(this.parseField(columns, DESCRIPTION, String.class));
        personnageNonJoueur.setUrlImage(this.parseField(columns, URLIMAGE, String.class));
        personnageNonJoueur.setProfession(this.parseSimpleRelationField(columns, PROFESSION, ProfessionDataLoader.getInstance(this.ctx)));
        personnageNonJoueur.setObjets(this.parseMultiRelationField(columns, OBJETS, ObjetDataLoader.getInstance(this.ctx)));
        if (personnageNonJoueur.getObjets() != null) {
            for (Objet related : personnageNonJoueur.getObjets()) {
                related.setPersonnageNonJoueur(personnageNonJoueur);
            }
        }
        personnageNonJoueur.setDresseurs(this.parseMultiRelationField(columns, DRESSEURS, DresseurDataLoader.getInstance(this.ctx)));
        if (personnageNonJoueur.getDresseurs() != null) {
            for (Dresseur related : personnageNonJoueur.getDresseurs()) {
                related.setPersonnageNonJoueur(personnageNonJoueur);
            }
        }
        personnageNonJoueur.setArenes(this.parseMultiRelationField(columns, ARENES, AreneDataLoader.getInstance(this.ctx)));
        if (personnageNonJoueur.getArenes() != null) {
            for (Arene related : personnageNonJoueur.getArenes()) {
                related.setMaitre(personnageNonJoueur);
            }
        }
        personnageNonJoueur.setPokemons(this.parseMultiRelationField(columns, POKEMONS, PokemonDataLoader.getInstance(this.ctx)));
        if (personnageNonJoueur.getPokemons() != null) {
            for (Pokemon related : personnageNonJoueur.getPokemons()) {
                related.setPersonnageNonJoueur(personnageNonJoueur);
            }
        }

        return personnageNonJoueur;
    }
    /**
     * Loads PersonnageNonJoueurs into the DataManager.
     * @param manager The DataManager
     */
    @Override
    public void load(final DataManager dataManager) {
        for (final PersonnageNonJoueur personnageNonJoueur : this.items.values()) {
            int id = dataManager.persist(personnageNonJoueur);
            personnageNonJoueur.setId(id);

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
    protected PersonnageNonJoueur get(final String key) {
        final PersonnageNonJoueur result;
        if (this.items.containsKey(key)) {
            result = this.items.get(key);
        }
        else {
            result = null;
        }
        return result;
    }
}
