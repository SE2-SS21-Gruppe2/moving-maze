package se2.gruppe2.moving_maze.helperclasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import se2.gruppe2.moving_maze.gameBoard.GameBoard;

import java.util.HashMap;

public class TextureLoader {
    public static final float tilePadding = 5.0f;
    public static float tileEdgeSize = (float) Gdx.graphics.getHeight() / GameBoard.tilesPerEdge - tilePadding*2.0f;
    public static final float tileEdgeSizeNoPadding = tileEdgeSize - 2.0f*tilePadding;
    public static final float itemEdgeSize= (float)(Gdx.graphics.getHeight()*1f / GameBoard.tilesPerEdge - 20f);


    private static HashMap<String, Sprite> textures = new HashMap<>();

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
        Pixmap originalPicture = new Pixmap(Gdx.files.internal(texturePath));
        Pixmap scaledPicture;

        if(onCard){
            scaledPicture = new Pixmap((int) TextureLoader.itemEdgeSize,(int) TextureLoader.itemEdgeSize,originalPicture.getFormat());
        }
        else {
            scaledPicture = new Pixmap((int) TextureLoader.itemEdgeSize/2,(int) TextureLoader.itemEdgeSize/2,originalPicture.getFormat());
        }

        scaledPicture.drawPixmap(originalPicture,
                0, 0, originalPicture.getWidth(), originalPicture.getHeight(),
                0, 0, scaledPicture.getWidth(), scaledPicture.getHeight());

        Texture scaledTileTexture = new Texture(scaledPicture);

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
