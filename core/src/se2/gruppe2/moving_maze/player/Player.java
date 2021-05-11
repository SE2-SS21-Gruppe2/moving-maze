package se2.gruppe2.moving_maze.player;

import se2.gruppe2.moving_maze.item.Card;
import se2.gruppe2.moving_maze.item.Position;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class Player {
    private String name;
    private Position pos;
    private Stack<Card> cardsToFind;
    private Stack<Card> cardsFound;
    private Card currentCard;
    private PlayerRole role;

    public Player() {
        cardsToFind = new Stack<>();
        cardsFound = new Stack<>();
    }

    public Player(String name) {
        this();
        this.name = name;
    }

    public void move(int dX, int dY){
        // TODO: validation
        this.pos.setPosition(
                this.pos.getX()+dX,
                this.pos.getY()+dY
        );
    }

    /**
     * Marks the currentCard as found and draws the next card.
     * @return the new currentCard; null if no cards are left
     */
    public Card currentCardFound() {
        cardsFound.push(currentCard);

        if(!cardsToFind.empty()) {
            currentCard = cardsToFind.pop();
        } else {
            currentCard = null;
        }

        return currentCard;
    }

    // GETTER & SETTER
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerRole getRole() {
        return role;
    }

    public void setRole(PlayerRole role) {
        this.role = role;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public Position getPos() {
        return this.pos;
    }

}
