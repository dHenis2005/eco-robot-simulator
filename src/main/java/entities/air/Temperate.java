package entities.air;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Air;
import fileio.AirInput;
import lombok.Data;

@Data
public class Temperate extends Air {
    private static final double OXYGEN = 2;
    private static final double HUMIDITY = 0.7;
    private static final double MAXSCORE = 84.0;
    private static final double POLLEN = 0.1;
    private double pollenLevel;

    public Temperate(final AirInput input) {
        super(input);
        this.pollenLevel = input.getPollenLevel();
    }

    /**
     * Calculates the air quality based on the input parameters.
     */
    @Override
    public double airQuality() {
        return (oxygenLevel * OXYGEN) + (humidity * HUMIDITY) - (pollenLevel * POLLEN);
    }

    /**
     * Returns the max score for this air type.
     */
    @Override
    public double getMaxScore() {
        return MAXSCORE;
    }

    /**
     * Returns the air as a JSON object.
     */
    @Override
    public ObjectNode toJSON(final ObjectMapper mapper) {
        ObjectNode node = super.toJSON(mapper);
        node.put("pollenLevel", pollenLevel);
        return node;
    }
}
