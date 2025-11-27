package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import lombok.Data;
import simulation.Simulation;

import java.util.Objects;

@Data
public abstract class Command {
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
}
