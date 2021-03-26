import org.sql2o.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EndangeredAnimal extends Animal{

    private String healthy;
    private String age;
    public static final String DATABASE_TYPE = "endangered";

    public EndangeredAnimal(String name,String healthy,String age){
        super(name);
        this.name=name;
        this.healthy=healthy;
        this.age=age;
        type=DATABASE_TYPE;
    }

    public String getName() {
        return name;
    }

    public String getHealth() {
        return healthy;
    }

    public String getAge() {
        return age;
    }

    public static String getDatabaseType() {
        return DATABASE_TYPE;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(String healthy) {
        this.healthy = healthy;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public static List<EndangeredAnimal> all() {
        String sql = "SELECT * FROM animals ;";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(EndangeredAnimal.class);
        }
    }
    public static EndangeredAnimal find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals where id=:id";
            EndangeredAnimal animal = con.createQuery(sql)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(EndangeredAnimal.class);
            return animal;
        }
    }
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO animals (name, healthy, age) VALUES (:name, :healthy, :age)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("healthy", this.healthy)
                    .addParameter("age", this.age)
                    //.addParameter("type", this.type)
                    .executeUpdate()
                    .getKey();
        }
    }
    public List<Object> getAnimals() {
        List<Object> allAnimals = new ArrayList<Object>();

        try(Connection con = DB.sql2o.open()) {
            String sqlSighting = "SELECT * FROM animals WHERE id=:id";
            List<Sighting> animals = con.createQuery(sqlSighting)
                    .addParameter("id", this.id)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Sighting.class);
            allAnimals.addAll(animals);

            String sqlEndangeredAnimal = "SELECT * FROM animals WHERE id=:id AND type='endangered';";
            List<Sighting> endangeredAnimals = con.createQuery(sqlEndangeredAnimal)
                    .addParameter("id", this.id)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Sighting.class);
            allAnimals.addAll(endangeredAnimals);
        }

        return allAnimals;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EndangeredAnimal that = (EndangeredAnimal) o;
        return Objects.equals(healthy, that.healthy) &&
                Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), healthy, age);
    }


}