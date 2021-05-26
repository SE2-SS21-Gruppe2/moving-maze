package se_ii.gruppe2.moving_maze.helperclasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import se_ii.gruppe2.moving_maze.gameboard.GameBoard;

import java.util.HashMap;

public class TextureLoader {
    public static final float TILE_PADDING = 5.0f;
    public static final float TILE_EDGE_SIZE = (float) Gdx.graphics.getHeight() / GameBoard.TILES_PER_EDGE - TILE_PADDING *2.0f;
    public static final float TILE_EDGE_SIZE_NO_PADDING = TILE_EDGE_SIZE - 2.0f* TILE_PADDING;
    public static final float TIME_EDGE_SIZE = (Gdx.graphics.getHeight()*1f / GameBoard.TILES_PER_EDGE - 20f);

    private static HashMap<String, Sprite> textures = new HashMap<>();

    // prevent instantiation - static utility class
    private TextureLoader() {}

    /**
     * Stores, scales & loads textures
     * @param path to load the texture from
     * @param type of the texture; used for determining scaling-factor
     * @return the generated sprite
     */
    public static Sprite getSpriteByTexturePath(String path, TextureType type) {
        if(textures.get(path) != null) {
            return textures.get(path);
        }

        Sprite sprite;
        switch (type) {
            case TILE:
                sprite = new Sprite(getScaledTileTexture(path));
                break;

            case ITEM:
                sprite = new Sprite(getScaledItemTexture(path, false));
                break;

            case PLAYER:
                sprite = new Sprite(getScaledPlayerTexture(path));
                break;

            default:
                sprite = null;
        }

        textures.put(path, sprite);
        return sprite;
    }

    /**
     * Scales an item-texture to the desired size on the card
     * @param texturePath path to the texture
     * @param onCard if currently displayed on curd
     * @return the scaled texture
     */
    private static Texture getScaledItemTexture(String texturePath, boolean onCard) {
        var originalPicture = new Pixmap(Gdx.files.internal(texturePath));
        Pixmap scaledPicture;

        if(onCard){
            scaledPicture = new Pixmap((int) TextureLoader.TIME_EDGE_SIZE,(int) TextureLoader.TIME_EDGE_SIZE,originalPicture.getFormat());
        }
        else {
            scaledPicture = new Pixmap((int) TextureLoader.TIME_EDGE_SIZE /2,(int) TextureLoader.TIME_EDGE_SIZE /2,originalPicture.getFormat());
        }

        scaledPicture.drawPixmap(originalPicture,
                0, 0, originalPicture.getWidth(), originalPicture.getHeight(),
                0, 0, scaledPicture.getWidth(), scaledPicture.getHeight());

        var scaledTileTexture = new Texture(scaledPicture);

        originalPicture.dispose();
        scaledPicture.dispose();

        return scaledTileTexture;
    }

    /**
     * Takes the path to an image and returns it as a scaled texture with respect to calculated tile sizes.
     * @param texturePath asset-path to the image
     * @return the constructed, scaled texture
     */
    private static Texture getScaledTileTexture(String texturePath) {
        var originalPicture = new Pixmap(Gdx.files.internal(texturePath));

        var scaledPicture = new Pixmap((int) TILE_EDGE_SIZE_NO_PADDING, (int) TILE_EDGE_SIZE_NO_PADDING, originalPicture.getFormat());

        scaledPicture.drawPixmap(originalPicture,
                0, 0, originalPicture.getWidth(), originalPicture.getHeight(),
                0, 0, scaledPicture.getWidth(), scaledPicture.getHeight());

        var scaledTileTexture = new Texture(scaledPicture);

        originalPicture.dispose();
        scaledPicture.dispose();

        return scaledTileTexture;
    }

    /**
     * Scales a player-texture to the desired size relative to a tile by PlayerColor-enum attribute
     * @param texturePath path to the texture
     * @return the scaled texture
     */
    private static Texture getScaledPlayerTexture(String texturePath) {
        var percentageOfTile = 0.7; // height should fill 70% of tile height

        var originalPicture = new Pixmap(Gdx.files.internal(texturePath));
        Pixmap scaledPicture;

        var scalingFactor = originalPicture.getHeight()/ TILE_EDGE_SIZE;
        var scaledHeight = (int) (originalPicture.getHeight() / scalingFactor * percentageOfTile);
        var scaledWidth = (int) (originalPicture.getWidth() / scalingFactor * percentageOfTile);

        scaledPicture = new Pixmap(scaledWidth, scaledHeight, originalPicture.getFormat());

        scaledPicture.drawPixmap(originalPicture,
                0, 0, originalPicture.getWidth(), originalPicture.getHeight(),
                0, 0, scaledPicture.getWidth(), scaledPicture.getHeight());

        var scaledTileTexture = new Texture(scaledPicture);

        originalPicture.dispose();
        scaledPicture.dispose();

        return scaledTileTexture;
    }

    public static Texture getTileTextureOverlay(){
        var originalPicture = new Pixmap(Gdx.files.internal("gameboard/yellow_tile.png"));

        var scaledPicture = new Pixmap((int) TILE_EDGE_SIZE_NO_PADDING, (int) TILE_EDGE_SIZE_NO_PADDING, originalPicture.getFormat());

        scaledPicture.drawPixmap(originalPicture,
                0, 0, originalPicture.getWidth(), originalPicture.getHeight(),
                0, 0, scaledPicture.getWidth(), scaledPicture.getHeight());

        var scaledTileTexture = new Texture(scaledPicture);

        originalPicture.dispose();
        scaledPicture.dispose();

        return scaledTileTexture;
    }

    public static Texture getLayeredTexture(String tileTexturePath, String itemTexturePath){

        var tilePictureOriginal = new Pixmap(Gdx.files.internal(tileTexturePath));

        var tilePictureScaled = new Pixmap((int) TILE_EDGE_SIZE_NO_PADDING, (int) TILE_EDGE_SIZE_NO_PADDING, tilePictureOriginal.getFormat());

        tilePictureScaled.drawPixmap(tilePictureOriginal,
                0, 0, tilePictureOriginal.getWidth(), tilePictureOriginal.getHeight(),
                0, 0, tilePictureScaled.getWidth(), tilePictureScaled.getHeight());

        if (itemTexturePath != null){
            var itemPictureOriginal = new Pixmap(Gdx.files.internal(itemTexturePath));

            var itemPictureScaled = new Pixmap((int) TILE_EDGE_SIZE/2,(int) TILE_EDGE_SIZE/2, itemPictureOriginal.getFormat());

            itemPictureScaled.drawPixmap(itemPictureOriginal,
                    0, 0, itemPictureOriginal.getWidth(), itemPictureOriginal.getHeight(),
                    0, 0, itemPictureScaled.getWidth(), itemPictureScaled.getHeight());

            tilePictureScaled.drawPixmap(itemPictureScaled,
                    0,0, tilePictureScaled.getWidth(), tilePictureScaled.getHeight(),
                    tilePictureScaled.getWidth()/2 - itemPictureScaled.getWidth()/2,tilePictureScaled.getHeight()/2 - itemPictureScaled.getHeight()/2, itemPictureScaled.getWidth()*2, itemPictureScaled.getHeight()*2);

            itemPictureOriginal.dispose();
            itemPictureScaled.dispose();
        }

        var scaledTileTexture = new Texture(tilePictureScaled);

        tilePictureOriginal.dispose();
        tilePictureScaled.dispose();

        return scaledTileTexture;

    }
}