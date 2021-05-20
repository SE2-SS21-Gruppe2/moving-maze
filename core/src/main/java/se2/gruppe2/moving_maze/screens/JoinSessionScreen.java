package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import se2.gruppe2.moving_maze.MovingMazeGame;
import se2.gruppe2.moving_maze.player.Player;

public class JoinSessionScreen implements Screen {

    final MovingMazeGame game;
    OrthographicCamera camera;

    // UI stuff
    Stage stage;
    Skin skin;
    Table tableLayout;
    Texture headerLogoScaled;
    TextButton joinGame;
    TextField gameCode;
    TextField playerName;
    TextButton backButton;

    // textures and views
    Texture bgImageTexture;
    TextureRegion bgTextureRegion;

    public JoinSessionScreen(final MovingMazeGame game) {
        this.game = game;
        camera = MovingMazeGame.getStandardizedCamera();
    }

    @Override
    public void show() {

        // global ui-stuff
        headerLogoScaled = getScaledImage("ui/logo.png", 0.5f);
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        float scalingFactor = Gdx.graphics.getWidth()/1280.0f;
        Texture myFontTexture = new Texture(Gdx.files.internal("ui/nunito.png"));
        myFontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        BitmapFont myFont = new BitmapFont(Gdx.files.internal("ui/nunito.fnt"), new TextureRegion(myFontTexture), false);
        //myFont.getData().setScale(2f);
        Label.LabelStyle myLblStyle = new Label.LabelStyle(myFont, Color.WHITE);

        var lblCreateLobbyHeading = new Label("CREATE LOBBY", myLblStyle);
        lblCreateLobbyHeading.setFontScale(2.0f*scalingFactor);
        lblCreateLobbyHeading.setAlignment(Align.center);

        var textFieldStyle = skin.get(TextField.TextFieldStyle.class);
        textFieldStyle.font.getData().scale(1.2f*scalingFactor);

        playerName = new TextField("Name", skin);
        playerName.setSize(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/7f);
        playerName.setStyle(textFieldStyle);
        playerName.setAlignment(Align.center);
        gameCode = new TextField("Game Code", skin);
        gameCode.setSize(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/7f);
        gameCode.setStyle(textFieldStyle);
        gameCode.setAlignment(Align.center);
        joinGame = new TextButton("Join Game", skin);
        joinGame.setSize(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/7f);
        joinGame.getLabel().setFontScale(Gdx.graphics.getHeight()/joinGame.getHeight()/2.2f);

        float offsetTop = 100f;
        tableLayout = new Table();
        tableLayout.setPosition(Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight()/2.0f - offsetTop);
        tableLayout.add(playerName).pad(10f).size(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/7f);
        tableLayout.row();
        tableLayout.add(gameCode).pad(10f).size(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/7f);
        tableLayout.row();
        tableLayout.add(joinGame).pad(10f).size(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/7f);;

        stage.addActor(tableLayout);
        stage.getCamera().position.set(Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight()/2.0f, 0);

        // instantiate textures
        bgImageTexture = new Texture(Gdx.files.internal("ui/bg_moss.jpeg"));
        bgTextureRegion = new TextureRegion(bgImageTexture);

        backButton = new TextButton("Back", skin);
        backButton.getLabel().setFontScale(2.0f*scalingFactor);
        Container<TextButton> backButtonContainer = new Container<>(backButton);
        backButtonContainer.setTransform(true);
        backButtonContainer.size(100*scalingFactor, 50f*scalingFactor);
        backButtonContainer.setPosition(75f*scalingFactor,Gdx.graphics.getHeight() - 50f*scalingFactor - backButtonContainer.getHeight());
        stage.addActor(backButtonContainer);

        setUpActorListeners();

    }

    private void setUpActorListeners() {

        joinGame.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setPlayer(new Player(playerName.getText()));
                if (!gameCode.getText().equals("") && !gameCode.getText().equals("Game Code")){
                    game.setSessionKey(gameCode.getText());
                    game.client.joinSession(game.getPlayer(), game.getSessionKey());
                    game.setScreen(game.waitingScreen);
                }

            }
        });

        gameCode.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameCode.getText().equals("Game Code")){
                    gameCode.setText("");
                }
            }
        });

        playerName.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerName.getText().equals("Name")){
                    playerName.setText("");
                }
            }
        });

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
        this.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

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
