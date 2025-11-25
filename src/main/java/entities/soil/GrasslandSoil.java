package entities.soil;

import entities.Soil;
import fileio.SoilInput;

public class GrasslandSoil extends Soil {
    double rootDensity;
    public GrasslandSoil(SoilInput input) {
        super(input);
        this.rootDensity = input.getRootDensity();
    }
    @Override
    public double soilQuality() {
        return (nitrogen * 1.3) + (organicMatter * 1.5) + (rootDensity * 0.8);
    }
    @Override
    public double blockBot() {
        return ((50 - rootDensity) + waterRetention * 0.5) / 75 * 100;
    }
}
