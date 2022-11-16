import core.manager.VenueManager;
import core.venue.*;
import core.user.Admin;
import core.user.User;
import helper.Choices;
import helper.DateHelper;
import helper.InputHelper;
import helper.PrintHelper;

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
                                case 16:
                                    manageSetMaximumPossibleReservationDate();
                                    break;
                                default:
                                    PrintHelper.printRed("OOPs! Invalid Choice, please choose a valid one\n");
                            }
                        }
                    }
                    continue;
                case 2:
                    PrintHelper.printBlue("\nExiting....");
                    System.exit(1);
                    break;
                default:
                    PrintHelper.printRed("OOPs! Invalid Choice, please choose a valid one\n");
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
            PrintHelper.printGreen("Authentication Successful!!\n");
            PrintHelper.printBlue("Welcome " + currentUser.getUsername() + "!");
            isLoginSuccessful = true;
        } else {
            System.out.println("\n---------");
            PrintHelper.printRed("You have entered an invalid username or password. Please try again\n");
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
                    venueCode = InputHelper.getVenueCodeInput("Enter Venue Code: ");
                    currentUser.displayVenueDetails(venueCode);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("\n---------");
                    PrintHelper.printRed("OOPs! Invalid Choice, please choose a valid one\n");
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
                PrintHelper.printBlue(
                        String.format(
                            "From: %s\nTo: %s\n",
                            DateHelper.getFormattedDate(from),
                            DateHelper.getFormattedDate(to)
                        )
                );
                System.out.println(Choices.checkAvailabilityChoices);

                choice = InputHelper.getIntegerInput("Enter choice: ");
                switch (choice) {
                    case 1:
                        availableVenueCodes = currentUser.checkAvailability(from, to);
                        venueManager.printVenuesAvailability(availableVenueCodes);
                        break;
                    case 2:
                        venueType = VenueType.CONFERENCE;
                        availableVenueCodes = currentUser.checkAvailability(venueType, from, to);
                        venueManager.printVenuesAvailability(availableVenueCodes, venueType);
                        break;
                    case 3:
                        venueType = VenueType.HANDS_ON_TRAINING;
                        availableVenueCodes = currentUser.checkAvailability(venueType, from, to);
                        venueManager.printVenuesAvailability(availableVenueCodes, venueType);
                        break;
                    case 4:
                        venueType = VenueType.AUDITORIUM;
                        availableVenueCodes = currentUser.checkAvailability(venueType, from, to);
                        venueManager.printVenuesAvailability(availableVenueCodes, venueType);
                        break;
                    case 5:
                        venueCode = InputHelper.getVenueCodeInput("Enter Venue Code: ");
                        boolean availability = currentUser.checkAvailability(venueCode, from, to);
                        venueManager.printVenueAvailability(venueCode, availability);
                        break;
                    case 6:
                        break innerLoop;
                    case 7:
                        return;
                    default:
                        PrintHelper.printRed("OOPs! Invalid Choice, please choose a valid one\n");
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
                PrintHelper.printBlue(
                    String.format(
                        "From: %s\nTo: %s\n",
                        DateHelper.getFormattedDate(from),
                        DateHelper.getFormattedDate(to)
                    )
                );

                System.out.println(Choices.reserveVenueChoices);

                choice = InputHelper.getIntegerInput("Enter choice: ");
                switch (choice) {
                    case 1:
                        reservationDetails = currentUser.reserveVenue(VenueType.CONFERENCE, from, to);
                        if(reservationDetails == null){
                            PrintHelper.printRed("Sorry! No Venues Available based on your request");
                        }
                        else{
                            PrintHelper.printGreen("Hurray!! You have reserved your venue successfully.");
                            for(String key: reservationDetails.getMap().keySet()){
                                PrintHelper.printYellow(key + ": " + reservationDetails.getMap().get(key));
                            }
                            System.out.println();
                        }
                        break;
                    case 2:
                        reservationDetails = currentUser.reserveVenue(VenueType.AUDITORIUM, from, to);
                        if(reservationDetails == null){
                            PrintHelper.printRed("Sorry! The venue you requested is already reserved");
                        }
                        else{
                            PrintHelper.printGreen("Hurray!! You have reserved your venue successfully.");
                            for(String key: reservationDetails.getMap().keySet()){
                                PrintHelper.printYellow(key + ": " + reservationDetails.getMap().get(key));
                            }
                            System.out.println();
                        }
                        break;
                    case 3:
                        reservationDetails = currentUser.reserveVenue(VenueType.HANDS_ON_TRAINING, from, to);
                        if(reservationDetails == null){
                            PrintHelper.printRed("Sorry! The venue you requested is already reserved");
                        }
                        else{
                            PrintHelper.printGreen("Hurray!! You have reserved your venue successfully.");
                            for(String key: reservationDetails.getMap().keySet()){
                                PrintHelper.printYellow(key + ": " + reservationDetails.getMap().get(key));
                            }
                            System.out.println();
                        }
                        break;
                    case 4:
                        venueCode = InputHelper.getVenueCodeInput("Enter Venue Code: ");
                        reservationDetails = currentUser.reserveVenue(venueCode, from, to);
                        if(reservationDetails == null){
                            PrintHelper.printRed("Sorry! The venue you requested is already reserved");
                        }
                        else{
                            PrintHelper.printGreen("Hurray!! You have reserved your venue successfully.");
                            for(String key: reservationDetails.getMap().keySet()){
                                PrintHelper.printYellow(key + ": " + reservationDetails.getMap().get(key));
                            }
                            System.out.println();
                        }
                        break;
                    case 5:
                        break innerLoop;
                    case 6:
                        return;
                    default:
                        PrintHelper.printRed("OOPs! Invalid Choice, please choose a valid one\n");
                }
            }
        }
    }

    // 3. Cancel Reserved Venue
    private static void manageVenueCancellation() {

        boolean isCancelled;

        char confirmation = InputHelper.getYesOrNoCharacterInput("Do you have a access Id? If not, please reserve the venue first (Y/N): ");
        if((confirmation == 'Y') || (confirmation == 'y')){}
        else{
            PrintHelper.printRed("Please reserve a venue to get the access id!");
            return;
        }

        while(true) {
            accessId = InputHelper.getIntegerInput("\nEnter Access Id (-1 to exit): ");
            if(accessId == -1)
                return;

            Reservation reservationDetails = venueManager.getReservationDetails(currentUser.getUsername(), accessId);
            if (reservationDetails == null){
                PrintHelper.printRed("OOPs! Access ID invalid. Please try again");
                continue;
            }
            else{
                PrintHelper.printYellowUnderlined("\nYour reservation details for given access id:");
                for(String key: reservationDetails.getMap().keySet()){
                    PrintHelper.printYellow(key + ": " + reservationDetails.getMap().get(key));
                }
            }

            char confirmation1 = InputHelper.getYesOrNoCharacterInput("\nAre you sure? You want to cancel the venue? (Y/N): "), confirmation2;
            if(confirmation1 == 'Y' || confirmation1 == 'y'){}
            else{
                PrintHelper.printRed("Cancellation process stopped!");
                return;
            }

            venueCode = reservationDetails.getVenueCode();

            innerLoop:
            while (true) {
                System.out.println("\n---------");
                System.out.println(Choices.cancelVenueChoices);

                choice = InputHelper.getIntegerInput("Enter choice: ");
                switch (choice) {
                    case -1:
                        return;
                    case 1:
                        confirmation2 = InputHelper.getYesOrNoCharacterInput("\nAre you sure? You want to cancel the venue? (Y/N): ");
                        if(confirmation2 == 'Y' || confirmation2 == 'y') {
                            isCancelled = currentUser.cancelVenue(venueCode, accessId);

                            if (isCancelled)
                                PrintHelper.printGreen("Success! You have successfully cancelled the venue");
                            else
                                PrintHelper.printRed("Sorry! Cancellation failed. Please try again");
                        }
                        else{
                            PrintHelper.printRed("Cancellation process stopped!");
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
                            PrintHelper.printRed("All/some dates are not in your reservation. Please check your reservation details and try again");
                            return;
                        }

                        confirmation2 = InputHelper.getYesOrNoCharacterInput("\nAre you sure? You want to cancel the venue? (Y/N): ");
                        if(confirmation2 == 'Y' || confirmation2 == 'y') {
                            isCancelled = currentUser.cancelVenue(venueCode, accessId, from, to);

                            if(isCancelled)
                                PrintHelper.printGreen("Success! You have successfully cancelled the mentioned dates");
                            else
                                PrintHelper.printRed("Sorry! Cancellation failed. Please try again");
                        }
                        else{
                            PrintHelper.printRed("Cancellation process stopped!");
                            return;
                        }

                        return;
                    case 3:
                        LocalDate dateToBeCancelled = InputHelper.getDateInput("Enter the date to be cancelled(DD-MM-YYYY): ");

                        boolean isValidDate = venueManager.isValidDate(accessId, dateToBeCancelled, currentUser.getUsername());

                        if(isValidDate){}
                        else{
                            PrintHelper.printRed("The date you entered is not in your reservation. Please check your reservation details and try again");
                            return;
                        }

                        confirmation2 = InputHelper.getYesOrNoCharacterInput("\nAre you sure? You want to cancel the venue? (Y/N): ");
                        if(confirmation2 == 'Y' || confirmation2 == 'y') {
                            isCancelled = currentUser.cancelVenue(venueCode, accessId, dateToBeCancelled, dateToBeCancelled);

                            if(isCancelled)
                                PrintHelper.printGreen("Success! You have successfully cancelled the requested date");
                            else
                                PrintHelper.printRed("Sorry! Cancellation failed. Please try again");
                        }
                        else{
                            PrintHelper.printRed("Cancellation process stopped!");
                            return;
                        }

                        return;
                    case 4:
                        break innerLoop;
                    case 5:
                        return;
                    default:
                        PrintHelper.printRed("OOPs! Invalid Choice, please choose a valid one\n");
                }
            }
        }
    }

    // 4. Change Reserved Venue
    private static void manageVenueChange() {

        char confirmation = InputHelper.getYesOrNoCharacterInput("Do you have a access Id? If not, please reserve the venue first (Y/N): ");
        if ((confirmation == 'Y') || (confirmation == 'y')) {}
        else {
            PrintHelper.printRed("Please reserve a venue to get the access id!");
            return;
        }

        while (true) {

            accessId = InputHelper.getIntegerInput("\nEnter Access Id (-1 to exit): ");
            if(accessId == -1) {
                PrintHelper.printBlue("Exiting... to VENUE MANAGER CONSOLE");
                return;
            }

            Reservation currentReservationDetails = venueManager.getReservationDetails(currentUser.getUsername(), accessId);
            if (currentReservationDetails == null) {
                PrintHelper.printRed("OOPs! Access ID invalid. Please try again");
                continue;
            } else {
                PrintHelper.printYellowUnderlined("\nYour reservation details for given access id:");
                for (String key : currentReservationDetails.getMap().keySet()) {
                    PrintHelper.printYellow(key + ": " + currentReservationDetails.getMap().get(key));
                }
            }

            int newVenueCode = InputHelper.getVenueCodeInput("Enter New Venue Code: ");
            Reservation newReservationDetails = currentUser.changeVenue(currentReservationDetails.getVenueCode(), accessId, newVenueCode);
            if (newReservationDetails == null) {
                PrintHelper.printRed("Sorry! The venue you requested is already reserved");
                return;
            }
            else {
                PrintHelper.printGreen("Hurray!! You have changed your venue successfully.");
                PrintHelper.printYellow("New Venue Details");
                for (String key : newReservationDetails.getMap().keySet()) {
                    PrintHelper.printYellow(key + ": " + newReservationDetails.getMap().get(key));
                }
                return;
            }
        }
    }

    // 5. Show Reserved Venues
    private static void getReservationDetails() {
        List<Reservation> reservationDetails = currentUser.getReservationDetails();
        if(reservationDetails.size() == 0){
            PrintHelper.printRed("You don't have any current reservations.");
            return;
        }
        PrintHelper.printYellowUnderlined("Your current reservations\n");
        for(Reservation reservation: reservationDetails){
            for(String key: reservation.getMap().keySet()){
                PrintHelper.printYellow(key + ": " + reservation.getMap().get(key));
            }
            System.out.println();
        }
    }

    // 6. View Profile
    private static void showProfile() {
        System.out.println("\n---------");
        PrintHelper.printYellowUnderlined("User Profile");
        Map<String, String> personalDetails = currentUser.getPersonalDetails();
        for (String key : personalDetails.keySet()) {
            PrintHelper.printYellow(String.format("%s: %s", key, personalDetails.get(key)));
        }
        System.out.println();
    }

    // 7. Modify Personal Details
    private static void managePersonalDetailsModification() {
        System.out.println("\n---------");
        System.out.println(Choices.modifyPersonalDetailsChoices);
        switch (InputHelper.getIntegerInput("Enter choice: ")){
            case 1:
                String newEmailId;
                newEmailId = InputHelper.getEmailId();
                if(currentUser.setEmailId(newEmailId))
                    PrintHelper.printGreen("Email Id modified successfully!");
                else
                    PrintHelper.printRed("Cannot modify Email id. Please try again");
                break;
            case 2:
                String newPhoneNumber = InputHelper.getPhoneNumber();
                if(currentUser.setPhoneNumber(newPhoneNumber))
                    PrintHelper.printGreen("Phone Number modified successfully!");
                else
                    PrintHelper.printRed("Cannot modify Phone Number. Please try again");
                break;
            case 3:
                String password = InputHelper.getStringInput("Enter your current password: ");
                User user = venueManager.authenticate(currentUser.getUsername(), password);
                if(user == null){
                    PrintHelper.printRed("Sorry! Incorrect password. Please try again");
                    managePersonalDetailsModification();
                }
                else{
                    String newPassword = InputHelper.getStringInput("Enter new password: ");
                    if(currentUser.changeUserPassword(newPassword)){
                        PrintHelper.printGreen("Password changed successfully!");
                    }
                }
                break;
            case 4:
                return;
            default:
                PrintHelper.printRed("You have entered an invalid username or password. Please try again\n");
        }
    }

    // 8. LOGOUT
    private static void logout() {
        currentUser = null;
        PrintHelper.printBlue("\nLogging out...");
        PrintHelper.printBlue("Logged out successfully!\n");
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
                    PrintHelper.printRed("Username doesn't exists. Please try again\n");
            }

            while (true) {
                PrintHelper.printBlue("\nUser: " + username);
                System.out.println(Choices.viewUserDetailsChoices);
                switch (InputHelper.getIntegerInput("Enter choice: ")) {
                    case 1:
                        Map<String, String> personalDetails = ((Admin) currentUser).getOtherUserPersonalDetails(username);

                        PrintHelper.printYellowUnderlined("Personal Details of " + username);
                        for (String key : personalDetails.keySet()) {
                            PrintHelper.printYellow(String.format("%s: %s", key, personalDetails.get(key)));
                        }
                        System.out.println();
                        break;
                    case 2:
                        List<Reservation> reservationDetails = ((Admin) currentUser).getOtherUserRegistrationDetails(username);
                        if(reservationDetails.size() == 0){
                            PrintHelper.printRed("The user doesn't have any current reservations.");
                            continue;
                        }
                        PrintHelper.printYellowUnderlined("Reservation details of " + username);
                        for (Reservation reservation : reservationDetails) {
                            for (String key : reservation.getMap().keySet()) {
                                PrintHelper.printYellow(key + ": " + reservation.getMap().get(key));
                            }
                            System.out.println();
                        }
                        break;
                    case 3:
                        return;
                    default:
                        PrintHelper.printRed("OOPs! Invalid Choice, please choose a valid one\n");
                }
            }
        }
        else
            PrintHelper.printRed("OOPs! Invalid Choice, please choose a valid one\n");
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
                    PrintHelper.printRed("Username doesn't exists. Please try again\n");
            }

            String newPassword = InputHelper.getStringInput("Enter new password: ");
            if (((Admin) currentUser).changeOtherUserPassword(username, newPassword))
                PrintHelper.printGreen("Password changed successfully!");
            else{
                PrintHelper.printRed("OOPs. Change Password failed. Please try again");
            }

        }
        else
            PrintHelper.printRed("OOPs! Invalid Choice, please choose a valid one\n");
    }

    // 11. Add User
    private static void manageUserAddition() {
        if(currentUser instanceof Admin){
            String username = InputHelper.getUsername(),
                   password = InputHelper.getStringInput("Enter password: "),
                   emailId = InputHelper.getEmailId(),
                   phoneNumber = InputHelper.getPhoneNumber();

            if(((Admin) currentUser).addUser(username, password, emailId, phoneNumber))
                PrintHelper.printGreen("The user is added successfully!");
            else
                PrintHelper.printRed("Add User failed. Please try again");
        }
        else
            PrintHelper.printRed("OOPs! Invalid Choice, please choose a valid one\n");
    }

    // 12. Remove User
    private static void manageUserRemoval() {
        if(currentUser instanceof Admin){
            String username;
            while(true) {
                username = InputHelper.getStringInput("Enter the username of the user to be deleted: ");
                if(venueManager.checkUserNameExistence(username)) {
                    if (username.equals(currentUser.getUsername()))
                        PrintHelper.printRed("You cannot remove yourself. Please try again with another username\n");
                    else
                        break;
                }
                else
                    PrintHelper.printRed("Username doesn't exists. Please try again\n");
            }
            char confirmation1 = InputHelper.getYesOrNoCharacterInput("Are you sure? You want to remove " + username + "? (Y/N): "), confirmation2;
            if(confirmation1 == 'Y' || confirmation1 == 'y'){
                confirmation2 = InputHelper.getYesOrNoCharacterInput("All the details of the User will be deleted. Are you sure? (Y/N): ");
                if(confirmation2 == 'Y' || confirmation2 == 'y'){

                    if(((Admin) currentUser).removeUser(username))
                        PrintHelper.printGreen("User '" + username + "' successfully removed!");
                    else
                        PrintHelper.printRed("Remove User failed. Please try again");
                }
            }
        }
        else
            PrintHelper.printRed("OOPs! Invalid Choice, please choose a valid one\n");
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
                    getHandsOnTrainingCentreDetails();
                    break;
                default:
                    PrintHelper.printRed("OOPs! Invalid Choice, please choose a valid one\n");
            }
        }
        else
            PrintHelper.printRed("OOPs! Invalid Choice, please choose a valid one\n");
    }

    private static void getConferenceRoomDetails() {
        System.out.println("Enter the following venue details");
        String venueName = InputHelper.getStringInput("Venue Name: "),
                location = InputHelper.getStringInput("Location: ");

        int seatingCapacity;
        while(true){
            seatingCapacity = InputHelper.getIntegerInput("Seating Capacity: ");
            // validating the seating capacity. It cannot exceed 1000.
            if(seatingCapacity >= 1000){
                PrintHelper.printRed("Seating capacity cannot be more than 1000. Please Contact developer ;)\n");
            }
            else
                break;
        }

        char isAirConditioned = InputHelper.getYesOrNoCharacterInput("Is Air Conditioner Available? (Y/N): "),
                isWifiAvailable = InputHelper.getYesOrNoCharacterInput("Is Wifi Available? (Y/N): "),
                isChargingPortsAvailable = InputHelper.getYesOrNoCharacterInput("Is Charging Ports Available? (Y/N): "),
                isWhiteBoardAvailable = InputHelper.getYesOrNoCharacterInput("Is White Board Available? (Y/N): ");

//        Venue newVenue = new ConferenceRoom(
//                venueName,
//                venueManager.getNewVenueCode(),
//                location,
//                String.valueOf(seatingCapacity),
//                getBoolFromYesOrNo(isAirConditioned),
//                getBoolFromYesOrNo(isWifiAvailable),
//                getBoolFromYesOrNo(isChargingPortsAvailable),
//                getBoolFromYesOrNo(isWhiteBoardAvailable)
//        );
        Venue newVenue = new ConferenceRoom.Builder(venueManager.getNewVenueCode(), location, VenueType.CONFERENCE)
                .venueName(venueName)
                .seatingCapacity(String.valueOf(seatingCapacity))
                .isAirConditioned(getBoolFromYesOrNo(isAirConditioned))
                .isWifiAvailable(getBoolFromYesOrNo(isWifiAvailable))
                .isChargingPortsAvailable(getBoolFromYesOrNo(isChargingPortsAvailable))
                .isWhiteBoardAvailable(getBoolFromYesOrNo(isWhiteBoardAvailable))
                .build();

        boolean isVenueAdded = ((Admin) currentUser).addVenue(newVenue);

        if(isVenueAdded)
            PrintHelper.printGreen("Venue Added Successfully");
        else
            PrintHelper.printRed("Cannot add Venue, Please try again");

    }

    private static void getAuditoriumDetails() {
        System.out.println("Enter the following venue details");
        String venueName = InputHelper.getStringInput("Venue Name: "),
                location = InputHelper.getStringInput("Location: "),
                //TODO noOfDisplayScreen should be a valid integer
                noOfDisplayScreen = InputHelper.getStringInput("No. of display screens: ");

        int seatingCapacity;
        while(true){
            seatingCapacity = InputHelper.getIntegerInput("Seating Capacity: ");
            // validating the seating capacity. It cannot exceed 1000.
            if(seatingCapacity > 1000){
                PrintHelper.printRed("Seating capacity cannot be more than 1000. Please Contact developer ;)\n");
            }
            else
                break;
        }

        char isAirConditioned = InputHelper.getYesOrNoCharacterInput("Is Air Conditioner Available? (Y/N): "),
                isWifiAvailable = InputHelper.getYesOrNoCharacterInput("Is Wifi Available? (Y/N): "),
                isChargingPortsAvailable = InputHelper.getYesOrNoCharacterInput("Is Charging Ports Available? (Y/N): "),
                isMicStandAvailable = InputHelper.getYesOrNoCharacterInput("Is Mic Stand Available? (Y/N): ");

//        Venue newVenue = new Auditorium(
//                venueName,
//                venueManager.getNewVenueCode(),
//                location,
//                String.valueOf(seatingCapacity),
//                getBoolFromYesOrNo(isAirConditioned),
//                getBoolFromYesOrNo(isWifiAvailable),
//                getBoolFromYesOrNo(isChargingPortsAvailable),
//                getBoolFromYesOrNo(isMicStandAvailable),
//                noOfDisplayScreen
//        );
        Venue newVenue = new Auditorium.Builder(venueManager.getNewVenueCode(), location, VenueType.AUDITORIUM)
                .seatingCapacity(String.valueOf(seatingCapacity))
                .isAirConditioned(getBoolFromYesOrNo(isAirConditioned))
                .isWifiAvailable(getBoolFromYesOrNo(isWifiAvailable))
                .isChargingPortsAvailable(getBoolFromYesOrNo(isChargingPortsAvailable))
                .isMicStandAvailable(getBoolFromYesOrNo(isMicStandAvailable))
                .noOfDisplayScreen(noOfDisplayScreen)
                .venueName(venueName)
                .build();

        boolean isVenueAdded = ((Admin) currentUser).addVenue(newVenue);

        if(isVenueAdded)
            PrintHelper.printGreen("Venue Added Successfully");
        else
            PrintHelper.printRed("Cannot add Venue, Please try again");
    }

    private static void getHandsOnTrainingCentreDetails() {
        System.out.println("Enter the following venue details");
        String venueName = InputHelper.getStringInput("Venue Name: "),
                location = InputHelper.getStringInput("Location: ");

        int seatingCapacity;
        while(true){
            seatingCapacity = InputHelper.getIntegerInput("Seating Capacity: ");
            // validating the seating capacity. It cannot exceed 1000.
            if(seatingCapacity > 1000){
                PrintHelper.printRed("Seating capacity cannot be more than 1000. Please Contact developer ;)\n");
            }
            else
                break;
        }
        char isAirConditioned = InputHelper.getYesOrNoCharacterInput("Is Air Conditioner Available? (Y/N): "),
                isWifiAvailable = InputHelper.getYesOrNoCharacterInput("Is Wifi Available? (Y/N): "),
                isChargingPortsAvailable = InputHelper.getYesOrNoCharacterInput("Is Charging Ports Available? (Y/N): "),
                isMicStandAvailable = InputHelper.getYesOrNoCharacterInput("Is Mic Stand Available? (Y/N): ");

//        Venue newVenue = new HandsOnTrainingCentre(
//                venueName,
//                venueManager.getNewVenueCode(),
//                location,
//                String.valueOf(seatingCapacity),
//                getBoolFromYesOrNo(isAirConditioned),
//                getBoolFromYesOrNo(isWifiAvailable),
//                getBoolFromYesOrNo(isChargingPortsAvailable),
//                getBoolFromYesOrNo(isMicStandAvailable)
//        );
        Venue newVenue = new HandsOnTrainingCentre.Builder(venueManager.getNewVenueCode(), location, VenueType.HANDS_ON_TRAINING)
                .venueName(venueName)
                .seatingCapacity(String.valueOf(seatingCapacity))
                .isAirConditioned(getBoolFromYesOrNo(isAirConditioned))
                .isWifiAvailable(getBoolFromYesOrNo(isWifiAvailable))
                .isChargingPortsAvailable(getBoolFromYesOrNo(isChargingPortsAvailable))
                .isMicStandAvailable(getBoolFromYesOrNo(isMicStandAvailable))
                .build();

        boolean isVenueAdded = ((Admin) currentUser).addVenue(newVenue);

        if(isVenueAdded)
            PrintHelper.printGreen("Venue Added Successfully");
        else
            PrintHelper.printRed("Cannot add Venue, Please try again");
    }

    private static boolean getBoolFromYesOrNo(char character) {
        return ( (character == 'Y') || (character == 'y') );
    }

    // 14. Update Venue
    private static void manageVenueUpdate() {
        if(currentUser instanceof Admin){
            venueCode = InputHelper.getVenueCodeInput("Enter Venue Code: ");

            System.out.println(Choices.updateVenueChoices);
            switch (InputHelper.getIntegerInput("Enter choice: ")){
                case 1:
                    String newVenueName = InputHelper.getStringInput("Enter new Venue Name: ");
                    boolean isVenueUpdateSuccess = ((Admin) currentUser).updateVenueName(venueCode, newVenueName);
                    if(isVenueUpdateSuccess)
                        PrintHelper.printGreen("New venue detail updated successfully");
                    else
                        PrintHelper.printRed("Cannot update venue. Please try again");
                    break;
                case 2:
                    //TODO seating capacity should not exceed 1000
                    String newSeatingCapacity = InputHelper.getStringInput("Enter new Seating Capacity: ");
                    isVenueUpdateSuccess = ((Admin)currentUser).updateVenueSeatingCapacity(venueCode, newSeatingCapacity);
                    if(isVenueUpdateSuccess)
                        PrintHelper.printGreen("New venue detail updated successfully");
                    else
                        PrintHelper.printRed("Cannot update venue. Please try again");
                    break;
                default:
                    PrintHelper.printRed("OOPs! Invalid Choice, please choose a valid one\n");
            }
        }
        else
            PrintHelper.printRed("OOPs! Invalid Choice, please choose a valid one\n");
    }

    // 15. Remove Venue
    private static void manageVenueRemoval() {
        if(currentUser instanceof Admin){
            venueCode = InputHelper.getVenueCodeInput("Enter Venue Code: ");

            venueManager.displayVenueDetails(venueCode);

            char confirmation1 = InputHelper.getYesOrNoCharacterInput("Are you Sure? You want to remove the venue? (Y/N): " );

            if( (confirmation1 == 'y') || (confirmation1 == 'Y')){}
            else {
                PrintHelper.printRed("Venue Removal Stooped!");
                return;
            }

            boolean isVenueRemoved = ((Admin) currentUser).removeVenue(venueCode);

            if(isVenueRemoved)
                PrintHelper.printGreen("Removed Venue successfully");
            else
                PrintHelper.printRed("Cannot remove venue. Please try again");


        }
        else
            PrintHelper.printRed("OOPs! Invalid Choice, please choose a valid one\n");
    }

    // 16. View/Set Maximum Possible Reservation Date
    private static void manageSetMaximumPossibleReservationDate() {
        if(currentUser instanceof Admin){

            System.out.println(Choices.maxPossibleReservationDateChoices);
            switch (InputHelper.getIntegerInput("Enter Choice: ")){
                case 1:
                    PrintHelper.printYellowUnderlined("Current Maximum Possible Reservation Date");
                    PrintHelper.printYellow(DateHelper.getFormattedDate(((Admin) currentUser).getMaxPossibleReservationDate()));
                    break;
                case 2:
                    LocalDate maxPossibleDate = InputHelper.getMaxPossibleReservationDateInput("Enter Maximum Possible Reservation Date(DD-MM-YYYY): ");
                    if(((Admin) currentUser).setMaxPossibleReservationDate(maxPossibleDate))
                        PrintHelper.printGreen("Maximum Possible Reservation Date Set successfully");
                    else
                        PrintHelper.printRed("Cannot set Maximum Possible Reservation Date. Please try again");
                    break;
                default:
                    PrintHelper.printRed("OOPs! Invalid Choice, please choose a valid one\n");
            }

        }
        else
            PrintHelper.printRed("OOPs! Invalid Choice, please choose a valid one\n");
    }

}