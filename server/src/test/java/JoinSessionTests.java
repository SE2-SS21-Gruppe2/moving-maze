import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se_ii.gruppe2.moving_maze.network.messages.in.RequestProcessError;
import se_ii.gruppe2.moving_maze.network.messages.out.JoinRequest;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.server.Session;
import se_ii.gruppe2.moving_maze.server.SessionManager;
import se_ii.gruppe2.moving_maze.server.handlers.JoinSessionHandler;

import java.util.ArrayList;
import java.util.Map;

public class JoinSessionTests {

    static Server srv;
    static Client cnt;
    static Player pl1;
    static Player pl2;
    public static final int PORT = 55321;
    public static final String TEST_SESSION_NAME = "TESTSN";

    @BeforeAll
    static void setup() {
        if(srv != null && cnt != null) {
            srv.close();
            cnt.close();
        }

        srv = ServerTestUtilities.getConfiguredServer(PORT);
        cnt = ServerTestUtilities.getConfiguredClient(PORT);

        srv.addListener(new JoinSessionHandler());

        pl1 = new Player();
        pl1.setName("JohnDoe95");

        pl2 = new Player();
        pl2.setName("MaxMustermann22");
    }

    @BeforeEach
    void reset() {
        SessionManager.reset();
        SessionManager.createSessionByKey(TEST_SESSION_NAME);
    }

    @AfterAll
    static void destroy() {
        srv.close();
        cnt.close();

        srv = null;
        cnt = null;
    }

    @Test
    void testValidJoinRequest() throws InterruptedException {

        cnt.sendTCP(new JoinRequest(TEST_SESSION_NAME, pl1));

        Thread.sleep(3000);

        boolean playerFound = false;

        for(Map.Entry<Player, Connection> entry : SessionManager.getSessionByKey(TEST_SESSION_NAME).getPlayers().entrySet()) {
            if(entry.getKey().getName().equals(pl1.getName())) {
                playerFound = true;
                break;
            }
        }

        assertTrue(playerFound);
    }

    @Test
    void testInvalidSession() throws InterruptedException {
        cnt.sendTCP(new JoinRequest("foobar", pl1));

        Thread.sleep(3000);

        assertTrue(SessionManager.getLastResponse() instanceof RequestProcessError);
    }

    @Test
    void testMultipleSessionJoin() throws InterruptedException {
        ArrayList<Player> players = new ArrayList<>();
        players.add(pl1);
        players.add(pl2);

        for(Player p : players) {
            cnt.sendTCP(new JoinRequest(TEST_SESSION_NAME, p));
        }

        Thread.sleep(3000);

        Session testSession = SessionManager.getSessionByKey(TEST_SESSION_NAME);

        for(Map.Entry<Player, Connection> entry : testSession.getPlayers().entrySet()) {
            players.removeIf(p -> p.equals(entry.getKey()));
        }

        assertEquals(0, players.size());
    }

    @Test
    void testMaxPlayersReached() throws InterruptedException {

        for(int i  = 1; i <= Session.MAX_PLAYERS+1; i++) {
            cnt.sendTCP(new JoinRequest(TEST_SESSION_NAME, new Player()));
        }

        Thread.sleep(3000);

        Session se = SessionManager.getSessionByKey(TEST_SESSION_NAME);

        assertEquals(Session.MAX_PLAYERS, se.getPlayers().size());
        assertTrue(SessionManager.getLastResponse() instanceof RequestProcessError);
    }

}
