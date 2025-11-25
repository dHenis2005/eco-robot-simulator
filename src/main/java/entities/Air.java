package entities;
import fileio.AirInput;

public abstract class Air extends Entity {
    protected double humidity;
    protected double temperature;
    protected double oxygenLevel;
    String type;
    public Air(AirInput input) {
        super(input.getName(), input.getMass());
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
}
