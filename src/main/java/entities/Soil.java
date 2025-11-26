package entities;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.SoilInput;

public abstract class Soil extends Entity {
    protected double nitrogen;
    protected double waterRetention;
    protected double soilpH;
    protected double organicMatter;
    String type;
    public Soil (SoilInput input) {
        super(input.getName(), input.getMass(), input.getType());
        this.nitrogen = input.getNitrogen();
        this.waterRetention = input.getWaterRetention();
        this.soilpH = input.getSoilpH();
        this.organicMatter = input.getOrganicMatter();
        this.type = input.getType();
    }
    public abstract double soilQuality();
    public abstract double blockBot();
    public double roundedSoilQuality() {
        double normalizedSoilQuality = Math.max(0, Math.min(100, soilQuality()));
        return Math.round(normalizedSoilQuality * 100.0) / 100.0;
    }
    @Override
    public ObjectNode toJSON(ObjectMapper mapper) {
        ObjectNode node = super.toJSON(mapper);
        node.put("nitrogen", nitrogen);
        node.put("waterRetention", waterRetention);
        node.put("soilpH", soilpH);
        node.put("organicMatter", organicMatter);
        node.put("soilQuality", roundedSoilQuality());
        return node;
    }
}
