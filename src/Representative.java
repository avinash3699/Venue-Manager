import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.HashMap;

public class Representative extends User{

    private LinkedHashMap<Integer, HashMap<Integer, ArrayList<LocalDate>>> reservationDetails;

    public Representative(String username, String phoneNumber, String emailId) {
        super(username, phoneNumber, emailId);
        reservationDetails = new LinkedHashMap<>();
    }

    public boolean updatePhoneNumber(String phoneNumber){
        super.setPhoneNumber(phoneNumber);

        if(super.getPhoneNumber() == phoneNumber)
            return true;
        return false;
    }

    public boolean updateEmailId(String emailId){
        super.setEmailId(emailId);

        if(super.getEmailId() == emailId)
            return true;
        return false;
    }

    public LinkedHashMap<Integer, HashMap<Integer, ArrayList<LocalDate>>> getReservationDetails() {
        return reservationDetails;
    }

    public void addReservationDetails(int accessId, int venueCode, ArrayList<LocalDate> dates){
        reservationDetails.put(
            accessId,
            new HashMap(){
                {
                    put(venueCode, dates);
                }
            }
        );
    }

    public void removeReservationDetails(int accessId){
        reservationDetails.remove(accessId);
    }

    public void removeReservationDetails(int accessId, LocalDate from, LocalDate to){
        int firstKey = reservationDetails.get(accessId).keySet().stream().findFirst().get();
        System.out.println("First Key " + firstKey);
        ArrayList<LocalDate> reservedDates = reservationDetails.get(accessId).get(firstKey);
        for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
            reservedDates.remove(date);
        }
        reservationDetails.put(
            accessId,
            new HashMap(){
                {
                    put(firstKey, reservedDates);
                }
            }
        );
    }
}