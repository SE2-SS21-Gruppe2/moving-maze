package se_ii.gruppe2.moving_maze.network.messages.in;

import java.util.ArrayList;

public class UpdateConnectedPlayersConfirmation {
    ArrayList<String> connectedPlayers;

    public UpdateConnectedPlayersConfirmation(){}

    public UpdateConnectedPlayersConfirmation(ArrayList<String> connectedPlayers){
        this.connectedPlayers = connectedPlayers;
    }

    public ArrayList<String> getConnectedPlayers() {
        return connectedPlayers;
    }

    public void setConnectedPlayers(ArrayList<String> connectedPlayers) {
        this.connectedPlayers = connectedPlayers;
    }
}
