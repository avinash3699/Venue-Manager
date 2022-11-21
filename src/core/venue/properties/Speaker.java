package core.venue.properties;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class Speaker extends VenueProperty{

    boolean isBluetoothCompatible;

    public Speaker(VenuePropertyType propertyType, String brand, String model, LocalDate dateBought, boolean isBluetoothCompatible) {
        super(propertyType, brand, model, dateBought);
        this.isBluetoothCompatible = isBluetoothCompatible;
    }

    @Override
    public Map<String, String> getDetails() {
        Map<String, String> speakerDetails = super.getDetails();
        speakerDetails.putAll(getAdditionalDetails());
        return speakerDetails;
    }

    private Map<String, String> getAdditionalDetails() {
        return new LinkedHashMap<String, String>(){
            {
                put("Bluetooth Compatible", (isBluetoothCompatible)? "Yes": "No");
            }
        };
    }
}
