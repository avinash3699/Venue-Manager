package core.venue;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Venue {

    // instance variables
    private String venueName, venueCode, location, seatingCapacity;
    private boolean isAirConditioned, isWifiAvailable, isChargingPortsAvailable;
    private final VenueType venueType;

    // constructor
    protected Venue(String venueName, String venueCode, String location, String seatingCapacity, boolean isAirConditioned, boolean isWifiAvailable, boolean isChargingPortsAvailable, VenueType venueType) {
        this.venueName = venueName;
        this.venueCode = venueCode;
        this.location = location;
        this.seatingCapacity = seatingCapacity;
        this.isAirConditioned = isAirConditioned;
        this.isWifiAvailable = isWifiAvailable;
        this.isChargingPortsAvailable = isChargingPortsAvailable;
        this.venueType = venueType;
    }

    // method to return the details of the venue
    public Map<String, String> getVenueDetails(){
        return new LinkedHashMap<String, String>(){
            {
                put("Venue Name", venueName);
                put("Venue Type", venueType.toString());
                put("Venue Code", venueCode);
                put("Venue Location", location);
                put("Seating Capacity", seatingCapacity);
                put("Air Conditioner", isAirConditioned? "Available": "Not Available");
                put("Wifi", isWifiAvailable? "Available": "Not Available");
                put("Individual Charging Ports", isChargingPortsAvailable? "Available": "Not Available");
            }
        };
    }

    public VenueType getType() {
        return this.venueType;
    }

    public String getVenueName(){
        return venueName;
    }

    protected abstract Map<String, String> getAdditionalVenueDetails();
}