package se_ii.gruppe2.moving_maze.network.messages.out;

import se_ii.gruppe2.moving_maze.player.Player;

public class WinGameRequest {
    Player player;
    String sessionCode;

    public WinGameRequest(){};

    public WinGameRequest(String sessionCode, Player player) {
        this.player = player;
        this.sessionCode = sessionCode;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }
}
