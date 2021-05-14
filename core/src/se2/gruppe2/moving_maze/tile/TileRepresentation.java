package se2.gruppe2.moving_maze.tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se2.gruppe2.moving_maze.item.ItemRepresentation;

/**
 * Graphical representation of a tile on the gameboard.
 */
public class TileRepresentation {
    private TileLogical logicalTile;

    private Texture texture;
    private Sprite sprite;
    private ItemRepresentation itemRepresentation;

    public TileRepresentation(TileLogical logicalTile) {
        this.logicalTile = logicalTile;
        this.texture = scaleTextureOnLoad(logicalTile.getTexturePath());
        this.sprite = new Sprite(this.texture);
        this.itemRepresentation = logicalTile.getItem() == null ? null : new ItemRepresentation(logicalTile.getItem());
    }

    /**
     * Draws the tile and its item onto the specified batch on the given position. Rotation is taken
     * into consideration by looking it up on the logicalTile.
     * @param batch to draw the sprite on
     * @param x -coordinate of position
     * @param y -coordinate of position
     */
    public void draw(SpriteBatch batch, float x, float y) {
        sprite.setRotation(logicalTile.getRotationDegrees());
        sprite.setPosition(x, y);
        sprite.draw(batch);

        if(itemRepresentation != null) {
            itemRepresentation.draw(batch, x, y);
        }
    }

    /**
     * Takes the path to an image and returns it as a scaled texture with respect to calculated tile sizes.
     * @param texturePath asset-path to the image
     * @return the constructed, scaled texture
     */
    private Texture scaleTextureOnLoad(String texturePath) {
        Pixmap originalPicture = new Pixmap(Gdx.files.internal(texturePath));

        Pixmap scaledPicture = new Pixmap((int) TileLogical.tileEdgeSizeNoPadding, (int) TileLogical.tileEdgeSizeNoPadding, originalPicture.getFormat());

        scaledPicture.drawPixmap(originalPicture,
                0, 0, originalPicture.getWidth(), originalPicture.getHeight(),
                0, 0, scaledPicture.getWidth(), scaledPicture.getHeight());

        Texture scaledTileTexture = new Texture(scaledPicture);

        originalPicture.dispose();
        scaledPicture.dispose();

        return scaledTileTexture;
    }

    // GETTER & SETTER
    public Texture getTexture() {
        return this.texture;
    }

    public Sprite getSprite() { return this.sprite; }

    public ItemRepresentation getItemRepresentation() {
        return itemRepresentation;
    }

    public void setItemRepresentation(ItemRepresentation itemRepresentation) {
        this.itemRepresentation = itemRepresentation;
    }

    public boolean hasItem() {
        return this.itemRepresentation != null;
    }
}
