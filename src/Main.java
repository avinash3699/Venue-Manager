import core.manager.VenueManager;
import core.venue.*;
import core.user.Admin;
import core.user.User;
import helper.Choices;
import helper.DateHelper;
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
                                    manageUserRemoval();
                                    break;
                                case 13:
                                    manageVenueAddition();
                                    break;
                                case 14:
                                    manageVenueUpdate();
                                    break;
                                case 15:
                                    manageVenueRemoval();
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

    // 0. Display different Venue Details
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
                    venueCode = InputHelper.getIntegerInput("\nEnter Venue Code: ");
                    if(venueManager.isValidVenueCode(venueCode)){}
                    else{
                        System.out.println("Invalid Venue Code. Please try again");
                        return;
                    }
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

    // 1. Check Venue Availability
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
                System.out.printf("From: %s\nTo: %s\n\n",
                        DateHelper.getFormattedDate(from),
                        DateHelper.getFormattedDate(to)
                );
                System.out.println(Choices.checkAvailabilityChoices);

                choice = InputHelper.getIntegerInput("Enter choice: ");
                switch (choice) {
                    case 1:
                        availableVenueCodes = currentUser.checkAvailability(from, to);
                        currentUser.printVenuesAvailability(availableVenueCodes);
                        break;
                    case 2:
                        venueType = VenueType.CONFERENCE;
                        availableVenueCodes = currentUser.checkAvailability(venueType, from, to);
                        currentUser.printVenuesAvailability(availableVenueCodes, venueType);
                        break;
                    case 3:
                        venueType = VenueType.HANDS_ON_TRAINING;
                        availableVenueCodes = currentUser.checkAvailability(venueType, from, to);
                        currentUser.printVenuesAvailability(availableVenueCodes, venueType);
                        break;
                    case 4:
                        venueType = VenueType.AUDITORIUM;
                        availableVenueCodes = currentUser.checkAvailability(venueType, from, to);
                        currentUser.printVenuesAvailability(availableVenueCodes, venueType);
                        break;
                    case 5:
                        venueCode = InputHelper.getVenueCodeInput("Enter Venue Code: ", venueManager.getVenuesCount());
                        boolean availability = currentUser.checkAvailability(venueCode, from, to);
                        venueManager.printVenueAvailability(venueCode, availability);
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

    // 2. Reserve Venue
    private static void manageVenueReservation() {

        while(true) {
            Reservation reservationDetails;
            dates = InputHelper.getFromToDates();
            from = dates[0];
            to = dates[1];

            innerLoop:
            while (true) {
                System.out.println("\n---------");
                System.out.printf("From: %s\nTo: %s\n\n",
                        DateHelper.getFormattedDate(from),
                        DateHelper.getFormattedDate(to)
                );

                System.out.println(Choices.reserveVenueChoices);

                choice = InputHelper.getIntegerInput("Enter choice: ");
                switch (choice) {
                    case 1:
                        reservationDetails = currentUser.reserveVenue(VenueType.CONFERENCE, from, to);
                        if(reservationDetails == null){
                            System.out.println("Sorry! No Venues Available based on your request");
                        }
                        else{
                            System.out.println("Hurray!! You have reserved your venue successfully.");
                            for(String key: reservationDetails.getMap().keySet()){
                                System.out.println(key + ": " + reservationDetails.getMap().get(key));
                            }
                            System.out.println();
                        }
                        break;
                    case 2:
                        reservationDetails = currentUser.reserveVenue(VenueType.HANDS_ON_TRAINING, from, to);
                        if(reservationDetails == null){
                            System.out.println("Sorry! The venue you requested is already reserved");
                        }
                        else{
                            System.out.println("Hurray!! You have reserved your venue successfully.");
                            for(String key: reservationDetails.getMap().keySet()){
                                System.out.println(key + ": " + reservationDetails.getMap().get(key));
                            }
                            System.out.println();
                        }
                        break;
                    case 3:
                        reservationDetails = currentUser.reserveVenue(VenueType.AUDITORIUM, from, to);
                        if(reservationDetails == null){
                            System.out.println("Sorry! The venue you requested is already reserved");
                        }
                        else{
                            System.out.println("Hurray!! You have reserved your venue successfully.");
                            for(String key: reservationDetails.getMap().keySet()){
                                System.out.println(key + ": " + reservationDetails.getMap().get(key));
                            }
                            System.out.println();
                        }
                        break;
                    case 4:
                        venueCode = InputHelper.getVenueCodeInput("Enter Venue Code: ", venueManager.getVenuesCount());
                        reservationDetails = currentUser.reserveVenue(venueCode, from, to);
                        if(reservationDetails == null){
                            System.out.println("Sorry! The venue you requested is already reserved");
                        }
                        else{
                            System.out.println("Hurray!! You have reserved your venue successfully.");
                            for(String key: reservationDetails.getMap().keySet()){
                                System.out.println(key + ": " + reservationDetails.getMap().get(key));
                            }
                            System.out.println();
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

    // 3. Cancel Reserved Venue
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
            char confirmation1 = InputHelper.getYesOrNoCharacterInput("Are you sure? You want to cancel the venue? (Y/N): "), confirmation2;
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
                        confirmation2 = InputHelper.getYesOrNoCharacterInput("Are you sure? You want to cancel the venue? (Y/N): ");
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
                        confirmation2 = InputHelper.getYesOrNoCharacterInput("Are you sure? You want to cancel the venue? (Y/N): ");
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
                        confirmation2 = InputHelper.getYesOrNoCharacterInput("Are you sure? You want to cancel the venue? (Y/N): ");
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

    // 4. Change Reserved Venue
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

        int newVenueCode = InputHelper.getVenueCodeInput("Enter New Venue Code: ", venueManager.getVenuesCount());
        Reservation newReservationDetails = currentUser.changeVenue(currentReservationDetails.getVenueCode(), accessId, newVenueCode);
        if(newReservationDetails == null){
            System.out.println("Sorry! The venue you requested is already reserved");
        }
        else{
            System.out.println("Hurray!! You have changed your venue successfully.\nNew Venue: " + newReservationDetails.getVenueCode());
            System.out.println("Your access ID is: " + newReservationDetails.getAccessId());
        }
    }

    // 5. Show Reserved Venues
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

    // 6. View Profile
    private static void showProfile() {
        System.out.println("\n---------");
        System.out.println("User Profile");
        Map<String, String> personalDetails = currentUser.getPersonalDetails();
        for (String key : personalDetails.keySet()) {
            System.out.printf("%s: %s\n", key, personalDetails.get(key));
        }
        System.out.println();
    }

    // 7. Modify Personal Details
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

    // 8. LOGOUT
    private static void logout() {
        currentUser = null;
        System.out.println("\n---------");
        System.out.println("Logging out!\n");
    }

    // Admin Operations

    // 9.  View User Details
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

    // 10. Change User Password
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
        else
            System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
    }

    // 11. Add User
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

    // 12. Remove User
    private static void manageUserRemoval() {
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
            char confirmation1 = InputHelper.getYesOrNoCharacterInput("Are you sure? You want to remove " + username + "? (Y/N): "), confirmation2;
            if(confirmation1 == 'Y' || confirmation1 == 'y'){
//                confirmation2 = InputHelper.getStringInput("All the details of the User will be deleted. Are you sure? (Y/N): ").charAt(0);
                confirmation2 = InputHelper.getYesOrNoCharacterInput("All the details of the User will be deleted. Are you sure? (Y/N): ");
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

    // 13. Add Venue
    private static void manageVenueAddition() {
        if(currentUser instanceof Admin){
            System.out.println(Choices.addVenueChoices);
            switch (InputHelper.getIntegerInput("Enter Choice: ")){
                case 1:
                    getConferenceRoomDetails();
                    break;
                case 2:
                    getAuditoriumDetails();
                    break;
                case 3:
                    getHandsonTrainingCentreDetails();
                    break;
                default:
                    System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
            }
        }
        else
            System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
    }

    private static void getConferenceRoomDetails() {
        System.out.println("Enter the following venue details");
        String venueName = InputHelper.getStringInput("Venue Name: "),
                location = InputHelper.getStringInput("Location: "),
                seatingCapacity = InputHelper.getStringInput("Seating Capacity: ");
        char isAirConditioned = InputHelper.getYesOrNoCharacterInput("Is Air Conditioner Available? (Y/N): "),
                isWifiAvailable = InputHelper.getYesOrNoCharacterInput("Is Wifi Available? (Y/N): "),
                isChargingPortsAvailable = InputHelper.getYesOrNoCharacterInput("Is Charging Ports Available? (Y/N): "),
                isWhiteBoardAvailable = InputHelper.getYesOrNoCharacterInput("Is White Board Available? (Y/N): ");

        Venue newVenue = new ConferenceRoom(
                venueName,
                venueManager.getNewVenueCode(),
                location,
                seatingCapacity,
                getBoolFromYesOrNo(isAirConditioned),
                getBoolFromYesOrNo(isWifiAvailable),
                getBoolFromYesOrNo(isChargingPortsAvailable),
                getBoolFromYesOrNo(isWhiteBoardAvailable)
        );

        boolean isVenueAdded = ((Admin) currentUser).addVenue(newVenue);

        System.out.println(
                (isVenueAdded)?
                        "Venue Added Successfully":
                        "Cannot add Venue, Please try again"
        );

    }

    private static void getAuditoriumDetails() {
        System.out.println("Enter the following venue details");
        String venueName = InputHelper.getStringInput("Venue Name: "),
                location = InputHelper.getStringInput("Location: "),
                seatingCapacity = InputHelper.getStringInput("Seating Capacity: "),
                noOfDisplayScreen = InputHelper.getStringInput("No. of display screens: ");
        char isAirConditioned = InputHelper.getYesOrNoCharacterInput("Is Air Conditioner Available? (Y/N): "),
                isWifiAvailable = InputHelper.getYesOrNoCharacterInput("Is Wifi Available? (Y/N): "),
                isChargingPortsAvailable = InputHelper.getYesOrNoCharacterInput("Is Charging Ports Available? (Y/N): "),
                isMicStandAvailable = InputHelper.getYesOrNoCharacterInput("Is Mic Stand Available? (Y/N): ");

        Venue newVenue = new Auditorium(
                venueName,
                venueManager.getNewVenueCode(),
                location,
                seatingCapacity,
                getBoolFromYesOrNo(isAirConditioned),
                getBoolFromYesOrNo(isWifiAvailable),
                getBoolFromYesOrNo(isChargingPortsAvailable),
                getBoolFromYesOrNo(isMicStandAvailable),
                noOfDisplayScreen
        );

        boolean isVenueAdded = ((Admin) currentUser).addVenue(newVenue);

        System.out.println(
                (isVenueAdded)?
                        "Venue Added Successfully":
                        "Cannot add Venue, Please try again"
        );
    }

    private static void getHandsonTrainingCentreDetails() {
        System.out.println("Enter the following venue details");
        String venueName = InputHelper.getStringInput("Venue Name: "),
                location = InputHelper.getStringInput("Location: "),
                seatingCapacity = InputHelper.getStringInput("Seating Capacity: ");
        char isAirConditioned = InputHelper.getYesOrNoCharacterInput("Is Air Conditioner Available? (Y/N): "),
                isWifiAvailable = InputHelper.getYesOrNoCharacterInput("Is Wifi Available? (Y/N): "),
                isChargingPortsAvailable = InputHelper.getYesOrNoCharacterInput("Is Charging Ports Available? (Y/N): "),
                isMicStandAvailable = InputHelper.getYesOrNoCharacterInput("Is Mic Stand Available? (Y/N): ");

        Venue newVenue = new HandsOnTrainingCentre(
                venueName,
                venueManager.getNewVenueCode(),
                location,
                seatingCapacity,
                getBoolFromYesOrNo(isAirConditioned),
                getBoolFromYesOrNo(isWifiAvailable),
                getBoolFromYesOrNo(isChargingPortsAvailable),
                getBoolFromYesOrNo(isMicStandAvailable)
        );

        boolean isVenueAdded = ((Admin) currentUser).addVenue(newVenue);

        System.out.println(
                (isVenueAdded)?
                        "Venue Added Successfully":
                        "Cannot add Venue, Please try again"
        );
    }

    private static boolean getBoolFromYesOrNo(char character) {
        return ( (character == 'Y') || (character == 'y') );
    }

    // 14. Update Venue
    private static void manageVenueUpdate() {
        if(currentUser instanceof Admin){

        }
        else
            System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
    }

    // 15. Remove Venue
    private static void manageVenueRemoval() {
        if(currentUser instanceof Admin){
            int venueCode = InputHelper.getIntegerInput("Enter the venue code of the venue you want to remove: ");
            if(venueManager.isValidVenueCode(venueCode)){}
            else{
                System.out.println("Invalid Venue Code. Please try again");
                return;
            }

            venueManager.displayVenueDetails(venueCode);

            char confirmation1 = InputHelper.getYesOrNoCharacterInput("Are you Sure? You want to remove the venue? (Y/N): " );

            if( (confirmation1 == 'y') || (confirmation1 == 'Y')){}
            else {
                System.out.println("Venue Removal Stooped!");
                return;
            }

            boolean isVenueRemoved = ((Admin) currentUser).removeVenue(venueCode);

            System.out.println(
                (isVenueRemoved)?
                "Removed Venue successfully":
                "Cannot remove venue. Please try again"
            );

        }
        else
            System.out.println("OOPs! Invalid Choice, please choose a valid one\n");
    }
}