/**************************************************************************
 * PersonnageNonJoueurBadgeUtilsBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueurBadge;



import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;

import com.maximeleau.harmony.android.pokemon.test.utils.PersonnageNonJoueurUtils;

import com.maximeleau.harmony.android.pokemon.test.utils.BadgeUtils;


public abstract class PersonnageNonJoueurBadgeUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static PersonnageNonJoueurBadge generateRandom(android.content.Context ctx){
        PersonnageNonJoueurBadge personnageNonJoueurBadge = new PersonnageNonJoueurBadge();

        personnageNonJoueurBadge.setId(TestUtils.generateRandomInt(0,100) + 1);
        personnageNonJoueurBadge.setPersonnageNonJoueur(PersonnageNonJoueurUtils.generateRandom(ctx));
        personnageNonJoueurBadge.setBadge(BadgeUtils.generateRandom(ctx));

        return personnageNonJoueurBadge;
    }

    public static boolean equals(PersonnageNonJoueurBadge personnageNonJoueurBadge1,
            PersonnageNonJoueurBadge personnageNonJoueurBadge2){
        return equals(personnageNonJoueurBadge1, personnageNonJoueurBadge2, true);
    }
    
    public static boolean equals(PersonnageNonJoueurBadge personnageNonJoueurBadge1,
            PersonnageNonJoueurBadge personnageNonJoueurBadge2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(personnageNonJoueurBadge1);
        Assert.assertNotNull(personnageNonJoueurBadge2);
        if (personnageNonJoueurBadge1!=null && personnageNonJoueurBadge2 !=null){
            Assert.assertEquals(personnageNonJoueurBadge1.getId(), personnageNonJoueurBadge2.getId());
            if (personnageNonJoueurBadge1.getPersonnageNonJoueur() != null
                    && personnageNonJoueurBadge2.getPersonnageNonJoueur() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(personnageNonJoueurBadge1.getPersonnageNonJoueur().getId(),
                            personnageNonJoueurBadge2.getPersonnageNonJoueur().getId());
                }
            }
            if (personnageNonJoueurBadge1.getBadge() != null
                    && personnageNonJoueurBadge2.getBadge() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(personnageNonJoueurBadge1.getBadge().getId(),
                            personnageNonJoueurBadge2.getBadge().getId());
                }
            }
        }

        return ret;
    }
}

