package se_ii.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import se_ii.gruppe2.moving_maze.MovingMazeGame;

public class JoinSessionScreen implements Screen {

    private final MovingMazeGame game;
    private final SpriteBatch batch;
    private OrthographicCamera camera;

    public JoinSessionScreen(final MovingMazeGame game) {
        this.game = game;
        this.batch = game.getBatch();
        camera = MovingMazeGame.getStandardizedCamera();
    }

    @Override
    public void show() {
        // lifecycle function
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        game.getFont().draw(batch, "Join session", 100, 100);
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
}
