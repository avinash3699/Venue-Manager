package core.venue;

import core.venue.properties.Microphone;
import core.venue.properties.Speaker;
import core.venue.properties.Stage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Auditorium extends Venue{

    // Instance Variables
    private boolean isMicStandAvailable;
    private String noOfDisplayScreen;

    //TODO initialize hall properties with their details
    private Speaker speaker;
    private ArrayList<Microphone> microphone;

    private Stage stage;

    // Constructor
//    public Auditorium(String venueName, String venueCode, String location, String seatingCapacity, boolean isAirConditioned, boolean isWifiAvailable, boolean isChargingPortsAvailable, boolean isMicStandAvailable, String noOfDisplayScreen) {
//        super(venueName, venueCode, location, seatingCapacity, isAirConditioned, isWifiAvailable, isChargingPortsAvailable, VenueType.AUDITORIUM);
//        this.isMicStandAvailable = isMicStandAvailable;
//        this.noOfDisplayScreen = noOfDisplayScreen;
//    }

    private Auditorium(Builder builder) {
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
        this.noOfDisplayScreen = builder.noOfDisplayScreen;
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
                put("No. of Display Screens", noOfDisplayScreen);
            }
        };
    }


    @Override
    public Venue clone(){
        Map<String, String> venueDetails = this.getVenueDetails();
        return new Builder(venueDetails.get("Venue Code"), venueDetails.get("Venue Location"), VenueType.AUDITORIUM)
                .venueName(venueDetails.get("Venue Name"))
                .seatingCapacity(venueDetails.get("Seating Capacity"))
                .isAirConditioned(venueDetails.get("Air Conditioner").equals("Available"))
                .isWifiAvailable(venueDetails.get("Wifi").equals("Available"))
                .isChargingPortsAvailable(venueDetails.get("Individual Charging Ports").equals("Available"))
                .isMicStandAvailable(isMicStandAvailable)
                .noOfDisplayScreen(noOfDisplayScreen)
                .build();
    }

    // Builder pattern to build the object, as it has may parameters to be set during object creation
    public static class Builder {
        private final String venueCode, location;
        private final VenueType venueType;
        private String venueName;
        private String seatingCapacity;
        private boolean isAirConditioned, isWifiAvailable, isChargingPortsAvailable, isMicStandAvailable;
        private String noOfDisplayScreen;

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

        public Builder noOfDisplayScreen(String noOfDisplayScreen) {
            this.noOfDisplayScreen = noOfDisplayScreen;
            return this;
        }

        public Auditorium build() {
            return new Auditorium(this);
        }
    }
}
