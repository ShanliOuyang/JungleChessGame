package view;

import controller.GameController;
import model.ChessPiece;
import model.Chessboard;
import model.ChessboardPoint;
import model.PlayerColor;
import view.LoginFrame;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class ChessGameFrame extends JFrame implements Serializable {
    public void setModel(Chessboard model) {
        this.model = model;
    }

    private Chessboard model;
    private final int WIDTH;
    private final int HEIGTH;
    private final int ONE_CHESS_SIZE;
    private ChessboardComponent chessboardComponent;
    private JLabel background;
    public boolean CoolOr;
    public final JLabel BGCool;
    public final JLabel BGHot;
    private Clip clip;
    private AudioInputStream audioInputStream;
    private AudioInputStream audioInputStream1;
    private String ID;
    int score1 = 0;
    int score2 = 0;
    int score3 = 0;
    String score4 = "蓝";
    JLabel label1 = new JLabel();
    JLabel label2 = new JLabel();
    JLabel label3 = new JLabel();
    JLabel label4 = new JLabel();

    public ChessGameFrame(int width, int height, String id) {
        setTitle("Jungle");
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;
        this.ID = id;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        addChessboard();
        addButton();
        addLabel();
        addSoundClose();
        addBack();
        CoolOr = true;
        {
            Image image = new ImageIcon("..\\Jungle\\Related Photos\\BACKPIC\\BACKCOOL.png").getImage();
            image = image.getScaledInstance(1100, 810, Image.SCALE_DEFAULT);
            ImageIcon icon = new ImageIcon(image);
            BGCool = new JLabel(icon);
            BGCool.setSize(1100, 810);
            BGCool.setLocation(0, 0);
        }
        {
            Image image1 = new ImageIcon("..\\Jungle\\Related Photos\\BACKPIC\\BACKHOT.png").getImage();
            image1 = image1.getScaledInstance(1100, 810, Image.SCALE_DEFAULT);
            ImageIcon icon1 = new ImageIcon(image1);
            BGHot = new JLabel(icon1);
            BGHot.setSize(1100, 810);
            BGHot.setLocation(0, 0);
        }
        background = BGCool;
        try {
            this.audioInputStream = AudioSystem.getAudioInputStream(new File("..\\Jungle\\Related BGM\\BGMCOOL.wav"));
            this.clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        add(background);
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    //添加的棋盘 这里可以设置位置
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 15);
        add(chessboardComponent);
    }

    private void addButton() {//在主界面加入了需要的按钮
        JButton button = new JButton();
        JButton button1 = new JButton();
        JButton button2 = new JButton();
        JButton button3 = new JButton();
        JButton button4 = new JButton();
        JButton button5 = new JButton();
        JButton button6 = new JButton();
        File imageFile = new File("..\\Jungle\\Related Photos\\BUTTONPIC\\BUTTONCOOL.png");
        File imageFile1 = new File("..\\Jungle\\Related Photos\\BUTTONPIC\\BUTTONHOT.png");
        //System.out.println(imageFile.getAbsolutePath()); debug用这条程序
        Image image = null;
        Image image1 = null;
        try {
            image = ImageIO.read(imageFile).getScaledInstance(220, 100, Image.SCALE_DEFAULT);
            image1 = ImageIO.read(imageFile1).getScaledInstance(220, 100, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon iconCool = new ImageIcon(image);
        ImageIcon iconHot = new ImageIcon(image1);//以上部分为对按钮 把图像引入
        {
            button.setBorderPainted(false);
            button.setOpaque(false);
            button.setContentAreaFilled(false);//以上三行为将按钮原本的东西抹掉
            button.setIcon(iconCool);
            JLabel label = new JLabel("读档");
            label.setFont(new Font("黑体", Font.BOLD, 30));
            label.setForeground(Color.WHITE); // 设置文字颜色（可选）
            label.setBounds(0, 0, 200, 100);
            button.setBounds(HEIGTH - 10, HEIGTH / 10 + 80, 200, 100);
            button.add(label);
            button.setLocation(HEIGTH - 10, HEIGTH / 10 + 80);
            button.setSize(200, 60);
            button.addActionListener((e) -> {
                LoadFrame newLoad = new LoadFrame(610, 660, chessboardComponent, ID);
            });
            add(button);
        }
        {//即悔棋
            button1.setBorderPainted(false);
            button1.setOpaque(false);
            button1.setContentAreaFilled(false);
            button1.setIcon(iconCool);
            JLabel label = new JLabel("后退");
            label.setFont(new Font("黑体", Font.BOLD, 30));
            label.setForeground(Color.WHITE); // 设置文字颜色（可选）
            label.setBounds(0, 0, 200, 100);
            button1.setBounds(HEIGTH - 10, HEIGTH / 10 + 80, 200, 100);
            button1.add(label);
            button1.setLocation(HEIGTH - 10, HEIGTH / 10 + 160);
            button1.setSize(200, 60);
            add(button1);
            button1.addActionListener(e -> {
                chessboardComponent.regret();
            });
        }
        {//保存当前局面 下次从这里开始
            button2.setBorderPainted(false);
            button2.setOpaque(false);
            button2.setContentAreaFilled(false);//以上三行为将按钮原本的东西抹掉
            button2.setIcon(iconCool);
            JLabel label = new JLabel("存档");
            label.setFont(new Font("黑体", Font.BOLD, 30));
            label.setForeground(Color.WHITE); // 设置文字颜色（可选）
            label.setBounds(0, 0, 200, 100);
            button2.setBounds(HEIGTH - 10, HEIGTH / 10 + 80, 200, 100);
            button2.add(label);
            button2.setLocation(HEIGTH - 10, HEIGTH / 10 + 240);
            button2.setSize(200, 60);
            button2.addActionListener((e) -> {
                SaveFrame newSave = new SaveFrame(610, 660, chessboardComponent, ID);
            });
            add(button2);
        }
        {//把之前的步骤都放回去
            button3.setBorderPainted(false);
            button3.setOpaque(false);
            button3.setContentAreaFilled(false);//以上三行为将按钮原本的东西抹掉
            button3.setIcon(iconCool);
            JLabel label = new JLabel("回放");
            label.setFont(new Font("黑体", Font.BOLD, 30));
            label.setForeground(Color.WHITE); // 设置文字颜色（可选）
            label.setBounds(0, 0, 200, 100);
            button3.setBounds(HEIGTH - 10, HEIGTH / 10 + 80, 200, 100);
            button3.add(label);
            button3.setLocation(HEIGTH - 10, HEIGTH / 10 + 320);
            button3.setSize(200, 60);
            add(button3);
            button3.addActionListener(e -> {
                chessboardComponent.playback();
            });
        }
        {//reset功能添加
            button4.setBorderPainted(false);
            button4.setOpaque(false);
            button4.setContentAreaFilled(false);//以上三行为将按钮原本的东西抹掉
            button4.setIcon(iconCool);
            JLabel label = new JLabel("结束");
            label.setFont(new Font("黑体", Font.BOLD, 30));
            label.setForeground(Color.WHITE); // 设置文字颜色（可选）
            label.setBounds(0, 0, 200, 100);
            button4.setBounds(HEIGTH - 10, HEIGTH / 10 + 80, 200, 100);
            button4.add(label);//结束这局 不存档
            button4.addActionListener((e) -> {
                int result = JOptionPane.showConfirmDialog(this, "您确认要结束这局吗？", "小提示", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    chessboardComponent.getGameController().reset();
                }
            });
            button4.setLocation(HEIGTH - 10, HEIGTH / 10 + 400);
            button4.setSize(200, 60);
            add(button4);
        }
        {
            button5.setBorderPainted(false);
            button5.setOpaque(false);
            button5.setContentAreaFilled(false);//以上三行为将按钮原本的东西抹掉
            button5.setIcon(iconCool);

            JLabel label = new JLabel("切换风格");
            label.setFont(new Font("黑体", Font.BOLD, 30));
            label.setForeground(Color.WHITE); // 设置文字颜色（可选）
            label.setBounds(0, 0, 200, 100);
            button5.setBounds(HEIGTH - 10, HEIGTH / 10 + 80, 200, 100);
            button5.add(label);

            button5.setIcon(iconCool);
            button5.setLocation(HEIGTH - 10, HEIGTH / 10 + 480);
            button5.setSize(200, 60);
            add(button5);
            button5.addActionListener((e) -> {
                if (CoolOr) {
                    remove(background);
                    CoolOr = false;
                    background = BGHot;
                    add(background);
                    button.setIcon(iconHot);
                    button.revalidate();
                    button.repaint();
                    button1.setIcon(iconHot);
                    button1.revalidate();
                    button1.repaint();
                    button2.setIcon(iconHot);
                    button2.revalidate();
                    button2.repaint();
                    button3.setIcon(iconHot);
                    button3.revalidate();
                    button3.repaint();
                    button4.setIcon(iconHot);
                    button4.revalidate();
                    button4.repaint();
                    button5.setIcon(iconHot);
                    button5.revalidate();
                    button5.repaint();
                    button6.setIcon(iconHot);
                    button6.revalidate();
                    button6.repaint();
                    clip.stop();
                    try {
                        audioInputStream1 = AudioSystem.getAudioInputStream(new File("..\\Jungle\\Related BGM\\BGMHOT.wav"));
                        clip = AudioSystem.getClip();
                        clip.open(audioInputStream1);
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    remove(background);
                    CoolOr = true;
                    background = BGCool;
                    add(background);
                    button.setIcon(iconCool);
                    button.revalidate();
                    button.repaint();
                    button1.setIcon(iconCool);
                    button1.revalidate();
                    button1.repaint();
                    button2.setIcon(iconCool);
                    button2.revalidate();
                    button2.repaint();
                    button3.setIcon(iconCool);
                    button3.revalidate();
                    button3.repaint();
                    button4.setIcon(iconCool);
                    button4.revalidate();
                    button4.repaint();
                    button5.setIcon(iconCool);
                    button5.revalidate();
                    button5.repaint();
                    button6.setIcon(iconCool);
                    button6.revalidate();
                    button6.repaint();
                    clip.stop();
                    try {
                        audioInputStream = AudioSystem.getAudioInputStream(new File("..\\Jungle\\Related BGM\\BGMCOOL.wav"));
                        clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                repaint();
                revalidate();
            });
        }
        {
            button6.setBorderPainted(false);
            button6.setOpaque(false);
            button6.setContentAreaFilled(false);//以上三行为将按钮原本的东西抹掉
            button6.setIcon(iconCool);
            JLabel label = new JLabel("退出");
            label.setFont(new Font("黑体", Font.BOLD, 30));
            label.setForeground(Color.WHITE); // 设置文字颜色（可选）
            label.setBounds(0, 0, 200, 100);
            button6.setBounds(HEIGTH - 10, HEIGTH / 10 + 80, 200, 100);
            button6.add(label);
            button6.addActionListener((e) -> {
                int result = JOptionPane.showConfirmDialog(this, "您确认要退出吗？", "小提示", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    setVisible(false);
                }

            });
            button6.setLocation(HEIGTH - 10, HEIGTH / 10 + 560);
            button6.setSize(200, 60);
            add(button6);
        }
    }

    public void addSoundClose() {
        JButton buttonC = new JButton();
        JButton buttonO = new JButton();
        {
            Icon icon = new ImageIcon("..\\Jungle\\Related Photos\\BUTTONPIC\\CLOSEBUTTON.png");
            buttonC.setBorderPainted(false);
            buttonC.setOpaque(false);
            buttonC.setContentAreaFilled(false);//以上三行为将按钮原本的东西抹掉
            buttonC.setIcon(icon);
            buttonC.setBounds(10, 10, icon.getIconWidth(), icon.getIconHeight());
            buttonC.addActionListener((e) -> {
                clip.stop();
                buttonC.setVisible(false);
                buttonO.setVisible(true);
            });
            buttonC.setLocation(HEIGTH - 750, HEIGTH / 10);
            buttonC.setSize(50, 50);
            add(buttonC);
        }
        {
            Icon icon = new ImageIcon("..\\Jungle\\Related Photos\\BUTTONPIC\\OPENBUTTON.png");
            buttonO.setBorderPainted(false);
            buttonO.setOpaque(false);
            buttonO.setContentAreaFilled(false);//以上三行为将按钮原本的东西抹掉
            buttonO.setIcon(icon);
            buttonO.setBounds(10, 10, icon.getIconWidth(), icon.getIconHeight());
            buttonO.addActionListener((e) -> {
                clip.start();
                buttonO.setVisible(false);
                buttonC.setVisible(true);
            });
            buttonO.setLocation(HEIGTH - 750, HEIGTH / 10);
            buttonO.setSize(50, 50);
            add(buttonO);
        }
    }

    public void addLabel() {
        {
            label1.setText("得分：0");
            label1.setFont(new Font("黑体", Font.BOLD, 30));
            label1.setForeground(new Color(105, 200, 250));
            label1.setBounds(0, 0, 200, 100);
            label1.setLocation(HEIGTH - 125, HEIGTH / 10 + 450);
            add(label1);
        }
        {
            label2.setText("得分：0");
            label2.setFont(new Font("黑体", Font.BOLD, 30));
            label2.setForeground(Color.RED);
            label2.setBounds(0, 0, 200, 100);
            label2.setLocation(HEIGTH - 125, HEIGTH / 10 + 20);
            add(label2);
        }
        {
            label3.setText("回合数：0");
            label3.setFont(new Font("黑体", Font.BOLD, 20));
            label3.setForeground(Color.BLACK);
            label3.setBounds(0, 0, 200, 100);
            label3.setLocation(HEIGTH - 125, HEIGTH / 10 + 235);
            add(label3);
        }
        {
            label4.setText("行棋方：蓝");
            label4.setFont(new Font("黑体", Font.BOLD, 25));
            label4.setForeground(Color.BLACK);
            label4.setBounds(0, 0, 200, 100);
            label4.setLocation(HEIGTH - 800, HEIGTH / 10 + 235);
            add(label4);
        }
    }

    public void addBack() {
        JButton back = new JButton();
        Icon icon = new ImageIcon("..\\Jungle\\Related Photos\\BUTTONPIC\\BACKBUTTON.png");
        back.setBorderPainted(false);
        back.setOpaque(false);
        back.setContentAreaFilled(false);//以上三行为将按钮原本的东西抹掉
        back.setIcon(icon);
        back.setBounds(10, 10, icon.getIconWidth(), icon.getIconHeight());
        back.addActionListener((e) -> {
            setVisible(false);
            LoginFrame loginFrame = new LoginFrame(910,710);
            loginFrame.setVisible(true);
        });
        back.setLocation(HEIGTH - 750, HEIGTH / 10 + 560);
        back.setSize(50, 50);
        add(back);
      }
    public void setScore1(int score1) {
        this.score1 = score1;
    }
    public void setScore2(int score2) {
        this.score2 = score2;
    }
    public void setScore3(int score3) {
        this.score3 = score3;
    }
    public void setScore4(String score4) {
        this.score4 = score4;
    }
    public int getScore1() {
        return score1;
    }
    public int getScore2() {
        return score2;
    }
    public int getScore3() {
        return score3;
    }
    public String getScore4() {
        return score4;
    }
    public JLabel getLabel1() {
        return label1;
    }
    public JLabel getLabel2() {
        return label2;
    }
    public JLabel getLabel3() {
        return label3;
    }
    public JLabel getLabel4() {
        return label4;
    }
}
//存读档的鼠标事件有所改写
//chessgameframe的构造器添加了一个ID传参
//添加了一个按钮键 可以关声音
//结束按钮进行了一波调整 可以结束了
//添加了得分label
//添加了很多score和label的getter和setter