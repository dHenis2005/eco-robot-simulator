package entities.soil;

import entities.Soil;
import fileio.SoilInput;

public class DesertSoil extends Soil {
    double salinity;
    public DesertSoil(SoilInput input) {
        super(input);
        this.salinity = input.getSalinity();
    }
    @Override
    public double soilQuality() {
        return (nitrogen * 0.5) + (waterRetention * 0.3) - (salinity * 2);
    }
    @Override
    public double blockBot() {
        return (100 - waterRetention + salinity) / 100 * 100;
    }
}
