package se_ii.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import se_ii.gruppe2.moving_maze.MovingMazeGame;
import se_ii.gruppe2.moving_maze.gameBoard.GameBoardFactory;
import se_ii.gruppe2.moving_maze.player.Player;

import java.security.SecureRandom;
import java.util.ArrayList;

public class MainMenuScreen implements Screen {

    private final MovingMazeGame game;
    private final SpriteBatch batch;
    private OrthographicCamera camera;

    // UI stuff
    private Stage stage;
    private Skin skin;
    private Table tableLayout = new Table();
    private Texture headerLogoScaled;
    private ArrayList<Actor> buttons;

    // textures and views
    private Texture bgImageTexture;
    private TextureRegion bgTextureRegion;

    public MainMenuScreen(final MovingMazeGame game) {
        this.game = game;
        this.batch = game.getBatch();
    }

    @Override
    public void show() {
        camera = MovingMazeGame.getStandardizedCamera();
        buttons = new ArrayList<>();

        // global ui-stuff
        headerLogoScaled = getScaledImage("ui/logo.png", 0.5f);
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        // generate and add buttons
        buttons.add(generateStandardButton("New Game", game.getCreateSessionScreen(), true));
        buttons.add(generateStandardButton("Join Game", game.getJoinSessionScreen(), true));
        buttons.add(generateStandardButton("Options", game.getOptionScreen(), true));
        buttons.add(generateStandardButton("Rules", game.getRuleScreen(), true));

        TextButton devMode = generateStandardButton("Dev Mode", game.getGameScreen(), false);
        buttons.add(devMode);
        setupDevMode(devMode);

        tableLayout = get2ColLayout(buttons, headerLogoScaled.getHeight()/2f);

        stage.addActor(tableLayout);
        stage.getCamera().position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, 0);

        // instantiate textures
        bgImageTexture = new Texture(Gdx.files.internal("ui/bg_moss.jpeg"));
        bgTextureRegion = new TextureRegion(bgImageTexture);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

            batch.draw(bgTextureRegion, 0, 0);

            batch.draw(headerLogoScaled,
                    camera.viewportWidth/2 - headerLogoScaled.getWidth()/2.0f,
                    camera.viewportHeight-headerLogoScaled.getHeight()-30);

            stage.draw();

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
     * Generates a TextButton that is ready to be displayed in the main-menu
     * @param label Label to display on the button
     * @param target -screen to redirect to when being clicked
     * @return A textbutton with appropriate properties
     */
    private TextButton generateStandardButton(String label, Screen target, boolean attachDefaultListener) {
        TextButton btn = new TextButton(label, skin);

        // Note: does not have any effect when being added as a table-cell
        btn.setSize(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/6f);

        // Button-label font size should be approximately half the size of the button itself
        btn.getLabel().setFontScale(Gdx.graphics.getHeight() / btn.getHeight() / 2.2f);

        if(attachDefaultListener) {
            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(target);
                }
            });
        }

        return btn;
    }

    /**
     * Get a 2-column Table-Layout for a list of actors (e.g. buttons)
     * @param uiElements The ui Elements to add to the table
     * @return the table containing the ui-elements
     */
    private Table get2ColLayout(ArrayList<Actor> uiElements, float offsetTop) {
        Table tbl = new Table();

        tbl.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f - offsetTop);

        int addCounter = 0;

        for(Actor act : uiElements) {

            if(addCounter >= 2) {
                tbl.row();
                addCounter = 0;
            }

            tbl.add(act).size(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/7f).pad(20);
            addCounter++;
        }

        return tbl;
    }

    /**
     * Setup non-standard behavior for the dev-mode.
     * @param devModeButton The button to bind behavior on.
     */
    private void setupDevMode(TextButton devModeButton) {
        devModeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // in developer mode, all players join the same (static) session
                game.getGameState().setSessionCode("devgame");
                game.getGameState().setBoard(GameBoardFactory.getStandardGameBoard());
                game.setPlayer(new Player("Developer " + new SecureRandom().nextInt(10)));
                game.getClient().joinSession(game.getPlayer(), "devgame");
                game.setScreen(game.getGameScreen());
            }
        });
    }
}
