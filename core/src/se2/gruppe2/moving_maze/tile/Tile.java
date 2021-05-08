package se2.gruppe2.moving_maze.tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.w3c.dom.Text;
import se2.gruppe2.moving_maze.MovingMazeGame;
import se2.gruppe2.moving_maze.gameBoard.GameBoard;
import se2.gruppe2.moving_maze.item.Item;

public abstract class Tile {

    public static final float tilePadding = 5.0f;
    public static final float tileEdgeSize = (float) Gdx.graphics.getHeight() / GameBoard.tilesPerEdge - tilePadding*2.0f;
    public static final float tileEdgeSizeNoPadding = tileEdgeSize - 2.0f*tilePadding;

    private boolean openTop;
    private boolean openRight;
    private boolean openBottom;
    private boolean openLeft;
    private Item item;
    private float rotationDegrees;
    private Texture texture;
    private Sprite sprite;
    // TODO: Probably remove if done with subclasses only ..
    private TileType tileType;

    public Tile(){}

    public Tile(boolean openTop, boolean openRight, boolean openBottom, boolean openLeft, String texturePath) {
        this.openTop = openTop;
        this.openRight = openRight;
        this.openBottom = openBottom;
        this.openLeft = openLeft;
        this.texture = scaleTextureOnLoad(texturePath);
        this.sprite = new Sprite(this.texture);
    }

    public Tile(boolean openTop, boolean openRight, boolean openBottom, boolean openLeft, String texturePath, float rotationDegrees) {
        this(openTop, openRight, openBottom, openLeft, texturePath );
        this.rotationDegrees = rotationDegrees;
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

    public Sprite getSprite() { return this.sprite; }

    public float getRotationDegrees() {
        return this.rotationDegrees;
    }

    public void setRotationDegrees(float rotationDegrees) {
        this.rotationDegrees = rotationDegrees;
    }

    /**
     * Used to construct a tile in a "builder-like"-manner. Returns the Tile itself after applying the rotation.
     * @param rotationDegrees Rotation to apply to the tile
     * @return
     */
    public Tile applyRotation(float rotationDegrees) {
        this.setRotationDegrees(rotationDegrees);
        this.sprite.setRotation(getRotationDegrees());
        return this;
    }

    /**
     * Takes the path to an image and returns it as a scaled texture with respect to calculated tile sizes.
     * @param texturePath asset-path to the image
     * @return the constructed, scaled texture
     */
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
