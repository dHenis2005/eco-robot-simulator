package entities.air;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Air;
import fileio.AirInput;
import lombok.Data;

@Data
public class Polar extends Air {
    private static final double OXYGEN = 2;
    private static final double MAXSCORE = 142.0;
    private static final double ROUNDING = 100.0;
    private static final double ICE = 0.05;
    private double iceCrystalConcentration;

    public Polar(final AirInput input) {
        super(input);
        this.iceCrystalConcentration = input.getIceCrystalConcentration();
    }

    /**
     * Calculates the air quality based on the input parameters.
     */
    @Override
    public double airQuality() {
        return (oxygenLevel * OXYGEN) + (ROUNDING - Math.abs(temperature))
                - (iceCrystalConcentration * ICE);
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
        node.put("iceCrystalConcentration", iceCrystalConcentration);
        return node;
    }
}
