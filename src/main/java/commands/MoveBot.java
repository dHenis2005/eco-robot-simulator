package commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import simulation.Simulation;
import simulation.TerritorySectionParams;

public class MoveBot extends Command {
    public MoveBot(final CommandInput input) {
        super(input);
    }

    /**
     * Checks the score of all the valid neighbors and returns the minimum score found.
     * If there isn't enough energy returns -1.
     */
    public int moveBot(final Simulation s) {
        int x = s.getBot().getXPosition();
        int y = s.getBot().getYPosition();
        int size = s.getMapSize();
        int minScore = Integer.MAX_VALUE;
        int[][] dir =  {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int xn = x, yn = y;
        for (int[] d:dir) {
            int x1 = x + d[0];
            int y1 = y + d[1];
            if (x1 < 0 || y1 < 0 || x1 >= size || y1 >= size) {
                continue;
            }
            TerritorySectionParams param = s.getMap()[x1][y1];
            int score = param.qualityScore();
            if (score < minScore) {
                minScore = score;
                xn = x1;
                yn = y1;
            }

        }
        if (s.getEnergyPoints() >= minScore) {
            s.getBot().setXPosition(xn);
            s.getBot().setYPosition(yn);
            s.setEnergyPoints(s.getEnergyPoints() - minScore);
            return minScore;
        } else {
            return -1;
        }
    }
    /**
     * Calls super to get what every command should have.
     * Returns the desired message and error if it is the case.
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
        int score = moveBot(s);
        if (score == -1) {
            node.put("message", "ERROR: Not enough battery left. Cannot perform action");
            node.put("timestamp", getTimestamp());
            return node;
        }
        int x = s.getBot().getXPosition();
        int y = s.getBot().getYPosition();
        node.put("message", "The robot has successfully moved to position (" + x + ", " + y + ").");
        node.put("timestamp", getTimestamp());
        return node;
    }
}
