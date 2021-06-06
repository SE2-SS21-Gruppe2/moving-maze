package se_ii.gruppe2.moving_maze.item;

import java.util.Objects;

public class ItemLogical {

    private String name;
    private Position position;
    private String texturePath;
    private boolean onCard;

    public ItemLogical(String texturePath, Position position, boolean onCard){
        this.position=position;
        this.name= texturePath.substring(6,texturePath.length()-4);
        this.texturePath = texturePath;
        this.onCard = onCard;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemLogical that = (ItemLogical) o;
        return texturePath.equals(that.texturePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(texturePath);
    }
}
