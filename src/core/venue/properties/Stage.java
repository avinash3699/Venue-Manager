package core.venue.properties;

import java.util.LinkedHashMap;
import java.util.Map;

public class Stage {

    private final float height, length, breadth;

    public Stage(float height, float length, float breadth) {
        this.height = height;
        this.length = length;
        this.breadth = breadth;
    }

    public Map<String, Float> getStageDimensions(){
        return new LinkedHashMap<String, Float>(){
            {
                put("Height", height);
                put("Length", length);
                put("Breadth", breadth);
            }
        };
    }

}