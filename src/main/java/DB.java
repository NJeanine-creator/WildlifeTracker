import org.sql2o.*;

public class DB {
    public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker", "nishimwe", "nishimwe");
    //public static Sql2o sql2o = new Sql2o("jdbc:postgres://ec2-34-192-173-173.compute-1.amazonaws.com:5432/d2r2quhn9fammo", "bdqqrvosrjsvsv", "9a74c767c3203425fd59b56426708414d277ef76762231654c770fcf152f1e23");
}