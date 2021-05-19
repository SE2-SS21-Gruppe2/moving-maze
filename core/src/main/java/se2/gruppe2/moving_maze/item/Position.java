package se2.gruppe2.moving_maze.item;

import se2.gruppe2.moving_maze.gameBoard.GameBoard;

public class Position {

    private int x;
    private int y;
    public static final int MAX_X = GameBoard.tilesPerEdge;
    public static final int MAX_Y = GameBoard.tilesPerEdge;

    public Position() {

    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(int x, int y){
        setY(y);
        setX(x);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
}
