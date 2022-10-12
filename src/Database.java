import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Database {

    // This field tracks the venueCode and incremented for every new venue addition
    int venueCode = 1;

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

    // This field stores the reservation details of the all the venues
    // Integer - venue code
    // LocalDate - dates reserved
    // String - reserved by whom
    Map<Integer, TreeMap<LocalDate, String>> reservationDetails = new TreeMap(){
        {
            put(1, new TreeMap(){
                {
                    put(LocalDate.parse("12-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")), "admin");
                }
            });
            put(2, new TreeMap(){
                {
                    put(LocalDate.parse("13-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")), "admin");
                }
            });
            put(3, new TreeMap(){
                {
                    put(LocalDate.parse("14-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")), "admin");
                }
            });
            put(4, new TreeMap(){
                {
                    put(LocalDate.parse("15-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")), "admin");
                }
            });
            put(5, new TreeMap(){
                {
                    put(LocalDate.parse("16-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")), "admin");
                }
            });
            put(6, new TreeMap(){
                {
                    put(LocalDate.parse("17-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")), "admin");
                }
            });
        }
    };

}