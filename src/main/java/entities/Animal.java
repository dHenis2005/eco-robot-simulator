package entities;
import fileio.AnimalInput;

public class Animal extends Entity {
    String type;
    int state;
    double organicMatterIncrement = 0;
    double attackChance = 0;
    public Animal(AnimalInput input){
        super(input.getName(), input.getMass());
        this.type = input.getType();
    }
    public void feed(Animal prey, Plant plant) {
        if (type.equals("Carnivores") || type.equals("Parasites")) {
            mass += prey.mass;
            organicMatterIncrement += 0.5;
        }

    }
    public void animalAttack() {
        if (type.equals("Carnivores")) {
            attackChance = (100-30)/10.0;
        }
        if (type.equals("Parasites")) {
            attackChance = (100-10)/10.0;
        }
        if (type.equals("Herbivores")) {
            attackChance = (100-85)/10.0;
        }
        if (type.equals("Omnivores")) {
            attackChance = (100-60)/10.0;
        }
        if (type.equals("Detrivores")) {
            attackChance = (100-90)/10.0;
        }
    }
}
