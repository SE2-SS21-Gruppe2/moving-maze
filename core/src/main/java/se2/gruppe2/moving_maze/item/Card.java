package se2.gruppe2.moving_maze.item;

public class Card {
    private ItemLogical item;

    //TODO: render Item in bigger, then on GameBoard, on Card.

    public Card(ItemLogical item){
        this.item=item;
    }

    public ItemLogical getItem() {
        return item;
    }
}
