package se_ii.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.ScreenUtils;
import se_ii.gruppe2.moving_maze.MovingMazeGame;
import se_ii.gruppe2.moving_maze.gameboard.GameBoardFactory;
import se_ii.gruppe2.moving_maze.gamestate.turnAction.InsertTile;
import se_ii.gruppe2.moving_maze.helperclasses.TextureLoader;
import se_ii.gruppe2.moving_maze.helperclasses.TextureType;
import se_ii.gruppe2.moving_maze.item.ItemLogical;
import se_ii.gruppe2.moving_maze.item.Position;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.tile.Tile;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private final MovingMazeGame game;
    private final SpriteBatch batch;
    private boolean newExtraTile;
    private OrthographicCamera camera;
    private Player player;
    private Stage stage;

    // Buffer-variables used for rendering
    Sprite currentSprite;
    Sprite currentExtraTileSprite;
    Tile currentTile;
    Tile currentExtraTile;
    ItemLogical currentItem;
    ItemLogical currentExtraTileItem;
    ArrayList<Player> currentPlayersOnTile = new ArrayList<>();

    Image img;


    // background
    private Texture bgImageTexture;
    private TextureRegion bgTextureRegion;

    public GameScreen(final MovingMazeGame game) {
        this.game = game;
        this.batch = game.getBatch();

        camera = MovingMazeGame.getStandardizedCamera();

        // instantiate textures for background
        bgImageTexture = new Texture(Gdx.files.internal("ui/bg_moss.jpeg"));
        bgTextureRegion = new TextureRegion(bgImageTexture);
    }

    @Override
    public void show() {
        game.getClient().sendGameStateUpdate(game.getGameState());
        player = game.getLocalPlayer();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        updateExtraTile();
        newExtraTile = false;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        batch.setProjectionMatrix(camera.combined);

        if(Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            Gdx.app.log("recreateBoard", "Recreating gameboard");
            recreateGameBoard();
        }

        batch.begin();
        batch.draw(bgTextureRegion, 0, 0);
        drawGameBoard(batch);
        stage.draw();
        game.getFont().draw(batch, "Game screen (DEV MODE)", 100, 100);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // lifecycle function
    }

    @Override
    public void pause() {
        // lifecycle function
    }

    @Override
    public void resume() {
        // lifecycle function
    }

    @Override
    public void hide() {
        // lifecycle function
    }

    @Override
    public void dispose() {
        // lifecycle function
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
        game.getClient().sendGameStateUpdate(game.getGameState());
    }

    /**
     * Draws a visual representation of a logical gameboard onto the screen.
     * @param batch
     * TODO: refactor
     */
    private void drawGameBoard(SpriteBatch batch) {
        Tile[][] tl = game.getGameState().getBoard().getBoard();
        Position initPos = getStartCoordinates();

        float curX = initPos.getX();
        float curY = initPos.getY();

        for(var y = 0; y < tl.length; y++) {
            for(var x = 0; x < tl[y].length; x++) {
                currentTile = tl[y][x];
                currentSprite = TextureLoader.getSpriteByTexturePath(currentTile.getTexturePath(), TextureType.TILE);
                currentItem = currentTile.getItem();

                currentSprite.setPosition(curX, curY);
                currentSprite.setRotation(currentTile.getRotationDegrees());
                currentSprite.draw(batch);

                // render item
                if(currentItem != null) {
                    currentSprite = TextureLoader.getSpriteByTexturePath(currentItem.getTexturePath(), TextureType.ITEM);
                    currentSprite.setPosition(curX+TextureLoader.TILE_EDGE_SIZE /4f, curY + TextureLoader.TILE_EDGE_SIZE /4f);
                    currentSprite.draw(batch);
                }

                // render players
                currentPlayersOnTile = game.getGameState().playersOnTile(y, x);

                if(currentPlayersOnTile.size() != 0) {
                    for(Player p : currentPlayersOnTile) {
                        currentSprite = TextureLoader.getSpriteByTexturePath(p.getTexturePath(), TextureType.PLAYER);
                        currentSprite.setPosition(curX+TextureLoader.TILE_EDGE_SIZE/4f, curY+TextureLoader.TILE_EDGE_SIZE/4f);
                        currentSprite.draw(batch);
                    }
                }

                curX += TextureLoader.TILE_EDGE_SIZE;
            }
            curX = initPos.getX();
            curY += TextureLoader.TILE_EDGE_SIZE;

            if (isNewExtraTile()){
                updateExtraTile();
            }
        }
    }

    public void updateExtraTile(){
        stage.clear();
        currentExtraTile = game.getGameState().getBoard().getExtraTile();
        if (TextureLoader.getSpriteByTexturePath(currentExtraTile.getTexturePath(), TextureType.TILE) != null){
            currentExtraTileSprite = TextureLoader.getSpriteByTexturePath(currentExtraTile.getTexturePath(), TextureType.TILE);

            img = new Image(currentExtraTileSprite);
            img.setOrigin(img.getWidth()/2f, img.getHeight()/2f);
            img.setPosition(300,500);
            img.setRotation(currentExtraTile.getRotationDegrees());

            img.addListener(new DragListener() {

                @Override
                public void drag(InputEvent event, float x, float y, int pointer) {
                    Vector2 dir = new Vector2();
                    Vector2 offset = new Vector2();
                    if (img.getRotation() == 0){
                        dir.x = x;
                        dir.y = y;
                        offset.x = 0 - img.getWidth()/2f;
                        offset.y = 0 - img.getHeight()/2f;
                    } else if (img.getRotation() == 90f){
                        dir.x = -y;
                        dir.y = x;
                        offset.x = img.getHeight()/2f;
                        offset.y = 0 - img.getWidth()/2f;
                    } else if (img.getRotation() == 180f){
                        dir.x = -x;
                        dir.y = -y;
                        offset.x = img.getWidth()/2f;
                        offset.y = img.getHeight()/2f;
                    } else if (img.getRotation() == 270f){
                        dir.x = y;
                        dir.y = -x;
                        offset.x = 0 - img.getHeight()/2f;
                        offset.y = img.getWidth()/2f;
                    }
                    img.moveBy(dir.x + offset.x, dir.y + offset.y);
                }

                @Override
                public void dragStop(InputEvent event, float x, float y, int pointer) {
                    boolean insertSuccess = false;
                    Position initPos = getStartCoordinates();
                    Vector2 inputPosition = new Vector2(0,0);
                    inputPosition.x = (int) Math.floor((Gdx.input.getX() - initPos.getX())/TextureLoader.TILE_EDGE_SIZE);
                    inputPosition.y = (int) Math.floor((Gdx.graphics.getHeight() - Gdx.input.getY() - initPos.getY())/TextureLoader.TILE_EDGE_SIZE);

                    System.out.println("X: "+ inputPosition.x + ", Y: " + inputPosition.y);

                    InsertTile insert = new InsertTile(inputPosition);

                    System.out.println(inputPosition.x);

                    insertSuccess = insert.validate();

                    if (insertSuccess){
                        insert.execute();
                    } else {
                        img.setPosition(300,500);
                    }
                }
            });

            img.addListener(new ClickListener(){

                @Override
                public void clicked(InputEvent event, float x, float y){
                    img.rotateBy(90);
                    currentExtraTile.rotateClockwise();
                }
            });


            stage.addActor(img);
            setNewExtraTile(false);
        }

    }

    public boolean isNewExtraTile() {
        return newExtraTile;
    }

    public void setNewExtraTile(boolean newExtraTile) {
        this.newExtraTile = newExtraTile;
    }
}