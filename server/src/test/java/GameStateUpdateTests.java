import com.badlogic.gdx.Game;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se_ii.gruppe2.moving_maze.gameboard.GameBoard;
import se_ii.gruppe2.moving_maze.gameboard.GameBoardFactory;
import se_ii.gruppe2.moving_maze.gamestate.GameStateHandler;
import se_ii.gruppe2.moving_maze.network.messages.out.JoinRequest;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.server.Session;
import se_ii.gruppe2.moving_maze.server.SessionManager;
import se_ii.gruppe2.moving_maze.server.handlers.JoinSessionHandler;
import se_ii.gruppe2.moving_maze.tile.LTile;

public class GameStateUpdateTests {

    static Server srv;
    static Client cnt;
    static Player pl1;
    static Player pl2;
    public static final int PORT = 54321;
    public static final String TEST_SESSION_NAME = "TESTSN";
    private static GameBoard testBoard;

    @BeforeAll
    static void setup() {
        srv = ServerTestUtilities.getConfiguredServer(PORT);
        cnt = ServerTestUtilities.getConfiguredClient(PORT);

        srv.addListener(new JoinSessionHandler());

        pl1 = new Player();
        pl1.setName("JohnDoe95");

        pl2 = new Player();
        pl2.setName("MaxMustermann22");

        testBoard = new GameBoard();

        for(int i = 0; i < testBoard.getBoard().length; i++) {
            for(int j = 0; j < testBoard.getBoard()[i].length; j++) {
                testBoard.getBoard()[i][j] = new LTile();
            }
        }

    }

    @BeforeEach
    void reset() throws InterruptedException {
        SessionManager.reset();
        SessionManager.createSessionByKey(TEST_SESSION_NAME);
        cnt.sendTCP(new JoinRequest(TEST_SESSION_NAME, pl1));
        cnt.sendTCP(new JoinRequest(TEST_SESSION_NAME, pl2));
        Thread.sleep(1000);
    }

    @Test
    void testUpdateOnValidSession() throws InterruptedException {
        GameStateHandler gsh = new GameStateHandler();
        gsh.setSessionCode(TEST_SESSION_NAME);
        gsh.setBoard(testBoard);

        cnt.sendTCP(gsh);

        Thread.sleep(3000);

        Session se = SessionManager.getSessionByKey(TEST_SESSION_NAME);

        assertEquals(testBoard, se.getState().getBoard());
        assertEquals(SessionManager.getLogStack().size(), se.getPlayers().size());
    }

}
