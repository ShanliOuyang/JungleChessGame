package view.animals;

import model.Cell;
import model.PlayerColor;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;

public class  AnimalChessComponent extends JComponent implements Serializable {
    public PlayerColor owner;
    public boolean selected;
    public void setSize(int size) {
        this.size = size;
    }
    public int size;
    int col;
    int row;
    public AnimalChessComponent(PlayerColor owner, int size,int row,int col) {
        this.owner = owner;
        this.selected = false;
        setSize(size / 2, size / 2);
        setLocation(0, 0);
        setVisible(true);
        this.size = size;
        this.row = row;
        this.col = col;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isSelected()) {
        Graphics2D g1 = (Graphics2D) g;
        g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//一些渲染图像的效果，不用管
        g1.setColor(new Color(255, 255, 255, 100));
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Double(1, 1, this.getWidth() - 2, this.getHeight() - 2, size / 4, size / 4);
        g1.fill(roundedRectangle);
        }
    }
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getCol() {
        return col;
    }
    public void setCol(int col) {
        this.col = col;
    }
}

//加了size的setter
//修改了传参形式 加了col和row的getter和setter