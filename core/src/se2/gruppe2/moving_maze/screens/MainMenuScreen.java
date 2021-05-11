package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import se2.gruppe2.moving_maze.MovingMazeGame;

import java.util.ArrayList;

public class MainMenuScreen implements Screen {

    final MovingMazeGame game;
    OrthographicCamera camera;

    // UI stuff
    Stage stage;
    Skin skin;
    Table tableLayout = new Table();
    TextButton createSession, joinSession, options, rules, devMode;
    Texture headerLogoScaled;
    ArrayList<Actor> buttons;


    // textures and views
    Texture bgImageTexture;
    TextureRegion bgTextureRegion;

    public MainMenuScreen(final MovingMazeGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = MovingMazeGame.gameboardCamera();
        buttons = new ArrayList<>();

        // ui
        headerLogoScaled = getScaledImage("ui/logo.png", 0.5f);

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        createSession = new TextButton("Create Session", skin);
        createSession.getLabel().setScale(10, 10);
        joinSession = new TextButton("Join Session", skin);
        joinSession.setSize(MovingMazeGame.BTN_WIDTH, MovingMazeGame.BTN_HEIGHT);
        options = new TextButton("Options", skin);
        options.setSize(MovingMazeGame.BTN_WIDTH, MovingMazeGame.BTN_HEIGHT);
        rules = new TextButton("Rules", skin);
        rules.setSize(MovingMazeGame.BTN_WIDTH, MovingMazeGame.BTN_HEIGHT);
        devMode = new TextButton("Developer Mode", skin);
        devMode.setSize(MovingMazeGame.BTN_WIDTH, MovingMazeGame.BTN_HEIGHT);

        buttons.add(createSession);
        buttons.add(joinSession);
        buttons.add(options);
        buttons.add(rules);
        buttons.add(devMode);

        tableLayout = get2ColLayout(buttons);

        setUpButtonListeners();

        stage.addActor(tableLayout);
        stage.getCamera().position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, 0);

        // instantiate textures
        bgImageTexture = new Texture(Gdx.files.internal("ui/bg_moss.jpeg"));
        bgTextureRegion = new TextureRegion(bgImageTexture);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

            game.batch.draw(bgTextureRegion, 0, 0);

            game.batch.draw(headerLogoScaled,
                    camera.viewportWidth/2 - headerLogoScaled.getWidth()/2.0f,
                    camera.viewportHeight-headerLogoScaled.getHeight()-30);

            stage.draw();

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

    /**
     * Bind ClickListeners to TextButtons so they load the correct screens.
     * NOTE: attempts to generalize this with "Screen" failed ...
     */
    private void setUpButtonListeners() {

        joinSession.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.joinSessionScreen);
            }
        });

        createSession.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.createSessionScreen);
            }
        });

        options.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.optionScreen);
            }
        });


        rules.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.ruleScreen);
            }
        });

        devMode.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.gameScreen);
            }
        });
    }

    /**
     * Get a 2-column Table-Layout for a list of actors (e.g. buttons)
     * @param uiElements The ui Elements to add to the table
     * @return the table containing the ui-elements
     */
    private Table get2ColLayout(ArrayList<Actor> uiElements) {
        Table tbl = new Table();

        int addCounter = 0;

        for(Actor act : uiElements) {

            if(addCounter > 2) {
                tbl.row();
                addCounter = 1;
            }

            tbl.add(act);
            addCounter++;
        }

        return tbl;
    }

}
