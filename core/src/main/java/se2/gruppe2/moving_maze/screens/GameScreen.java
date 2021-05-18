package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import se2.gruppe2.moving_maze.MovingMazeGame;
import se2.gruppe2.moving_maze.gameBoard.GameBoardFactory;
import se2.gruppe2.moving_maze.helperclasses.TextureLoader;
import se2.gruppe2.moving_maze.helperclasses.TextureType;
import se2.gruppe2.moving_maze.item.ItemLogical;
import se2.gruppe2.moving_maze.item.Position;
import se2.gruppe2.moving_maze.tile.Tile;

public class GameScreen implements Screen {

    final MovingMazeGame game;
    OrthographicCamera camera;

    // background
    Texture bgImageTexture;
    TextureRegion bgTextureRegion;

    public GameScreen(final MovingMazeGame game) {
        this.game = game;

        camera = MovingMazeGame.gameboardCamera();

        // instantiate textures for background
        bgImageTexture = new Texture(Gdx.files.internal("ui/bg_moss.jpeg"));
        bgTextureRegion = new TextureRegion(bgImageTexture);
    }

    @Override
    public void show() {
        game.client.sendGameStateUpdate(game.getGameState());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        game.batch.setProjectionMatrix(camera.combined);

        if(Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            Gdx.app.log("recreateBoard", "Recreating gameboard");
            recreateGameBoard();
        }

        game.batch.begin();
            game.batch.draw(bgTextureRegion, 0, 0);
            drawGameBoard(game.batch);
            game.font.draw(game.batch, "Game screen (DEV MODE)", 100, 100);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    /**
     * Calculates the start-coordinates for a gameboard with respect to the aspect-ratio.
     */
    private Position getStartCoordinates(){
        float aspectRatio=(float) Gdx.graphics.getWidth()/(float) Gdx.graphics.getHeight();
        if(aspectRatio<= 19f/9f && aspectRatio>= 16f/9f){
            return new Position(Gdx.graphics.getWidth()/100 *45, Gdx.graphics.getHeight()/100);
        }
        else if(aspectRatio==4f/3f){
            return new Position(Gdx.graphics.getWidth()/100 * 35, Gdx.graphics.getHeight()/100*10);
        }
        else if(aspectRatio==1f){
            return new Position(Gdx.graphics.getWidth()/100, Gdx.graphics.getHeight()/100);
        } else {
            return new Position(0,0);
        }

    }

    public void recreateGameBoard() {
        game.getGameState().setBoard(GameBoardFactory.getStandardGameBoard());
        game.client.sendGameStateUpdate(game.getGameState());
    }

    /**
     * Draws a visual representation of a logical gameboard onto the screen.
     * @param batch
     */
    private void drawGameBoard(SpriteBatch batch) {
        Tile[][] tl = game.getGameState().getBoard().getBoard();
        Position init_pos = getStartCoordinates();

        float cur_x = init_pos.getX();
        float cur_y = init_pos.getY();

        Sprite currentSprite;
        Tile currentTile;
        ItemLogical currentItem;
        for(int i = 0; i < tl.length; i++) {
            for(int j = 0; j < tl[i].length; j++) {
                currentTile = tl[i][j];
                currentSprite = TextureLoader.getSpriteByTexturePath(currentTile.getTexturePath(), TextureType.TILE);
                currentItem = currentTile.getItem();

                currentSprite.setPosition(cur_x, cur_y);
                currentSprite.setRotation(currentTile.getRotationDegrees());
                currentSprite.draw(batch);

                if(currentItem != null) {
                    currentSprite = TextureLoader.getSpriteByTexturePath(currentItem.getTexturePath(), TextureType.ITEM);
                    currentSprite.setPosition(cur_x+TextureLoader.tileEdgeSize/4f, cur_y + TextureLoader.tileEdgeSize/4f);
                    currentSprite.draw(batch);
                }

                cur_x += TextureLoader.tileEdgeSize;
            }
            cur_x = init_pos.getX();
            cur_y += TextureLoader.tileEdgeSize;
        }
    }

}