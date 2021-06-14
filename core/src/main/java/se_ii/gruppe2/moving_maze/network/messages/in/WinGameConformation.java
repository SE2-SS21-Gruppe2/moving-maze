package se_ii.gruppe2.moving_maze.network.messages.in;

import se_ii.gruppe2.moving_maze.player.Player;

public class WinGameConformation {
    Player player;

    public WinGameConformation(){};

    public  WinGameConformation(Player player){
        this.player=player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
