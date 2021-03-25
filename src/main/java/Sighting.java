import org.sql2o.Connection;
import org.sql2o.Sql2oException;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Sighting {
    private int id;
    private int AnimalId;
    private String location;
    private String rangerName;
    private Timestamp dateSpotted;


    public Sighting(int AnimalId, String location, String rangerName) {
        this.id = id;
        this.AnimalId = AnimalId;
        this.location = location;
        this.rangerName = rangerName;
        dateSpotted  = new Timestamp(new Date().getTime());

    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateSpotted() {
        return dateSpotted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sighting)) return false;
        Sighting sighting = (Sighting) o;
        return getId() == sighting.getId() &&
                getAnimalId() == sighting.getAnimalId() &&
                getLocation().equals(sighting.getLocation()) &&
                getRangerName().equals(sighting.getRangerName()) &&
                getDateSpotted().equals(sighting.getDateSpotted());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAnimalId(), getLocation(), getRangerName(), getDateSpotted());
    }

    public void setDateSpotted(Timestamp dateSpotted) {
        this.dateSpotted = dateSpotted;
    }
    public int getAnimalId() {
        return AnimalId;
    }

    public void setAnimalId(int animalId) {
        AnimalId = animalId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRangerName() {
        return rangerName;
    }

    public void setRangerName(String rangerName) {
        this.rangerName = rangerName;
    }

    public void saveSighting(){
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO sightings (location, rangerName, animalId, dateSpotted) VALUES (:location, :rangerName, :animalId, :dateSpotted)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("location", this.location)
                    .addParameter("rangerName", this.rangerName)
                    .addParameter("dateSpotted", this.dateSpotted)
                    .addParameter("animalId", this.AnimalId)
                    .executeUpdate()
                    .getKey();
        } catch (Sql2oException ex) {
            System.out.println("found "+ex);
        }

    }

    public static List<Sighting> getSightings(){
        String sql = "SELECT * FROM sightings;";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Sighting.class);
        }
    }

    public int getId(){
        return id;
    }

    public static Sighting find(int findId) {
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT * FROM sightings WHERE id=:id;")
                    .addParameter("id",findId)
                    .executeAndFetchFirst(Sighting.class);
        }
    }

    public static List<Animal> getAll(){
        String sql = "SELECT id, name FROM animals;";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Animal.class);
        }
    }


}