/**************************************************************************
 * ObjetUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.test.utils.base;


import junit.framework.Assert;
import com.maximeleau.harmony.android.pokemon.entity.Objet;



import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;
import com.maximeleau.harmony.android.pokemon.entity.TypeObjet;
import com.maximeleau.harmony.android.pokemon.fixture.TypeObjetDataLoader;

import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.fixture.PersonnageNonJoueurDataLoader;


import java.util.ArrayList;

public abstract class ObjetUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Objet generateRandom(android.content.Context ctx){
        Objet objet = new Objet();

        objet.setId(TestUtils.generateRandomInt(0,100) + 1);
        objet.setNom("nom_"+TestUtils.generateRandomString(10));
        objet.setQuantite(TestUtils.generateRandomInt(0,100));
        objet.setUrlImage("urlImage_"+TestUtils.generateRandomString(10));
        ArrayList<TypeObjet> typeObjets =
            new ArrayList<TypeObjet>();
        typeObjets.addAll(TypeObjetDataLoader.getInstance(ctx).getMap().values());
        if (!typeObjets.isEmpty()) {
            objet.setTypeObjet(typeObjets.get(TestUtils.generateRandomInt(0, typeObjets.size())));
        }
        ArrayList<PersonnageNonJoueur> personnageNonJoueurs =
            new ArrayList<PersonnageNonJoueur>();
        personnageNonJoueurs.addAll(PersonnageNonJoueurDataLoader.getInstance(ctx).getMap().values());
        if (!personnageNonJoueurs.isEmpty()) {
            objet.setPersonnageNonJoueur(personnageNonJoueurs.get(TestUtils.generateRandomInt(0, personnageNonJoueurs.size())));
        }

        return objet;
    }

    public static boolean equals(Objet objet1,
            Objet objet2){
        return equals(objet1, objet2, true);
    }
    
    public static boolean equals(Objet objet1,
            Objet objet2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(objet1);
        Assert.assertNotNull(objet2);
        if (objet1!=null && objet2 !=null){
            Assert.assertEquals(objet1.getId(), objet2.getId());
            Assert.assertEquals(objet1.getNom(), objet2.getNom());
            Assert.assertEquals(objet1.getQuantite(), objet2.getQuantite());
            Assert.assertEquals(objet1.getUrlImage(), objet2.getUrlImage());
            if (objet1.getTypeObjet() != null
                    && objet2.getTypeObjet() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(objet1.getTypeObjet().getId(),
                            objet2.getTypeObjet().getId());
                }
            }
            if (objet1.getPersonnageNonJoueur() != null
                    && objet2.getPersonnageNonJoueur() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(objet1.getPersonnageNonJoueur().getId(),
                            objet2.getPersonnageNonJoueur().getId());
                }
            }
        }

        return ret;
    }
}

