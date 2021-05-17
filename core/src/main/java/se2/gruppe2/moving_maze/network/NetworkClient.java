package se2.gruppe2.moving_maze.network;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import se2.gruppe2.moving_maze.gameState.GameStateHandler;
import se2.gruppe2.moving_maze.network.listeners.ErrorResponseListener;
import se2.gruppe2.moving_maze.network.listeners.GameStateUpdateListener;
import se2.gruppe2.moving_maze.network.listeners.JoinConfirmationListener;
import se2.gruppe2.moving_maze.network.messages.out.JoinRequest;
import se2.gruppe2.moving_maze.player.Player;

import java.io.IOException;

public class NetworkClient {
    private static NetworkClient singleton;
    private int bufferSize = 4096;

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
                    NetworkConfig.timeout,
                    NetworkConfig.host,
                    NetworkConfig.port
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
        kryoClient = new Client(bufferSize, bufferSize);
        Registry.registerClassesOnKryo(kryoClient.getKryo());
        kryoClient.start();
        kryoClient.connect(timeout, host, port);

        kryoClient.addListener(new ErrorResponseListener());
        kryoClient.addListener(new GameStateUpdateListener());
        kryoClient.addListener(new JoinConfirmationListener());
    }

    /**
     * Issues a request to join an existing session
     * @param player that wants to join
     * @param sessionKey that should be joined
     * @return true if the join was successful, false if session could not be joined
     */
    public boolean joinSession(Player player, String sessionKey) {
        kryoClient.sendTCP(new JoinRequest(sessionKey, player));
        Gdx.app.log("NetworkClient/joinSession", "Submitted request to join session '" + sessionKey + "'");
        // TODO: actually implement receiving logic
        return true;
    }

    public void sendGameStateUpdate(GameStateHandler state) {
        kryoClient.sendTCP(state);
        Gdx.app.log("NetworkClient/sendGameStateUpdate", "Sent gamestate-update to server");
    }

}
