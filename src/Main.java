import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public class Main {

    static Scanner sc;
    static VenueManager venueManager;
    static int choice, accessId, venueCode;
    static LocalDate from, to;
    static LocalDate[] dates;
    static User currentUser;

    public static void main(String[] args) {

        sc = new Scanner(System.in);

        venueManager = new VenueManager();

//         logging in the user
        while(true) {

            boolean isLoginSuccessful = false;

            System.out.println(Choices.loginExitChoice);
            switch (getIntegerInput("Enter choice: ")) {
                case 1:

                    isLoginSuccessful = login();

                    if(isLoginSuccessful){
                        mainLoop:
                        while(true) {
                            System.out.println("\n---------");

                            System.out.println(Choices.userChoices);

                            if(currentUser instanceof Admin){
                                System.out.println(Choices.adminChoices);
                            }

                            choice = getIntegerInput("Enter choice: ");
                            switch (choice) {

                                case 0:
                                    manageVenueDetailsDisplay();
                                    break;
                                case 1:
                                    manageVenueAvailabilityChecking();
                                    break;
                                case 2:
                                    manageVenueReservation();
                                    break;
                                case 3:
                                    manageVenueCancellation();
                                    break;
                                case 4:
                                    manageVenueChange();
                                    break;
                                case 5:
                                    getReservationDetails();
                                    break;
                                case 6:
                                    logout();
                                    break mainLoop;
                                case 7:
                                    manageUserAddition();
                                    break;
                                case 8:
                                    viewUsers();
                                    break;
                                case 9:
                                    manageVenueUpdate();
                                default:
                                    System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
                            }
                        }
                    }
                    continue;
                case 2:
                    System.exit(1);
            }
            break;
        }

    }

    private static boolean login() {
        boolean isLoginSuccessful;
        String username = getStringInput("Enter username: "),
                password = getStringInput("Enter password: ");
        if ((currentUser = venueManager.authenticate(username, password)) != null) {
            System.out.println("\n---------");
            System.out.println("Authentication Successful!!\n");
            System.out.println("Welcome " + currentUser.getUsername());
            isLoginSuccessful = true;
        } else {
            System.out.println("\n---------");
            System.out.println("You have entered an invalid username or password. Please try again\n");
            isLoginSuccessful = false;
        }
        return isLoginSuccessful;
    }

    private static void manageVenueUpdate() {
        if(currentUser instanceof Admin){

        }
        else
            System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
    }

    private static void viewUsers() {
        if(currentUser instanceof Admin){

        }
        else
            System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
    }

    private static void manageUserAddition() {
        if(currentUser instanceof Admin){

        }
        else
            System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
    }

    private static void logout() {
        System.out.println("\n---------");
        System.out.println("Logging out!\n");
    }

    private static void getReservationDetails() {
        System.out.println(((Representative)currentUser).getReservationDetails());
    }

    private static void manageVenueChange() {
        accessId = getAccessIdInput();
        int oldVenueCode = getVenueCodeInput("Enter Venue Code: "),
                newVenueCode = getVenueCodeInput("Enter New Venue Code: ");
        venueManager.changeVenue(oldVenueCode, accessId, newVenueCode);
    }

    private static void manageVenueCancellation() {
        while(true) {
            accessId = getAccessIdInput();
            venueCode = getVenueCodeInput("Enter Venue Code: ");

            innerLoop:
            while (true) {
                System.out.println("\n---------");
                System.out.println(Choices.cancelVenueChoices);

                choice = getIntegerInput("Enter choice: ");
                switch (choice) {
                    case 1:
                        venueManager.cancelVenue(venueCode, accessId);
                        break;
                    case 2:
                        dates = getFromToDates();
                        from = dates[0];
                        to = dates[1];

                        venueManager.cancelVenue(venueCode, accessId, from, to);
                        break;
                    case 3:
                        LocalDate dateToBeCancelled = getDateInput("Enter the date to be cancelled(DD-MM-YYYY): ");
                        venueManager.cancelVenue(venueCode, accessId, dateToBeCancelled);
                        break;
                    case 4:
                        break innerLoop;
                    case 5:
                        return;
                    default:
                        System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
                }
            }
        }
    }

    private static void manageVenueReservation() {

        while(true) {
            dates = getFromToDates();
            from = dates[0];
            to = dates[1];

            innerLoop:
            while (true) {
                System.out.println("\n---------");
                System.out.printf("From: %s\nTo: %s\n\n", from, to);

                System.out.println(Choices.reserveVenueChoices);

                choice = getIntegerInput("Enter choice: ");
                switch (choice) {
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
                        venueCode = getVenueCodeInput("Enter Venue Code: ");
                        venueManager.reserveVenue(venueCode, from, to);
                        break;
                    case 5:
                        break innerLoop;
                    case 6:
                        return;
                    default:
                        System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
                }
            }
        }
    }

    private static void manageVenueAvailabilityChecking() {
        while(true) {
            dates = getFromToDates();
            from = dates[0];
            to = dates[1];

            innerLoop:
            while (true) {
                System.out.println("\n---------");
                System.out.printf("From: %s\nTo: %s\n\n", from, to);
                System.out.println(Choices.checkAvailabilityChoices);

                choice = getIntegerInput("Enter choice: ");
                switch (choice) {
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
                        venueCode = getVenueCodeInput("Enter Venue Code: ");
                        System.out.println(venueManager.checkAvailability(venueCode, from, to));
                        break;
                    case 6:
                        break innerLoop;
                    case 7:
                        return;
                    default:
                        System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
                }
            }
        }
    }

    private static void manageVenueDetailsDisplay(){
        while (true) {
            System.out.println("\n---------");
            System.out.println(Choices.displayVenueChoices);

            int choice = getIntegerInput("Enter choice: ");

            switch (choice) {
                case 1:
                    System.out.println("\n---------");
                    venueManager.displayVenueDetails();
                    break;
                case 2:
                    System.out.println("\n---------");
                    venueManager.displayVenueDetails("Conference");
                    break;
                case 3:
                    System.out.println("\n---------");
                    venueManager.displayVenueDetails("Hands-On training");
                    break;
                case 4:
                    System.out.println("\n---------");
                    venueManager.displayVenueDetails("Auditorium");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("\n---------");
                    System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
            }
        }
    }

    // function to get string input with a hint text
    static String getStringInput(String text){
        System.out.print(text);
        return new Scanner(System.in).nextLine();
    }

    private static int getAccessIdInput() {
        int accessId;
        System.out.println("\n---------");

        while(true) {
            accessId = getIntegerInput("Enter Access Id: ");
            if((Database.getInstance().getAccessIds()).contains(accessId))
                break;
            else
            {
                System.out.println("\n---------");
                System.out.println("OOPs! Invalid Access Id. Please try again!\n");
            }
        }
        return accessId;
    }

    private static int getVenueCodeInput(String hintText) {
        int venueCode;
        System.out.println("\n---------");

        while(true) {
            venueCode = getIntegerInput(hintText);
            if(venueCode <= Database.getInstance().getVenuesCount())
                break;
            else{
                System.out.println("\n---------");
                System.out.println("OOPs! Invalid Venue Code. Please try again!\n");
            }
        }
        return venueCode;
    }

    private static int getIntegerInput(String hintText) {

        int choice;

        while (true) {
            try {
                choice = Integer.parseInt(getStringInput(hintText));
                break;
            } catch (NumberFormatException e) {
//                e.printStackTrace();
                System.out.println("\n---------");
                System.out.println("OOPs! Invalid Input, please enter a valid number\n");
            }
        }

        return choice;
    }

    private static LocalDate getDateInput(String hintText){
        String date;
        LocalDate parsedDate;
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        while(true) {
            System.out.println("\n---------");
            date = getStringInput(hintText);
            try{
                // ResolverStyle.STRICT is used to throw exception for 29th and 30th of February, considering leap year
                // https://gist.github.com/MenoData/da0aa200b8df31a1d308ad61587a94e6
                parsedDate = LocalDate.parse(date, pattern.withResolverStyle(ResolverStyle.STRICT));
                boolean isPastDate = isDatePast(parsedDate);
                if(isPastDate) {
                    System.out.println("OOPs! You have entered a past date. Please enter a valid one");
                    continue;
                }
                break;
            }
            catch (DateTimeParseException e){
                System.out.println("OOPs! Invalid Date, please enter a valid date");
            }
        }
        return parsedDate;
    }

    private static LocalDate[] getFromToDates(){
        while(true) {
            LocalDate from = getDateInput("From Date (DD-MM-YYYY): "),
                      to = getDateInput("To Date (DD-MM-YYYY): ");

            if(to.isBefore(from))
                System.out.println("You have enter a 'To date' less than 'From to'. Please try again");
            else if(to.isAfter(from.plusDays(10)))
                System.out.println("Your from and to date cannot be not be more than 10 days");
            else
                return new LocalDate[]{
                        from,
                        to
                };
        }
    }

    private static boolean isDatePast(LocalDate inputDate) {
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
        return inputDate.isBefore(localDate);
    }

}