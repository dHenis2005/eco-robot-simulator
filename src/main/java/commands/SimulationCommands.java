package commands;

import fileio.CommandInput;
import fileio.SimulationInput;
import simulation.Simulation;

public class SimulationCommands {
    String command;
    int timestamp;
    public SimulationCommands(CommandInput input) {
        this.command = input.getCommand();
        this.timestamp = input.getTimestamp();
    }
    public static Simulation startSimulation(SimulationInput s) {

        return new Simulation(s);
    }
    public static Simulation endSimulation() {
        return null;
    }
}
