package entities.air;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Air;
import fileio.AirInput;

public class Tropical extends Air {
    double co2Level;
    public Tropical(AirInput input){
        super(input);
        this.co2Level = input.getCo2Level();
    }
    @Override
    public double airQuality() {
        return (oxygenLevel*2) + (humidity*0.5) - (co2Level*0.01);
    }
    @Override
    public double getMaxScore() {
        return 82.0;
    }
    @Override
    public ObjectNode toJSON(ObjectMapper mapper) {
        ObjectNode node = super.toJSON(mapper);
        node.put("co2Level", co2Level);
        return node;
    }
}
