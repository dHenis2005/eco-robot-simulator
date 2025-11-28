package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.Air;
import entities.Plant;
import entities.Soil;
import entities.Water;
import fileio.CommandInput;
import lombok.Data;
import simulation.Simulation;

import java.util.Objects;

@Data
public abstract class Command {
    private static final int TIMESTAMP2 = 2;
    private String command;
    private int timestamp;

    public Command(final CommandInput input) {
        this.command = input.getCommand();
        this.timestamp = input.getTimestamp();
    }

    private int rechargeTime = 0;

    /**
     * Returns the output in the desired JSON format.
     */
    public ObjectNode print(final ObjectMapper mapper, final Simulation s) {
        ObjectNode node = new ObjectMapper().createObjectNode();
        node.put("command", command);
        if (s == null && !Objects.equals(command, "startSimulation")) {
            node.put("message", "ERROR: Simulation not started. Cannot perform action");
            node.put("timestamp", timestamp);
            return node;
        }
        rechargeUpdate(s);
        if (s.getBot().getChargeTimestamp() + s.getBot().getChargeTime() > timestamp) {
            node.put("message", "ERROR: Robot still charging. Cannot perform action");
            node.put("timestamp", timestamp);
            return node;
        }
        updateWeather(s);
        return node;
    }

    /**
     * Checks to see if the robot is charging and updates the charge time accordingly.
     */
    public void rechargeUpdate(final Simulation s) {
        if (s == null) {
            return;
        }
        int chargeTime = s.getBot().getChargeTime();
        int chargeTimestamp = s.getBot().getChargeTimestamp();
        if (chargeTime + chargeTimestamp <= timestamp && chargeTime > 0) {
            s.getBot().setChargeTimestamp(0);
            s.getBot().setChargeTime(0);
            s.getBot().setCharging(false);
        }
    }

    /**
     * Updates the weather of the map according to the timer.
     */
    public void updateWeather(final Simulation s) {
        for (int i = 0; i < s.getMapSize(); i++) {
            for (int j = 0; j < s.getMapSize(); j++) {
                Air air = s.getMap()[i][j].getAir();
                Water water = s.getMap()[i][j].getWater();
                Soil soil = s.getMap()[i][j].getSoil();
                Plant plant = s.getMap()[i][j].getPlant();
                if (air.getWeatherTimer() == 1) {
                    air.setWeatherTimer(0);
                    air.resetWeather();
                }
                if (air.getWeatherTimer() > 1) {
                    air.setWeatherTimer(air.getWeatherTimer() - 1);
                }
                if (water != null) {
                    if (water.isScanned()) {
                        if (water.getScanTimestamp() % TIMESTAMP2 == getTimestamp() % TIMESTAMP2) {
                            air.setHumidity(air.addHumidity(0.1));
                            soil.setWaterRetention(soil.addWater(0.1));
                        }
                    }
                }
                if (plant != null) {
                    if (plant.isScanned()) {
                        plant.setGrowthRate(plant.addGrowth(0.2));
                        if (plant.getGrowthRate() <= 3) {
                            plant.oxygenGeneration();
                            air.setOxygenLevel(air.addOxygen(plant.getOxygenPlant()));
                        } else {
                            plant = null;
                        }
                    }
                }
            }
        }
    }
}
