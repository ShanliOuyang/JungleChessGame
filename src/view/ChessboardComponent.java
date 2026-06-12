package view;

import controller.GameController;
import model.*;
import view.animals.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

public class ChessboardComponent extends JComponent implements Serializable {
    public CellComponent[][] getGridComponents() {
        return gridComponents;
    }
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    public int getCHESS_SIZE() {
        return CHESS_SIZE;
    }
    private final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    private final Set<ChessboardPoint> trapCell = new HashSet<>();
    private final Set<ChessboardPoint> caveCell = new HashSet<>();
    //set类比于arraylist Hashset不存储相同元素
    private GameController gameController;
    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        //启用鼠标处理
        setLayout(null);
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);
        initiateGridComponents();
    }
    //负责把棋盘上的各种动物初始化放上去(通过chessPiece的名字）
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    System.out.println(chessPiece.getOwner());
                    AnimalChessComponent chessComponent = null;
                    if (Objects.equals(chessPiece.getName(), "Cat")) {
                        chessComponent = new CatChessComponent(chessPiece.getOwner(), CHESS_SIZE, i, j);
                    }
                    else if (Objects.equals(chessPiece.getName(), "Lion")) {
                        chessComponent = new LionChessComponent(chessPiece.getOwner(), CHESS_SIZE, i, j);
                    }
                    else if (Objects.equals(chessPiece.getName(), "Elephant")) {
                        chessComponent = new ElephantChessComponent(chessPiece.getOwner(), CHESS_SIZE, i, j);
                    }
                    else if (Objects.equals(chessPiece.getName(), "Tiger")) {
                        chessComponent = new TigerChessComponent(chessPiece.getOwner(), CHESS_SIZE, i, j);
                    }
                    else if (Objects.equals(chessPiece.getName(), "Leopard")) {
                        chessComponent = new LeopardChessComponent(chessPiece.getOwner(), CHESS_SIZE, i, j);
                    }
                    else if (Objects.equals(chessPiece.getName(), "Rat")) {
                        chessComponent = new RatChessComponent(chessPiece.getOwner(), CHESS_SIZE, i, j);
                    }
                    else if (Objects.equals(chessPiece.getName(), "Dog")) {
                        chessComponent = new DogChessComponent(chessPiece.getOwner(), CHESS_SIZE, i, j);
                    }
                    else if (Objects.equals(chessPiece.getName(), "Wolf")) {
                        chessComponent = new WolfChessComponent(chessPiece.getOwner(), CHESS_SIZE, i, j);
                    }
                    gridComponents[i][j].add(chessComponent);
                }
            }
        }
    }
    //定义格子里面的填充物（即初始地图）
    public void initiateGridComponents() {
        riverCell.add(new ChessboardPoint(3,1));
        riverCell.add(new ChessboardPoint(3,2));
        riverCell.add(new ChessboardPoint(4,1));
        riverCell.add(new ChessboardPoint(4,2));
        riverCell.add(new ChessboardPoint(5,1));
        riverCell.add(new ChessboardPoint(5,2));
        riverCell.add(new ChessboardPoint(3,4));
        riverCell.add(new ChessboardPoint(3,5));
        riverCell.add(new ChessboardPoint(4,4));
        riverCell.add(new ChessboardPoint(4,5));
        riverCell.add(new ChessboardPoint(5,4));
        riverCell.add(new ChessboardPoint(5,5));
        trapCell.add(new ChessboardPoint(8, 2));
        trapCell.add(new ChessboardPoint(8, 4));
        trapCell.add(new ChessboardPoint(7, 3));
        trapCell.add(new ChessboardPoint(0, 2));
        trapCell.add(new ChessboardPoint(0, 4));
        trapCell.add(new ChessboardPoint(1, 3));
        caveCell.add(new ChessboardPoint(8, 3));
        caveCell.add(new ChessboardPoint(0, 3));
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(GridTypes.WATER, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else if (trapCell.contains(temp)) {
                    cell = new CellComponent(GridTypes.TRAP, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else if (caveCell.contains(temp)){
                    cell = new CellComponent(GridTypes.CAVE, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else {
                    cell = new CellComponent(GridTypes.GRASS, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
            }
        }
    }
    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }
    public void setChessComponentAtGrid(ChessboardPoint point, AnimalChessComponent chess) {
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).add(chess);
    }
    public AnimalChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        AnimalChessComponent animal = (AnimalChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        animal.setSelected(false);
        return animal;
    }
    //在这里添加其他动物的动作指令
    private CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }
    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y/CHESS_SIZE +  ", " +point.x/CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y/CHESS_SIZE, point.x/CHESS_SIZE);
    }
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
    protected void processMouseEvent(MouseEvent e) {//添加音效部分
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
                //没有棋子 可以添加音效
            } else {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (AnimalChessComponent)clickedComponent.getComponents()[0]);
            }
        }
    }
    public void resetChessboardComponent() {
        removeAll();
        initiateGridComponents();
        initiateChessComponent(gameController.model);
        revalidate();
    }
    public GameController getGameController() {
        return gameController;
    }
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
    public void regret(){
        gameController.regretOneStep();
    }
    public void playback(){
        gameController.playback();
    }
}
//这里面对gameController动用了getter和setter
//CHESS-SIZE的getter和setter