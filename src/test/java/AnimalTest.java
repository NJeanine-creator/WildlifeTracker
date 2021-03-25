
import db.DatabaseRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnimalTest {
    @Rule
    public DatabaseRule databaseRule = new DatabaseRule();

    private Animal newAnimal() {
        return new Animal("Lion","Good", "Young");
    }
    private Animal newAnimal2() {
        return new Animal("Wolf","Healthy", "Adult");
    }

    @Test
    public void animal_instantiatesCorrectly_true(){
        Animal animal = newAnimal();
        assertTrue(animal instanceof Animal);
    }

    @Test
    public void getName_returnAnimalsName(){
        Animal animal = newAnimal();
        assertEquals("Lion", animal.getName());
    }

    @Test
    public void getHealth_returnAnimalsHealth(){
        Animal animal = newAnimal();
        assertEquals("Good", animal.getHealth());
    }

    @Test
    public void getAge_returnAnimalsAge(){
        Animal animal = newAnimal();
        assertEquals("Young", animal.getAge());
    }

    @Test
    public void getStatus_returnAnimalsStatus(){
        Animal animal = newAnimal();
        assertEquals("Not Endangered", Animal.getDatabaseType());
    }

    @Test
    public void saveAnimal_savedToDb_int(){
        Animal animal = newAnimal();
        Animal animal1 = newAnimal2();
        animal.saveAnimal();
        animal1.saveAnimal();
        int one = animal.getId();
        int two = Animal.getAnimals().get(0).getId();
        assertEquals(one, two);
    }

    @Test
    public void find_locateNormalAnimal_Name(){
        Animal animal = newAnimal();
        animal.saveAnimal();
        Animal animal1 = Animal.find(animal.getId());
        assertEquals(animal,animal1);
    }

}