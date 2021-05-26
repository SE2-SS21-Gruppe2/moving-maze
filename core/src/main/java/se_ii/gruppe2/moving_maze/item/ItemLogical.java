package se_ii.gruppe2.moving_maze.item;

public class ItemLogical {

    private String name;
    private Position position;
    private String texturePath;
    private boolean onCard;

    public ItemLogical(String texturePath, Position position, boolean onCard){
        this.position=position;
        this.name= getItemName(texturePath);
        this.texturePath = texturePath;
        this.onCard = onCard;

    }

    public ItemLogical() {}

    /** method that gets the name of the item */



    private static String getItemName(String path){

        String[] split = path.split("[/\\.]");
        return split[split.length-2];
    }

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
