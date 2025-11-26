package entities.air;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Air;
import fileio.AirInput;

public class Polar extends Air {
    double iceCrystalConcentration;
    public Polar(AirInput input) {
        super(input);
        this.iceCrystalConcentration = input.getIceCrystalConcentration();
    }
    @Override
    public double airQuality() {
        return (oxygenLevel*2) + (100-Math.abs(temperature)) - (iceCrystalConcentration*0.05);
    }
    @Override
    public double getMaxScore() {
        return 142.0;
    }
    @Override
    public ObjectNode toJSON(ObjectMapper mapper) {
        ObjectNode node = super.toJSON(mapper);
        node.put("iceCrystalConcentration", iceCrystalConcentration);
        return node;
    }
}
