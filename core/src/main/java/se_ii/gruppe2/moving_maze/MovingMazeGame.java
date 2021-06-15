package se_ii.gruppe2.moving_maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import se_ii.gruppe2.moving_maze.audio.AudioManager;
import se_ii.gruppe2.moving_maze.gamestate.GameStateHandler;
import se_ii.gruppe2.moving_maze.helperclasses.Styles;
import se_ii.gruppe2.moving_maze.network.NetworkClient;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.screens.*;

import java.util.ArrayList;

public class MovingMazeGame extends Game {

	// Constants
	public static int HEIGHT;
	public static int WIDTH;

	// Singleton of the game
	private static MovingMazeGame gameInstance;

	private GameStateHandler state;
	private Preferences preferences;
	private Player localPlayer;

	private SpriteBatch batch;
	private BitmapFont font;
	private Styles style;

	// Pre-instantiate screens to re-use
	private JoinSessionScreen joinSessionScreen;
	private CreateSessionScreen createSessionScreen;
	private MainMenuScreen mainMenuScreen;
	private GameScreen gameScreen;
	private OptionScreen optionScreen;
	private RuleScreen ruleScreen;
	private VictoryScreen victoryScreen;
	// new
	private LoadingScreen loadingScreen;
	private SplashScreen splashScreen;
	private WaitingScreen waitingScreen;
	private AssetManager assets;
	private OrthographicCamera camera;

	private String sessionKey;
	private ArrayList<String> connectedPlayers;
	private boolean inGame;

	// Network
	private NetworkClient client;

	// Audio
	private AudioManager audioManager;

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
		MovingMazeGame.WIDTH = Gdx.graphics.getWidth();
		MovingMazeGame.HEIGHT = Gdx.graphics.getHeight();

		client = NetworkClient.getInstance();
		audioManager = AudioManager.getAudioManagerInstance();
		state = new GameStateHandler();
		preferences = Gdx.app.getPreferences("localPreferences");

		sessionKey = "------";
		connectedPlayers = new ArrayList<>();

		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(3);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		style = new Styles(sessionKey);

		// Instantiate all screens
		joinSessionScreen = new JoinSessionScreen(this);
		createSessionScreen = new CreateSessionScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		optionScreen = new OptionScreen(this);
		ruleScreen = new RuleScreen(this);
		loadingScreen = new LoadingScreen(this);
		splashScreen = new SplashScreen(this);
		waitingScreen = new WaitingScreen(this);
		victoryScreen= new VictoryScreen(this);

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
		waitingScreen.dispose();
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
		var camera= new OrthographicCamera();
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

	public Player getLocalPlayer() {
		return localPlayer;
	}

	public void setLocalPlayer(Player localPlayer) {
		this.localPlayer = localPlayer;
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

	public VictoryScreen getVictoryScreen(){
		return victoryScreen;
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

	public WaitingScreen getWaitingScreen() {
		return waitingScreen;
	}

	public void setWaitingScreen(WaitingScreen waitingScreen) {
		this.waitingScreen = waitingScreen;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public ArrayList<String> getConnectedPlayers() {
		return connectedPlayers;
	}

	public void setConnectedPlayers(ArrayList<String> connectedPlayers) {
		this.connectedPlayers = connectedPlayers;
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public Preferences getPreferences() {
		return preferences;
	}

	public AudioManager getAudioManager() {
		return audioManager;
	}

	public Styles getStyle() {
		return style;
	}

	public void setStyle(Styles style) {
		this.style = style;
	}
}
