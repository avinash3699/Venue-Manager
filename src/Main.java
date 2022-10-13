import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        VenueManager venueManager = new VenueManager();

        // logging in the user
        //venueManager.authenticate();
//        System.out.println(Database.getInstance().venues);

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
                case 1:
                    String fromDate = VenueManager.getStringInput("From Date (DD-MM-YYYY): "),
                           toDate = VenueManager.getStringInput("To Date (DD-MM-YYYY): ");
                    LocalDate from = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                              to = LocalDate.parse(toDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    System.out.println(from + " " + to);
                    System.out.println(Choices.checkAvailabilityChoices);
                    switch (sc.nextInt()){
                        case 1:
                            System.out.println(venueManager.checkAvailability(from, to));
                            break;
                        case 2:
                            System.out.println(venueManager.checkAvailability("Conference", from, to));
                            break;
                        case 3:
                            System.out.println(venueManager.checkAvailability("Hands-On training", from, to));
                            break;
                        case 4:
                            System.out.println(venueManager.checkAvailability("Auditorium", from, to));
                            break;
                        case 5:
                            int venueCode = Integer.parseInt(VenueManager.getStringInput("Enter Venue Code: "));
                            System.out.println(venueManager.checkAvailability(venueCode, from, to));
                            break;
                    }
                    break;
                case 2:
                    fromDate = VenueManager.getStringInput("From Date (DD-MM-YYYY): ");
                    toDate = VenueManager.getStringInput("To Date (DD-MM-YYYY): ");
                    from = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    to = LocalDate.parse(toDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    System.out.println(Choices.reserveHallChoices);
                    switch (sc.nextInt()){
                        case 1:
                            venueManager.reserveVenue("Conference", from, to);
                            break;
                        case 2:
                            venueManager.reserveVenue("Hands-On training", from, to);
                            break;
                        case 3:
                            venueManager.reserveVenue("Auditorium", from, to);
                            break;
                        case 4:
                            int venueCode = Integer.parseInt(VenueManager.getStringInput("Enter Venue Code: "));
                            venueManager.reserveVenue(venueCode, from, to);
                    }
                    break;
                case 3:
                    int venueCode = Integer.parseInt(VenueManager.getStringInput("Enter Venue Code: ")),
                        accessId = Integer.parseInt(VenueManager.getStringInput("Enter Access Id: "));
                    System.out.println(Choices.cancelHallChoices);
                    switch (sc.nextInt()){
                        case 1:
                            venueManager.cancelVenue(venueCode, accessId);
                            break;
                        case 2:
                            fromDate = VenueManager.getStringInput("From Date (DD-MM-YYYY): ");
                            toDate = VenueManager.getStringInput("To Date (DD-MM-YYYY): ");
                            from = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                            to = LocalDate.parse(toDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                            venueManager.cancelVenue(venueCode, accessId, from, to);
                            break;
                        case 3:
                            String date = VenueManager.getStringInput("Enter the date to be cancelled(DD-MM-YYYY): ");
                            LocalDate dateToBeCancelled = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                            venueManager.cancelVenue(venueCode, accessId, dateToBeCancelled);
                            break;
                    }
                    break;
                case 4:
                    int oldVenueCode = Integer.parseInt(VenueManager.getStringInput("Enter Old Venue Code: "));
                    accessId = Integer.parseInt(VenueManager.getStringInput("Enter Access Id: "));
                    int newVenueCode = Integer.parseInt(VenueManager.getStringInput("Enter New Venue Code: "));
                    venueManager.changeVenue(oldVenueCode, accessId, newVenueCode);
                    break;
                default:
                    System.out.println("Oops! Invalid Choice, please choose a valid one\n");
            }
        }

//        Venue confRoom = new ConferenceRoom();
//        System.out.println(confRoom.getVenueDetails());
    }

}