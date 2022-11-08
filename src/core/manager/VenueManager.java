package core.manager;

import core.venue.Reservation;
import core.venue.VenueType;
import core.user.Representative;
import core.user.User;
import core.venue.Venue;
import core.venue.VenueUpdate;
import database.Database;
import helper.DefensiveCopyHelper;
import helper.PrintHelper;

import java.time.LocalDate;
import java.util.*;

// The system that acts as an interface between the user and the database
public class VenueManager implements AdminManager, RepresentativeManager {

    /**
     * This method authenticates the logging user
     * delegates the authentication function to Database class
     *
     * interacts with the Database class
     *
     * @param userName The username entered by the user
     * @param enteredPassword The password entered by the user
     * @return 'User object' if authentication is successful,
     *         'null' if authentication fails
     */
    public User authenticate(String userName, String enteredPassword){
        return Database.getInstance().authenticate(userName, enteredPassword);
    }

    /**
     * This method displays the details of all the venues
     *
     * interacts with the Database class
     */
    @Override
    public void displayVenueDetails() {
        for(Venue venue: Database.getInstance().getVenues().values()){
            Map<String, String> venueDetails = venue.getVenueDetails();
            for(String key: venueDetails.keySet()){
                PrintHelper.printYellow(
                    String.format("%s: %s", key, venueDetails.get(key))
                );
            }
            System.out.println();
        }
//        System.out.println();
    }

    // Display Venue Details
    /**
     * This method displays the details of a single venue
     *
     * interacts with the Database class
     *
     * @param venueCode The code of the venue for which the details have to be displayed
     */
    @Override
    public void displayVenueDetails(int venueCode){
        Map<String, String> venueDetails = Database.getInstance().getVenues().get(venueCode).getVenueDetails();
        for(String key: venueDetails.keySet()){
            PrintHelper.printYellow(
                    String.format("%s: %s", key, venueDetails.get(key))
            );
        }
//        System.out.println();
    }

    /**
     * This method displays the details of, only the venues that is of the given type
     * VenueType: CONFERENCE, HANDS_ON_TRAINING, AUDITORIUM
     *
     * @param type VenueType(enum) The type of the venues for which the details have to be displayed
     */
    @Override
    public void displayVenueDetails(VenueType type) {
        for(Venue venue: Database.getInstance().getVenues().values()){
            Map<String, String> venueDetails = venue.getVenueDetails();
            if(!(venue.getType() == type))
                continue;
            for(String key: venueDetails.keySet()){
                PrintHelper.printYellow(
                        String.format("%s: %s", key, venueDetails.get(key))
                );
            }
            System.out.println();
        }
//        System.out.println();
    }

