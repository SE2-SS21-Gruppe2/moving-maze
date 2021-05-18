package se2.gruppe2.moving_maze.network.messages.in;

/**
 * Issued when a join-request for a session is confirmed by the server.
 */
public class JoinRequestConfirmation {
    String sessionKey;

    public JoinRequestConfirmation() { }

    public JoinRequestConfirmation(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    // GETTER & SETTER
    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
