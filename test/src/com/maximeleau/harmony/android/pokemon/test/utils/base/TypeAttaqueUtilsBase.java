/**************************************************************************
 * TypeAttaqueUtilsBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.TypeAttaque;



import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;


public abstract class TypeAttaqueUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static TypeAttaque generateRandom(android.content.Context ctx){
        TypeAttaque typeAttaque = new TypeAttaque();

        typeAttaque.setId(TestUtils.generateRandomInt(0,100) + 1);
        typeAttaque.setNom("nom_"+TestUtils.generateRandomString(10));

        return typeAttaque;
    }

    public static boolean equals(TypeAttaque typeAttaque1,
            TypeAttaque typeAttaque2){
        return equals(typeAttaque1, typeAttaque2, true);
    }
    
    public static boolean equals(TypeAttaque typeAttaque1,
            TypeAttaque typeAttaque2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(typeAttaque1);
        Assert.assertNotNull(typeAttaque2);
        if (typeAttaque1!=null && typeAttaque2 !=null){
            Assert.assertEquals(typeAttaque1.getId(), typeAttaque2.getId());
            Assert.assertEquals(typeAttaque1.getNom(), typeAttaque2.getNom());
        }

        return ret;
    }
}

