import java.util.LinkedHashMap;
import java.util.Map;

abstract class Venue {

    public Venue(String hallName, String hallCode, String location, String seatingCapacity, boolean isAirConditioned, boolean isWifiAvailable, boolean isChargingPortsAvailable) {
        this.hallName = hallName;
        this.hallCode = hallCode;
        this.location = location;
        this.seatingCapacity = seatingCapacity;
        this.isAirConditioned = isAirConditioned;
        this.isWifiAvailable = isWifiAvailable;
        this.isChargingPortsAvailable = isChargingPortsAvailable;
    }

    String hallName, hallCode, location, seatingCapacity;
    boolean isAirConditioned, isWifiAvailable, isChargingPortsAvailable;

    public Map<String, String> getVenueDetails(){
        return new LinkedHashMap(){
            {
                put("Hall Name", hallName);
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