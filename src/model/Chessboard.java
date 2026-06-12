package model;

import java.util.ArrayList;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;
    public ArrayList<Step> steps;
    public ArrayList<ChessPiece> blueDead = new ArrayList<>();
    public ArrayList<ChessPiece> redDead = new ArrayList<>();
    public Chessboard() {
        this.grid = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19
        steps = new ArrayList<>();
        initGrid();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }
    public void resetChessboard() {
        // Reset the grid and chess pieces to the initial state
        for (int row = 0; row < Constant.CHESSBOARD_ROW_SIZE.getNum(); row++) {
            for (int col = 0; col < Constant.CHESSBOARD_COL_SIZE.getNum(); col++) {
                grid[row][col].setPiece(null); // Remove any chess piece from the cell
            }
        }
        initPieces();
        // TODO: Place the chess pieces at their initial positions
        // You need to implement the logic to place the chess pieces at their starting positions on the grid
    }
    private void initPieces() {
        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant",8));
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant",8));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion",7));
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, "Lion",7));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger",6));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Tiger",6));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard",5));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, "Leopard",5));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf",4));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, "Wolf",4));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog",3));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, "Dog",3));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat",2));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, "Cat",2));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat",1));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, "Rat",1));
    }

    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    public ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        else if(isValidMove(src,dest)){
            setChessPiece(dest, removeChessPiece(src));
            steps.add(new Step(src, dest, getChessPieceAt(dest).getOwner()));
        }
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        ChessPiece dead=removeChessPiece(dest);
        setChessPiece(dest,removeChessPiece(src));
        if (dead.getOwner() == PlayerColor.BLUE){
            blueDead.add(dead);
            System.out.println("blue eaten " + blueDead.size());
        } else {
            redDead.add(dead);
            System.out.println("red eaten " + redDead.size());
        }
        steps.add(new Step(src, dest, getChessPieceAt(dest).getOwner(),dead));
        // TODO: Finish the method.
    }

    public Cell[][] getGrid() {
        return grid;
    }
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if(!getChessPieceAt(src).getName().equals("Rat")){//有改
            if(dest.getRow()>=3&&dest.getRow()<=5&&dest.getCol()>=1&&dest.getCol()<=2){
                return false;
            }
            if(dest.getRow()>=3&&dest.getRow()<=5&&dest.getCol()>=4&&dest.getCol()<=5){
                return false;
            }
        }
        if(getChessPieceAt(src).getName().equals("Rat")){//有添加
            if(src.getRow()>=3&&src.getRow()<=5&&src.getCol()>=1&&src.getCol()<=2){
                getChessPieceAt(src).setInriver(1);
            }
            else if(src.getRow()>=3&&src.getRow()<=5&&src.getCol()>=4&&src.getCol()<=5){
                getChessPieceAt(src).setInriver(1);
            }
            else {
                getChessPieceAt(src).setInriver(0);
            }
        }
        if(dest.getRow()==0&&dest.getCol()==3&&calculateDistance(src,dest)==1){
            if(getChessPieceAt(src).getOwner()==PlayerColor.BLUE){
                return true;
            }
            else return false;
        }
        if(dest.getRow()==8&&dest.getCol()==3&&calculateDistance(src,dest)==1){
            if(getChessPieceAt(src).getOwner()==PlayerColor.RED){
                return true;
            }
            else return false;
        }
        if(getChessPieceAt(src).getName().equals("Lion") ||getChessPieceAt(src).getName().equals("Tiger")){
            if(src.getRow()==2) {
                int w = 0;
                if(dest.getRow()==src.getRow()+4) {
                    for (int i = 1; i <= 3; i++) {
                        if (grid[2+i][src.getCol()].getPiece() != null) {
                            if (grid[2+i][src.getCol()].getPiece().getName().equals("Rat")) {
                                w++;
                            }
                        }
                    }
                    if (w == 0) {
                        if (src.getCol() != 0 && src.getCol() != 3 && src.getCol() != 6) {
                            if (dest.getRow() == 6 && dest.getCol() == src.getCol()) {
                                if (getChessPieceAt(dest) == null) {
                                    return true;
                                }
                                if (getChessPieceAt(dest) != null){
                                    return isValidCapture(src,dest);
                                }
                            } else return calculateDistance(src, dest) == 1;
                        }
                        return calculateDistance(src, dest) == 1;
                    }
                    return false;
                }
            }
            if(src.getRow()==6) {
                int w = 0;
                if(src.getRow()==dest.getRow()+4) {
                    for (int i = 1; i <= 3; i++) {
                        if (grid[6-i][src.getCol()].getPiece() != null) {
                            if (grid[6-i][src.getCol()].getPiece().getName().equals("Rat")) {
                                w++;
                            }
                        }
                    }
                    if (w == 0) {
                        if (src.getCol() != 0 && src.getCol() != 3 && src.getCol() != 6) {
                            if (dest.getRow() == 2 && dest.getCol() == src.getCol()) {
                                if (getChessPieceAt(dest) == null) {
                                    return true;
                                }
                                if (getChessPieceAt(dest) != null){
                                    return isValidCapture(src,dest);
                                }
                            } else return calculateDistance(src, dest) == 1;
                        }
                        return calculateDistance(src, dest) == 1;
                    }
                    return false;
                }
            }
            if(src.getRow()==3||src.getRow()==4||src.getRow()==5) {
                int w = 0;
                if(src.getCol()==dest.getCol()+3||src.getCol()==dest.getCol()-3) {
                    for (int i = 1; i <= 2; i++) {
                        if(dest.getCol()-src.getCol()==3) {
                            if (grid[src.getRow()][src.getCol() + i].getPiece() != null) {
                                if (grid[src.getRow()][src.getCol()+i].getPiece().getName().equals("Rat")) {
                                    w++;
                                }
                            }
                        }
                        else if(dest.getCol()-src.getCol()==-3){
                            if (grid[src.getRow()][src.getCol() - i].getPiece() != null) {
                                if (grid[src.getRow()][src.getCol()-i].getPiece().getName().equals("Rat")) {
                                    w++;
                                }
                            }
                        }
                    }
                    if (w == 0) {
                        if (dest.getRow() == src.getRow() && (dest.getCol() == src.getCol() + 3 || dest.getCol() == src.getCol() - 3)) {
                            return true;
                        } else return calculateDistance(src, dest) == 1;
                    }
                    return false;
                }
            }
        }
        if (getChessPieceAt(src) == null) {
            return false;
        }
        if(getChessPieceAt(src)!=null && getChessPieceAt(dest)!=null){
            if(calculateDistance(src,dest)==1) {
                return isValidCapture(src, dest);
            }
            else return false;
        }
        return calculateDistance(src, dest) == 1;
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        // TODO:Fix this method
        if(getChessPieceAt(src) == null || getChessPieceAt(dest) == null || getChessPieceAt(src).getOwner().equals(getChessPieceAt(dest).getOwner())){
            return false;
        }
        if(getChessPieceAt(src).canCapture(getChessPieceAt(dest))){
            return true;
        }
        return false;
    }
    public ArrayList<ChessPiece> getBlueDead() {
        return blueDead;
    }
    public ArrayList<ChessPiece> getRedDead() {
        return redDead;
    }
    public void setBlueDead(ArrayList<ChessPiece> blueDead) {
        this.blueDead = blueDead;
    }
    public void setRedDead(ArrayList<ChessPiece> redDead) {
        this.redDead = redDead;
    }
}
//添加了reddead和bluedead的setter和getter 以及 初始化new arraylist
//修改了isvalidcapture