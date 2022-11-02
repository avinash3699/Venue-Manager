package core.manager;

import core.user.User;
import core.venue.Reservation;
import core.venue.VenueType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface RepresentativeManager {

    void displayVenueDetails();
    void displayVenueDetails(int venueCode);
    void displayVenueDetails(VenueType type);

    ArrayList<Integer> checkAvailability(LocalDate from, LocalDate to);
    ArrayList<Integer> checkAvailability(VenueType type, LocalDate from, LocalDate to);
    boolean checkAvailability(int venueCode, LocalDate from, LocalDate to);

    Reservation reserveVenue(VenueType type, LocalDate from, LocalDate to, String username);
    Reservation reserveVenue(int venueCode, LocalDate from, LocalDate to, String username);

    boolean cancelVenue(int venueCode, int accessId, String username);
    boolean cancelVenue(int venueCode, int accessId, LocalDate from, LocalDate to, String username);

    Reservation changeVenue(int oldVenueCode, int accessId, int newVenueCode, String username);

    void printVenuesAvailability(ArrayList<Integer> availableVenueCodes);
    void printVenuesAvailability(ArrayList<Integer> availableVenueCodes, VenueType inputType);

    boolean updateUserDatabase(User user);

    List<Reservation> getReservationDetails(String username);
}