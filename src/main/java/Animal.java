import org.sql2o.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public  class Animal {
    public int id;
    public String name;
    public String type;

    public Animal(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    public static List<EndangeredAnimal> all() {
        String sql = "SELECT * FROM animals ";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(EndangeredAnimal.class);
        }
    }

    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO animals (name, type) VALUES (:name, :type)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("type", this.type)
                    .executeUpdate()
                    .getKey();
        }
    }
    public List<Object> getAnimals() {
        List<Object> allAnimals = new ArrayList<Object>();

        try(Connection con = DB.sql2o.open()) {
            String sqlSighting = "SELECT * FROM animals WHERE animalid=:id";
            List<Sighting> animals = con.createQuery(sqlSighting)
                    .addParameter("id", this.id)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Sighting.class);
            allAnimals.addAll(animals);

            String sqlEndangeredAnimal = "SELECT * FROM animals WHERE animalid=:id AND type='endangered';";
            List<Sighting> endangeredAnimals = con.createQuery(sqlEndangeredAnimal)
                    .addParameter("id", this.id)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Sighting.class);
            allAnimals.addAll(endangeredAnimals);
        }

        return allAnimals;
    }
    public static void update(int id, String name) {
        try (Connection con = DB.sql2o.open()) {
            String sql = "UPDATE animals SET name = :name WHERE id = :id";
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeUpdate();
        }
    }
    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM animals WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id", this.id)
                    .executeUpdate();
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(name, animal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}