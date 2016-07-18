/**************************************************************************
 * TypeDePokemonTestProviderBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.provider.TypeDePokemonProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.utils.TypeDePokemonProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonContract;

import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonSQLiteAdapter;

import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;

import com.maximeleau.harmony.android.pokemon.fixture.TypeDePokemonDataLoader;

import java.util.ArrayList;
import com.maximeleau.harmony.android.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** TypeDePokemon database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit TypeDePokemonTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class TypeDePokemonTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected TypeDePokemonSQLiteAdapter adapter;

    protected TypeDePokemon entity;
    protected ContentResolver provider;
    protected TypeDePokemonProviderUtils providerUtils;

    protected ArrayList<TypeDePokemon> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new TypeDePokemonSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<TypeDePokemon>();
        this.entities.addAll(TypeDePokemonDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += TypeDePokemonDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new TypeDePokemonProviderUtils(this.getContext());
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
            TypeDePokemon typeDePokemon = TypeDePokemonUtils.generateRandom(this.ctx);

            try {
                ContentValues values = TypeDePokemonContract.itemToContentValues(typeDePokemon);
                values.remove(TypeDePokemonContract.COL_ID);
                result = this.provider.insert(TypeDePokemonProviderAdapter.TYPEDEPOKEMON_URI, values);

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
        TypeDePokemon result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        TypeDePokemonProviderAdapter.TYPEDEPOKEMON_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = TypeDePokemonContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            TypeDePokemonUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<TypeDePokemon> result = null;
        try {
            android.database.Cursor c = this.provider.query(TypeDePokemonProviderAdapter.TYPEDEPOKEMON_URI, this.adapter.getCols(), null, null, null);
            result = TypeDePokemonContract.cursorToItems(c);
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
            TypeDePokemon typeDePokemon = TypeDePokemonUtils.generateRandom(this.ctx);

            try {
                typeDePokemon.setId(this.entity.getId());
                if (this.entity.getPokemons() != null) {
                    typeDePokemon.getPokemons().addAll(this.entity.getPokemons());
                }

                ContentValues values = TypeDePokemonContract.itemToContentValues(typeDePokemon);
                result = this.provider.update(
                    Uri.parse(TypeDePokemonProviderAdapter.TYPEDEPOKEMON_URI
                        + "/"
                        + typeDePokemon.getId()),
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
                TypeDePokemon typeDePokemon = TypeDePokemonUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = TypeDePokemonContract.itemToContentValues(typeDePokemon);
                    values.remove(TypeDePokemonContract.COL_ID);
    
                    result = this.provider.update(TypeDePokemonProviderAdapter.TYPEDEPOKEMON_URI, values, null, null);
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
                        Uri.parse(TypeDePokemonProviderAdapter.TYPEDEPOKEMON_URI
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
                    result = this.provider.delete(TypeDePokemonProviderAdapter.TYPEDEPOKEMON_URI, null, null);
    
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
        TypeDePokemon result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            TypeDePokemonUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<TypeDePokemon> result = null;
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
            TypeDePokemon typeDePokemon = TypeDePokemonUtils.generateRandom(this.ctx);

            typeDePokemon.setId(this.entity.getId());
            if (this.entity.getPokemons() != null) {
                for (Pokemon pokemons : this.entity.getPokemons()) {
                    boolean found = false;
                    for (Pokemon pokemons2 : typeDePokemon.getPokemons()) {
                        if (pokemons.getId() == pokemons2.getId() ) {
                            found = true;
                            break;
                        }
                    }                    
                    if(!found) {
                        typeDePokemon.getPokemons().add(pokemons);
                    }
                }
            }
            result = this.providerUtils.update(typeDePokemon);

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
