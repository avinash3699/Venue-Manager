package core.user;

import core.manager.RepresentativeManager;
import core.venue.Reservation;
import core.venue.VenueType;

import java.time.LocalDate;
import java.util.*;

public class Representative extends User{

    RepresentativeManager venueManager;

    public Representative(String username, String phoneNumber, String emailId, RepresentativeManager venueManager) {
        super(username, phoneNumber, emailId);
//        this.username = username;
//        this.phoneNumber = phoneNumber;
//        this.emailId = emailId;
        this.venueManager = venueManager;
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

    // Print Venue Availability
    public void printVenuesAvailability(ArrayList<Integer> availableVenueCodes) {
        venueManager.printVenuesAvailability(availableVenueCodes);
    }

    public void printVenuesAvailability(ArrayList<Integer> availableVenueCodes, VenueType inputType) {
        venueManager.printVenuesAvailability(availableVenueCodes, inputType);
    }

    // Update User Database
    @Override
    public boolean updateUserDatabase(User user) {
        return venueManager.updateUserDatabase(user);
    }

    @Override
    public List<Reservation> getReservationDetails() {
        return venueManager.getReservationDetails(this.getUsername());
    }

//    @Override
//    public User setPhoneNumber(String phoneNumber) {
//        User user = super.setPhoneNumber(phoneNumber);
//        venueManager.updateUserDatabase(user);
//        return user;
//    }

//    @Override
//    public boolean setEmailId(String emailId) {
//        return false;
//    }

    public boolean updatePhoneNumber(String phoneNumber){
        super.setPhoneNumber(phoneNumber);

        return super.getPhoneNumber().equals(phoneNumber);
    }

    public boolean updateEmailId(String emailId){
        super.setEmailId(emailId);

        return super.getEmailId().equals(emailId);
    }

}