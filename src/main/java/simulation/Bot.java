package simulation;

import lombok.Data;

@Data
public class Bot {
    private int xPosition;
    private int yPosition;
    public Bot(final int xPosition, final int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
    private int chargeTime = 0;
    private int chargeTimestamp = 0;
    private boolean isCharging = false;
}
