import org.junit.*;
import static org.junit.Assert.*;

public class AnimalTest {

    @Rule
    public DatabaseRule database=new DatabaseRule();

    @Test
    public void animals_instanciatesCorrectly_true(){
        Animal testAnimal=new Animal("Tiger");
        assertEquals(true,testAnimal instanceof Animal);
    }
    @Test
    public void animals_instanciatesWithName_true(){
        Animal testAnimal=new Animal("Tiger");
        assertEquals("Tiger",testAnimal.getName());
    }
    @Test
    public void animals_returnIfnameIsSame_true(){
        Animal testAnimal=new Animal("Tiger");
        Animal testAnimal1=new Animal("Tiger");
        assertTrue(testAnimal.equals(testAnimal1));
    }


}
