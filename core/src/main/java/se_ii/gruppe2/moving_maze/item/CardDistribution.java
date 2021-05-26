package se_ii.gruppe2.moving_maze.item;

import java.util.Stack;

public class CardDistribution {


    private static final ItemLogical[] items = ItemFactory.itemArray();


    private CardDistribution(){

    }

    /** Create a stack of cards  */

    private static Stack<Card> buildCardStack(){

        Stack<Card> cardStack = new Stack<>();

        for (int i = 0 ; i < items.length ; i++){

            Card card = new Card(items[i]);
            cardStack.add(card);

        }
        return cardStack;
    }
    





}
