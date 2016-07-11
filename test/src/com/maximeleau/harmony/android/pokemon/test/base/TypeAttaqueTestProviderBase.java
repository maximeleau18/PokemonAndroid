/**************************************************************************
 * TypeAttaqueTestProviderBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 10, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.test.base;

import android.test.suitebuilder.annotation.SmallTest;

import com.maximeleau.harmony.android.pokemon.provider.TypeAttaqueProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.utils.TypeAttaqueProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeAttaqueContract;

import com.maximeleau.harmony.android.pokemon.data.TypeAttaqueSQLiteAdapter;

import com.maximeleau.harmony.android.pokemon.entity.TypeAttaque;

import com.maximeleau.harmony.android.pokemon.fixture.TypeAttaqueDataLoader;

import java.util.ArrayList;
import com.maximeleau.harmony.android.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** TypeAttaque database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit TypeAttaqueTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class TypeAttaqueTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected TypeAttaqueSQLiteAdapter adapter;

    protected TypeAttaque entity;
    protected ContentResolver provider;
    protected TypeAttaqueProviderUtils providerUtils;

    protected ArrayList<TypeAttaque> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new TypeAttaqueSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<TypeAttaque>();
        this.entities.addAll(TypeAttaqueDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += TypeAttaqueDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new TypeAttaqueProviderUtils(this.getContext());
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
            TypeAttaque typeAttaque = TypeAttaqueUtils.generateRandom(this.ctx);

            try {
                ContentValues values = TypeAttaqueContract.itemToContentValues(typeAttaque);
                values.remove(TypeAttaqueContract.COL_ID);
                result = this.provider.insert(TypeAttaqueProviderAdapter.TYPEATTAQUE_URI, values);

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
        TypeAttaque result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        TypeAttaqueProviderAdapter.TYPEATTAQUE_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = TypeAttaqueContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            TypeAttaqueUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<TypeAttaque> result = null;
        try {
            android.database.Cursor c = this.provider.query(TypeAttaqueProviderAdapter.TYPEATTAQUE_URI, this.adapter.getCols(), null, null, null);
            result = TypeAttaqueContract.cursorToItems(c);
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
            TypeAttaque typeAttaque = TypeAttaqueUtils.generateRandom(this.ctx);

            try {
                typeAttaque.setId(this.entity.getId());

                ContentValues values = TypeAttaqueContract.itemToContentValues(typeAttaque);
                result = this.provider.update(
                    Uri.parse(TypeAttaqueProviderAdapter.TYPEATTAQUE_URI
                        + "/"
                        + typeAttaque.getId()),
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
                TypeAttaque typeAttaque = TypeAttaqueUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = TypeAttaqueContract.itemToContentValues(typeAttaque);
                    values.remove(TypeAttaqueContract.COL_ID);
    
                    result = this.provider.update(TypeAttaqueProviderAdapter.TYPEATTAQUE_URI, values, null, null);
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
                        Uri.parse(TypeAttaqueProviderAdapter.TYPEATTAQUE_URI
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
                    result = this.provider.delete(TypeAttaqueProviderAdapter.TYPEATTAQUE_URI, null, null);
    
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
        TypeAttaque result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            TypeAttaqueUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<TypeAttaque> result = null;
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
            TypeAttaque typeAttaque = TypeAttaqueUtils.generateRandom(this.ctx);

            typeAttaque.setId(this.entity.getId());
            result = this.providerUtils.update(typeAttaque);

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
