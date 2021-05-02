package se2.gruppe2.moving_maze.tile;

import se2.gruppe2.moving_maze.item.Item;

public class Tile {
    private boolean openTop;
    private boolean openRight;
    private boolean openBottom;
    private boolean openLeft;
    private Item item;
    private TileType tileType;
    private int rotationDegrees;

    public Tile(){}

    public void rotateClockwise(){}

    public void rotateCounterClockwise(){}

    public void getBinaryString(){}

    public void setBinaryString(String binaryString){}


    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public TileType getTileType() {
        return tileType;
    }

    public boolean isOpenTop() {
        return openTop;
    }

    public boolean isOpenRight() {
        return openRight;
    }

    public boolean isOpenBottom() {
        return openBottom;
    }

    public boolean isOpenLeft() {
        return openLeft;
    }
}
