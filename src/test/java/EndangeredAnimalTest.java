import db.DatabaseRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class EndangeredAnimalTest {
    @Rule
    public DatabaseRule databaseRule = new DatabaseRule();

    private EndangeredAnimal newAnimal() {
        return new EndangeredAnimal("Elephant","Good", "Young");
    }
    private EndangeredAnimal newAnimal2() {
        return new EndangeredAnimal("Dog","Healthy", "Adult");
    }

    @Test
    public void endangeredanimal_instantiatesCorrectly_true(){
        EndangeredAnimal animal = newAnimal();
        assertTrue(animal instanceof EndangeredAnimal);
    }

    @Test
    public void getName_returnAnimalsName(){
        EndangeredAnimal animal = newAnimal();
        assertEquals("Elephant", animal.getName());
    }

    @Test
    public void getHealth_returnAnimalsHealth(){
        EndangeredAnimal animal = newAnimal();
        assertEquals("Good", animal.getHealth());
    }

    @Test
    public void getAge_returnAnimalsAge(){
        EndangeredAnimal animal = newAnimal();
        assertEquals("Young", animal.getAge());
    }

    @Test
    public void getStatus_returnAnimalsStatus(){
        EndangeredAnimal animal = newAnimal();
        assertEquals("Not Endangered", Animal.getDatabaseType());
    }

    @Test
    public void saveAnimal_savedToDb_int(){
        EndangeredAnimal animal = newAnimal();
        EndangeredAnimal animal1 = newAnimal2();
        animal.saveEndangered();
        animal1.saveEndangered();
        int one = animal.getId();
        int two = EndangeredAnimal.getEndangeredAnimals().get(0).getId();
        assertEquals(one, two);
    }

    @Test
    public void find_locateEndangeredAnimal_Name(){
        EndangeredAnimal animal = newAnimal();
        animal.saveEndangered();
        int id = animal.getId();
        EndangeredAnimal animal1 = EndangeredAnimal.find(id);
        assertEquals(animal,animal1);
    }


}