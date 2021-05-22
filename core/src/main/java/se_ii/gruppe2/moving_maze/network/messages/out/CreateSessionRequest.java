package se_ii.gruppe2.moving_maze.network.messages.out;

import se_ii.gruppe2.moving_maze.player.Player;

public class CreateSessionRequest {
    Player player;

    public CreateSessionRequest() {}

    public CreateSessionRequest(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
