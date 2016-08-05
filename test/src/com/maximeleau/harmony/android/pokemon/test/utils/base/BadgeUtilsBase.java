/**************************************************************************
 * BadgeUtilsBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.Badge;



import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;


public abstract class BadgeUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Badge generateRandom(android.content.Context ctx){
        Badge badge = new Badge();

        badge.setId(TestUtils.generateRandomInt(0,100) + 1);
        badge.setNom("nom_"+TestUtils.generateRandomString(10));

        return badge;
    }

    public static boolean equals(Badge badge1,
            Badge badge2){
        return equals(badge1, badge2, true);
    }
    
    public static boolean equals(Badge badge1,
            Badge badge2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(badge1);
        Assert.assertNotNull(badge2);
        if (badge1!=null && badge2 !=null){
            Assert.assertEquals(badge1.getId(), badge2.getId());
            Assert.assertEquals(badge1.getNom(), badge2.getNom());
        }

        return ret;
    }
}

