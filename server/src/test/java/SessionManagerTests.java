import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import se_ii.gruppe2.moving_maze.server.Session;
import se_ii.gruppe2.moving_maze.server.SessionManager;


public class SessionManagerTests {

    String testkey = "testsession";

    @Test
    public void createAndFindSessionByKey() {
        SessionManager.createSessionByKey(testkey);

        assertEquals(testkey, SessionManager.getSessionByKey(testkey).getKey());
    }

    @Test
    public void addDuplicateSessionByKey() {
        SessionManager.createSessionByKey(testkey);

        assertNull(SessionManager.createSessionByKey(testkey));
    }

    @Test
    public void randomKeysWithinBounds() {
        int numberOfKeys = 9;

        String key;
        int minLength = String.valueOf(SessionManager.rand_min_key).length();
        int lastKeyMixerLength;
        for(int i = 1; i <= numberOfKeys; i++) {
            key = SessionManager.generateRandomKey();
            lastKeyMixerLength = String.valueOf(SessionManager.getLastKey()).length();

            assertEquals(minLength + lastKeyMixerLength, key.length());
        }
    }

    @Test
    public void randomSessionCreation() {
        Session se = SessionManager.createRandomSession();

        assertEquals(se, SessionManager.getSessionByKey(se.getKey()));
    }

    @Test
    public void resetSessions() {
        SessionManager.createRandomSession();
        SessionManager.reset();
        assertEquals(0, SessionManager.getSessionCount());
    }

    @Test
    public void sessionCount() {
        SessionManager.reset();

        int sessions = 10;
        for(int i = 1; i <= sessions; i++) {
            SessionManager.createRandomSession();
        }

        assertEquals(sessions, SessionManager.getSessionCount());
    }
}
