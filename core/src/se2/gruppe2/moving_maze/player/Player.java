package se2.gruppe2.moving_maze.player;

import se2.gruppe2.moving_maze.item.Card;
import se2.gruppe2.moving_maze.item.Position;

public class Player {
    private String name;
    private Position pos;
    private Card[] cardsToFind;
    private Card[] cardsFound;
    private Card[] currentCard;
    private PlayerRole role;

    public Position move(int dX, int dy){
        return new Position();
    }
}
