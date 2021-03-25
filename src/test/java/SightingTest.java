import org.junit.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SightingTest {
    public Sighting setupSighting() {
        return new Sighting("Zone A", "Zuma", 1);
    }

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void Sighting_instanciatesCorrectly_true() {
        Sighting testSighting = setupSighting();
        assertEquals(true, testSighting instanceof Sighting);
    }

    @Test
    public void Sighting_instanciatesWithLocation_true() {
        Sighting testSighting = setupSighting();
        assertEquals("Zone A", testSighting.getLocation());
    }

    @Test
    public void Sighting_instanciatesRangerName_true() {
        Sighting testSighting = setupSighting();
        assertEquals("Zuma", testSighting.getRangername());
    }

    @Test
    public void Sighting_instanciatesRangerAnimalId_true() {
        Sighting testSighting = setupSighting();
        assertEquals(1, testSighting.getAnimalid());
    }

    @Test
    public void Sightings_returnIfnameIsSame_true() {
        Sighting testSighting = setupSighting();
        Sighting testSighting1 = setupSighting();
        assertTrue(testSighting.equals(testSighting1));
    }

    @Test
    public void Sightings_successfullyAddsAnimalToDatabase_List() {
        Sighting testSighting1 = setupSighting();
        testSighting1.save();
        assertTrue(Sighting.all().get(0).equals(testSighting1));
    }

    @Test
    public void save_assignsIdToSighting() {
        Sighting testSighting = setupSighting();
        testSighting.save();
        Sighting savedSighting = Sighting.all().get(0);
        assertEquals(savedSighting.getId(), testSighting.getId());
    }

    @Test
    public void all_returnsAllInstancesOfSighting_true() {
        Sighting firstSighting = setupSighting();
        firstSighting.save();
        Sighting secondSighting = setupSighting();
        secondSighting.save();
        assertEquals(true, Sighting.all().get(0).equals(firstSighting));
        assertEquals(true, Sighting.all().get(1).equals(secondSighting));
    }

    @Test
    public void find_returnsSightingWithSameId_secondSighting() {
        Sighting firstSighting = setupSighting();
        firstSighting.save();
        Sighting secondSighting = setupSighting();
        secondSighting.save();
        assertEquals(Sighting.find(secondSighting.getId()), secondSighting);
    }

    @Test
    public void delete_deletesFireMonster_true() {
        Sighting testSighting = setupSighting();
        testSighting.save();
        testSighting.delete();
        assertEquals(null, Sighting.find(testSighting.getId()));
    }
}