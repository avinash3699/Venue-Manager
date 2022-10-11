/**
 * Authenticator is a utility class that provides an interface to authenticate the username and password.
 * The credentials are stored in a static map object, a member of Authenticator class.
 */

import java.util.HashMap;
import java.util.Map;

public class Authenticator{

//    /**
//     * This field acts as a database that stores the usernames and passwords of all the users
//     */
//    private static Map<String, String> userCredentials = new HashMap(){
//        {
//            put("cse", "viii");
//            put("it", "viii");
//            put("nss", "viii");
//            put("uba", "viii");
//
//            put("admin1", "admin");
//            put("admin2", "admin");
//        }
//    };

    /**
     * This method is used to check the username and password entered by the user with that in the database
     * @param userName The username entered by the user
     * @param enteredPassword The password entered by the user
     * @throws AuthenticationException on invalid username or password
     */
    public static void authenticate(String userName, String enteredPassword) throws AuthenticationException {

        Map<String, String> userCredentials = Database.getInstance().userCredentials;

        if(userCredentials.containsKey(userName)){

            String dbPassword = userCredentials.get(userName);

            if(enteredPassword.equals(dbPassword)){}
            else{throw new AuthenticationException();}
        }
        else
            throw new AuthenticationException();

    }

}

class AuthenticationException extends Exception{}