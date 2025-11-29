package entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;
import lombok.Data;

@Data
public abstract class Air extends Entity {
    private static final double ROUNDING = 100.0;
    private static final double TOXIC = 0.8;
    protected double humidity;
    protected double temperature;
    protected double oxygenLevel;
    private int weatherTimer = 0;

    public Air(final AirInput input) {
        super(input.getName(), input.getMass(), input.getType());
        this.humidity = input.getHumidity();
        this.temperature = input.getTemperature();
        this.oxygenLevel = input.getOxygenLevel();
    }

    /**
     * Abstract method for calculating the air quality score.
     */
    public abstract double airQuality();

    /**
     * Abstract method for getting the score of the air type.
     */
    public abstract double getMaxScore();

    /**
     * Method for rounding the air quality score.
     */
    public double roundedAirQuality() {
        return round(airQuality(), ROUNDING);
    }

    /**
     * Method for calculating the toxicity of the air.
     */
    public double toxicity() {
        double airQualityScore = roundedAirQuality();
        double toxicityAQ = (ROUNDING * (1 - airQualityScore / getMaxScore()));
        return Math.round(toxicityAQ * ROUNDING) / ROUNDING;
    }

    /**
     * Method for rounding the toxicity score.
     */
    public double roundedToxicity() {
        return round(toxicity(), getMaxScore());
    }

    /**
     * Returns the air as a JSON object.
     */
    @Override
    public ObjectNode toJSON(final ObjectMapper mapper) {
        ObjectNode node = super.toJSON(mapper);
        node.put("humidity", humidity);
        node.put("temperature", temperature);
        node.put("oxygenLevel", oxygenLevel);
        node.put("airQuality", roundUpdatedAirQuality());
        return node;
    }

    /**
     * Method for checking if the air is toxic.
     */
    public boolean isToxic() {
        return toxicity() > TOXIC * getMaxScore();
    }

    /**
     * Abstract method for updating the air quality according to the weather.
     */
    public abstract double updateAirQuality();

    /**
     * Abstract method for setting the weather.
     */
    public abstract void setWeather(Object val);

    /**
     * Abstract method for resetting the weather.
     */
    public abstract void resetWeather();

    /**
     * Rounding for the airQuality.
     */
    public double roundUpdatedAirQuality() {
        return round(updateAirQuality(), ROUNDING);
    }

    /**
     * Method used for adding humidity based on water in the cell.
     */
    public double addHumidity(double val) {
        return round(humidity + val, ROUNDING);
    }

    /**
     * Method for adding oxygen based on plant in cell.
     */
    public double addOxygen(double val) {
        return round(oxygenLevel + val, ROUNDING);
    }
}
