package entities.soil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Soil;
import fileio.SoilInput;
import lombok.Data;

@Data
public class GrasslandSoil extends Soil {
    private static final double ROUNDING = 100.0;
    private static final double NITROQ = 1.3;
    private static final double ORGANICQ = 1.5;
    private static final double ROOTQ = 0.8;
    private static final double ROOTB = 50;
    private static final double WATERB = 0.5;
    private static final double BLOCK = 75;
    private double rootDensity;

    public GrasslandSoil(final SoilInput input) {
        super(input);
        this.rootDensity = input.getRootDensity();
    }

    /**
     * Calculates the soil quality based on the input parameters.
     */
    @Override
    public double soilQuality() {
        return (nitrogen * NITROQ) + (organicMatter * ORGANICQ) + (rootDensity * ROOTQ);
    }

    /**
     * Calculates the chance to block the bot.
     */
    @Override
    public double blockBot() {
        return ((ROOTB - rootDensity) + waterRetention * WATERB) / BLOCK * ROUNDING;
    }

    /**
     * Returns the soil as a JSON object.
     */
    @Override
    public ObjectNode toJSON(final ObjectMapper mapper) {
        ObjectNode node = super.toJSON(mapper);
        node.put("rootDensity", rootDensity);
        return node;
    }

}
