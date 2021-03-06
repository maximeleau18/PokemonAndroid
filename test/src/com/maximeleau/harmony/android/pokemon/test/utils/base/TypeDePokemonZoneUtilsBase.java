/**************************************************************************
 * TypeDePokemonZoneUtilsBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonZone;



import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;
import com.maximeleau.harmony.android.pokemon.entity.Zone;
import com.maximeleau.harmony.android.pokemon.fixture.ZoneDataLoader;

import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;
import com.maximeleau.harmony.android.pokemon.fixture.TypeDePokemonDataLoader;


import java.util.ArrayList;

public abstract class TypeDePokemonZoneUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static TypeDePokemonZone generateRandom(android.content.Context ctx){
        TypeDePokemonZone typeDePokemonZone = new TypeDePokemonZone();

        typeDePokemonZone.setId(TestUtils.generateRandomInt(0,100) + 1);
        ArrayList<Zone> zones =
            new ArrayList<Zone>();
        zones.addAll(ZoneDataLoader.getInstance(ctx).getMap().values());
        if (!zones.isEmpty()) {
            typeDePokemonZone.setZone(zones.get(TestUtils.generateRandomInt(0, zones.size())));
        }
        ArrayList<TypeDePokemon> typeDePokemons =
            new ArrayList<TypeDePokemon>();
        typeDePokemons.addAll(TypeDePokemonDataLoader.getInstance(ctx).getMap().values());
        if (!typeDePokemons.isEmpty()) {
            typeDePokemonZone.setTypeDePokemon(typeDePokemons.get(TestUtils.generateRandomInt(0, typeDePokemons.size())));
        }

        return typeDePokemonZone;
    }

    public static boolean equals(TypeDePokemonZone typeDePokemonZone1,
            TypeDePokemonZone typeDePokemonZone2){
        return equals(typeDePokemonZone1, typeDePokemonZone2, true);
    }
    
    public static boolean equals(TypeDePokemonZone typeDePokemonZone1,
            TypeDePokemonZone typeDePokemonZone2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(typeDePokemonZone1);
        Assert.assertNotNull(typeDePokemonZone2);
        if (typeDePokemonZone1!=null && typeDePokemonZone2 !=null){
            Assert.assertEquals(typeDePokemonZone1.getId(), typeDePokemonZone2.getId());
            if (typeDePokemonZone1.getZone() != null
                    && typeDePokemonZone2.getZone() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(typeDePokemonZone1.getZone().getId(),
                            typeDePokemonZone2.getZone().getId());
                }
            }
            if (typeDePokemonZone1.getTypeDePokemon() != null
                    && typeDePokemonZone2.getTypeDePokemon() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(typeDePokemonZone1.getTypeDePokemon().getId(),
                            typeDePokemonZone2.getTypeDePokemon().getId());
                }
            }
        }

        return ret;
    }
}

