package entities.air;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Air;
import fileio.AirInput;
import lombok.Data;

@Data
public class Tropical extends Air {
    private static final double OXYGEN = 2;
    private static final double HUMIDITY = 0.5;
    private static final double MAXSCORE = 82.0;
    private static final double CO2 = 0.01;
    private double co2Level;
    private double rainfall;

    public Tropical(final AirInput input) {
        super(input);
        this.co2Level = input.getCo2Level();
    }

    /**
     * Calculates the air quality based on the input parameters.
     */
    @Override
    public double airQuality() {
        return (oxygenLevel * OXYGEN) + (humidity * HUMIDITY) - (co2Level * CO2);
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
        node.put("co2Level", co2Level);
        return node;
    }
    /**
     * Abstract method for updating the air quality according to the weather.
     */
    @Override
    public double updateAirQuality() {
        return roundedAirQuality() + (rainfall * 0.3);
    }
    /**
     * Abstract method for setting the weather.
     */
    @Override
    public void setWeather(Object val) {
        this.rainfall = (double) val;
    }
    public void resetWeather() {
        rainfall = 0;
    }
}
