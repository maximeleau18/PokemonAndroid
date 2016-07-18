/**************************************************************************
 * CombatUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 18, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.test.utils.base;


import junit.framework.Assert;
import com.maximeleau.harmony.android.pokemon.entity.Combat;



import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.fixture.PokemonDataLoader;

import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.fixture.DresseurDataLoader;


import java.util.ArrayList;

public abstract class CombatUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Combat generateRandom(android.content.Context ctx){
        Combat combat = new Combat();

        combat.setId(TestUtils.generateRandomInt(0,100) + 1);
        combat.setLanceLe(TestUtils.generateRandomDateTime());
        combat.setDuree(TestUtils.generateRandomInt(0,100));
        ArrayList<Pokemon> pokemon1s =
            new ArrayList<Pokemon>();
        pokemon1s.addAll(PokemonDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Pokemon> relatedPokemon1s = new ArrayList<Pokemon>();
        if (!pokemon1s.isEmpty()) {
            relatedPokemon1s.add(pokemon1s.get(TestUtils.generateRandomInt(0, pokemon1s.size())));
            combat.setPokemon1(relatedPokemon1s);
        }
        ArrayList<Pokemon> pokemon2s =
            new ArrayList<Pokemon>();
        pokemon2s.addAll(PokemonDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Pokemon> relatedPokemon2s = new ArrayList<Pokemon>();
        if (!pokemon2s.isEmpty()) {
            relatedPokemon2s.add(pokemon2s.get(TestUtils.generateRandomInt(0, pokemon2s.size())));
            combat.setPokemon2(relatedPokemon2s);
        }
        ArrayList<Dresseur> dresseur1s =
            new ArrayList<Dresseur>();
        dresseur1s.addAll(DresseurDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Dresseur> relatedDresseur1s = new ArrayList<Dresseur>();
        if (!dresseur1s.isEmpty()) {
            relatedDresseur1s.add(dresseur1s.get(TestUtils.generateRandomInt(0, dresseur1s.size())));
            combat.setDresseur1(relatedDresseur1s);
        }
        ArrayList<Dresseur> dresseur2s =
            new ArrayList<Dresseur>();
        dresseur2s.addAll(DresseurDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Dresseur> relatedDresseur2s = new ArrayList<Dresseur>();
        if (!dresseur2s.isEmpty()) {
            relatedDresseur2s.add(dresseur2s.get(TestUtils.generateRandomInt(0, dresseur2s.size())));
            combat.setDresseur2(relatedDresseur2s);
        }
        combat.setDresseur1Vainqueur(TestUtils.generateRandomBool());
        combat.setDresseur2Vainqueur(TestUtils.generateRandomBool());
        combat.setPokemon1Vainqueur(TestUtils.generateRandomBool());
        combat.setPokemon2Vainqueur(TestUtils.generateRandomBool());

        return combat;
    }

    public static boolean equals(Combat combat1,
            Combat combat2){
        return equals(combat1, combat2, true);
    }
    
    public static boolean equals(Combat combat1,
            Combat combat2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(combat1);
        Assert.assertNotNull(combat2);
        if (combat1!=null && combat2 !=null){
            Assert.assertEquals(combat1.getId(), combat2.getId());
            Assert.assertEquals(combat1.getLanceLe(), combat2.getLanceLe());
            Assert.assertEquals(combat1.getDuree(), combat2.getDuree());
            if (combat1.getPokemon1() != null
                    && combat2.getPokemon1() != null) {
                Assert.assertEquals(combat1.getPokemon1().size(),
                    combat2.getPokemon1().size());
                if (checkRecursiveId) {
                    for (Pokemon pokemon11 : combat1.getPokemon1()) {
                        boolean found = false;
                        for (Pokemon pokemon12 : combat2.getPokemon1()) {
                            if (pokemon11.getId() == pokemon12.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated pokemon1 (id = %s) in Combat (id = %s)",
                                        pokemon11.getId(),
                                        combat1.getId()),
                                found);
                    }
                }
            }
            if (combat1.getPokemon2() != null
                    && combat2.getPokemon2() != null) {
                Assert.assertEquals(combat1.getPokemon2().size(),
                    combat2.getPokemon2().size());
                if (checkRecursiveId) {
                    for (Pokemon pokemon21 : combat1.getPokemon2()) {
                        boolean found = false;
                        for (Pokemon pokemon22 : combat2.getPokemon2()) {
                            if (pokemon21.getId() == pokemon22.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated pokemon2 (id = %s) in Combat (id = %s)",
                                        pokemon21.getId(),
                                        combat1.getId()),
                                found);
                    }
                }
            }
            if (combat1.getDresseur1() != null
                    && combat2.getDresseur1() != null) {
                Assert.assertEquals(combat1.getDresseur1().size(),
                    combat2.getDresseur1().size());
                if (checkRecursiveId) {
                    for (Dresseur dresseur11 : combat1.getDresseur1()) {
                        boolean found = false;
                        for (Dresseur dresseur12 : combat2.getDresseur1()) {
                            if (dresseur11.getId() == dresseur12.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated dresseur1 (id = %s) in Combat (id = %s)",
                                        dresseur11.getId(),
                                        combat1.getId()),
                                found);
                    }
                }
            }
            if (combat1.getDresseur2() != null
                    && combat2.getDresseur2() != null) {
                Assert.assertEquals(combat1.getDresseur2().size(),
                    combat2.getDresseur2().size());
                if (checkRecursiveId) {
                    for (Dresseur dresseur21 : combat1.getDresseur2()) {
                        boolean found = false;
                        for (Dresseur dresseur22 : combat2.getDresseur2()) {
                            if (dresseur21.getId() == dresseur22.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated dresseur2 (id = %s) in Combat (id = %s)",
                                        dresseur21.getId(),
                                        combat1.getId()),
                                found);
                    }
                }
            }
            Assert.assertEquals(combat1.isDresseur1Vainqueur(), combat2.isDresseur1Vainqueur());
            Assert.assertEquals(combat1.isDresseur2Vainqueur(), combat2.isDresseur2Vainqueur());
            Assert.assertEquals(combat1.isPokemon1Vainqueur(), combat2.isPokemon1Vainqueur());
            Assert.assertEquals(combat1.isPokemon2Vainqueur(), combat2.isPokemon2Vainqueur());
        }

        return ret;
    }
}

