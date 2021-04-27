package se2.gruppe2.moving_maze.server;

import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) {

        Server srv = new Server();
        srv.start();

        try {
            srv.bind(ServerConfiguration.PORT);
            srv.addListener(new CustomListener());
            System.out.println("Server successfully listening on port " + ServerConfiguration.PORT);
        } catch(IOException ioe) {
            System.err.println("Failed to bind port " + ServerConfiguration.PORT + " to server! Exiting ...");
        }

    }
}
