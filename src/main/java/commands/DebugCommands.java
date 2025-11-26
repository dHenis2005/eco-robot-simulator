package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import simulation.Simulation;
import simulation.TerritorySectionParams;

public class DebugCommands {
    String command;
    int timestamp;
    public DebugCommands(CommandInput input) {
        this.command = input.getCommand();
        this.timestamp = input.getTimestamp();
    }
    public ObjectNode printEnvConditions(ObjectMapper mapper,  TerritorySectionParams params) {
        ObjectNode root = mapper.createObjectNode();
        ObjectNode output = mapper.createObjectNode();

        root.put("command", "printEnvConditions");

        output.set("soil", params.getSoil().toJSON(mapper));
        output.set("plants", params.getPlants().toJSON(mapper));
        output.set("animals", params.getAnimals().toJSON(mapper));
        output.set("water", params.getWater().toJSON(mapper));
        output.set("air", params.getAir().toJSON(mapper));

        root.set("output", output);
        root.put("timestamp", timestamp);

        return root;
    }
    public ObjectNode printMap(ObjectMapper mapper, Simulation s) {
        ObjectNode root = mapper.createObjectNode();
        ArrayNode output = mapper.createArrayNode();
        root.put("command", "printMap");
        if (s == null) {
            root.put("message", "ERROR: Simulation not started. Cannot perform action");
            root.put("timestamp", timestamp);
            return root;
        }
        for (int i = 0; i < s.getMapSize(); i++) {
            for (int j = 0; j < s.getMapSize(); j++) {
                ObjectNode sectionNode = mapper.createObjectNode();
                sectionNode.putArray("section").add(j).add(i);
                TerritorySectionParams params = s.getMap()[j][i];
                int obj = 0;
                if (params.getPlants() != null) {
                    obj++;
                }
                if (params.getAnimals() != null) {
                    obj++;
                }
                if (params.getWater() != null) {
                    obj++;
                }
                sectionNode.put("totalNrOfObjects", obj);
                double airQuality = s.getMap()[j][i].getAir().roundedAirQuality();
                if (airQuality >= 70) {
                    sectionNode.put("airQuality", "good");
                } else if (airQuality >= 40) {
                    sectionNode.put("airQuality", "moderate");
                } else {
                    sectionNode.put("airQuality", "poor");
                }
                double soilQuality = s.getMap()[j][i].getSoil().roundedSoilQuality();
                if (soilQuality >= 70) {
                    sectionNode.put("soilQuality", "good");
                } else if (soilQuality >= 40) {
                    sectionNode.put("soilQuality", "moderate");
                } else {
                    sectionNode.put("soilQuality", "poor");
                }
                output.add(sectionNode);
            }
        }
        root.set("output", output);
        root.put("timestamp", timestamp);
        return root;
    }
}
