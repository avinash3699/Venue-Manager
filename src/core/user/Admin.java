package core.user;

import core.manager.AdminFunctions;
import core.venue.Reservation;
import core.venue.Venue;
import core.venue.VenueType;
import core.venue.VenueUpdate;
import helper.DefensiveCopyHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Admin extends User{

    AdminFunctions venueManager;

    public Admin(String username, String phoneNumber, String emailId, AdminFunctions venueManager) {
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
    @Override
    public void displayVenueDetails() {
        venueManager.displayVenueDetails();
    }

    @Override
    public void displayVenueDetails(int venueCode) {
        venueManager.displayVenueDetails(venueCode);
    }

    @Override
    public void displayVenueDetails(VenueType type) {
        venueManager.displayVenueDetails(type);
    }

    // Check Venue Availability
    @Override
    public ArrayList<Integer> checkAvailability(LocalDate from, LocalDate to) {
        return venueManager.checkAvailability(from, to);
    }

    @Override
    public ArrayList<Integer> checkAvailability(VenueType type, LocalDate from, LocalDate to) {
        return venueManager.checkAvailability(type, from, to);
    }

    @Override
    public boolean checkAvailability(int venueCode, LocalDate from, LocalDate to) {
        return venueManager.checkAvailability(venueCode, from, to);
    }

    // Reserve Venue
    @Override
    public Reservation reserveVenue(VenueType type, LocalDate from, LocalDate to) {
        return venueManager.reserveVenue(type, from, to, this.getUsername());
    }

    @Override
    public Reservation reserveVenue(int venueCode, LocalDate from, LocalDate to) {
        return venueManager.reserveVenue(venueCode, from, to, this.getUsername());
    }

    // Cancel Venue
    @Override
    public boolean cancelVenue(int venueCode, int accessId) {
        return venueManager.cancelVenue(venueCode, accessId, this.getUsername());
    }

    @Override
    public boolean cancelVenue(int venueCode, int accessId, LocalDate from, LocalDate to) {
        return venueManager.cancelVenue(venueCode, accessId, from, to, this.getUsername());
    }

    // Change Venue
    @Override
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
        return new Admin(
            personalDetails.get("Username"),
            personalDetails.get("Phone Number"),
            personalDetails.get("Email Id"),
            venueManager
        );
    }

    // Admin only Operations
    public boolean addUser(String username, String password, String emailId, String phoneNumber){
        return venueManager.addUser(username, password, emailId, phoneNumber);
    }

    public boolean removeUser(String username){
        return venueManager.removeUser(username);
    }

    public Map<String, String> getOtherUserPersonalDetails(String username){
        return DefensiveCopyHelper.getDefensiveCopyMap(venueManager.getOtherUserPersonalDetails(username));
    }

    public List<Reservation> getOtherUserRegistrationDetails(String username){
        return DefensiveCopyHelper.getDefensiveCopyList(venueManager.getOtherUserReservationDetails(username));
    }

    public boolean addVenue(Venue newVenue){
        return venueManager.addVenue(newVenue);
    }

    public boolean removeVenue(int venueCode){
        return venueManager.removeVenue(venueCode);
    }

    public boolean updateVenueName(int venueCode, String newVenueName) {
        return venueManager.updateVenue(venueCode, newVenueName, VenueUpdate.NAME);
    }

    public boolean updateVenueSeatingCapacity(int venueCode, String newSeatingCapacity) {
        return venueManager.updateVenue(venueCode, newSeatingCapacity, VenueUpdate.SEATING_CAPACITY);
    }

    public LocalDate getMaxPossibleReservationDate() {
        return venueManager.getMaxPossibleReservationDate();
    }

    public boolean setMaxPossibleReservationDate(LocalDate maxPossibleDate) {
        return venueManager.setMaxPossibleReservationDate(maxPossibleDate);
    }

    public boolean changeOtherUserPassword(String username, String newPassword) {
        return venueManager.changeOtherUserPassword(username, newPassword);
    }
}