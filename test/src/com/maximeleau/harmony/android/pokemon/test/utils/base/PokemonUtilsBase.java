/**************************************************************************
 * PokemonUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.test.utils.base;


import junit.framework.Assert;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;



import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;

import com.maximeleau.harmony.android.pokemon.test.utils.AttaqueUtils;

import com.maximeleau.harmony.android.pokemon.test.utils.TypeDePokemonUtils;

import com.maximeleau.harmony.android.pokemon.test.utils.PersonnageNonJoueurUtils;


public abstract class PokemonUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Pokemon generateRandom(android.content.Context ctx){
        Pokemon pokemon = new Pokemon();

        pokemon.setId(TestUtils.generateRandomInt(0,100) + 1);
        pokemon.setSurnom("surnom_"+TestUtils.generateRandomString(10));
        pokemon.setNiveau(TestUtils.generateRandomInt(0,100));
        pokemon.setCaptureLe(TestUtils.generateRandomDateTime());
        pokemon.setAttaque1(AttaqueUtils.generateRandom(ctx));
        pokemon.setAttaque2(AttaqueUtils.generateRandom(ctx));
        pokemon.setAttaque3(AttaqueUtils.generateRandom(ctx));
        pokemon.setAttaque4(AttaqueUtils.generateRandom(ctx));
        pokemon.setTypeDePokemon(TypeDePokemonUtils.generateRandom(ctx));
        pokemon.setPersonnageNonJoueur(PersonnageNonJoueurUtils.generateRandom(ctx));

        return pokemon;
    }

    public static boolean equals(Pokemon pokemon1,
            Pokemon pokemon2){
        return equals(pokemon1, pokemon2, true);
    }
    
    public static boolean equals(Pokemon pokemon1,
            Pokemon pokemon2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(pokemon1);
        Assert.assertNotNull(pokemon2);
        if (pokemon1!=null && pokemon2 !=null){
            Assert.assertEquals(pokemon1.getId(), pokemon2.getId());
            Assert.assertEquals(pokemon1.getSurnom(), pokemon2.getSurnom());
            Assert.assertEquals(pokemon1.getNiveau(), pokemon2.getNiveau());
            Assert.assertEquals(pokemon1.getCaptureLe(), pokemon2.getCaptureLe());
            if (pokemon1.getAttaque1() != null
                    && pokemon2.getAttaque1() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokemon1.getAttaque1().getId(),
                            pokemon2.getAttaque1().getId());
                }
            }
            if (pokemon1.getAttaque2() != null
                    && pokemon2.getAttaque2() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokemon1.getAttaque2().getId(),
                            pokemon2.getAttaque2().getId());
                }
            }
            if (pokemon1.getAttaque3() != null
                    && pokemon2.getAttaque3() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokemon1.getAttaque3().getId(),
                            pokemon2.getAttaque3().getId());
                }
            }
            if (pokemon1.getAttaque4() != null
                    && pokemon2.getAttaque4() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokemon1.getAttaque4().getId(),
                            pokemon2.getAttaque4().getId());
                }
            }
            if (pokemon1.getTypeDePokemon() != null
                    && pokemon2.getTypeDePokemon() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokemon1.getTypeDePokemon().getId(),
                            pokemon2.getTypeDePokemon().getId());
                }
            }
            if (pokemon1.getPersonnageNonJoueur() != null
                    && pokemon2.getPersonnageNonJoueur() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(pokemon1.getPersonnageNonJoueur().getId(),
                            pokemon2.getPersonnageNonJoueur().getId());
                }
            }
        }

        return ret;
    }
}

