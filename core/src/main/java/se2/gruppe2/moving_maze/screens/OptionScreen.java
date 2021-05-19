
package se2.gruppe2.moving_maze.screens;

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
import se2.gruppe2.moving_maze.MovingMazeGame;
import se2.gruppe2.moving_maze.audio.AudioManager;

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

    private Button backButton;

    private Skin skin ;
    private Stage stage;

    private Table table1;

    private int musicInc = 1;


    private final Drawable soundOnDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/sound_on.png"))));
    private final Drawable soundOffDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/sound_off.png"))));
    private final Drawable vibrateOnDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/vibrate_on.png"))));
    private final Drawable vibrateOffDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/vibrate_off.png"))));
    private final Drawable rotateOnDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/rotate_on.png"))));
    private final Drawable rotateOffDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/rotate_off.png"))));

    private AudioManager audioManager = AudioManager.getInstance();

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

        title = new Label("OPTIONS",skin);

        setTitleCoordinates();

        table1 = new Table();

        soundLabel = new Label("Sound",skin);
        vibrationLabel = new Label("Vibration",skin);
        rotateScreenLabel = new Label("Rotate the Screen", skin);

        backButton = new TextButton("BACK",skin);
        backButton.setPosition( camera.viewportWidth/4f, camera.viewportHeight - title.getHeight()-80f, Align.left);

        soundButton = new ImageButton(soundOnDrawable,soundOffDrawable,soundOffDrawable);
        vibrationButton = new ImageButton(vibrateOnDrawable,vibrateOffDrawable,vibrateOffDrawable);
        rotateScreenButton = new ImageButton(rotateOnDrawable,rotateOffDrawable,rotateOffDrawable);

        soundButton.setChecked(true);

        initTable1();
        setTable1();
        setUpButtonListeners();

        stage.addActor(title);
        stage.addActor(table1);

        stage.addActor(backButton);
        stage.getCamera().position.set(MovingMazeGame.WIDTH/2.0f, MovingMazeGame.HEIGHT/2.0f, 0);

        bgImageTexture = new Texture(Gdx.files.internal("ui/bg_moss.jpeg")); // background image
        bgTextureRegion = new TextureRegion(bgImageTexture);


    }




    @Override
    public void render(float delta) {

        ScreenUtils.clear(0,0,0,1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bgTextureRegion, 0, 0);
        batch.end();
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

    private void setTitleCoordinates(){

        title.setAlignment(Align.center);
        title.setSize(20f,10f);
        title.setX( camera.viewportWidth/2f - title.getWidth()/2f );
        title.setY(camera.viewportHeight - title.getHeight()-80f);
    }

    private void setTable1(){

        table1.align(Align.center);
        table1.setPosition(MovingMazeGame.WIDTH/2.5f,MovingMazeGame.HEIGHT/2.5f,Align.center);
        table1.padBottom(290);
    }


    private void initTable1(){

        table1.align(Align.center);

        table1.add(soundLabel).size(170,70).center();
        table1.add(soundButton).size(60,60).center();

        table1.row();
        table1.add(vibrationLabel).size(170,70).center();
        table1.add(vibrationButton).size(60,60).center();

        table1.row();
        table1.add(rotateScreenLabel).size(170,70).center();
        table1.add(rotateScreenButton).size(50,50).center();

        table1.padTop(180);
        table1.padLeft(130);

    }

    private void setUpButtonListeners(){

        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                musicInc++;

                if (musicInc % 2 == 0){

                    audioManager.getBackgroundMusic().play(0.4f);
                    audioManager.getBackgroundMusic().loop();

                }else {

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
    }






}

