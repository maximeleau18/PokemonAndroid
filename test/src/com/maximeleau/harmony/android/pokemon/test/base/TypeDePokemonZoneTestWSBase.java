/**************************************************************************
 * TypeDePokemonZoneTestWSBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonZoneWebServiceClientAdapter;
import com.maximeleau.harmony.android.pokemon.data.RestClient.RequestConstants;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonZone;
import com.maximeleau.harmony.android.pokemon.fixture.TypeDePokemonZoneDataLoader;
import com.maximeleau.harmony.android.pokemon.test.utils.TypeDePokemonZoneUtils;
import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;

import com.google.mockwebserver.MockResponse;

import junit.framework.Assert;

/** TypeDePokemonZone Web Service Test.
 * 
 * @see android.app.Fragment
 */
public abstract class TypeDePokemonZoneTestWSBase extends TestWSBase {
    /** model {@link TypeDePokemonZone}. */
    protected TypeDePokemonZone model;
    /** web {@link TypeDePokemonZoneWebServiceClientAdapter}. */
    protected TypeDePokemonZoneWebServiceClientAdapter web;
    /** entities ArrayList<TypeDePokemonZone>. */
    protected ArrayList<TypeDePokemonZone> entities;
    /** nbEntities Number of entities. */
    protected int nbEntities = 0;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        String host = this.server.getHostName();
        int port = this.server.getPort();

        this.web = new TypeDePokemonZoneWebServiceClientAdapter(
                this.ctx, host, port, RequestConstants.HTTP);
        
        this.entities = new ArrayList<TypeDePokemonZone>();        
        this.entities.addAll(TypeDePokemonZoneDataLoader.getInstance(this.ctx).getMap().values());
        
        if (entities.size() > 0) {
            this.model = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += TypeDePokemonZoneDataLoader.getInstance(this.ctx).getMap().size();
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
        this.server.enqueue(new MockResponse().setBody("{TypeDePokemonZones :"
            + this.web.itemsToJson(this.entities).toString() + "}"));

        ArrayList<TypeDePokemonZone> typeDePokemonZoneList = 
                new ArrayList<TypeDePokemonZone>();

        int result = this.web.getAll(typeDePokemonZoneList);

        Assert.assertEquals(typeDePokemonZoneList.size(), this.entities.size());
    }
    
    /** Test case Update Entity. */
    public void testUpdate() {
        this.server.enqueue(new MockResponse().setBody("{'result'='1'}"));

        int result = this.web.update(this.model);

        Assert.assertTrue(result >= 0);
        
        this.server.enqueue(new MockResponse().setBody(
                this.web.itemToJson(this.model).toString()));

        TypeDePokemonZone item = new TypeDePokemonZone();
        item.setId(this.model.getId());

        result = this.web.get(item);
        
        TypeDePokemonZoneUtils.equals(this.model, item);
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
