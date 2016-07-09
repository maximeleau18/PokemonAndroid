/**************************************************************************
 * TypeDePokemonEvolutionUtilsBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonEvolution;



import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;

import com.maximeleau.harmony.android.pokemon.test.utils.TypeDePokemonUtils;


public abstract class TypeDePokemonEvolutionUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static TypeDePokemonEvolution generateRandom(android.content.Context ctx){
        TypeDePokemonEvolution typeDePokemonEvolution = new TypeDePokemonEvolution();

        typeDePokemonEvolution.setEvolueEn(TypeDePokemonUtils.generateRandom(ctx));
        typeDePokemonEvolution.setEstEvolueEn(TypeDePokemonUtils.generateRandom(ctx));

        return typeDePokemonEvolution;
    }

    public static boolean equals(TypeDePokemonEvolution typeDePokemonEvolution1,
            TypeDePokemonEvolution typeDePokemonEvolution2){
        return equals(typeDePokemonEvolution1, typeDePokemonEvolution2, true);
    }
    
    public static boolean equals(TypeDePokemonEvolution typeDePokemonEvolution1,
            TypeDePokemonEvolution typeDePokemonEvolution2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(typeDePokemonEvolution1);
        Assert.assertNotNull(typeDePokemonEvolution2);
        if (typeDePokemonEvolution1!=null && typeDePokemonEvolution2 !=null){
            if (typeDePokemonEvolution1.getEvolueEn() != null
                    && typeDePokemonEvolution2.getEvolueEn() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(typeDePokemonEvolution1.getEvolueEn().getId(),
                            typeDePokemonEvolution2.getEvolueEn().getId());
                }
            }
            if (typeDePokemonEvolution1.getEstEvolueEn() != null
                    && typeDePokemonEvolution2.getEstEvolueEn() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(typeDePokemonEvolution1.getEstEvolueEn().getId(),
                            typeDePokemonEvolution2.getEstEvolueEn().getId());
                }
            }
        }

        return ret;
    }
}

