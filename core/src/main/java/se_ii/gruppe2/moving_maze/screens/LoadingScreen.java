package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se2.gruppe2.moving_maze.MovingMazeGame;

public class LoadingScreen implements Screen {

    private final MovingMazeGame game;
    private ShapeRenderer shapeRenderer;
    private float progress;
    private Stage stage;
    OrthographicCamera camera;
    private Image loadingImg;
    private Table table;




    public LoadingScreen(MovingMazeGame game){
        this.game = game;
        this.stage = new Stage(new FitViewport(MovingMazeGame.WIDTH, MovingMazeGame.HEIGHT, game.camera));

    }

    private void queueAssets() {

        game.assets.load("ui/splash.png", Texture.class);
        game.assets.load("ui/uiskin.atlas", TextureAtlas.class);
        game.assets.load("ui/loadingBar.png",Texture.class);

    }

    @Override
    public void show() {

        camera = new OrthographicCamera();


        Gdx.input.setInputProcessor(stage);
        stage = new Stage();

        this.shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(game.camera.combined);


        this.progress = 0f;

        queueAssets();

        Texture load = new Texture(Gdx.files.internal("ui/loadingBar.png"));

        loadingImg = new Image(load);

        loadingImg.setAlign(Align.center);
        loadingImg.setVisible(true);
        loadingImg.setSize(70f,16f);

        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight()/2f + 70f);
        table.padBottom(500);
        table.add(loadingImg).size(Gdx.graphics.getWidth()/2f,100).center();


        stage.addActor(table);

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

        update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(game.camera.viewportWidth/4 + 15, game.camera.viewportHeight / 2 , game.camera.viewportWidth/2f -33 , 16);


        shapeRenderer.setColor(Color.TEAL);
        shapeRenderer.rect(game.camera.viewportWidth/4 + 15, game.camera.viewportHeight / 2 , progress * (game.camera.viewportWidth/2f -33), 16);
        shapeRenderer.end();

        stage.draw();



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

        shapeRenderer.dispose();
        stage.dispose();
    }
}
