package entities.soil;

import entities.Soil;
import fileio.SoilInput;

public class TundraSoil extends Soil {
    double permafrostDepth;
    public TundraSoil(SoilInput input) {
        super(input);
        this.permafrostDepth = input.getPermafrostDepth();
    }
    @Override
    public double soilQuality() {
        return  (nitrogen * 0.7) + (organicMatter * 0.5) - (permafrostDepth * 1.5) ;
    }
    @Override
    public double blockBot() {
        return (50 - permafrostDepth) / 50 * 100;
    }
}
