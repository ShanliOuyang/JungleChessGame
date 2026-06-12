package model;


import java.io.Serializable;

public class ChessPiece implements Serializable {
    // the owner of the chess
    private PlayerColor owner;

    // Elephant? Cat? Dog? ...
    private String name;
    public int getRank() {
        return rank;
    }
    private int rank;
    private int trap = 0;
    private int inriver=0;

    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }

    public boolean canCapture(ChessPiece target) {
        // TODO: Finish this method!
        if(inriver==0) {
            if (target.trap == 0) {
                if (target.rank == 1) {
                    if (rank == 8) {
                        return false;
                    } else return true;
                }
                if (target.rank == 8) {
                    if (rank == 1 || rank == 8) {
                        return true;
                    } else return false;
                } else if (rank >= target.rank) {
                    return true;
                } else return false;
            } else {
                return true;
            }
        }
        else return false;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setTrap(int trap) {
        this.trap = trap;
    }

    public int getInriver() {
        return inriver;
    }

    public void setInriver(int inriver) {
        this.inriver = inriver;
    }
}
