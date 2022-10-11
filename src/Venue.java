import java.util.HashMap;
import java.util.Map;

abstract class Venue {

    String hallName, hallCode, location, seatingCapacity;
    boolean isAirConditioned, isWifiAvailable, isChargingPortsAvailable;

    public Map<String, String> getVenueDetails(){
        return new HashMap(){
            {
                put("Hall Code", hallCode);
                put("Hall Name", hallName);
                put("Hall Location", location);
                put("Seating Capacity", seatingCapacity);
                put("Air Conditioner", getAvailabilityFromBoolean(isAirConditioned));
                put("Wifi", getAvailabilityFromBoolean(isWifiAvailable));
                put("Individual Charging Ports", getAvailabilityFromBoolean(isChargingPortsAvailable));
            }

        };
    }

    private String getAvailabilityFromBoolean(boolean isAirConditioned) {
        return (isAirConditioned)? "Available": "Not Available";
    }

    public abstract Map<String, String> getAdditionalVenueDetails();
}