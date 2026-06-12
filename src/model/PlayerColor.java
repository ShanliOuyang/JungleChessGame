package model;

import java.awt.*;
import java.io.Serializable;

public enum PlayerColor implements Serializable {
    BLUE(Color.BLUE), RED(Color.RED), GREEN(Color.GREEN), YELLOW(Color.YELLOW);
    private final Color color;
    PlayerColor(Color color) {
        this.color = color;
    }
    public Color getColor() {
        return color;
    }
}
