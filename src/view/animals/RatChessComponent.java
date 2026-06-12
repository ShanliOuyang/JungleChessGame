package view.animals;

import model.Cell;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class RatChessComponent extends AnimalChessComponent implements Serializable {
    ImageIcon icon;
    public RatChessComponent(PlayerColor owner, int size, int row,int col) {
        super(owner, size, row, col);
        if (owner == PlayerColor.RED)
            icon = new ImageIcon("..\\Jungle\\Related Photos\\ANIMALRED\\RATRED.png");
        if (owner == PlayerColor.BLUE)
            icon = new ImageIcon("..\\Jungle\\Related Photos\\ANIMALBLUE\\RATBLUE.png");
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (icon != null) {
            g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}


