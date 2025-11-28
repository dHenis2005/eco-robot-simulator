package entities.air;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Air;
import fileio.AirInput;
import lombok.Data;

@Data
public class Mountain extends Air {
    private static final double OXYGEN = 2;
    private static final double HUMIDITY = 0.6;
    private static final double MAXSCORE = 78.0;
    private static final double ROUNDING = 1000.0;
    private static final double ALTITUDE = 0.5;
    private double altitude;
    private int hikers;

    public Mountain(final AirInput input) {
        super(input);
        altitude = input.getAltitude();
    }

    /**
     * Calculates the air quality based on the input parameters.
     */
    @Override
    public double airQuality() {
        return ((oxygenLevel - (altitude / ROUNDING * ALTITUDE)) * OXYGEN) + (humidity * HUMIDITY);
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
        node.put("altitude", altitude);
        return node;
    }
    /**
     * Abstract method for updating the air quality according to the weather.
     */
    @Override
    public double updateAirQuality() {
        return roundedAirQuality() - (hikers * 0.1);
    }
    /**
     * Abstract method for setting the weather.
     */
    @Override
    public void setWeather(Object val) {
        this.hikers = (int) val;
    }
    public void resetWeather() {
        hikers = 0;
    }
}
