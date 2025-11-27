package entities.soil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Soil;
import fileio.SoilInput;
import lombok.Data;

@Data
public class SwampSoil extends Soil {
    private static final double ROUNDING = 10.0;
    private static final double NITROQ = 1.1;
    private static final double ORGANICQ = 2.2;
    private static final double WATERL = 5;
    private double waterLogging;

    public SwampSoil(final SoilInput input) {
        super(input);
        this.waterLogging = input.getWaterLogging();
    }

    /**
     * Calculates the soil quality based on the input parameters.
     */
    @Override
    public double soilQuality() {
        return (nitrogen * NITROQ) + (organicMatter * ORGANICQ) - (waterLogging * WATERL);
    }

    /**
     * Calculates the chance to block the bot.
     */
    @Override
    public double blockBot() {
        return waterLogging * ROUNDING;
    }

    /**
     * Returns the soil as a JSON object.
     */
    @Override
    public ObjectNode toJSON(final ObjectMapper mapper) {
        ObjectNode node = super.toJSON(mapper);
        node.put("waterLogging", waterLogging);
        return node;
    }

}
