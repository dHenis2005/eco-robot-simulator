package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import simulation.Simulation;
import simulation.TerritorySectionParams;

public class PrintEnvConditions extends Command {
    public PrintEnvConditions(final CommandInput input) {
        super(input);
    }

    /**
     * Calls super to get what every command should have.
     * Returns the specifics of all the entities on the current cell.
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
        ObjectNode output = mapper.createObjectNode();
        int x = s.getBot().getXPosition();
        int y = s.getBot().getYPosition();
        TerritorySectionParams params = s.getMap()[x][y];
        if (params.getSoil() != null) {
            output.set("soil", params.getSoil().toJSON(mapper));
        }
        if (params.getPlant() != null) {
            output.set("plants", params.getPlant().toJSON(mapper));
        }
        if (params.getAnimal() != null) {
            output.set("animals", params.getAnimal().toJSON(mapper));
        }
        if (params.getWater() != null) {
            output.set("water", params.getWater().toJSON(mapper));
        }
        if (params.getAir() != null) {
            output.set("air", params.getAir().toJSON(mapper));
        }
        root.set("output", output);
        root.put("timestamp", getTimestamp());
        return root;
    }
}
