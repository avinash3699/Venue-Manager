package core.manager;

import core.venue.Reservation;
import core.venue.Venue;
import core.venue.VenueUpdate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * an interface which declares all the functions that can be performed by the 'Admin'
 * it inherits the 'RepresentativeFunctions' interface, as the admin can also perform the functions declared in RepresentativeFunctions
 * this interface is implemented by "VenueManager" in core/manager/VenueManager.java
 */
public interface AdminFunctions extends RepresentativeFunctions {

    /**
     * This method is used to add a new user
     *
     * @param username The username of the new user
     * @param password The password of the new user
     * @param emailId The email of the new user
     * @param phoneNumber The phone number of the new user
     *
     * @return Returns
     *         'true', if user added successfully
     *         'false', otherwise
     */
    boolean addUser(String username, String password, String emailId, String phoneNumber);

    /**
     * This method is used to remove an existing user
     *
     * @param username The username of the user to be removed
     *
     * @return Returns
     *         'true', if user removed successfully
     *         'false', otherwise
     */
    boolean removeUser(String username);

    /**
     * This method is used to get the personal details of the user
     *
     * @param username The username of the user whose details have to be got.
     *
     * @return Returns the personal details of the user as a 'Map' object
     */
    Map<String, String> getOtherUserPersonalDetails(String username);

    /**
     * This method is used to get the reservation details of the user
     *
     * @param username The username of the user whose details have to be got.
     *
     * @return Returns the reservation details of the user as a 'List' of 'Reservation' objects
     */
    List<Reservation> getOtherUserReservationDetails(String username);

    /**
     * This method is used to add a new venue
     *
     * @param newVenue The new 'Venue' object that is to be added
     *
     * @return Returns
     *         'true', if added a new venue successfully
     *         'false', otherwise
     */
    boolean addVenue(Venue newVenue);

    /**
     * This method is used to remove an existing venue
     *
     * @param venueCode The code of the venue that is to be removed
     *
     * @return Returns
     *         'true', if venue removed successfully
     *         'false', otherwise
     */
    boolean removeVenue(int venueCode);

    /**
     * This method is used to update a detail of the venue
     *
     * @param venueCode The code of the venue for which the detail has to be changed
     * @param newValue The new detail that is to be updated
     * @param updateOption VenueUpdate(enum) This specifies the detail to be updated
     *
     * @return Returns
     *         'true', if updated successfully
     *         'false', otherwise
     */
    boolean updateVenue(int venueCode, String newValue, VenueUpdate updateOption);

    /**
     * This method is used to change other user's password
     *
     * @param username The username of the used whose password has to be changed
     * @param newPassword The new password that is to be updated
     *
     * @return Returns
     *         'true', if password changed successfully
     *         'false', otherwise
     */
    boolean changeOtherUserPassword(String username, String newPassword);

    /**
     * This method is used to get the maximum possible reservation date
     * 'Maximum Possible Reservation Date' is the date beyond which a user cannot reserve a venue
     *
     * @return Returns the 'Maximum Possible Reservation Date'
     */
    LocalDate getMaxPossibleReservationDate();

    /**
     * This method is used to set the maximum possible reservation date
     * 'Maximum Possible Reservation Date' is the date beyond which a user cannot reserve a venue
     *
     * @param maxPossibleDate The date that is to be set as the new 'Maximum Possible Reservation Date'
     *
     * @return Returns
     *         'true', if set successfully
     *         'false', otherwise
     */
    boolean setMaxPossibleReservationDate(LocalDate maxPossibleDate);

}