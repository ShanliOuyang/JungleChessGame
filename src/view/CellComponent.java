package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;

public class CellComponent extends JPanel implements Serializable {
    private Color background;
    private GridTypes back;
    public boolean selected;
    public CellComponent(GridTypes back, Point location, int size) {
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
        this.back = back;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        if (back == GridTypes.WATER){
            ImageIcon icon = new ImageIcon("..\\Jungle\\Related Photos\\CELLPIC\\WATER.jpg");
            Image image = icon.getImage();
            g.drawImage(image, 0, 0, null);
            int x, y;
            for (y = 0; y < getHeight(); y++) {
                for (x = 0; x < getWidth(); x++) {
                    int offset = (int) (getHeight() * 0.1 * Math.sin((double) y / getHeight() * Math.PI * 4));
                    g.copyArea(x, y, 1, 1, 0, offset);
                }
            }
        } else if (back == GridTypes.GRASS) {
            ImageIcon icon = new ImageIcon("..\\Jungle\\Related Photos\\CELLPIC\\GRASS.jpg");
            Image image = icon.getImage();
            g.drawImage(image, 0, 0, null);
        }
        else if (back == GridTypes.TRAP){
            ImageIcon icon = new ImageIcon("..\\Jungle\\Related Photos\\CELLPIC\\TRAP.jpg");
            Image image = icon.getImage();
            g.drawImage(image, 0, 0, null);
        }
        else{ImageIcon icon = new ImageIcon("..\\Jungle\\Related Photos\\CELLPIC\\CAVE.jpg");
            Image image = icon.getImage();
            g.drawImage(image, 0, 0, null);
        }
        if (isSelected()) {
            Graphics2D g1 = (Graphics2D) g;
            g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//一些渲染图像的效果，不用管
            g1.setColor(new Color(255, 255, 255, 100));
            RoundRectangle2D roundedRectangle = new RoundRectangle2D.Double(1, 1, this.getWidth() - 2, this.getHeight() - 2,(710 * 4 / 5) / 9  / 4, (710 * 4 / 5) / 9 / 4);
            g1.fill(roundedRectangle);
        }
    }
    public Color getBackground() {
        return background;
    }
    @Override
    public void setBackground(Color background) {
        this.background = background;
    }
}
//添加了selected变量和setter和getter方法
//绘制里面添加了isSelected
