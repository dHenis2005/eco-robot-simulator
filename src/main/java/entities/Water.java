package entities;

import fileio.WaterInput;
import lombok.Data;

@Data
public class Water extends Entity {
    private static final double ROUNDING = 100.0;
    private static final double PH = 7.5;
    private static final double SALINITY = 350.0;
    private static final double PURITY = 100.0;
    private static final double TURBIDITY = 100.0;
    private static final double CONTAMINANT = 100.0;
    private static final double RPURITY = 0.3;
    private static final double RPH = 0.2;
    private static final double RSAL = 0.15;
    private static final double RFROZEN = 0.2;
    private static final double RTUR = 0.1;
    private double salinity;
    private double pH;
    private double purity;
    private double turbidity;
    private double contaminantIndex;
    private boolean isFrozen;

    public Water(final WaterInput input) {
        super(input.getName(), input.getMass(), input.getType());
        this.salinity = input.getSalinity();
        this.pH = input.getPH();
        this.purity = input.getPurity();
        this.turbidity = input.getTurbidity();
        this.contaminantIndex = input.getContaminantIndex();
        this.isFrozen = input.isFrozen();
    }

    /**
     * Calculates the water quality score.
     */
    public double waterQuality() {
        double purityScore = purity / PURITY;
        double pHScore = 1 - Math.abs(pH - PH) / PH;
        double salinityScore = 1 - (salinity / SALINITY);
        double turbidityScore = 1 - (turbidity / TURBIDITY);
        double contaminantScore = 1 - (contaminantIndex / CONTAMINANT);
        double frozenScore = isFrozen ? 0 : 1;
        return (RPURITY * purityScore + RPH * pHScore + RSAL * salinityScore
                + RTUR * turbidityScore + RSAL * contaminantScore
                + RFROZEN * frozenScore) * ROUNDING;
    }
}
