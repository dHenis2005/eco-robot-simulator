package entities.soil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Soil;
import fileio.SoilInput;
import lombok.Data;

@Data
public class TundraSoil extends Soil {
    private static final double ROUNDING = 100.0;
    private static final double NITROQ = 0.7;
    private static final double ORGANICQ = 0.5;
    private static final double PERMA = 1.5;
    private static final double BLOCK = 50;
    private double permafrostDepth;

    public TundraSoil(final SoilInput input) {
        super(input);
        this.permafrostDepth = input.getPermafrostDepth();
    }

    /**
     * Calculates the soil quality based on the input parameters.
     */
    @Override
    public double soilQuality() {
        return (nitrogen * NITROQ) + (organicMatter * ORGANICQ) - (permafrostDepth * PERMA);
    }

    /**
     * Calculates the chance to block the bot.
     */
    @Override
    public double blockBot() {
        return (BLOCK - permafrostDepth) / BLOCK * ROUNDING;
    }

    /**
     * Returns the soil as a JSON object.
     */
    @Override
    public ObjectNode toJSON(final ObjectMapper mapper) {
        ObjectNode node = super.toJSON(mapper);
        node.put("permafrostDepth", permafrostDepth);
        return node;
    }

}
