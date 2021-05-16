package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import se2.gruppe2.moving_maze.MovingMazeGame;

public class RuleScreen implements Screen {

    final MovingMazeGame game;
    OrthographicCamera camera;

    //ui stuff
    private Stage stage;
    private Texture backTexture;
    private TextureRegion textureRegion;
    private TextureRegionDrawable textureRegionDrawable;
    private ImageButton backButton;
    private ScrollPane scrollPane;
    private Table table;
    private Skin skin;

    //Textures and views
    private Texture bgImageTexture;
    private TextureRegion bgTextureRegion;

    //Texts
    private String txtAblauf;
    private String txtSpielende;
    private String txtSpielfigurZiehen;
    private String txtVerschieben;
    private String txtZiel;

    public RuleScreen(final MovingMazeGame game) {
        this.game = game;
        camera = MovingMazeGame.getStandardizedCamera();
    }

    @Override
    public void show() {
        //instantiate background textures
        bgImageTexture = new Texture(Gdx.files.internal("ui/bg_moss.jpeg"));
        bgTextureRegion = new TextureRegion(bgImageTexture);

        //Buttons
        backTexture = new Texture(Gdx.files.internal("ui/buttons/backButton.png"));
        textureRegion = new TextureRegion(backTexture);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion);
        backButton = new ImageButton(textureRegionDrawable);
        backButton.setPosition(20f, camera.viewportHeight - 20f - backButton.getHeight()/2f, Align.left);

        stage = new Stage(new ScreenViewport());
        stage.addActor(backButton);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        Gdx.input.setInputProcessor(stage);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.mainMenuScreen);

            }
        });

        setUpTable();
        scrollPane = new ScrollPane(table, skin);
        scrollPane.setWidth(Gdx.graphics.getWidth());
        scrollPane.setHeight(Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/8f);
        stage.addActor(scrollPane);
    }

    public void setUpTable() {
        table = new Table();
        table.setWidth(Gdx.graphics.getWidth());
        table.defaults().padTop(25f);

        txtZiel = Gdx.files.internal("klsdfj").readString();

        var label = new Label("Test:", skin);
        label.setAlignment(Align.left);
        label.setFontScale(2.5f);
        table.add(label).width(Gdx.graphics.getWidth()).height(25f).row();
        var label2 = new Label("This is the first test of the lineksdjflsjflö lksdjflöjsdf ldfk sldf", skin);
        label2.setAlignment(Align.left);
        label2.setFontScale(2.5f);
        table.add(label2).width(Gdx.graphics.getWidth()).height(25f).row();
        var label3 = new Label("This is the first test of the lineksdjflsjflö lksdjflöjsdf ldfk sldf", skin);
        label3.setAlignment(Align.left);
        label3.setFontScale(2.5f);
        table.add(label3).width(Gdx.graphics.getWidth()).height(25f).row();
        var label4 = new Label("This is the first test of the lineksdjflsjflö lksdjflöjsdf ldfk sldf", skin);
        label4.setAlignment(Align.left);
        label4.setFontScale(2.5f);
        table.add(label4).width(Gdx.graphics.getWidth()).height(25f).row();
        var label5 = new Label("This is the first test of the lineksdjflsjflö lksdjflöjsdf ldfk sldf", skin);
        label5.setAlignment(Align.left);
        label5.setFontScale(2.5f);
        table.add(label5).width(Gdx.graphics.getWidth()).height(25f).row();
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
