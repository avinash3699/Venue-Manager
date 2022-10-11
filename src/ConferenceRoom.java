import java.util.Map;

public class ConferenceRoom extends Venue{

    // Instance variables
    public boolean isWhiteBoardAvailable;

    private Projector projector;
    private Ethernet ethernet;

    // Constructor
    ConferenceRoom(){

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