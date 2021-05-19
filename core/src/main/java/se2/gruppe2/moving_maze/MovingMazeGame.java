package se2.gruppe2.moving_maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se2.gruppe2.moving_maze.gameState.GameStateHandler;
import se2.gruppe2.moving_maze.network.NetworkClient;
import se2.gruppe2.moving_maze.player.Player;
import se2.gruppe2.moving_maze.screens.*;

public class MovingMazeGame extends Game {

	// Constants
	public static final int HEIGHT = 600;
	public static final int WIDTH = 1100;

	// Singleton of the game
	private static MovingMazeGame gameInstance;

	private GameStateHandler state;

	private Player player;

	private SpriteBatch batch;
	private BitmapFont font;

	// Pre-instantiate screens to re-use
	private JoinSessionScreen joinSessionScreen;
	private CreateSessionScreen createSessionScreen;
	private MainMenuScreen mainMenuScreen;
	private GameScreen gameScreen;
	private OptionScreen optionScreen;
	private RuleScreen ruleScreen;
	// new
	private LoadingScreen loadingScreen;
	private SplashScreen splashScreen;
	private AssetManager assets;
	private OrthographicCamera camera;

	// Network
	private NetworkClient client;

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
		// set game-reliant constants

		client = NetworkClient.getInstance();
		state = new GameStateHandler();

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

		// when started, show loading screen (which then transitions to the MainMenuScreen)
		setScreen(loadingScreen);
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
	public static OrthographicCamera getStandardizedCamera(){
		OrthographicCamera camera= new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		return camera;
	}

	// GETTER & SETTER
	public GameStateHandler getGameState() {
		return state;
	}

	public void setGameState(GameStateHandler state) {
		this.state = state;
	}

	public BitmapFont getFont() {
		return this.font;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public OptionScreen getOptionScreen() {
		return optionScreen;
	}

	public CreateSessionScreen getCreateSessionScreen() {
		return createSessionScreen;
	}

	public JoinSessionScreen getJoinSessionScreen() {
		return joinSessionScreen;
	}

	public RuleScreen getRuleScreen() {
		return ruleScreen;
	}

	public MainMenuScreen getMainMenuScreen() {
		return mainMenuScreen;
	}

	public SplashScreen getSplashScreen() {
		return splashScreen;
	}

	public LoadingScreen getLoadingScreen() {
		return loadingScreen;
	}

	public NetworkClient getClient() {
		return this.client;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public AssetManager getAssets() {
		return assets;
	}
}
