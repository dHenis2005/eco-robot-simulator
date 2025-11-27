package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import simulation.Simulation;

public class GetEnergy extends Command {
    public GetEnergy(final CommandInput input) {
        super(input);
    }

    /**
     * Calls super to get what every command should have.
     * Returns the energy points left.
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
        node.put("message", "TerraBot has " + s.getEnergyPoints() + " energy points left.");
        node.put("timestamp", getTimestamp());
        return node;
    }
}
