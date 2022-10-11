import java.util.Map;
import java.util.Scanner;

public class VenueManager {

    //methods

    // method to get username and password from the user and authenticate it
    void authenticate(){

        while(true) {

            String username = getStringInput("Enter username: ");
            String password = getStringInput("Enter password: ");

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

    //functions

    // function to get string input with a hint text
    private static String getStringInput(String text){
        System.out.print(text);
        return new Scanner(System.in).nextLine();
    }

}
