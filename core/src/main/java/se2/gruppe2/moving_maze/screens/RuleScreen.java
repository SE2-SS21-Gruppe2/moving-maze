package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import se2.gruppe2.moving_maze.MovingMazeGame;

public class RuleScreen implements Screen {

    private final MovingMazeGame game;
    private final SpriteBatch batch;
    private OrthographicCamera camera;

    //ui stuff
    private Stage stage;
    private Texture backTexture;
    private TextureRegion textureRegion;
    private TextureRegionDrawable textureRegionDrawable;
    private ImageButton backButton;

    //Textures and views
    private Texture bgImageTexture;
    private TextureRegion bgTextureRegion;

    public RuleScreen(final MovingMazeGame game) {
        this.game = game;
        this.batch = game.getBatch();
        camera = MovingMazeGame.getStandardizedCamera();
    }

    @Override
    public void show() {
        //instantiate background textures
        bgImageTexture = new Texture(Gdx.files.internal("ui/bg_moss.jpeg"));
        bgTextureRegion = new TextureRegion(bgImageTexture);

        //Buttons
        backTexture = new Texture(Gdx.files.internal("ui/buttons/backButton.png"));
        textureRegion = new TextureRegion(backTexture);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion);
        backButton = new ImageButton(textureRegionDrawable);
        //backButton.setPosition(MovingMazeGame.WIDTH /9f, MovingMazeGame.HEIGHT - 20f , Align.left);

        stage = new Stage(new ScreenViewport());
        stage.addActor(backButton);

        Gdx.input.setInputProcessor(stage);

        backButton.addListener(event -> {
            game.setScreen(game.getMainMenuScreen());
            return true;
        });

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(bgTextureRegion, 0, 0);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());stage.draw();
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
