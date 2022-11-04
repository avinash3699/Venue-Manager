import core.manager.VenueManager;
import core.venue.Reservation;
import core.venue.VenueType;
import core.user.Admin;
import core.user.User;
import database.Database;
import helper.Choices;
import helper.InputHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
                                    manageChangeUserPassword();
                                    break;
                                case 11:
                                    manageUserAddition();
                                    break;
                                case 12:
                                    manageUserDeletion();
                                    break;
                                case 13:
                                    manageVenueAddition();
                                case 14:
                                    manageVenueUpdate();
                                    break;
                                default:
                                    System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
                            }
                        }
                    }
                    continue;
                case 2:
                    System.out.println("Exiting....");
                    System.exit(1);
                    break;
                default:
                    System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
                    continue;
            }
            break;
        }

    }

    private static void manageChangeUserPassword() {
        if(currentUser instanceof Admin) {
            String username;
            while (true) {
                username = InputHelper.getStringInput("Enter Username: ");
                if (venueManager.checkUserNameExistence(username))
                    break;
                else
                    System.out.println("Username doesn't exists. Please try again\n");
            }

            String newPassword = InputHelper.getStringInput("Enter new password: ");
            if (venueManager.changeUserPassword(username, newPassword))
                System.out.println("Password changed successfully!");
            else{
                System.out.println("OOPs. Change Password failed. Please try again");
            }

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
            case 3:
                String password = InputHelper.getStringInput("Enter your current password: ");
                User user = venueManager.authenticate(currentUser.getUsername(), password);
                if(user == null){
                    System.out.println("Sorry! Incorrect password. Please try again");
                    managePersonalDetailsModification();
                }
                else{
                    String newPassword = InputHelper.getStringInput("Enter new password: ");
                    if(venueManager.changeUserPassword(currentUser.getUsername(), newPassword)){
                        System.out.println("Password changed successfully!");
                    }
                }
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

            while (true) {
                System.out.println("\nUser: " + username);
                System.out.println(Choices.viewUserDetailsChoices);
                switch (InputHelper.getIntegerInput("Enter choice: ")) {
                    case 1:
                        Map<String, String> personalDetails = ((Admin) currentUser).getOtherUserPersonalDetails(username);
                        for (String key : personalDetails.keySet()) {
                            System.out.printf("%s: %s\n", key, personalDetails.get(key));
                        }
                        System.out.println();
                        break;
                    case 2:
                        List<Reservation> reservationDetails = ((Admin) currentUser).getOtherUserRegistrationDetails(username);
                        if(reservationDetails.size() == 0){
                            System.out.println("The user doesn't have any current reservations.");
                            return;
                        }
                        for (Reservation reservation : reservationDetails) {
                            for (String key : reservation.getMap().keySet()) {
                                System.out.println(key + ": " + reservation.getMap().get(key));
                            }
                            System.out.println();
                        }
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
                }
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
//            char confirmation1 = InputHelper.getStringInput("Are you sure? You want to remove " + username + "? (Y/N): ").charAt(0), confirmation2;
            char confirmation1 = InputHelper.getConfirmationInput("Are you sure? You want to remove " + username + "? (Y/N): "), confirmation2;
            if(confirmation1 == 'Y' || confirmation1 == 'y'){
//                confirmation2 = InputHelper.getStringInput("All the details of the User will be deleted. Are you sure? (Y/N): ").charAt(0);
                confirmation2 = InputHelper.getConfirmationInput("All the details of the User will be deleted. Are you sure? (Y/N): ");
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
        List<Reservation> reservationDetails = currentUser.getReservationDetails();
        if(reservationDetails.size() == 0){
            System.out.println("You don't have any current reservations.");
            return;
        }
        for(Reservation reservation: reservationDetails){
            for(String key: reservation.getMap().keySet()){
                System.out.println(key + ": " + reservation.getMap().get(key));
            }
            System.out.println();
        }
    }

    private static void manageVenueChange() {
        accessId = InputHelper.getIntegerInput("\nEnter Access Id: ");

        Reservation currentReservationDetails = venueManager.getReservationDetails(currentUser.getUsername(), accessId);
        if (currentReservationDetails == null){
            System.out.println("\nOOPs! Access ID invalid. Please try again");
            return;
        }
        else{
            System.out.println("\nYour reservation details for given access id:");
            for(String key: currentReservationDetails.getMap().keySet()){
                System.out.println(key + ": " + currentReservationDetails.getMap().get(key));
            }
        }

        int newVenueCode = InputHelper.getVenueCodeInput("Enter New Venue Code: ");
        Reservation newReservationDetails = currentUser.changeVenue(currentReservationDetails.getVenueCode(), accessId, newVenueCode);
        if(newReservationDetails == null){
            System.out.println("Sorry! The venue you requested is already reserved");
        }
        else{
            System.out.println("Hurray!! You have changed your venue successfully.\nNew Venue: " + newReservationDetails.getVenueCode());
            System.out.println("Your access ID is: " + newReservationDetails.getAccessId());
        }
    }

    private static void manageVenueCancellation() {

        boolean isCancelled;

        while(true) {
            accessId = InputHelper.getIntegerInput("\nEnter Access Id: ");

            Reservation reservationDetails = venueManager.getReservationDetails(currentUser.getUsername(), accessId);
            if (reservationDetails == null){
                System.out.println("\nOOPs! Access ID invalid. Please try again");
                continue;
            }
            else{
                System.out.println("\nYour reservation details for given access id:");
                for(String key: reservationDetails.getMap().keySet()){
                    System.out.println(key + ": " + reservationDetails.getMap().get(key));
                }
            }

//            char confirmation1 = InputHelper.getStringInput("Are you sure? You want to cancel the venue? (Y/N): ").charAt(0);
            char confirmation1 = InputHelper.getConfirmationInput("Are you sure? You want to cancel the venue? (Y/N): "), confirmation2;
            if(confirmation1 == 'Y' || confirmation1 == 'y'){}
            else{
                System.out.println("Cancellation process stopped!");
                return;
            }

//            venueCode = InputHelper.getVenueCodeInput("Enter Venue Code: ");
            venueCode = reservationDetails.getVenueCode();

            innerLoop:
            while (true) {
                System.out.println("\n---------");
                System.out.println(Choices.cancelVenueChoices);

                choice = InputHelper.getIntegerInput("Enter choice: ");
                switch (choice) {
                    case 1:

//                        confirmation2 = InputHelper.getStringInput("Are you sure? You want to cancel the venue? (Y/N): ").charAt(0);
                        confirmation2 = InputHelper.getConfirmationInput("Are you sure? You want to cancel the venue? (Y/N): ");
                        if(confirmation2 == 'Y' || confirmation2 == 'y') {
                            isCancelled = currentUser.cancelVenue(venueCode, accessId);

                            if (isCancelled)
                                System.out.println("Success! You have successfully cancelled the venue");
                            else
                                System.out.println("Sorry! Cancellation failed. Please try again");
                        }
                        else{
                            System.out.println("Cancellation process stopped!");
                            return;
                        }
                        return;
                    case 2:
                        dates = InputHelper.getFromToDates();
                        from = dates[0];
                        to = dates[1];

                        // it checks whether the entered from and to dates are in the reserved dates of the reservation object
                        // if any of the date is not present cancellation process is stopped
                        boolean isValidDates = venueManager.areValidDates(accessId, from, to, currentUser.getUsername());

                        if(isValidDates){}
                        else{
                            System.out.println("All/some dates are not in your reservation. Please check your reservation details and try again");
                            return;
                        }

//                        confirmation2 = InputHelper.getStringInput("Are you sure? You want to cancel the venue? (Y/N): ").charAt(0);
                        confirmation2 = InputHelper.getConfirmationInput("Are you sure? You want to cancel the venue? (Y/N): ");
                        if(confirmation2 == 'Y' || confirmation2 == 'y') {
                            isCancelled = currentUser.cancelVenue(venueCode, accessId, from, to);

                            if(isCancelled)
                                System.out.println("Success! You have successfully cancelled the mentioned dates");
                            else
                                System.out.println("Sorry! Cancellation failed. Please try again");
                        }
                        else{
                            System.out.println("Cancellation process stopped!");
                            return;
                        }

                        return;
                    case 3:
                        LocalDate dateToBeCancelled = InputHelper.getDateInput("Enter the date to be cancelled(DD-MM-YYYY): ");

                        boolean isValidDate = venueManager.isValidDate(accessId, dateToBeCancelled, currentUser.getUsername());

                        if(isValidDate){}
                        else{
                            System.out.println("The date you entered is not in your reservation. Please check your reservation details and try again");
                            return;
                        }

//                        confirmation2 = InputHelper.getStringInput("Are you sure? You want to cancel the venue? (Y/N): ").charAt(0);
                        confirmation2 = InputHelper.getConfirmationInput("Are you sure? You want to cancel the venue? (Y/N): ");
                        if(confirmation2 == 'Y' || confirmation2 == 'y') {
                            isCancelled = currentUser.cancelVenue(venueCode, accessId, dateToBeCancelled, dateToBeCancelled);

                            if(isCancelled)
                                System.out.println("Success! You have successfully cancelled the requested date");
                            else
                                System.out.println("Sorry! Cancellation failed. Please try again");
                        }
                        else{
                            System.out.println("Cancellation process stopped!");
                            return;
                        }

                        return;
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
            Reservation reservationDetails;
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
                        reservationDetails = currentUser.reserveVenue(VenueType.CONFERENCE, from, to);
                        if(reservationDetails == null){
                            System.out.println("Sorry! No Venues Available based on your request");
                        }
                        else{
                            System.out.println("Hurray!! You have reserved your venue successfully.\nVenue: " + reservationDetails.getVenueCode());
                            System.out.println("Your access ID is: " + reservationDetails.getAccessId());
                        }
                        break;
                    case 2:
                        reservationDetails = currentUser.reserveVenue(VenueType.HANDS_ON_TRAINING, from, to);
                        if(reservationDetails == null){
                            System.out.println("Sorry! The venue you requested is already reserved");
                        }
                        else{
                            System.out.println("Hurray!! You have reserved your venue successfully.\nVenue: " + reservationDetails.getVenueCode());
                            System.out.println("Your access ID is: " + reservationDetails.getAccessId());
                        }
                        break;
                    case 3:
                        reservationDetails = currentUser.reserveVenue(VenueType.AUDITORIUM, from, to);
                        if(reservationDetails == null){
                            System.out.println("Sorry! The venue you requested is already reserved");
                        }
                        else{
                            System.out.println("Hurray!! You have reserved your venue successfully.\nVenue: " + reservationDetails.getVenueCode());
                            System.out.println("Your access ID is: " + reservationDetails.getAccessId());
                        }
                        break;
                    case 4:
                        venueCode = InputHelper.getVenueCodeInput("Enter Venue Code: ");
                        reservationDetails = currentUser.reserveVenue(venueCode, from, to);
                        if(reservationDetails == null){
                            System.out.println("Sorry! The venue you requested is already reserved");
                        }
                        else{
                            System.out.println("Hurray!! You have reserved your venue successfully.\nVenue: " + reservationDetails.getVenueCode());
                            System.out.println("Your access ID is: " + reservationDetails.getAccessId());
                        }
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
                        availableVenueCodes = currentUser.checkAvailability(from, to);
                        printVenuesAvailability(availableVenueCodes);
                        break;
                    case 2:
                        venueType = VenueType.CONFERENCE;
                        availableVenueCodes = currentUser.checkAvailability(venueType, from, to);
                        printVenuesAvailability(availableVenueCodes, venueType);
                        break;
                    case 3:
                        venueType = VenueType.HANDS_ON_TRAINING;
                        availableVenueCodes = currentUser.checkAvailability(venueType, from, to);
                        printVenuesAvailability(availableVenueCodes, venueType);
                        break;
                    case 4:
                        venueType = VenueType.AUDITORIUM;
                        availableVenueCodes = currentUser.checkAvailability(venueType, from, to);
                        printVenuesAvailability(availableVenueCodes, venueType);
                        break;
                    case 5:
                        venueCode = InputHelper.getVenueCodeInput("Enter Venue Code: ");
                        System.out.println(
                            "\n" +
                            Database.getInstance().getVenueNameFromCode(venueCode) +
                            (
                                currentUser.checkAvailability(venueCode, from, to)?
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
        currentUser.printVenuesAvailability(availableVenueCodes);
    }

    // function to print the availability of all the venues of the 'given type' when the available venue codes are given
    // delegates the function to VenueManager
    private static void printVenuesAvailability(ArrayList<Integer> availableVenueCodes, VenueType type) {
        currentUser.printVenuesAvailability(availableVenueCodes, type);
    }

    private static void manageVenueDetailsDisplay(){
        while (true) {
            System.out.println("\n---------");
            System.out.println(Choices.displayVenueChoices);

            int choice = InputHelper.getIntegerInput("Enter choice: ");

            switch (choice) {
                case 1:
                    System.out.println("\n---------");
                    currentUser.displayVenueDetails();
                    break;
                case 2:
                    System.out.println("\n---------");
                    currentUser.displayVenueDetails(VenueType.CONFERENCE);
                    break;
                case 3:
                    System.out.println("\n---------");
                    currentUser.displayVenueDetails(VenueType.HANDS_ON_TRAINING);
                    break;
                case 4:
                    System.out.println("\n---------");
                    currentUser.displayVenueDetails(VenueType.AUDITORIUM);
                    break;
                case 5:
                    venueCode = InputHelper.getVenueCodeInput("Enter Venue Code: ");
                    currentUser.displayVenueDetails(venueCode);
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