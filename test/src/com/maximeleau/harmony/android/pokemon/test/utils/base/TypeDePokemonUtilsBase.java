/**************************************************************************
 * TypeDePokemonUtilsBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;



import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.test.utils.PokemonUtils;

import java.util.ArrayList;

public abstract class TypeDePokemonUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static TypeDePokemon generateRandom(android.content.Context ctx){
        TypeDePokemon typeDePokemon = new TypeDePokemon();

        typeDePokemon.setId(TestUtils.generateRandomInt(0,100) + 1);
        typeDePokemon.setNom("nom_"+TestUtils.generateRandomString(10));
        typeDePokemon.setAttaque(TestUtils.generateRandomInt(0,100));
        typeDePokemon.setDefense(TestUtils.generateRandomInt(0,100));
        typeDePokemon.setPv(TestUtils.generateRandomInt(0,100));
        typeDePokemon.setNumPokedex(TestUtils.generateRandomInt(0,100));
        ArrayList<Pokemon> relatedPokemonss = new ArrayList<Pokemon>();
        relatedPokemonss.add(PokemonUtils.generateRandom(ctx));
        typeDePokemon.setPokemons(relatedPokemonss);

        return typeDePokemon;
    }

    public static boolean equals(TypeDePokemon typeDePokemon1,
            TypeDePokemon typeDePokemon2){
        return equals(typeDePokemon1, typeDePokemon2, true);
    }
    
    public static boolean equals(TypeDePokemon typeDePokemon1,
            TypeDePokemon typeDePokemon2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(typeDePokemon1);
        Assert.assertNotNull(typeDePokemon2);
        if (typeDePokemon1!=null && typeDePokemon2 !=null){
            Assert.assertEquals(typeDePokemon1.getId(), typeDePokemon2.getId());
            Assert.assertEquals(typeDePokemon1.getNom(), typeDePokemon2.getNom());
            Assert.assertEquals(typeDePokemon1.getAttaque(), typeDePokemon2.getAttaque());
            Assert.assertEquals(typeDePokemon1.getDefense(), typeDePokemon2.getDefense());
            Assert.assertEquals(typeDePokemon1.getPv(), typeDePokemon2.getPv());
            Assert.assertEquals(typeDePokemon1.getNumPokedex(), typeDePokemon2.getNumPokedex());
            if (typeDePokemon1.getPokemons() != null
                    && typeDePokemon2.getPokemons() != null) {
                Assert.assertEquals(typeDePokemon1.getPokemons().size(),
                    typeDePokemon2.getPokemons().size());
                if (checkRecursiveId) {
                    for (Pokemon pokemons1 : typeDePokemon1.getPokemons()) {
                        boolean found = false;
                        for (Pokemon pokemons2 : typeDePokemon2.getPokemons()) {
                            if (pokemons1.getId() == pokemons2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated pokemons (id = %s) in TypeDePokemon (id = %s)",
                                        pokemons1.getId(),
                                        typeDePokemon1.getId()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}

