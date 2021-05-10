package se2.gruppe2.moving_maze.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import se2.gruppe2.moving_maze.gameBoard.GameBoard;

public class Item {

    private final float itemEdgeSize= (float)(Gdx.graphics.getHeight() / GameBoard.tilesPerEdge - 20f);

    private String name;
    private Position position;
    private Texture texture;
    private Sprite sprite;

    public Item(String texturePath, Position position, boolean onCard){
        this.texture=scaleTextureOnLoad(texturePath, onCard);
        this.position=position;
        this.sprite= new Sprite(this.texture);
    }

    public Sprite getSprite() {
        return sprite;
    }

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
