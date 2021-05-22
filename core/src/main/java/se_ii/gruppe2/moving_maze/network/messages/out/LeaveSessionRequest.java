package se_ii.gruppe2.moving_maze.network.messages.out;

import se_ii.gruppe2.moving_maze.player.Player;

public class LeaveSessionRequest {
    Player player;
    String sessionKey;

    public LeaveSessionRequest(){}

    public LeaveSessionRequest(Player player, String sessionKey){
        this.player = player;
        this.sessionKey = sessionKey;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
