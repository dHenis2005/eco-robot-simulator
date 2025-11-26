package entities.air;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Air;
import fileio.AirInput;

public class Temperate extends Air {
    double pollenLevel;
    public Temperate(AirInput input) {
        super(input);
        this.pollenLevel = input.getPollenLevel();
    }
    @Override
    public double airQuality() {
        return (oxygenLevel*2) + (humidity*0.7) - (pollenLevel*0.1);
    }
    @Override
    public double getMaxScore() {
        return 84.0;
    }
    @Override
    public ObjectNode toJSON(ObjectMapper mapper) {
        ObjectNode node = super.toJSON(mapper);
        node.put("pollenLevel", pollenLevel);
        return node;
    }
}
