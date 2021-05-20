package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import se2.gruppe2.moving_maze.MovingMazeGame;
import se2.gruppe2.moving_maze.helperclasses.MyShapeRenderer;
import se2.gruppe2.moving_maze.player.Player;

public class CreateSessionScreen implements Screen {

    final MovingMazeGame game;
    private OrthographicCamera camera;

    private MyShapeRenderer myShapeRenderer;
    private Stage stage;
    private Skin skin;

    private TextField txfName;

    // textures and views
    private Texture bgImageTexture;
    private TextureRegion bgTextureRegion;

    private Texture myFontTexture;
    private Texture startImageTexture;
    private Label.LabelStyle myLblStyle;
    private TextButton backButton;

    // measures
    private float myScreenHeight;
    private float myScreenWidth;
    private float xWidth;
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
    private Label connectedPlayersLabel;
    private Label player1Label;
    private Label player2Label;
    private Label player3Label;
    private Label gameCode;
    private Image startIcon;

    // game state variables
    private Boolean gameReady;
    private Boolean cheatingAllowed;
    private int difficulty;
    private int numOfCards;
    private String theme;
    private float clock;


    public CreateSessionScreen(final MovingMazeGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = MovingMazeGame.getStandardizedCamera();

        // instantiate textures
        bgImageTexture = new Texture(Gdx.files.internal("ui/bg_nomoss.jpeg"));
        bgTextureRegion = new TextureRegion(bgImageTexture);
        startImageTexture = new Texture(Gdx.files.internal("ui/start.png"));

        myShapeRenderer = new MyShapeRenderer();

        myScreenHeight = Gdx.graphics.getHeight();
        myScreenWidth = Gdx.graphics.getWidth();
        xWidth = myScreenWidth / 25f;

        // game state variables
        gameReady = true;
        cheatingAllowed = true;
        difficulty = 2;
        numOfCards = 3;
        theme = "Original";

        myFontTexture = new Texture(Gdx.files.internal("ui/nunito.png"));
        myFontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        BitmapFont myFont = new BitmapFont(Gdx.files.internal("ui/nunito.fnt"), new TextureRegion(myFontTexture), false);
        myLblStyle = new Label.LabelStyle(myFont, Color.WHITE);
        scalingFactor = myScreenWidth/1280f;


        stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);
        clock = 0;

        setUpTables();
        stage.addActor(table);

        backButton = new TextButton("Back", skin);
        backButton.getLabel().setFontScale(2.0f*scalingFactor);
        Container<TextButton> backButtonContainer = new Container<>(backButton);
        backButtonContainer.setTransform(true);
        backButtonContainer.size(100*scalingFactor, 50f*scalingFactor);
        backButtonContainer.setPosition(75f*scalingFactor,myScreenHeight - 50f*scalingFactor - backButtonContainer.getHeight());
        stage.addActor(backButtonContainer);

        setUpActorListeners();

        game.setPlayer(new Player("temp_SessionCreator"));
        game.client.createNewSession(game.getPlayer());


