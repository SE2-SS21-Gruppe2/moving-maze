package se2.gruppe2.moving_maze.network.messages.out;

import se2.gruppe2.moving_maze.player.Player;

public class JoinRequest {
    String key;
    Player player;

    public JoinRequest() {}

    public JoinRequest(String sessionCode, Player player) {
        this.key= sessionCode;
        this.player = player;
    }

    public String getSessionKey() {
        return key;
    }

    public void setSessionKey(String key) {
        this.key = key;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
