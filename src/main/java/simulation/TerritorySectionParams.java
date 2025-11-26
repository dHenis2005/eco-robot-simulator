package simulation;

import entities.*;
import fileio.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TerritorySectionParams {
    private Soil soil;
    private Plant plants;
    private Animal animals;
    private Water water;
    private Air air;
    public TerritorySectionParams() {
        this.air = getAir();
        this.plants = getPlants();
        this.soil = getSoil();
        this.water = getWater();
        this.animals = getAnimals();
    }
}
