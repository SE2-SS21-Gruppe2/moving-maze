package se2.gruppe2.moving_maze.item;

public class Card {
    private Item item;

    //TODO: render Item in bigger, then on GameBoard, on Card.

    public Card(Item item){
        this.item=item;
    }

    public Item getItem() {
        return item;
    }
}
