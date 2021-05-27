package se_ii.gruppe2.moving_maze.server;

import com.esotericsoftware.kryonet.Server;
import se_ii.gruppe2.moving_maze.network.NetworkClient;
import se_ii.gruppe2.moving_maze.network.Registry;
import se_ii.gruppe2.moving_maze.server.handlers.*;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) {

        Server srv = new Server(NetworkClient.BUFFER_SIZE, NetworkClient.BUFFER_SIZE);

        Registry.registerClassesOnKryo(srv.getKryo());
        srv.start();

        // always create a default session for dev-purpose
        SessionManager.createSessionByKey("DEVGME");

        try {
            srv.bind(ServerConfiguration.PORT);
            srv.addListener(new JoinSessionHandler());
            srv.addListener(new GameStateUpdateHandler());
            srv.addListener(new CreateSessionHandler());
            srv.addListener(new CloseSessionHandler());
            srv.addListener(new LeaveSessionHandler());
            srv.addListener(new InitGameHandler());
            System.out.println("Server successfully listening on port " + ServerConfiguration.PORT);
        } catch(IOException ioe) {
            System.err.println("Failed to bind port " + ServerConfiguration.PORT + " to server! Exiting ...");
        }

    }
}
