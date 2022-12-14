package database;

import core.manager.VenueManager;
import core.user.Admin;
import core.user.Representative;
import core.user.User;
import core.venue.Auditorium;
import core.venue.ConferenceRoom;
import core.venue.HandsOnTrainingCentre;
import core.venue.Reservation;
import core.venue.Venue;
import core.venue.VenueType;
import core.venue.VenueUpdate;
import helper.DefensiveCopyHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
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
    private final HashMap<String, User> users = new HashMap<String, User>(){
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
        users.put(username, user.clone());
    }

    public void removeFromUsers(String username) {
        users.remove(username);
    }


    // **********************************************************************************************

    // This field stores the usernames and passwords of all the users
    private final Map<String, String> userCredentials = new HashMap<String, String>(){
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

                return users.get(userName).clone();
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
    private final Map<Integer, Venue> venues = new LinkedHashMap<Integer, Venue>(){
        {
            put(venueCode++, new Auditorium.Builder("1", "SEC College", VenueType.AUDITORIUM)
                    .seatingCapacity("400")
                    .isAirConditioned(true)
                    .isWifiAvailable(false)
                    .isChargingPortsAvailable(false)
                    .isMicStandAvailable(true)
                    .noOfDisplayScreen("2")
                    .venueName("Sigma")
                    .build()
            );
            put(venueCode++, new ConferenceRoom.Builder("2", "B Block Ground Floor", VenueType.CONFERENCE)
                    .venueName("Conferoom B")
                    .seatingCapacity("30")
                    .isAirConditioned(true)
                    .isWifiAvailable(true)
                    .isChargingPortsAvailable(true)
                    .isWhiteBoardAvailable(true)
                    .build()
            );
            put(venueCode++, new Auditorium.Builder("3", "Inside LM Stadium", VenueType.AUDITORIUM)
                    .seatingCapacity("286")
                    .isAirConditioned(true)
                    .isWifiAvailable(true)
                    .isChargingPortsAvailable(false)
                    .isMicStandAvailable(true)
                    .noOfDisplayScreen("1")
                    .venueName("VRR")
                    .build()
            );
            put(venueCode++, new HandsOnTrainingCentre.Builder("4", "New Library Building, Third Floor", VenueType.HANDS_ON_TRAINING)
                    .venueName("Alpha")
                    .seatingCapacity("160")
                    .isAirConditioned(true)
                    .isWifiAvailable(true)
                    .isChargingPortsAvailable(true)
                    .isMicStandAvailable(false)
                    .build()
            );
            put(venueCode++, new HandsOnTrainingCentre.Builder("5", "New Library Building, Third Floor", VenueType.HANDS_ON_TRAINING)
                    .venueName("Beta")
                    .seatingCapacity("160")
                    .isAirConditioned(true)
                    .isWifiAvailable(true)
                    .isChargingPortsAvailable(true)
                    .isMicStandAvailable(true)
                    .build()
            );
            put(venueCode++, new ConferenceRoom.Builder("6", "A Block Third Floor", VenueType.CONFERENCE)
                    .venueName("Conferoom A")
                    .seatingCapacity("20")
                    .isAirConditioned(true)
                    .isWifiAvailable(true)
                    .isChargingPortsAvailable(false)
                    .isWhiteBoardAvailable(false)
                    .build()
            );
        }
    };

    public Map<Integer, Venue> getVenues() {
        return DefensiveCopyHelper.getDefensiveCopyMap(venues);
    }

    public void addVenue(Venue newVenue) {
        venues.put(Integer.parseInt(newVenue.getVenueCode()), newVenue.clone());

        // inserting an empty arraylist, for the new venue code
        // else, NPE will be thrown when tried to check the availability of the newly added venues
        venueReservationDetails.put(Integer.parseInt(newVenue.getVenueCode()), new ArrayList<>());
    }

    public void removeVenue(int venueCode) {
        venues.remove(venueCode);

        // removing the venue reservation details from the venue reservation list
        venueReservationDetails.remove(venueCode);

        // removing the venue reservations details from the user reservation list
        for(String username: userReservationDetails.keySet()){
            userReservationDetails.get(username)
                                  .removeIf(reservation -> (reservation.getVenueCode() == venueCode));
        }
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
        List<Integer> venueCodes = new ArrayList<>();
        for(int venueCode: venues.keySet())
            venueCodes.add(venueCode);
        return venueCodes;
    }

    public String getVenueNameFromCode(int venueCode){
        return venues.get(venueCode).getVenueName();
    }


    // **********************************************************************************************
    private final Map<Integer, List<Reservation>> venueReservationDetails = new HashMap<Integer, List<Reservation>>(){
        {
            put(
                    1,
                    new ArrayList<Reservation>(){
                        {
                            add(new Reservation());
                        }
                    }
            );
            put(
                    2,
                    new ArrayList<Reservation>(){
                        {
                            add(new Reservation());
                        }
                    }
            );
            put(
                    3,
                    new ArrayList<Reservation>(){
                        {
                            add(new Reservation());
                        }
                    }
            );
            put(
                    4,
                    new ArrayList<Reservation>(){
                        {
                            add(new Reservation());
                        }
                    }
            );
            put(
                    5,
                    new ArrayList<Reservation>(){
                        {
                            add(new Reservation());
                        }
                    }
            );
            put(
                    6,
                    new ArrayList<Reservation>(){
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

    public boolean addToVenueReservationDetails(int venueCode, Reservation reservationDetails) {
        if(venueReservationDetails.containsKey(venueCode)) {
            venueReservationDetails.get(venueCode).add(new Reservation(reservationDetails));
        }
        else{
            ArrayList<Reservation> reservationList = new ArrayList<>();
            reservationList.add(new Reservation(reservationDetails));
            venueReservationDetails.put(venueCode, reservationList);
        }
        return true;
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
    private final Map<Integer, String> accessIdUserMap = new HashMap<>();

    public boolean addToAccessIdUserMap(int accessId, String username) {
        accessIdUserMap.put(accessId, username);
        return true;
    }

    public ArrayList<Integer> getAccessIds() {
        ArrayList<Integer> accessIds = new ArrayList<>();
        for(int accessId: accessIdUserMap.keySet())
            accessIds.add(accessId);
        return accessIds;
    }


    // **********************************************************************************************
    private final Map<String, List<Reservation>> userReservationDetails = new HashMap<>();

    public List<Reservation> getUserReservation(String username) {
        if(userReservationDetails.containsKey(username))
            return DefensiveCopyHelper.getDefensiveCopyList(userReservationDetails.get(username));
        return new ArrayList<>();
    }

    public boolean addToUserReservationDetails(String username, Reservation currentReservation) {
        if(userReservationDetails.containsKey(username))
            userReservationDetails.get(username).add(new Reservation(currentReservation));
        else{
            List<Reservation> reservations = new ArrayList<>();
            reservations.add(new Reservation(currentReservation));
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


    // **********************************************************************************************
    LocalDate maxPossibleReservationDate = LocalDate.parse(
            "31-12-2022",
            DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT)
    );

    public void setMaxPossibleReservationDate(LocalDate date){
        this.maxPossibleReservationDate = date.plusDays(0);
    }

    public LocalDate getMaxPossibleReservationDate(){
        return this.maxPossibleReservationDate.plusDays(0);
    }


    // **********************************************************************************************
    public int getNewVenueCode() {
        int tempVenueCode = venueCode;
        venueCode++;
        return tempVenueCode;
    }

}