import org.sql2o.Connection;

import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sighting {
    private String location;
    private String ranger;
    private int id;
    public int animalid;
    public Date date= new Date();
    public Timestamp time;

    public Sighting(String location,String ranger, int animalid){
        this.location=location;
        this.ranger=ranger;
        this.animalid=animalid;
        this.time = new Timestamp(date.getTime());
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRangername() {
        return ranger;
    }

    public void setRangername(String rangername) {
        this.ranger = rangername;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnimalid() {
        return animalid;
    }

    public void setAnimalid(int animalid) {
        this.animalid = animalid;
    }

    public int getId() {
        return id;
    }

    public Timestamp getTime() {
        return time;
    }


    public static List<Sighting> all() {
        String sql = "SELECT * FROM sightings";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Sighting.class);
        }
    }
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO sightings (location, ranger, animalid) VALUES (:location, :ranger, :animalid)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("location", this.location)
                    .addParameter("ranger", this.ranger)
                    .addParameter("animalid", this.animalid)
                    .executeUpdate()
                    .getKey();
        }
    }
    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM sightings WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id", this.id)
                    .executeUpdate();
        }
    }

    public static Sighting find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM sightings where id=:id";
            Sighting sighting = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Sighting.class);
            return sighting;
        }
    }
    public List<Object> getSightings() {
        List<Object> allSightings = new ArrayList<Object>();

        try(Connection con = DB.sql2o.open()) {
            String sqlSighting = "SELECT * FROM sightings WHERE animalid=:id";
            List<Sighting> sightings = con.createQuery(sqlSighting)
                    .addParameter("id", this.id)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Sighting.class);
            allSightings.addAll(sightings);

            String sqlEndangeredAnimal = "SELECT * FROM sightings WHERE animalid=:id AND type='endangered';";
            List<Sighting> endangeredSightings = con.createQuery(sqlSighting)
                    .addParameter("id", this.id)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Sighting.class);
            allSightings.addAll(endangeredSightings);
        }

        return allSightings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sighting sighting = (Sighting) o;
        return animalid == sighting.animalid &&
                Objects.equals(location, sighting.location) &&
                Objects.equals(ranger, sighting.ranger);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, ranger, animalid);
    }
}
