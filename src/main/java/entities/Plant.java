package entities;

import fileio.PlantInput;
import lombok.Data;

@Data
public class Plant extends Entity {
    private static final double ROUNDING = 100.0;
    private static final double O2FLOWERING = 6;
    private static final double O2MOSSES = 0.8;
    private static final double O2ALGAE = 0.5;
    private static final double GYMNOSPERMS = 0.6;
    private static final double FERNS = 0.3;
    private static final double FLOWERING = 0.9;
    private static final double MOSSES = 0.4;
    private static final double ALGAE = 0.2;
    private static final double YOUNG = 0.2;
    private static final double OLD = 0.4;
    private static final double MATURE = 0.7;
    private static final int YOUNGAGE = 1;
    private static final int OLDAGE = 3;
    private static final int MATUREAGE = 2;
    private double status = 0;
    private double growthRate = 0;
    private double oxygenPlant = 0;
    private double stuckPossibility = 0;

    public Plant(final PlantInput input) {
        super(input.getName(), input.getMass(), input.getType());
    }

    /**
     * Oxygen generation
     */
    public void oxygenGeneration() {
        switch (getType()) {
            case "FloweringPlants" -> oxygenPlant += O2FLOWERING;
            case "Mosses" -> oxygenPlant += O2MOSSES;
            case "Algae" -> oxygenPlant += O2ALGAE;
            default -> oxygenPlant += 0;
        }
        if (status < YOUNGAGE) {
            oxygenPlant += YOUNG;
        } else if (status < MATUREAGE) {
            oxygenPlant += MATURE;
        } else if (status < OLDAGE) {
            oxygenPlant += OLD;
        }
    }

    /**
     * Calculates the chance to get stuck in the plants.
     */
    public double plantStuck() {
        switch (getType()) {
            case "FloweringPlants" -> stuckPossibility = FLOWERING;
            case "GymnospermsPlants" -> stuckPossibility = GYMNOSPERMS;
            case "Ferns" -> stuckPossibility = FERNS;
            case "Mosses" -> stuckPossibility = MOSSES;
            case "Algae" -> stuckPossibility = ALGAE;
            default -> stuckPossibility = 0;
        }
        return stuckPossibility;
    }
}

