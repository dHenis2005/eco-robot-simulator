package entities.air;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Air;
import fileio.AirInput;

public class Desert extends Air {
    double dustParticles;
    public Desert(AirInput input) {
        super(input);
        dustParticles = input.getDustParticles();
    }
    @Override
    public double airQuality() {
        return (oxygenLevel*2) - (dustParticles*0.2) - (temperature*0.3);
    }
    @Override
    public double getMaxScore() {
        return 65.0;
    }
    @Override
    public ObjectNode toJSON(ObjectMapper mapper) {
        ObjectNode node = super.toJSON(mapper);
        node.put("dustParticles", dustParticles);
        return node;
    }
}
