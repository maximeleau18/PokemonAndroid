/**************************************************************************
 * AreneTestProviderBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Aug 5, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.maximeleau.harmony.android.pokemon.provider.AreneProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.utils.AreneProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;

import com.maximeleau.harmony.android.pokemon.data.AreneSQLiteAdapter;

import com.maximeleau.harmony.android.pokemon.entity.Arene;

import com.maximeleau.harmony.android.pokemon.fixture.AreneDataLoader;

import java.util.ArrayList;
import com.maximeleau.harmony.android.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Arene database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit AreneTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class AreneTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected AreneSQLiteAdapter adapter;

    protected Arene entity;
    protected ContentResolver provider;
    protected AreneProviderUtils providerUtils;

    protected ArrayList<Arene> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new AreneSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<Arene>();
        this.entities.addAll(AreneDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += AreneDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new AreneProviderUtils(this.getContext());
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
            Arene arene = AreneUtils.generateRandom(this.ctx);

            try {
                ContentValues values = AreneContract.itemToContentValues(arene, 0);
                values.remove(AreneContract.COL_ID);
                result = this.provider.insert(AreneProviderAdapter.ARENE_URI, values);

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
        Arene result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        AreneProviderAdapter.ARENE_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = AreneContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            AreneUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Arene> result = null;
        try {
            android.database.Cursor c = this.provider.query(AreneProviderAdapter.ARENE_URI, this.adapter.getCols(), null, null, null);
            result = AreneContract.cursorToItems(c);
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
            Arene arene = AreneUtils.generateRandom(this.ctx);

            try {
                arene.setId(this.entity.getId());

                ContentValues values = AreneContract.itemToContentValues(arene, 0);
                result = this.provider.update(
                    Uri.parse(AreneProviderAdapter.ARENE_URI
                        + "/"
                        + arene.getId()),
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
                Arene arene = AreneUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = AreneContract.itemToContentValues(arene, 0);
                    values.remove(AreneContract.COL_ID);
    
                    result = this.provider.update(AreneProviderAdapter.ARENE_URI, values, null, null);
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
                        Uri.parse(AreneProviderAdapter.ARENE_URI
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
                    result = this.provider.delete(AreneProviderAdapter.ARENE_URI, null, null);
    
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
        Arene result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            AreneUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Arene> result = null;
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
            Arene arene = AreneUtils.generateRandom(this.ctx);

            arene.setId(this.entity.getId());
            result = this.providerUtils.update(arene);

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
