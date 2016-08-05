/**************************************************************************
 * BadgeTestWSBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.data.BadgeWebServiceClientAdapter;
import com.maximeleau.harmony.android.pokemon.data.RestClient.RequestConstants;
import com.maximeleau.harmony.android.pokemon.entity.Badge;
import com.maximeleau.harmony.android.pokemon.fixture.BadgeDataLoader;
import com.maximeleau.harmony.android.pokemon.test.utils.BadgeUtils;
import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;

import com.google.mockwebserver.MockResponse;

import junit.framework.Assert;

/** Badge Web Service Test.
 * 
 * @see android.app.Fragment
 */
public abstract class BadgeTestWSBase extends TestWSBase {
    /** model {@link Badge}. */
    protected Badge model;
    /** web {@link BadgeWebServiceClientAdapter}. */
    protected BadgeWebServiceClientAdapter web;
    /** entities ArrayList<Badge>. */
    protected ArrayList<Badge> entities;
    /** nbEntities Number of entities. */
    protected int nbEntities = 0;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        String host = this.server.getHostName();
        int port = this.server.getPort();

        this.web = new BadgeWebServiceClientAdapter(
                this.ctx, host, port, RequestConstants.HTTP);
        
        this.entities = new ArrayList<Badge>();        
        this.entities.addAll(BadgeDataLoader.getInstance(this.ctx).getMap().values());
        
        if (entities.size() > 0) {
            this.model = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += BadgeDataLoader.getInstance(this.ctx).getMap().size();
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
        this.server.enqueue(new MockResponse().setBody("{Badges :"
            + this.web.itemsToJson(this.entities).toString() + "}"));

        ArrayList<Badge> badgeList = 
                new ArrayList<Badge>();

        int result = this.web.getAll(badgeList);

        Assert.assertEquals(badgeList.size(), this.entities.size());
    }
    
    /** Test case Update Entity. */
    public void testUpdate() {
        this.server.enqueue(new MockResponse().setBody("{'result'='1'}"));

        int result = this.web.update(this.model);

        Assert.assertTrue(result >= 0);
        
        this.server.enqueue(new MockResponse().setBody(
                this.web.itemToJson(this.model).toString()));

        Badge item = new Badge();
        item.setId(this.model.getId());

        result = this.web.get(item);
        
        BadgeUtils.equals(this.model, item);
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
