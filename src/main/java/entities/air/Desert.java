package entities.air;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Air;
import fileio.AirInput;
import lombok.Data;

@Data
public class Desert extends Air {
    private static final double DUST = 0.2;
    private static final double OXYGEN = 2;
    private static final double TEMPERATURE = 0.3;
    private static final double MAXSCORE = 65.0;
    private static final double STORM = 30;
    private double dustParticles;
    private boolean desertStorm = false;

    public Desert(final AirInput input) {
        super(input);
        dustParticles = input.getDustParticles();
    }

    /**
     * Calculates the air quality based on the input parameters.
     */
    @Override
    public double airQuality() {
        return (oxygenLevel * OXYGEN) - (dustParticles * DUST) - (temperature * TEMPERATURE);
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
//        node.put("dustParticles", dustParticles);
        node.put("desertStorm", desertStorm);
        return node;
    }
    /**
     * Abstract method for updating the air quality according to the weather.
     */
    @Override
    public double updateAirQuality() {
        return roundedAirQuality() - (desertStorm ? STORM : 0);
    }
    /**
     * Abstract method for setting the weather.
     */
    @Override
    public void setWeather(final Object val) {
        this.desertStorm = (boolean) val;
    }

    /**
     * Abstract method for resetting the weather.
     */
    @Override
    public void resetWeather() {
        desertStorm = false;
    }
}
