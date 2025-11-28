package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import lombok.Data;
import simulation.Simulation;

@Data
public class ChangeWeather extends Command {
    private static final int TIMER = 3;
    private String type;
    private Object val;

    public ChangeWeather(final CommandInput input) {
        super(input);
        this.type = input.getType();
        switch (this.type) {
            case "desertStorm":
                this.val = input.isDesertStorm();
                break;
            case "rainfall":
                this.val = input.getRainfall();
                break;
            case "polarStorm":
                this.val = input.getWindSpeed();
                break;
            case "newSeason":
                this.val = input.getSeason();
                break;
            case "peopleHiking":
                this.val = input.getNumberOfHikers();
                break;
            default:
                break;
        }
    }

    /**
     * Prints the message of the command
     * Calls the changeWeather method
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
        if (changeWeather(s)) {
            node.put("message", "The weather has changed.");
        } else {
            node.put("message",
            "ERROR: The weather change does not affect the environment. Cannot perform action");
        }
        node.put("timestamp", getTimestamp());
        return node;
    }

    /**
     * Changes the weather of cells with the corresponding air type
     * Sets the timer to 3 so that at the beginning of the third timestamp it resets the weather
     */
    public boolean changeWeather(final Simulation s) {
        String targetAirType = switch (this.type) {
            case "desertStorm" -> "DesertAir";
            case "polarStorm" -> "PolarAir";
            case "peopleHiking" -> "MountainAir";
            case "newSeason" -> "TemperateAir";
            case "rainfall" -> "TropicalAir";
            default -> null;
        };
        boolean changed = false;
        for (int i = 0; i < s.getMapSize(); i++) {
            for (int j = 0; j < s.getMapSize(); j++) {
                if (s.getMap()[i][j].getAir().getType().equals(targetAirType)) {
                    s.getMap()[i][j].getAir().setWeather(this.val);
                    s.getMap()[i][j].getAir().setWeatherTimer(TIMER);
                    changed = true;
                }
            }
        }
        return changed;
    }
}
