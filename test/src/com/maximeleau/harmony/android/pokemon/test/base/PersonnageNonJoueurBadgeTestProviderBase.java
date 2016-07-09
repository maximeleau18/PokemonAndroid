/**************************************************************************
 * PersonnageNonJoueurBadgeTestProviderBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.maximeleau.harmony.android.pokemon.provider.PersonnageNonJoueurBadgeProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.utils.PersonnageNonJoueurBadgeProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurBadgeContract;

import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurBadgeSQLiteAdapter;

import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueurBadge;


import java.util.ArrayList;
import com.maximeleau.harmony.android.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** PersonnageNonJoueurBadge database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PersonnageNonJoueurBadgeTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class PersonnageNonJoueurBadgeTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected PersonnageNonJoueurBadgeSQLiteAdapter adapter;

    protected PersonnageNonJoueurBadge entity;
    protected ContentResolver provider;
    protected PersonnageNonJoueurBadgeProviderUtils providerUtils;

    protected ArrayList<PersonnageNonJoueurBadge> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new PersonnageNonJoueurBadgeSQLiteAdapter(this.ctx);

        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new PersonnageNonJoueurBadgeProviderUtils(this.getContext());
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
            PersonnageNonJoueurBadge personnageNonJoueurBadge = PersonnageNonJoueurBadgeUtils.generateRandom(this.ctx);

            try {
                ContentValues values = PersonnageNonJoueurBadgeContract.itemToContentValues(personnageNonJoueurBadge);
                values.remove(PersonnageNonJoueurBadgeContract.COL_ID);
                result = this.provider.insert(PersonnageNonJoueurBadgeProviderAdapter.PERSONNAGENONJOUEURBADGE_URI, values);

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
        PersonnageNonJoueurBadge result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        PersonnageNonJoueurBadgeProviderAdapter.PERSONNAGENONJOUEURBADGE_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = PersonnageNonJoueurBadgeContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            PersonnageNonJoueurBadgeUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<PersonnageNonJoueurBadge> result = null;
        try {
            android.database.Cursor c = this.provider.query(PersonnageNonJoueurBadgeProviderAdapter.PERSONNAGENONJOUEURBADGE_URI, this.adapter.getCols(), null, null, null);
            result = PersonnageNonJoueurBadgeContract.cursorToItems(c);
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
            PersonnageNonJoueurBadge personnageNonJoueurBadge = PersonnageNonJoueurBadgeUtils.generateRandom(this.ctx);

            try {
                personnageNonJoueurBadge.setId(this.entity.getId());

                ContentValues values = PersonnageNonJoueurBadgeContract.itemToContentValues(personnageNonJoueurBadge);
                result = this.provider.update(
                    Uri.parse(PersonnageNonJoueurBadgeProviderAdapter.PERSONNAGENONJOUEURBADGE_URI
                        + "/"
                        + personnageNonJoueurBadge.getId()),
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
                PersonnageNonJoueurBadge personnageNonJoueurBadge = PersonnageNonJoueurBadgeUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = PersonnageNonJoueurBadgeContract.itemToContentValues(personnageNonJoueurBadge);
                    values.remove(PersonnageNonJoueurBadgeContract.COL_ID);
    
                    result = this.provider.update(PersonnageNonJoueurBadgeProviderAdapter.PERSONNAGENONJOUEURBADGE_URI, values, null, null);
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
                        Uri.parse(PersonnageNonJoueurBadgeProviderAdapter.PERSONNAGENONJOUEURBADGE_URI
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
                    result = this.provider.delete(PersonnageNonJoueurBadgeProviderAdapter.PERSONNAGENONJOUEURBADGE_URI, null, null);
    
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
        PersonnageNonJoueurBadge result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            PersonnageNonJoueurBadgeUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<PersonnageNonJoueurBadge> result = null;
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
            PersonnageNonJoueurBadge personnageNonJoueurBadge = PersonnageNonJoueurBadgeUtils.generateRandom(this.ctx);

            personnageNonJoueurBadge.setId(this.entity.getId());
            result = this.providerUtils.update(personnageNonJoueurBadge);

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