    // Check availability
    /**
     * This method checks the availability of all the venues for the given 'from date' to 'end date'
     *
     * interacts with the Database class
     *
     * @param from The date from which the venue has to be checked for availability (start date)
     * @param to The date to which the venue has to be checked for availability (end date)
     * @return Returns the list of venue codes of available venues
     */
    @Override
    public ArrayList<Integer> checkAvailability(LocalDate from, LocalDate to) {
        Map<Integer, List<Reservation>> reservationDetails = Database.getInstance().getVenueReservationDetails();
        ArrayList<Integer> availableVenues = new ArrayList<>();
        for(int venueCode: reservationDetails.keySet()){
            boolean available = true;
            List<Reservation> venueReservationDetails = reservationDetails.get(venueCode);
            outerLoop:
            for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)){
                for(Reservation currentReservation: venueReservationDetails){
                    if(currentReservation.getReservedDates().contains(date)) {
                        available = false;
                        break outerLoop;
                    }
                }
            }
            if(available)
                availableVenues.add(venueCode);
        }
        return availableVenues;
    }

    /**
     * This method checks the availability of venues that is of the given 'type' for the given 'from date' to 'end date'
     *
     * interacts with the Database class
     *
     * @param type VenueType(enum) The type of the venues for which the availability has to be checked
     * @param from The date from which the venue has to be checked for availability (start date)
     * @param to The date to which the venue has to be checked for availability (end date)
     * @return Returns the list of venue codes of available venues
     */
    @Override
    public ArrayList<Integer> checkAvailability(VenueType type, LocalDate from, LocalDate to){
        Map<Integer, List<Reservation>> reservationDetails = Database.getInstance().getVenueReservationDetails();
        ArrayList<Integer> availableVenues = new ArrayList<>();
        for(int venueCode: reservationDetails.keySet()){
            boolean available = true;
            if (Database.getInstance().getVenues().get(venueCode).getType() == type) {
                List<Reservation> venueReservationDetails = reservationDetails.get(venueCode);
                outerLoop:
                for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
                    for (Reservation currentReservation : venueReservationDetails) {
                        if (currentReservation.getReservedDates().contains(date)) {
                            available = false;
                            break outerLoop;
                        }
                    }
                }
                if (available)
                    availableVenues.add(venueCode);
            }
        }
        return availableVenues;
    }

    /**
     * This method checks the availability of a specific venue for the given 'venue code, ''from date' to 'end date'
     * interacts with the Database class
     *
     * @param venueCode The code of the venue for which the availability has to be checked
     * @param from The date from which the venue has to be checked for availability (start date)
     * @param to The date to which the venue has to be checked for availability (end date)
     * @return Returns 'true' if available, 'false' otherwise
     */
    @Override
    public boolean checkAvailability(int venueCode, LocalDate from, LocalDate to){
        List<Reservation> reservationDetails = Database.getInstance().getVenueReservationDetails().get(venueCode);

        for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
            for (Reservation currentReservation : reservationDetails) {
                if (currentReservation.getReservedDates().contains(date)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Reserve Venue
    /**
     * This method reserves the venue of given 'type' for the given 'from date' to 'to date'
     *
     * @param type VenueType(enum) The type of the venues for which the reservation can be done
     * @param from The date from which the venue has to be reserved (start date)
     * @param to The date to which the venue has to be reserved (end date)
     * @param username The username of the user making the reservation
     * @return Returns
     *         'Reservation' object, if reservation is successful
     *         'null', if reservation fails
     */
    @Override
    public Reservation reserveVenue(VenueType type, LocalDate from, LocalDate to, String username){
        ArrayList<Integer> availableVenues = checkAvailability(type, from, to);
        Reservation currentReservation = null;
        if(availableVenues.size() != 0) {
            int accessId = generateUniqueAccessId();

            currentReservation = new Reservation(
                    accessId,
                    username,
                    availableVenues.get(0),
                    from,
                    to
            );

            updateAvailability(currentReservation);
        }

        return currentReservation;

    }

    /**
     * This method reserves the venue with the given venue code
     *
     * @param venueCode The code of the venue that has to be reserved
     * @param from The date from which the venue has to be reserved (start date)
     * @param to The date to which the venue has to be reserved (end date)
     * @param username The username of the user making the reservation
     * @return Returns
     *         'Reservation' object, if reservation is successful
     *         'null', if reservation fails
     */
    @Override
    public Reservation reserveVenue(int venueCode, LocalDate from, LocalDate to, String username){
        Reservation currentReservation = null;
        boolean available = checkAvailability(venueCode, from, to);
        if(available) {
            int accessId = generateUniqueAccessId();

            currentReservation = new Reservation(
                    accessId,
                    username,
                    venueCode,
                    from,
                    to
            );
            updateAvailability(currentReservation);
        }
        return currentReservation;

    }

    // Update availability
    /**
     * This method updates the database after a new reservation has been made
     *
     * Called by the reserveVenue and cancelVenue functions
     * interacts with the Database class
     *
     * @param reservationDetails Reservation object which contains the reservation details
     * @return Returns
     *         'true', if updated the database successfully
     *         'false', otherwise
     */
    private boolean updateAvailability(Reservation reservationDetails){
        Database.getInstance().addToVenueReservationDetails(reservationDetails.getVenueCode(), reservationDetails);
        Database.getInstance().addToUserReservationDetails(reservationDetails.getUsername(), reservationDetails);
        Database.getInstance().addToAccessIdUserMap(reservationDetails.getAccessId(), reservationDetails.getUsername());
        return true;
    }

    // Cancel Venue
    /**
     * This method cancels a reserved venue of the given 'access id'
     * This method 'cancels the entire reservation'
     *
     * interacts with the Database class
     *
     * @param venueCode The code of the venue that is to be cancelled
     * @param accessId The access id of the reservation. It uniquely identifies a reservation
     * @param username The username of the user making the cancellation
     * @return Returns
     *         'true', if cancelled successfully
     *         'false', otherwise
     */
    @Override
    public boolean cancelVenue(int venueCode, int accessId, String username) {
        Database database = Database.getInstance();
        database.removeFromVenueReservationDetails(venueCode, accessId);

        System.out.println(database.getVenueReservationDetails().get(venueCode));

        database.removeFromUserReservationDetails(accessId, username);

        return true;
    }

    /**
     * This method cancels a reserved venue of the given 'access id'
     * This method 'cancels the dates between the given "from" and "to" dates'
     * This method also 'cancels a single date' if the 'from' and 'to' dates are same
     *
     * interacts with the Database class
     *
     * @param venueCode The code of the venue that is to be cancelled
     * @param accessId The access id of the reservation. It uniquely identifies a reservation
     * @param from The date from which the venue has to be cancelled (start date)
     * @param to The date to which the venue has to be cancelled (end date)
     * @param username The username of the user making the cancellation
     * @return Returns
     *         'true', if cancelled successfully
     *         'false', otherwise
     */
    @Override
    public boolean cancelVenue(int venueCode, int accessId, LocalDate from, LocalDate to, String username) {
        Database.getInstance().removeFromVenueReservationDetails(venueCode, accessId, from, to);
        System.out.println(Database.getInstance().getVenueReservationDetails().get(venueCode));

        Database.getInstance().removeFromUserReservationDetails(accessId, from, to, username);

        return true;
    }

    // Change Venue
    /**
     * This method is used to change the reservation from one venue to another venue
     * It changes only the venue. The dates and the access id will be the same as that of the old reservation
     *
     * interacts with the Database class
     * calls the 'updateAvailability' and 'cancelVenue' functions
     *
     * @param oldVenueCode The current venue code of the reservation
     * @param accessId The access id of the reservation. It uniquely identifies a reservation
     * @param newVenueCode The new venue code that will be changed to
     * @param username The username of the user making the change
     * @return Returns
     *         'Reservation' object, if venue change is successful
     *         'null', otherwise
     */
    @Override
    public Reservation changeVenue(int oldVenueCode, int accessId, int newVenueCode, String username){
        Database database = Database.getInstance();

        Reservation currentReservation = null;

        List<Reservation> reservations = database.getVenueReservationDetails().get(oldVenueCode);
        for(Reservation reservation: reservations){
            if(reservation.getAccessId() == accessId){
                currentReservation = reservation;
                break;
            }
        }

        //TODO check for availability before changing to the new venue

        boolean isAvailable = checkAvailability(currentReservation.getReservedDates(), newVenueCode);

        if(! isAvailable){
            return null;
        }
        currentReservation.setVenueCode(newVenueCode);

        updateAvailability(currentReservation);
        cancelVenue(oldVenueCode, accessId, currentReservation.getUsername());


        return currentReservation;
    }

    private boolean checkAvailability(List<LocalDate> reservedDates, int newVenueCode) {
        List<LocalDate> allReservedDates = new ArrayList<>();
        List<Reservation> reservationDetails = Database.getInstance().getVenueReservationDetails().get(newVenueCode);
        for(Reservation reservation: reservationDetails){
            allReservedDates.addAll(reservation.getReservedDates());
        }

        for(LocalDate date: reservedDates){
            if(allReservedDates.contains(date))
                return false;
        }
        return true;
    }

    // Get reservation details
    /**
     * This method is used to get the reservation details of the user from the database
     *
     * interacts with the Database class
     *
     * @param username The username of the user whose details have to be got.
     * @return Returns the reservation details of the user as a list of reservation objects
     */
    @Override
    public List<Reservation> getReservationDetails(String username) {
        return Database.getInstance().getUserReservation(username);
    }

    /**
     * This method is used to get the reservation details of a specific reservation using the access id
     * This method also indirectly checks whether the user is authorised to operate using the provided 'access id'
     *
     * @param username The username of the user whose details have to be got.
     * @param accessId The access id of the reservation
     * @return Returns
     *         'Reservation' object "if the user is authorized to use the provided access id" and "the details are taken from the database successfully"
     *         'null', otherwise
     */
    public Reservation getReservationDetails(String username, int accessId) {

        List<Reservation> userReservations = Database.getInstance().getUserReservation(username);
        for(Reservation reservation: userReservations){
            if(reservation.getAccessId() == accessId)
                return reservation;
        }
        return null;

    }

    /**
     * This method is used to update the User details of the user
     *
     * @param user The User object that is to be updated in the database
     * @return Returns
     *         'true', if updated successfully
     *         'false', otherwise
     */
    @Override
    public boolean updateUserDatabase(User user) {
        Database.getInstance().addToUsers(user.getUsername(), user);
        return true;
    }

    /**
     * This method is used to change the password of the user in the database
     *
     * @param username The username of the user for which the password has to be changed
     * @param newPassword The password that is to be updated it the database
     * @return Returns
     *         'true', if changed successfully
     *         'false', otherwise
     */
    public boolean changeUserPassword(String username, String newPassword) {
        return Database.getInstance().changeUserPassword(username, newPassword);
    }

    /**
     * This method checks whether the dates between 'from' and 'to' are present in the reservation details of the provided access id
     * This check is done during the cancellation process to let know the user that they have entered date(s) that is/are not in the reservation
     *
     * @param accessId The access id of the reservation for which the dates have to be checked
     * @param from The date from which the reservation has to be checked (start date)
     * @param to The date to which the reservation has to be checked (end date)
     * @param username The username of the user whose reservation details has to be got
     * @return Returns
     *         'true', if valid
     *         'false', otherwise
     */
    public boolean areValidDates(int accessId, LocalDate from, LocalDate to, String username) {
        List<Reservation> userReservations = Database.getInstance().getUserReservation(username);
        for(Reservation reservation: userReservations){
            if(reservation.getAccessId() == accessId){
                List<LocalDate> reservedDates = reservation.getReservedDates();
                for(LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)){
                    if(reservedDates.contains(date)){}
                    else
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * This method checks whether the date is present in the reservation details of the provided access id
     * This check is done during the cancellation process to let know the user that they have entered date that is not in the reservation
     *
     * @param accessId The access id of the reservation for which the date has to be checked
     * @param date The date that is to be validated
     * @param username The username of the user whose reservation details has to be got
     * @return Returns
     *         'true', if valid
     *         'false', otherwise
     */
    public boolean isValidDate(int accessId, LocalDate date, String username) {
        List<Reservation> userReservations = Database.getInstance().getUserReservation(username);
        boolean isValidDate = false;
        for(Reservation reservation: userReservations){
            if(reservation.getAccessId() == accessId){
                List<LocalDate> reservedDates = reservation.getReservedDates();
                isValidDate = reservedDates.contains(date);
            }
        }
        return isValidDate;
    }

    /**
     * This method is used to check whether a username exists or not using the user details in the database
     *
     * interacts with the Database class
     *
     * @param username The username to be checked
     * @return Returns
     *         'true', if exists
     *         'false, otherwise
     */
    public boolean checkUserNameExistence(String username) {
        return Database.getInstance().getUsers().containsKey(username);
    }

    // Add User
    /**
     * This method is used to add a new user
     * Only admin is authorized to call this method
     *
     * interacts with the Database class
     *
     * @param username The username of the new user
     * @param password The password of the new user
     * @param emailId The email of the new user
     * @param phoneNumber The phone number of the new user
     * @return Returns
     *         'true', if user added successfully
     *         'false', otherwise
     */
    @Override
    public boolean addUser(String username, String password, String emailId, String phoneNumber){
        Database database = Database.getInstance();
        database.addToUserCredentials(username, password);
        database.addToUsers(
                username,
                new Representative(
                        username,
                        phoneNumber,
                        emailId,
                        new VenueManager()
                )
        );
        return true;
    }

    // Remove User
    /**
     * This method is used to remove a user
     * Only admin is authorized to call this method
     *
     * interacts with the Database class
     *
     * @param username The username of the user to be removed
     * @return Returns
     *         'true', if user removed successfully
     *         'false', otherwise
     */
    @Override
    public boolean removeUser(String username) {
        Database database = Database.getInstance();
        database.removeFromUserCredentials(username);
        database.removeFromUsers(username);
        return true;
    }

    /**
     * This method is used to get the personal details of the user from the database
     * Only admin is authorized to call this method
     *
     * interacts with the Database class
     *
     * @param username The username of the user whose details have to be got.
     * @return Returns the personal details of the user as a map object
     */
    @Override
    public Map<String, String> getOtherUserPersonalDetails(String username) {
        Database database = Database.getInstance();
        return DefensiveCopyHelper.getDefensiveCopyMap(database.getUsers().get(username).getPersonalDetails());
    }

    /**
     * This method is used to get the reservation details of the user from the database
     * Only admin is authorized to call this method
     *
     * interacts with the Database class
     *
     * @param username The username of the user whose details have to be got.
     * @return Returns the reservation details of the user as a list of reservation objects
     */
    @Override
    public List<Reservation> getOtherUserReservationDetails(String username) {
        return getReservationDetails(username);
    }

    // This function generates a unique 'access id'
    // It is returned to the user as a token of successful registration
    // An 'access id' like a key to the venue for the reserved dates
    private int generateUniqueAccessId() {
        int generatedAccessId;
        ArrayList<Integer> accessIds = Database.getInstance().getAccessIds();
        while(accessIds.contains(generatedAccessId = new Random().nextInt(Integer.MAX_VALUE - 10)));
        return generatedAccessId;
    }

    // Add Venue
    @Override
    public boolean addVenue(Venue newVenue) {
        Database.getInstance().addVenue(newVenue);
        return true;
    }

    // Remove Venue
    @Override
    public boolean removeVenue(int venueCode) {
        Database.getInstance().removeVenue(venueCode);
        return true;
    }

    // Update Venue
    @Override
    public boolean updateVenue(int venueCode, String newValue, VenueUpdate updateOption) {
        return Database.getInstance().updateVenue(venueCode, newValue, updateOption);
    }

    // Print Venues Availability

    // This function gets the venue codes of all the available venues as the input.
    // It gets all the venues from the database.
    // It then checks whether the venue is in the available venues list.
    // if present, it prints "Available" else, prints "Not Available"
    //
    // interacts with the Database class
    public void printVenuesAvailability(ArrayList<Integer> availableVenueCodes) {
        Database database = Database.getInstance();
        System.out.println();
        for(int venueCode: database.getVenues().keySet()){
            if(availableVenueCodes.contains(venueCode)){
                PrintHelper.printGreen(database.getVenueNameFromCode(venueCode) + ": Available");
                continue;
            }
            PrintHelper.printRed(database.getVenueNameFromCode(venueCode) + ": Not Available");
        }
    }

    // This function gets the venue codes of all the 'available venues' as the input.
    // It gets all the venues of the given 'type' from the database.
    // It then checks whether the venue is in the available venues list.
    // if present, it prints "Available" else, prints "Not Available"
    //
    // interacts with the Database class
    @Override
    public void printVenuesAvailability(ArrayList<Integer> availableVenueCodes, VenueType inputType) {
        Database database = Database.getInstance();
        System.out.println();
        for(int venueCode: database.getVenues().keySet()){
            VenueType venueType = database.getVenues().get(venueCode).getType();
            if(venueType == inputType){
                if(availableVenueCodes.contains(venueCode)){
                    PrintHelper.printGreen(database.getVenueNameFromCode(venueCode) + ": Available");
                    continue;
                }
                PrintHelper.printRed(database.getVenueNameFromCode(venueCode) + ": Not Available");
            }
        }
    }

    public void printVenueAvailability(int venueCode, boolean isAvailable){
        if(isAvailable){
            PrintHelper.printGreen(Database.getInstance().getVenueNameFromCode(venueCode) + ": Available");
        }
        else{
            PrintHelper.printRed(Database.getInstance().getVenueNameFromCode(venueCode) + ": Not Available");
        }
    }

    public int getVenuesCount() {
        return Database.getInstance().getVenuesCount();
    }

    public boolean isValidVenueCode(int venueCode){
        List<Integer> venueCodesList = Database.getInstance().getVenueCodesList();
        return venueCodesList.contains(venueCode);
    }

    public String getNewVenueCode() {
        return String.valueOf(Database.getInstance().getNewVenueCode());
    }
}