import java.util.LinkedHashMap;
import java.util.Map;

abstract class Venue {

    // instance variables
    String hallName, hallCode, location, seatingCapacity, type;
    boolean isAirConditioned, isWifiAvailable, isChargingPortsAvailable;

    // constructor
    protected Venue(String hallName, String hallCode, String location, String seatingCapacity, boolean isAirConditioned, boolean isWifiAvailable, boolean isChargingPortsAvailable, String type) {
        this.hallName = hallName;
        this.hallCode = hallCode;
        this.location = location;
        this.seatingCapacity = seatingCapacity;
        this.isAirConditioned = isAirConditioned;
        this.isWifiAvailable = isWifiAvailable;
        this.isChargingPortsAvailable = isChargingPortsAvailable;
        this.type = type;
    }

    // method to return the details of a venue
    public Map<String, String> getVenueDetails(){
        return new LinkedHashMap(){
            {
                put("Hall Name", hallName);
                put("Hall Type", type);
                put("Hall Code", hallCode);
                put("Hall Location", location);
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
}