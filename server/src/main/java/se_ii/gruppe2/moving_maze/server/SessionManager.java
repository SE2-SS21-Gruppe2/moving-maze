package se_ii.gruppe2.moving_maze.server;

import com.esotericsoftware.minlog.Log;

import java.security.SecureRandom;
import java.util.HashMap;

public class SessionManager {
    private static int lastKey = 0;
    public static final int rand_min_key = 10000;
    public static final int rand_max_key = 99999;
    private static HashMap<String, Session> sessionRegistry = new HashMap<>();
    private static final SecureRandom random = new SecureRandom();

    // utility class
    private SessionManager() {}

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
        String generatedKey = generateRandomKey();
        Log.info("Generated session-key '" + generatedKey + "'");

        return createSessionByKey(generatedKey);
    }

    /**
     * Creates a session with a predefined key
     * @param key to use for the session
     * @return the created session if successful; null otherwise
     */
    public static Session createSessionByKey(String key) {
        if(sessionRegistry.get(key) == null) {
            Log.info("SessionManager", "Creating new session with key '" + key +"'");
            Session session = new Session(key);
            sessionRegistry.put(key, session);
            return session;
        } else {
            Log.warn("SessionManager", "Session with key '" + key +"' cannot be created because it already exists!");
            return null;
        }
    }

    /**
     * Creates a randomized session key
     * @return key represented as String
     */
    public static String generateRandomKey() {
        //String randBetweenBounds = String.valueOf(random.nextInt((rand_max_key-rand_min_key)+1) + rand_min_key);
        //return randBetweenBounds + lastKey++;

        String setOfCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String randomString = "";
        for (int i = 0; i < 6; i++){
            randomString += setOfCharacters.charAt(random.nextInt(setOfCharacters.length()));
        }
        return randomString;

    }

    public static void reset() {
        sessionRegistry = new HashMap<>();
    }

    public static int getLastKey() {
        return lastKey;
    }

    public static boolean sessionExists(String key) {
        return sessionRegistry.get(key) != null;
    }

    public static void closeSession(String sessionKey) {
        sessionRegistry.remove(sessionKey);
        Log.info("Session '" + sessionKey + "' closed.");
    }
}
