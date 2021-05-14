package se2.gruppe2.moving_maze.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se2.gruppe2.moving_maze.tile.TileRepresentation;
import se2.gruppe2.moving_maze.gameBoard.GameBoardLogical;

public class ItemRepresentation {
    public static final float itemEdgeSize= (float)(Gdx.graphics.getHeight() / GameBoardLogical.tilesPerEdge - 20f);
    private ItemLogical logicalItem;
    private Texture texture;
    private Sprite sprite;

    public ItemRepresentation(ItemLogical logicalItem) {
        this.logicalItem = logicalItem;
        this.texture=scaleTextureOnLoad(logicalItem.getTexturePath(), logicalItem.isOnCard());
        this.sprite= new Sprite(this.texture);
    }

    /**
     * Draws an item onto a tile by the tiles x and y coordinates
     * @param batch to draw the item on
     * @param tile_x coordinate of the tile
     * @param tile_y coordinate of the tile
     */
    public void draw(SpriteBatch batch, float tile_x, float tile_y) {
        float iX = tile_x+ TileRepresentation.tileEdgeSize/4;
        float iY = tile_y+ TileRepresentation.tileEdgeSize/4;
        sprite.setPosition(iX, iY);
        sprite.draw(batch);
    }

    /**
     * Scales an item-texture to the desired size on the card
     * @param texturePath path to the texture
     * @param onCard if currently displayed on curd
     * @return the scaled texture
     */
    private Texture scaleTextureOnLoad(String texturePath, boolean onCard) {
        Pixmap originalPicture = new Pixmap(Gdx.files.internal(texturePath));
        Pixmap scaledPicture;

        if(onCard){
            scaledPicture = new Pixmap((int) itemEdgeSize,(int) itemEdgeSize,originalPicture.getFormat());
        }
        else {
            scaledPicture = new Pixmap((int) itemEdgeSize/2,(int) itemEdgeSize/2,originalPicture.getFormat());
        }

        scaledPicture.drawPixmap(originalPicture,
                0, 0, originalPicture.getWidth(), originalPicture.getHeight(),
                0, 0, scaledPicture.getWidth(), scaledPicture.getHeight());

        Texture scaledTileTexture = new Texture(scaledPicture);

        originalPicture.dispose();
        scaledPicture.dispose();

        return scaledTileTexture;
    }
}
