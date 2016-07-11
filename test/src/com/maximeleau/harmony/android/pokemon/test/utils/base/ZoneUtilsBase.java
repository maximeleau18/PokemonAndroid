/**************************************************************************
 * ZoneUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 10, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.test.utils.base;


import junit.framework.Assert;
import com.maximeleau.harmony.android.pokemon.entity.Zone;



import com.maximeleau.harmony.android.pokemon.test.utils.TestUtils;
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.fixture.AreneDataLoader;

import com.maximeleau.harmony.android.pokemon.entity.Badge;
import com.maximeleau.harmony.android.pokemon.fixture.BadgeDataLoader;

import com.maximeleau.harmony.android.pokemon.entity.Position;
import com.maximeleau.harmony.android.pokemon.fixture.PositionDataLoader;


import java.util.ArrayList;

public abstract class ZoneUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Zone generateRandom(android.content.Context ctx){
        Zone zone = new Zone();

        zone.setId(TestUtils.generateRandomInt(0,100) + 1);
        zone.setNom("nom_"+TestUtils.generateRandomString(10));
        ArrayList<Arene> areness =
            new ArrayList<Arene>();
        areness.addAll(AreneDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Arene> relatedAreness = new ArrayList<Arene>();
        if (!areness.isEmpty()) {
            relatedAreness.add(areness.get(TestUtils.generateRandomInt(0, areness.size())));
            zone.setArenes(relatedAreness);
        }
        ArrayList<Badge> badgess =
            new ArrayList<Badge>();
        badgess.addAll(BadgeDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Badge> relatedBadgess = new ArrayList<Badge>();
        if (!badgess.isEmpty()) {
            relatedBadgess.add(badgess.get(TestUtils.generateRandomInt(0, badgess.size())));
            zone.setBadges(relatedBadgess);
        }
        ArrayList<Position> positionss =
            new ArrayList<Position>();
        positionss.addAll(PositionDataLoader.getInstance(ctx).getMap().values());
        ArrayList<Position> relatedPositionss = new ArrayList<Position>();
        if (!positionss.isEmpty()) {
            relatedPositionss.add(positionss.get(TestUtils.generateRandomInt(0, positionss.size())));
            zone.setPositions(relatedPositionss);
        }

        return zone;
    }

    public static boolean equals(Zone zone1,
            Zone zone2){
        return equals(zone1, zone2, true);
    }
    
    public static boolean equals(Zone zone1,
            Zone zone2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(zone1);
        Assert.assertNotNull(zone2);
        if (zone1!=null && zone2 !=null){
            Assert.assertEquals(zone1.getId(), zone2.getId());
            Assert.assertEquals(zone1.getNom(), zone2.getNom());
            if (zone1.getArenes() != null
                    && zone2.getArenes() != null) {
                Assert.assertEquals(zone1.getArenes().size(),
                    zone2.getArenes().size());
                if (checkRecursiveId) {
                    for (Arene arenes1 : zone1.getArenes()) {
                        boolean found = false;
                        for (Arene arenes2 : zone2.getArenes()) {
                            if (arenes1.getId() == arenes2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated arenes (id = %s) in Zone (id = %s)",
                                        arenes1.getId(),
                                        zone1.getId()),
                                found);
                    }
                }
            }
            if (zone1.getBadges() != null
                    && zone2.getBadges() != null) {
                Assert.assertEquals(zone1.getBadges().size(),
                    zone2.getBadges().size());
                if (checkRecursiveId) {
                    for (Badge badges1 : zone1.getBadges()) {
                        boolean found = false;
                        for (Badge badges2 : zone2.getBadges()) {
                            if (badges1.getId() == badges2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated badges (id = %s) in Zone (id = %s)",
                                        badges1.getId(),
                                        zone1.getId()),
                                found);
                    }
                }
            }
            if (zone1.getPositions() != null
                    && zone2.getPositions() != null) {
                Assert.assertEquals(zone1.getPositions().size(),
                    zone2.getPositions().size());
                if (checkRecursiveId) {
                    for (Position positions1 : zone1.getPositions()) {
                        boolean found = false;
                        for (Position positions2 : zone2.getPositions()) {
                            if (positions1.getId() == positions2.getId()) {
                                found = true;
                            }
                        }
                        Assert.assertTrue(
                                String.format(
                                        "Couldn't find associated positions (id = %s) in Zone (id = %s)",
                                        positions1.getId(),
                                        zone1.getId()),
                                found);
                    }
                }
            }
        }

        return ret;
    }
}

