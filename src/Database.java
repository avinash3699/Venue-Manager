import java.util.HashMap;
import java.util.Map;

public class Database {

    /**
     * This field stores the usernames and passwords of all the users
     */
    static Map<String, String> userCredentials = new HashMap(){
        {
            put("cse", "viii");
            put("it", "viii");
            put("nss", "viii");
            put("uba", "viii");

            put("admin1", "admin");
            put("admin2", "admin");
        }
    };

}
