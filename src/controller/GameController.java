package controller;

import listener.GameListener;
import model.*;
import view.CellComponent;
import view.ChessGameFrame;
import view.ChessboardComponent;
import view.animals.AnimalChessComponent;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;
import java.net.IDN;
import java.util.*;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
 */
public class GameController implements GameListener {
    public Chessboard model;
    private ChessboardComponent view;
    private String IDs;
    public boolean AL=false;
    private int nub;
    private Chessboard oldmodel;
    private PlayerColor currentPlayer;
    public int isload=0;
    private PlayerColor winner;
    private ChessboardPoint selectedPoint;
    private Thread thread;
    public boolean isPlayback;
    ArrayList<Integer> recordX = new ArrayList<>();
    ArrayList<Integer> recordY = new ArrayList<>();
    public ChessboardComponent getView() {
        return view;
    }
    private ChessGameFrame chessGameFrame;

    public void setView(ChessboardComponent view) {
        this.view = view;
    }

    public Chessboard getModel() {
        return model;
    }

    public void setModel(Chessboard model) {
        this.model = model;
    }

    public GameController(ChessboardComponent view, Chessboard model,ChessGameFrame chessGameFrame) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;
        this.winner = null;
        this.chessGameFrame = chessGameFrame;
        model.redDead = new ArrayList<>();
        model.blueDead = new ArrayList<>();
        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
            }
        }
    }

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        if(currentPlayer==PlayerColor.BLUE){
            SwingUtilities.invokeLater(() -> {
                chessGameFrame.setScore4("蓝");
                chessGameFrame.getLabel4().setText("行棋方：" + chessGameFrame.getScore4());
                chessGameFrame.getLabel4().revalidate();
                chessGameFrame.getLabel4().repaint();
            });
        }
        else {
            SwingUtilities.invokeLater(() -> {
                chessGameFrame.setScore4("红");
                chessGameFrame.getLabel4().setText("行棋方：" + chessGameFrame.getScore4());
                chessGameFrame.getLabel4().revalidate();
                chessGameFrame.getLabel4().repaint();
            });
        }
    }
    private boolean win() {
        // TODO: Check the board if there is a winner
        if (model.getGrid()[0][3].getPiece() != null||model.redDead.size() == 8) {
            this.winner = PlayerColor.BLUE;
            SwingUtilities.invokeLater(() -> {
                chessGameFrame.setScore1(chessGameFrame.getScore1() + 1);
                chessGameFrame.getLabel1().setText("得分：" + chessGameFrame.getScore1());
                chessGameFrame.getLabel1().revalidate();
                chessGameFrame.getLabel1().repaint();
            });
            return true;
        }
        if (model.getGrid()[8][3].getPiece() != null||model.blueDead.size() == 8) {
            this.winner = PlayerColor.RED;
            SwingUtilities.invokeLater(() -> {
                chessGameFrame.setScore2(chessGameFrame.getScore2() + 1);
                chessGameFrame.getLabel2().setText("得分：" + chessGameFrame.getScore2());
                chessGameFrame.getLabel2().revalidate();
                chessGameFrame.getLabel2().repaint();
            });
            return true;
        }
        return false;
    }

    public void doWin() {
        JOptionPane.showMessageDialog(view, (winner == PlayerColor.BLUE ? "BLUE" : "RED") + " Win !");
    }
    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            model.moveChessPiece(selectedPoint,point);
            System.out.println(model.getGrid()[point.getRow()][point.getCol()].getPiece().getName());
                //添加放棋音效
            File audioFile = new File("..\\Jungle\\Related BGM\\PUT.wav"); // 指定音频文件路径
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
                //添加放棋音效
            //加入了一段去掉高亮的
           for (int i = 0;i < recordY.size();i++){
                view.getGridComponents()[recordX.get(i)][recordY.get(i)].setSelected(false);
                view.getGridComponents()[recordX.get(i)][recordY.get(i)].revalidate();
                view.getGridComponents()[recordX.get(i)][recordY.get(i)].repaint();
            }
           recordX.clear();
           recordY.clear();
            System.out.println(recordX.size());
            //这里结束
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            SwingUtilities.invokeLater(() -> {
                chessGameFrame.setScore3(chessGameFrame.getScore3() + 1);
                chessGameFrame.getLabel3().setText("回合数：" + chessGameFrame.getScore3());
                chessGameFrame.getLabel3().revalidate();
                //chessGameFrame.getLabel1().repaint();
            });
            selectedPoint = null;
            swapColor();
            view.repaint();
            component.revalidate();
            if (point.getRow() == 0 && point.getCol() == 2) {
                model.getGrid()[point.getRow()][point.getCol()].getPiece().setTrap(1);
            } else if (point.getRow() == 8 && point.getCol() == 2) {
                model.getGrid()[point.getRow()][point.getCol()].getPiece().setTrap(1);
            } else if (point.getRow() == 0 && point.getCol() == 4) {
                model.getGrid()[point.getRow()][point.getCol()].getPiece().setTrap(1);
            } else if (point.getRow() == 8 && point.getCol() == 4) {
                model.getGrid()[point.getRow()][point.getCol()].getPiece().setTrap(1);
            } else if (point.getRow() == 1 && point.getCol() == 3) {
                model.getGrid()[point.getRow()][point.getCol()].getPiece().setTrap(1);
            } else if (point.getRow() == 7 && point.getCol() == 3) {
                model.getGrid()[point.getRow()][point.getCol()].getPiece().setTrap(1);
            } else {
                model.getGrid()[point.getRow()][point.getCol()].getPiece().setTrap(0);
            }
            if (win()) {
                //添加胜利音效
                File audioFileWin = new File("..\\Jungle\\Related BGM\\WIN.wav"); // 指定音频文件路径
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFileWin);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
                //添加到此
                doWin();
                reset();
                return;
            }
            if(AL){
                easyAI();
            }
            // TODO: if the chess enter Dens or Traps and so on
        }
    }

    public void reset() {
        currentPlayer = PlayerColor.BLUE;
        winner = null;
        selectedPoint = null;
        model.resetChessboard();
        SwingUtilities.invokeLater(() -> {
            chessGameFrame.setScore3(0);
            chessGameFrame.getLabel3().setText("回合数：" + chessGameFrame.getScore3());
            chessGameFrame.getLabel3().revalidate();
            //chessGameFrame.getLabel1().repaint();
        });
        model.redDead=new ArrayList<>();
        model.blueDead=new ArrayList<>();
        model.steps = new ArrayList<>();
        if(currentPlayer==PlayerColor.BLUE){
            SwingUtilities.invokeLater(() -> {
                chessGameFrame.setScore4("蓝");
                chessGameFrame.getLabel4().setText("行棋方：" + chessGameFrame.getScore4());
                chessGameFrame.getLabel4().revalidate();
                chessGameFrame.getLabel4().repaint();
            });
        }
        else {
            SwingUtilities.invokeLater(() -> {
                chessGameFrame.setScore4("红");
                chessGameFrame.getLabel4().setText("行棋方：" + chessGameFrame.getScore4());
                chessGameFrame.getLabel4().revalidate();
                chessGameFrame.getLabel4().repaint();
            });
        }
        view.resetChessboardComponent(); // Reset the chessboard in the view
        view.repaint();
    }
    ChessboardPoint src = new ChessboardPoint(1,1);
    // click a cell with a chess
    public void onPlayerClickChessPiece(ChessboardPoint point, view.animals.AnimalChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                //添加拿棋音效
                File audioFile = new File("..\\Jungle\\Related BGM\\GET.wav"); // 指定音频文件路径
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
                //添加拿棋音效
                selectedPoint = point;
                component.setSelected(true);
                System.out.println(model.getChessPieceAt(point).getName());
                //此处为高亮部分
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 7; j++) {
                        ChessboardPoint dest = new ChessboardPoint(i, j);
                        if (model.isValidMove(point, dest)) {//问题就出在这里了
                            System.out.println("problem");
                            view.getGridComponents()[i][j].setSelected(true);
                            recordX.add(i);
                            recordY.add(j);
                            view.getGridComponents()[i][j].revalidate();
                            view.getGridComponents()[i][j].repaint();
                        }
                    }
                }
                //到这里结束
                component.repaint();
                component.revalidate();
                view.repaint();
                view.revalidate();
            }
        } else if (selectedPoint.equals(point)) {//也就是点这个位置的时候 这个位置处于被点击状态
            selectedPoint = null;
            //添加放棋音效
            File audioFile = new File("..\\Jungle\\Related BGM\\PUT.wav"); // 指定音频文件路径
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
            //添加放棋音效
            for (int i = 0;i < recordY.size();i++){
                view.getGridComponents()[recordX.get(i)][recordY.get(i)].setSelected(false);
                view.getGridComponents()[recordX.get(i)][recordY.get(i)].revalidate();
                view.getGridComponents()[recordX.get(i)][recordY.get(i)].repaint();
            }
            recordX.clear();
            recordY.clear();
            //消除
            component.setSelected(false);
            System.out.println("here");
            component.repaint();
            view.revalidate();
            view.repaint();
        } else if (model.isValidMove(selectedPoint, point)) {
            model.captureChessPiece(selectedPoint, point);
            //添加吃子音效
            File audioFile = new File("..\\Jungle\\Related BGM\\EAT.wav"); // 指定音频文件路径
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
            //添加吃子音效
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            for (int i = 0;i < recordY.size();i++){
                view.getGridComponents()[recordX.get(i)][recordY.get(i)].setSelected(false);
                view.getGridComponents()[recordX.get(i)][recordY.get(i)].revalidate();
                view.getGridComponents()[recordX.get(i)][recordY.get(i)].repaint();
            }
            //消除
            swapColor();
            view.repaint();
            view.revalidate();
            component.revalidate();
            SwingUtilities.invokeLater(() -> {
                chessGameFrame.setScore3(chessGameFrame.getScore3() + 1);
                chessGameFrame.getLabel3().setText("回合数：" + chessGameFrame.getScore3());
                chessGameFrame.getLabel3().revalidate();
                //chessGameFrame.getLabel1().repaint();
            });
            if(AL){
                easyAI();
            }
            view.repaint();
        }
        if (win()) {
            doWin();
            reset();
            return;
        }
        // TODO: Implement capture function
    }

    public void regretOneStep(){
        //chessGameFrame.setScore3(chessGameFrame.getScore3() - 1);
        model.steps.remove(model.steps.size() - 1);
        ArrayList<Step> list = model.steps;
        if(isload==0) {
            reset();
            for (int i = 0; i < list.size(); i++) {
                Step step = list.get(i);
                ChessboardPoint src = step.src;
                ChessboardPoint dest = step.dest;
                boolean isCapture = step.captured != null;
                if (!isCapture) {
                    model.moveChessPiece(step.src, step.dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                    SwingUtilities.invokeLater(() -> {
                        chessGameFrame.setScore3(chessGameFrame.getScore3() + 1);
                        chessGameFrame.getLabel3().setText("回合数：" + chessGameFrame.getScore3());
                        chessGameFrame.getLabel3().revalidate();
                        //chessGameFrame.getLabel1().repaint();
                    });
                    if (dest.getRow() == 0 && dest.getCol() == 2) {
                        model.getGrid()[dest.getRow()][dest.getCol()].getPiece().setTrap(1);
                    } else if (dest.getRow() == 8 && dest.getCol() == 2) {
                        model.getGrid()[dest.getRow()][dest.getCol()].getPiece().setTrap(1);
                    } else if (dest.getRow() == 0 && dest.getCol() == 4) {
                        model.getGrid()[dest.getRow()][dest.getCol()].getPiece().setTrap(1);
                    } else if (dest.getRow() == 8 && dest.getCol() == 4) {
                        model.getGrid()[dest.getRow()][dest.getCol()].getPiece().setTrap(1);
                    } else if (dest.getRow() == 1 && dest.getCol() == 3) {
                        model.getGrid()[dest.getRow()][dest.getCol()].getPiece().setTrap(1);
                    } else if (dest.getRow() == 7 && dest.getCol() == 3) {
                        model.getGrid()[dest.getRow()][dest.getCol()].getPiece().setTrap(1);
                    } else {
                        model.getGrid()[dest.getRow()][dest.getCol()].getPiece().setTrap(0);
                    }
                    selectedPoint = null;
                    swapColor();
                    view.repaint();
                } else {
                    model.captureChessPiece(src, dest);
                    //view.removeChessComponentAtGrid(dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                    SwingUtilities.invokeLater(() -> {
                        chessGameFrame.setScore3(chessGameFrame.getScore3() + 1);
                        chessGameFrame.getLabel3().setText("回合数：" + chessGameFrame.getScore3());
                        chessGameFrame.getLabel3().revalidate();
                        //chessGameFrame.getLabel1().repaint();
                    });
                    if (dest.getRow() == 0 && dest.getCol() == 2) {
                        model.getGrid()[dest.getRow()][dest.getCol()].getPiece().setTrap(1);
                    } else if (dest.getRow() == 8 && dest.getCol() == 2) {
                        model.getGrid()[dest.getRow()][dest.getCol()].getPiece().setTrap(1);
                    } else if (dest.getRow() == 0 && dest.getCol() == 4) {
                        model.getGrid()[dest.getRow()][dest.getCol()].getPiece().setTrap(1);
                    } else if (dest.getRow() == 8 && dest.getCol() == 4) {
                        model.getGrid()[dest.getRow()][dest.getCol()].getPiece().setTrap(1);
                    } else if (dest.getRow() == 1 && dest.getCol() == 3) {
                        model.getGrid()[dest.getRow()][dest.getCol()].getPiece().setTrap(1);
                    } else if (dest.getRow() == 7 && dest.getCol() == 3) {
                        model.getGrid()[dest.getRow()][dest.getCol()].getPiece().setTrap(1);
                    } else {
                        model.getGrid()[dest.getRow()][dest.getCol()].getPiece().setTrap(0);
                    }
                    selectedPoint = null;
                    swapColor();
                    view.repaint();
                    view.revalidate();
                }
            }
        }
        else {
            Load(IDs,nub);
            for (int i = 0; i < list.size(); i++) {
                Step step = list.get(i);
                ChessboardPoint src = step.src;
                ChessboardPoint dest = step.dest;
                boolean isCapture = step.captured != null;
                if (!isCapture) {
                    model.moveChessPiece(step.src, step.dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                    SwingUtilities.invokeLater(() -> {
                        chessGameFrame.setScore3(chessGameFrame.getScore3() + 1);
                        chessGameFrame.getLabel3().setText("回合数：" + chessGameFrame.getScore3());
                        chessGameFrame.getLabel3().revalidate();
                        //chessGameFrame.getLabel1().repaint();
                    });
                    selectedPoint = null;
                    swapColor();
                    view.repaint();
                } else {
                    model.captureChessPiece(src, dest);
                    //view.removeChessComponentAtGrid(dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                    SwingUtilities.invokeLater(() -> {
                        chessGameFrame.setScore3(chessGameFrame.getScore3() + 1);
                        chessGameFrame.getLabel3().setText("回合数：" + chessGameFrame.getScore3());
                        chessGameFrame.getLabel3().revalidate();
                        //chessGameFrame.getLabel1().repaint();
                    });
                    selectedPoint = null;
                    swapColor();
                    view.repaint();
                    view.revalidate();
                }
            }
        }
        if(currentPlayer==PlayerColor.BLUE){
            SwingUtilities.invokeLater(() -> {
                chessGameFrame.setScore4("蓝");
                chessGameFrame.getLabel4().setText("行棋方：" + chessGameFrame.getScore4());
                chessGameFrame.getLabel4().revalidate();
                chessGameFrame.getLabel4().repaint();
            });
        }
        else {
            SwingUtilities.invokeLater(() -> {
                chessGameFrame.setScore4("红");
                chessGameFrame.getLabel4().setText("行棋方：" + chessGameFrame.getScore4());
                chessGameFrame.getLabel4().revalidate();
                chessGameFrame.getLabel4().repaint();
            });
        }
    }
    public void playback(){
        isPlayback = true;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Step> steps = model.steps;
                reset();
                for (int i = 0; i < steps.size(); i++) {
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    Step step = steps.get(i);
                    ChessboardPoint src = step.src;
                    ChessboardPoint dest = step.dest;
                    boolean isCapture = step.captured != null;
                    if (!isCapture) {
                        model.moveChessPiece(src, dest);
                        view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                        SwingUtilities.invokeLater(() -> {
                            chessGameFrame.setScore3(chessGameFrame.getScore3() + 1);
                            chessGameFrame.getLabel3().setText("回合数：" + chessGameFrame.getScore3());
                            chessGameFrame.getLabel3().revalidate();
                            //chessGameFrame.getLabel1().repaint();
                        });
                        selectedPoint = null;
                        swapColor();
                        view.repaint();
                    } else {
                        model.captureChessPiece(src, dest);
                        view.removeChessComponentAtGrid(dest);
                        view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                        SwingUtilities.invokeLater(() -> {
                            chessGameFrame.setScore3(chessGameFrame.getScore3() + 1);
                            chessGameFrame.getLabel3().setText("回合数：" + chessGameFrame.getScore3());
                            chessGameFrame.getLabel3().revalidate();
                            //chessGameFrame.getLabel1().repaint();
                        });
                        swapColor();
                        view.repaint();
                        view.revalidate();
                    }
                }
            }
        });
        thread.start();

        isPlayback = false;
    }
    public void Load(String ID, int number) {
        try {
            boolean isSame = false;
            boolean isNew = false;
            String[][] checkSame = new String[9][7];
            File file = new File("..\\Jungle\\Related Load\\" + ID + "\\" + number + ".txt");
            if (!file.exists()){
                JOptionPane.showMessageDialog(null, "未存入存档或文件名错误！");
                throw new Exception("Error");
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            isload=1;
            IDs=ID;
            nub=number;
            reset();
            for (int i = 0; i < 12; i++) {
                if(i < 9) {
                    String[] pieces = reader.readLine().split(" ");
                    if (pieces.length != 7){
                        JOptionPane.showMessageDialog(null, "非法操作：棋盘形状不对！");
                        throw new Exception("Error");
                    }
                    for (int j = 0;j < pieces.length;j++){
                        checkSame[i][j] = pieces[j];
                        if (!pieces[j].equals("1a")&&!pieces[j].equals("2a")&&!pieces[j].equals("3a")&&!pieces[j].equals("4a")
                                &&!pieces[j].equals("5a")&&!pieces[j].equals("6a")&&!pieces[j].equals("7a")&&!pieces[j].equals("1b")
                                &&!pieces[j].equals("2b")&&!pieces[j].equals("3b")&&!pieces[j].equals("4b")&&!pieces[j].equals("5b")
                                &&!pieces[j].equals("6b")&&!pieces[j].equals("7b")&&!pieces[j].equals("-")&&!pieces[j].equals("8a")&&
                        !pieces[j].equals("8b"))
                            isNew = true;
                    }
                    for (int j = 0; j < 7; j++) {
                        view.getGridComponents()[i][j].removeAll();
                        if (!pieces[j].equals("-")) {
                            if (pieces[j].charAt(1) == 'a') {
                                if (pieces[j].charAt(0) == '8')
                                    model.getGrid()[i][j].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant", 8));
                                if (pieces[j].charAt(0) == '7')
                                    model.getGrid()[i][j].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion", 7));
                                if (pieces[j].charAt(0) == '6')
                                    model.getGrid()[i][j].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger", 6));
                                if (pieces[j].charAt(0) == '5')
                                    model.getGrid()[i][j].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard", 5));
                                if (pieces[j].charAt(0) == '4')
                                    model.getGrid()[i][j].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf", 4));
                                if (pieces[j].charAt(0) == '3')
                                    model.getGrid()[i][j].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog", 3));
                                if (pieces[j].charAt(0) == '2')
                                    model.getGrid()[i][j].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat", 2));
                                if (pieces[j].charAt(0) == '1')
                                    model.getGrid()[i][j].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat", 1));
                            }
                            if (pieces[j].charAt(1) == 'b') {
                                if (pieces[j].charAt(0) == '8')
                                    model.getGrid()[i][j].setPiece(new ChessPiece(PlayerColor.RED, "Elephant", 8));
                                if (pieces[j].charAt(0) == '7')
                                    model.getGrid()[i][j].setPiece(new ChessPiece(PlayerColor.RED, "Lion", 7));
                                if (pieces[j].charAt(0) == '6')
                                    model.getGrid()[i][j].setPiece(new ChessPiece(PlayerColor.RED, "Tiger", 6));
                                if (pieces[j].charAt(0) == '5')
                                    model.getGrid()[i][j].setPiece(new ChessPiece(PlayerColor.RED, "Leopard", 5));
                                if (pieces[j].charAt(0) == '4')
                                    model.getGrid()[i][j].setPiece(new ChessPiece(PlayerColor.RED, "Wolf", 4));
                                if (pieces[j].charAt(0) == '3')
                                    model.getGrid()[i][j].setPiece(new ChessPiece(PlayerColor.RED, "Dog", 3));
                                if (pieces[j].charAt(0) == '2')
                                    model.getGrid()[i][j].setPiece(new ChessPiece(PlayerColor.RED, "Cat", 2));
                                if (pieces[j].charAt(0) == '1')
                                    model.getGrid()[i][j].setPiece(new ChessPiece(PlayerColor.RED, "Rat", 1));
                            }
                        }
                        if (pieces[j].equals("-")) {
                            model.getGrid()[i][j].setPiece(null);
                        }
                    }
                }
                if (i == 9) {
                    int Player = reader.read();
                    String line = reader.readLine();
                    System.out.println(Player);
                    if (Player != 1 && Player != 2){
                        JOptionPane.showMessageDialog(null, "非法操作：缺少行棋方！");
                        throw new Exception("Error");
                    }
                    else {
                        if (Player == 1)
                            currentPlayer = PlayerColor.BLUE;
                        if (Player == 2)
                            currentPlayer = PlayerColor.RED;
                        if (currentPlayer == PlayerColor.BLUE) {
                            SwingUtilities.invokeLater(() -> {
                                chessGameFrame.setScore4("蓝");
                                chessGameFrame.getLabel4().setText("行棋方：" + chessGameFrame.getScore4());
                                chessGameFrame.getLabel4().revalidate();
                                chessGameFrame.getLabel4().repaint();
                            });
                        } else {
                            SwingUtilities.invokeLater(() -> {
                                chessGameFrame.setScore4("红");
                                chessGameFrame.getLabel4().setText("行棋方：" + chessGameFrame.getScore4());
                                chessGameFrame.getLabel4().revalidate();
                                chessGameFrame.getLabel4().repaint();
                            });
                        }
                    }
                }
                if (i == 10) {
                    String line = reader.readLine();
                    model.getBlueDead().removeAll(model.getBlueDead());
                    System.out.println(model.blueDead.size() + "BlueDeadCheck");
                    if (line != null) {
                        if (line.isEmpty()) {
                            continue;
                        }
                        if (!line.isEmpty()) {
                            String[] Dead = line.split(" ");
                            for (String s : Dead) {
                                if (s.charAt(0) == '8')
                                    model.getBlueDead().add(new ChessPiece(PlayerColor.BLUE, "Elephant", 8));
                                if (s.charAt(0) == '7')
                                    model.getBlueDead().add(new ChessPiece(PlayerColor.BLUE, "Lion", 7));
                                if (s.charAt(0) == '6')
                                    model.getBlueDead().add(new ChessPiece(PlayerColor.BLUE, "Tiger", 6));
                                if (s.charAt(0) == '5')
                                    model.getBlueDead().add(new ChessPiece(PlayerColor.BLUE, "Leopard", 5));
                                if (s.charAt(0) == '4')
                                    model.getBlueDead().add(new ChessPiece(PlayerColor.BLUE, "Wolf", 4));
                                if (s.charAt(0) == '3')
                                    model.getBlueDead().add(new ChessPiece(PlayerColor.BLUE, "Dog", 3));
                                if (s.charAt(0) == '2')
                                    model.getBlueDead().add(new ChessPiece(PlayerColor.BLUE, "Cat", 2));
                                if (s.charAt(0) == '1')
                                    model.getBlueDead().add(new ChessPiece(PlayerColor.BLUE, "Rat", 1));
                            }
                            System.out.println("hello look at Blue" + model.blueDead.size());
                        }
                    }
                }
                if (i == 11) {
                    String line = reader.readLine();
                    model.getRedDead().removeAll(model.getRedDead());
                    System.out.println(model.redDead.size()+"redDeadcheck");
                    if (line != null) {
                        if (line.isEmpty()) {
                        }
                        if (!line.isEmpty()) {
                            String[] Dead = line.split(" ");
                            for (String s : Dead) {
                                if (s.charAt(0) == '8')
                                    model.redDead.add(new ChessPiece(PlayerColor.RED, "Elephant", 8));
                                if (s.charAt(0) == '7')
                                    model.redDead.add(new ChessPiece(PlayerColor.RED, "Lion", 7));
                                if (s.charAt(0) == '6')
                                    model.redDead.add(new ChessPiece(PlayerColor.RED, "Tiger", 6));
                                if (s.charAt(0) == '5')
                                    model.redDead.add(new ChessPiece(PlayerColor.RED, "Leopard", 5));
                                if (s.charAt(0) == '4')
                                    model.redDead.add(new ChessPiece(PlayerColor.RED, "Wolf", 4));
                                if (s.charAt(0) == '3')
                                    model.redDead.add(new ChessPiece(PlayerColor.RED, "Dog", 3));
                                if (s.charAt(0) == '2')
                                    model.redDead.add(new ChessPiece(PlayerColor.RED, "Cat", 2));
                                if (s.charAt(0) == '1')
                                    model.redDead.add(new ChessPiece(PlayerColor.RED, "Rat", 1));
                            }
                            System.out.println("hello look at Red" + model.redDead.size());
                        }
                    }
                }
            }
            Set<String> seen = new HashSet<String>();
            for(int m = 0; m < 9; m++) {
                for(int n = 0; n < 7; n++) {
                    String curr = checkSame[m][n];
                    if (!curr.equals("-") && seen.contains(curr)) {
                        isSame = true;
                    }
                    seen.add(curr);
                }
            }
            System.out.println(isSame);
            if (model.getGrid()[3][1].getPiece() != null && model.getGrid()[3][1].getPiece().getRank() != 1
                    || model.getGrid()[3][2].getPiece() != null && model.getGrid()[3][2].getPiece().getRank() != 1
                    || model.getGrid()[4][1].getPiece() != null && model.getGrid()[4][1].getPiece().getRank() != 1
                    || model.getGrid()[4][2].getPiece() != null && model.getGrid()[4][2].getPiece().getRank() != 1
                    || model.getGrid()[5][1].getPiece() != null && model.getGrid()[5][1].getPiece().getRank() != 1
                    || model.getGrid()[5][2].getPiece() != null && model.getGrid()[5][2].getPiece().getRank() != 1
                    || model.getGrid()[3][4].getPiece() != null && model.getGrid()[3][4].getPiece().getRank() != 1
                    || model.getGrid()[3][5].getPiece() != null && model.getGrid()[3][5].getPiece().getRank() != 1
                    || model.getGrid()[4][4].getPiece() != null && model.getGrid()[4][4].getPiece().getRank() != 1
                    || model.getGrid()[4][5].getPiece() != null && model.getGrid()[4][5].getPiece().getRank() != 1
                    || model.getGrid()[5][4].getPiece() != null && model.getGrid()[5][4].getPiece().getRank() != 1
                    || model.getGrid()[5][5].getPiece() != null && model.getGrid()[5][5].getPiece().getRank() != 1) {
                JOptionPane.showMessageDialog(null, "非法操作：棋子在水里！");
                throw new Exception("Error");
        }
            if (isSame){
                JOptionPane.showMessageDialog(null, "非法操作：有重复棋子！");
                throw new Exception("Error");
            }
            if(isNew){
                JOptionPane.showMessageDialog(null, "非法操作：有不存在棋子！");
                throw new Exception("Error");
            }
            else {
                view.initiateChessComponent(model);
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 7; j++) {
                        view.getGridComponents()[i][j].revalidate();
                        view.getGridComponents()[i][j].repaint();
                    }
                }
            }
            reader.close();
            System.out.println("Successfully loaded game!");
        } catch (IOException ex) {
            System.out.println("Error loading game!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void Save(String ID, int number) {
        try {
            FileWriter writer = new FileWriter("..\\Jungle\\Related Load\\" + ID + "\\" + number + ".txt");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 7; j++) {
                    if (model.getGrid()[i][j].getPiece() == null) {
                        writer.write("- ");
                    } else {
                        if (model.getGrid()[i][j].getPiece().getOwner() == PlayerColor.BLUE)
                            writer.write(model.getGrid()[i][j].getPiece().getRank() + "a" + " ");
                        if (model.getGrid()[i][j].getPiece().getOwner() == PlayerColor.RED)
                            writer.write(model.getGrid()[i][j].getPiece().getRank() + "b" + " ");
                    }
                }
                writer.write("\n");
            }
            if (currentPlayer == PlayerColor.BLUE)
                writer.write(1);
            if (currentPlayer == PlayerColor.RED)
                writer.write(2);
            writer.write("\n");
            for (int i = 0;i < model.getBlueDead().size();i++) {
                writer.write(model.getBlueDead().get(i).getRank() + "a ");
            }
            writer.write("\n");
            for (int i = 0;i < model.getRedDead().size();i++) {
                writer.write(model.getRedDead().get(i).getRank() + "b ");
            }
            writer.close();
            System.out.println("Successfully saved game!");
        } catch (IOException ex) {
            System.out.println("Error saving game!");
        }
    }
    public ArrayList<ChessboardPoint> getCanStepPoints(ChessboardPoint src) {
        ArrayList<ChessboardPoint> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                ChessboardPoint dest = new ChessboardPoint(i, j);
                if (model.isValidMove(src, dest)){
                    //view.getGridComponents()[i][j].canStep = true;
                    list.add(dest);
                }
                if(model.getChessPieceAt(src)!=null&&model.getChessPieceAt(dest)!=null) {
                    if (model.isValidCapture(src, dest)&&model.isValidMove(src,dest)) {
                        //view.gridViews[i][j].canStep = true;
                        list.add(dest);
                    }
                }
            }
        }
        return list;
    }

    public ChessboardPoint[] eastAIGetPoint(){
        ArrayList<ChessboardPoint> canMove = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (model.getGrid()[i][j].getPiece() != null && model.getGrid()[i][j].getPiece().getOwner() == currentPlayer){
                    ArrayList<ChessboardPoint> list = getCanStepPoints(new ChessboardPoint(i, j));
                    if (list.size() != 0) canMove.add(new ChessboardPoint(i, j));
                }
            }
        }

        int size = canMove.size();
        Random random = new Random();
        int index = random.nextInt(size);
        ChessboardPoint src = canMove.get(index);

        ArrayList<ChessboardPoint> list = getCanStepPoints(src);
        size = list.size();
        index = random.nextInt(size);
        ChessboardPoint dest = list.get(index);

        return new ChessboardPoint[]{src, dest};
    }
    public void easyAI() {
        System.out.println("easyAI");

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (Exception e){
                    e.printStackTrace();
                }

                ChessboardPoint[] points = eastAIGetPoint();
                ChessboardPoint src = points[0];
                ChessboardPoint dest = points[1];

                if (model.getChessPieceAt(dest) == null){
                    model.moveChessPiece(src, dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                    SwingUtilities.invokeLater(() -> {
                        chessGameFrame.setScore3(chessGameFrame.getScore3() + 1);
                        chessGameFrame.getLabel3().setText("回合数：" + chessGameFrame.getScore3());
                        chessGameFrame.getLabel3().revalidate();
                        //chessGameFrame.getLabel1().repaint();
                    });
                } else {
                    model.captureChessPiece(src, dest);
                    view.removeChessComponentAtGrid(dest);
                    view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
                    SwingUtilities.invokeLater(() -> {
                        chessGameFrame.setScore3(chessGameFrame.getScore3() + 1);
                        chessGameFrame.getLabel3().setText("回合数：" + chessGameFrame.getScore3());
                        chessGameFrame.getLabel3().revalidate();
                        //chessGameFrame.getLabel1().repaint();
                    });
                }
                swapColor();
                view.repaint();
                view.getGridComponents()[dest.getRow()][dest.getCol()].revalidate();
                if (win()){
                    doWin();
                    reset();
                }
            }
        });
        thread.start();
    }

}
//load和save和reset方法写在gamecontroller里了
//给controller加入了chessframe的传参 并定义了prvate chessgameframe
//给win里增加了一点判断
//加了write dead的内容
//添加了recordX和recordY两个arraylist