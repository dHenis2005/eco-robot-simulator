package entities.soil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Soil;
import fileio.SoilInput;

public class ForestSoil extends Soil {
    double leafLitter;
    public ForestSoil(SoilInput input) {
        super(input);
        this.leafLitter = input.getLeafLitter();
    }
    @Override
    public double soilQuality() {
        return (nitrogen * 1.2) + (organicMatter * 2) + (waterRetention * 1.5) + (leafLitter * 0.3);
    }
    @Override
    public double blockBot() {
        return (waterRetention * 0.6 + leafLitter * 0.4) / 80 * 100;
    }
    @Override
    public ObjectNode toJSON(ObjectMapper mapper) {
        ObjectNode node = super.toJSON(mapper);
        node.put("leafLitter", leafLitter);
        return node;
    }

}
