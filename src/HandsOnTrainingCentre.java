import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class HandsOnTrainingCentre extends Venue{

    // Instance variables
    public boolean isMicStandAvailable;

    private Projector projector;
    private Ethernet ethernet;
    private ArrayList<Microphone> microphone;
    private Speaker speaker;

    // Constructor
    public HandsOnTrainingCentre(String hallName, String hallCode, String location, String seatingCapacity, boolean isAirConditioned, boolean isWifiAvailable, boolean isChargingPortsAvailable, boolean isMicStandAvailable) {
        super(hallName, hallCode, location, seatingCapacity, isAirConditioned, isWifiAvailable, isChargingPortsAvailable, "Hands-On training");
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
    public Map<String, String> getAdditionalVenueDetails() {
        return new LinkedHashMap(){
            {
                put("Mic Stand", getAvailabilityFromBoolean(isMicStandAvailable));
            }
        };
    }
}
