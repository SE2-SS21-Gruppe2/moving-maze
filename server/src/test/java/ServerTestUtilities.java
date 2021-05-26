import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import se_ii.gruppe2.moving_maze.network.Registry;

public class ServerTestUtilities {
    public static final int BUFFER_SIZE = 4096;

    public static Server getConfiguredServer(int port) {
        Server server = new Server(BUFFER_SIZE, BUFFER_SIZE);

        Registry.registerClassesOnKryo(server.getKryo());
        server.start();

        try {
            server.bind(port);
            return server;
        } catch(Exception e) {
            System.err.println("Failed to bind port " + port + " to server ..");
            return null;
        }
    }

    public static Client getConfiguredClient(int port) {
        Client client = new Client(BUFFER_SIZE, BUFFER_SIZE);

        Registry.registerClassesOnKryo(client.getKryo());

        client.start();
        try {
            client.connect(5000, "localhost", port);
            return client;
        } catch(Exception e) {
            System.err.println("Client failed to connect");
            return null;
        }
    }

}
