package core.manager;

import core.venue.Reservation;
import core.venue.VenueType;
import core.user.Representative;
import core.user.User;
import core.venue.Venue;
import database.Database;
import helper.DefensiveCopyHelper;

import java.time.LocalDate;
import java.util.*;

// The system that acts as an interface between the user and the database
public class VenueManager implements AdminManager, RepresentativeManager {

    //methods

    // method to authenticate the logging user
    // delegates the authentication function to Database.Database object
    // interacts with the Database.Database class
    public User authenticate(String userName, String enteredPassword){
        return Database.getInstance().authenticate(userName, enteredPassword);
    }

    // method to display the details of all the venues
    // interacts with the Database.Database class
    public void displayVenueDetails() {
        for(Venue venue: Database.getInstance().getVenues().values()){
            Map<String, String> venueDetails = venue.getVenueDetails();
            for(String key: venueDetails.keySet()){
                System.out.printf("%s: %s\n", key, venueDetails.get(key));
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displayVenueDetails(int venueCode){
        Map<String, String> venueDetails = Database.getInstance().getVenues().get(venueCode).getVenueDetails();
        for(String key: venueDetails.keySet()){
            System.out.printf("%s: %s\n", key, venueDetails.get(key));
        }
        System.out.println();
    }

    // method to display the details of venues that is of the given type (E.g. Conference Room, Auditorium)
    // interacts with the Database.Database class
    public void displayVenueDetails(VenueType type) {
        for(Venue venue: Database.getInstance().getVenues().values()){
            Map<String, String> venueDetails = venue.getVenueDetails();
            if(!(venue.getType() == type))
                continue;
            for(String key: venueDetails.keySet()){
                System.out.printf("%s: %s\n", key, venueDetails.get(key));
            }
            System.out.println();
        }
        System.out.println();
    }

    // to check the availability of all the venues for the given 'from date' to 'end date'
    // returns the name of all the available venues
    // interacts with the Database.Database class
    public ArrayList<Integer> checkAvailability(LocalDate from, LocalDate to) {
        Map<Integer, TreeMap<Integer, ArrayList<LocalDate>>> reservationDetails = Database.getInstance().getReservationDetails();
        ArrayList<Integer> availableVenues = new ArrayList<>();
        for(int venueCode: reservationDetails.keySet()){
            boolean available = true;
            outerLoop:
            for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
                for(int accessId: (reservationDetails.get(venueCode)).keySet()){
                    if (((reservationDetails.get(venueCode)).get(accessId)).contains(date)) {
                        available = false;
                        break outerLoop;
                    }
                }
            }
            if(available)
                availableVenues.add(venueCode);
        }
        return availableVenues;
    }

    // to check the availability of venues that is of the given type for the given 'from date' to 'end date'
    // returns the name of the available venue from the given type
    // interacts with the Database.Database class
    public ArrayList<Integer> checkAvailability(VenueType type, LocalDate from, LocalDate to) {
        Map<Integer, TreeMap<Integer, ArrayList<LocalDate>>> reservationDetails = Database.getInstance().getReservationDetails();
        ArrayList<Integer> availableVenues = new ArrayList<>();
        for(int venueCode: reservationDetails.keySet()){
            boolean available = true;
            if(Database.getInstance().getVenues().get(venueCode).getType() == type) {
                outerLoop:
                for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
                    for(int accessId: (reservationDetails.get(venueCode)).keySet()){
                        if (reservationDetails.get(venueCode).get(accessId).contains(date)) {
                            available = false;
                            break outerLoop;
                        }
                    }
                }
                if (available)
                    availableVenues.add(venueCode);
            }
        }
        return availableVenues;
    }

    // to check the availability of a specific venue for the given 'from date' to 'end date'
    // returns 'true' if available, else 'false'
    // interacts with the Database.Database class
    public boolean checkAvailability(int venueCode, LocalDate from, LocalDate to) {
        TreeMap<Integer, ArrayList<LocalDate>> reservationDetails = Database.getInstance().getReservationDetails().get(venueCode);
        for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
            for(int accessId: reservationDetails.keySet()){
                if(reservationDetails.get(accessId).contains(date)){
                    return false;
                }
            }
        }
        return true;
    }

