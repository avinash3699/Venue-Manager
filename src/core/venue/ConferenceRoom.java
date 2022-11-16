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
//    public ConferenceRoom(String venueName, String venueCode, String location, String seatingCapacity, boolean isAirConditioned, boolean isWifiAvailable, boolean isChargingPortsAvailable, boolean isWhiteBoardAvailable) {
//        super(venueName, venueCode, location, seatingCapacity, isAirConditioned, isWifiAvailable, isChargingPortsAvailable, VenueType.CONFERENCE);
//        this.isWhiteBoardAvailable = isWhiteBoardAvailable;
//    }

    private ConferenceRoom(Builder builder) {
//        super(builder.venueName, builder.venueCode, builder.location, builder.seatingCapacity, builder.isAirConditioned, builder.isWifiAvailable, builder.isChargingPortsAvailable, builder.venueType);
        super(
            new Venue.Builder(builder.venueCode, builder.location, builder.venueType)
                    .venueName(builder.venueName)
                    .seatingCapacity(builder.seatingCapacity)
                    .isAirConditioned(builder.isAirConditioned)
                    .isWifiAvailable(builder.isWifiAvailable)
                    .isChargingPortsAvailable(builder.isChargingPortsAvailable)
        );
        this.isWhiteBoardAvailable = builder.isWhiteBoardAvailable;
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
    public Venue clone(){
        Map<String, String> venueDetails = this.getVenueDetails();
        return new Builder(venueDetails.get("Venue Code"), venueDetails.get("Venue Location"), VenueType.CONFERENCE)
                .venueName(venueDetails.get("Venue Name"))
                .seatingCapacity(venueDetails.get("Seating Capacity"))
                .isAirConditioned(venueDetails.get("Air Conditioner").equals("Available"))
                .isWifiAvailable(venueDetails.get("Wifi").equals("Available"))
                .isChargingPortsAvailable(venueDetails.get("Individual Charging Ports").equals("Available"))
                .isWhiteBoardAvailable(isWhiteBoardAvailable)
                .build();
    }

    private Map<String, String> getAdditionalVenueDetails() {
        return new LinkedHashMap<String, String>(){
            {
                put("White Board", isWhiteBoardAvailable? "Available": "Not Available");
            }
        };
    }

    // Builder pattern to build the object, as it has may parameters to be set during object creation
    public static class Builder {
        private final String venueCode, location;
        private final VenueType venueType;
        private String venueName;
        private String seatingCapacity;
        private boolean isAirConditioned, isWifiAvailable, isChargingPortsAvailable, isWhiteBoardAvailable;

        public Builder(String venueCode, String location, VenueType venueType) {
            this.venueCode = venueCode;
            this.location = location;
            this.venueType = venueType;
        }

        public Builder venueName(String venueName) {
            this.venueName = venueName;
            return this;
        }

        public Builder seatingCapacity(String seatingCapacity) {
            this.seatingCapacity = seatingCapacity;
            return this;
        }

        public Builder isAirConditioned(boolean isAirConditioned) {
            this.isAirConditioned = isAirConditioned;
            return this;
        }

        public Builder isWifiAvailable(boolean isWifiAvailable) {
            this.isWifiAvailable = isWifiAvailable;
            return this;
        }

        public Builder isChargingPortsAvailable(boolean isChargingPortsAvailable) {
            this.isChargingPortsAvailable = isChargingPortsAvailable;
            return this;
        }

        public Builder isWhiteBoardAvailable(boolean isWhiteBoardAvailable) {
            this.isWhiteBoardAvailable = isWhiteBoardAvailable;
            return this;
        }

        public ConferenceRoom build() {
            return new ConferenceRoom(this);
        }
    }
}