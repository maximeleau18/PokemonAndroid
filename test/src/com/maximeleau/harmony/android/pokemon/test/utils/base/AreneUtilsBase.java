/**************************************************************************
 * AreneUtilsBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.Arene;



import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;

import com.maximeleau.harmony.android.pokemon.test.utils.PersonnageNonJoueurUtils;

import com.maximeleau.harmony.android.pokemon.test.utils.BadgeUtils;

import com.maximeleau.harmony.android.pokemon.test.utils.PositionUtils;


public abstract class AreneUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Arene generateRandom(android.content.Context ctx){
        Arene arene = new Arene();

        arene.setId(TestUtils.generateRandomInt(0,100) + 1);
        arene.setNom("nom_"+TestUtils.generateRandomString(10));
        arene.setMaitre(PersonnageNonJoueurUtils.generateRandom(ctx));
        arene.setBadge(BadgeUtils.generateRandom(ctx));
        arene.setPosition(PositionUtils.generateRandom(ctx));

        return arene;
    }

    public static boolean equals(Arene arene1,
            Arene arene2){
        return equals(arene1, arene2, true);
    }
    
    public static boolean equals(Arene arene1,
            Arene arene2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(arene1);
        Assert.assertNotNull(arene2);
        if (arene1!=null && arene2 !=null){
            Assert.assertEquals(arene1.getId(), arene2.getId());
            Assert.assertEquals(arene1.getNom(), arene2.getNom());
            if (arene1.getMaitre() != null
                    && arene2.getMaitre() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(arene1.getMaitre().getId(),
                            arene2.getMaitre().getId());
                }
            }
            if (arene1.getBadge() != null
                    && arene2.getBadge() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(arene1.getBadge().getId(),
                            arene2.getBadge().getId());
                }
            }
            if (arene1.getPosition() != null
                    && arene2.getPosition() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(arene1.getPosition().getId(),
                            arene2.getPosition().getId());
                }
            }
        }

        return ret;
    }
}

