package core.venue;

import core.venue.properties.Ethernet;
import core.venue.properties.Microphone;
import core.venue.properties.Projector;
import core.venue.properties.Speaker;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class HandsOnTrainingCentre extends Venue{

    // Instance variables
    private boolean isMicStandAvailable;

    //TODO initialize hall properties with their details
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

    private Map<String, String> getAdditionalVenueDetails() {
        return new LinkedHashMap<String, String>(){
            {
                put("Mic Stand", isMicStandAvailable? "Available": "Not Available");
            }
        };
    }
}
