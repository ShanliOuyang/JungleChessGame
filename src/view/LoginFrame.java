package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

import controller.GameController;
import model.Chessboard;

public class LoginFrame extends JFrame{
    private final int WIDTH;
    private final int HEIGTH;
    private JLabel background;
    JLabel BackIn;
    private CardLayout cardLayout;
    private JFrame cardFrame;
    private String ID;
    public LoginFrame(int width, int height) {
        setTitle("Welcome!");
        this.WIDTH = width;
        this.HEIGTH = height;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        addButtonsIn();
        Image image = new ImageIcon("..\\Jungle\\Related Photos\\BACKPIC\\BACKIN.png").getImage();
        image = image.getScaledInstance(910,710,Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);
        BackIn = new JLabel(icon);
        BackIn.setSize(910, 710);
        BackIn.setLocation(0, 0);
        background = BackIn;
        add(background);
    }
    private void addButtonsIn(){
        JButton button = new JButton();
        JButton button1 = new JButton();
        JButton button2 = new JButton();
        File imageFile = new File("..\\Jungle\\Related Photos\\BUTTONPIC\\BUTTONIN.png");
        Image image = null;
        try {
            image = ImageIO.read(imageFile).getScaledInstance(220, 100, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon iconIn = new ImageIcon(image);
        {//进入游戏的button应当在输入用户名之后确定后再显示 回头再做更改
            button.setBorderPainted(false);
            button.setOpaque(false);
            button.setContentAreaFilled(false);//以上三行为将按钮原本的东西抹掉
            button.setIcon(iconIn);
            JLabel label = new JLabel("进入游戏");
            label.setFont(new Font("黑体", Font.BOLD, 30));
            label.setForeground(Color.WHITE); // 设置文字颜色（可选）
            label.setBounds(0, 0, 200, 100);
            button.setBounds(HEIGTH - 10, HEIGTH / 10 + 80, 200, 100);
            button.add(label);
            button.setLocation(HEIGTH - 350, HEIGTH / 10 + 500);
            button.setSize(200, 60);
            add(button);
            button.setVisible(false);
            button.addActionListener(e ->{
                Object[] options = {"普通模式", "AI模式"};
                int choice = JOptionPane.showOptionDialog(null, "请选择游戏模式", "游戏模式选择", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (choice == JOptionPane.YES_OPTION) { // 普通模式
                    setVisible(false);
                    ChessGameFrame mainFrame = new ChessGameFrame(1100, 810,ID);
                    Chessboard model = new Chessboard();
                    mainFrame.setModel(model); // 设置模型对象
                    GameController controller = new GameController(mainFrame.getChessboardComponent(), model,mainFrame);
                    controller.setModel(model);
                } else if (choice == JOptionPane.NO_OPTION) { // AI模式
                    setVisible(false);
                    ChessGameFrame mainFrame = new ChessGameFrame(1100, 810,ID);
                    Chessboard model = new Chessboard();
                    mainFrame.setModel(model); // 设置模型对象
                    GameController controller = new GameController(mainFrame.getChessboardComponent(), model,mainFrame);
                    controller.AL=true;
                    controller.setModel(model);
                }
            });
        }
        {
            button1.setBorderPainted(false);
            button1.setOpaque(false);
            button1.setContentAreaFilled(false);//以上三行为将按钮原本的东西抹掉
            button1.setIcon(iconIn);
            JLabel label = new JLabel("退出游戏");
            label.setFont(new Font("黑体", Font.BOLD, 30));
            label.setForeground(Color.WHITE); // 设置文字颜色（可选）
            label.setBounds(0, 0, 200, 100);
            button1.add(label);
            button1.setBounds(HEIGTH - 10, HEIGTH / 10 + 80, 200, 100);
            button1.setLocation(HEIGTH - 350, HEIGTH / 10 + 400);
            button1.setSize(200, 60);
            add(button1);
            button1.addActionListener(e ->setVisible(false));
        }
        {
            button2.setBorderPainted(false);
            button2.setOpaque(false);
            button2.setContentAreaFilled(false);//以上三行为将按钮原本的东西抹掉
            button2.setIcon(iconIn);
            JLabel label = new JLabel("登录/注册");
            label.setFont(new Font("黑体", Font.BOLD, 30));
            label.setForeground(Color.WHITE); // 设置文字颜色（可选）
            label.setBounds(0, 0, 200, 100);
            button2.add(label);
            button2.setBounds(HEIGTH - 10, HEIGTH / 10 + 80, 200, 100);
            button2.setLocation(HEIGTH - 350, HEIGTH / 10 + 300);
            button2.setSize(200,60);
            add(button2);
            button2.addActionListener(e -> {
                String id = JOptionPane.showInputDialog("请输入您的 ID：");
                if (id != null && !id.isEmpty()) {
                    this.ID = id;
                    String folderPath = "..\\Jungle\\Related Load";
                    File folder = new File(folderPath, ID);
                    if (!folder.exists()) {
                        folder.mkdirs();
                    }
                    button.setVisible(true);
                }
            });
        }
    }
}

//用一个string ID记录下了id
//修改了button内部的方法
//设计了通过ID创造新文件夹的功能



