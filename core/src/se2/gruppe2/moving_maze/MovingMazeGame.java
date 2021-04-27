package se2.gruppe2.moving_maze;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MovingMazeGame extends Game {

	// Constants
	public static final int HEIGHT = 480;
	public static final int WIDTH = 800;

	// Singleton of the game
	private static MovingMazeGame gameInstance;

	public SpriteBatch batch;
	public BitmapFont font;

	// Pre-instantiate screens to re-use
	public MainMenuScreen mainMenuScreen;
	public GameScreen gameScreen;
	public OptionScreen optionScreen;
	public RuleScreen ruleScreen;


	/**
	 * Private constructor to avoid accidental instantiation
	 */
	private MovingMazeGame() { }

	/**
	 * Lifecycle method called on game creation
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(3);

		// Instantiate all screens
		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		optionScreen = new OptionScreen(this);
		ruleScreen = new RuleScreen(this);

		// display main-menu when the game is started
		setScreen(mainMenuScreen);
	}

	/**
	 * Lifecycle method called to render each frame
	 */
	@Override
	public void render () {
		super.render();
	}

	/**
	 * Lifecycle method to dispose heavy objects after the game quits
	 */
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}

	/**
	 * Returns the singleton of the game-class
	 * @return singleton of the game-class
	 */
	public static MovingMazeGame getGameInstance() {
		if(gameInstance == null) {
			gameInstance = new MovingMazeGame();
		}
		return gameInstance;
	}

	/**
	 * Creates a OrthographicCamera object with the correct dimensions for the game.
	 * Mostly used by screens to get the same virtual resolution.
	 * @return OrthographicCamera with standardized height and width
	 */
	public static OrthographicCamera getStandardizedCamera() {
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, MovingMazeGame.WIDTH, MovingMazeGame.HEIGHT);
		return camera;
	}
}
