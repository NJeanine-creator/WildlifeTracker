import java.sql.Timestamp;

public abstract class WildAnimals {
    public int id;
    public String name;
    public Timestamp dateAdded;
    public String type;
    public  String health;
    public  String age;
    public static final String[] ageTypes = {"New born", "Young","Adult"};
    public static  final String[] healthStatuses = {"Healthy", "Ill", "Okay"};

}
