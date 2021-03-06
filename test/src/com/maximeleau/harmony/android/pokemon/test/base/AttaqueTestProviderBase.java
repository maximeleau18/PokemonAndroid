/**************************************************************************
 * AttaqueTestProviderBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.provider.AttaqueProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.utils.AttaqueProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.contract.AttaqueContract;

import com.maximeleau.harmony.android.pokemon.data.AttaqueSQLiteAdapter;

import com.maximeleau.harmony.android.pokemon.entity.Attaque;

import com.maximeleau.harmony.android.pokemon.fixture.AttaqueDataLoader;

import java.util.ArrayList;
import com.maximeleau.harmony.android.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Attaque database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit AttaqueTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class AttaqueTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected AttaqueSQLiteAdapter adapter;

    protected Attaque entity;
    protected ContentResolver provider;
    protected AttaqueProviderUtils providerUtils;

    protected ArrayList<Attaque> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new AttaqueSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<Attaque>();
        this.entities.addAll(AttaqueDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += AttaqueDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new AttaqueProviderUtils(this.getContext());
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
            Attaque attaque = AttaqueUtils.generateRandom(this.ctx);

            try {
                ContentValues values = AttaqueContract.itemToContentValues(attaque);
                values.remove(AttaqueContract.COL_ID);
                result = this.provider.insert(AttaqueProviderAdapter.ATTAQUE_URI, values);

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
        Attaque result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        AttaqueProviderAdapter.ATTAQUE_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = AttaqueContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            AttaqueUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Attaque> result = null;
        try {
            android.database.Cursor c = this.provider.query(AttaqueProviderAdapter.ATTAQUE_URI, this.adapter.getCols(), null, null, null);
            result = AttaqueContract.cursorToItems(c);
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
            Attaque attaque = AttaqueUtils.generateRandom(this.ctx);

            try {
                attaque.setId(this.entity.getId());

                ContentValues values = AttaqueContract.itemToContentValues(attaque);
                result = this.provider.update(
                    Uri.parse(AttaqueProviderAdapter.ATTAQUE_URI
                        + "/"
                        + attaque.getId()),
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
                Attaque attaque = AttaqueUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = AttaqueContract.itemToContentValues(attaque);
                    values.remove(AttaqueContract.COL_ID);
    
                    result = this.provider.update(AttaqueProviderAdapter.ATTAQUE_URI, values, null, null);
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
                        Uri.parse(AttaqueProviderAdapter.ATTAQUE_URI
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
                    result = this.provider.delete(AttaqueProviderAdapter.ATTAQUE_URI, null, null);
    
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
        Attaque result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            AttaqueUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Attaque> result = null;
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
            Attaque attaque = AttaqueUtils.generateRandom(this.ctx);

            attaque.setId(this.entity.getId());
            result = this.providerUtils.update(attaque);

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
