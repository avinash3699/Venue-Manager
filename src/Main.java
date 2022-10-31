import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Map;
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
                                    showProfile();
                                    break;
                                case 7:
                                    managePersonalDetailsModification();
                                    break;
                                case 8:
                                    logout();
                                    break mainLoop;
                                case 9:
                                    managerViewUserDetails();
                                    break;
                                case 10:
                                    manageUserAddition();
                                    break;
                                case 11:
                                    manageUserDeletion();
                                    break;
                                case 12:
                                    manageVenueAddition();
                                case 13:
                                    manageVenueUpdate();
                                    break;
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

    private static void managePersonalDetailsModification() {
        System.out.println("\n---------");
        System.out.println(Choices.modifyPersonalDetailsChoices);
        switch (getIntegerInput("Enter choice: ")){
            case 1:
                String newEmailId = getStringInput("Enter new Email Id: ");
                System.out.println(
                    (currentUser.setEmailId(newEmailId))?
                    "Email Id modified successfully!":
                    "Cannot modify Email id. Please try again"
                );
                break;
            case 2:
                String newPhoneNumber = getStringInput("Enter new Phone Number: ");
                System.out.println(
                    (currentUser.setPhoneNumber(newPhoneNumber))?
                    "Phone Number modified successfully!":
                    "Cannot modify Phone Number. Please try again"
                );
                break;
            default:
                System.out.println("You have entered an invalid username or password. Please try again\n");
        }
    }

    private static void showProfile() {
        System.out.println("\n---------");
        System.out.println("User Profile");
        Map<String, String> personalDetails = currentUser.getPersonalDetails();
        for (String key : personalDetails.keySet()) {
            System.out.printf("%s: %s\n", key, personalDetails.get(key));
        }
        System.out.println();
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

    private static void manageVenueAddition() {
        if(currentUser instanceof Admin){

        }
        else
            System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
    }

    private static void manageVenueUpdate() {
        if(currentUser instanceof Admin){

        }
        else
            System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
    }

    private static void managerViewUserDetails() {
        if(currentUser instanceof Admin){
            String username;
            while(true) {
                username = getStringInput("Enter Username: ");
                if(venueManager.checkUserNameExistence(username))
                    break;
                else
                    System.out.println("Username doesn't exists. Please try again\n");
            }
            System.out.println(Choices.viewUserDetailsChoices);
            switch(getIntegerInput("Enter choice: ")){
                case 1:
                    Map<String, String> personalDetails = ((Admin) currentUser).getOtherUserPersonalDetails(username);
                    for (String key : personalDetails.keySet()) {
                        System.out.printf("%s: %s\n", key, personalDetails.get(key));
                    }
                    System.out.println();
                    break;
                case 2:
                    Map registrationDetails = ((Admin) currentUser).getOtherUserRegistrationDetails(username);
                    System.out.println(registrationDetails);
                    break;
                default:
                    System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
            }
        }
        else
            System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
    }

    private static void manageUserAddition() {
        if(currentUser instanceof Admin){
            String username = getStringInput("Enter Username: "),
                   password = getStringInput("Enter password: "),
                   emailId = getStringInput("Enter Email ID: "),
                   phoneNumber = getStringInput("Enter phone number: ");
            System.out.println(
                    ((Admin) currentUser).addUser(username, password, emailId, phoneNumber)?
                    "The user is added successfully!":
                    "Add User failed. Please try again"
            );
        }
        else
            System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
    }

    private static void manageUserDeletion() {
        if(currentUser instanceof Admin){
            String username;
            while(true) {
                username = getStringInput("Enter the username of the user to be deleted: ");
                if(venueManager.checkUserNameExistence(username)) {
                    if (username.equals(currentUser.getUsername()))
                        System.out.println("You cannot remove current user. Please try again\n");
                    else
                        break;
                }
                else
                    System.out.println("Username doesn't exists. Please try again\n");
            }
            char confirmation1 = getStringInput("Are you sure? You want to remove " + username + "? (Y/N): ").charAt(0), confirmation2;
            if(confirmation1 == 'Y' || confirmation1 == 'y'){
                confirmation2 = getStringInput("All the details of the User will be deleted. Are you sure? (Y/N): ").charAt(0);
                if(confirmation2 == 'Y' || confirmation2 == 'y'){
                    System.out.println(
                            ((Admin) currentUser).removeUser(username)?
                            "User '" + username + "' successfully removed!":
                            "Remove User failed. Please try again"
                    );
                }
            }
        }
        else
            System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
    }

    private static void logout() {
        currentUser = null;
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
                        venueManager.cancelVenue(venueCode, accessId, dateToBeCancelled, dateToBeCancelled);
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
                        venueManager.reserveVenue(VenueType.CONFERENCE, from, to);
                        break;
                    case 2:
                        venueManager.reserveVenue(VenueType.HANDS_ON_TRAINING, from, to);
                        break;
                    case 3:
                        venueManager.reserveVenue(VenueType.AUDITORIUM, from, to);
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

        ArrayList<Integer> availableVenueCodes;
        VenueType venueType;

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
                        availableVenueCodes = venueManager.checkAvailability(from, to);
                        printVenuesAvailability(availableVenueCodes);
                        break;
                    case 2:
                        venueType = VenueType.CONFERENCE;
                        availableVenueCodes = venueManager.checkAvailability(venueType, from, to);
                        printVenuesAvailability(availableVenueCodes, venueType);
                        break;
                    case 3:
                        venueType = VenueType.HANDS_ON_TRAINING;
                        availableVenueCodes = venueManager.checkAvailability(venueType, from, to);
                        printVenuesAvailability(availableVenueCodes, venueType);
                        break;
                    case 4:
                        venueType = VenueType.AUDITORIUM;
                        availableVenueCodes = venueManager.checkAvailability(venueType, from, to);
                        printVenuesAvailability(availableVenueCodes, venueType);
                        break;
                    case 5:
                        venueCode = getVenueCodeInput("Enter Venue Code: ");
                        System.out.println(
                            "\n" +
                            Database.getInstance().getVenueNameFromCode(venueCode) +
                            (
                                venueManager.checkAvailability(venueCode, from, to)?
                                ": Available":
                                ": Not Available"
                            )
                        );
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

    // function to print the availability of 'all the venues' when the available venue codes are given
    // delegates the function to VenueManager
    private static void printVenuesAvailability(ArrayList<Integer> availableVenueCodes) {
        venueManager.printVenuesAvailability(availableVenueCodes);
    }

    // function to print the availability of all the venues of the 'given type' when the available venue codes are given
    // delegates the function to VenueManager
    private static void printVenuesAvailability(ArrayList<Integer> availableVenueCodes, VenueType type) {
        venueManager.printVenuesAvailability(availableVenueCodes, type);
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
                    venueManager.displayVenueDetails(VenueType.CONFERENCE);
                    break;
                case 3:
                    System.out.println("\n---------");
                    venueManager.displayVenueDetails(VenueType.HANDS_ON_TRAINING);
                    break;
                case 4:
                    System.out.println("\n---------");
                    venueManager.displayVenueDetails(VenueType.AUDITORIUM);
                    break;
                case 5:
                    venueCode = getVenueCodeInput("Enter Venue Code: ");
                    venueManager.displayVenueDetails(venueCode);
                    break;
                case 6:
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

                boolean isPastDate = isPastDate(parsedDate);
                if(isPastDate) {
                    System.out.println("OOPs! You have entered a past date. Please enter a valid one");
                    continue;
                }

                boolean isCurrentDate = isCurrentDate(parsedDate);
                if(isCurrentDate){
                    System.out.println("OOPs! You cannot enter current date. Please enter again");
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

    // function to check whether the entered date is past date or not
    // returns 'true', if past date. else, 'false'
    private static boolean isPastDate(LocalDate inputDate) {
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
        return inputDate.isBefore(localDate);
    }

    // function to check whether the entered date is current date or not
    // returns 'true', if current date. else, 'false'
    private static boolean isCurrentDate(LocalDate inputDate) {
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
        return inputDate.isEqual(localDate);
    }

}