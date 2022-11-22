package core.manager;

import core.user.Representative;
import core.user.User;
import core.venue.Reservation;
import core.venue.Venue;
import core.venue.VenueType;
import core.venue.VenueUpdate;
import database.Database;
import helper.DefensiveCopyHelper;
import helper.PrintHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

// The system that acts as an interface between the user and the database
public final class VenueManager implements AdminFunctions, RepresentativeFunctions {

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

    // Display Venue Details
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
    }

    @Override
    public void displayVenueDetails(int venueCode){
        Map<String, String> venueDetails = Database.getInstance().getVenues().get(venueCode).getVenueDetails();
        for(String key: venueDetails.keySet()){
            PrintHelper.printYellow(
                    String.format("%s: %s", key, venueDetails.get(key))
            );
        }
    }

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
    }

    // Check availability
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

            boolean isUpdateSuccessful = updateAvailability(currentReservation);
            if(!isUpdateSuccessful)
                return null;
        }

        return currentReservation;

    }

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

            boolean isUpdateSuccessful = updateAvailability(currentReservation);
            if(!isUpdateSuccessful)
                return null;
        }
        return currentReservation;

    }

    // Update availability
    /**
     * This method updates the database after a new reservation has been made
     *
     * @param reservationDetails Reservation object which contains the reservation details
     *
     * @return Returns
     *         'true', if updated the database successfully
     *         'false', otherwise
     */
    // Called by the reserveVenue and cancelVenue functions
    private boolean updateAvailability(Reservation reservationDetails){

        boolean isAddedToVenueReservation = Database.getInstance().addToVenueReservationDetails(reservationDetails.getVenueCode(), reservationDetails);
        if(!isAddedToVenueReservation)
            return false;

        boolean isAddedToUserReservation = Database.getInstance().addToUserReservationDetails(reservationDetails.getUsername(), reservationDetails);
        if(!isAddedToUserReservation)
            return false;

        boolean isAddedToAccessIdUserMap = Database.getInstance().addToAccessIdUserMap(reservationDetails.getAccessId(), reservationDetails.getUsername());
        if(!isAddedToAccessIdUserMap)
            return false;

        return true;
    }

    // Cancel Venue
    @Override
    public boolean cancelVenue(int venueCode, int accessId, String username) {
        Database database = Database.getInstance();

        boolean isRemovedFromVenueReservation = database.removeFromVenueReservationDetails(venueCode, accessId);
        if(!isRemovedFromVenueReservation)
            return false;

        boolean isRemovedFromUserReservation = database.removeFromUserReservationDetails(accessId, username);
        if(!isRemovedFromUserReservation)
            return false;

        return true;
    }
    
    @Override
    public boolean cancelVenue(int venueCode, int accessId, LocalDate from, LocalDate to, String username) {
        boolean isRemovedFromVenueReservation = Database.getInstance().removeFromVenueReservationDetails(venueCode, accessId, from, to);
        if(!isRemovedFromVenueReservation)
            return false;

        boolean isRemovedFromUserReservation = Database.getInstance().removeFromUserReservationDetails(accessId, from, to, username);
        if(!isRemovedFromUserReservation)
            return false;

        return true;
    }

    // Change Venue

    // calls the 'checkAvailability, 'updateAvailability' and 'cancelVenue' functions
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

        boolean isAvailable = checkAvailability(currentReservation.getReservedDates(), newVenueCode);

        if(! isAvailable){
            return null;
        }
        currentReservation.setVenueCode(newVenueCode);

        boolean isUpdateSuccessful = updateAvailability(currentReservation);
        if(!isUpdateSuccessful)
            return null;

        cancelVenue(oldVenueCode, accessId, currentReservation.getUsername());

        return currentReservation;
    }

    /**
     * This method checks whether a list of dates given is available in the given venue code
     *
     * @param reservedDates the list of dates for which the availability has to be checked
     * @param newVenueCode the venue in which the availability of dates has to be checked
     *
     * @return Returns
     *         'true' only if all the dates in the input list are available,
     *         'false' otherwise
     */
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

    // Update User Database
    @Override
    public boolean updateUserDatabase(User user) {
        Database.getInstance().addToUsers(user.getUsername(), user);
        return true;
    }

    // Change User password
    @Override
    public boolean changeUserPassword(String username, String newPassword) {
        return Database.getInstance().changeUserPassword(username, newPassword);
    }

    // Check valid Date(s)
    /**
     * This method checks whether the dates between 'from' and 'to' are present in the reservation details of the provided access id
     * This check is done during the cancellation process to let know the user that they have entered date(s) that is/are not in the reservation
     *
     * @param accessId The access id of the reservation for which the dates have to be checked
     * @param from The date from which the reservation has to be checked (start date)
     * @param to The date to which the reservation has to be checked (end date)
     * @param username The username of the user whose reservation details has to be got
     *                 
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

    // Check Username Existence
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

    // Only admin is authorized to call this method
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

    // Only admin is authorized to call this method
    @Override
    public boolean removeUser(String username) {
        Database database = Database.getInstance();
        database.removeFromUserCredentials(username);
        database.removeFromUsers(username);
        return true;
    }

    // Only admin is authorized to call this method
    @Override
    public Map<String, String> getOtherUserPersonalDetails(String username) {
        Database database = Database.getInstance();
        return DefensiveCopyHelper.getDefensiveCopyMap(database.getUsers().get(username).getPersonalDetails());
    }

    // Only admin is authorized to call this method
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

    // Change other user's password
    @Override
    public boolean changeOtherUserPassword(String username, String newPassword) {
        return Database.getInstance().changeUserPassword(username, newPassword);
    }

    // Maximum Possible Reservation Date
    @Override
    public LocalDate getMaxPossibleReservationDate(){
        return Database.getInstance().getMaxPossibleReservationDate();
    }

    @Override
    public boolean setMaxPossibleReservationDate(LocalDate maxPossibleDate) {
        Database.getInstance().setMaxPossibleReservationDate(maxPossibleDate);
        return true;
    }

    // Print Venues Availability

    // This function gets the venue codes of all the available venues as the input.
    // It gets all the venues from the database.
    // It then checks whether the venue is in the available venues list.
    // if present, it prints "Available" else, prints "Not Available"
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

    // This function is used to print the availability of a single venue
    public void printVenueAvailability(int venueCode, boolean isAvailable){
        System.out.println();
        if(isAvailable){
            PrintHelper.printGreen(Database.getInstance().getVenueNameFromCode(venueCode) + ": Available");
        }
        else{
            PrintHelper.printRed(Database.getInstance().getVenueNameFromCode(venueCode) + ": Not Available");
        }
    }

    // Check valid Venue Code
    public boolean isValidVenueCode(int venueCode){
        List<Integer> venueCodesList = Database.getInstance().getVenueCodesList();
        return venueCodesList.contains(venueCode);
    }

    // Get new Venue Code
    public String getNewVenueCode() {
        return String.valueOf(Database.getInstance().getNewVenueCode());
    }


    public Map<Integer, String> getAllVenueCodesWithName() {
        List<Integer> venueCodesList = Database.getInstance().getVenueCodesList();
        Map<Integer, String> map = new LinkedHashMap<>();
        for(int venueCode: venueCodesList){
            map.put(venueCode, Database.getInstance().getVenueNameFromCode(venueCode));
        }
        return map;
    }
}