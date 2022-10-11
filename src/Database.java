import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Database {

    // This field tracks the venueCode and incremented for every new venue addition
    int venueCode = 0;

    // declaring a singleton object
    private static Database singletonInstance;

    // constructor declared as private to restrict the object creation from outside the class
    private Database(){}

    // method that returns the singleton object when called
    static Database getInstance(){

        // lazy instantiation
        if(singletonInstance == null)
            singletonInstance = new Database();

        return singletonInstance;
    }

    // This field stores the usernames and passwords of all the users
    Map<String, String> userCredentials = new HashMap(){
        {
            put("cse", "viii");
            put("it", "viii");
            put("nss", "viii");
            put("uba", "viii");

            put("admin1", "admin");
            put("admin2", "admin");
        }
    };

    // This field consists of the venue code of the venues mapped with their object
    Map<Integer, Venue> venues = new LinkedHashMap(){
        {
            put(venueCode++, new Auditorium(
                    "Sigma",
                    "1",
                    "SEC College",
                    "400",
                    true,
                    false,
                    false,
                    true,
                    "2"
            ));
            put(venueCode++, new ConferenceRoom(
                    "Conferoom B",
                    "2",
                    "B Block Ground Floor",
                    "30",
                    true,
                    true,
                    true,
                    true
            ));
            put(venueCode++, new Auditorium(
                    "VRR",
                    "2",
                    "Inside LM Stadium",
                    "286",
                    true,
                    true,
                    false,
                    true,
                    "1"
            ));
            put(venueCode++, new HandsOnTrainingCentre(
                    "Alpha",
                    "4",
                    "New Library Building, Third Floor",
                    "160",
                    true,
                    true,
                    true,
                    false
            ));
            put(venueCode++, new HandsOnTrainingCentre(
                    "Beta",
                    "5",
                    "New Library Building, Third Floor",
                    "160",
                    true,
                    true,
                    true,
                    true
            ));
            put(venueCode++, new ConferenceRoom(
                    "Conferoom A",
                    "6",
                    "A Block Third Floor",
                    "20",
                    true,
                    true,
                    false,
                    false
            ));
        }
    };

}
