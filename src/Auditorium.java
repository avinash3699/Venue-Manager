import java.util.ArrayList;
import java.util.Map;

public class Auditorium extends Venue{

    // Instance Variables
    boolean isMicStandAvailable;
    int noOfDisplayScreen;

    Speaker speaker;
    ArrayList<Microphone> microphone;
    public Stage stage;

    // Constructor
    Auditorium(){
    }

    // Methods
    @Override
    public Map<String, String> getVenueDetails() {
        super.getVenueDetails()
        Map<String, String> additionalVenueDetails = getAdditionalVenueDetails();
        return null;
    }

    @Override
    public Map<String, String> getAdditionalVenueDetails() {
        return null;
    }
}
