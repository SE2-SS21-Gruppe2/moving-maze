package se_ii.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import se_ii.gruppe2.moving_maze.MovingMazeGame;

public class WaitingScreen implements Screen {

    final MovingMazeGame game;
    OrthographicCamera camera;
    private final SpriteBatch batch;

    //ui stuff
    private Stage stage;


    //Textures and views
    private TextureRegion bgTextureRegion;

    public WaitingScreen(final MovingMazeGame game) {
        this.game = game;
        this.batch = game.getBatch();
        camera = MovingMazeGame.getStandardizedCamera();
    }

    @Override
    public void show() {
        //instantiate background textures
        var bgImageTexture = new Texture(Gdx.files.internal("ui/bg_moss.jpeg"));
        bgTextureRegion = new TextureRegion(bgImageTexture);

        stage = new Stage(new ScreenViewport());
        var skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        var scalingFactor = Gdx.graphics.getWidth() / 1280f;


        //Button
        var backButton = new TextButton("Back", skin);
        backButton.getLabel().setFontScale(2.0f * scalingFactor);
        Container<TextButton> backButtonContainer = new Container<>(backButton);
        backButtonContainer.setTransform(true);
        backButtonContainer.size(100 * scalingFactor, 50f * scalingFactor);
        backButtonContainer.setPosition(75f * scalingFactor, Gdx.graphics.getHeight() - 50f * scalingFactor - backButtonContainer.getHeight());
        stage.addActor(backButtonContainer);


        var myFontTexture = new Texture(Gdx.files.internal("ui/nunito.png"));
        myFontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        var myFont = new BitmapFont(Gdx.files.internal("ui/nunito.fnt"), new TextureRegion(myFontTexture), false);
        var myLblStyle = new Label.LabelStyle(myFont, Color.WHITE);

        var myLabel = new Label("Waiting for host to start the game ...", myLblStyle);
        myLabel.setScale(3.0f * scalingFactor);
        myLabel.setPosition(Gdx.graphics.getWidth() / 2f - myLabel.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2f - myLabel.getHeight() / 2.0f);


        stage.addActor(myLabel);

        Gdx.input.setInputProcessor(stage);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getClient().leaveSession(game.getLocalPlayer(), game.getSessionKey());
                game.setScreen(game.getMainMenuScreen());
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(bgTextureRegion, 0, 0);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
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

    }

}
