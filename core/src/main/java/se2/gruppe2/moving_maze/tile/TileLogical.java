package se2.gruppe2.moving_maze.tile;

import se2.gruppe2.moving_maze.item.ItemLogical;

public abstract class TileLogical {

    private boolean openTop;
    private boolean openRight;
    private boolean openBottom;
    private boolean openLeft;
    private ItemLogical item;
    private float rotationDegrees;
    private String texturePath;

    public TileLogical(){}

    public TileLogical(boolean openTop, boolean openRight, boolean openBottom, boolean openLeft, String texturePath) {
        this.openTop = openTop;
        this.openRight = openRight;
        this.openBottom = openBottom;
        this.openLeft = openLeft;
        this.texturePath = texturePath;
    }

    public TileLogical(boolean openTop, boolean openRight, boolean openBottom, boolean openLeft, String texturePath, float rotationDegrees) {
        this(openTop, openRight, openBottom, openLeft, texturePath );
        this.rotationDegrees = rotationDegrees;
    }

    /**
     * Used to construct a tile in a "builder-like"-manner. Returns the Tile itself after applying the rotation.
     * @param rotationDegrees Rotation to apply to the tile
     * @return
     */
    public TileLogical applyRotation(float rotationDegrees) {
        this.setRotationDegrees(rotationDegrees);
        return this;
    }

    // GETTER & SETTER
    public void rotateClockwise(){}

    public void rotateCounterClockwise(){}

    public void getBinaryString(){}

    public void setBinaryString(String binaryString){}

    public void setItem(ItemLogical item) {
        this.item = item;
    }

    public ItemLogical getItem() {
        return item;
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

    public float getRotationDegrees() {
        return this.rotationDegrees;
    }

    public boolean hasItem(){
        return this.item != null;
    }

    public void setRotationDegrees(float rotationDegrees) {
        this.rotationDegrees = rotationDegrees;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }
}
