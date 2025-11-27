package commands;

import fileio.CommandInput;
import fileio.SimulationInput;
import lombok.Data;
import simulation.Simulation;
@Data
public class SimulationCommands {
    private String command;
    private int timestamp;
    private SimulationInput s;
    public SimulationCommands(final CommandInput input, final SimulationInput s) {
        this.command = input.getCommand();
        this.timestamp = input.getTimestamp();
        this.s = s;
    }

    /**
     * Starts a new simulation.
     */
    public static Simulation startSimulation(final SimulationInput s) {
        return new Simulation(s);
    }

    /**
     * Ends the current simulation.
     */
    public static Simulation endSimulation() {
        return null;
    }
}
