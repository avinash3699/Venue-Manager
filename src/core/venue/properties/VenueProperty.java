package core.venue.properties;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class VenueProperty {

    final VenuePropertyType propertyType;
    String brand, model;
    LocalDate dateBought;

    public VenueProperty(VenuePropertyType propertyType, String brand, String model, LocalDate dateBought) {
        this.propertyType = propertyType;
        this.brand = brand;
        this.model = model;
        this.dateBought = dateBought;
    }

    public Map<String, String> getDetails(){
        return new LinkedHashMap<String, String>(){
            {
                put("Property Type", propertyType.toString());
                put("Brand", brand);
                put("Model", model);
                put("Date Bought", dateBought.toString());
            }
        };
    }
}
