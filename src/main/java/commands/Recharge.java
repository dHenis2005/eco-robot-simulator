package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import simulation.Simulation;

public class Recharge extends Command {
    private int time;

    public Recharge(final CommandInput input) {
        super(input);
        this.time = input.getTimeToCharge();
    }

    /**
     * Calls super to get what every command should have.
     * Calls the actual recharge method.
     * Returns the desired message.
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
        node.put("message", "Robot battery is charging.");
        recharge(s);
        node.put("timestamp", getTimestamp());
        return node;
    }

    /**
     * Sets the charging time and timestamp of the robot.
     * Updates the energy points of the robot.
     */
    public void recharge(final Simulation s) {
        s.getBot().setChargeTime(time);
        s.getBot().setChargeTimestamp(getTimestamp());
        s.getBot().setCharging(true);
        s.setEnergyPoints(s.getEnergyPoints() + s.getBot().getChargeTime());
    }
}
