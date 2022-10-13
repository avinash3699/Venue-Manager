import org.jetbrains.annotations.NotNull;

import javax.xml.crypto.Data;
import java.sql.Array;
import java.time.LocalDate;
import java.util.*;

public class VenueManager {

    //methods

    // method to get username and password from the user and authenticate it
    void authenticate(){

        while(true) {

            String username = getStringInput("Enter username: "),
                   password = getStringInput("Enter password: ");

            try {
                System.out.println("\nAuthenticating....");
                Authenticator.authenticate(
                        username,
                        password
                );
                System.out.println("Authentication Successful!!\n");
                break;
            } catch (AuthenticationException e) {
                // Displaying a common message would be of better security
                // https://stackoverflow.com/questions/14922130/which-error-message-is-better-when-users-entered-a-wrong-password
                System.out.println("You have entered an invalid username or password. Please try again\n");
            }
        }

    }

    // method to display the details of all the venues
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

    // method to display the details of venue based on the type (E.g. Conference, Auditorium)
    public void displayVenueDetails(String type) {
        for(Venue venue: Database.getInstance().venues.values()){
            Map<String, String> venueDetails = venue.getVenueDetails();
            if(!venueDetails.get("Hall Type").equals(type))
                continue;
            for(String key: venueDetails.keySet()){
                System.out.printf("%s: %s\n", key, venueDetails.get(key));
            }
            System.out.println();
        }
        System.out.println();
    }

    // to check the availability of all the venues for the given from data te end date
    // returns the name of all the available venues
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
//                availableVenues.add(Database.getInstance().venues.get(venueCode).hallName);
        }
        return availableVenues;
    }

    // to check the availability of specific type of venues for the given from data te end date
    // returns the name of the available venue from the given type
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
//                    availableVenues.add(Database.getInstance().venues.get(venueCode).hallName);
            }
        }
        return availableVenues;
    }

    // to check the availability of a specific venue for the given from data te end date
    // returns true if available else false
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

    // to reserve the venue of given type for the specified from date to end date
    public void reserveVenue(String type, LocalDate from, LocalDate to) {
        ArrayList<Integer> availableVenues = checkAvailability(type, from, to);
        if(availableVenues.size() != 0) {
            updateAvailability(availableVenues.get(0), from, to, generateRandomNumber());
            System.out.println("Hurray!! You have reserved your venue successfully. Venue: " + availableVenues.get(0));
        }
        else
            System.out.println("Sorry! No Venues Available based on your request");
    }

    // to reserve a specific venue using the venue code
    public void reserveVenue(int venueCode, LocalDate from, LocalDate to) {
        boolean available = checkAvailability(venueCode, from, to);
        if(available) {
            updateAvailability(venueCode, from, to, generateRandomNumber());
            System.out.printf("Hurray!! You have reserved your venue successfully. Venue: %s\n\n", venueCode);
        }
        else
            System.out.println("Sorry! The venue you requested is already reserved");
    }

    // to update the database with the details of the new venue reservation
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
    }

    // to cancel the reserved venue using the venue code and the unique access id
    public void cancelVenue(int venueCode, int accessId) {
        TreeMap<Integer, ArrayList<LocalDate>> reservationDetails = Database.getInstance().reservationDetails.get(venueCode);
        reservationDetails.remove(accessId);
        Database.getInstance().reservationDetails.put(venueCode, reservationDetails);
        System.out.println(Database.getInstance().reservationDetails.get(venueCode));
        System.out.println("Success! You have successfully cancelled the venue");
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
    }

    public void cancelVenue(int venueCode, int accessId, LocalDate dateToBeCancelled) {
        TreeMap<Integer, ArrayList<LocalDate>> reservationDetails = Database.getInstance().reservationDetails.get(venueCode);
        ArrayList<LocalDate> bookedDates = reservationDetails.get(accessId);
        bookedDates.remove(dateToBeCancelled);
        reservationDetails.put(accessId, bookedDates);
        Database.getInstance().reservationDetails.put(venueCode, reservationDetails);
        System.out.println(Database.getInstance().reservationDetails.get(venueCode));
        System.out.println("Success! You have successfully cancelled the requested date");
    }

    public void changeVenue(int oldVenueCode, int accessId, int newVenueCode) {
        Map<Integer, TreeMap<Integer, ArrayList<LocalDate>>> reservationDetails = Database.getInstance().reservationDetails;
        ArrayList<LocalDate> reservedDates = (reservationDetails.get(oldVenueCode)).get(accessId);
        LocalDate from = reservedDates.get(0), to = reservedDates.get(reservedDates.size() - 1);
        reserveVenue(newVenueCode, from, to);
        cancelVenue(oldVenueCode, accessId);
    }

    //functions

    // function to get string input with a hint text
    static String getStringInput(String text){
        System.out.print(text);
        return new Scanner(System.in).nextLine();
    }

    private int generateRandomNumber() {
        return new Random().nextInt(Integer.MAX_VALUE - 10);
    }

}