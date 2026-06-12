package view;

import controller.GameController;
import model.Chessboard;
import view.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class SaveFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGTH;
    private JLabel background;
    JLabel BackIn;
    private CardLayout cardLayout;
    private JFrame cardFrame;
    private ChessboardComponent chessboardComponent;
    private String ID;
    public SaveFrame(int width, int height,ChessboardComponent chessboardComponent,String id) {
        setTitle("Save Here");
        this.WIDTH = width;
        this.HEIGTH = height;
        this.chessboardComponent = chessboardComponent;
        this.ID = id;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        addSaveButtons();
    }
    public void addSaveButtons(){
        {
            JButton button1 = new JButton("存档1");
            button1.setFont(new Font("黑体", Font.BOLD, 30));
            button1.setBounds(HEIGTH - 10, HEIGTH / 10 + 80, 200, 100);
            button1.setLocation(HEIGTH - 600, HEIGTH / 10);
            button1.setSize(200, 100);
            add(button1);
            button1.addActionListener(e -> {
                chessboardComponent.getGameController().Save(ID,1);
                setVisible(false);
            });
        }
        {
            JButton button2 = new JButton("存档2");
            button2.setFont(new Font("黑体", Font.BOLD, 30));
            button2.setBounds(HEIGTH - 10, HEIGTH / 10 + 80, 200, 100);
            button2.setLocation(HEIGTH - 600, HEIGTH / 10 + 200);
            button2.setSize(200, 100);
            add(button2);
            button2.addActionListener(e -> {
                chessboardComponent.getGameController().Save(ID,2);
                setVisible(false);
            });
        }
        {
            JButton button3 = new JButton("存档3");
            button3.setFont(new Font("黑体", Font.BOLD, 30));
            button3.setBounds(HEIGTH - 10, HEIGTH / 10 + 80, 200, 100);
            button3.setLocation(HEIGTH - 600, HEIGTH / 10 + 400);
            button3.setSize(200, 100);
            add(button3);
            button3.addActionListener(e -> {
                chessboardComponent.getGameController().Save(ID,3);
                setVisible(false);
            });
        }
        {
            JButton button4 = new JButton("存档4");
            button4.setFont(new Font("黑体", Font.BOLD, 30));
            button4.setBounds(HEIGTH - 10, HEIGTH / 10 + 80, 200, 100);
            button4.setLocation(HEIGTH - 330, HEIGTH / 10);
            button4.setSize(200, 100);
            add(button4);
            button4.addActionListener(e -> {
                chessboardComponent.getGameController().Save(ID,4);
                setVisible(false);
            });
        }
        {
            JButton button5 = new JButton("存档5");
            button5.setFont(new Font("黑体", Font.BOLD, 30));
            button5.setBounds(HEIGTH - 10, HEIGTH / 10 + 80, 200, 100);
            button5.setLocation(HEIGTH - 330, HEIGTH / 10 + 200);
            button5.setSize(200, 100);
            add(button5);
            button5.addActionListener(e -> {
                chessboardComponent.getGameController().Save(ID,5);
                setVisible(false);
            });
        }
        {
            JButton button6 = new JButton("存档6");
            button6.setFont(new Font("黑体", Font.BOLD, 30));
            button6.setBounds(HEIGTH - 10, HEIGTH / 10 + 80, 200, 100);
            button6.setLocation(HEIGTH - 330, HEIGTH / 10 + 400);
            button6.setSize(200, 100);
            add(button6);
            button6.addActionListener(e -> {
                chessboardComponent.getGameController().Save(ID,6);
                setVisible(false);
            });
        }
    }

}
