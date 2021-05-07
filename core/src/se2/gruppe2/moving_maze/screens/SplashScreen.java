package se2.gruppe2.moving_maze.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import se2.gruppe2.moving_maze.MovingMazeGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen implements Screen {

        private final MovingMazeGame game;
        private Stage stage;

        private Image splashImg;

        public SplashScreen(final MovingMazeGame game) {
            this.game = game;
            this.stage = new Stage(new FitViewport(MovingMazeGame.WIDTH, MovingMazeGame.HEIGHT, game.camera));
        }

        @Override
        public void show() {

            Gdx.input.setInputProcessor(stage);

            Runnable transitionRunnable = () -> game.setScreen(game.mainMenuScreen);

            Texture splashTex = game.assets.get("ui/loadingbar/splash.png", Texture.class);
            splashImg = new Image(splashTex);
            splashImg.setOrigin(splashImg.getWidth() / 2, splashImg.getHeight() / 2);
            splashImg.setPosition(stage.getWidth() , stage.getHeight() );
            splashImg.addAction(sequence(alpha(0), scaleTo(.1f, .1f),
                    parallel(fadeIn(2f, Interpolation.pow2),
                            scaleTo(2f, 2f, 2.3f, Interpolation.pow5),
                            moveTo(stage.getWidth() / 2 - 160 , stage.getHeight() / 2 , 2f, Interpolation.swing)),
                    delay(1.5f), fadeOut(1.25f), run(transitionRunnable)));

            stage.addActor(splashImg);
        }

        @Override
        public void render(float delta) {
            Gdx.gl.glClearColor(0, 0, 0, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            update(delta);

            stage.draw();

        }

        public void update(float delta) {
            stage.act(delta);
        }

        @Override
        public void resize(int width, int height) {
            stage.getViewport().update(width, height, false);
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
