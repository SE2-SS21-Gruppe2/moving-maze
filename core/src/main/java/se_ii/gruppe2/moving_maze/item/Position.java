package se_ii.gruppe2.moving_maze.item;

import se_ii.gruppe2.moving_maze.gameboard.GameBoard;

public class Position {

    private int x;
    private int y;
    public static final int MAX_X = GameBoard.TILES_PER_EDGE;
    public static final int MAX_Y = GameBoard.TILES_PER_EDGE;

    public Position() {

    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(float x, float y) {
        this.x = (int) x;
        this.y = (int) y;
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
