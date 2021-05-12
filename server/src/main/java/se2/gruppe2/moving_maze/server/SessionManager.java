package se2.gruppe2.moving_maze.server;

import com.esotericsoftware.minlog.Log;

import java.util.HashMap;
import java.util.Random;

public class SessionManager {
    private static int lastKey = 0;
    private static HashMap<String, Session> sessionRegistry = new HashMap<>();
    private static Random random = new Random();
    private static String logTag;

    /**
     * Searches and returns a session by its key
     * @param key that the session has
     * @return the session if found, null otherwise
     */
    public static Session getSessionByKey(String key) {
        return sessionRegistry.get(key);
    }

    /**
     * Gets the number of currently stored sessions
     * @return the number of sessions currently stored
     */
    public static int getSessionCount() {
        return sessionRegistry.size();
    }

    /**
     * Creates a session with a randomly generated key
     * @return the created session
     */
    public static Session createRandomSession() {
        return null;
    }

    /**
     * Creates a session with a predefined key
     * @param key to use for the session
     * @return the created session if successful; null otherwise
     */
    public static Session createSessionByKey(String key) {
        if(sessionRegistry.get(key) == null) {
            Log.info(logTag , "Creating new session with key '" + key +"'");
            Session session = new Session(key);
            sessionRegistry.put(key, session);
            return session;
        } else {
            Log.warn(logTag, "Session with key '" + key +"' cannot be created because it already exists!");
            return null;
        }
    }
}
