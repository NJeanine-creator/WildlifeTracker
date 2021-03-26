import org.sql2o.*;

public class DB {
    String connectionString = "jdbc:postgresql://ec2-52-71-161-140.compute-1.amazonaws.com:5432/d637nrtd1sp4mq";//!
    //Sql2o sql2o = new Sql2o(connectionString, "epsydrsinwlbvn", "ae6ad6285b68d84082f907686dc997e9882fd472c4419e087e5bc135a0c2424d"); //!
    public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker", "nishimwe", "nishimwe");
    //public static Sql2o sql2o = new Sql2o("jdbc:postgres://ec2-34-192-173-173.compute-1.amazonaws.com:5432/d2r2quhn9fammo", "bdqqrvosrjsvsv", "9a74c767c3203425fd59b56426708414d277ef76762231654c770fcf152f1e23");
//

}