    // to reserve the venue of given type for the given 'from date' to 'end date'
    public Reservation reserveVenue(VenueType type, LocalDate from, LocalDate to, String username) {
        ArrayList<Integer> availableVenues = checkAvailability(type, from, to);
        Reservation currentReservation = null;
        boolean isUserReservationDBUpdated = false;
        if(availableVenues.size() != 0) {
            int accessId = generateUniqueAccessId();

            boolean isDatabaseUpdated = updateAvailability(availableVenues.get(0), from, to, accessId, username);

            if(! isDatabaseUpdated)
                return null;

            currentReservation = new Reservation(
                    accessId,
                    username,
                    availableVenues.get(0),
                    from,
                    to
            );
            isUserReservationDBUpdated = Database.getInstance().addToUserReservationDetails(username, currentReservation);

//            System.out.println("Hurray!! You have reserved your venue successfully. Venue: " + availableVenues.get(0));
//            System.out.println("Your access ID is: " + accessId);
        }

        if(isUserReservationDBUpdated)
            return currentReservation;
        return null;
        
//        else
//            System.out.println("Sorry! No Venues Available based on your request");
    }

    // to reserve a specific venue using the venue code
    public Reservation reserveVenue(int venueCode, LocalDate from, LocalDate to, String username) {
        Reservation currentReservation = null;
        boolean available = checkAvailability(venueCode, from, to);
        if(available) {
            int accessId = generateUniqueAccessId();

            boolean isDatabaseUpdated = updateAvailability(venueCode, from, to, accessId, username);

            if(! isDatabaseUpdated)
                return null;

            currentReservation = new Reservation(
                    accessId,
                    username,
                    venueCode,
                    from,
                    to
            );
            Database.getInstance().addToUserReservationDetails(username, currentReservation);
//            System.out.println("Hurray!! You have reserved your venue successfully. Venue: " + venueCode);
//            System.out.println("Your access ID is: " + accessId);
        }

        return currentReservation;
//        else
//            System.out.println("Sorry! The venue you requested is already reserved");
    }

    // to update the database with the details of the new venue reservation
    // interacts with the Database.Database class
    private boolean updateAvailability(int venueCode, LocalDate from, LocalDate to, int accessId, String username) {
        TreeMap<Integer, ArrayList<LocalDate>> accessIdWithDates = new TreeMap<>();
        ArrayList<LocalDate> reservedDates = new ArrayList<>();
        for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
            reservedDates.add(date);
        }
        accessIdWithDates.put(accessId, reservedDates);
        Database.getInstance().addToReservationDetails(venueCode, accessIdWithDates);
        System.out.println(Database.getInstance().getReservationDetails().get(venueCode));

        Database.getInstance().addToAccessIdUserMap(accessId, username);

        return true;

//        ((Representative)currentUser).addReservationDetails(accessId, venueCode, reservedDates);
    }

    // to cancel the reserved venue using the venue code and the unique access id
    public boolean cancelVenue(int venueCode, int accessId, String username) {
        Database database = Database.getInstance();
//        TreeMap<Integer, ArrayList<LocalDate>> reservationDetails = database.getReservationDetails().get(venueCode);
//        reservationDetails.remove(accessId);
//        database.addToReservationDetails(venueCode, reservationDetails);
        database.removeFromReservationDetails(venueCode, accessId);

        System.out.println(Database.getInstance().getReservationDetails().get(venueCode));

        database.removeFromUserReservationDetails(accessId, username);

        return true;
//        System.out.println("Success! You have successfully cancelled the venue");

//        ((Representative) currentUser).removeFromReservationDetails(accessId);
    }

    public boolean cancelVenue(int venueCode, int accessId, LocalDate from, LocalDate to, String username) {
        Database.getInstance().removeFromReservationDetails(venueCode, accessId, from, to);
        System.out.println(Database.getInstance().getReservationDetails().get(venueCode));

        Database.getInstance().removeFromUserReservationDetails(accessId, from, to, username);

        return true;
//        System.out.println("Success! You have successfully cancelled the mentioned dates");

//        ((Representative) currentUser).removeFromReservationDetails(accessId, from, to);
    }

