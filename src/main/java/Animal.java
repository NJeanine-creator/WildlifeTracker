import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Animal extends WildAnimals {

    public static final String DATABASE_TYPE = "Not Endangered";

    public  Animal(String name, String health, String age){
        this.name = name;
        this.dateAdded = new Timestamp(new Date().getTime());
        type = DATABASE_TYPE;
        this.health = health;
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public static String getDatabaseType() {
        return DATABASE_TYPE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;
        Animal animal = (Animal) o;
        return getAge().equals(animal.getAge()) &&
                getHealth().equals(animal.getHealth());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAge(), getHealth());
    }

    public void saveAnimal() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO animals (name, health, age, dateadded, type) VALUES (:name, :health, :age, :dateAdded, :type)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", getName())
                    .addParameter("health", getHealth())
                    .addParameter("age", getAge())
                    .addParameter("dateAdded", getDateAdded())
                    .addParameter("type", getDatabaseType())
                    .executeUpdate()
                    .getKey();
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    public static List<Animal> getAnimals() {
        String sql = "SELECT * FROM animals WHERE type =:type;";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("type",getDatabaseType())
                    .executeAndFetch(Animal.class);
        }
    }

    public int getId() {

        return id;
    }
    public static Animal find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals where id =:id;";
            Animal animal = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Animal.class);
            return animal;
        }
    }
}
