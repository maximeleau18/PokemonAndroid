/**************************************************************************
 * DresseurUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 10, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.test.utils.base;


import junit.framework.Assert;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;



import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.fixture.PersonnageNonJoueurDataLoader;


import java.util.ArrayList;

public abstract class DresseurUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Dresseur generateRandom(android.content.Context ctx){
        Dresseur dresseur = new Dresseur();

        dresseur.setId(TestUtils.generateRandomInt(0,100) + 1);
        dresseur.setNom("nom_"+TestUtils.generateRandomString(10));
        dresseur.setPrenom("prenom_"+TestUtils.generateRandomString(10));
        dresseur.setLogin("login_"+TestUtils.generateRandomString(10));
        dresseur.setMotDePasse("motDePasse_"+TestUtils.generateRandomString(10));
        ArrayList<PersonnageNonJoueur> personnageNonJoueurs =
            new ArrayList<PersonnageNonJoueur>();
        personnageNonJoueurs.addAll(PersonnageNonJoueurDataLoader.getInstance(ctx).getMap().values());
        if (!personnageNonJoueurs.isEmpty()) {
            dresseur.setPersonnageNonJoueur(personnageNonJoueurs.get(TestUtils.generateRandomInt(0, personnageNonJoueurs.size())));
        }

        return dresseur;
    }

    public static boolean equals(Dresseur dresseur1,
            Dresseur dresseur2){
        return equals(dresseur1, dresseur2, true);
    }
    
    public static boolean equals(Dresseur dresseur1,
            Dresseur dresseur2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(dresseur1);
        Assert.assertNotNull(dresseur2);
        if (dresseur1!=null && dresseur2 !=null){
            Assert.assertEquals(dresseur1.getId(), dresseur2.getId());
            Assert.assertEquals(dresseur1.getNom(), dresseur2.getNom());
            Assert.assertEquals(dresseur1.getPrenom(), dresseur2.getPrenom());
            Assert.assertEquals(dresseur1.getLogin(), dresseur2.getLogin());
            Assert.assertEquals(dresseur1.getMotDePasse(), dresseur2.getMotDePasse());
            if (dresseur1.getPersonnageNonJoueur() != null
                    && dresseur2.getPersonnageNonJoueur() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(dresseur1.getPersonnageNonJoueur().getId(),
                            dresseur2.getPersonnageNonJoueur().getId());
                }
            }
        }

        return ret;
    }
}

