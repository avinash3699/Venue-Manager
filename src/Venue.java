import java.util.LinkedHashMap;
import java.util.Map;

abstract class Venue {

    // instance variables
    String venueName, venueCode, location, seatingCapacity;
    boolean isAirConditioned, isWifiAvailable, isChargingPortsAvailable;
    VenueType venueType;

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

    // method to return the details of a venue
    public Map<String, String> getVenueDetails(){
        return new LinkedHashMap(){
            {
                put("Venue Name", venueName);
                put("Venue Type", venueType.toString());
                put("Venue Code", venueCode);
                put("Venue Location", location);
                put("Seating Capacity", seatingCapacity);
                put("Air Conditioner", getAvailabilityFromBoolean(isAirConditioned));
                put("Wifi", getAvailabilityFromBoolean(isWifiAvailable));
                put("Individual Charging Ports", getAvailabilityFromBoolean(isChargingPortsAvailable));
            }
        };
    }

    protected String getAvailabilityFromBoolean(boolean isAirConditioned) {
        return (isAirConditioned)? "Available": "Not Available";
    }

    public abstract Map<String, String> getAdditionalVenueDetails();

    public VenueType getType() {
        return this.venueType;
    }
}