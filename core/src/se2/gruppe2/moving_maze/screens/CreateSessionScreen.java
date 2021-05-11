package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import se2.gruppe2.moving_maze.MovingMazeGame;
import se2.gruppe2.moving_maze.helperclasses.MyShapeRenderer;

public class CreateSessionScreen implements Screen {

    final MovingMazeGame game;
    private OrthographicCamera camera;

    private ShapeRenderer shapeRenderer;
    private MyShapeRenderer myShapeRenderer;
    private Stage stage;
    private Skin skin;

    private String headingText;
    private TextField txfName;

    // textures and views
    private Texture bgImageTexture;
    private TextureRegion bgTextureRegion;

    private Texture heading;
    private BitmapFont headingFont;
    private Texture startImageTexture;

    // measures
    private float myScreenHeight;
    private float myScreenWidth;
    private float yHeight;
    private float xWidth;

    // Tables
    private Table table;
    private Table leftTable;
    private Table rightTable;
    private Label lblCreateLobbyHeading;
    private Label nameLabel;
    private Label settingsLabel;
    private Label difficultyLabel;
    private Label cheatingAllowedLabel;
    private Label numberOfCardsLabel;
    private Slider difficultySlider;
    private Slider numberOfCardsSlider;
    private TextButton cheatingAllowedButton;
    private Label connectedPlayersLabel;
    private Label player1Label;
    private Label player2Label;
    private Label player3Label;
    private Label gameCode;
    private Image startIcon;
    private Label difficultyValueLabel;
    private Label numOfCardsValueLabel;

    private Boolean gameReady;
    private Boolean cheatingAllowed;

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

        shapeRenderer = new ShapeRenderer();
        myShapeRenderer = new MyShapeRenderer();

        myScreenHeight = Gdx.graphics.getHeight();
        myScreenWidth = Gdx.graphics.getWidth();
        yHeight = Gdx.graphics.getHeight() / 15f;
        xWidth = Gdx.graphics.getWidth() / 25f;

        gameReady = true;
        cheatingAllowed = true;


