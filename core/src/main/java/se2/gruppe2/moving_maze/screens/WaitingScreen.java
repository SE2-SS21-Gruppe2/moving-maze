package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import se2.gruppe2.moving_maze.MovingMazeGame;

public class WaitingScreen implements Screen {

    final MovingMazeGame game;
    OrthographicCamera camera;

    //ui stuff
    private Stage stage;
    private Skin skin;
    private TextButton backButton;
    private float scalingFactor;

    //Textures and views
    private Texture bgImageTexture;
    private TextureRegion bgTextureRegion;
    private Texture myFontTexture;
    private BitmapFont myFont;
    private Label.LabelStyle myLblStyle;

    public WaitingScreen(final MovingMazeGame game) {
        this.game = game;
        camera = MovingMazeGame.getStandardizedCamera();
    }

    @Override
    public void show() {
        //instantiate background textures
        bgImageTexture = new Texture(Gdx.files.internal("ui/bg_moss.jpeg"));
        bgTextureRegion = new TextureRegion(bgImageTexture);

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        scalingFactor = Gdx.graphics.getWidth()/1280f;


        //Button
        backButton = new TextButton("Back", skin);
        backButton.getLabel().setFontScale(2.0f*scalingFactor);
        Container<TextButton> backButtonContainer = new Container<>(backButton);
        backButtonContainer.setTransform(true);
        backButtonContainer.size(100*scalingFactor, 50f*scalingFactor);
        backButtonContainer.setPosition(75f*scalingFactor,Gdx.graphics.getHeight() - 50f*scalingFactor - backButtonContainer.getHeight());
        stage.addActor(backButtonContainer);


        myFontTexture = new Texture(Gdx.files.internal("ui/nunito.png"));
        myFontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        myFont = new BitmapFont(Gdx.files.internal("ui/nunito.fnt"), new TextureRegion(myFontTexture), false);
        myLblStyle = new Label.LabelStyle(myFont, Color.WHITE);

        Label myLabel = new Label("Waiting for host to start the game ...", myLblStyle);
        myLabel.setScale(3.0f * scalingFactor);
        myLabel.setPosition(Gdx.graphics.getWidth()/2f - myLabel.getWidth()/2.0f, Gdx.graphics.getHeight()/2f - myLabel.getHeight()/2.0f);


        stage.addActor(myLabel);

        Gdx.input.setInputProcessor(stage);

        backButton.addListener(new ClickListener(){

            public void clicked(InputEvent event, float x, float y) {
                game.client.leaveSession(game.getPlayer(), game.getSessionKey());
                game.setScreen(game.mainMenuScreen);
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(bgTextureRegion, 0, 0);
        game.batch.end();

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