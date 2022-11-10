package core.venue;

import core.venue.properties.Ethernet;
import core.venue.properties.Projector;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConferenceRoom extends Venue{

    // Instance variables
    private boolean isWhiteBoardAvailable;

    //TODO initialize hall properties with their details
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

    private Map<String, String> getAdditionalVenueDetails() {
        return new LinkedHashMap<String, String>(){
            {
                put("White Board", isWhiteBoardAvailable? "Available": "Not Available");
            }
        };
    }

}