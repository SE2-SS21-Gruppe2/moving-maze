
package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import se2.gruppe2.moving_maze.MovingMazeGame;

public class OptionScreen implements Screen {

    static float volume = 1.0f;
    final MovingMazeGame game;
    public OrthographicCamera camera;

    public Texture bgImageTexture;
    public TextureRegion bgTextureRegion;

    public Label soundLabel, difficultyLabel, modeLabel; // dummy labels
    public Button soundButton,difficultyButton,modeButton; // dummy buttons

    public Skin skin;
    public Stage stage;

    private Table table1,table2;


    public OptionScreen(final MovingMazeGame game) {

        this.game = game;
    }

    @Override
    public void show() {
        camera = MovingMazeGame.getStandardizedCamera();

        this.stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        Gdx.input.setInputProcessor(stage);

        table1 = new Table();

        soundLabel = new Label("Sound",skin);
        difficultyLabel = new Label("Difficulty",skin);
        modeLabel = new Label("Mode", skin);

        soundButton = new TextButton("ON",skin);
        difficultyButton = new TextButton("easy",skin);
        modeButton = new TextButton("single",skin);

        initTable1();
        setTable1();
        setUpButtonListeners();

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


     /* Initializes the positions of all buttons */

    private void initLabelPositions() {


    }

    private void setTable1(){

        table1.align(Align.center);
        table1.setPosition(MovingMazeGame.WIDTH/2.5f,MovingMazeGame.HEIGHT/2.5f,Align.center);
        table1.padBottom(290);
    }


    private void initTable1(){

        table1.align(Align.center);
        table1.add(soundLabel).size(200,70).center();
        table1.add(soundButton).size(50,30).center();
        table1.row();
        table1.add(difficultyLabel).size(200,70).center();
        table1.add(difficultyButton).size(50,30).center();
        table1.row();
        table1.add(modeLabel).size(200,70).center();
        table1.add(modeButton).size(60,30).center();

        table1.padTop(180);
        table1.padLeft(130);

    }

    private void setUpButtonListeners(){
    }




}

