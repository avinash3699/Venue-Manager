package core.user;

import core.manager.RepresentativeFunctions;
import core.venue.Reservation;
import core.venue.VenueType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Representative extends User{

    RepresentativeFunctions venueManager;

    public Representative(String username, String phoneNumber, String emailId, RepresentativeFunctions venueManager) {
        super(username, phoneNumber, emailId);
        this.venueManager = venueManager;
    }

    // Change Phone number
    @Override
    public boolean setPhoneNumber(String phoneNumber) {
        super.changePhoneNumber(phoneNumber);
        return venueManager.updateUserDatabase(this);
    }

    // Change Mail id
    @Override
    public boolean setEmailId(String emailId) {
        super.changeEmailId(emailId);
        return venueManager.updateUserDatabase(this);
    }

    // Display Venue Details
    public void displayVenueDetails() {
        venueManager.displayVenueDetails();
    }

    public void displayVenueDetails(int venueCode) {
        venueManager.displayVenueDetails(venueCode);
    }

    public void displayVenueDetails(VenueType type) {
        venueManager.displayVenueDetails(type);
    }

    // Check Venue Availability
    public ArrayList<Integer> checkAvailability(LocalDate from, LocalDate to) {
        return venueManager.checkAvailability(from, to);
    }

    public ArrayList<Integer> checkAvailability(VenueType type, LocalDate from, LocalDate to) {
        return venueManager.checkAvailability(type, from, to);
    }

    public boolean checkAvailability(int venueCode, LocalDate from, LocalDate to) {
        return venueManager.checkAvailability(venueCode, from, to);
    }

    // Reserve Venue
    public Reservation reserveVenue(VenueType type, LocalDate from, LocalDate to) {
        return venueManager.reserveVenue(type, from, to, this.getUsername());
    }

    public Reservation reserveVenue(int venueCode, LocalDate from, LocalDate to) {
        return venueManager.reserveVenue(venueCode, from, to, this.getUsername());
    }

    // Cancel Venue
    public boolean cancelVenue(int venueCode, int accessId) {
        return venueManager.cancelVenue(venueCode, accessId, this.getUsername());
    }

    public boolean cancelVenue(int venueCode, int accessId, LocalDate from, LocalDate to) {
        return venueManager.cancelVenue(venueCode, accessId, from, to, this.getUsername());
    }

    // Change Venue
    public Reservation changeVenue(int oldVenueCode, int accessId, int newVenueCode) {
        return venueManager.changeVenue(oldVenueCode, accessId, newVenueCode, this.getUsername());
    }

    // Change User password
    @Override
    public boolean changeUserPassword(String newPassword) {
        return venueManager.changeUserPassword(this.getUsername(), newPassword);
    }

    @Override
    public List<Reservation> getReservationDetails() {
        return venueManager.getReservationDetails(this.getUsername());
    }

    @Override
    public User clone() {
        Map<String, String> personalDetails = this.getPersonalDetails();
        return new Representative(
            personalDetails.get("Username"),
            personalDetails.get("Phone Number"),
            personalDetails.get("Email Id"),
            venueManager
        );
    }

}