package entities.soil;

import entities.Soil;
import fileio.SoilInput;

public class SwampSoil extends Soil {
    double waterLogging;
    public SwampSoil(SoilInput input) {
        super(input);
        this.waterLogging = input.getWaterLogging();
    }
    @Override
    public double soilQuality() {
        return (nitrogen * 1.1) + (organicMatter * 2.2) - (waterLogging * 5);
    }
    @Override
    public double blockBot() {
        return  waterLogging * 10;
    }
}
