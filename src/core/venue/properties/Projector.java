package core.venue.properties;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class Projector extends VenueProperty{

    boolean isRemoteAvailable;

    public Projector(VenuePropertyType propertyType, String brand, String model, LocalDate dateBought, boolean isRemoteAvailable) {
        super(propertyType, brand, model, dateBought);
        this.isRemoteAvailable = isRemoteAvailable;
    }

    @Override
    public Map<String, String> getDetails() {
        Map<String, String> projectorDetails = super.getDetails();
        projectorDetails.putAll(additionalDetails());
        return projectorDetails;
    }

    private Map<String, String> additionalDetails(){
        return new LinkedHashMap<String, String>(){
            {
                put("Remote", (isRemoteAvailable)? "Available": "Not Available");
            }
        };
    }

}