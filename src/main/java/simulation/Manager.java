package simulation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.GetEnergy;
import commands.MoveBot;
import commands.PrintEnvConditions;
import commands.PrintMap;
import commands.Recharge;
import commands.SimulationCommands;
import commands.ScanObj;
import commands.ChangeWeather;
import fileio.CommandInput;
import fileio.SimulationInput;

import java.util.List;

public class Manager {
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Iterates through all the simulation and for each one
     * iterates through the commands and executes them.
     */
    public ArrayNode runSimulations(final List<SimulationInput> simulations,
                                    final List<CommandInput> commands) {
        ArrayNode output = mapper.createArrayNode();
        int simIn = 0;
        Simulation s = null;
        for (CommandInput comIn : commands) {
            switch (comIn.getCommand()) {
                case "startSimulation":
                    ObjectNode startNode = mapper.createObjectNode();
                    startNode.put("command", "startSimulation");
                    if (s == null) {
                        if (simIn < simulations.size()) {
                            s = SimulationCommands.startSimulation(simulations.get(simIn));
                            s.createMap(simulations.get(simIn));
                            simIn++;
                            startNode.put("message", "Simulation has started.");
                        }
                    } else {
                        startNode.put("message",
                                "ERROR: Simulation already started. Cannot perform action");
                    }
                    startNode.put("timestamp", comIn.getTimestamp());
                    output.add(startNode);
                    break;
                case "endSimulation":
                    ObjectNode endNode = mapper.createObjectNode();
                    endNode.put("command", "endSimulation");
                    if (s != null) {
                        endNode.put("message", "Simulation has ended.");
                    } else {
                        endNode.put("message",
                                "ERROR: Simulation not started. Cannot perform action");
                    }
                    s = SimulationCommands.endSimulation(s);
                    endNode.put("timestamp", comIn.getTimestamp());
                    output.add(endNode);
                    break;
                case "printEnvConditions":
                    PrintEnvConditions debugEnv = new PrintEnvConditions(comIn);
                    ObjectNode pEnvNode = debugEnv.print(mapper, s);
                    output.add(pEnvNode);
                    break;
                case "printMap":
                    PrintMap debugMap = new PrintMap(comIn);
                    ObjectNode pMap = debugMap.print(mapper, s);
                    output.add(pMap);
                    break;
                case "moveRobot":
                    MoveBot move = new MoveBot(comIn);
                    ObjectNode mBot = move.print(mapper, s);
                    output.add(mBot);
                    break;
                case "rechargeBattery":
                    Recharge recharge = new Recharge(comIn);
                    ObjectNode rBot = recharge.print(mapper, s);
                    output.add(rBot);
                    break;
                case "getEnergyStatus":
                    GetEnergy debugEnergy = new GetEnergy(comIn);
                    ObjectNode eNode = debugEnergy.print(mapper, s);
                    output.add(eNode);
                    break;
                case "changeWeatherConditions":
                    ChangeWeather weather = new ChangeWeather(comIn);
                    ObjectNode wNode = weather.print(mapper, s);
                    output.add(wNode);
                    break;
                case "scanObject":
                    ScanObj scan = new ScanObj(comIn);
                    ObjectNode sNode = scan.print(mapper, s);
                    output.add(sNode);
                    break;
                default:
                    break;
            }
        }
        return output;
    }

}
