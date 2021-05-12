package se2.gruppe2.moving_maze.network.messages.out;

import se2.gruppe2.moving_maze.player.Player;

public class JoinRequest {
    String sessionCode;
    Player player;

    public JoinRequest() {}

    public JoinRequest(String sessionCode, Player player) {
        this.sessionCode = sessionCode;
        this.player = player;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
