package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import fileio.InputLoader;
import fileio.SimulationInput;
import simulation.Simulation;
import commands.*;
import simulation.TerritorySectionParams;

import java.io.File;
import java.io.IOException;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {

    private Main() {
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final ObjectWriter WRITER = MAPPER.writer().withDefaultPrettyPrinter();

    /**
     * @param inputPath input file path
     * @param outputPath output file path
     * @throws IOException when files cannot be loaded.
     */
    public static void action(final String inputPath,
                              final String outputPath) throws IOException {

        InputLoader inputLoader = new InputLoader(inputPath);
        ArrayNode output = MAPPER.createArrayNode();

        for (SimulationInput simIn: inputLoader.getSimulations()) {
            Simulation s = null;
            boolean sStarted =  false;
            for (CommandInput comIn : inputLoader.getCommands()) {
                switch (comIn.getCommand()) {
                    case "startSimulation":
                        s = SimulationCommands.startSimulation(simIn);
                        s.createMap(simIn);
                        sStarted = true;
                        ObjectNode startNode = MAPPER.createObjectNode();
                        startNode.put("command", "startSimulation");
                        startNode.put("message", "Simulation has started.");
                        startNode.put("timestamp", comIn.getTimestamp());
                        output.add(startNode);
                        break;
                    case "endSimulation":
                        s = SimulationCommands.endSimulation();
                        sStarted = false;
                        ObjectNode endNode = MAPPER.createObjectNode();
                        endNode.put("command", "endSimulation");
                        endNode.put("message", "Simulation has ended.");
                        endNode.put("timestamp", comIn.getTimestamp());
                        output.add(endNode);
                        break;
                    case "printEnvConditions":
                        if (sStarted) {
                            DebugCommands debug = new DebugCommands(comIn);
                            TerritorySectionParams current = s.getMap()[s.getBot().getX_position()][s.getBot().getY_position()];
                            ObjectNode pEnvNode = debug.printEnvConditions(Main.MAPPER, current);
                            output.add(pEnvNode);
                        } else {
                            ObjectNode pEnvNode = MAPPER.createObjectNode();
                            pEnvNode.put("command", "printEnvConditions");
                            pEnvNode.put("message", "ERROR: Simulation not started. Cannot perform action");
                            pEnvNode.put("timestamp", comIn.getTimestamp());
                            output.add(pEnvNode);
                        }
                        break;
                    case "printMap":
                        DebugCommands debug2 = new DebugCommands(comIn);
                        ObjectNode pMap = debug2.printMap(Main.MAPPER, s);
                        output.add(pMap);
                }
            }
        }
        /*
         * TODO Implement your function here
         *
         * How to add output to the output array?
         * There are multiple ways to do this, here is one example:
         *
         *
         * ObjectNode objectNode = MAPPER.createObjectNode();
         * objectNode.put("field_name", "field_value");
         *
         * ArrayNode arrayNode = MAPPER.createArrayNode();
         * arrayNode.add(objectNode);
         *
         * output.add(arrayNode);
         * output.add(objectNode);
         *
         */

        File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdirs();
        WRITER.writeValue(outputFile, output);
    }
}
