package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import se2.gruppe2.moving_maze.MovingMazeGame;

public class OptionScreen implements Screen {

    final MovingMazeGame game;
    public OrthographicCamera camera;

    public Texture bgImageTexture;
    public TextureRegion bgTextureRegion;

    public Label soundLabel, difficultyLabel, modeLabel; // dummy labels
    public Button soundButton,difficultyButton,modeButton; // dummy buttons

    public Group labelGroup ;
    public Group buttonGroup;

    public Skin skin;
    public Stage stage;

    public OptionScreen(final MovingMazeGame game) {

        this.game = game;
        camera = MovingMazeGame.getStandardizedCamera();

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        buttonGroup = new Group();
        labelGroup = new Group();

        buttonGroup.setBounds(0,0,MovingMazeGame.WIDTH, MovingMazeGame.HEIGHT);
        labelGroup.setBounds(0,0,MovingMazeGame.WIDTH, MovingMazeGame.HEIGHT);

        soundLabel = new Label("Sound",skin);
        difficultyLabel = new Label("Difficulty", skin);
        modeLabel = new Label("Mode", skin);

        soundButton = new TextButton("ON",skin);
        difficultyButton = new TextButton("easy",skin);
        modeButton = new TextButton("single",skin);

        initButtonPositions();
        initLabelPositions();
        setUpButtonListeners();

        buttonGroup.addActor(soundButton);
        buttonGroup.addActor(difficultyButton);
        buttonGroup.addActor(modeButton);

        labelGroup.addActor(soundLabel);
        labelGroup.addActor(difficultyLabel);
        labelGroup.addActor(modeLabel);

        stage.addActor(labelGroup);
        stage.addActor(buttonGroup);

        stage.getCamera().position.set(MovingMazeGame.WIDTH/2.0f, MovingMazeGame.HEIGHT/2.0f, 0);

        bgImageTexture = new Texture(Gdx.files.internal("ui/bg_moss.jpeg")); // background image
        bgTextureRegion = new TextureRegion(bgImageTexture);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0,0,0,1);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(bgTextureRegion, 0, 0); // draw 2D rectangles that reference a texture.
        stage.draw();
        game.batch.end();
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

    /**
     * Initializes the positions of all buttons.
     */
    private void initLabelPositions() {
       /*
        soundLabel.setPosition(x,y);
        difficultyLabel.setPosition(x,y);
        modeLabel.setPosition(x,y);
        */

    }

    /**
     * Initializes the positions of all buttons.
     */
    private void initButtonPositions() {
        /*
        soundButton.setPosition(x,y);
        difficultyButton.setPosition(x,y);
        modeButton.setPosition(x,y);
        */

    }

    private void setUpButtonListeners(){

    }

}
