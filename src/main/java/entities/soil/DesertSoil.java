package entities.soil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Soil;
import fileio.SoilInput;
import lombok.Data;

@Data
public class DesertSoil extends Soil {
    private static final double ROUNDING = 100.0;
    private static final double NITROQ = 0.5;
    private static final double SALQ = 2;
    private static final double WATERQ = 0.3;
    private double salinity;

    public DesertSoil(final SoilInput input) {
        super(input);
        this.salinity = input.getSalinity();
    }

    /**
     * Calculates the soil quality based on the input parameters.
     */
    @Override
    public double soilQuality() {
        return (nitrogen * NITROQ) + (waterRetention * WATERQ) - (salinity * SALQ);
    }

    /**
     * Calculates the chance to block the bot.
     */
    @Override
    public double blockBot() {
        return (ROUNDING - waterRetention + salinity) / ROUNDING * ROUNDING;
    }

    /**
     * Returns the soil as a JSON object.
     */
    @Override
    public ObjectNode toJSON(final ObjectMapper mapper) {
        ObjectNode node = super.toJSON(mapper);
        node.put("salinity", salinity);
        return node;
    }
}
