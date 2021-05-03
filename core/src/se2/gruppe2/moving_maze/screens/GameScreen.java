package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import se2.gruppe2.moving_maze.MovingMazeGame;
import se2.gruppe2.moving_maze.gameBoard.GameBoard;
import se2.gruppe2.moving_maze.gameBoard.GameBoardFactory;

public class GameScreen implements Screen {

    final MovingMazeGame game;
    OrthographicCamera camera;
    final GameBoard board;

    public GameScreen(final MovingMazeGame game) {
        this.game = game;
        this.board = GameBoardFactory.getLOnlyBoard();
        this.board.set
        camera = MovingMazeGame.getStandardizedCamera();
    }

    @Override
    public void show() {
        System.out.println("Gamescreen has been shown!");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        game.batch.draw(board.getBoard()[0][0].getTexture(), 200, 200);

        game.font.draw(game.batch, "Game screen", 100, 100);
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
}
