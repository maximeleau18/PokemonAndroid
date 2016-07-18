/**************************************************************************
 * ZoneTestProviderBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.provider.ZoneProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.utils.ZoneProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.contract.ZoneContract;

import com.maximeleau.harmony.android.pokemon.data.ZoneSQLiteAdapter;

import com.maximeleau.harmony.android.pokemon.entity.Zone;

import com.maximeleau.harmony.android.pokemon.fixture.ZoneDataLoader;

import java.util.ArrayList;
import com.maximeleau.harmony.android.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Zone database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ZoneTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ZoneTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected ZoneSQLiteAdapter adapter;

    protected Zone entity;
    protected ContentResolver provider;
    protected ZoneProviderUtils providerUtils;

    protected ArrayList<Zone> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new ZoneSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<Zone>();
        this.entities.addAll(ZoneDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += ZoneDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new ZoneProviderUtils(this.getContext());
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
            Zone zone = ZoneUtils.generateRandom(this.ctx);

            try {
                ContentValues values = ZoneContract.itemToContentValues(zone);
                values.remove(ZoneContract.COL_ID);
                result = this.provider.insert(ZoneProviderAdapter.ZONE_URI, values);

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
        Zone result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        ZoneProviderAdapter.ZONE_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = ZoneContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            ZoneUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Zone> result = null;
        try {
            android.database.Cursor c = this.provider.query(ZoneProviderAdapter.ZONE_URI, this.adapter.getCols(), null, null, null);
            result = ZoneContract.cursorToItems(c);
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
            Zone zone = ZoneUtils.generateRandom(this.ctx);

            try {
                zone.setId(this.entity.getId());

                ContentValues values = ZoneContract.itemToContentValues(zone);
                result = this.provider.update(
                    Uri.parse(ZoneProviderAdapter.ZONE_URI
                        + "/"
                        + zone.getId()),
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
                Zone zone = ZoneUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = ZoneContract.itemToContentValues(zone);
                    values.remove(ZoneContract.COL_ID);
    
                    result = this.provider.update(ZoneProviderAdapter.ZONE_URI, values, null, null);
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
                        Uri.parse(ZoneProviderAdapter.ZONE_URI
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
                    result = this.provider.delete(ZoneProviderAdapter.ZONE_URI, null, null);
    
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
        Zone result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            ZoneUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Zone> result = null;
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
            Zone zone = ZoneUtils.generateRandom(this.ctx);

            zone.setId(this.entity.getId());
            result = this.providerUtils.update(zone);

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
