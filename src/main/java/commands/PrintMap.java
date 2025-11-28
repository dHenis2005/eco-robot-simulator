package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import simulation.Simulation;
import simulation.TerritorySectionParams;

public class PrintMap extends Command {
    private static final int AIR_GOOD = 70;
    private static final int AIR_MOD = 40;
    private static final int SOIL_GOOD = 70;
    private static final int SOIL_MOD = 40;
    public PrintMap(final CommandInput input) {
        super(input);
    }
    /**
     * Calls super to get what every command should have.
     * Iterates through the whole map returning the number of entities and the
     * quality of the air and soil in each cell.
     */
    @Override
    public ObjectNode print(final ObjectMapper mapper, final Simulation s) {
        ObjectNode root = super.print(mapper, s);
        if (s == null) {
            return root;
        }
        if (s.getBot().isCharging()) {
            return root;
        }
        ArrayNode output = mapper.createArrayNode();
        for (int i = 0; i < s.getMapSize(); i++) {
            for (int j = 0; j < s.getMapSize(); j++) {
                ObjectNode sectionNode = mapper.createObjectNode();
                sectionNode.putArray("section").add(j).add(i);
                TerritorySectionParams params = s.getMap()[j][i];
                int obj = 0;
                if (params.getPlant() != null) {
                    obj++;
                }
                if (params.getAnimal() != null) {
                    obj++;
                }
                if (params.getWater() != null) {
                    obj++;
                }
                sectionNode.put("totalNrOfObjects", obj);
                double airQuality = s.getMap()[j][i].getAir().roundUpdatedAirQuality();
                if (airQuality >= AIR_GOOD) {
                    sectionNode.put("airQuality", "good");
                } else if (airQuality >= AIR_MOD) {
                    sectionNode.put("airQuality", "moderate");
                } else {
                    sectionNode.put("airQuality", "poor");
                }
                double soilQuality = s.getMap()[j][i].getSoil().roundedSoilQuality();
                if (soilQuality >= SOIL_GOOD) {
                    sectionNode.put("soilQuality", "good");
                } else if (soilQuality >= SOIL_MOD) {
                    sectionNode.put("soilQuality", "moderate");
                } else {
                    sectionNode.put("soilQuality", "poor");
                }
                output.add(sectionNode);
            }
        }
        root.set("output", output);
        root.put("timestamp", getTimestamp());
        return root;
    }
}
