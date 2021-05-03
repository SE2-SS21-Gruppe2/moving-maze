package se2.gruppe2.moving_maze.tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.w3c.dom.Text;
import se2.gruppe2.moving_maze.gameBoard.GameBoard;
import se2.gruppe2.moving_maze.item.Item;

public class Tile {

    public static final float tilePadding = 5.0f;
    public static final float tileEdgeSize = (float) Gdx.graphics.getHeight()*0.8f / GameBoard.tilesPerEdge + tilePadding*2.0f;
    public Texture[] tileTextures = new Texture[3];

    private boolean openTop;
    private boolean openRight;
    private boolean openBottom;
    private boolean openLeft;
    private Item item;
    private TileType tileType;
    private int rotationDegrees;
    private Texture texture;

    public Tile(){}

    public Tile(boolean openTop, boolean openRight, boolean openBottom, boolean openLeft, TileType tileType) {
        this.openTop = openTop;
        this.openRight = openRight;
        this.openBottom = openBottom;
        this.openLeft = openLeft;

        this.tileType = tileType;

    }

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
