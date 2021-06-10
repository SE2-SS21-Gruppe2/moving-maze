package se_ii.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

import se_ii.gruppe2.moving_maze.MovingMazeGame;

public class VictoryScreen implements Screen {

    private final MovingMazeGame game;
    private final SpriteBatch batch;
    private OrthographicCamera camera;

    //Textures
    private Texture bgImageTexture;
    private TextureRegion bgTextureRegion;

    public VictoryScreen(MovingMazeGame game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;
    }

    @Override
    public void show() {
    camera= MovingMazeGame.getStandardizedCamera();

    bgImageTexture = new Texture(Gdx.files.internal("ui/bg_moss.jpeg"));
    bgTextureRegion= new TextureRegion(bgImageTexture);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);

        batch.begin();
            batch.draw(bgTextureRegion,0,0);


        batch.end();
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
