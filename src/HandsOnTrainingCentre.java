import java.util.ArrayList;
import java.util.Map;

public class HandsOnTrainingCentre extends Venue{

    // Instance variables
    public boolean isMicStandAvailable;

    private Projector projector;
    private Ethernet ethernet;
    private ArrayList<Microphone> microphone;
    private Speaker speaker;

    // Constructor
    HandsOnTrainingCentre(){
    }

    // Methods
    @Override
    public Map<String, String> getVenueDetails() {
        Map<String, String> venueDetails = super.getVenueDetails();
        Map<String, String> additionalVenueDetails = getAdditionalVenueDetails();
        return null;
    }

    @Override
    public Map<String, String> getAdditionalVenueDetails() {
        return null;
    }
}
