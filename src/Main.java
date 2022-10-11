import java.awt.*;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        VenueManager venueManager = new VenueManager();

        // logging in the user
        //venueManager.authenticate();

        while(true) {
            System.out.println(Choices.userChoices);

            switch(sc.nextInt()){
                case 0:
                    System.out.println(Choices.displayVenueChoices);
                    switch (sc.nextInt()){
                        case 1:
                            venueManager.displayVenueDetails();
                            break;
                        case 2:
                            venueManager.displayVenueDetails("Conference");
                            break;
                        case 3:
                            venueManager.displayVenueDetails("Hands-On training");
                            break;
                        case 4:
                            venueManager.displayVenueDetails("Auditorium");
                            break;
                    }
                    break;
                case 2:
                    System.out.println();
                default:
                    System.out.println("Oops! Invalid Choice, please choose a valid one\n");
            }
        }

//        Venue confRoom = new ConferenceRoom();
//        System.out.println(confRoom.getVenueDetails());
    }





}