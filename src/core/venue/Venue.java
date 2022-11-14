package core.venue;

import java.util.LinkedHashMap;
import java.util.Map;

public class Venue {

    // instance variables
    private final String venueCode, location;
    private final VenueType venueType;
    private String venueName;
    private String seatingCapacity;
    private boolean isAirConditioned, isWifiAvailable, isChargingPortsAvailable;

    // constructor
//    protected Venue(String venueName, String venueCode, String location, String seatingCapacity, boolean isAirConditioned, boolean isWifiAvailable, boolean isChargingPortsAvailable, VenueType venueType) {
//        this.venueName = venueName;
//        this.venueCode = venueCode;
//        this.location = location;
//        this.seatingCapacity = seatingCapacity;
//        this.isAirConditioned = isAirConditioned;
//        this.isWifiAvailable = isWifiAvailable;
//        this.isChargingPortsAvailable = isChargingPortsAvailable;
//        this.venueType = venueType;
//    }

    protected Venue(Builder builder) {
        this.venueName = builder.venueName;
        this.venueCode = builder.venueCode;
        this.location = builder.location;
        this.seatingCapacity = builder.seatingCapacity;
        this.isAirConditioned = builder.isAirConditioned;
        this.isWifiAvailable = builder.isWifiAvailable;
        this.isChargingPortsAvailable = builder.isChargingPortsAvailable;
        this.venueType = builder.venueType;
    }

    public static class Builder{
        private final String venueCode, location;
        private final VenueType venueType;
        private String venueName;
        private String seatingCapacity;
        private boolean isAirConditioned, isWifiAvailable, isChargingPortsAvailable;

        public Builder(String venueCode, String location, VenueType venueType){
            this.venueCode = venueCode;
            this.location = location;
            this.venueType = venueType;
        }

        public Builder venueName(String venueName){
            this.venueName = venueName;
            return this;
        }

        public Builder seatingCapacity(String seatingCapacity){
            this.seatingCapacity = seatingCapacity;
            return this;
        }

        public Builder isAirConditioned(boolean isAirConditioned){
            this.isAirConditioned = isAirConditioned;
            return this;
        }

        public Builder isWifiAvailable(boolean isWifiAvailable){
            this.isWifiAvailable = isWifiAvailable;
            return this;
        }

        public Builder isChargingPortsAvailable(boolean isChargingPortsAvailable){
            this.isChargingPortsAvailable = isChargingPortsAvailable;
            return this;
        }

        public Venue build(){
            return new Venue(this);
        }
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

    public String getVenueName(){
        return venueName;
    }

    public String getVenueCode() {
        return venueCode;
    }

    public VenueType getType() {
        return this.venueType;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public void setSeatingCapacity(String seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

}