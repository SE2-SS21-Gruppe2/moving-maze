package se_ii.gruppe2.moving_maze.network;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import se_ii.gruppe2.moving_maze.gameboard.GameBoard;
import se_ii.gruppe2.moving_maze.item.ItemFactory;
import se_ii.gruppe2.moving_maze.network.listeners.CreateSessionConfirmationListener;
import se_ii.gruppe2.moving_maze.network.listeners.UpdateConnectedPlayersListener;
import se_ii.gruppe2.moving_maze.network.messages.out.*;
import se_ii.gruppe2.moving_maze.gamestate.GameStateHandler;
import se_ii.gruppe2.moving_maze.network.listeners.ErrorResponseListener;
import se_ii.gruppe2.moving_maze.network.listeners.GameStateUpdateListener;
import se_ii.gruppe2.moving_maze.network.listeners.JoinConfirmationListener;
import se_ii.gruppe2.moving_maze.player.Player;

import java.io.IOException;

public class NetworkClient {
    private static NetworkClient singleton;
    public static final int BUFFER_SIZE = 8192;

    Client kryoClient;

    private NetworkClient() {
    }

    /**
     * Returns the NetworkClient singleton and instantiates it on the first call
     * @return NetworkClient singleton object
     */
    public static NetworkClient getInstance() {
        if(singleton == null) {
            singleton = new NetworkClient();
            try {
            singleton.initKryoClient(
                    NetworkConfig.TIMEOUT,
                    NetworkConfig.HOST,
                    NetworkConfig.PORT
            );
            Gdx.app.log("moving-maze/NetworkClient", "Connection to gameserver established");
            } catch(IOException ioe) {
                Gdx.app.error("moving-maze/NetworkClient", "Failed to connect to gameserver", ioe);
            }
        }

        return singleton;
    }

    /**
     * Initiates the internal kryoClient
     * @param timeout in ms to wait for the server ack
     * @param host -name or ip-address of server
     * @param port (tcp) to talk exchange data with
     */
    public void initKryoClient(int timeout, String host, int port) throws IOException {
        kryoClient = new Client(BUFFER_SIZE, BUFFER_SIZE);
        Registry.registerClassesOnKryo(kryoClient.getKryo());
        kryoClient.start();
        kryoClient.connect(timeout, host, port);

        kryoClient.addListener(new ErrorResponseListener());
        kryoClient.addListener(new GameStateUpdateListener());
        kryoClient.addListener(new JoinConfirmationListener());
        kryoClient.addListener(new CreateSessionConfirmationListener());
        kryoClient.addListener(new UpdateConnectedPlayersListener());
    }

    /**
     * Issues a request to join an existing session
     * @param player that wants to join
     * @param sessionKey that should be joined
     * @return true if the join was successful, false if session could not be joined
     */
    public void joinSession(Player player, String sessionKey) {
        kryoClient.sendTCP(new JoinRequest(sessionKey, player));
        Gdx.app.log("NetworkClient/joinSession", "Submitted request to join session '" + sessionKey + "'");
    }

    public void sendGameStateUpdate(GameStateHandler state) {
        kryoClient.sendTCP(state);
        Gdx.app.log("NetworkClient/sendGameStateUpdate", "Sent gamestate-update to server");
    }

    public void createNewSession(Player player) {
        kryoClient.sendTCP(new CreateSessionRequest(player));
        Gdx.app.log("NetworkClient/createNewSession", "Submitted request to create new session");
    }

    public void closeSession(String sessionKey) {
        kryoClient.sendTCP(new CloseSessionRequest(sessionKey));
        Gdx.app.log("NetworkClient/closeSession", "Submitted request to close session '" + sessionKey + "'");
    }

    public void leaveSession(Player player, String sessionKey) {
        kryoClient.sendTCP(new LeaveSessionRequest(player, sessionKey));
        Gdx.app.log("NetworkClient/leaveSession", "Submitted request to leave session '" + sessionKey + "'");
    }

    public void initGame(String key, GameBoard board, String finalHostName) {
        kryoClient.sendTCP(new InitGameStart(key, board, finalHostName, ItemFactory.items));
        Gdx.app.log("NetworkClient/initGame", "Sent request to initialize game '" + key + "'");
    }

}
