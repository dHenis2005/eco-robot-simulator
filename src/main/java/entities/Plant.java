package entities;
import fileio.PlantInput;

public class Plant extends Entity {
    String type;
    double status = 0;
    double growthRate = 0;
    double oxygenPlant = 0;
    double stuckPossibility = 0;

    public Plant(PlantInput input){
        super(input.getName(), input.getMass());
        this.type = input.getType();
    }
    public void oxygenGeneration() {
        if (type.equals("FloweringPlants")) {
            oxygenPlant += 6;
        } else if (type.equals("Mosses")) {
            oxygenPlant += 0.8;
        } else if (type.equals("Algae")) {
            oxygenPlant += 0.5;
        }
        if (status < 1) {
            oxygenPlant += 0.2;
        } else if (status < 2) {
            oxygenPlant += 0.7;
        } else if (status < 3) {
            oxygenPlant += 0.4;
        }
    }
    public void plantStuck() {
        if (type.equals("FloweringPlants")) {
            stuckPossibility = 90.0/100;
        } else if (type.equals("Gymnosperms")) {
            stuckPossibility = 60.0/100;
        } else if (type.equals("Ferns")) {
            stuckPossibility = 30.0/100;
        } else if (type.equals("Mosses")) {
            stuckPossibility = 40.0/100;
        } else if (type.equals("Algae")) {
            stuckPossibility = 20.0/100;
        }
    }
}

