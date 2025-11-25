package entities.air;

import entities.Air;
import fileio.AirInput;

public class Polar extends Air {
    double iceCrystalConcentration;
    public Polar(AirInput input) {
        super(input);
        this.iceCrystalConcentration = input.getIceCrystalConcentration();
    }
    @Override
    public double airQuality() {
        return (oxygenLevel*2) + (100-Math.abs(temperature)) - (iceCrystalConcentration*0.05);
    }
    @Override
    public double getMaxScore() {
        return 142.0;
    }
}
