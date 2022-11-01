import java.util.LinkedHashMap;
import java.util.Map;

public class ConferenceRoom extends Venue{

    // Instance variables
    private boolean isWhiteBoardAvailable;

    private Projector projector;
    private Ethernet ethernet;

    // Constructor
    public ConferenceRoom(String venueName, String venueCode, String location, String seatingCapacity, boolean isAirConditioned, boolean isWifiAvailable, boolean isChargingPortsAvailable, boolean isWhiteBoardAvailable) {
        super(venueName, venueCode, location, seatingCapacity, isAirConditioned, isWifiAvailable, isChargingPortsAvailable, VenueType.CONFERENCE);
        this.isWhiteBoardAvailable = isWhiteBoardAvailable;
    }

    // Methods
    @Override
    public Map<String, String> getVenueDetails() {
        Map<String, String> venueDetails = super.getVenueDetails();
        Map<String, String> additionalVenueDetails = getAdditionalVenueDetails();
        venueDetails.putAll(additionalVenueDetails);
        return venueDetails;
    }

    @Override
    protected Map<String, String> getAdditionalVenueDetails() {
        return new LinkedHashMap(){
            {
                put("White Board", getAvailabilityFromBoolean(isWhiteBoardAvailable));
            }
        };
    }

}