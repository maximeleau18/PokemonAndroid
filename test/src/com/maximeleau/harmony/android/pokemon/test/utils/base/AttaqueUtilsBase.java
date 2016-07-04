/**************************************************************************
 * AttaqueUtilsBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.Attaque;



import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;

import com.maximeleau.harmony.android.pokemon.test.utils.TypeAttaqueUtils;


public abstract class AttaqueUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Attaque generateRandom(android.content.Context ctx){
        Attaque attaque = new Attaque();

        attaque.setId(TestUtils.generateRandomInt(0,100) + 1);
        attaque.setNom("nom_"+TestUtils.generateRandomString(10));
        attaque.setPuissance(TestUtils.generateRandomInt(0,100));
        attaque.setDegats(TestUtils.generateRandomInt(0,100));
        attaque.setTypeAttaque(TypeAttaqueUtils.generateRandom(ctx));

        return attaque;
    }

    public static boolean equals(Attaque attaque1,
            Attaque attaque2){
        return equals(attaque1, attaque2, true);
    }
    
    public static boolean equals(Attaque attaque1,
            Attaque attaque2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(attaque1);
        Assert.assertNotNull(attaque2);
        if (attaque1!=null && attaque2 !=null){
            Assert.assertEquals(attaque1.getId(), attaque2.getId());
            Assert.assertEquals(attaque1.getNom(), attaque2.getNom());
            Assert.assertEquals(attaque1.getPuissance(), attaque2.getPuissance());
            Assert.assertEquals(attaque1.getDegats(), attaque2.getDegats());
            if (attaque1.getTypeAttaque() != null
                    && attaque2.getTypeAttaque() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(attaque1.getTypeAttaque().getId(),
                            attaque2.getTypeAttaque().getId());
                }
            }
        }

        return ret;
    }
}

