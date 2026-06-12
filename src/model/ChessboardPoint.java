package model;

import java.io.Serializable;

public class ChessboardPoint implements Serializable {
    private final int row;
    private final int col;
    public ChessboardPoint(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public int hashCode() {
        return row + col;
    }
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        ChessboardPoint temp = (ChessboardPoint) obj;
        return (temp.getRow() == this.row) && (temp.getCol() == this.col);
    }
    public String toString() {
        return "("+row + ","+col+") " + "on the chessboard is clicked!";
    }
}
