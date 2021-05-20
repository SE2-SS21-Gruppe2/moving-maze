package se2.gruppe2.moving_maze.server;

import com.esotericsoftware.kryonet.Server;
import se2.gruppe2.moving_maze.network.Registry;
import se2.gruppe2.moving_maze.server.handlers.*;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) {

        int bufferSize = 4096;

        Server srv = new Server(bufferSize, bufferSize);

        Registry.registerClassesOnKryo(srv.getKryo());
        srv.start();

        // always create a default session for dev-purpose
        SessionManager.createSessionByKey("DEVGME");
        // SessionManager.getSessionByKey("DEVGME").getState().setBoard(GameBoardFactory.getStandardGameBoard());

        try {
            srv.bind(ServerConfiguration.PORT);
            srv.addListener(new JoinSessionHandler());
            srv.addListener(new GameStateUpdateHandler());
            srv.addListener(new CreateSessionHandler());
            srv.addListener(new CloseSessionHandler());
            srv.addListener(new LeaveSessionHandler());
            System.out.println("Server successfully listening on port " + ServerConfiguration.PORT);
        } catch(IOException ioe) {
            System.err.println("Failed to bind port " + ServerConfiguration.PORT + " to server! Exiting ...");
        }

    }
}
