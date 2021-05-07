package se2.gruppe2.moving_maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se2.gruppe2.moving_maze.screens.*;

public class MovingMazeGame extends Game {

	// Constants
	public static final int HEIGHT = 600;
	public static final int WIDTH = 1100;

	// Singleton of the game
	private static MovingMazeGame gameInstance;

	public SpriteBatch batch;
	public BitmapFont font;

	// Pre-instantiate screens to re-use
	public JoinSessionScreen joinSessionScreen;
	public CreateSessionScreen createSessionScreen;
	public MainMenuScreen mainMenuScreen;
	public GameScreen gameScreen;
	public OptionScreen optionScreen;
	public RuleScreen ruleScreen;
	// new
	public LoadingScreen loadingScreen;
	public SplashScreen splashScreen;
	public AssetManager assets;
	public OrthographicCamera camera;


    /**
	 * Private constructor to avoid accidental instantiation
	 */
	private MovingMazeGame() { }

	/**
	 * Lifecycle method called on game creation
	 */
	@Override
	public void create () {
		assets = new AssetManager();
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(3);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);

		// Instantiate all screens
		joinSessionScreen = new JoinSessionScreen(this);
		createSessionScreen = new CreateSessionScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		optionScreen = new OptionScreen(this);
		ruleScreen = new RuleScreen(this);
		loadingScreen = new LoadingScreen(this);
		splashScreen = new SplashScreen(this);

		// display main-menu when the game is started

		// try loading screen
		//setScreen(mainMenuScreen);
		setScreen(loadingScreen);
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
		assets.dispose();
		loadingScreen.dispose();
		splashScreen.dispose();
		mainMenuScreen.dispose();

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
