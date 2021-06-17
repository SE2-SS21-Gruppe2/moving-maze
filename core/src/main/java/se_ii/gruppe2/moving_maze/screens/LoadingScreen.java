package se_ii.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se_ii.gruppe2.moving_maze.MovingMazeGame;

public class LoadingScreen implements Screen {

    private final MovingMazeGame game;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private float progress;
    private Stage stage;
    OrthographicCamera camera;
    private Image loadingImg;
    private Table table;

    public LoadingScreen(MovingMazeGame game){
        this.game = game;
        this.stage = new Stage(new FitViewport(MovingMazeGame.WIDTH, MovingMazeGame.HEIGHT, game.getCamera()));

        this.batch = game.getBatch();
    }

    private void queueAssets() {

        game.getAssets().load("ui/splash.png", Texture.class);
        game.getAssets().load("ui/uiskin.atlas", TextureAtlas.class);
        game.getAssets().load("ui/loadingBar.png",Texture.class);

    }

    @Override
    public void show() {

        camera = MovingMazeGame.getStandardizedCamera();


        Gdx.input.setInputProcessor(stage);
        stage = new Stage();

        this.shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(game.getCamera().combined);


        this.progress = 0f;

        queueAssets();

        Texture load = new Texture(Gdx.files.internal("ui/loadingBar.png"));

        loadingImg = new Image(load);

        loadingImg.scaleBy(1f);
        loadingImg.setPosition(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/2- loadingImg.getHeight()/2);

        stage.addActor(loadingImg);
        stage.getCamera().position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, 0);



    }

    private void update(float delta) {
        progress = MathUtils.lerp(progress, game.getAssets().getProgress(), .1f);
        if (game.getAssets().update() && progress >= game.getAssets().getProgress() - 0.01f) {
            game.setScreen(game.getSplashScreen());
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(Gdx.graphics.getWidth()/3 + 15, Gdx.graphics.getHeight()/2 , loadingImg.getWidth()*2 -30 , 30);


        shapeRenderer.setColor(Color.TEAL);
        shapeRenderer.rect(Gdx.graphics.getWidth()/3 + 15 , Gdx.graphics.getHeight()/2  , progress * (loadingImg.getWidth()*2 - 30), 30);
        shapeRenderer.end();

        stage.draw();



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
        shapeRenderer.dispose();
        stage.dispose();
    }
}
