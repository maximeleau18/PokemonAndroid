/**************************************************************************
 * AreneTestDBBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Aug 5, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.test.base;

import java.util.ArrayList;

import android.test.suitebuilder.annotation.SmallTest;

import com.maximeleau.harmony.android.pokemon.data.AreneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.entity.Arene;

import com.maximeleau.harmony.android.pokemon.fixture.AreneDataLoader;

import com.maximeleau.harmony.android.pokemon.test.utils.*;

import junit.framework.Assert;

/** Arene database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit AreneTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class AreneTestDBBase extends TestDBBase {
    protected android.content.Context ctx;

    protected AreneSQLiteAdapter adapter;

    protected Arene entity;
    protected ArrayList<Arene> entities;
    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new AreneSQLiteAdapter(this.ctx);
        this.adapter.open();

        this.entities = new ArrayList<Arene>();        
        this.entities.addAll(AreneDataLoader.getInstance(this.ctx).getMap().values());
        if (entities.size()>0){
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += AreneDataLoader.getInstance(this.ctx).getMap().size();
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        this.adapter.close();

        super.tearDown();
    }

    /** Test case Create Entity */
    @SmallTest
    public void testCreate() {
        int result = -1;
        if (this.entity != null) {
            Arene arene = AreneUtils.generateRandom(this.ctx);

            result = (int)this.adapter.insert(arene);

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        Arene result = null;
        if (this.entity != null) {
            result = this.adapter.getByID(this.entity.getId());

            AreneUtils.equals(this.entity, result);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            Arene arene = AreneUtils.generateRandom(this.ctx);
            arene.setId(this.entity.getId());

            result = (int) this.adapter.update(arene);

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testDelete() {
        int result = -1;
        if (this.entity != null) {
            result = (int) this.adapter.remove(this.entity.getId());
            Assert.assertTrue(result >= 0);
        }
    }
    
    /** Test the get all method. */
    @SmallTest
    public void testAll() {
        int result = this.adapter.getAll().size();
        int expectedSize = this.nbEntities;
        Assert.assertEquals(expectedSize, result);
    }
}
