package core.manager;

import core.venue.Reservation;
import core.venue.Venue;
import core.venue.VenueUpdate;

import java.util.List;
import java.util.Map;

public interface AdminManager extends RepresentativeManager {

    boolean addUser(String username, String password, String emailId, String phoneNumber);

    boolean removeUser(String username);

    Map<String, String> getOtherUserPersonalDetails(String username);

    List<Reservation> getOtherUserReservationDetails(String username);

    boolean addVenue(Venue newVenue);

    boolean removeVenue(int venueCode);

    boolean updateVenue(int venueCode, String newValue, VenueUpdate updateOption);

}