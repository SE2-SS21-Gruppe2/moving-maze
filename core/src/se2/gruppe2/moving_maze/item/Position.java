package se2.gruppe2.moving_maze.item;

public class Position {

    private int x;
    private int y;
    private int MAX_X;
    private int MAX_Y;



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
