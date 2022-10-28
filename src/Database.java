import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

// have to generate setters and getters for properties instead of reading and writing it directly
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

    HashMap<String, User> users = new HashMap(){
        {
            put("admin1", new Admin("admin1", "9790877950", "admin1@org"));
            put("cse", new Representative("cse", "9790963512", "cse@org"));
            put("nss", new Representative("nss", "8056117046", "nss@org"));
        }
    };

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
                    "3",
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
    Map<Integer, TreeMap<Integer, ArrayList<LocalDate>>> reservationDetails = new TreeMap(){
        {
            put(1, new TreeMap(){
                {
                    put(Integer.MAX_VALUE - 1, new ArrayList(){
                        {
                            add(LocalDate.parse("12-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        }
                    });
                }
            });
            put(2, new TreeMap(){
                {
                    put(Integer.MAX_VALUE - 2, new ArrayList(){
                        {
                            add(LocalDate.parse("13-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        }
                    });
                }
            });
            put(3, new TreeMap(){
                {
                    put(Integer.MAX_VALUE - 3, new ArrayList(){
                        {
                            add(LocalDate.parse("14-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        }
                    });
                }
            });
            put(4, new TreeMap(){
                {
                    put(Integer.MAX_VALUE - 4, new ArrayList(){
                        {
                            add(LocalDate.parse("15-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        }
                    });
                }
            });
            put(5, new TreeMap(){
                {
                    put(Integer.MAX_VALUE - 5, new ArrayList(){
                        {
                            add(LocalDate.parse("16-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        }
                    });
                }
            });
            put(6, new TreeMap(){
                {
                    put(Integer.MAX_VALUE - 6, new ArrayList(){
                        {
                            add(LocalDate.parse("17-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        }
                    });
                }
            });
        }
    };

    Map<Integer, String> accessIdUserMap = new HashMap<>();

    public int getVenuesCount() {
        return venues.size();
    }

    public ArrayList<Integer> getAccessIds() {
        ArrayList<Integer> accessIds = new ArrayList<>();
        for(int accessId: accessIdUserMap.keySet())
            accessIds.add(accessId);
        return accessIds;
    }

    public String getVenueNameFromCode(int venueCode){
        return venues.get(venueCode).venueName;
    }

    // delegated by VenueManager.authenticate() to authenticate
    // Reason: to not expose the User Credentials outside the Database class
    User authenticate(String userName, String enteredPassword){

        if(userCredentials.containsKey(userName)){

            String dbPassword = userCredentials.get(userName);

            // if successful, return logged in User object
            if(enteredPassword.equals(dbPassword)){
                return users.get(userName);
            }
            // if password mismatch, return null
            else return null;
        }

        // if username doesn't exist, return null
        else
            return null;
    }

}