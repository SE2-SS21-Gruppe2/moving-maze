package se2.gruppe2.moving_maze.network;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class NetworkClient {
    private static NetworkClient singleton;
    private static Registry registry;

    Client kryoClient;

    private NetworkClient() { }

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
            ); } catch(IOException ioe) {
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
        kryoClient = new Client();
        kryoClient.start();
        kryoClient.connect(timeout, host, port);
    }



}
