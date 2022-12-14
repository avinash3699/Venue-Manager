package core.user;

import core.venue.Reservation;
import core.venue.VenueType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class User {

    private final String username;
    private String phoneNumber, emailId;

    public User(String username, String phoneNumber, String emailId) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
    }

    public Map<String, String> getPersonalDetails(){
        return new LinkedHashMap<String, String>(){
            {
                put("Username", username);
                put("Email Id", emailId);
                put("Phone Number", phoneNumber);
            }
        };
    }

    public String getUsername() {
        return username;
    }


    public abstract boolean setPhoneNumber(String phoneNumber);
    public abstract boolean setEmailId(String emailId);

    // abstract methods
    public abstract void displayVenueDetails();
    public abstract void displayVenueDetails(int venueCode);
    public abstract void displayVenueDetails(VenueType type);

    public abstract ArrayList<Integer> checkAvailability(LocalDate from, LocalDate to);
    public abstract ArrayList<Integer> checkAvailability(VenueType type, LocalDate from, LocalDate to);
    public abstract boolean checkAvailability(int venueCode, LocalDate from, LocalDate to);

    public abstract Reservation reserveVenue(VenueType type, LocalDate from, LocalDate to);
    public abstract Reservation reserveVenue(int venueCode, LocalDate from, LocalDate to);

    public abstract boolean cancelVenue(int venueCode, int accessId);
    public abstract boolean cancelVenue(int venueCode, int accessId, LocalDate from, LocalDate to);

    public abstract Reservation changeVenue(int oldVenueCode, int accessId, int newVenueCode);

    public abstract boolean changeUserPassword(String newPassword);

    public abstract List<Reservation> getReservationDetails();

    protected void changePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    protected void changeEmailId(String emailId) {
        this.emailId = emailId;
    }

    public abstract User clone();
}