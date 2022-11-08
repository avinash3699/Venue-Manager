package database;

import core.manager.VenueManager;
import core.user.Admin;
import core.user.Representative;
import core.user.User;
import core.venue.*;
import helper.DefensiveCopyHelper;

import java.time.LocalDate;
import java.util.*;

// have to generate setters and getters for properties instead of reading and writing it directly
public final class Database {

    // This field tracks the venueCode and incremented for every new venue addition
    private int venueCode = 1;


    // **********************************************************************************************
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


    // **********************************************************************************************
    private HashMap<String, User> users = new HashMap<String, User>(){
        {
            put("admin1", new Admin("admin1", "9790877950", "admin1@org", new VenueManager()));
            put("cse", new Representative("cse", "9790963512", "cse@org", new VenueManager()));
            put("nss", new Representative("nss", "8056117046", "nss@org", new VenueManager()));
        }
    };

    public Map<String, User> getUsers() {
        return DefensiveCopyHelper.getDefensiveCopyMap(users);
    }

    public void addToUsers(String username, User user) {
        //TODO put a user object's 'defensive copy'
        users.put(username, user);
    }

    public void removeFromUsers(String username) {
        users.remove(username);
    }


    // **********************************************************************************************

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

    public void addToUserCredentials(String username, String password) {
        userCredentials.put(username, password);
    }

    public void removeFromUserCredentials(String username) {
        userCredentials.remove(username);
    }

    public boolean changeUserPassword(String username, String newPassword) {
        userCredentials.put(username, newPassword);
        return true;
    }

    // delegated by VenueManager.authenticate() to authenticate
    // Reason: to not expose the User Credentials outside the Database.Database class
    public User authenticate(String userName, String enteredPassword){

        if(userCredentials.containsKey(userName)){

            String dbPassword = userCredentials.get(userName);

            // if successful, return logged-in User object
            if(enteredPassword.equals(dbPassword)){

                //TODO return a defensive copy of the object
                return users.get(userName);
            }
            // if password mismatch, return null
            else return null;
        }

        // if username doesn't exist, return null
        else
            return null;
    }


    // **********************************************************************************************

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

    public Map<Integer, Venue> getVenues() {
        return DefensiveCopyHelper.getDefensiveCopyMap(venues);
    }

    public void addVenue(Venue newVenue) {
        //TODO put a defensive copy of the Venue object(newVenue)
        venues.put(Integer.parseInt(newVenue.getVenueCode()), newVenue);

        // inserting an empty arraylist, for the new venue code
        // else, NPE will be thrown when tried to check the availability of the newly added venues
        venueReservationDetails.put(Integer.parseInt(newVenue.getVenueCode()), new ArrayList<>());
    }

    public void removeVenue(int venueCode) {
        venues.remove(venueCode);

        //TODO - remove the reservation details from 'venueReservationDetails' and 'userReservationDetails'
        //     - should think whether the reservation details have to be removed when the venue is removed
        venueReservationDetails.remove(venueCode);
    }

    public boolean updateVenue(int venueCode, String newValue, VenueUpdate updateOption) {
        if(updateOption == VenueUpdate.NAME){
            venues.get(venueCode).setVenueName(newValue);
            return true;
        }
        else if (updateOption == VenueUpdate.SEATING_CAPACITY) {
            venues.get(venueCode).setSeatingCapacity(newValue);
            return true;
        }
        return false;
    }

    public List<Integer> getVenueCodesList() {
        List<Integer> venueCodes = new ArrayList();
        for(int venueCode: venues.keySet())
            venueCodes.add(venueCode);
        return venueCodes;
    }

    public int getVenuesCount() {
        return venues.size();
    }

    public String getVenueNameFromCode(int venueCode){
        return venues.get(venueCode).getVenueName();
    }


