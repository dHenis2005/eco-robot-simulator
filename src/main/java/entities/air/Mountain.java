package entities.air;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Air;
import fileio.AirInput;

public class Mountain extends Air {
    double altitude;
    public Mountain(AirInput input) {
        super(input);
        altitude = input.getAltitude();
    }
    @Override
    public double airQuality() {
        return ((oxygenLevel - (altitude/1000*0.5))*2) + (humidity*0.6);
    }
    @Override
    public double getMaxScore() {
        return 78.0;
    }
    @Override
    public ObjectNode toJSON(ObjectMapper mapper) {
        ObjectNode node = super.toJSON(mapper);
        node.put("altitude", altitude);
        return node;
    }
}
