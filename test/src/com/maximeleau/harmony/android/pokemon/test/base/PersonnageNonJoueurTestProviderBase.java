/**************************************************************************
 * PersonnageNonJoueurTestProviderBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 4, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.maximeleau.harmony.android.pokemon.provider.PersonnageNonJoueurProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.utils.PersonnageNonJoueurProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;

import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurSQLiteAdapter;

import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.entity.Arene;


import java.util.ArrayList;
import com.maximeleau.harmony.android.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** PersonnageNonJoueur database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PersonnageNonJoueurTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class PersonnageNonJoueurTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected PersonnageNonJoueurSQLiteAdapter adapter;

    protected PersonnageNonJoueur entity;
    protected ContentResolver provider;
    protected PersonnageNonJoueurProviderUtils providerUtils;

    protected ArrayList<PersonnageNonJoueur> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new PersonnageNonJoueurSQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new PersonnageNonJoueurProviderUtils(this.getContext());
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /********** Direct Provider calls. *******/

    /** Test case Create Entity */
    @SmallTest
    public void testCreate() {
        Uri result = null;
        if (this.entity != null) {
            PersonnageNonJoueur personnageNonJoueur = PersonnageNonJoueurUtils.generateRandom(this.ctx);

            try {
                ContentValues values = PersonnageNonJoueurContract.itemToContentValues(personnageNonJoueur);
                values.remove(PersonnageNonJoueurContract.COL_ID);
                result = this.provider.insert(PersonnageNonJoueurProviderAdapter.PERSONNAGENONJOUEUR_URI, values);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertNotNull(result);
            Assert.assertTrue(Integer.parseInt(result.getPathSegments().get(1)) > 0);        
            
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        PersonnageNonJoueur result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        PersonnageNonJoueurProviderAdapter.PERSONNAGENONJOUEUR_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = PersonnageNonJoueurContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            PersonnageNonJoueurUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<PersonnageNonJoueur> result = null;
        try {
            android.database.Cursor c = this.provider.query(PersonnageNonJoueurProviderAdapter.PERSONNAGENONJOUEUR_URI, this.adapter.getCols(), null, null, null);
            result = PersonnageNonJoueurContract.cursorToItems(c);
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(result);
        if (result != null) {
            Assert.assertEquals(result.size(), this.nbEntities);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            PersonnageNonJoueur personnageNonJoueur = PersonnageNonJoueurUtils.generateRandom(this.ctx);

            try {
                personnageNonJoueur.setId(this.entity.getId());
                if (this.entity.getArenes() != null) {
                    personnageNonJoueur.getArenes().addAll(this.entity.getArenes());
                }

                ContentValues values = PersonnageNonJoueurContract.itemToContentValues(personnageNonJoueur);
                result = this.provider.update(
                    Uri.parse(PersonnageNonJoueurProviderAdapter.PERSONNAGENONJOUEUR_URI
                        + "/"
                        + personnageNonJoueur.getId()),
                    values,
                    null,
                    null);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Assert.assertTrue(result > 0);
        }
    }

    /** Test case UpdateAll Entity */
    @SmallTest
    public void testUpdateAll() {
        int result = -1;
        if (this.entities != null) {
            if (this.entities.size() > 0) {
                PersonnageNonJoueur personnageNonJoueur = PersonnageNonJoueurUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = PersonnageNonJoueurContract.itemToContentValues(personnageNonJoueur);
                    values.remove(PersonnageNonJoueurContract.COL_ID);
    
                    result = this.provider.update(PersonnageNonJoueurProviderAdapter.PERSONNAGENONJOUEUR_URI, values, null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    
                Assert.assertEquals(result, this.nbEntities);
            }
        }
    }

    /** Test case Delete Entity */
    @SmallTest
    public void testDelete() {
        int result = -1;
        if (this.entity != null) {
            try {
                result = this.provider.delete(
                        Uri.parse(PersonnageNonJoueurProviderAdapter.PERSONNAGENONJOUEUR_URI
                            + "/" 
                            + this.entity.getId()),
                        null,
                        null);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Assert.assertTrue(result >= 0);
        }

    }

    /** Test case DeleteAll Entity */
    @SmallTest
    public void testDeleteAll() {
        int result = -1;
        if (this.entities != null) {
            if (this.entities.size() > 0) {
    
                try {
                    result = this.provider.delete(PersonnageNonJoueurProviderAdapter.PERSONNAGENONJOUEUR_URI, null, null);
    
                } catch (Exception e) {
                    e.printStackTrace();
                }
    
                Assert.assertEquals(result, this.nbEntities);
            }
        }
    }

    /****** Provider Utils calls ********/

    /** Test case Read Entity by provider utils. */
    @SmallTest
    public void testUtilsRead() {
        PersonnageNonJoueur result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            PersonnageNonJoueurUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<PersonnageNonJoueur> result = null;
        result = this.providerUtils.queryAll();

        Assert.assertNotNull(result);
        if (result != null) {
            Assert.assertEquals(result.size(), this.nbEntities);
        }
    }

    /** Test case Update Entity by provider utils. */
    @SmallTest
    public void testUtilsUpdate() {
        int result = -1;
        if (this.entity != null) {
            PersonnageNonJoueur personnageNonJoueur = PersonnageNonJoueurUtils.generateRandom(this.ctx);

            personnageNonJoueur.setId(this.entity.getId());
            if (this.entity.getArenes() != null) {
                for (Arene arenes : this.entity.getArenes()) {
                    boolean found = false;
                    for (Arene arenes2 : personnageNonJoueur.getArenes()) {
                        if (arenes.getId() == arenes2.getId() ) {
                            found = true;
                            break;
                        }
                    }                    
                    if(!found) {
                        personnageNonJoueur.getArenes().add(arenes);
                    }
                }
            }
            result = this.providerUtils.update(personnageNonJoueur);

            Assert.assertTrue(result > 0);
        }
    }


    /** Test case Delete Entity by provider utils. */
    @SmallTest
    public void testUtilsDelete() {
        int result = -1;
        if (this.entity != null) {
            result = this.providerUtils.delete(this.entity);
            Assert.assertTrue(result >= 0);
        }

    }
}
