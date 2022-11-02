package database;

import core.manager.VenueManager;
import core.user.Admin;
import core.user.Representative;
import core.user.User;
import core.venue.*;
import helper.DefensiveCopyHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

// have to generate setters and getters for properties instead of reading and writing it directly
public final class Database {

    // This field tracks the venueCode and incremented for every new venue addition
    private int venueCode = 1;

    // declaring a singleton object
    private static Database singletonInstance;

    // constructor declared as private to restrict the object creation from outside the class
    private Database(){}

    // method that returns the singleton object when called
    public static Database getInstance(){

        // lazy instantiation
        if(singletonInstance == null)
            singletonInstance = new Database();

        return singletonInstance;
    }

    private HashMap<String, User> users = new HashMap<String, User>(){
        {
            put("admin1", new Admin("admin1", "9790877950", "admin1@org", new VenueManager()));
            put("cse", new Representative("cse", "9790963512", "cse@org", new VenueManager()));
            put("nss", new Representative("nss", "8056117046", "nss@org", new VenueManager()));
        }
    };

    // This field stores the usernames and passwords of all the users
    private Map<String, String> userCredentials = new HashMap<String, String>(){
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
    private Map<Integer, Venue> venues = new LinkedHashMap<Integer, Venue>(){
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
    private Map<Integer, TreeMap<Integer, ArrayList<LocalDate>>> reservationDetails = new TreeMap<Integer, TreeMap<Integer, ArrayList<LocalDate>>>(){
        {
            put(1, new TreeMap<Integer, ArrayList<LocalDate>>(){
                {
                    put(Integer.MAX_VALUE - 1, new ArrayList(){
                        {
                            add(LocalDate.parse("12-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        }
                    });
                }
            });
            put(2, new TreeMap<Integer, ArrayList<LocalDate>>(){
                {
                    put(Integer.MAX_VALUE - 2, new ArrayList(){
                        {
                            add(LocalDate.parse("13-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        }
                    });
                }
            });
            put(3, new TreeMap<Integer, ArrayList<LocalDate>>(){
                {
                    put(Integer.MAX_VALUE - 3, new ArrayList(){
                        {
                            add(LocalDate.parse("14-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        }
                    });
                }
            });
            put(4, new TreeMap<Integer, ArrayList<LocalDate>>(){
                {
                    put(Integer.MAX_VALUE - 4, new ArrayList(){
                        {
                            add(LocalDate.parse("15-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        }
                    });
                }
            });
            put(5, new TreeMap<Integer, ArrayList<LocalDate>>(){
                {
                    put(Integer.MAX_VALUE - 5, new ArrayList(){
                        {
                            add(LocalDate.parse("16-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        }
                    });
                }
            });
            put(6, new TreeMap<Integer, ArrayList<LocalDate>>(){
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

    private Map<Integer, String> accessIdUserMap = new HashMap<>();

    private Map<String, List<Reservation>> userReservationDetails = new HashMap<>();

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
        return venues.get(venueCode).getVenueName();
    }

    // delegated by VenueManager.authenticate() to authenticate
    // Reason: to not expose the User Credentials outside the Database.Database class
    public User authenticate(String userName, String enteredPassword){

        if(userCredentials.containsKey(userName)){

            String dbPassword = userCredentials.get(userName);

            // if successful, return logged-in User object
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

    public Map<String, User> getUsers() {
        return DefensiveCopyHelper.getDefensiveCopyMap(users);
    }

    public Map<Integer, Venue> getVenues() {
        return DefensiveCopyHelper.getDefensiveCopyMap(venues);
    }

    public Map<Integer, TreeMap<Integer, ArrayList<LocalDate>>> getReservationDetails() {
        return DefensiveCopyHelper.getDefensiveCopyMap(reservationDetails);
    }

    public void removeFromUserCredentials(String username) {
        userCredentials.remove(username);
    }

    public void removeFromUsers(String username) {
        users.remove(username);
    }

    public void addToUsers(String username, User user) {
        users.put(username, user);
    }

    public void addToUserCredentials(String username, String password) {
        userCredentials.put(username, password);
    }

    public void addToReservationDetails(int venueCode, TreeMap<Integer, ArrayList<LocalDate>> newReservationDetails) {
        TreeMap<Integer, ArrayList<LocalDate>> givenVenueReservationDetails = reservationDetails.get(venueCode);
        givenVenueReservationDetails.putAll(newReservationDetails);
        reservationDetails.put(venueCode, givenVenueReservationDetails);
    }

    public void addToAccessIdUserMap(int accessId, String username) {
        accessIdUserMap.put(accessId, username);
    }

    public void removeFromReservationDetails(int venueCode, int accessId) {
        reservationDetails.get(venueCode).remove(accessId);
    }

    public void removeFromReservationDetails(int venueCode, int accessId, LocalDate from, LocalDate to) {
        TreeMap<Integer, ArrayList<LocalDate>> tempReservationDetails = reservationDetails.get(venueCode);
        ArrayList<LocalDate> bookedDates = tempReservationDetails.get(accessId);
        for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
            bookedDates.remove(date);
        }
        tempReservationDetails.put(accessId, bookedDates);
        reservationDetails.put(venueCode, tempReservationDetails);
    }

    public List<Reservation> getUserReservation(String username) {
        if(userReservationDetails.containsKey(username))
            return DefensiveCopyHelper.getDefensiveCopyList(userReservationDetails.get(username));
        return new ArrayList<>();
    }

    public boolean addToUserReservationDetails(String username, Reservation currentReservation) {
        if(userReservationDetails.containsKey(username))
            userReservationDetails.get(username).add(currentReservation);
        else{
            List<Reservation> reservations = new ArrayList<>();
            reservations.add(currentReservation);
            userReservationDetails.put(username, reservations);
        }
        return true;
    }

    public boolean removeFromUserReservationDetails(int accessId, String userName) {
        List<Reservation> reservations = userReservationDetails.get(userName);
        int removeIndex = -1;
        for(int index = 0; index < reservations.size(); index++){
            if(reservations.get(index).getAccessId() == accessId){
                removeIndex = index;
                break;
            }
        }
        userReservationDetails.get(userName).remove(removeIndex);
        return true;
    }

    public boolean removeFromUserReservationDetails(int accessId, LocalDate fromDate, LocalDate toDate, String userName) {
        List<Reservation> reservations = userReservationDetails.get(userName);
        Reservation desiredReservation = null;
        for(Reservation reservation: reservations){
            if(reservation.getAccessId() == accessId){
                desiredReservation = reservation;
                break;
            }
        }

        // if the given access id does not match with that of the user reservations
        // desiredReservation will be null
        if(desiredReservation != null) {
            desiredReservation.removeDates(fromDate, toDate);

            // if the reservation dates becomes empty after removing the dates from the reservation
            // removing the reservation from the list
            if(desiredReservation.getReservedDates().size() == 0)
                removeFromUserReservationDetails(accessId, userName);
            return true;
        }
        return false;
    }
}