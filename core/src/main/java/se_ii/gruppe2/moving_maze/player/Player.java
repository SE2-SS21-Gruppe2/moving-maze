package se_ii.gruppe2.moving_maze.player;

import com.badlogic.gdx.math.Vector2;
import se_ii.gruppe2.moving_maze.item.Card;
import se_ii.gruppe2.moving_maze.item.ItemLogical;
import se_ii.gruppe2.moving_maze.item.Position;

import java.security.SecureRandom;
import java.util.Stack;

public class Player {
    private long id;
    private String name;
    private String texturePath;
    private Position pos;
    private Stack<ItemLogical> cardsToFind;
    private Stack<ItemLogical> cardsFound;
    private ItemLogical currentCard;
    private PlayerRole role;

    private PlayerColor color;

    public Player() {
        cardsToFind = new Stack<>();
        cardsFound = new Stack<>();
        id = generateId();
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


    public static void getTexturePathByColor(PlayerColor color) {

    }

    /**
     * Marks the currentCard as found and draws the next card.
     * TODO: mark player as winner once the stack is empty (==> all cards found)
     * @return the new currentCard; null if no cards are left
     */
    public ItemLogical nextCard() {
        cardsFound.push(currentCard);

        if(!cardsToFind.empty()) {
            currentCard = cardsToFind.pop();
        } else {
            currentCard = null;
        }

        return currentCard;
    }

    /**
     * Creates a semi-random id for a player object.
     * currentTimeMillis on creation and a randomly generated number are taken into consideration.
     * @return the id
     */
    public long generateId() {
        long currentMillis = System.currentTimeMillis();
        long salt = new SecureRandom().nextInt(99);

        return currentMillis + salt;
    }

    @Override
    public boolean equals(Object object) {
        if(object instanceof  Player) {
            Player pl = (Player) object;

            return pl.getId() == this.getId();
        } else {
            return false;
        }
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

    public void setPos(int x, int y) { this.pos.setPosition(x, y);}

    public Position getPos() {
        return this.pos;
    }

    public long getId() {
        return this.id;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
        this.texturePath = PlayerColorMapper.getTexturePathByPlayerColor(this.color);
        this.pos = PlayerColorMapper.getInitialPositionByColor(this.color);
    }

    public String getTexturePath() {
        return this.texturePath;
    }

    public Stack<ItemLogical> getCardsToFind() {
        return cardsToFind;
    }

    public void setCardsToFind(Stack<ItemLogical> cardsToFind) {
        this.cardsToFind = cardsToFind;
    }

    public Stack<ItemLogical> getCardsFound() {
        return cardsFound;
    }

    public void setCardsFound(Stack<ItemLogical> cardsFound) {
        this.cardsFound = cardsFound;
    }

    public ItemLogical getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(ItemLogical currentCard) {
        this.currentCard = currentCard;
    }
}