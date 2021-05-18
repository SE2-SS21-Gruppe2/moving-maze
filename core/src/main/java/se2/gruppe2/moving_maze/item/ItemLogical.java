package se2.gruppe2.moving_maze.item;

public class ItemLogical {

    private String name;
    private Position position;
    private String texturePath;
    private boolean onCard;

    public ItemLogical(String texturePath, Position position, boolean onCard){
        this.position=position;
        this.name= texturePath.substring(6,texturePath.length()-4);
        this.texturePath = texturePath;
    }

    public ItemLogical() {}

    // GETTER & SETTER

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }

    public boolean isOnCard() {
        return onCard;
    }

    public void setOnCard(boolean onCard) {
        this.onCard = onCard;
    }
}
