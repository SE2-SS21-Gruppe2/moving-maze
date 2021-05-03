package se2.gruppe2.moving_maze.tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import org.w3c.dom.Text;
import se2.gruppe2.moving_maze.gameBoard.GameBoard;
import se2.gruppe2.moving_maze.item.Item;

public abstract class Tile {

    public static final float tilePadding = 5.0f;
    public static final float tileEdgeSize = (float) Gdx.graphics.getHeight() / GameBoard.tilesPerEdge + tilePadding*2.0f;
    public static final float tileEdgeSizeNoPadding = tileEdgeSize - 2.0f*tilePadding;

    private boolean openTop;
    private boolean openRight;
    private boolean openBottom;
    private boolean openLeft;
    private Item item;
    private int rotationDegrees;
    private Texture texture;
    // TODO: Probably remove if done with subclasses only ..
    private TileType tileType;

    public Tile(){}

    public Tile(boolean openTop, boolean openRight, boolean openBottom, boolean openLeft, String texturePath) {
        this.openTop = openTop;
        this.openRight = openRight;
        this.openBottom = openBottom;
        this.openLeft = openLeft;
        this.texture = scaleTextureOnLoad(texturePath);
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

    public Texture getTexture() {
        return this.texture;
    }

    private Texture scaleTextureOnLoad(String texturePath) {
        Pixmap originalPicture = new Pixmap(Gdx.files.internal(texturePath));

        Pixmap scaledPicture = new Pixmap((int) tileEdgeSizeNoPadding, (int) tileEdgeSizeNoPadding, originalPicture.getFormat());

        scaledPicture.drawPixmap(originalPicture,
                0, 0, originalPicture.getWidth(), originalPicture.getHeight(),
                0, 0, scaledPicture.getWidth(), scaledPicture.getHeight());

        Texture scaledTileTexture = new Texture(scaledPicture);

        originalPicture.dispose();
        scaledPicture.dispose();

        return scaledTileTexture;
    }
}
