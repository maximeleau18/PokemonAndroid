/**************************************************************************
 * CombatUtilsBase.java, pokemon Android
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
        if (!pokemon1s.isEmpty()) {
            combat.setPokemon1(pokemon1s.get(TestUtils.generateRandomInt(0, pokemon1s.size())));
        }
        ArrayList<Pokemon> pokemon2s =
            new ArrayList<Pokemon>();
        pokemon2s.addAll(PokemonDataLoader.getInstance(ctx).getMap().values());
        if (!pokemon2s.isEmpty()) {
            combat.setPokemon2(pokemon2s.get(TestUtils.generateRandomInt(0, pokemon2s.size())));
        }
        ArrayList<Dresseur> dresseur1s =
            new ArrayList<Dresseur>();
        dresseur1s.addAll(DresseurDataLoader.getInstance(ctx).getMap().values());
        if (!dresseur1s.isEmpty()) {
            combat.setDresseur1(dresseur1s.get(TestUtils.generateRandomInt(0, dresseur1s.size())));
        }
        ArrayList<Dresseur> dresseur2s =
            new ArrayList<Dresseur>();
        dresseur2s.addAll(DresseurDataLoader.getInstance(ctx).getMap().values());
        if (!dresseur2s.isEmpty()) {
            combat.setDresseur2(dresseur2s.get(TestUtils.generateRandomInt(0, dresseur2s.size())));
        }
        combat.setDresseur1Vainqueur(TestUtils.generateRandomBool());
        combat.setDresseur2Vainqueur(TestUtils.generateRandomBool());
        combat.setPokemon1Vainqueur(TestUtils.generateRandomBool());
        combat.setPokemon2Vainqueur(TestUtils.generateRandomBool());
        combat.setDresseur1DeviceId("dresseur1DeviceId_"+TestUtils.generateRandomString(10));
        combat.setDresseur2DeviceId("dresseur2DeviceId_"+TestUtils.generateRandomString(10));

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
                if (checkRecursiveId) {
                    Assert.assertEquals(combat1.getPokemon1().getId(),
                            combat2.getPokemon1().getId());
                }
            }
            if (combat1.getPokemon2() != null
                    && combat2.getPokemon2() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(combat1.getPokemon2().getId(),
                            combat2.getPokemon2().getId());
                }
            }
            if (combat1.getDresseur1() != null
                    && combat2.getDresseur1() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(combat1.getDresseur1().getId(),
                            combat2.getDresseur1().getId());
                }
            }
            if (combat1.getDresseur2() != null
                    && combat2.getDresseur2() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(combat1.getDresseur2().getId(),
                            combat2.getDresseur2().getId());
                }
            }
            Assert.assertEquals(combat1.isDresseur1Vainqueur(), combat2.isDresseur1Vainqueur());
            Assert.assertEquals(combat1.isDresseur2Vainqueur(), combat2.isDresseur2Vainqueur());
            Assert.assertEquals(combat1.isPokemon1Vainqueur(), combat2.isPokemon1Vainqueur());
            Assert.assertEquals(combat1.isPokemon2Vainqueur(), combat2.isPokemon2Vainqueur());
            Assert.assertEquals(combat1.getDresseur1DeviceId(), combat2.getDresseur1DeviceId());
            Assert.assertEquals(combat1.getDresseur2DeviceId(), combat2.getDresseur2DeviceId());
        }

        return ret;
    }
}

