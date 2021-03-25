import org.sql2o.*;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Sql2o;

public class DB {
    private static URI dbUri;
    public static Sql2o sql2o;
    static Logger logger = LoggerFactory.getLogger(DB.class);

    static {

        try{
            if(System.getenv("DATABASE_URL") == null){
                dbUri = new URI("postgres://localhost:5432/wildlife_tracker");
            }
            else {
                dbUri = new URI(System.getenv("DATABASE_URL"));
            }

            int port = dbUri.getPort();
            String host = dbUri.getHost();
            String path = dbUri.getPath();
            String username = (dbUri.getUserInfo() == null) ? Credentials.username : dbUri.getUserInfo().split(":")[0];
            String password = (dbUri.getUserInfo() == null) ? Credentials.pass : dbUri.getUserInfo().split(":")[1];
            sql2o = new Sql2o("jdbc:postgresql://" + host + ":" + port + path, username, password);
        } catch (URISyntaxException e){
            logger.error("unable to connect to database");
        }

    }
}