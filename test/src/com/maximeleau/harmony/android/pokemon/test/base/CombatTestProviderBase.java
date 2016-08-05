/**************************************************************************
 * CombatTestProviderBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.provider.CombatProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.utils.CombatProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.contract.CombatContract;

import com.maximeleau.harmony.android.pokemon.data.CombatSQLiteAdapter;

import com.maximeleau.harmony.android.pokemon.entity.Combat;

import com.maximeleau.harmony.android.pokemon.fixture.CombatDataLoader;

import java.util.ArrayList;
import com.maximeleau.harmony.android.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Combat database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit CombatTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class CombatTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected CombatSQLiteAdapter adapter;

    protected Combat entity;
    protected ContentResolver provider;
    protected CombatProviderUtils providerUtils;

    protected ArrayList<Combat> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new CombatSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<Combat>();
        this.entities.addAll(CombatDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += CombatDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new CombatProviderUtils(this.getContext());
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
            Combat combat = CombatUtils.generateRandom(this.ctx);

            try {
                ContentValues values = CombatContract.itemToContentValues(combat);
                values.remove(CombatContract.COL_ID);
                result = this.provider.insert(CombatProviderAdapter.COMBAT_URI, values);

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
        Combat result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        CombatProviderAdapter.COMBAT_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = CombatContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            CombatUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Combat> result = null;
        try {
            android.database.Cursor c = this.provider.query(CombatProviderAdapter.COMBAT_URI, this.adapter.getCols(), null, null, null);
            result = CombatContract.cursorToItems(c);
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
            Combat combat = CombatUtils.generateRandom(this.ctx);

            try {
                combat.setId(this.entity.getId());

                ContentValues values = CombatContract.itemToContentValues(combat);
                result = this.provider.update(
                    Uri.parse(CombatProviderAdapter.COMBAT_URI
                        + "/"
                        + combat.getId()),
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
                Combat combat = CombatUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = CombatContract.itemToContentValues(combat);
                    values.remove(CombatContract.COL_ID);
    
                    result = this.provider.update(CombatProviderAdapter.COMBAT_URI, values, null, null);
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
                        Uri.parse(CombatProviderAdapter.COMBAT_URI
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
                    result = this.provider.delete(CombatProviderAdapter.COMBAT_URI, null, null);
    
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
        Combat result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            CombatUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Combat> result = null;
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
            Combat combat = CombatUtils.generateRandom(this.ctx);

            combat.setId(this.entity.getId());
            result = this.providerUtils.update(combat);

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
