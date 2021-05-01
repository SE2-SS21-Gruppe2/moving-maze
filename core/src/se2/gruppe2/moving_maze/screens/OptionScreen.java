
package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

public class OptionScreen implements Screen {

    final MovingMazeGame game;
    public OrthographicCamera camera;

    public Texture bgImageTexture;
    public TextureRegion bgTextureRegion;

    public Label title;

    public Label soundLabel, vibrationLabel, rotateScreenLabel; // dummy labels
    public Button soundButton,vibrationButton,rotateScreenButton; // dummy buttons

    public Skin skin;
    public Stage stage;

    private Table table1;

    private boolean music = true ;
    private boolean vibration = true ;
    private boolean rotation = true ;

    Drawable sound_on_drawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/sound_on.png"))));
    Drawable sound_off_drawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/sound_off.png"))));
    Drawable vibrate_on_drawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/vibrate_on.png"))));
    Drawable vibrate_off_drawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/vibrate_off.png"))));
    Drawable rotate_on_drawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/rotate_on.png"))));
    Drawable rotate_off_drawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/buttons/rotate_off.png"))));



    public OptionScreen(final MovingMazeGame game) {

        this.game = game;
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

        soundButton = new ImageButton(sound_on_drawable,sound_off_drawable,sound_off_drawable);
        vibrationButton = new ImageButton(vibrate_on_drawable,vibrate_off_drawable,vibrate_off_drawable);
        rotateScreenButton = new ImageButton(rotate_on_drawable,rotate_off_drawable,rotate_off_drawable);

        initTable1();
        setTable1();
        setUpButtonListeners();

        stage.addActor(title);
        stage.addActor(table1);

        stage.getCamera().position.set(MovingMazeGame.WIDTH/2.0f, MovingMazeGame.HEIGHT/2.0f, 0);

        bgImageTexture = new Texture(Gdx.files.internal("ui/bg_moss.jpeg")); // background image
        bgTextureRegion = new TextureRegion(bgImageTexture);}

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0,0,0,1);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(bgTextureRegion, 0, 0);
        game.batch.end();
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
        title.setX( camera.viewportWidth/2 - title.getWidth()/2.0f );
        title.setY(camera.viewportHeight - title.getHeight()-30);
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

                if (soundButton.isPressed()){

                    if (music ){
                        music = false ;
                        System.out.println("Music is " + music );
                        soundButton.setChecked(false);
                    }else {
                        music = true;
                        soundButton.setChecked(true);
                        System.out.println("Music is " + music );
                    }
                }
            }
        });

        vibrationButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (vibrationButton.isPressed()){

                    if (vibration ){
                        vibration = false ;
                        vibrationButton.setChecked(false);
                    }else {
                        vibration = true;
                        vibrationButton.setChecked(true);
                        System.out.println("Vibration is " + vibration );
                    }
                }
            }
        });

        rotateScreenButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (rotateScreenButton.isPressed()){

                    if (rotation ){
                        rotation = false;
                        rotateScreenButton.setChecked(false);
                        System.out.println("Rotation is " + rotation );
                    }else {
                        rotation = true;
                        rotateScreenButton.setChecked(true);
                        System.out.println("Music is " + rotation );
                    }
                }
            }
        });
    }




}

