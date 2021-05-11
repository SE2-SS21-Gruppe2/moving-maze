package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
        this.gameBoard = GameBoardFactory.getStandardGameBoard();
        setStartCoordinates();
        camera = MovingMazeGame.gameboardCamera();
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
        renderGameBoard(this.gameBoard, game.batch);
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


    // TODO: minor Posigining changes.
    private void renderGameBoard(GameBoard gb, SpriteBatch batch) {
        float current_x = gb.getX();
        float current_y = gb.getY();

        Tile[][] board = gb.getBoard();

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j].getSprite().setPosition(current_x, current_y);
                board[i][j].getSprite().draw(batch);

                if(board[i][j].hasItem()){
                    float current_Ix=current_x+Tile.tileEdgeSize/4;
                    float current_Iy=current_y+Tile.tileEdgeSize/4;
                    board[i][j].getItem().getSprite().setPosition(current_Ix,current_Iy);
                    board[i][j].getItem().getSprite().draw(batch);
                }


                current_x += Tile.tileEdgeSize;
            }
            current_y += Tile.tileEdgeSize;
            current_x = gb.getX();
        }
    }






    private void setStartCoordinates(){
        float aspectRatio=(float) Gdx.graphics.getWidth()/(float) Gdx.graphics.getHeight();
        if(aspectRatio<= 19f/9f && aspectRatio>= 16f/9f){
            this.gameBoard.setStartCoordinates(Gdx.graphics.getWidth()/100 *45, Gdx.graphics.getHeight()/100);
        }
        else if(aspectRatio==4f/3f){
            this.gameBoard.setStartCoordinates(Gdx.graphics.getWidth()/100 * 35, Gdx.graphics.getHeight()/100*10);
        }
        else if(aspectRatio==1f){
            this.gameBoard.setStartCoordinates(Gdx.graphics.getWidth()/100, Gdx.graphics.getHeight()/100);
        }

    }

}
