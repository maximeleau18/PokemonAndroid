/**************************************************************************
 * PokemonUtilsBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;



import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;
import com.maximeleau.harmony.android.pokemon.entity.Attaque;
import com.maximeleau.harmony.android.pokemon.fixture.AttaqueDataLoader;

import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;
import com.maximeleau.harmony.android.pokemon.fixture.TypeDePokemonDataLoader;

import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.fixture.PersonnageNonJoueurDataLoader;


import java.util.ArrayList;

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
        ArrayList<Attaque> attaque1s =
            new ArrayList<Attaque>();
        attaque1s.addAll(AttaqueDataLoader.getInstance(ctx).getMap().values());
        if (!attaque1s.isEmpty()) {
            pokemon.setAttaque1(attaque1s.get(TestUtils.generateRandomInt(0, attaque1s.size())));
        }
        ArrayList<Attaque> attaque2s =
            new ArrayList<Attaque>();
        attaque2s.addAll(AttaqueDataLoader.getInstance(ctx).getMap().values());
        if (!attaque2s.isEmpty()) {
            pokemon.setAttaque2(attaque2s.get(TestUtils.generateRandomInt(0, attaque2s.size())));
        }
        ArrayList<Attaque> attaque3s =
            new ArrayList<Attaque>();
        attaque3s.addAll(AttaqueDataLoader.getInstance(ctx).getMap().values());
        if (!attaque3s.isEmpty()) {
            pokemon.setAttaque3(attaque3s.get(TestUtils.generateRandomInt(0, attaque3s.size())));
        }
        ArrayList<Attaque> attaque4s =
            new ArrayList<Attaque>();
        attaque4s.addAll(AttaqueDataLoader.getInstance(ctx).getMap().values());
        if (!attaque4s.isEmpty()) {
            pokemon.setAttaque4(attaque4s.get(TestUtils.generateRandomInt(0, attaque4s.size())));
        }
        ArrayList<TypeDePokemon> typeDePokemons =
            new ArrayList<TypeDePokemon>();
        typeDePokemons.addAll(TypeDePokemonDataLoader.getInstance(ctx).getMap().values());
        if (!typeDePokemons.isEmpty()) {
            pokemon.setTypeDePokemon(typeDePokemons.get(TestUtils.generateRandomInt(0, typeDePokemons.size())));
        }
        ArrayList<PersonnageNonJoueur> personnageNonJoueurs =
            new ArrayList<PersonnageNonJoueur>();
        personnageNonJoueurs.addAll(PersonnageNonJoueurDataLoader.getInstance(ctx).getMap().values());
        if (!personnageNonJoueurs.isEmpty()) {
            pokemon.setPersonnageNonJoueur(personnageNonJoueurs.get(TestUtils.generateRandomInt(0, personnageNonJoueurs.size())));
        }

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

