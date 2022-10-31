import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Admin extends Representative{

    public Admin(String username, String phoneNumber, String emailId) {
        super(username, phoneNumber, emailId);
    }

    public boolean updateVenueDetails(){
        return false;
    }

    public TreeMap<Integer, ArrayList<LocalDate>> getReservationDetails(Integer venueCode){
        return Database.getInstance().reservationDetails.get(venueCode);
    }

    public boolean addUser(String username, String password, String emailId, String phoneNumber){
        return venueManager.addUser(username, password, emailId, phoneNumber);
    }

    public boolean removeUser(String username){
        return venueManager.removeUser(username);
    }

    public Map getOtherUserPersonalDetails(String username){
        return venueManager.getOtherUserPersonalDetails(username);
    }

    public Map getOtherUserRegistrationDetails(String username){
        return venueManager.getOtherUserRegistrationDetails(username);
    }
}
