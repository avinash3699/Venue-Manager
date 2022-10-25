import java.time.LocalDate;
import java.util.ArrayList;
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

}