    // **********************************************************************************************
    private Map<Integer, List<Reservation>> venueReservationDetails = new HashMap<Integer, List<Reservation>>(){
        {
            put(
                    1,
                    new ArrayList(){
                        {
                            add(new Reservation());
                        }
                    }
            );
            put(
                    2,
                    new ArrayList(){
                        {
                            add(new Reservation());
                        }
                    }
            );
            put(
                    3,
                    new ArrayList(){
                        {
                            add(new Reservation());
                        }
                    }
            );
            put(
                    4,
                    new ArrayList(){
                        {
                            add(new Reservation());
                        }
                    }
            );
            put(
                    5,
                    new ArrayList(){
                        {
                            add(new Reservation());
                        }
                    }
            );
            put(
                    6,
                    new ArrayList(){
                        {
                            add(new Reservation());
                        }
                    }
            );
        }
    };

    public Map<Integer, List<Reservation>> getVenueReservationDetails() {
        return DefensiveCopyHelper.getDefensiveCopyMap(venueReservationDetails);
    }

    public void addToVenueReservationDetails(int venueCode, Reservation reservationDetails) {
        if(venueReservationDetails.containsKey(venueCode))
            //TODO put a defensive copy of the Reservation object(reservationDetails)
            venueReservationDetails.get(venueCode).add(reservationDetails);
        else{
            ArrayList<Reservation> reservationList = new ArrayList<>();
            //TODO put a defensive copy of the Reservation object(reservationDetails)
            reservationList.add(reservationDetails);
            venueReservationDetails.put(venueCode, reservationList);
        }
    }

    public boolean removeFromVenueReservationDetails(int venueCode, int accessId) {
        List<Reservation> reservations = venueReservationDetails.get(venueCode);
        int removeIndex = -1;
        for(int index = 0; index < reservations.size(); index++){
            if(reservations.get(index).getAccessId() == accessId){
                removeIndex = index;
                break;
            }
        }
        venueReservationDetails.get(venueCode).remove(removeIndex);
        return true;
    }

    public boolean removeFromVenueReservationDetails(int venueCode, int accessId, LocalDate fromDate, LocalDate toDate) {
        List<Reservation> reservations = venueReservationDetails.get(venueCode);
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
            //TODO pass a defensive copy of the dates 'fromDate' and 'toDate'
            desiredReservation.removeDates(fromDate, toDate);

            // if the reservation dates becomes empty after removing the dates from the reservation
            // removing the reservation from the list
            if(desiredReservation.getReservedDates().size() == 0)
                removeFromVenueReservationDetails(venueCode, accessId);
            return true;
        }
        return false;
    }


    // **********************************************************************************************
    private Map<Integer, String> accessIdUserMap = new HashMap<>();

    public void addToAccessIdUserMap(int accessId, String username) {
        accessIdUserMap.put(accessId, username);
    }

    public ArrayList<Integer> getAccessIds() {
        ArrayList<Integer> accessIds = new ArrayList<>();
        for(int accessId: accessIdUserMap.keySet())
            accessIds.add(accessId);
        return accessIds;
    }


    // **********************************************************************************************
    private Map<String, List<Reservation>> userReservationDetails = new HashMap<>();

    public List<Reservation> getUserReservation(String username) {
        if(userReservationDetails.containsKey(username))
            return DefensiveCopyHelper.getDefensiveCopyList(userReservationDetails.get(username));
        return new ArrayList<>();
    }

    public boolean addToUserReservationDetails(String username, Reservation currentReservation) {
        if(userReservationDetails.containsKey(username))
            //TODO put a defensive copy of the Reservation object(currentReservation)
            userReservationDetails.get(username).add(currentReservation);
        else{
            List<Reservation> reservations = new ArrayList<>();
            //TODO put a defensive copy of the Reservation object(currentReservation)
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
            //TODO pass a defensive copy of the dates 'fromDate' and 'toDate'
            desiredReservation.removeDates(fromDate, toDate);

            // if the reservation dates becomes empty after removing the dates from the reservation
            // removing the reservation from the list
            if(desiredReservation.getReservedDates().size() == 0)
                removeFromUserReservationDetails(accessId, userName);
            return true;
        }
        return false;
    }


    // **********************************************************************************************
    public int getNewVenueCode() {
        int tempVenueCode = venueCode;
        venueCode++;
        return tempVenueCode;
    }

}