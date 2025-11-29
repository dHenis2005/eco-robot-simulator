package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import lombok.Data;
import simulation.Simulation;
import simulation.TerritorySectionParams;

@Data
public class ScanObj extends Command {
    private String smell;
    private String color;
    private String sound;

    public ScanObj(final CommandInput input) {
        super(input);
        smell = input.getSmell();
        color = input.getColor();
        sound = input.getSound();
    }

    /**
     * Method to print desired output.
     * It checks what type of entity it scans and sets its scan value to true.
     */
    @Override
    public ObjectNode print(final ObjectMapper mapper, final Simulation s) {
        ObjectNode node = super.print(mapper, s);
        if (s == null) {
            return node;
        }
        if (s.getBot().isCharging()) {
            return node;
        }
        int x = s.getBot().getXPosition();
        int y = s.getBot().getYPosition();
        TerritorySectionParams param = s.getMap()[x][y];
        if (color.equals("none")) {
            if (param.getWater() == null) {
                node.put("message", "ERROR: Object not found. Cannot perform action");
            } else {
                node.put("message", "The scanned object is water.");
                param.getWater().setScanned(true);
                param.getWater().setScanTimestamp(getTimestamp());
                s.setEnergyPoints(s.getEnergyPoints() - 7);
            }
        } else if (sound.equals("none")) {
            if (param.getPlant() == null) {
                node.put("message", "ERROR: Object not found. Cannot perform action");
            } else {
                node.put("message", "The scanned object is a plant.");
                param.getPlant().setScanned(true);
                param.getPlant().setScanTimestamp(getTimestamp());
                s.setEnergyPoints(s.getEnergyPoints() - 7);
            }
        } else {
            if (param.getAnimal() == null) {
                node.put("message", "ERROR: Object not found. Cannot perform action");
            } else {
                node.put("message", "The scanned object is an animal.");
                s.setEnergyPoints(s.getEnergyPoints() - 7);
            }
        }
        node.put("timestamp", getTimestamp());
        return node;
    }
}
