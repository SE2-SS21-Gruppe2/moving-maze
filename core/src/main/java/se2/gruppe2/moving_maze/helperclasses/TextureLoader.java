package se2.gruppe2.moving_maze.helperclasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import se2.gruppe2.moving_maze.item.ItemRepresentation;
import se2.gruppe2.moving_maze.tile.TileRepresentation;

import java.util.HashMap;

public class TextureLoader {
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
            scaledPicture = new Pixmap((int) ItemRepresentation.itemEdgeSize,(int) ItemRepresentation.itemEdgeSize,originalPicture.getFormat());
        }
        else {
            scaledPicture = new Pixmap((int) ItemRepresentation.itemEdgeSize/2,(int) ItemRepresentation.itemEdgeSize/2,originalPicture.getFormat());
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

        Pixmap scaledPicture = new Pixmap((int) TileRepresentation.tileEdgeSizeNoPadding, (int) TileRepresentation.tileEdgeSizeNoPadding, originalPicture.getFormat());

        scaledPicture.drawPixmap(originalPicture,
                0, 0, originalPicture.getWidth(), originalPicture.getHeight(),
                0, 0, scaledPicture.getWidth(), scaledPicture.getHeight());

        Texture scaledTileTexture = new Texture(scaledPicture);

        originalPicture.dispose();
        scaledPicture.dispose();

        return scaledTileTexture;
    }
}
