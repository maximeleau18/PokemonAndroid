/**************************************************************************
 * PersonnageNonJoueurBadgeTestWSBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurBadgeWebServiceClientAdapter;
import com.maximeleau.harmony.android.pokemon.data.RestClient.RequestConstants;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueurBadge;
import com.maximeleau.harmony.android.pokemon.fixture.PersonnageNonJoueurBadgeDataLoader;
import com.maximeleau.harmony.android.pokemon.test.utils.PersonnageNonJoueurBadgeUtils;
import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;

import com.google.mockwebserver.MockResponse;

import junit.framework.Assert;

/** PersonnageNonJoueurBadge Web Service Test.
 * 
 * @see android.app.Fragment
 */
public abstract class PersonnageNonJoueurBadgeTestWSBase extends TestWSBase {
    /** model {@link PersonnageNonJoueurBadge}. */
    protected PersonnageNonJoueurBadge model;
    /** web {@link PersonnageNonJoueurBadgeWebServiceClientAdapter}. */
    protected PersonnageNonJoueurBadgeWebServiceClientAdapter web;
    /** entities ArrayList<PersonnageNonJoueurBadge>. */
    protected ArrayList<PersonnageNonJoueurBadge> entities;
    /** nbEntities Number of entities. */
    protected int nbEntities = 0;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        String host = this.server.getHostName();
        int port = this.server.getPort();

        this.web = new PersonnageNonJoueurBadgeWebServiceClientAdapter(
                this.ctx, host, port, RequestConstants.HTTP);
        
        this.entities = new ArrayList<PersonnageNonJoueurBadge>();        
        this.entities.addAll(PersonnageNonJoueurBadgeDataLoader.getInstance(this.ctx).getMap().values());
        
        if (entities.size() > 0) {
            this.model = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += PersonnageNonJoueurBadgeDataLoader.getInstance(this.ctx).getMap().size();
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
        this.server.enqueue(new MockResponse().setBody("{PersonnageNonJoueurBadges :"
            + this.web.itemsToJson(this.entities).toString() + "}"));

        ArrayList<PersonnageNonJoueurBadge> personnageNonJoueurBadgeList = 
                new ArrayList<PersonnageNonJoueurBadge>();

        int result = this.web.getAll(personnageNonJoueurBadgeList);

        Assert.assertEquals(personnageNonJoueurBadgeList.size(), this.entities.size());
    }
    
    /** Test case Update Entity. */
    public void testUpdate() {
        this.server.enqueue(new MockResponse().setBody("{'result'='1'}"));

        int result = this.web.update(this.model);

        Assert.assertTrue(result >= 0);
        
        this.server.enqueue(new MockResponse().setBody(
                this.web.itemToJson(this.model).toString()));

        PersonnageNonJoueurBadge item = new PersonnageNonJoueurBadge();
        item.setId(this.model.getId());

        result = this.web.get(item);
        
        PersonnageNonJoueurBadgeUtils.equals(this.model, item);
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
