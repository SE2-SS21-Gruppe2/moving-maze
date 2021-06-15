package se_ii.gruppe2.moving_maze.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;


import se_ii.gruppe2.moving_maze.MovingMazeGame;
import se_ii.gruppe2.moving_maze.helperclasses.TextureLoader;
import se_ii.gruppe2.moving_maze.helperclasses.TextureType;
import se_ii.gruppe2.moving_maze.player.Player;

public class VictoryScreen implements Screen {

    private final MovingMazeGame game;
    private final SpriteBatch batch;
    private OrthographicCamera camera;

    private Table tbl;
    private Stage stage;

    private static Player winingPlayer;

    public VictoryScreen(MovingMazeGame game) {
        this.game = game;
        this.batch = game.getBatch();
        stage = new Stage();
    }

    @Override
    public void show() {
        camera = MovingMazeGame.getStandardizedCamera();

        tbl = new Table();

        TextButton tbBack = new TextButton("Back to menu", MainMenuScreen.skin);
        tbBack.setSize(Gdx.graphics.getWidth()/5f, Gdx.graphics.getHeight()/10f);
        tbBack.getLabel().setFontScale(Gdx.graphics.getHeight()/tbBack.getHeight() / 3f);
        tbBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getMainMenuScreen());
            }
        });

        tbl.setPosition(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
        // tbl.row();
        // tbl.add(new Label(winingPlayer.getName() + " beat the maze!", MainMenuScreen.skin));
        tbl.row();
        tbl.add(tbBack).size(tbBack.getWidth(), tbBack.getHeight());

        Gdx.input.setInputProcessor(stage);
        stage.addActor(tbl);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();
            batch.draw(TextureLoader.getSpriteByTexturePath("ui/bg_moss.jpeg", TextureType.BACKGROUND).getTexture(), 0f, 0f);
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

    public static void setWiningPlayer(Player player) {
        winingPlayer= player;
    }
}
