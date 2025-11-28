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
    private boolean isScanned = false;
    private int scanTimestamp = 0;
    private enum  Type {
        FloweringPlant,
        Mosses,
        Algae,
    }

    public Plant(final PlantInput input) {
        super(input.getName(), input.getMass(), input.getType());
    }

    /**
     * Oxygen generation
     */
    public void oxygenGeneration() {
        status += growthRate;
        status = round(status,  ROUNDING);
        double oxygen = 0;
        switch (getType()) {
            case "FloweringPlants" -> oxygen += O2FLOWERING;
            case "Mosses" -> oxygen += O2MOSSES;
            case "Algae" -> oxygen += O2ALGAE;
            default -> oxygen += 0;
        }
        if (status < YOUNGAGE) {
            oxygen += YOUNG;
        } else if (status < MATUREAGE) {
            oxygen += MATURE;
        } else if (status <= OLDAGE) {
            oxygen += OLD;
        }
        oxygenPlant = oxygen;
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

    /**
     * Method to modify growth based on entities in cell.
     */
    public double addGrowth(final double val) {
        return round(val, ROUNDING);
    }
}

