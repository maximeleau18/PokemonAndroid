/**************************************************************************
 * PersonnageNonJoueurUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.test.utils.base;


import junit.framework.Assert;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;



import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;

import com.maximeleau.harmony.android.pokemon.test.utils.ProfessionUtils;
import com.maximeleau.harmony.android.pokemon.entity.Objet;
import com.maximeleau.harmony.android.pokemon.test.utils.ObjetUtils;

import com.maximeleau.harmony.android.pokemon.test.utils.DresseurUtils;
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.test.utils.AreneUtils;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.test.utils.PokemonUtils;

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
        personnageNonJoueur.setProfession(ProfessionUtils.generateRandom(ctx));
        ArrayList<Objet> relatedObjetss = new ArrayList<Objet>();
        relatedObjetss.add(ObjetUtils.generateRandom(ctx));
        personnageNonJoueur.setObjets(relatedObjetss);
        personnageNonJoueur.setDresseur(DresseurUtils.generateRandom(ctx));
        ArrayList<Arene> relatedAreness = new ArrayList<Arene>();
        relatedAreness.add(AreneUtils.generateRandom(ctx));
        personnageNonJoueur.setArenes(relatedAreness);
        ArrayList<Pokemon> relatedPokemonss = new ArrayList<Pokemon>();
        relatedPokemonss.add(PokemonUtils.generateRandom(ctx));
        personnageNonJoueur.setPokemons(relatedPokemonss);

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
            if (personnageNonJoueur1.getDresseur() != null
                    && personnageNonJoueur2.getDresseur() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(personnageNonJoueur1.getDresseur().getId(),
                            personnageNonJoueur2.getDresseur().getId());
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

