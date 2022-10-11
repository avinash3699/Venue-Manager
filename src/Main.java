import java.util.Scanner;

public class Main {

    static Scanner sc;

    public static void main(String[] args) {

        sc = new Scanner(System.in);

        VenueManager venueManager = new VenueManager();

        // logging in the user
        //venueManager.authenticate();

        while(true) {
            System.out.println(Choices.userChoices);

            switch(sc.nextInt()){
                case 0:
                    venueManager.displayVenueDetails();
                    break;
                default:
                    System.out.println("Oops! Invalid Choice, please choose a valid one\n");
            }
        }

//        Venue confRoom = new ConferenceRoom();
//        System.out.println(confRoom.getVenueDetails());
    }





}