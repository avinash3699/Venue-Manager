package core.venue.properties;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class Microphone extends VenueProperty{

    final MicrophoneType microphoneType;

    public Microphone(VenuePropertyType propertyType, String brand, String model, LocalDate dateBought, MicrophoneType microphoneType) {
        super(propertyType, brand, model, dateBought);
        this.microphoneType = microphoneType;
    }

    @Override
    public Map<String, String> getDetails() {
        Map<String, String> microphoneDetails = super.getDetails();
        microphoneDetails.putAll(getAdditionalDetails());
        return microphoneDetails;
    }

    private Map<String, String> getAdditionalDetails() {
        return new LinkedHashMap<String, String>(){
            {
                put("Microphone Type", microphoneType.toString());
            }
        };
    }

}