/**************************************************************************
 * AreneTestWSBase.java, pokemon Android
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

import android.database.Cursor;

import com.maximeleau.harmony.android.pokemon.data.AreneWebServiceClientAdapter;
import com.maximeleau.harmony.android.pokemon.data.RestClient.RequestConstants;
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.fixture.AreneDataLoader;
import com.maximeleau.harmony.android.pokemon.test.utils.AreneUtils;
import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;

import com.google.mockwebserver.MockResponse;

import junit.framework.Assert;

/** Arene Web Service Test.
 * 
 * @see android.app.Fragment
 */
public abstract class AreneTestWSBase extends TestWSBase {
    /** model {@link Arene}. */
    protected Arene model;
    /** web {@link AreneWebServiceClientAdapter}. */
    protected AreneWebServiceClientAdapter web;
    /** entities ArrayList<Arene>. */
    protected ArrayList<Arene> entities;
    /** nbEntities Number of entities. */
    protected int nbEntities = 0;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        String host = this.server.getHostName();
        int port = this.server.getPort();

        this.web = new AreneWebServiceClientAdapter(
                this.ctx, host, port, RequestConstants.HTTP);
        
        this.entities = new ArrayList<Arene>();        
        this.entities.addAll(AreneDataLoader.getInstance(this.ctx).getMap().values());
        
        if (entities.size() > 0) {
            this.model = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += AreneDataLoader.getInstance(this.ctx).getMap().size();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    /** Test case Create Entity */
    public void testInsert() {
        this.server.enqueue(new MockResponse().setBody("{'result'='0'}"));

        int result = this.web.insert(this.model);

        Assert.assertTrue(result >= 0);
    }
    
    /** Test case Get Entity. */
    public void testGet() {
        this.server.enqueue(new MockResponse().setBody(
                this.web.itemToJson(this.model).toString()));

        int result = this.web.get(this.model);

        Assert.assertTrue(result >= 0);
    }

    /** Test case Read Entity. */
    public void testQuery() {
        this.server.enqueue(new MockResponse().setBody(
                this.web.itemToJson(this.model).toString()));

        Cursor result = this.web.query(this.model.getId());
        
        Assert.assertTrue(result.getCount() >= 0);
    }

    /** Test case get all Entity. */
    public void testGetAll() {
        this.server.enqueue(new MockResponse().setBody("{Arenes :"
            + this.web.itemsToJson(this.entities).toString() + "}"));

        ArrayList<Arene> areneList = 
                new ArrayList<Arene>();

        int result = this.web.getAll(areneList);

        Assert.assertEquals(areneList.size(), this.entities.size());
    }
    
    /** Test case Update Entity. */
    public void testUpdate() {
        this.server.enqueue(new MockResponse().setBody("{'result'='1'}"));

        int result = this.web.update(this.model);

        Assert.assertTrue(result >= 0);
        
        this.server.enqueue(new MockResponse().setBody(
                this.web.itemToJson(this.model).toString()));

        Arene item = new Arene();
        item.setId(this.model.getId());

        result = this.web.get(item);
        
        AreneUtils.equals(this.model, item);
    }
    
    /** Test case Delete Entity. */
    public void testDelete() {
        this.server.enqueue(new MockResponse().setBody("{'result'='1'}"));

        int result = this.web.delete(this.model);

        Assert.assertTrue(result == 0);

        this.server.enqueue(new MockResponse().setBody("{}"));

        result = this.web.get(this.model);

        Assert.assertTrue(result < 0);
    }
}
