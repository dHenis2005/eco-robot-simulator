package simulation;

import entities.*;
import entities.air.*;
import entities.soil.*;
import fileio.*;
import lombok.Data;
@Data
public class Simulation {
    private TerritorySectionParams[][] map;
    int energyPoints;
    String territoryDim;
    int mapSize;
    private Bot bot;

    public Simulation(SimulationInput input) {
        this.energyPoints = input.getEnergyPoints();
        this.territoryDim = input.getTerritoryDim();
        String x = territoryDim.split("x")[0];
        this.mapSize = Integer.parseInt(x);
        map = new TerritorySectionParams[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                map[i][j] = new TerritorySectionParams();
            }
        }
        this.bot = new Bot(0,0);
    }
    public void createMap(SimulationInput input) {
        for (PlantInput plantInput : input.getTerritorySectionParams().getPlants()) {
            Plant plant = new Plant(plantInput);
            for (PairInput section : plantInput.getSections()) {
                int x = section.getX();
                int y = section.getY();
                map[x][y].setPlants(plant);
            }
        }
        for (AnimalInput animalInput : input.getTerritorySectionParams().getAnimals()) {
            Animal animal = new Animal(animalInput);
            for (PairInput section : animalInput.getSections()) {
                int x = section.getX();
                int y = section.getY();
                map[x][y].setAnimals(animal);
            }
        }
        for (WaterInput waterInput : input.getTerritorySectionParams().getWater()) {
            Water water = new Water(waterInput);
            for (PairInput section : waterInput.getSections()) {
                int x = section.getX();
                int y = section.getY();
                map[x][y].setWater(water);
            }
        }
        for (AirInput airInput : input.getTerritorySectionParams().getAir()) {
            Air air;
            switch (airInput.getType()) {
                case "TropicalAir" -> air = new Tropical(airInput);
                case "DesertAir" -> air = new Desert(airInput);
                case "PolarAir" -> air = new Polar(airInput);
                case "TemperateAir" -> air = new Temperate(airInput);
                case "MountainAir" -> air = new Mountain(airInput);
                default -> throw new IllegalArgumentException("Unknown air type: " + airInput.getType());
            }
            for (PairInput section : airInput.getSections()) {
                int x = section.getX();
                int y = section.getY();
                map[x][y].setAir(air);
            }
        }
        for (SoilInput soilInput : input.getTerritorySectionParams().getSoil()) {
            Soil soil;
            switch (soilInput.getType()) {
                case "ForestSoil" -> soil = new ForestSoil(soilInput);
                case "SwampSoil" -> soil = new SwampSoil(soilInput);
                case "DesertSoil" -> soil = new DesertSoil(soilInput);
                case "GrasslandSoil" -> soil = new GrasslandSoil(soilInput);
                case "TundraSoil" -> soil = new TundraSoil(soilInput);
                default -> throw new IllegalArgumentException("Unknown soil type: " + soilInput.getType());
            }
            for (PairInput section : soilInput.getSections()) {
                int x = section.getX();
                int y = section.getY();
                map[x][y].setSoil(soil);
            }
        }
    }
}
