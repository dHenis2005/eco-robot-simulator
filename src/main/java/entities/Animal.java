package entities;

import fileio.AnimalInput;

public class Animal extends Entity {
    private static final double PRECENT = 100.0;
    private static final double CARNIVORE = 30.0;
    private static final double HERBIVORE = 85.0;
    private static final double PARASITES = 10.0;
    private static final double OMNIVORES = 60.0;
    private static final double DETRIVORES = 90.0;
    private static final double ROUNDING = 10.0;
    private int state;
//    private double organicMatterIncrement = 0;
    private double attackChance = 0;

    public Animal(final AnimalInput input) {
        super(input.getName(), input.getMass(), input.getType());
    }

    /**
     * Feed
     */
    public void feed(final Animal prey, final Plant plant) {
        if (getType().equals("Carnivores") || getType().equals("Parasites")) {
            setMass(getMass() + prey.getMass());
//            organicMatterIncrement += 0.5;
        }
    }

    /**
     * Calculate the chance to attack bot.
     */
    public double animalAttack() {
        if (getType().equals("Carnivores")) {
            attackChance = (PRECENT - CARNIVORE) / ROUNDING;
        }
        if (getType().equals("Parasites")) {
            attackChance = (PRECENT - PARASITES) / ROUNDING;
        }
        if (getType().equals("Herbivores")) {
            attackChance = (PRECENT - HERBIVORE) / ROUNDING;
        }
        if (getType().equals("Omnivores")) {
            attackChance = (PRECENT - OMNIVORES) / ROUNDING;
        }
        if (getType().equals("Detrivores")) {
            attackChance = (PRECENT - DETRIVORES) / ROUNDING;
        }
        return attackChance;
    }
}