        // Debugging
        stage.setDebugAll(false);

    }

    public void setUpTables(){
        // ---------- Table Layout ----------

        // --- Left Table ---
        leftTable = new Table();
        leftTable.defaults().padTop(35f*scalingFactor);
        leftTable.columnDefaults(0).width(0.17f*myScreenWidth);
        leftTable.columnDefaults(2).width(50f*scalingFactor);

        // --- LT - 1st Row (Name) ---
        var nameLabel = new Label("Name:", myLblStyle);
        nameLabel.setAlignment(Align.left);
        nameLabel.setFontScale(scalingFactor);
        leftTable.add(nameLabel).pad(0, 20f*scalingFactor, 0, 0).align(Align.left);

        txfName = new TextField("Martin", skin);
        var textFieldStyle = skin.get(TextField.TextFieldStyle.class);
        textFieldStyle.font.getData().scale(scalingFactor);
        txfName.setStyle(textFieldStyle);
        leftTable.add(txfName).pad(10f, 10f, 0, 10f).width(250f*scalingFactor).height(75f*scalingFactor).expandX().fillX();


        // --- LT - 2nd Row (Lobby Settings) ---
        leftTable.row();
        settingsLabel = new Label("Lobby Settings:", myLblStyle);
        settingsLabel.setAlignment(Align.left);
        settingsLabel.setFontScale(0.85f*scalingFactor);
        leftTable.add(settingsLabel).colspan(3).pad(50f*scalingFactor, 20f*scalingFactor, 0, 0).align(Align.left).top();

        // --- LT - 3rd Row (Difficulty) ---
        leftTable.row();
        var difficultyLabel = new Label("Difficulty", myLblStyle);
        difficultyLabel.setAlignment(Align.left);
        difficultyLabel.setFontScale(scalingFactor);
        leftTable.add(difficultyLabel).padLeft(20f*scalingFactor).align(Align.left);

        difficultySlider = new Slider(1, 3, 1, false, skin);
        difficultySlider.setValue(2);
        Container<Slider> difficultySliderContainer = new Container<>(difficultySlider);
        difficultySliderContainer.setTransform(true);
        difficultySliderContainer.size(100f*scalingFactor, 25f*scalingFactor);
        difficultySliderContainer.setOrigin((difficultySliderContainer.getWidth() + 100f*scalingFactor)/2.0f,
                (difficultySliderContainer.getHeight() + 25f*scalingFactor)/2.0f);
        difficultySliderContainer.setScale(2.5f);
        leftTable.add(difficultySliderContainer).expandX().fillX();

        difficultyValueLabel = new Label(String.valueOf(Math.round(difficultySlider.getValue())), myLblStyle);
        difficultyValueLabel.setFontScale(scalingFactor);
        difficultyValueLabel.setAlignment(Align.center);
        leftTable.add(difficultyValueLabel).align(Align.center).padRight(20f*scalingFactor);


        // --- LT - 4th Row (# of cards) ---
        leftTable.row();
        var numberOfCardsLabel = new Label("# of Cards", myLblStyle);
        numberOfCardsLabel.setAlignment(Align.left);
        numberOfCardsLabel.setFontScale(scalingFactor);
        leftTable.add(numberOfCardsLabel).padLeft(20f*scalingFactor).align(Align.left);

        numberOfCardsSlider = new Slider(1, 6, 1, false, skin);
        numberOfCardsSlider.setValue(3);
        Container<Slider> numberOfCardsSliderContainer = new Container<>(numberOfCardsSlider);
        numberOfCardsSliderContainer.setTransform(true);
        numberOfCardsSliderContainer.size(100f*scalingFactor, 25f*scalingFactor);
        numberOfCardsSliderContainer.setOrigin((numberOfCardsSliderContainer.getWidth() + 100f*scalingFactor)/2.0f,
                (numberOfCardsSliderContainer.getHeight() + 25f*scalingFactor)/2.0f);
        numberOfCardsSliderContainer.setScale(2.5f);
        leftTable.add(numberOfCardsSliderContainer).expandX().fillX();


        numOfCardsValueLabel = new Label(String.valueOf(Math.round(numberOfCardsSlider.getValue())), myLblStyle);
        numOfCardsValueLabel.setFontScale(scalingFactor);
        numOfCardsValueLabel.setAlignment(Align.center);
        leftTable.add(numOfCardsValueLabel).align(Align.center).padRight(20f*scalingFactor);


        // --- LT - 5th Row (Cheating) ---
        leftTable.row();
        var cheatingAllowedLabel = new Label("Cheating", myLblStyle);
        cheatingAllowedLabel.setAlignment(Align.left);
        cheatingAllowedLabel.setFontScale(scalingFactor);
        leftTable.add(cheatingAllowedLabel).padLeft(20f*scalingFactor).align(Align.left);

        cheatingAllowedButton = new TextButton("Allowed", skin);
        cheatingAllowedButton.getLabel().setFontScale(2.0f*scalingFactor);
        Container<TextButton> cheatingAllowedButtonContainer = new Container<>(cheatingAllowedButton);
        cheatingAllowedButtonContainer.setTransform(true);
        cheatingAllowedButtonContainer.size(225f*scalingFactor,cheatingAllowedButton.getHeight()*0.8f);
        leftTable.add(cheatingAllowedButtonContainer).expandX().fillX();


        // --- LT - 6th Row (Theme) ---
        leftTable.row();
        themeLabel = new Label("Theme", myLblStyle);
        themeLabel.setAlignment(Align.left);
        themeLabel.setFontScale(scalingFactor);
        leftTable.add(themeLabel).padLeft(20f*scalingFactor).align(Align.left);

        themeButton = new TextButton(theme, skin);
        themeButton.getLabel().setFontScale(2.0f*scalingFactor);
        themeButton.setDisabled(true);
        themeButton.setTouchable(Touchable.disabled);
        themeButton.setColor(0.5f, 0.5f,0.5f,1f);
        Container<TextButton> themeButtonContainer = new Container<>(themeButton);
        themeButtonContainer.setTransform(true);
        themeButtonContainer.size(225f*scalingFactor, themeButton.getHeight()*0.8f);
        leftTable.add(themeButtonContainer).expandX().fillX();

        leftTable.row();
        var emptyLabel1 = new Label("", myLblStyle);
        emptyLabel1.setFontScale(0.1f*scalingFactor);
        leftTable.add(emptyLabel1).colspan(3).padLeft(20f*scalingFactor).expand().fill();




        // --- Right Table
        rightTable = new Table();
        rightTable.defaults().padTop(35f*scalingFactor);

        // --- RT - 1st Row (Connected Players) ---
        connectedPlayersLabel = new Label("Connected Players:", myLblStyle);
        connectedPlayersLabel.setAlignment(Align.left);
        connectedPlayersLabel.setFontScale(0.85f*scalingFactor);
        rightTable.add(connectedPlayersLabel).colspan(2).pad(0, 10f, 0, 0).align(Align.left).top().expandX();

        // --- RT - 2nd Row (Player 1) ---
        rightTable.row();
        player1Label = new Label("Flo", myLblStyle);
        player1Label.setAlignment(Align.left);
        player1Label.setFontScale(scalingFactor);
        rightTable.add(player1Label).padLeft(40f).align(Align.left);

        // --- RT - 3rd Row (Player 2) ---
        rightTable.row();
        player2Label = new Label("Alexandra", myLblStyle);
        player2Label.setAlignment(Align.left);
        player2Label.setFontScale(scalingFactor);
        rightTable.add(player2Label).padLeft(40f).align(Align.left);

        // --- RT - 4th Row (Player 3) ---
        rightTable.row();
        player3Label = new Label("Andi", myLblStyle);
        player3Label.setAlignment(Align.left);
        player3Label.setFontScale(scalingFactor);
        rightTable.add(player3Label).padLeft(40f).align(Align.left);


        // --- RT - 5th Row (Code & Start) ---
        rightTable.row();
        gameCode = new Label("------", myLblStyle);
        gameCode.setAlignment(Align.center);
        gameCode.setFontScale(1.3f*scalingFactor);
        rightTable.add(gameCode).padLeft(20f*scalingFactor).expandY().align(Align.center).center();

        var startIconSprite = new Sprite(startImageTexture);
        startIcon = new Image(new SpriteDrawable(startIconSprite));
        startIcon.setOrigin(startIcon.getOriginX()+startIcon.getWidth()/2.0f, startIcon.getOriginY()+startIcon.getHeight()/2.0f);
        startIcon.setScale(0.75f*scalingFactor);
        stage.addActor(startIcon);
        rightTable.add(startIcon).expand().align(Align.center).center();



        // --- Table ---
        table = new Table();
        table.defaults().pad(5F).maxHeight(myScreenHeight *0.9f);
        table.setFillParent(true);

        // --- Table - 1st Row ---
        var lblCreateLobbyHeading = new Label("CREATE LOBBY", myLblStyle);
        lblCreateLobbyHeading.setFontScale(2.0f*scalingFactor);
        lblCreateLobbyHeading.setAlignment(Align.center);
        table.add(lblCreateLobbyHeading).colspan(2).padBottom(15f*scalingFactor).padTop(10f*scalingFactor).fillX().expand();

        // --- Table - 2nd Row ---
        table.row();
        table.add(leftTable).expand().fill().width(myScreenWidth /2.0f + 40.0f).top();
        table.add(rightTable).expand().fill().width(myScreenWidth /2.0f - 80.0f).top();

        // --- Table - Last Row ---
        table.row().height(30f*scalingFactor);
        var emptyLabel = new Label("", skin);
        table.add(emptyLabel).colspan(2);


        // ---------- End Tables ----------
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

        startIcon.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Boolean.TRUE.equals(gameReady)){
                    // TODO: update playerName on server
                    // TODO: create and start game
                }
            }
        });

        cheatingAllowedButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                cheatingAllowed ^= true;
                if (Boolean.TRUE.equals(cheatingAllowed)) {
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
                game.setScreen(game.mainMenuScreen);
                game.client.closeSession(game.getSessionKey());
                game.setSessionKey("------");
            }
        });
    }

    public void createAndStartGame(String[] players, float difficulty, float numOfCards, boolean cheatingAllowed, String theme){
        // TODO: Connection to Server
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(bgTextureRegion, 0, 0);
        game.batch.end();

        drawShapes();

        game.batch.begin();
        stage.draw();
        game.batch.end();

        clock -= Gdx.graphics.getDeltaTime();
        if (clock < 0){
            updateGameCodeAndConnectedPlayers();
            clock = 5;
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
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        myShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        myShapeRenderer.setColor(0.5f,0.5f,0.5f,0.8f);
        // name
        myShapeRenderer.roundedRect(leftTable.getX() + 5f*scalingFactor, txfName.getY() + txfName.getHeight()/2 - 5f*scalingFactor, leftTable.getWidth() - leftTable.getX() + 10f*scalingFactor, txfName.getHeight()+25f*scalingFactor,10);
        // lobby settings
        myShapeRenderer.roundedRect(leftTable.getX()+5f*scalingFactor, themeLabel.getY() + themeLabel.getHeight()/2.0f, leftTable.getWidth() - leftTable.getX() + 10f*scalingFactor, ((settingsLabel.getY() + settingsLabel.getHeight()) - themeLabel.getY() + themeLabel.getHeight()),10);
        // joining players
        myShapeRenderer.roundedRect(rightTable.getX(), player3Label.getY() + connectedPlayersLabel.getHeight()/3.0f, rightTable.getWidth() - leftTable.getX() + 5f*scalingFactor, ((txfName.getY() + txfName.getHeight()/2 - 5f*scalingFactor) + (txfName.getHeight()+25f*scalingFactor) - player3Label.getY() - connectedPlayersLabel.getHeight()/3.0f),10);
        // game code
        myShapeRenderer.roundedRect(rightTable.getX() + gameCode.getX() - 10f*scalingFactor, rightTable.getY() + gameCode.getY() - 10f*scalingFactor, gameCode.getWidth()+20f*scalingFactor, gameCode.getHeight()+20f*scalingFactor, 10);
        myShapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        myShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // joining players
        if(!player1Label.getText().toString().equals("")){
            myShapeRenderer.setColor(0.2f, 0.8f, 0.2f, 1);
            myShapeRenderer.roundedRect(rightTable.getX()+0.4f*xWidth, player1Label.getY() + player1Label.getHeight()*0.65f, rightTable.getWidth() - 0.8f*xWidth, player1Label.getHeight()*1.5f, 10);
        }
        if(!player2Label.getText().toString().equals("")) {
            myShapeRenderer.setColor(0.8f, 0.2f, 0.2f, 1);
            myShapeRenderer.roundedRect(rightTable.getX() + 0.4f * xWidth, player2Label.getY() + player1Label.getHeight() * 0.65f, rightTable.getWidth() - 0.8f * xWidth, player1Label.getHeight() * 1.5f, 10);
        }
        if (!player3Label.getText().toString().equals("")) {
            myShapeRenderer.setColor(0.2f, 0.2f, 0.8f, 1);
            myShapeRenderer.roundedRect(rightTable.getX() + 0.4f * xWidth, player3Label.getY() + player1Label.getHeight() * 0.65f, rightTable.getWidth() - 0.8f * xWidth, player1Label.getHeight() * 1.5f, 10);
        }
        myShapeRenderer.end();
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
        dispose();
    }

    @Override
    public void dispose() {
        bgImageTexture.dispose();
        myFontTexture.dispose();
        stage.dispose();
        skin.dispose();
        startImageTexture.dispose();
    }
}