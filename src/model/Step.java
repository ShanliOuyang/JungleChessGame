package model;

public class Step {
    public PlayerColor color;
    public ChessPiece captured;
    public ChessboardPoint src;
    public ChessboardPoint dest;
    public Step(ChessboardPoint src, ChessboardPoint dest, PlayerColor color) {
        this.src = src;
        this.dest = dest;
        this.color = color;
        captured = null;
    }

    public Step(ChessboardPoint src, ChessboardPoint dest, PlayerColor color, ChessPiece captured) {
        this.src = src;
        this.dest = dest;
        this.color = color;
        this.captured = captured;
    }
}