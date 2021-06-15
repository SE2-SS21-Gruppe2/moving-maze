package se_ii.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se_ii.gruppe2.moving_maze.MovingMazeGame;
import se_ii.gruppe2.moving_maze.helperclasses.TextureLoader;
import se_ii.gruppe2.moving_maze.helperclasses.TextureType;
import se_ii.gruppe2.moving_maze.player.Player;

public class JoinSessionScreen implements Screen {

    private final MovingMazeGame game;
    private final SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;

    // UI stuff
    Stage stage;
    Skin skin;
    Table tableLayout;
    Texture headerLogoScaled;
    TextButton joinGame;
    TextField gameCode;
    TextField playerName;
    TextButton backButton;
    float scalingFactor;

    // helper variables
    boolean gameCodeFocused;
    boolean playerNameFocused;
    Button unfocusButton;

    public JoinSessionScreen(final MovingMazeGame game) {
        this.game = game;
        this.batch = game.getBatch();
        camera = MovingMazeGame.getStandardizedCamera();
        viewport = new FitViewport(0,0, camera);
    }

    @Override
    public void show() {
        // global ui-stuff
        headerLogoScaled = getScaledImage("ui/logo.png", 0.5f);
        stage = new Stage();
        skin = game.getStyle().getSkin();
        Gdx.input.setInputProcessor(stage);

        scalingFactor = Gdx.graphics.getWidth()/1280.0f;
        var myLblStyle = game.getStyle().getLabelStyle();

        playerNameFocused = false;
        gameCodeFocused = false;

        unfocusButton = new Button(skin);
        unfocusButton.setColor(0,0,0,0);
        unfocusButton.setVisible(false);
        unfocusButton.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        unfocusButton.setPosition(0,0);
        stage.addActor(unfocusButton);

        var lblCreateLobbyHeading = new Label("CREATE LOBBY", myLblStyle);
        lblCreateLobbyHeading.setFontScale(2.0f*scalingFactor);
        lblCreateLobbyHeading.setAlignment(Align.center);

        var textFieldStyle = skin.get(TextField.TextFieldStyle.class);
        textFieldStyle.font.getData().scale(1.2f*scalingFactor);

        playerName = new TextField(game.getPreferences().getString("localPlayerName", "Name"), skin);
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

        var offsetTop = 100f;
        tableLayout = new Table();
        tableLayout.setPosition(Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight()/2.0f - offsetTop);
        tableLayout.add(playerName).pad(10f).size(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/7f);
        tableLayout.row();
        tableLayout.add(gameCode).pad(10f).size(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/7f);
        tableLayout.row();
        tableLayout.add(joinGame).pad(10f).size(Gdx.graphics.getWidth()/4f, Gdx.graphics.getHeight()/7f);

        stage.addActor(tableLayout);

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

        playerName.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerName.getText().equals("Name")){
                    playerName.setText("");
                }
                joinGame.setVisible(false);
                gameCode.setVisible(false);
                tableLayout.setPosition(Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight()/2.0f - 100f + 50f*scalingFactor);
                playerNameFocused = true;
                unfocusButton.setVisible(true);
            }
        });

        gameCode.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameCode.getText().equals("Game Code")){
                    gameCode.setText("");
                }
                joinGame.setVisible(false);
                playerName.setVisible(false);
                tableLayout.setPosition(Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight()/2.0f - 100f + 150f*scalingFactor);
                gameCodeFocused = true;
                unfocusButton.setVisible(true);
            }
        });

        gameCode.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                var cursorPos = gameCode.getCursorPosition();
                gameCode.setText(gameCode.getText().toUpperCase());
                gameCode.setCursorPosition(cursorPos);
            }
        });

        joinGame.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameCode.getText().length() == 6 && gameCode.getText().matches("^[A-Z]{6}")){
                    game.setLocalPlayer(new Player(playerName.getText()));
                    game.getPreferences().putString("localPlayerName", playerName.getText());
                    game.getPreferences().flush();

                    game.setSessionKey(gameCode.getText());
                    game.getClient().joinSession(game.getLocalPlayer(), game.getSessionKey());
                    game.setScreen(game.getWaitingScreen());
                }
            }
        });

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getMainMenuScreen());
            }
        });

        unfocusButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
               resetLayout();
            }
        });
    }

    private void resetLayout(){
        if (gameCodeFocused || playerNameFocused){
            stage.unfocusAll();
            Gdx.input.setOnscreenKeyboardVisible(false);
            gameCodeFocused = false;
            playerNameFocused = false;
            joinGame.setVisible(true);
            gameCode.setVisible(true);
            playerName.setVisible(true);
            tableLayout.setPosition(Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight()/2.0f - 100f);
            unfocusButton.setVisible(false);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(TextureLoader.getSpriteByTexturePath("ui/bg_moss.jpeg", TextureType.BACKGROUND).getTexture(), 0f, 0f);
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
        this.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    private Texture getScaledImage(String path, float percentOfScreen) {
        var originalBg = new Pixmap(Gdx.files.internal(path));

        // determine how much the picture has to be scaled in order to fit the screen width exactly
        float baseScalingFactor = (originalBg.getWidth()*1.0f) / (camera.viewportWidth);
        float scalingFactor = baseScalingFactor / percentOfScreen;

        var scaledBg = new Pixmap((int) (originalBg.getWidth()/scalingFactor),
                (int) (originalBg.getHeight()/scalingFactor),
                originalBg.getFormat());

        // scale by "redrawing" the original pixmap into the smaller pixmap
        scaledBg.drawPixmap(originalBg,
                0, 0, originalBg.getWidth(), originalBg.getHeight(),
                0, 0, scaledBg.getWidth(), scaledBg.getHeight());

        var scaledBgTexture = new Texture(scaledBg);

        originalBg.dispose();
        scaledBg.dispose();

        return scaledBgTexture;
    }
}