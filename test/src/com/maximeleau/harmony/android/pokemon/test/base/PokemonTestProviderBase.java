/**************************************************************************
 * PokemonTestProviderBase.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.provider.PokemonProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.utils.PokemonProviderUtils;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;

import com.maximeleau.harmony.android.pokemon.data.PokemonSQLiteAdapter;

import com.maximeleau.harmony.android.pokemon.entity.Pokemon;

import com.maximeleau.harmony.android.pokemon.fixture.PokemonDataLoader;

import java.util.ArrayList;
import com.maximeleau.harmony.android.pokemon.test.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;


import android.net.Uri;

import junit.framework.Assert;

/** Pokemon database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokemonTestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class PokemonTestProviderBase extends TestDBBase {
    protected android.content.Context ctx;

    protected PokemonSQLiteAdapter adapter;

    protected Pokemon entity;
    protected ContentResolver provider;
    protected PokemonProviderUtils providerUtils;

    protected ArrayList<Pokemon> entities;

    protected int nbEntities = 0;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.ctx = this.getContext();

        this.adapter = new PokemonSQLiteAdapter(this.ctx);

        this.entities = new ArrayList<Pokemon>();
        this.entities.addAll(PokemonDataLoader.getInstance(this.ctx).getMap().values());
        if (this.entities.size()>0) {
            this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
        }

        this.nbEntities += PokemonDataLoader.getInstance(this.ctx).getMap().size();
        this.provider = this.getContext().getContentResolver();
        this.providerUtils = new PokemonProviderUtils(this.getContext());
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
            Pokemon pokemon = PokemonUtils.generateRandom(this.ctx);

            try {
                ContentValues values = PokemonContract.itemToContentValues(pokemon, 0, 0);
                values.remove(PokemonContract.COL_ID);
                result = this.provider.insert(PokemonProviderAdapter.POKEMON_URI, values);

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
        Pokemon result = null;

        if (this.entity != null) {
            try {
                android.database.Cursor c = this.provider.query(Uri.parse(
                        PokemonProviderAdapter.POKEMON_URI
                                + "/" 
                                + this.entity.getId()),
                        this.adapter.getCols(),
                        null,
                        null,
                        null);
                c.moveToFirst();
                result = PokemonContract.cursorToItem(c);
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            PokemonUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity */
    @SmallTest
    public void testReadAll() {
        ArrayList<Pokemon> result = null;
        try {
            android.database.Cursor c = this.provider.query(PokemonProviderAdapter.POKEMON_URI, this.adapter.getCols(), null, null, null);
            result = PokemonContract.cursorToItems(c);
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
            Pokemon pokemon = PokemonUtils.generateRandom(this.ctx);

            try {
                pokemon.setId(this.entity.getId());

                ContentValues values = PokemonContract.itemToContentValues(pokemon, 0, 0);
                result = this.provider.update(
                    Uri.parse(PokemonProviderAdapter.POKEMON_URI
                        + "/"
                        + pokemon.getId()),
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
                Pokemon pokemon = PokemonUtils.generateRandom(this.ctx);
    
                try {
                    ContentValues values = PokemonContract.itemToContentValues(pokemon, 0, 0);
                    values.remove(PokemonContract.COL_ID);
    
                    result = this.provider.update(PokemonProviderAdapter.POKEMON_URI, values, null, null);
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
                        Uri.parse(PokemonProviderAdapter.POKEMON_URI
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
                    result = this.provider.delete(PokemonProviderAdapter.POKEMON_URI, null, null);
    
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
        Pokemon result = null;

        if (this.entity != null) {
            result = this.providerUtils.query(this.entity);

            PokemonUtils.equals(this.entity, result);
        }
    }

    /** Test case ReadAll Entity by provider utils. */
    @SmallTest
    public void testUtilsReadAll() {
        ArrayList<Pokemon> result = null;
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
            Pokemon pokemon = PokemonUtils.generateRandom(this.ctx);

            pokemon.setId(this.entity.getId());
            result = this.providerUtils.update(pokemon);

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
