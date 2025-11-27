package entities.soil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Soil;
import fileio.SoilInput;
import lombok.Data;

@Data
public class ForestSoil extends Soil {
    private static final double ROUNDING = 100.0;
    private static final double NITROQ = 1.2;
    private static final double ORGANICQ = 2;
    private static final double WATERQ = 1.5;
    private static final double LEAFQ = 0.3;
    private static final double WATERB = 0.6;
    private static final double LEAFB = 0.4;
    private static final double BLOCK = 80;

    private double leafLitter;

    public ForestSoil(final SoilInput input) {
        super(input);
        this.leafLitter = input.getLeafLitter();
    }

    /**
     * Calculates the soil quality based on the input parameters.
     */
    @Override
    public double soilQuality() {
        return (nitrogen * NITROQ) + (organicMatter * ORGANICQ) + (waterRetention * WATERQ)
                + (leafLitter * LEAFQ);
    }

    /**
     * Calculates the
     */
    @Override
    public double blockBot() {
        return (waterRetention * WATERB + leafLitter * LEAFB) / BLOCK * ROUNDING;
    }

    /**
     * Calculates the soil quality based on the input parameters.
     */
    @Override
    public ObjectNode toJSON(final ObjectMapper mapper) {
        ObjectNode node = super.toJSON(mapper);
        node.put("leafLitter", leafLitter);
        return node;
    }

}
