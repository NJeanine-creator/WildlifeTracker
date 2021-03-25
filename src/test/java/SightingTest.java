package models;

import db.DatabaseRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;

import static org.junit.Assert.*;

public class SightingTest {
    @Rule
    public DatabaseRule databaseRule = new DatabaseRule();

    private Sighting newSightingHelper(){
        
        return new Sighting(1,"Zone A","Atemba");
    }

    private Sighting newSightingHelper2(){return new Sighting(2,"Zone B","Emmanuel");}

    @Test
    public void sighting_instantiatesCorrectly(){
        Sighting sighting = newSightingHelper();
        assertTrue(sighting instanceof Sighting);
    }
    @Test
    public void getLocation_returnSightingLocation_true(){
        Sighting sighting = newSightingHelper();
        assertEquals("Zone A",sighting.getLocation());
    }

    @Test
    public void getTimestamp_returnDateSpotted_true(){
        Sighting sighting = newSightingHelper();
        Timestamp testTimestamp = new Timestamp(new Date().getTime());
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        assertEquals(dateFormat.format(testTimestamp),dateFormat.format(sighting.getDateSpotted()));
    }

    @Test
    public void saveSighting_savesSightingIntoDatabase_true(){
        Sighting sighting = newSightingHelper();
        int id = sighting.getId();
        sighting.saveSighting();
        assertNotEquals(id,sighting.getId());
    }

    @Test
    public void find_findSightingWithId_true(){
        Sighting sighting1 = newSightingHelper();
        Sighting sighting2 = newSightingHelper2();
        sighting1.saveSighting();
        sighting2.saveSighting();
        assertEquals(Sighting.find(sighting2.getId()), sighting2);
    }

    @Test
    public void getSightings_getAllSightings_true(){
        Sighting sighting1 = newSightingHelper();
        Sighting sighting2 = newSightingHelper2();
        sighting1.saveSighting();
        sighting2.saveSighting();
        assertTrue(Sighting.getSightings().contains(sighting1));

    }
}