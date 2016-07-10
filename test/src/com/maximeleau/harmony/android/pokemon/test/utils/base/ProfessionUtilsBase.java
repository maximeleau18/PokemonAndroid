/**************************************************************************
 * ProfessionUtilsBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.Profession;



import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;


public abstract class ProfessionUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Profession generateRandom(android.content.Context ctx){
        Profession profession = new Profession();

        profession.setId(TestUtils.generateRandomInt(0,100) + 1);
        profession.setNom("nom_"+TestUtils.generateRandomString(10));

        return profession;
    }

    public static boolean equals(Profession profession1,
            Profession profession2){
        return equals(profession1, profession2, true);
    }
    
    public static boolean equals(Profession profession1,
            Profession profession2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(profession1);
        Assert.assertNotNull(profession2);
        if (profession1!=null && profession2 !=null){
            Assert.assertEquals(profession1.getId(), profession2.getId());
            Assert.assertEquals(profession1.getNom(), profession2.getNom());
        }

        return ret;
    }
}

