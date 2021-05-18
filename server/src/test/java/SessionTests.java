import static org.junit.jupiter.api.Assertions.*;

import com.esotericsoftware.kryonet.Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se2.gruppe2.moving_maze.gameState.GameStateHandler;
import se2.gruppe2.moving_maze.player.Player;
import se2.gruppe2.moving_maze.server.Session;

public class SessionTests {
    Session se;
    String testkey = "testsession";

    @BeforeEach
    public void setup() {
        se = new Session(testkey);
    }

    @Test
    public void correctKeyOnNewSession() {
        assertEquals(testkey, se.getKey());
    }

    @Test
    public void playerAdded() {
        Player p = new Player("John Doe");
        // TODO: mock connection
        se.addPlayer(p, null);

        assertEquals(1, se.getPlayers().size());
    }

    @Test
    public void gameStateSet() {
        GameStateHandler gsh = new GameStateHandler();
        se.setState(gsh);
        assertEquals(gsh, se.getState());
    }
}
