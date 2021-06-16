package se_ii.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import se_ii.gruppe2.moving_maze.gameboard.GameBoardFactory;
import se_ii.gruppe2.moving_maze.helperclasses.*;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.MovingMazeGame;

public class CreateSessionScreen implements Screen {

    private final MovingMazeGame game;
    private final SpriteBatch batch;
    private OrthographicCamera camera;

    private MyShapeRenderer myShapeRenderer;
    private Stage stage;
    private Skin skin;

    private TextField txfName;

    private Texture myFontTexture;
    private Texture startImageTexture;
    private Label.LabelStyle myLblStyle;
    private TextButton backButton;

    // measures
    private float myScreenWidth;
    private float scalingFactor;

    // Tables
    private Table table;
    private Table leftTable;
    private Table rightTable;
    private Label settingsLabel;
    private Slider difficultySlider;
    private Label difficultyValueLabel;
    private Slider numberOfCardsSlider;
    private Label numOfCardsValueLabel;
    private TextButton cheatingAllowedButton;
    private Label themeLabel;
    private TextButton themeButton;
    private Label player1Label;
    private Label player2Label;
    private Label player3Label;
    private Label gameCode;
    private Image startIcon;

    // game state variables
    private boolean cheatingAllowed;
    private int difficulty;
    private int numOfCards;
    private String theme;
    private float clock;


    private static final String NOGAMECODE = "------";

    // Keys for the locally stored preferences
    private static final String LOCALPLAYERNAME = "localPlayerName";
    private static final String LOBBYDIFFICULTY = "lobbyDifficulty";
    private static final String LOBBYNUMOFCARDS = "lobbyNumOfCards";
    private static final String LOBBYCHEATINGALLOWED = "lobbyCheatingAllowed";
    private static final String LOBBYTHEME = "lobbyTheme";


    public CreateSessionScreen(final MovingMazeGame game) {
        this.game = game;
        this.batch = game.getBatch();
    }

    @Override
    public void show() {
        camera = game.getStandardizedCamera();

        startImageTexture = new Texture(Gdx.files.internal("ui/start.png"));

        myShapeRenderer = new MyShapeRenderer();

        myScreenWidth = Gdx.graphics.getWidth();
        scalingFactor = myScreenWidth/1280f;
        clock = 0;

        myLblStyle = game.getStyle().getLabelStyle();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        setUpTables();
        stage.addActor(table);

        backButton = new TextButton("Back", skin);
        backButton.getLabel().setFontScale(2.0f*scalingFactor);
        Container<TextButton> backButtonContainer = new Container<>(backButton);
        backButtonContainer.setTransform(true);
        backButtonContainer.size(100*scalingFactor, 50f*scalingFactor);
        backButtonContainer.setPosition(75f*scalingFactor,Gdx.graphics.getHeight() - 50f*scalingFactor - backButtonContainer.getHeight());
        stage.addActor(backButtonContainer);

        setUpActorListeners();

        game.setLocalPlayer(new Player(game.getPreferences().getString(LOCALPLAYERNAME, "temp_SessionCreator")));
        game.getClient().createNewSession(game.getLocalPlayer());

        // Debugging
        stage.setDebugAll(false);
    }

