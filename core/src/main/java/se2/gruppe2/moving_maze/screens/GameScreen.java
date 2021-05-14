package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import se2.gruppe2.moving_maze.MovingMazeGame;
import se2.gruppe2.moving_maze.gameBoard.GameBoardLogical;
import se2.gruppe2.moving_maze.gameBoard.GameBoardRepresentation;
import se2.gruppe2.moving_maze.gameState.GameStateHandler;

public class GameScreen implements Screen {

    final MovingMazeGame game;
    GameBoardRepresentation gameBoardRepresentation;
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
        gameBoardRepresentation = new GameBoardRepresentation(game.getGameState().getBoard());
        setStartCoordinates(gameBoardRepresentation);
        game.client.sendGameStateUpdate(game.getGameState());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
            game.batch.draw(bgTextureRegion, 0, 0);
            gameBoardRepresentation.draw(game.batch);
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
     * @param gameBoard to set the coordinates on
     */
    private void setStartCoordinates(GameBoardRepresentation gameBoard){
        float aspectRatio=(float) Gdx.graphics.getWidth()/(float) Gdx.graphics.getHeight();
        if(aspectRatio<= 19f/9f && aspectRatio>= 16f/9f){
            gameBoard.setStartCoordinates(Gdx.graphics.getWidth()/100f *45, Gdx.graphics.getHeight()/100f);
        }
        else if(aspectRatio==4f/3f){
            gameBoard.setStartCoordinates(Gdx.graphics.getWidth()/100f * 35, Gdx.graphics.getHeight()/100f*10);
        }
        else if(aspectRatio==1f){
            gameBoard.setStartCoordinates(Gdx.graphics.getWidth()/100f, Gdx.graphics.getHeight()/100f);
        }

    }

    /**
     * Updates representation based on the current gameState;
     * @param state to build the representation upon
     */
    public void updateRepresentationFromState(GameStateHandler state) {
        gameBoardRepresentation = new GameBoardRepresentation(state.getBoard());
    }

}
