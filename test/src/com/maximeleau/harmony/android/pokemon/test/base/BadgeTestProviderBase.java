/**************************************************************************
 * BadgeTestProviderBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.provider.BadgeProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.utils.BadgeProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.contract.BadgeContract;

import com.maximeleau.harmony.android.pokemon.data.BadgeSQLiteAdapter;

import com.maximeleau.harmony.android.pokemon.entity.Badge;


import java.util.ArrayList;
import com.maximeleau.harmony.android.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Badge database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit BadgeTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class BadgeTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected BadgeSQLiteAdapter adapter;

    protected Badge entity;
    protected ContentResolver provider;
    protected BadgeProviderUtils providerUtils;

    protected ArrayList<Badge> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new BadgeSQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new BadgeProviderUtils(this.getContext());
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
            Badge badge = BadgeUtils.generateRandom(this.ctx);

            try {
                ContentValues values = BadgeContract.itemToContentValues(badge, 0);
                values.remove(BadgeContract.COL_ID);
                result = this.provider.insert(BadgeProviderAdapter.BADGE_URI, values);

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
        Badge result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        BadgeProviderAdapter.BADGE_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = BadgeContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            BadgeUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Badge> result = null;
        try {
            android.database.Cursor c = this.provider.query(BadgeProviderAdapter.BADGE_URI, this.adapter.getCols(), null, null, null);
            result = BadgeContract.cursorToItems(c);
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
            Badge badge = BadgeUtils.generateRandom(this.ctx);

            try {
                badge.setId(this.entity.getId());

                ContentValues values = BadgeContract.itemToContentValues(badge, 0);
                result = this.provider.update(
                    Uri.parse(BadgeProviderAdapter.BADGE_URI
                        + "/"
                        + badge.getId()),
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
                Badge badge = BadgeUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = BadgeContract.itemToContentValues(badge, 0);
                    values.remove(BadgeContract.COL_ID);
    
                    result = this.provider.update(BadgeProviderAdapter.BADGE_URI, values, null, null);
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
                        Uri.parse(BadgeProviderAdapter.BADGE_URI
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
                    result = this.provider.delete(BadgeProviderAdapter.BADGE_URI, null, null);
    
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
        Badge result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            BadgeUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Badge> result = null;
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
            Badge badge = BadgeUtils.generateRandom(this.ctx);

            badge.setId(this.entity.getId());
            result = this.providerUtils.update(badge);

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
