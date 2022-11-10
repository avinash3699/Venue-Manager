package core.manager;

import core.user.User;
import core.venue.Reservation;
import core.venue.VenueType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * an interface which declares all the functions that can be performed by a 'Representative'
 * this interface is implemented by "VenueManager" in core/manager/VenueManager.java
 */
public interface RepresentativeFunctions {

    /**
     * This method displays the details of all the venues
     */
    void displayVenueDetails();

    /**
     * This method displays the details of a single venue
     *
     * @param venueCode The code of the venue for which the details have to be displayed
     */
    void displayVenueDetails(int venueCode);

    /**
     * This method displays the details of, only the venues that is of the given type
     * VenueType: CONFERENCE, HANDS_ON_TRAINING, AUDITORIUM
     *
     * @param type VenueType(enum) The type of the venues for which the details have to be displayed
     */
    void displayVenueDetails(VenueType type);


    /**
     * This method checks the availability of all the venues for the given 'from date' to 'end date'
     *
     * @param from The date from which the venue has to be checked for availability (start date)
     * @param to The date to which the venue has to be checked for availability (end date)
     *
     * @return Returns the list of venue codes of available venues
     */
    ArrayList<Integer> checkAvailability(LocalDate from, LocalDate to);

    /**
     * This method checks the availability of venues that is of the given 'type' for the given 'from date' to 'end date'
     *
     * @param type VenueType(enum) The type of the venues for which the availability has to be checked
     * @param from The date from which the venue has to be checked for availability (start date)
     * @param to The date to which the venue has to be checked for availability (end date)
     *
     * @return Returns the list of venue codes of available venues
     */
    ArrayList<Integer> checkAvailability(VenueType type, LocalDate from, LocalDate to);

    /**
     * This method checks the availability of a specific venue for the given 'venue code, ''from date' to 'end date'
     *
     *
     * @param venueCode The code of the venue for which the availability has to be checked
     * @param from The date from which the venue has to be checked for availability (start date)
     * @param to The date to which the venue has to be checked for availability (end date)
     *
     * @return Returns
     *         'true' if available,
     *         'false' otherwise
     */
    boolean checkAvailability(int venueCode, LocalDate from, LocalDate to);

    /**
     * This method reserves the venue of given 'type' for the given 'from date' to 'to date'
     *
     * @param type VenueType(enum) The type of the venues for which the reservation can be done
     * @param from The date from which the venue has to be reserved (start date)
     * @param to The date to which the venue has to be reserved (end date)
     * @param username The username of the user making the reservation
     *
     * @return Returns
     *         'Reservation' object, if reservation is successful
     *         'null', if reservation fails
     */
    Reservation reserveVenue(VenueType type, LocalDate from, LocalDate to, String username);

    /**
     * This method reserves the venue with the given venue code
     *
     * @param venueCode The code of the venue that has to be reserved
     * @param from The date from which the venue has to be reserved (start date)
     * @param to The date to which the venue has to be reserved (end date)
     * @param username The username of the user making the reservation
     *
     * @return Returns
     *         'Reservation' object, if reservation is successful
     *         'null', if reservation fails
     */
    Reservation reserveVenue(int venueCode, LocalDate from, LocalDate to, String username);

    /**
     * This method cancels a reserved venue of the given 'access id'
     * This method 'cancels the entire reservation'
     *
     * @param venueCode The code of the venue that is to be cancelled
     * @param accessId The access id of the reservation. It uniquely identifies a reservation
     * @param username The username of the user making the cancellation
     *
     * @return Returns
     *         'true', if cancelled successfully
     *         'false', otherwise
     */
    boolean cancelVenue(int venueCode, int accessId, String username);

    /**
     * This method cancels a reserved venue of the given 'access id'
     * This method 'cancels the dates from a reservation between the given "from" and "to" dates'
     * This method also 'cancels a single date' if the 'from' and 'to' dates are same
     *
     * @param venueCode The code of the venue that is to be cancelled
     * @param accessId The access id of the reservation. It uniquely identifies a reservation
     * @param from The date from which the venue has to be cancelled (start date)
     * @param to The date to which the venue has to be cancelled (end date)
     * @param username The username of the user making the cancellation
     *
     * @return Returns
     *         'true', if cancelled successfully
     *         'false', otherwise
     */
    boolean cancelVenue(int venueCode, int accessId, LocalDate from, LocalDate to, String username);

    /**
     * This method is used to change the reservation from one venue to another venue
     * It changes only the venue. The dates and the access id will be the same as that of the old reservation
     *
     * @param oldVenueCode The current venue code of the reservation
     * @param accessId The access id of the reservation. It uniquely identifies a reservation
     * @param newVenueCode The new venue code that will be changed to
     * @param username The username of the user making the change
     *
     * @return Returns
     *         'Reservation' object, if venue change is successful
     *         'null', otherwise
     */
    Reservation changeVenue(int oldVenueCode, int accessId, int newVenueCode, String username);

    /**
     * This method changes the user password in the database
     *
     * @param username the username of the user whose password has to be changed
     * @param newPassword the new password that is to be updated
     *
     * @return Returns
     *         'true', if changed password successfully
     *         'false', otherwise
     */
    boolean changeUserPassword(String username, String newPassword);

    /**
     * This method is used to update the User details of the user
     *
     * @param user The 'User' object that is to be updated in the database
     *
     * @return Returns
     *         'true', if updated successfully
     *         'false', otherwise
     */
    boolean updateUserDatabase(User user);

    /**
     * This method gets the reservation details of the user from the database
     *
     * @param username The username of the user whose details have to be got.
     *
     * @return Returns the reservation details of the user as a list of 'Reservation' objects
     */
    List<Reservation> getReservationDetails(String username);

}