    public void setUpTables(){
        // ------------------------- Table Layout -------------------------

        // ------------------------- Left Table -------------------------
        leftTable = new Table();
        leftTable.defaults().padTop(20f*scalingFactor);
        leftTable.columnDefaults(0).padLeft(25f*scalingFactor);
        leftTable.columnDefaults(2).width(50f*scalingFactor).padRight(25f*scalingFactor);

        // --- LT - 1st Row (Name) ---
        var nameLabel = new Label("Name:", myLblStyle);
        nameLabel.setFontScale(scalingFactor);
        leftTable.add(nameLabel).align(Align.left);

        txfName = new TextField(game.getPreferences().getString(LOCALPLAYERNAME, "Name"), skin);
        var textFieldStyle = skin.get(TextField.TextFieldStyle.class);
        textFieldStyle.font.getData().scale(1.5f*scalingFactor);
        txfName.setStyle(textFieldStyle);
        txfName.setAlignment(Align.center);
        leftTable.add(txfName).width(225f*scalingFactor).height(70f*scalingFactor).expandX().fillX();


        // --- LT - 2nd Row (Lobby Settings) ---
        leftTable.row();
        settingsLabel = new Label("Lobby Settings:", myLblStyle);
        settingsLabel.setFontScale(0.85f*scalingFactor);
        leftTable.add(settingsLabel).colspan(3).padTop(60f*scalingFactor).align(Align.left).top();

        // --- LT - 3rd Row (Difficulty) ---
        leftTable.row();
        var difficultyLabel = new Label("Difficulty", myLblStyle);
        difficultyLabel.setFontScale(scalingFactor);
        leftTable.add(difficultyLabel).align(Align.left);

        difficultySlider = new Slider(1, 3, 1, false, skin);
        difficultySlider.setValue(game.getPreferences().getInteger(LOBBYDIFFICULTY, 2));
        Container<Slider> difficultySliderContainer = new Container<>(difficultySlider);
        difficultySliderContainer.setTransform(true);
        difficultySliderContainer.size(100f*scalingFactor, 30f*scalingFactor);
        difficultySliderContainer.setOrigin((100f*scalingFactor)/2.0f,(30f*scalingFactor)/2.0f);
        difficultySliderContainer.setScale(2.5f);
        leftTable.add(difficultySliderContainer).expandX().fillX();

        difficultyValueLabel = new Label(String.valueOf(Math.round(difficultySlider.getValue())), myLblStyle);
        difficultyValueLabel.setFontScale(scalingFactor);
        difficultyValueLabel.setAlignment(Align.center);
        leftTable.add(difficultyValueLabel).align(Align.center);


        // --- LT - 4th Row (# of cards) ---
        leftTable.row();
        var numberOfCardsLabel = new Label("# of Cards", myLblStyle);
        numberOfCardsLabel.setFontScale(scalingFactor);
        leftTable.add(numberOfCardsLabel).align(Align.left);

        numberOfCardsSlider = new Slider(1, 6, 1, false, skin);
        numberOfCardsSlider.setValue(game.getPreferences().getInteger(LOBBYNUMOFCARDS, 3));
        Container<Slider> numberOfCardsSliderContainer = new Container<>(numberOfCardsSlider);
        numberOfCardsSliderContainer.setTransform(true);
        numberOfCardsSliderContainer.size(100f*scalingFactor, 25f*scalingFactor);
        numberOfCardsSliderContainer.setOrigin((100f*scalingFactor)/2.0f,(25f*scalingFactor)/2.0f);
        numberOfCardsSliderContainer.setScale(2.5f);
        leftTable.add(numberOfCardsSliderContainer).expandX().fillX();


        numOfCardsValueLabel = new Label(String.valueOf(Math.round(numberOfCardsSlider.getValue())), myLblStyle);
        numOfCardsValueLabel.setFontScale(scalingFactor);
        numOfCardsValueLabel.setAlignment(Align.center);
        leftTable.add(numOfCardsValueLabel).align(Align.center);


        // --- LT - 5th Row (Cheating) ---
        leftTable.row();
        var cheatingAllowedLabel = new Label("Cheating", myLblStyle);
        cheatingAllowedLabel.setFontScale(scalingFactor);
        leftTable.add(cheatingAllowedLabel).align(Align.left);

        cheatingAllowed = game.getPreferences().getBoolean(LOBBYCHEATINGALLOWED, true);
        if (cheatingAllowed) {
            cheatingAllowedButton = new TextButton("Allowed", skin);
        } else {
            cheatingAllowedButton = new TextButton("Not Allowed", skin);
        }
        cheatingAllowedButton.getLabel().setFontScale(1.8f*scalingFactor);
        Container<TextButton> cheatingAllowedButtonContainer = new Container<>(cheatingAllowedButton);
        cheatingAllowedButtonContainer.setTransform(true);
        cheatingAllowedButtonContainer.size(225f*scalingFactor,cheatingAllowedButton.getHeight()*0.8f);
        leftTable.add(cheatingAllowedButtonContainer).expandX().fillX();


        // --- LT - 6th Row (Theme) ---
        leftTable.row();
        themeLabel = new Label("Theme", myLblStyle);
        themeLabel.setFontScale(scalingFactor);
        leftTable.add(themeLabel).align(Align.left);

        theme = game.getPreferences().getString(LOBBYTHEME, "Original");
        themeButton = new TextButton(theme, skin);
        themeButton.getLabel().setFontScale(1.8f*scalingFactor);
        themeButton.setDisabled(true);
        themeButton.setTouchable(Touchable.disabled);
        themeButton.setColor(0.5f, 0.5f,0.5f,1f);
        Container<TextButton> themeButtonContainer = new Container<>(themeButton);
        themeButtonContainer.setTransform(true);
        themeButtonContainer.size(225f*scalingFactor, themeButton.getHeight()*0.8f);
        leftTable.add(themeButtonContainer).expandX().fillX();

        leftTable.row();
        var emptyLabel = new Label("", myLblStyle);
        emptyLabel.setFontScale(0.5f*scalingFactor);
        leftTable.add(emptyLabel).colspan(3).expand().fill();



        // ------------------------- Right Table -------------------------
        rightTable = new Table();
        rightTable.defaults().padTop(30f*scalingFactor);
        rightTable.columnDefaults(0).padLeft(25f*scalingFactor);

        // --- RT - 1st Row (Connected Players) ---
        var connectedPlayersLabel = new Label("Connected Players:", myLblStyle);
        connectedPlayersLabel.setFontScale(0.85f*scalingFactor);
        rightTable.add(connectedPlayersLabel).colspan(2).align(Align.left).top().expandX();

        // --- RT - 2nd Row (Player 1) ---
        rightTable.row();
        player1Label = new Label("", myLblStyle);
        player1Label.setFontScale(scalingFactor);
        rightTable.add(player1Label).colspan(2).padLeft(40f*scalingFactor).align(Align.left);

        // --- RT - 3rd Row (Player 2) ---
        rightTable.row();
        player2Label = new Label("", myLblStyle);
        player2Label.setFontScale(scalingFactor);
        rightTable.add(player2Label).colspan(2).padLeft(40f*scalingFactor).align(Align.left);

        // --- RT - 4th Row (Player 3) ---
        rightTable.row();
        player3Label = new Label("", myLblStyle);
        player3Label.setFontScale(scalingFactor);
        rightTable.add(player3Label).colspan(2).padLeft(40f*scalingFactor).align(Align.left);


        // --- RT - 5th Row (Code & Start) ---
        rightTable.row();
        gameCode = new Label(NOGAMECODE, myLblStyle);
        gameCode.setAlignment(Align.center);
        gameCode.setFontScale(1.3f*scalingFactor);
        rightTable.add(gameCode).padLeft(40f*scalingFactor).expandY().align(Align.center).center();

        var startIconSprite = new Sprite(startImageTexture);
        startIcon = new Image(new SpriteDrawable(startIconSprite));
        startIcon.setOrigin(startIcon.getOriginX()+startIcon.getWidth()/2.0f, startIcon.getOriginY()+startIcon.getHeight()/2.0f);
        startIcon.setScale(0.75f*scalingFactor);
        stage.addActor(startIcon);
        rightTable.add(startIcon).expand().align(Align.center).center();

        rightTable.row();
        var emptyLabel1 = new Label("", myLblStyle);
        emptyLabel1.setFontScale(0.5f*scalingFactor);
        rightTable.add(emptyLabel1).colspan(2).expandX().fillX();


        // ------------------------- Table -------------------------
        table = new Table();
        table.defaults().pad(5F);
        table.setFillParent(true);

        // --- Table - 1st Row ---
        var lblCreateLobbyHeading = new Label("CREATE LOBBY", myLblStyle);
        lblCreateLobbyHeading.setFontScale(2.0f*scalingFactor);
        lblCreateLobbyHeading.setAlignment(Align.center);
        table.add(lblCreateLobbyHeading).colspan(2).height(100f*scalingFactor).fill().expandY();

        // --- Table - 2nd Row ---
        table.row();
        table.add(leftTable).expand().fill().width(myScreenWidth /2.0f + 40.0f).top();
        table.add(rightTable).expand().fill().width(myScreenWidth /2.0f - 80.0f).top();

        // ------------------------- End Tables -------------------------
    }

