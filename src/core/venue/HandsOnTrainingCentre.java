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
//    public HandsOnTrainingCentre(String venueName, String venueCode, String location, String seatingCapacity, boolean isAirConditioned, boolean isWifiAvailable, boolean isChargingPortsAvailable, boolean isMicStandAvailable) {
//        super(venueName, venueCode, location, seatingCapacity, isAirConditioned, isWifiAvailable, isChargingPortsAvailable, VenueType.HANDS_ON_TRAINING);
//        this.isMicStandAvailable = isMicStandAvailable;
//    }

    private HandsOnTrainingCentre(Builder builder) {
//        super(builder.venueName, builder.venueCode, builder.location, builder.seatingCapacity, builder.isAirConditioned, builder.isWifiAvailable, builder.isChargingPortsAvailable, builder.venueType);
        super(
            new Venue.Builder(builder.venueCode, builder.location, builder.venueType)
                    .venueName(builder.venueName)
                    .seatingCapacity(builder.seatingCapacity)
                    .isAirConditioned(builder.isAirConditioned)
                    .isWifiAvailable(builder.isWifiAvailable)
                    .isChargingPortsAvailable(builder.isChargingPortsAvailable)
        );
        this.isMicStandAvailable = builder.isMicStandAvailable;
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

    // Builder pattern to build the object, as it has may parameters to be set during object creation
    public static class Builder {
        private final String venueCode, location;
        private final VenueType venueType;
        private String venueName;
        private String seatingCapacity;
        private boolean isAirConditioned, isWifiAvailable, isChargingPortsAvailable, isMicStandAvailable;

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

        public Builder isMicStandAvailable(boolean isMicStandAvailable) {
            this.isMicStandAvailable = isMicStandAvailable;
            return this;
        }

        public Venue build() {
            return new HandsOnTrainingCentre(this);
        }
    }
}
