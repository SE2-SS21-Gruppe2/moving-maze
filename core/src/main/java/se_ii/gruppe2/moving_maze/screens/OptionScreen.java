package se_ii.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import se_ii.gruppe2.moving_maze.MovingMazeGame;
import se_ii.gruppe2.moving_maze.audio.AudioManager;

public class OptionScreen implements Screen {

    private final MovingMazeGame game;
    private final SpriteBatch batch;
    private OrthographicCamera camera;

    private Texture bgImageTexture;
    private TextureRegion bgTextureRegion;

    private Label title;

    private Label soundLabel;
    private Label vibrationLabel;
    private Label rotateScreenLabel;
    private Button soundButton;
    private Button vibrationButton;
    private Button rotateScreenButton;

    private Texture backTexture;
    private TextureRegion textureRegion;
    private TextureRegionDrawable textureRegionDrawable;
    private ImageButton backButton;

    private Skin skin;
    private Stage stage;

    private Table table1;

    //settings
    private static boolean playMusic = false;
    private static boolean rotateTileByGyro = true;


    private final Drawable soundOnDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/sound_on.png"))));
    private final Drawable soundOffDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/sound_off.png"))));
    private final Drawable vibrateOnDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/vibrate_on.png"))));
    private final Drawable vibrateOffDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/vibrate_off.png"))));
    private final Drawable rotateOnDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/rotate_on.png"))));
    private final Drawable rotateOffDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/rotate_off.png"))));

    private final AudioManager audioManager = AudioManager.getInstance();

    public OptionScreen(final MovingMazeGame game) {
        this.game = game;
        this.batch = game.getBatch();
    }

    @Override
    public void show() {
        camera = MovingMazeGame.getStandardizedCamera();

        this.stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        Gdx.input.setInputProcessor(stage);

        title = new Label("OPTIONS", skin);

        setTitleCoordinates();

        table1 = new Table();

        soundLabel = new Label("Sound", skin);
        soundLabel.setFontScale(3f);
        vibrationLabel = new Label("Vibration", skin);
        vibrationLabel.setFontScale(3f);
        rotateScreenLabel = new Label("Tile control", skin);
        rotateScreenLabel.setFontScale(3f);

        //Button
        backTexture = new Texture(Gdx.files.internal("ui/buttons/backButton.png"));
        textureRegion = new TextureRegion(backTexture);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion);
        backButton = new ImageButton(textureRegionDrawable);
        backButton.setPosition(20f, camera.viewportHeight - 100f, Align.left);


        soundButton = new ImageButton(soundOnDrawable, soundOffDrawable, soundOffDrawable);
        vibrationButton = new ImageButton(vibrateOnDrawable, vibrateOffDrawable, vibrateOffDrawable);
        rotateScreenButton = new ImageButton(rotateOnDrawable, rotateOffDrawable, rotateOffDrawable);

        soundButton.setChecked(true);

        initTable1();
        setTable1();
        setUpButtonListeners();

        stage.addActor(title);
        stage.addActor(table1);

        stage.addActor(backButton);
        stage.getCamera().position.set(MovingMazeGame.WIDTH / 2.0f, MovingMazeGame.HEIGHT / 2.0f, 0);

        bgImageTexture = new Texture(Gdx.files.internal("ui/bg_moss.jpeg")); // background image
        bgTextureRegion = new TextureRegion(bgImageTexture);


    }


    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bgTextureRegion, 0, 0);
        batch.end();
        stage.draw();
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

    private void setTitleCoordinates() {

        title.setAlignment(Align.center);
        title.setFontScale(4f);
        title.setX(camera.viewportWidth / 2f - title.getWidth() / 2f);
        title.setY(camera.viewportHeight - title.getHeight() - 80f);
    }

    private void setTable1() {

        table1.setPosition(Gdx.graphics.getWidth() / 2f - table1.getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f + table1.getHeight() / 2f, Align.center);

    }


    private void initTable1() {

        table1.align(Align.center);

        table1.add(soundLabel).size(300, 100).center();
        table1.add(soundButton).size(100, 100).center();
        table1.row();

        table1.add(vibrationLabel).size(300, 100).center();
        table1.add(vibrationButton).size(100, 100).center();
        table1.row();

        table1.add(rotateScreenLabel).size(300, 100).center();
        table1.add(rotateScreenButton).size(100, 100).center();

        table1.pad(150);
        //table1.padLeft(130);

    }

    private void setUpButtonListeners() {

        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playMusic = !playMusic;

                if (playMusic) {
                    audioManager.getBackgroundMusic().play(0.4f);
                    audioManager.getBackgroundMusic().loop();

                } else {
                    audioManager.getBackgroundMusic().stop();
                }
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getMainMenuScreen());
            }
        });

        rotateScreenButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rotateTileByGyro = !rotateTileByGyro;
            }
        });
    }

    //Getter
    public static boolean rotateTileByGyro() {
        return rotateTileByGyro;
    }
}

