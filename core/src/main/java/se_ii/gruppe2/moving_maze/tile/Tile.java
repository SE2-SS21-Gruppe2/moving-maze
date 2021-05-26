package se_ii.gruppe2.moving_maze.tile;

import se_ii.gruppe2.moving_maze.item.ItemLogical;

import java.util.Objects;

public abstract class Tile {

    private boolean openTop;
    private boolean openRight;
    private boolean openBottom;
    private boolean openLeft;
    private ItemLogical item;
    private float rotationDegrees;
    private String texturePath;

    public Tile(){}

    public Tile(boolean openTop, boolean openRight, boolean openBottom, boolean openLeft, String texturePath) {
        this.openTop = openTop;
        this.openRight = openRight;
        this.openBottom = openBottom;
        this.openLeft = openLeft;
        this.texturePath = texturePath;
    }

    public Tile(boolean openTop, boolean openRight, boolean openBottom, boolean openLeft, String texturePath, float rotationDegrees) {
        this(openTop, openRight, openBottom, openLeft, texturePath );
        this.rotationDegrees = rotationDegrees;
    }

    /**
     * Used to construct a tile in a "builder-like"-manner. Returns the Tile itself after applying the rotation.
     * @param rotationDegrees Rotation to apply to the tile
     * @return
     */
    public Tile applyRotation(float rotationDegrees) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return openTop == tile.openTop && openRight == tile.openRight && openBottom == tile.openBottom && openLeft == tile.openLeft
                && Float.compare(tile.rotationDegrees, rotationDegrees) == 0
                && Objects.equals(item, tile.item) && texturePath.equals(tile.texturePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(openTop, openRight, openBottom, openLeft, item, rotationDegrees, texturePath);
    }
}
