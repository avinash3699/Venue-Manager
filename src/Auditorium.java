import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Auditorium extends Venue{

    // Instance Variables
    boolean isMicStandAvailable;
    String noOfDisplayScreen;

    Speaker speaker;
    ArrayList<Microphone> microphone;
    public Stage stage;

    // Constructor


    public Auditorium(String hallName, String hallCode, String location, String seatingCapacity, boolean isAirConditioned, boolean isWifiAvailable, boolean isChargingPortsAvailable, boolean isMicStandAvailable, String noOfDisplayScreen) {
        super(hallName, hallCode, location, seatingCapacity, isAirConditioned, isWifiAvailable, isChargingPortsAvailable);
        this.isMicStandAvailable = isMicStandAvailable;
        this.noOfDisplayScreen = noOfDisplayScreen;
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
                put("No. of Display Screens", noOfDisplayScreen);
            }
        };
    }
}
