/**************************************************************************
 * DresseurTestProviderBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 18, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.maximeleau.harmony.android.pokemon.provider.DresseurProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.utils.DresseurProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;

import com.maximeleau.harmony.android.pokemon.data.DresseurSQLiteAdapter;

import com.maximeleau.harmony.android.pokemon.entity.Dresseur;

import com.maximeleau.harmony.android.pokemon.fixture.DresseurDataLoader;

import java.util.ArrayList;
import com.maximeleau.harmony.android.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Dresseur database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit DresseurTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class DresseurTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected DresseurSQLiteAdapter adapter;

    protected Dresseur entity;
    protected ContentResolver provider;
    protected DresseurProviderUtils providerUtils;

    protected ArrayList<Dresseur> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new DresseurSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<Dresseur>();
        this.entities.addAll(DresseurDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += DresseurDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new DresseurProviderUtils(this.getContext());
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
            Dresseur dresseur = DresseurUtils.generateRandom(this.ctx);

            try {
                ContentValues values = DresseurContract.itemToContentValues(dresseur, 0, 0);
                values.remove(DresseurContract.COL_ID);
                result = this.provider.insert(DresseurProviderAdapter.DRESSEUR_URI, values);

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
        Dresseur result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        DresseurProviderAdapter.DRESSEUR_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = DresseurContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            DresseurUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Dresseur> result = null;
        try {
            android.database.Cursor c = this.provider.query(DresseurProviderAdapter.DRESSEUR_URI, this.adapter.getCols(), null, null, null);
            result = DresseurContract.cursorToItems(c);
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
            Dresseur dresseur = DresseurUtils.generateRandom(this.ctx);

            try {
                dresseur.setId(this.entity.getId());

                ContentValues values = DresseurContract.itemToContentValues(dresseur, 0, 0);
                result = this.provider.update(
                    Uri.parse(DresseurProviderAdapter.DRESSEUR_URI
                        + "/"
                        + dresseur.getId()),
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
                Dresseur dresseur = DresseurUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = DresseurContract.itemToContentValues(dresseur, 0, 0);
                    values.remove(DresseurContract.COL_ID);
    
                    result = this.provider.update(DresseurProviderAdapter.DRESSEUR_URI, values, null, null);
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
                        Uri.parse(DresseurProviderAdapter.DRESSEUR_URI
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
                    result = this.provider.delete(DresseurProviderAdapter.DRESSEUR_URI, null, null);
    
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
        Dresseur result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            DresseurUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Dresseur> result = null;
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
            Dresseur dresseur = DresseurUtils.generateRandom(this.ctx);

            dresseur.setId(this.entity.getId());
            result = this.providerUtils.update(dresseur);

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
