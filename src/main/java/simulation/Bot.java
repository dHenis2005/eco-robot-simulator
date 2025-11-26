package simulation;

import lombok.Data;

@Data
public class Bot {
    private int x_position;
    private int y_position;
    public Bot(int x_position, int y_position) {
        this.x_position = x_position;
        this.y_position = y_position;
    }

}
