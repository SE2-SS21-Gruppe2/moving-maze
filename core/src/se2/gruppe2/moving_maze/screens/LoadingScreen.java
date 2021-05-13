package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import se2.gruppe2.moving_maze.MovingMazeGame;

public class LoadingScreen implements Screen {

    private final MovingMazeGame game;
    private ShapeRenderer shapeRenderer;
    private float progress;
    private Texture loadingBar;
    SpriteBatch batch;
    private Stage stage;
    OrthographicCamera camera;



    public LoadingScreen(MovingMazeGame game){
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();
        loadingBar = new Texture(Gdx.files.internal("ui/loadingBar.png"));
        stage = new Stage();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();

    }

    private void queueAssets() {

        game.assets.load("ui/splash.png", Texture.class);
        game.assets.load("ui/uiskin.atlas", TextureAtlas.class);

    }

    @Override
    public void show() {

        shapeRenderer.setProjectionMatrix(game.camera.combined);
        this.progress = 0f;
        queueAssets();

    }

    private void update(float delta) {
        progress = MathUtils.lerp(progress, game.assets.getProgress(), .1f);
        if (game.assets.update() && progress >= game.assets.getProgress() - 0.01f) {
            game.setScreen(game.splashScreen);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);



        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(game.camera.viewportWidth/4, game.camera.viewportHeight / 2 , game.camera.viewportWidth/2f - 33, 16);


        shapeRenderer.setColor(Color.TEAL);
        shapeRenderer.rect(game.camera.viewportWidth/4, game.camera.viewportHeight / 2 , progress * (game.camera.viewportWidth/2f - 33), 16);
        shapeRenderer.end();

        batch.begin();
        batch.draw(new TextureRegion(loadingBar), game.font.getScaleX()/2, game.font.getScaleY()/2);
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

        batch.dispose();
        shapeRenderer.dispose();
        loadingBar.dispose();
    }
}
