package se2.gruppe2.moving_maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se2.gruppe2.moving_maze.network.NetworkClient;
import se2.gruppe2.moving_maze.player.Player;
import se2.gruppe2.moving_maze.screens.*;

import java.io.IOException;
import java.util.Random;

public class MovingMazeGame extends Game {

	// Constants
	public static final int HEIGHT = 600;
	public static final int WIDTH = 1100;
	public static float BTN_WIDTH;
	public static float BTN_HEIGHT;

	// Singleton of the game
	private static MovingMazeGame gameInstance;

	public Player player;

	public SpriteBatch batch;
	public BitmapFont font;

	// Pre-instantiate screens to re-use
	public JoinSessionScreen joinSessionScreen;
	public CreateSessionScreen createSessionScreen;
	public MainMenuScreen mainMenuScreen;
	public GameScreen gameScreen;
	public OptionScreen optionScreen;
	public RuleScreen ruleScreen;

	// Network
	public NetworkClient client;

	/**
	 * Private constructor to avoid accidental instantiation
	 */
	private MovingMazeGame() { }

	/**
	 * Lifecycle method called on game creation
	 */
	@Override
	public void create () {
		// set game-reliant constants
		MovingMazeGame.BTN_HEIGHT = Gdx.graphics.getHeight()/8.5f;
		MovingMazeGame.BTN_WIDTH = Gdx.graphics.getWidth()/5f;

		client = NetworkClient.getInstance();

		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(3);

		// Instantiate all screens
		joinSessionScreen = new JoinSessionScreen(this);
		createSessionScreen = new CreateSessionScreen(this);
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

	public static OrthographicCamera gameboardCamera(){
		OrthographicCamera camera= new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		return camera;
	}

}
