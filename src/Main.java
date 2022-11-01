import core.manager.VenueManager;
import core.venue.VenueType;
import core.user.Admin;
import core.user.Representative;
import core.user.User;
import database.Database;
import helper.Choices;
import helper.InputHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public final class Main {

    static private VenueManager venueManager;
    static private int choice, accessId, venueCode;
    static private LocalDate from, to;
    static private LocalDate[] dates;
    static private User currentUser;

    public static void main(String[] args) {

        venueManager = new VenueManager();

        while(true) {

            boolean isLoginSuccessful;

            System.out.println(Choices.loginExitChoice);
            switch (InputHelper.getIntegerInput("Enter choice: ")) {
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

                            choice = InputHelper.getIntegerInput("Enter choice: ");
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
        switch (InputHelper.getIntegerInput("Enter choice: ")){
            case 1:
                String newEmailId = InputHelper.getStringInput("Enter new Email Id: ");
                System.out.println(
                    (currentUser.setEmailId(newEmailId))?
                    "Email Id modified successfully!":
                    "Cannot modify Email id. Please try again"
                );
                break;
            case 2:
                String newPhoneNumber = InputHelper.getStringInput("Enter new Phone Number: ");
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
        String username = InputHelper.getStringInput("Enter username: "),
                password = InputHelper.getStringInput("Enter password: ");
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
                username = InputHelper.getStringInput("Enter Username: ");
                if(venueManager.checkUserNameExistence(username))
                    break;
                else
                    System.out.println("Username doesn't exists. Please try again\n");
            }
            System.out.println(Choices.viewUserDetailsChoices);
            switch(InputHelper.getIntegerInput("Enter choice: ")){
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
            String username = InputHelper.getStringInput("Enter Username: "),
                   password = InputHelper.getStringInput("Enter password: "),
                   emailId = InputHelper.getStringInput("Enter Email ID: "),
                   phoneNumber = InputHelper.getStringInput("Enter phone number: ");
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
                username = InputHelper.getStringInput("Enter the username of the user to be deleted: ");
                if(venueManager.checkUserNameExistence(username)) {
                    if (username.equals(currentUser.getUsername()))
                        System.out.println("You cannot remove current user. Please try again\n");
                    else
                        break;
                }
                else
                    System.out.println("Username doesn't exists. Please try again\n");
            }
            char confirmation1 = InputHelper.getStringInput("Are you sure? You want to remove " + username + "? (Y/N): ").charAt(0), confirmation2;
            if(confirmation1 == 'Y' || confirmation1 == 'y'){
                confirmation2 = InputHelper.getStringInput("All the details of the User will be deleted. Are you sure? (Y/N): ").charAt(0);
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
        accessId = InputHelper.getAccessIdInput();
        int oldVenueCode = InputHelper.getVenueCodeInput("Enter Venue Code: "),
            newVenueCode = InputHelper.getVenueCodeInput("Enter New Venue Code: ");
        venueManager.changeVenue(oldVenueCode, accessId, newVenueCode);
    }

    private static void manageVenueCancellation() {
        while(true) {
            accessId = InputHelper.getAccessIdInput();
            venueCode = InputHelper.getVenueCodeInput("Enter Venue Code: ");

            innerLoop:
            while (true) {
                System.out.println("\n---------");
                System.out.println(Choices.cancelVenueChoices);

                choice = InputHelper.getIntegerInput("Enter choice: ");
                switch (choice) {
                    case 1:
                        venueManager.cancelVenue(venueCode, accessId);
                        break;
                    case 2:
                        dates = InputHelper.getFromToDates();
                        from = dates[0];
                        to = dates[1];

                        venueManager.cancelVenue(venueCode, accessId, from, to);
                        break;
                    case 3:
                        LocalDate dateToBeCancelled = InputHelper.getDateInput("Enter the date to be cancelled(DD-MM-YYYY): ");
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
            dates = InputHelper.getFromToDates();
            from = dates[0];
            to = dates[1];

            innerLoop:
            while (true) {
                System.out.println("\n---------");
                System.out.printf("From: %s\nTo: %s\n\n", from, to);

                System.out.println(Choices.reserveVenueChoices);

                choice = InputHelper.getIntegerInput("Enter choice: ");
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
                        venueCode = InputHelper.getVenueCodeInput("Enter Venue Code: ");
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
            dates = InputHelper.getFromToDates();
            from = dates[0];
            to = dates[1];

            innerLoop:
            while (true) {
                System.out.println("\n---------");
                System.out.printf("From: %s\nTo: %s\n\n", from, to);
                System.out.println(Choices.checkAvailabilityChoices);

                choice = InputHelper.getIntegerInput("Enter choice: ");
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
                        venueCode = InputHelper.getVenueCodeInput("Enter Venue Code: ");
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

            int choice = InputHelper.getIntegerInput("Enter choice: ");

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
                    venueCode = InputHelper.getVenueCodeInput("Enter Venue Code: ");
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

}