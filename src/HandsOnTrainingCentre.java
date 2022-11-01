import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class HandsOnTrainingCentre extends Venue{

    // Instance variables
    private boolean isMicStandAvailable;

    private Projector projector;
    private Ethernet ethernet;
    private ArrayList<Microphone> microphone;
    private Speaker speaker;

    // Constructor
    public HandsOnTrainingCentre(String venueName, String venueCode, String location, String seatingCapacity, boolean isAirConditioned, boolean isWifiAvailable, boolean isChargingPortsAvailable, boolean isMicStandAvailable) {
        super(venueName, venueCode, location, seatingCapacity, isAirConditioned, isWifiAvailable, isChargingPortsAvailable, VenueType.HANDS_ON_TRAINING);
        this.isMicStandAvailable = isMicStandAvailable;
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
                put("Mic Stand", getAvailabilityFromBoolean(isMicStandAvailable));
            }
        };
    }
}
