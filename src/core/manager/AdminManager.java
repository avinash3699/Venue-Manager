package core.manager;

import core.venue.Reservation;

import java.util.List;
import java.util.Map;

public interface AdminManager extends RepresentativeManager {

    boolean addUser(String username, String password, String emailId, String phoneNumber);

    boolean removeUser(String username);

    Map<String, String> getOtherUserPersonalDetails(String username);

    List<Reservation> getOtherUserRegistrationDetails(String username);

}