        heading = new Texture(Gdx.files.internal("ui/nunito.png"));
        heading.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        headingFont = new BitmapFont(Gdx.files.internal("ui/nunito.fnt"), new TextureRegion(heading), false);
        headingFont.getData().setScale(2f);
        headingText = "Create Lobby";

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);


        setUpTables();
        setUpActorListeners();

        stage.addActor(table);

        // Debugging
        stage.setDebugAll(false);
    }

    public void setUpTables(){
        // ---------- Table Layout ----------

        // --- Left Table ---
        leftTable = new Table();
        leftTable.defaults().padTop(0.5f* xWidth);
        leftTable.columnDefaults(0).width(200);

        // --- LT - 1st Row (Name) ---
        nameLabel = new Label("Name:", skin);
        nameLabel.setAlignment(Align.left);
        nameLabel.setFontScale(2.5f);
        leftTable.add(nameLabel).pad(0.5f* xWidth, 0.2f* xWidth, 0, 0).align(Align.left);

        txfName = new TextField("Martin", skin);
        TextField.TextFieldStyle textFieldStyle = skin.get(TextField.TextFieldStyle.class);
        textFieldStyle.font.getData().scale(1.5f);
        txfName.setStyle(textFieldStyle);
        leftTable.add(txfName).padTop(0.5f* xWidth).maxHeight(100).height(80).expandX().fillX();


        // --- LT - 2nd Row (Lobby Settings) ---
        leftTable.row();
        settingsLabel = new Label("Lobby Settings:", skin);
        settingsLabel.setAlignment(Align.left);
        settingsLabel.setFontScale(1.8f);
        leftTable.add(settingsLabel).colspan(3).pad(1.0f* xWidth, 0.2f* xWidth, 0, 0).align(Align.left).top();

        // --- LT - 3rd Row (Difficulty) ---
        leftTable.row();
        difficultyLabel = new Label("Difficulty", skin);
        difficultyLabel.setAlignment(Align.left);
        difficultyLabel.setFontScale(2.0f);
        leftTable.add(difficultyLabel).padLeft(0.2f* xWidth).align(Align.left);

        difficultySlider = new Slider(1, 3, 1, false, skin);
        difficultySlider.setValue(2);
        Container<Slider> difficultySliderContainer = new Container<Slider>(difficultySlider);
        difficultySliderContainer.setTransform(true);
        difficultySliderContainer.size(100, 50);
        difficultySliderContainer.setOrigin((difficultySliderContainer.getWidth()+ 100)/2,
                (difficultySliderContainer.getHeight()+60)/2);
        difficultySliderContainer.setScale(3);
        leftTable.add(difficultySliderContainer).expandX().fillX();

        difficultyValueLabel = new Label(String.valueOf(Math.round(difficultySlider.getValue())), skin);
        difficultyValueLabel.setFontScale(2.0f);
        leftTable.add(difficultyValueLabel).align(Align.center).padRight(30.0f);


        // --- LT - 4th Row (# of cards) ---
        leftTable.row();
        numberOfCardsLabel = new Label("# of Cards", skin);
        numberOfCardsLabel.setAlignment(Align.left);
        numberOfCardsLabel.setFontScale(2.0f);
        leftTable.add(numberOfCardsLabel).padLeft(0.2f* xWidth).align(Align.left);

        numberOfCardsSlider = new Slider(1, 6, 1, false, skin);
        numberOfCardsSlider.setValue(3);
        Container<Slider> numberOfCardsSliderContainer = new Container<Slider>(numberOfCardsSlider);
        numberOfCardsSliderContainer.setTransform(true);
        numberOfCardsSliderContainer.size(100, 50);
        numberOfCardsSliderContainer.setOrigin((numberOfCardsSliderContainer.getWidth()+ 100)/2,
                (numberOfCardsSliderContainer.getHeight()+60)/2);
        numberOfCardsSliderContainer.setScale(3);
        leftTable.add(numberOfCardsSliderContainer).expandX().fillX();


        numOfCardsValueLabel = new Label(String.valueOf(Math.round(numberOfCardsSlider.getValue())), skin);
        numOfCardsValueLabel.setFontScale(2.0f);
        leftTable.add(numOfCardsValueLabel).align(Align.center).padRight(30.0f);


        // --- LT - 5th Row (Cheating) ---
        leftTable.row();
        cheatingAllowedLabel = new Label("Cheating", skin);
        cheatingAllowedLabel.setAlignment(Align.left);
        cheatingAllowedLabel.setFontScale(2.0f);
        leftTable.add(cheatingAllowedLabel).padLeft(0.2f* xWidth).align(Align.left);

        cheatingAllowedButton = new TextButton("Allowed", skin);
        cheatingAllowedButton.getLabel().setFontScale(2.0f);
        Container<TextButton> cheatingAllowedButtonContainer = new Container<TextButton>(cheatingAllowedButton);
        cheatingAllowedButtonContainer.setTransform(true);
        cheatingAllowedButtonContainer.size(225
                ,cheatingAllowedButton.getHeight()*0.8f);
        leftTable.add(cheatingAllowedButtonContainer).expandX().fillX();


        // --- LT - 6th Row (Theme) ---
        leftTable.row();
        Label themeLabel = new Label("Theme", skin);
        themeLabel.setAlignment(Align.left);
        themeLabel.setFontScale(2.0f);
        leftTable.add(themeLabel).padLeft(0.2f*xWidth).align(Align.left);

        TextButton themeButton = new TextButton("Original", skin);
        themeButton.getLabel().setFontScale(2.0f);
        themeButton.setDisabled(true);
        themeButton.setTouchable(Touchable.disabled);
        themeButton.setColor(0.5f, 0.5f,0.5f,1f);
        Container<TextButton> themeButtonContainer = new Container<>(themeButton);
        themeButtonContainer.setTransform(true);
        themeButtonContainer.size(225, themeButton.getHeight()*0.8f);
        leftTable.add(themeButtonContainer).expandX().fillX();




        // --- Right Table
        rightTable = new Table();
        rightTable.defaults().padTop(0.75f* xWidth);

        // --- RT - 1st Row (Connected Players) ---
        connectedPlayersLabel = new Label("Connected Players:", skin);
        connectedPlayersLabel.setAlignment(Align.left);
        connectedPlayersLabel.setFontScale(1.8f);
        rightTable.add(connectedPlayersLabel).colspan(2).pad(yHeight, 0.2f* xWidth, 0, 0).align(Align.left).top().expandX();

        // --- RT - 2nd Row (Player 1) ---
        rightTable.row();
        player1Label = new Label("Florian", skin);
        player1Label.setAlignment(Align.left);
        player1Label.setFontScale(2.0f);
        rightTable.add(player1Label).padLeft(0.8f* xWidth).align(Align.left);

        // --- RT - 3rd Row (Player 2) ---
        rightTable.row();
        player2Label = new Label("Alexandra", skin);
        player2Label.setAlignment(Align.left);
        player2Label.setFontScale(2.0f);
        rightTable.add(player2Label).padLeft(0.8f* xWidth).align(Align.left);

        // --- RT - 4th Row (Player 3) ---
        rightTable.row();
        player3Label = new Label("Andi", skin);
        player3Label.setAlignment(Align.left);
        player3Label.setFontScale(2.0f);
        rightTable.add(player3Label).padLeft(0.8f* xWidth).align(Align.left);


        // --- RT - 5th Row (Code & Start) ---
        rightTable.row();
        gameCode = new Label("XYABCD", skin);
        gameCode.setAlignment(Align.center);
        gameCode.setFontScale(3.0f);
        rightTable.add(gameCode).padTop(50F).padLeft(10F).expandY().center();

        Sprite startIconSprite = new Sprite(startImageTexture);
        startIcon = new Image(new SpriteDrawable(startIconSprite));
        startIcon.setOrigin(startIcon.getOriginX()+startIcon.getWidth()/2, startIcon.getOriginY()+startIcon.getHeight()/2);
        startIcon.setScale(1.5f);
        stage.addActor(startIcon);
        rightTable.add(startIcon).padTop(50F).expandY().center();



        // --- Table ---
        table = new Table();
        table.defaults().pad(5F).maxHeight(myScreenHeight *0.9f);
        table.setFillParent(true);

        // --- Table - 1st Row ---
        lblCreateLobbyHeading = new Label("CREATE LOBBY", new Label.LabelStyle(headingFont, Color.WHITE));
        lblCreateLobbyHeading.setFontScale(2.0f);
        lblCreateLobbyHeading.setAlignment(Align.center);
        table.add(lblCreateLobbyHeading).colspan(2).fillX();

        // --- Table - 2nd Row ---
        table.row();
        table.add(leftTable).expand().fill().width(myScreenWidth /2+40);
        table.add(rightTable).expand().fill().width(myScreenWidth /2-80);

        // --- Table - Last Row ---
        table.row().height(30);
        Label emptyLabel = new Label("", skin);
        table.add(emptyLabel).colspan(2);


        // ---------- End Tables ----------
    }

    private void setUpActorListeners() {

        difficultySlider.addListener(new ChangeListener(){
            public void changed (ChangeEvent event, Actor actor) {
                difficultyValueLabel.setText((int) difficultySlider.getValue());
            }
        });

        numberOfCardsSlider.addListener(new ChangeListener(){
            public void changed (ChangeEvent event, Actor actor) {
                numOfCardsValueLabel.setText((int) numberOfCardsSlider.getValue());
            }
        });

        startIcon.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameReady){
                System.out.println("START GAME");
                }
            }
        });

        cheatingAllowedButton.addListener(new ClickListener() {
           public void clicked(InputEvent event, float x, float y) {
               cheatingAllowed ^= true;
               if (cheatingAllowed) {
                   cheatingAllowedButton.setText("Allowed");
               } else {
                   cheatingAllowedButton.setText("Not Allowed");
               }
           }
        });

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


    }

    private void drawShapes(){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        myShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        myShapeRenderer.setColor(0.5f,0.5f,0.5f,0.8f);
        // name
        myShapeRenderer.roundedRect(leftTable.getX()+5, txfName.getY() + txfName.getHeight()/2 - 10, leftTable.getWidth() - leftTable.getX() + 10, txfName.getHeight()+30,10);
        // lobby settings
        myShapeRenderer.roundedRect(leftTable.getX()+5, leftTable.getY(), leftTable.getWidth() - leftTable.getX() + 10,((settingsLabel.getY() + settingsLabel.getHeight()+60) - leftTable.getY()),10);
        // joining players
        myShapeRenderer.roundedRect(rightTable.getX()+3, player3Label.getY() + player1Label.getHeight()/3, rightTable.getWidth() - leftTable.getX(),((connectedPlayersLabel.getY() + connectedPlayersLabel.getHeight()+ 2*player3Label.getHeight()/3) - player3Label.getY()),10);
        // game code
        myShapeRenderer.roundedRect(rightTable.getX()+10, gameCode.getY()+gameCode.getHeight()/2, gameCode.getWidth()+30, gameCode.getHeight()+20, 10);

        myShapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        myShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // joining players
        myShapeRenderer.setColor(0.2f, 0.8f, 0.2f, 1);
        myShapeRenderer.roundedRect(rightTable.getX()+0.4f*xWidth, player1Label.getY() + player1Label.getHeight()*0.65f, rightTable.getWidth() - 0.8f*xWidth, player1Label.getHeight()*1.5f, 10);
        myShapeRenderer.setColor(0.8f, 0.2f, 0.2f, 1);
        myShapeRenderer.roundedRect(rightTable.getX()+0.4f*xWidth, player2Label.getY() + player1Label.getHeight()*0.65f, rightTable.getWidth() - 0.8f*xWidth, player1Label.getHeight()*1.5f, 10);
        myShapeRenderer.setColor(0.2f, 0.2f, 0.8f, 1);
        myShapeRenderer.roundedRect(rightTable.getX()+0.4f*xWidth, player3Label.getY() + player1Label.getHeight()*0.65f, rightTable.getWidth() - 0.8f*xWidth, player1Label.getHeight()*1.5f, 10);
        //myShapeRenderer.setColor(0.8f, 0.8f, 0.2f, 1);
        //myShapeRenderer.circle(10.75f* xWidth, 10.25f* yHeight, 2* xWidth /3);
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

    }

    @Override
    public void dispose() {

    }
}