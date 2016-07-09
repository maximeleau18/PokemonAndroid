/**************************************************************************
 * PositionUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.test.utils.base;


import junit.framework.Assert;
import com.maximeleau.harmony.android.pokemon.entity.Position;



import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;

import com.maximeleau.harmony.android.pokemon.test.utils.ZoneUtils;


public abstract class PositionUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Position generateRandom(android.content.Context ctx){
        Position position = new Position();

        position.setId(TestUtils.generateRandomInt(0,100) + 1);
        position.setX(TestUtils.generateRandomInt(0,100));
        position.setY(TestUtils.generateRandomInt(0,100));
        position.setZone(ZoneUtils.generateRandom(ctx));

        return position;
    }

    public static boolean equals(Position position1,
            Position position2){
        return equals(position1, position2, true);
    }
    
    public static boolean equals(Position position1,
            Position position2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(position1);
        Assert.assertNotNull(position2);
        if (position1!=null && position2 !=null){
            Assert.assertEquals(position1.getId(), position2.getId());
            Assert.assertEquals(position1.getX(), position2.getX());
            Assert.assertEquals(position1.getY(), position2.getY());
            if (position1.getZone() != null
                    && position2.getZone() != null) {
                if (checkRecursiveId) {
                    Assert.assertEquals(position1.getZone().getId(),
                            position2.getZone().getId());
                }
            }
        }

        return ret;
    }
}

