package se_ii.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
import se_ii.gruppe2.moving_maze.helperclasses.TextureLoader;
import se_ii.gruppe2.moving_maze.helperclasses.TextureType;

public class OptionScreen implements Screen {

    private final MovingMazeGame game;
    private final SpriteBatch batch;
    private OrthographicCamera camera;

    private Label title;

    private Label soundLabel;
    private Label vibrationLabel;
    private Label rotateScreenLabel;
    private Button soundButton;
    private Button vibrationButton;
    private Button tileRotationButton;

    private Texture backTexture;
    private TextureRegion textureRegion;
    private TextureRegionDrawable textureRegionDrawable;
    private ImageButton backButton;

    private Skin skin;
    private Stage stage;

    private Table table1;

    //settings
    private boolean playMusic;
    private boolean rotateTileByGyro;
    private boolean vibratePhone;

    private float scalingFactor;


    private final Drawable soundOnDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/sound_on.png"))));
    private final Drawable soundOffDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/sound_off.png"))));
    private final Drawable vibrateOnDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/vibrate_on.png"))));
    private final Drawable vibrateOffDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/vibrate_off.png"))));
    private final Drawable rotateOnDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/rotate_on.png"))));
    private final Drawable rotateOffDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/rotate_off.png"))));


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
        title.setColor(Color.GOLD);
        setTitleCoordinates();

        table1 = new Table();
        scalingFactor = Gdx.graphics.getWidth()/1280f;

        soundLabel = new Label("Sound", skin);
        soundLabel.setFontScale(3f);
        soundLabel.setColor(Color.GOLD);
        vibrationLabel = new Label("Vibration", skin);
        vibrationLabel.setFontScale(3f);
        vibrationLabel.setColor(Color.GOLD);
        rotateScreenLabel = new Label("Tile control", skin);
        rotateScreenLabel.setFontScale(3f);
        rotateScreenLabel.setColor(Color.GOLD);

        //Button
        backTexture = new Texture(Gdx.files.internal("ui/buttons/backButton.png"));
        textureRegion = new TextureRegion(backTexture);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion);
        textureRegionDrawable.setMinHeight(150f);
        textureRegionDrawable.setMinWidth(300f);
        backButton = new ImageButton(textureRegionDrawable);
        backButton.setPosition(80f * scalingFactor, Gdx.graphics.getHeight() - 57f * scalingFactor - backButton.getHeight());

        soundOnDrawable.setMinWidth(150f);
        soundOnDrawable.setMinHeight(150f);

        soundOffDrawable.setMinHeight(150f);
        soundOffDrawable.setMinWidth(150f);

        vibrateOffDrawable.setMinWidth(150f);
        vibrateOffDrawable.setMinHeight(150f);

        rotateOnDrawable.setMinWidth(150f);
        rotateOnDrawable.setMinHeight(150f);

        rotateOffDrawable.setMinWidth(150f);
        rotateOffDrawable.setMinHeight(150f);

        soundButton = new ImageButton(soundOffDrawable, soundOnDrawable, soundOnDrawable);
        vibrationButton = new ImageButton(vibrateOffDrawable, vibrateOnDrawable, vibrateOnDrawable);
        tileRotationButton = new ImageButton(rotateOffDrawable, rotateOnDrawable, rotateOnDrawable);

        playMusic = game.getPreferences().getBoolean("soundOn", true);
        vibratePhone = game.getPreferences().getBoolean("vibrationOn", true);
        rotateTileByGyro = game.getPreferences().getBoolean("rotateWithSensorOn", true);

        soundButton.setChecked(playMusic);
        vibrationButton.setChecked(vibratePhone);
        tileRotationButton.setChecked(rotateTileByGyro);

        if (playMusic) {
            AudioManager.playBackgroundMusic();
        }

        initTable1();
        setTable1();
        setUpButtonListeners();

        stage.addActor(title);
        stage.addActor(table1);

        stage.addActor(backButton);
        stage.getCamera().position.set(MovingMazeGame.WIDTH / 2.0f, MovingMazeGame.HEIGHT / 2.0f, 0);


    }


    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(TextureLoader.getSpriteByTexturePath("ui/bg_moss.jpeg", TextureType.BACKGROUND).getTexture(), 0f, 0f);
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
        table1.add(soundLabel).size(400, 200).center();
        table1.add(soundButton).size(400, 200).center();
        table1.row();

        table1.add(vibrationLabel).size(400, 200).center();
        table1.add(vibrationButton).size(400, 200).center();
        table1.row();


        table1.add(rotateScreenLabel).size(400, 200).center();
        table1.add(tileRotationButton).size(400, 200).center();

        table1.pad(150);
        //table1.padLeft(130);

    }

    private void setUpButtonListeners() {

        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AudioManager.playButtonClick();
                playMusic = soundButton.isChecked();
                game.getPreferences().putBoolean("soundOn", playMusic);
                game.getPreferences().flush();

                if (playMusic) {
                    AudioManager.playBackgroundMusic();

                } else {
                    AudioManager.stopBackgroundMusic();
                }
            }
        });

        tileRotationButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AudioManager.playButtonClick();
                rotateTileByGyro = tileRotationButton.isChecked();
                game.getPreferences().putBoolean("rotateWithSensorOn", rotateTileByGyro);
                game.getPreferences().flush();
            }
        });

        vibrationButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AudioManager.playButtonClick();
                vibratePhone = vibrationButton.isChecked();
                game.getPreferences().putBoolean("vibrationOn", vibratePhone);
                game.getPreferences().flush();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AudioManager.playButtonClick();
                game.setScreen(game.getMainMenuScreen());
            }
        });
    }
}

