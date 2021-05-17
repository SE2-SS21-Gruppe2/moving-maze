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
import se2.gruppe2.moving_maze.gameBoard.GameBoardLogical;
import se2.gruppe2.moving_maze.gameBoard.GameBoardRepresentation;
import se2.gruppe2.moving_maze.gameState.GameStateHandler;
import se2.gruppe2.moving_maze.helperclasses.TextureLoader;
import se2.gruppe2.moving_maze.helperclasses.TextureType;
import se2.gruppe2.moving_maze.item.Position;
import se2.gruppe2.moving_maze.tile.TileLogical;
import se2.gruppe2.moving_maze.tile.TileRepresentation;

public class GameScreen implements Screen {

    final MovingMazeGame game;
    GameBoardRepresentation gameBoardRepresentation;
    OrthographicCamera camera;

    // background
    Texture bgImageTexture;
    TextureRegion bgTextureRegion;

    public boolean updatedFromServer;

    public GameScreen(final MovingMazeGame game) {
        this.game = game;
        updatedFromServer = false;

        camera = MovingMazeGame.gameboardCamera();

        // instantiate textures for background
        bgImageTexture = new Texture(Gdx.files.internal("ui/bg_moss.jpeg"));
        bgTextureRegion = new TextureRegion(bgImageTexture);
    }

    @Override
    public void show() {
        updateRepresentationFromState(game.getGameState());
        // setStartCoordinates(gameBoardRepresentation);
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

    /**
     * Updates representation based on the current gameState;
     * @param state to build the representation upon
     */
    public void updateRepresentationFromState(GameStateHandler state) {
        gameBoardRepresentation = new GameBoardRepresentation(state.getBoard());
        // setStartCoordinates(gameBoardRepresentation);
    }

    public void recreateGameBoard() {
        game.getGameState().setBoard(GameBoardFactory.getStandardGameBoard());
        updateRepresentationFromState(game.getGameState());
    }

    /**
     * Draws
     * @param batch
     */
    private void drawGameBoard(SpriteBatch batch) {
        TileLogical[][] tl = game.getGameState().getBoard().getBoard();
        Position init_pos = getStartCoordinates();

        float cur_x = init_pos.getX();
        float cur_y = init_pos.getY();

        Sprite current;
        TileLogical currentTile;
        for(int i = 0; i < tl.length; i++) {
            for(int j = 0; j < tl[i].length; j++) {
                currentTile = tl[i][j];

                current = TextureLoader.getSpriteByTexturePath(currentTile.getTexturePath(), TextureType.TILE);
                current.setPosition(cur_x, cur_y);
                current.setRotation(currentTile.getRotationDegrees());
                current.draw(batch);

                cur_x += TileRepresentation.tileEdgeSize;
            }
            cur_x = init_pos.getX();
            cur_y += TileRepresentation.tileEdgeSize;
        }
    }

}