//    public void cancelVenue(int venueCode, int accessId, LocalDate dateToBeCancelled) {
//        TreeMap<Integer, ArrayList<LocalDate>> reservationDetails = Database.Database.getInstance().getReservationDetails().get(venueCode);
//        ArrayList<LocalDate> bookedDates = reservationDetails.get(accessId);
//        bookedDates.remove(dateToBeCancelled);
//        reservationDetails.put(accessId, bookedDates);
//        Database.Database.getInstance().addToReservationDetails(venueCode, reservationDetails);
//        System.out.println(Database.Database.getInstance().getReservationDetails().get(venueCode));
//        System.out.println("Success! You have successfully cancelled the requested date");
//
//        ((Representative) currentUser).removeFromReservationDetails(accessId, dateToBeCancelled, dateToBeCancelled);
//    }

    public Reservation changeVenue(int oldVenueCode, int accessId, int newVenueCode, String username) {
        Map<Integer, TreeMap<Integer, ArrayList<LocalDate>>> reservationDetails = Database.getInstance().getReservationDetails();
        ArrayList<LocalDate> reservedDates = (reservationDetails.get(oldVenueCode)).get(accessId);

        // below line should be handled for no elements in reservedDates
        LocalDate from = reservedDates.get(0), to = reservedDates.get(reservedDates.size() - 1);

        Reservation newReservation = reserveVenue(newVenueCode, from, to, username);
        if(newReservation == null)
            return null;
        if(cancelVenue(oldVenueCode, accessId, username))
            return newReservation;
        return null;
    }

    //functions

    // to generate an access id which is returned to the user
    // an access id like a key to the venue for the reserved dates
    private int generateUniqueAccessId() {
        int generatedAccessId;
        ArrayList<Integer> accessIds = Database.getInstance().getAccessIds();
        while(accessIds.contains(generatedAccessId = new Random().nextInt(Integer.MAX_VALUE - 10)));
        return generatedAccessId;
    }

    // delegated by Main class
    // interacts with the Database.Database class
    // This method gets all the venues from the Database.Database and checks whether each venue is present in the input 'availableVenueCodes'
    // if present, it prints "Available" else, prints "Not Available"
    public void printVenuesAvailability(ArrayList<Integer> availableVenueCodes) {
        Database database = Database.getInstance();
        for(int venueCode: database.getVenues().keySet()){
            String availability = "Not Available";
            if(availableVenueCodes.contains(venueCode)){
                availability = "Available";
            }
            System.out.println(database.getVenueNameFromCode(venueCode) + ": " + availability);
        }
    }

    // delegated by Main class
    // interacts with the Database.Database class
    // This method gets all the venues from the Database.Database, and it first checks whether the venue is of the given input 'inputType'
    // if yes, it then checks whether that venue is present in the input 'availableVenueCodes'
    // if present, it prints "Available" else, prints "Not Available"
    public void printVenuesAvailability(ArrayList<Integer> availableVenueCodes, VenueType inputType) {
        Database database = Database.getInstance();
        for(int venueCode: database.getVenues().keySet()){
            VenueType venueType = database.getVenues().get(venueCode).getType();
            if(venueType == inputType){
                String availability = "Not Available";
                if(availableVenueCodes.contains(venueCode)){
                    availability = "Available";
                }
                System.out.println(database.getVenueNameFromCode(venueCode) + ": " + availability);
            }
        }
    }

    public boolean addUser(String username, String password, String emailId, String phoneNumber){
        Database database = Database.getInstance();
        database.addToUserCredentials(username, password);
        database.addToUsers(
                username,
                new Representative(
                       username,
                       phoneNumber,
                       emailId,
                       new VenueManager()
                )
        );
        return true;
    }

    public boolean removeUser(String username) {
        Database database = Database.getInstance();
        database.removeFromUserCredentials(username);
        database.removeFromUsers(username);
        return true;
    }

    public Map<String, String> getOtherUserPersonalDetails(String username) {
        Database database = Database.getInstance();
        return DefensiveCopyHelper.getDefensiveCopyMap(database.getUsers().get(username).getPersonalDetails());
    }

    public List<Reservation> getOtherUserRegistrationDetails(String username) {
        return getReservationDetails(username);
    }

    public boolean updateUserDatabase(User user) {
        Database.getInstance().addToUsers(user.getUsername(), user);
        return true;
    }

    @Override
    public List<Reservation> getReservationDetails(String username) {
        return Database.getInstance().getUserReservation(username);
    }

    public boolean checkUserNameExistence(String username) {
        return Database.getInstance().getUsers().containsKey(username);
    }
}