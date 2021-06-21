package se_ii.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import se_ii.gruppe2.moving_maze.MovingMazeGame;
import se_ii.gruppe2.moving_maze.helperclasses.TextureLoader;
import se_ii.gruppe2.moving_maze.helperclasses.TextureType;
import java.util.ArrayList;
import se_ii.gruppe2.moving_maze.audio.AudioManager;

public class RuleScreen implements Screen {

    final MovingMazeGame game;
    private OrthographicCamera camera;

    //ui stuff
    private Stage stage;
    private Texture backTexture;
    private TextureRegion textureRegion;
    private TextureRegionDrawable textureRegionDrawable;
    private ImageButton backButton;
    private ScrollPane scrollPane;
    private Table table;
    private Table outertable;
    private Skin skin;
    private SpriteBatch batch;

    //Textures and views
    private Texture bgImageTexture;
    private TextureRegion bgTextureRegion;
    private Texture background;
    private Label premadeLabel;
    private ArrayList<Label> labels;
    private Table scrollTable ;

    private float scalingFactor;

    public RuleScreen(final MovingMazeGame game) {
        this.game = game;
        batch = game.getBatch();

    }

    @Override
    public void show() {

        camera = MovingMazeGame.getStandardizedCamera();

        scalingFactor = Gdx.graphics.getWidth()/1280f;


        background = new Texture(Gdx.files.internal("rules/background.png"));


        //Button
        backTexture = new Texture(Gdx.files.internal("ui/buttons/backButton.png"));
        textureRegion = new TextureRegion(backTexture);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion);
        textureRegionDrawable.setMinHeight(150f);
        textureRegionDrawable.setMinWidth(300f);
        backButton = new ImageButton(textureRegionDrawable);
        backButton.setPosition(80f * scalingFactor, Gdx.graphics.getHeight() - 57f * scalingFactor - backButton.getHeight());
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));


        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        labels = new ArrayList<>();
        setUpTable();



        Table container = new Table();
        stage.addActor(container);
        container.setFillParent(false);
        container.setWidth(Gdx.graphics.getWidth()/1.7f);
        container.setHeight(Gdx.graphics.getHeight()/1.2f);
        container.setPosition(Gdx.graphics.getWidth()/2f - container.getWidth()/2f,Gdx.graphics.getHeight()/2f - container.getHeight()/2f);


        Table table = new Table();

        final ScrollPane scroll = new ScrollPane(table, skin);
        scroll.setColor(Color.NAVY);
        scroll.setScrollingDisabled(true,false);
        table.row();table.row();table.row();
        table.pad(10).defaults().expandX().space(4);
        for (int i = 0; i < labels.size(); i++) {
            table.row();
            table.row();



            Label label = labels.get(i);
            label.setWrap(true);
            table.add(label).width(Gdx.graphics.getWidth()/1.8f);
            table.row();
            table.row();
        }
        table.row();
        table.row();
        table.row();
        table.row();

        container.add(scroll).expand().fill();


        stage.addActor(backButton);


        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                AudioManager.playButtonClick();
                game.setScreen(game.getMainMenuScreen());

            }
        });
        stage.getCamera().position.set(MovingMazeGame.WIDTH / 2.0f, MovingMazeGame.HEIGHT / 2.0f, 0);

    }

    public void setUpTable() {
        labels = new ArrayList<>();

        var lblZiel = new Label("Ziel:", skin);
        lblZiel.setAlignment(Align.center);
        lblZiel.setFontScale(3f);
        lblZiel.setColor(Color.GOLD);
        labels.add(lblZiel);

        String txtZiel = Gdx.files.internal("rules/ziel.txt").readString();
        var ziel = new Label(txtZiel, skin);
        ziel.setAlignment(Align.left);
        ziel.setFontScale(2f);
        labels.add(ziel);

        var lblAblauf = new Label("Ablauf:", skin);
        lblAblauf.setAlignment(Align.center);
        lblAblauf.setFontScale(3f);
        lblAblauf.setColor(Color.GOLD);
        labels.add(lblAblauf);

        //Texts
        String txtAblauf = Gdx.files.internal("rules/ablauf.txt").readString();
        var ablauf = new Label(txtAblauf, skin);
        ablauf.setAlignment(Align.left);
        ablauf.setFontScale(2f);
        labels.add(ablauf);


        var lblVerschieben = new Label("1. Gaenge verschieben:", skin);
        lblVerschieben.setAlignment(Align.center);
        lblVerschieben.setFontScale(3f);
        lblVerschieben.setColor(Color.GOLD);
        labels.add(lblVerschieben);

        String txtVerschieben = Gdx.files.internal("rules/verschieben.txt").readString();
        var verschieben = new Label(txtVerschieben, skin);
        verschieben.setAlignment(Align.left);
        verschieben.setFontScale(2f);
        labels.add(verschieben);

        var lblSpielfigurZiehen = new Label("2. Spielfigur ziehen:", skin);
        lblSpielfigurZiehen.setAlignment(Align.center);
        lblSpielfigurZiehen.setFontScale(3f);
        lblSpielfigurZiehen.setColor(Color.GOLD);
        labels.add(lblSpielfigurZiehen);

        String txtSpielfigurZiehen = Gdx.files.internal("rules/spielfigur_ziehen.txt").readString();
        var spielfigurZiehen = new Label(txtSpielfigurZiehen, skin);
        spielfigurZiehen.setAlignment(Align.left);
        spielfigurZiehen.setFontScale(2f);
        labels.add(spielfigurZiehen);

        var lblSpielende = new Label("Spielende:", skin);
        lblSpielende.setAlignment(Align.center);
        lblSpielende.setFontScale(3f);
        lblSpielende.setColor(Color.GOLD);
        labels.add(lblSpielende);

        String txtSpielende = Gdx.files.internal("rules/spielende.txt").readString();
        var spielende = new Label(txtSpielende, skin);
        spielende.setAlignment(Align.left);
        spielende.setFontScale(2f);
        labels.add(spielende);

    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(TextureLoader.getSpriteByTexturePath("ui/bg_moss.jpeg", TextureType.BACKGROUND).getTexture(), 0f, 0f);
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
        stage.dispose();
    }
}
