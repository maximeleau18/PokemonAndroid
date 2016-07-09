/**************************************************************************
 * TypeDePokemonEvolutionTestDBBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.test.base;

import java.util.ArrayList;

import android.test.suitebuilder.annotation.SmallTest;

import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonEvolutionSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonEvolution;


import com.maximeleau.harmony.android.pokemon.test.utils.*;

import junit.framework.Assert;

/** TypeDePokemonEvolution database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit TypeDePokemonEvolutionTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class TypeDePokemonEvolutionTestDBBase extends TestDBBase {
    protected android.content.Context ctx;

    protected TypeDePokemonEvolutionSQLiteAdapter adapter;

    protected TypeDePokemonEvolution entity;
    protected ArrayList<TypeDePokemonEvolution> entities;
    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new TypeDePokemonEvolutionSQLiteAdapter(this.ctx);
        this.adapter.open();

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
            TypeDePokemonEvolution typeDePokemonEvolution = TypeDePokemonEvolutionUtils.generateRandom(this.ctx);

            result = (int)this.adapter.insert(typeDePokemonEvolution);

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Read Entity */
    @SmallTest
    public void testRead() {
        TypeDePokemonEvolution result = null;
        if (this.entity != null) {
            result = this.adapter.getByID(this.entity.getEvolueEn(),
                    this.entity.getEstEvolueEn());

            TypeDePokemonEvolutionUtils.equals(this.entity, result);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testUpdate() {
        int result = -1;
        if (this.entity != null) {
            TypeDePokemonEvolution typeDePokemonEvolution = TypeDePokemonEvolutionUtils.generateRandom(this.ctx);
            typeDePokemonEvolution.setEvolueEn(this.entity.getEvolueEn());
            typeDePokemonEvolution.setEstEvolueEn(this.entity.getEstEvolueEn());

            result = (int) this.adapter.update(typeDePokemonEvolution);

            Assert.assertTrue(result >= 0);
        }
    }

    /** Test case Update Entity */
    @SmallTest
    public void testDelete() {
        int result = -1;
        if (this.entity != null) {
            result = (int) this.adapter.remove(this.entity.getEvolueEn(),
                    this.entity.getEstEvolueEn());
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