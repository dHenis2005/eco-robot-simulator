package entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;

public abstract class Soil extends Entity {
    private static final double ROUNDING = 100.0;
    protected double nitrogen;
    protected double waterRetention;
    protected double soilpH;
    protected double organicMatter;

    public Soil(final SoilInput input) {
        super(input.getName(), input.getMass(), input.getType());
        this.nitrogen = input.getNitrogen();
        this.waterRetention = input.getWaterRetention();
        this.soilpH = input.getSoilpH();
        this.organicMatter = input.getOrganicMatter();
    }

    /**
     * Abstract method for calculating the soil quality score.
     */
    public abstract double soilQuality();

    /**
     * Abstract method for calculating the chance to block the bot
     */
    public abstract double blockBot();

    /**
     * Method for rounding the soil quality score.
     */
    public double roundedSoilQuality() {
        double normalizedSoilQuality = Math.max(0, Math.min(ROUNDING, soilQuality()));
        return Math.round(normalizedSoilQuality * ROUNDING) / ROUNDING;
    }

    /**
     * Returns the soil as a JSON object.
     */
    @Override
    public ObjectNode toJSON(final ObjectMapper mapper) {
        ObjectNode node = super.toJSON(mapper);
        node.put("nitrogen", nitrogen);
        node.put("waterRetention", waterRetention);
        node.put("soilpH", soilpH);
        node.put("organicMatter", organicMatter);
        node.put("soilQuality", roundedSoilQuality());
        return node;
    }
}
