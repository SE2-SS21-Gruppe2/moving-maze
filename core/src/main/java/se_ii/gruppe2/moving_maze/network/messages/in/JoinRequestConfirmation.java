package se_ii.gruppe2.moving_maze.network.messages.in;

import se_ii.gruppe2.moving_maze.player.PlayerColor;

/**
 * Issued when a join-request for a session is confirmed by the server.
 */
public class JoinRequestConfirmation {
    String sessionKey;
    PlayerColor assignedColor;

    public JoinRequestConfirmation() { }

    public JoinRequestConfirmation(String sessionKey, PlayerColor assignedColor) {
        this.sessionKey = sessionKey;
        this.assignedColor = assignedColor;
    }

    // GETTER & SETTER
    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public PlayerColor getAssignedColor() {
        return assignedColor;
    }

    public void setAssignedColor(PlayerColor assignedColor) {
        this.assignedColor = assignedColor;
    }
}
