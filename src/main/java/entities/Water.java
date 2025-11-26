package entities;
import fileio.WaterInput;

public class Water extends Entity {
    double salinity;
    double pH;
    double purity;
    double turbidity;
    double contaminantIndex;
    boolean isFrozen;
    String type;
    public Water(WaterInput input) {
        super(input.getName(), input.getMass(), input.getType());
        this.salinity = input.getSalinity();
        this.pH = input.getPH();
        this.purity = input.getPurity();
        this.turbidity = input.getTurbidity();
        this.contaminantIndex = input.getContaminantIndex();
        this.isFrozen = input.isFrozen();
        this.type = input.getType();
    }
    public double waterQuality() {
        double purity_score        = purity / 100;
        double pH_score            = 1 - Math.abs(pH - 7.5) / 7.5;
        double salinity_score      = 1 - (salinity / 350);
        double turbidity_score     = 1 - (turbidity / 100);
        double contaminant_score   = 1 - (contaminantIndex / 100);
        double frozen_score        = isFrozen ? 0 : 1;
        return (0.3 * purity_score + 0.2 * pH_score + 0.15 * salinity_score + 0.1 * turbidity_score + 0.15 * contaminant_score + 0.2 * frozen_score) * 100;
    }
}
