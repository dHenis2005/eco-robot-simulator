package entities.soil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Soil;
import fileio.SoilInput;

public class SwampSoil extends Soil {
    double waterLogging;
    public SwampSoil(SoilInput input) {
        super(input);
        this.waterLogging = input.getWaterLogging();
    }
    @Override
    public double soilQuality() {
        return (nitrogen * 1.1) + (organicMatter * 2.2) - (waterLogging * 5);
    }
    @Override
    public double blockBot() {
        return  waterLogging * 10;
    }
    @Override
    public ObjectNode toJSON(ObjectMapper mapper) {
        ObjectNode node = super.toJSON(mapper);
        node.put("waterLogging", waterLogging);
        return node;
    }

}
