import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

// The system that acts as a interface between the user and the database
public class VenueManager {

    private User currentUser;

    //methods

    // method to authenticate the logging user
    // delegates the authentication function to Database object
    // interacts with the Database class
    User authenticate(String userName, String enteredPassword){
        currentUser = Database.getInstance().authenticate(userName, enteredPassword);
        return currentUser;
    }

    // method to display the details of all the venues
    // interacts with the Database class
    public void displayVenueDetails() {
        for(Venue venue: Database.getInstance().venues.values()){
            Map<String, String> venueDetails = venue.getVenueDetails();
            for(String key: venueDetails.keySet()){
                System.out.printf("%s: %s\n", key, venueDetails.get(key));
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displayVenueDetails(int venueCode){
        Map<String, String> venueDetails = Database.getInstance().venues.get(venueCode).getVenueDetails();
        for(String key: venueDetails.keySet()){
            System.out.printf("%s: %s\n", key, venueDetails.get(key));
        }
        System.out.println();
    }

    // method to display the details of venues that is of the given type (E.g. Conference Room, Auditorium)
    // interacts with the Database class
    public void displayVenueDetails(String type) {
        for(Venue venue: Database.getInstance().venues.values()){
            Map<String, String> venueDetails = venue.getVenueDetails();
            if(!venueDetails.get("Venue Type").equals(type))
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
    // interacts with the Database class
    public ArrayList<Integer> checkAvailability(LocalDate from, LocalDate to) {
        Map<Integer, TreeMap<Integer, ArrayList<LocalDate>>> reservationDetails = Database.getInstance().reservationDetails;
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
    // interacts with the Database class
    public ArrayList<Integer> checkAvailability(String type, LocalDate from, LocalDate to) {
        Map<Integer, TreeMap<Integer, ArrayList<LocalDate>>> reservationDetails = Database.getInstance().reservationDetails;
        ArrayList<Integer> availableVenues = new ArrayList<>();
        for(int venueCode: reservationDetails.keySet()){
            boolean available = true;
            if(Database.getInstance().venues.get(venueCode).type.equals(type)) {
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
    // interacts with the Database class
    public boolean checkAvailability(int venueCode, LocalDate from, LocalDate to) {
        TreeMap<Integer, ArrayList<LocalDate>> reservationDetails = Database.getInstance().reservationDetails.get(venueCode);
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
    public void reserveVenue(String type, LocalDate from, LocalDate to) {
        ArrayList<Integer> availableVenues = checkAvailability(type, from, to);
        if(availableVenues.size() != 0) {
            int accessId = generateRandomNumber();
            updateAvailability(availableVenues.get(0), from, to, accessId);
            System.out.println("Hurray!! You have reserved your venue successfully. Venue: " + availableVenues.get(0));
            System.out.println("Your access ID is: " + accessId);
        }
        else
            System.out.println("Sorry! No Venues Available based on your request");
    }

    // to reserve a specific venue using the venue code
    public void reserveVenue(int venueCode, LocalDate from, LocalDate to) {
        boolean available = checkAvailability(venueCode, from, to);
        if(available) {
            int accessId = generateRandomNumber();
            updateAvailability(venueCode, from, to, accessId);
            System.out.println("Hurray!! You have reserved your venue successfully. Venue: " + venueCode);
            System.out.println("Your access ID is: " + accessId);
        }
        else
            System.out.println("Sorry! The venue you requested is already reserved");
    }

    // to update the database with the details of the new venue reservation
    // interacts with the Database class
    private void updateAvailability(int venueCode, LocalDate from, LocalDate to, int accessId) {
        TreeMap<Integer, ArrayList<LocalDate>> accessIdWithDates = new TreeMap<>();
        ArrayList<LocalDate> reservedDates = new ArrayList<>();
        for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
            reservedDates.add(date);
        }
        accessIdWithDates.put(accessId, reservedDates);
        accessIdWithDates.putAll(Database.getInstance().reservationDetails.get(venueCode));
        Database.getInstance().reservationDetails.put(venueCode, accessIdWithDates);
        System.out.println(Database.getInstance().reservationDetails.get(venueCode));

        Database.getInstance().accessIdUserMap.put(accessId, currentUser.getUsername());

        ((Representative)currentUser).addReservationDetails(accessId, venueCode, reservedDates);
    }

    // to cancel the reserved venue using the venue code and the unique access id
    public void cancelVenue(int venueCode, int accessId) {
        TreeMap<Integer, ArrayList<LocalDate>> reservationDetails = Database.getInstance().reservationDetails.get(venueCode);
        reservationDetails.remove(accessId);
        Database.getInstance().reservationDetails.put(venueCode, reservationDetails);
        System.out.println(Database.getInstance().reservationDetails.get(venueCode));
        System.out.println("Success! You have successfully cancelled the venue");

        ((Representative) currentUser).removeReservationDetails(accessId);
    }

    public void cancelVenue(int venueCode, int accessId, LocalDate from, LocalDate to) {
        TreeMap<Integer, ArrayList<LocalDate>> reservationDetails = Database.getInstance().reservationDetails.get(venueCode);
        ArrayList<LocalDate> bookedDates = reservationDetails.get(accessId);
        for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
            bookedDates.remove(date);
        }
        reservationDetails.put(accessId, bookedDates);
        Database.getInstance().reservationDetails.put(venueCode, reservationDetails);
        System.out.println(Database.getInstance().reservationDetails.get(venueCode));
        System.out.println("Success! You have successfully cancelled the mentioned dates");

        ((Representative) currentUser).removeReservationDetails(accessId, from, to);
    }

    public void cancelVenue(int venueCode, int accessId, LocalDate dateToBeCancelled) {
        TreeMap<Integer, ArrayList<LocalDate>> reservationDetails = Database.getInstance().reservationDetails.get(venueCode);
        ArrayList<LocalDate> bookedDates = reservationDetails.get(accessId);
        bookedDates.remove(dateToBeCancelled);
        reservationDetails.put(accessId, bookedDates);
        Database.getInstance().reservationDetails.put(venueCode, reservationDetails);
        System.out.println(Database.getInstance().reservationDetails.get(venueCode));
        System.out.println("Success! You have successfully cancelled the requested date");

        ((Representative) currentUser).removeReservationDetails(accessId, dateToBeCancelled, dateToBeCancelled);
    }

    public void changeVenue(int oldVenueCode, int accessId, int newVenueCode) {
        Map<Integer, TreeMap<Integer, ArrayList<LocalDate>>> reservationDetails = Database.getInstance().reservationDetails;
        ArrayList<LocalDate> reservedDates = (reservationDetails.get(oldVenueCode)).get(accessId);

        // below line should be handled for no elements in reservedDates
        LocalDate from = reservedDates.get(0), to = reservedDates.get(reservedDates.size() - 1);

        // if reservation fails, should not call the
        reserveVenue(newVenueCode, from, to);
        cancelVenue(oldVenueCode, accessId);
    }

    //functions

    // do not generate id that is already generated
    private int generateRandomNumber() {
        return new Random().nextInt(Integer.MAX_VALUE - 10);
    }

}