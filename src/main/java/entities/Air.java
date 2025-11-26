package entities;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.AirInput;

public abstract class Air extends Entity {
    protected double humidity;
    protected double temperature;
    protected double oxygenLevel;
    String type;
    public Air(AirInput input) {
        super(input.getName(), input.getMass(),  input.getType());
        this.humidity = input.getHumidity();
        this.temperature = input.getTemperature();
        this.oxygenLevel = input.getOxygenLevel();
        this.type = input.getType();
    }
    public abstract double airQuality();
    public abstract double getMaxScore();
    public double roundedAirQuality() {
        double normalized = Math.max(0, Math.min(airQuality(), getMaxScore()));
        return Math.round(normalized * 100.0) / 100.0;
    }
    public double toxicity() {
        double airQualityScore = roundedAirQuality();
        double toxicityAQ = 100 * (1 - airQualityScore / getMaxScore());
        return Math.round(toxicityAQ * 100.0) / 100.0;
    }
    public double roundedToxicity() {
        double normalized = Math.max(0, Math.min(toxicity(), getMaxScore()));
        return Math.round(normalized * 100.0) / 100.0;
    }
    @Override
    public ObjectNode toJSON(ObjectMapper mapper) {
        ObjectNode node = super.toJSON(mapper);
        node.put("humidity", humidity);
        node.put("temperature", temperature);
        node.put("oxygenLevel", oxygenLevel);
        node.put("airQuality", roundedAirQuality());
        return node;
    }
}