    private void setUpActorListeners() {

        difficultySlider.addListener(new ChangeListener(){

            @Override
            public void changed (ChangeEvent event, Actor actor) {
                difficulty = (int) difficultySlider.getValue();
                difficultyValueLabel.setText(difficulty);
            }
        });

        numberOfCardsSlider.addListener(new ChangeListener(){

            @Override
            public void changed (ChangeEvent event, Actor actor) {
                numOfCards = (int) numberOfCardsSlider.getValue();
                numOfCardsValueLabel.setText(numOfCards);
            }
        });

        cheatingAllowedButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                cheatingAllowed ^= true;
                if (cheatingAllowed) {
                    cheatingAllowedButton.setText("Allowed");
                } else {
                    cheatingAllowedButton.setText("Not Allowed");
                }
            }
        });

        themeButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                // code for multiple themes
            }
        });

        txfName.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
            }
        });

        backButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getMainMenuScreen());
                game.getClient().closeSession(game.getSessionKey());
                game.setSessionKey(NOGAMECODE);
            }
        });

        startIcon.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!gameCode.getText().toString().equals(NOGAMECODE)){

                    String oldHostName = game.getPreferences().getString(LOCALPLAYERNAME);

                    game.getPreferences().putString(LOCALPLAYERNAME, txfName.getText());
                    game.getPreferences().putInteger(LOBBYDIFFICULTY, (int) difficultySlider.getValue());
                    game.getPreferences().putInteger(LOBBYNUMOFCARDS, (int) numberOfCardsSlider.getValue());
                    game.getPreferences().putBoolean(LOBBYCHEATINGALLOWED, cheatingAllowed);
                    game.getPreferences().putString(LOBBYTHEME, theme);
                    game.getPreferences().flush();

                    game.getLocalPlayer().setName(txfName.getText());

                    int numberOfItems = (int) numberOfCardsSlider.getValue();

                    game.getClient().initGame(game.getSessionKey(), GameBoardFactory.getStandardGameBoard(), oldHostName, txfName.getText(), numberOfItems);
                }}
        });
    }

    public void createAndStartGame(String[] players, float difficulty, float numOfCards, boolean cheatingAllowed, String theme){
        // TODO: Connection to Server
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(TextureLoader.getSpriteByTexturePath("ui/bg_moss.jpeg", TextureType.BACKGROUND).getTexture(), 0f, 0f);
        batch.end();

        drawShapes();

        batch.begin();
        stage.draw();
        batch.end();

        clock -= Gdx.graphics.getDeltaTime();
        if (clock < 0){
            updateGameCodeAndConnectedPlayers();
            clock = 3;
        }
    }

    private void updateGameCodeAndConnectedPlayers() {

        gameCode.setText(game.getSessionKey());

        if (!game.getConnectedPlayers().isEmpty()){
            if (game.getConnectedPlayers().size() > 0){
                player1Label.setText(game.getConnectedPlayers().get(0));
            } else {
                player1Label.setText("");
            }
            if (game.getConnectedPlayers().size() > 1){
                player2Label.setText(game.getConnectedPlayers().get(1));
            } else {
                player2Label.setText("");
            }
            if (game.getConnectedPlayers().size() > 2){
                player3Label.setText(game.getConnectedPlayers().get(2));
            } else {
                player3Label.setText("");
            }
        } else {
            player1Label.setText("");
            player2Label.setText("");
            player3Label.setText("");
        }
    }

    private void drawShapes(){

        var padding = 10f*scalingFactor;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        myShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        myShapeRenderer.setColor(0.5f,0.5f,0.5f,0.75f);
        // name
        myShapeRenderer.roundedRect(leftTable.getX()+padding, txfName.getY()-0.75f*padding, leftTable.getWidth() - 2.0f*padding, txfName.getHeight() + 2.0f*padding,padding);
        // lobby settings
        myShapeRenderer.roundedRect(leftTable.getX()+padding, themeLabel.getY()-padding, leftTable.getWidth() - 2.0f*padding, ((settingsLabel.getY() + settingsLabel.getHeight()+padding) - (themeLabel.getY()-padding)),padding);
        // joining players
        myShapeRenderer.roundedRect(rightTable.getX() + padding, player3Label.getY() - padding, rightTable.getWidth() - 2.0f*padding, (txfName.getY()-0.75f*padding + txfName.getHeight() + 2.0f*padding) - (player3Label.getY() - padding),padding);
        // game code
        myShapeRenderer.roundedRect(rightTable.getX() + gameCode.getX() - padding, rightTable.getY() + gameCode.getY() - padding, gameCode.getWidth()+2.0f*padding, gameCode.getHeight()+2.0f*padding, padding);
        myShapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        myShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // joining players
        if(!player1Label.getText().toString().equals("")){
            myShapeRenderer.setColor(0.2f, 0.8f, 0.2f, 1);
            myShapeRenderer.roundedRect(rightTable.getX()+player1Label.getX() - padding, rightTable.getY() + player1Label.getY() - padding, rightTable.getWidth() - 6.0f*padding, player1Label.getHeight()+2.0f*padding, padding);
        }
        if(!player2Label.getText().toString().equals("")) {
            myShapeRenderer.setColor(0.8f, 0.2f, 0.2f, 1);
            myShapeRenderer.roundedRect(rightTable.getX()+player1Label.getX() - padding, rightTable.getY() + player2Label.getY() - padding, rightTable.getWidth() - 6.0f*padding, player1Label.getHeight() +2.0f*padding, padding);
        }
        if (!player3Label.getText().toString().equals("")) {
            myShapeRenderer.setColor(0.2f, 0.2f, 0.8f, 1);
            myShapeRenderer.roundedRect(rightTable.getX()+player1Label.getX() - padding, rightTable.getY() + player3Label.getY() - padding, rightTable.getWidth() - 6.0f*padding, player1Label.getHeight() +2.0f*padding, padding);
        }
        myShapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        // lifecycle method
    }

    @Override
    public void pause() {
        // lifecycle method
    }

    @Override
    public void resume() {
        // lifecycle method
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        // skin.dispose();
        startImageTexture.dispose();
    }
}