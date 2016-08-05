/**************************************************************************
 * PersonnageNonJoueurUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Aug 5, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.test.utils.base;


import junit.framework.Assert;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;



import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;
import com.maximeleau.harmony.android.pokemon.entity.Profession;
import com.maximeleau.harmony.android.pokemon.fixture.ProfessionDataLoader;

import com.maximeleau.harmony.android.pokemon.entity.Objet;
import com.maximeleau.harmony.android.pokemon.fixture.ObjetDataLoader;

import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.fixture.DresseurDataLoader;

import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.fixture.AreneDataLoader;

import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.fixture.PokemonDataLoader;


import java.util.ArrayList;

public abstract class PersonnageNonJoueurUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static PersonnageNonJoueur generateRandom(android.content.Context ctx){
        PersonnageNonJoueur personnageNonJoueur = new PersonnageNonJoueur();

        personnageNonJoueur.setId(TestUtils.generateRandomInt(0,100) + 1);
        personnageNonJoueur.setNom("nom_"+TestUtils.generateRandomString(10));
        personnageNonJoueur.setDescription("description_"+TestUtils.generateRandomString(10));
        personnageNonJoueur.setUrlImage("urlImage_"+TestUtils.generateRandomString(10));
        ArrayList<Profession> professions =
            new ArrayList<Profession>();
        professions.addAll(ProfessionDataLoader.getInstance(ctx).getMap().values());
        if (!professions.isEmpty()) {
            personnageNonJoueur.setProfession(professions.get(TestUtils.generateRandomInt(0, professions.size())));
        }
        ArrayList<Objet> objetss =
            new ArrayList<Objet>();
        objetss.addAll(ObjetDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Objet> relatedObjetss = new ArrayList<Objet>();
        if (!objetss.isEmpty()) {
            relatedObjetss.add(objetss.get(TestUtils.generateRandomInt(0, objetss.size())));
            personnageNonJoueur.setObjets(relatedObjetss);
        }
        ArrayList<Dresseur> dresseurss =
            new ArrayList<Dresseur>();
        dresseurss.addAll(DresseurDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Dresseur> relatedDresseurss = new ArrayList<Dresseur>();
        if (!dresseurss.isEmpty()) {
            relatedDresseurss.add(dresseurss.get(TestUtils.generateRandomInt(0, dresseurss.size())));
            personnageNonJoueur.setDresseurs(relatedDresseurss);
        }
        ArrayList<Arene> areness =
            new ArrayList<Arene>();
        areness.addAll(AreneDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Arene> relatedAreness = new ArrayList<Arene>();
        if (!areness.isEmpty()) {
            relatedAreness.add(areness.get(TestUtils.generateRandomInt(0, areness.size())));
            personnageNonJoueur.setArenes(relatedAreness);
        }
        ArrayList<Pokemon> pokemonss =
            new ArrayList<Pokemon>();
        pokemonss.addAll(PokemonDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Pokemon> relatedPokemonss = new ArrayList<Pokemon>();
        if (!pokemonss.isEmpty()) {
            relatedPokemonss.add(pokemonss.get(TestUtils.generateRandomInt(0, pokemonss.size())));
            personnageNonJoueur.setPokemons(relatedPokemonss);
        }

        return personnageNonJoueur;
    }

    public static boolean equals(PersonnageNonJoueur personnageNonJoueur1,
            PersonnageNonJoueur personnageNonJoueur2){
        return equals(personnageNonJoueur1, personnageNonJoueur2, true);
    }
    
    public static boolean equals(PersonnageNonJoueur personnageNonJoueur1,
            PersonnageNonJoueur personnageNonJoueur2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(personnageNonJoueur1);
        Assert.assertNotNull(personnageNonJoueur2);
        if (personnageNonJoueur1!=null && personnageNonJoueur2 !=null){
            Assert.assertEquals(personnageNonJoueur1.getId(), personnageNonJoueur2.getId());
            Assert.assertEquals(personnageNonJoueur1.getNom(), personnageNonJoueur2.getNom());
            Assert.assertEquals(personnageNonJoueur1.getDescription(), personnageNonJoueur2.getDescription());
            Assert.assertEquals(personnageNonJoueur1.getUrlImage(), personnageNonJoueur2.getUrlImage());
            if (personnageNonJoueur1.getProfession() != null
                    && personnageNonJoueur2.getProfession() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(personnageNonJoueur1.getProfession().getId(),
                            personnageNonJoueur2.getProfession().getId());
                }
            }
            if (personnageNonJoueur1.getObjets() != null
                    && personnageNonJoueur2.getObjets() != null) {
                Assert.assertEquals(personnageNonJoueur1.getObjets().size(),
                    personnageNonJoueur2.getObjets().size());
                if (checkRecursiveId) {
                    for (Objet objets1 : personnageNonJoueur1.getObjets()) {
                        boolean found = false;
                        for (Objet objets2 : personnageNonJoueur2.getObjets()) {
                            if (objets1.getId() == objets2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated objets (id = %s) in PersonnageNonJoueur (id = %s)",
                                        objets1.getId(),
                                        personnageNonJoueur1.getId()),
                                found);
                    }
                }
            }
            if (personnageNonJoueur1.getDresseurs() != null
                    && personnageNonJoueur2.getDresseurs() != null) {
                Assert.assertEquals(personnageNonJoueur1.getDresseurs().size(),
                    personnageNonJoueur2.getDresseurs().size());
                if (checkRecursiveId) {
                    for (Dresseur dresseurs1 : personnageNonJoueur1.getDresseurs()) {
                        boolean found = false;
                        for (Dresseur dresseurs2 : personnageNonJoueur2.getDresseurs()) {
                            if (dresseurs1.getId() == dresseurs2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated dresseurs (id = %s) in PersonnageNonJoueur (id = %s)",
                                        dresseurs1.getId(),
                                        personnageNonJoueur1.getId()),
                                found);
                    }
                }
            }
            if (personnageNonJoueur1.getArenes() != null
                    && personnageNonJoueur2.getArenes() != null) {
                Assert.assertEquals(personnageNonJoueur1.getArenes().size(),
                    personnageNonJoueur2.getArenes().size());
                if (checkRecursiveId) {
                    for (Arene arenes1 : personnageNonJoueur1.getArenes()) {
                        boolean found = false;
                        for (Arene arenes2 : personnageNonJoueur2.getArenes()) {
                            if (arenes1.getId() == arenes2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated arenes (id = %s) in PersonnageNonJoueur (id = %s)",
                                        arenes1.getId(),
                                        personnageNonJoueur1.getId()),
                                found);
                    }
                }
            }
            if (personnageNonJoueur1.getPokemons() != null
                    && personnageNonJoueur2.getPokemons() != null) {
                Assert.assertEquals(personnageNonJoueur1.getPokemons().size(),
                    personnageNonJoueur2.getPokemons().size());
                if (checkRecursiveId) {
                    for (Pokemon pokemons1 : personnageNonJoueur1.getPokemons()) {
                        boolean found = false;
                        for (Pokemon pokemons2 : personnageNonJoueur2.getPokemons()) {
                            if (pokemons1.getId() == pokemons2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated pokemons (id = %s) in PersonnageNonJoueur (id = %s)",
                                        pokemons1.getId(),
                                        personnageNonJoueur1.getId()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}

