package simulation;

import entities.Animal;
import entities.Plant;
import entities.Water;
import entities.air.Desert;
import entities.air.Mountain;
import entities.air.Polar;
import entities.air.Tropical;
import entities.air.Temperate;
import entities.soil.DesertSoil;
import entities.soil.ForestSoil;
import entities.soil.GrasslandSoil;
import entities.soil.SwampSoil;
import entities.soil.TundraSoil;
import entities.Soil;
import entities.Air;
import fileio.AirInput;
import fileio.AnimalInput;
import fileio.PlantInput;
import fileio.SimulationInput;
import fileio.SoilInput;
import fileio.WaterInput;
import fileio.PairInput;
import lombok.Data;

@Data
public class Simulation {
    private TerritorySectionParams[][] map;
    private int energyPoints;
    private String territoryDim;
    private int mapSize;
    private Bot bot;

    public Simulation(final SimulationInput input) {
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
        this.bot = new Bot(0, 0);
    }
    /**
    * Go through all the entities received on input and put them at the specified coordinates
    */
    public void createMap(final SimulationInput input) {
        for (PlantInput plantInput : input.getTerritorySectionParams().getPlants()) {
            for (PairInput section : plantInput.getSections()) {
                int x = section.getX();
                int y = section.getY();
                Plant plant = new Plant(plantInput);
                map[x][y].setPlant(plant);
            }
        }
        for (AnimalInput animalInput : input.getTerritorySectionParams().getAnimals()) {
            for (PairInput section : animalInput.getSections()) {
                int x = section.getX();
                int y = section.getY();
                Animal animal = new Animal(animalInput);
                map[x][y].setAnimal(animal);
            }
        }
        for (WaterInput waterInput : input.getTerritorySectionParams().getWater()) {
            for (PairInput section : waterInput.getSections()) {
                int x = section.getX();
                int y = section.getY();
                Water water = new Water(waterInput);
                map[x][y].setWater(water);
            }
        }
        for (AirInput airInput : input.getTerritorySectionParams().getAir()) {
            for (PairInput section : airInput.getSections()) {
                int x = section.getX();
                int y = section.getY();
                Air air = switch (airInput.getType()) {
                    case "TropicalAir" -> new Tropical(airInput);
                    case "DesertAir" -> new Desert(airInput);
                    case "PolarAir" -> new Polar(airInput);
                    case "TemperateAir" -> new Temperate(airInput);
                    case "MountainAir" -> new Mountain(airInput);
                    default -> null;
                };
                map[x][y].setAir(air);
            }
        }
        for (SoilInput soilInput : input.getTerritorySectionParams().getSoil()) {
            for (PairInput section : soilInput.getSections()) {
                int x = section.getX();
                int y = section.getY();
                Soil soil = switch (soilInput.getType()) {
                    case "ForestSoil" -> new ForestSoil(soilInput);
                    case "SwampSoil" -> new SwampSoil(soilInput);
                    case "DesertSoil" -> new DesertSoil(soilInput);
                    case "GrasslandSoil" -> new GrasslandSoil(soilInput);
                    case "TundraSoil" -> new TundraSoil(soilInput);
                    default -> null;
                };
                map[x][y].setSoil(soil);
            }
        }
    }
}
