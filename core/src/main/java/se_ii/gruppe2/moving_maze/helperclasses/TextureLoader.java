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

    public static final float CARD_Height = (float) Gdx.graphics.getHeight() / 4f;
    public static final float CARD_Width = (float) Gdx.graphics.getWidth() / 8f;


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

    private static Texture getScaledCardTexture(String texturePath) { /**  NEW METHOD */

        var originalPicture = new Pixmap(Gdx.files.internal(texturePath));

        var scaledPicture = new Pixmap((int) CARD_Width, (int) CARD_Height, originalPicture.getFormat());

        scaledPicture.drawPixmap(originalPicture,
                0, 0, originalPicture.getWidth(), originalPicture.getHeight(),
                0, 0, scaledPicture.getWidth(), scaledPicture.getHeight());

        var scaledCardTexture = new Texture(scaledPicture);

        originalPicture.dispose();
        scaledPicture.dispose();

        return scaledCardTexture;
    }
}
