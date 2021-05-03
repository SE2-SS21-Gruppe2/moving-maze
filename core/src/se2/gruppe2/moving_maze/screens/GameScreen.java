package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import se2.gruppe2.moving_maze.MovingMazeGame;
import se2.gruppe2.moving_maze.gameBoard.GameBoard;
import se2.gruppe2.moving_maze.gameBoard.GameBoardFactory;
import se2.gruppe2.moving_maze.tile.Tile;

public class GameScreen implements Screen {

    final MovingMazeGame game;
    OrthographicCamera camera;
    final GameBoard gameBoard;

    public GameScreen(final MovingMazeGame game) {
        this.game = game;
        this.gameBoard = GameBoardFactory.getLOnlyBoard();
        this.gameBoard.setStartCoordinates(200f, 200f);
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
        renderGameBoard(this.gameBoard);
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

    private void renderGameBoard(GameBoard gb) {
        float current_x = gb.getX();
        float current_y = gb.getY();

        Tile[][] board = gb.getBoard();

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                game.batch.draw(board[i][j].getTexture(), current_x, current_y);
                current_x += Tile.tileEdgeSize;
            }
            current_y += Tile.tileEdgeSize;
            current_x = gb.getX();
        }
    }
}
