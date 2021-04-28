package se2.gruppe2.moving_maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final MovingMazeGame game;
    OrthographicCamera camera;

    // UI stuff
    Stage stage;
    Skin skin;
    Group buttonGroup;
    TextButton createSession, joinSession, options, rules, devMode;
    Texture headerLogoScaled;


    // textures and views
    Texture bgImageTexture;
    TextureRegion bgTextureRegion;

    public MainMenuScreen(final MovingMazeGame game) {
        this.game = game;
        camera = MovingMazeGame.getStandardizedCamera();

        // ui
        /*
        stage = new Stage();
        // TODO: add path
        skin = new Skin(Gdx.files.internal(""));
        Gdx.input.setInputProcessor(stage);
        buttonGroup = new Group();
        buttonGroup.setBounds(0,0,MovingMazeGame.WIDTH, MovingMazeGame.HEIGHT);

        createSession = new TextButton("Create Session", skin);
        joinSession = new TextButton("Join Session", skin);
        options = new TextButton("Options", skin);
        rules = new TextButton("Rules", skin);
        devMode = new TextButton("Developer Mode", skin);

        buttonGroup.addActor(createSession);
        buttonGroup.addActor(joinSession);
        buttonGroup.addActor(options);
        buttonGroup.addActor(rules);
        buttonGroup.addActor(devMode);

        stage.addActor(buttonGroup);
        stage.getCamera().position.set(MovingMazeGame.WIDTH/2.0f, MovingMazeGame.HEIGHT/2.0f, 0);
         */

        headerLogoScaled = getScaledImage("ui/logo.png", 0.7f);


        // instantiate textures
        bgImageTexture = new Texture(Gdx.files.internal("ui/bg_moss.jpeg"));
        bgTextureRegion = new TextureRegion(bgImageTexture);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
            game.batch.draw(bgTextureRegion, 0, 0);
            game.batch.draw(headerLogoScaled, 100, 100);
            game.font.draw(game.batch, "Main menu screen", 100, 100);
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
     * Scales a picture to be x percent of the current screen
     * @param path Gdx-internal asset path to the image file
     * @return the scaled version of the image as Texture-object
     */
    private Texture getScaledImage(String path, float percentOfScreen) {
        Pixmap originalBg = new Pixmap(Gdx.files.internal(path));

        // determine how much the picture has to be scaled in order to fit the screen width exactly
        float baseScalingFactor = (originalBg.getWidth()*1.0f) / (camera.viewportWidth);
        float scalingFactor = baseScalingFactor / percentOfScreen;

        Pixmap scaledBg = new Pixmap((int) (originalBg.getWidth()/scalingFactor),
                                    (int) (originalBg.getHeight()/scalingFactor),
                                        originalBg.getFormat());

        // scale by "redrawing" the original pixmap into the smaller pixmap
        scaledBg.drawPixmap(originalBg,
                0, 0, originalBg.getWidth(), originalBg.getHeight(),
                0, 0, scaledBg.getWidth(), scaledBg.getHeight());

        Texture scaledBgTexture = new Texture(scaledBg);

        originalBg.dispose();
        scaledBg.dispose();

        return scaledBgTexture;
    }
}
