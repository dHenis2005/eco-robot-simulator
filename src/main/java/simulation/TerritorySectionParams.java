package simulation;

import entities.Animal;
import entities.Plant;
import entities.Water;
import entities.Soil;
import entities.Air;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TerritorySectionParams {
    private Soil soil;
    private Plant plant;
    private Animal animal;
    private Water water;
    private Air air;
    public TerritorySectionParams() {
        this.air = getAir();
        this.plant = getPlant();
        this.soil = getSoil();
        this.water = getWater();
        this.animal = getAnimal();
    }

    /**
     * Calculates the quality score of a cell using all the hazards the robot would
     * have to go through.
     * The rounding is done according to the assignment.
     */
    public int qualityScore() {
        double stuckSoil = 0;
        double damageAir = 0;
        double attackAnimal = 0;
        double stuckPlant = 0;
        int count = 0;
        if (soil != null) {
            stuckSoil = soil.blockBot();
            count++;
        }
        if (plant != null) {
            stuckPlant = plant.plantStuck();
            count++;
        }
        if (animal != null) {
            attackAnimal = animal.animalAttack();
            count++;
        }
        if (air != null) {
            damageAir = air.roundedToxicity();
            count++;
        }
        double sum = stuckSoil + damageAir + attackAnimal + stuckPlant;
        double mean = Math.abs(sum / count);
        return (int) Math.round(mean);
    }
}
