import javax.xml.crypto.Data;
import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

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

    public ArrayList<Integer> checkAvailability(LocalDate from, LocalDate to) {
        Map<Integer, TreeMap<LocalDate, String>> reservationDetails = Database.getInstance().reservationDetails;
        ArrayList<Integer> availableVenues = new ArrayList<>();
        for(int venueCode: reservationDetails.keySet()){
            boolean available = true;
            for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
                if(reservationDetails.get(venueCode).containsKey(date)){
                    available = false;
                    break;
                }
            }
            if(available)
                availableVenues.add(venueCode);
//                availableVenues.add(Database.getInstance().venues.get(venueCode).hallName);
        }
        return availableVenues;
    }

    public ArrayList<Integer> checkAvailability(String type, LocalDate from, LocalDate to) {
        Map<Integer, TreeMap<LocalDate, String>> reservationDetails = Database.getInstance().reservationDetails;
        ArrayList<Integer> availableVenues = new ArrayList<>();
        for(int venueCode: reservationDetails.keySet()){
            boolean available = true;
            if(Database.getInstance().venues.get(venueCode).type.equals(type)) {
                for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
                    if (reservationDetails.get(venueCode).containsKey(date)) {
                        available = false;
                        break;
                    }
                }
                if (available)
                    availableVenues.add(venueCode);
//                    availableVenues.add(Database.getInstance().venues.get(venueCode).hallName);
            }
        }
        return availableVenues;
    }

    public boolean checkAvailability(int venueCode, LocalDate from, LocalDate to) {
        TreeMap<LocalDate, String> reservationDetails = Database.getInstance().reservationDetails.get(venueCode);
        for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
            if(reservationDetails.containsKey(date)){
                return false;
            }
        }
        return true;
    }

    public void reserveVenue(String type, LocalDate from, LocalDate to) {
        ArrayList<Integer> availableVenues = checkAvailability(type, from, to);
        if(availableVenues.size() != 0) {
            updateAvailability(availableVenues.get(0), from, to);
            System.out.println("Congrats! You have reserved your venue successfully. Venue: " + availableVenues.get(0));
        }
        else
            System.out.println("Sorry! No Venues Available based on your request");
    }

    public void reserveVenue(int venueCode, LocalDate from, LocalDate to) {
        boolean available = checkAvailability(venueCode, from, to);
        if(available) {
            updateAvailability(venueCode, from, to);
            System.out.printf("Congrats! You have reserved your venue successfully. Venue: %s\n\n", venueCode);
        }
        else
            System.out.println("Sorry! The venue you requested is already reserved");
    }

    private void updateAvailability(int venueCode, LocalDate from, LocalDate to) {
        TreeMap<LocalDate, String> datesReserved = new TreeMap<>();
        for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
            datesReserved.put(date, "joker");
        }
        datesReserved.putAll(Database.getInstance().reservationDetails.get(venueCode));
        Database.getInstance().reservationDetails.put(venueCode, datesReserved);
        System.out.println(Database.getInstance().reservationDetails.get(venueCode));
    }

    //functions

    // function to get string input with a hint text
    static String getStringInput(String text){
        System.out.print(text);
        return new Scanner(System.in).nextLine();
    }

}
