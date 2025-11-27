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
    public Air(final AirInput input) {
        super(input.getName(), input.getMass(),  input.getType());
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
        double normalized = Math.max(0, Math.min(airQuality(), getMaxScore()));
        return Math.round(normalized * ROUNDING) / ROUNDING;
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
        double normalized = Math.max(0, Math.min(toxicity(), getMaxScore()));
        return Math.round(normalized * ROUNDING) / ROUNDING;
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
        node.put("airQuality", roundedAirQuality());
        return node;
    }
    /**
     * Method for checking if the air is toxic.
     */
    public boolean isToxic() {
        return toxicity() > TOXIC * getMaxScore();
    }
}
