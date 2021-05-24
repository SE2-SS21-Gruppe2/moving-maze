package item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import se_ii.gruppe2.moving_maze.item.Position;
import se_ii.gruppe2.moving_maze.item.ItemLogical;


public class ItemLogicalTest {

    Position position = new Position();
    ItemLogical itemLogical;
    String texturePath;
    boolean onCard;


    @BeforeEach
    public void setup(){
        onCard=false;
        texturePath="java/item/test.jpg";
        position.setPosition(4,3);
        itemLogical= new ItemLogical(texturePath,position,onCard);
    }


    @Test
    public void getPositionTest(){
        Position pos = new Position();
        pos.setPosition(4,3);
        assertEquals(pos.getX(),itemLogical.getPosition().getX());
        assertEquals(pos.getY(),itemLogical.getPosition().getY());
    }

    @Test
    public void setPositionTest(){
        Position pos = new Position();
        pos.setPosition(6,0);
        Position newPosition = new Position();
        newPosition.setPosition(6,0);
        itemLogical.setPosition(newPosition);
        assertEquals(pos.getX(),itemLogical.getPosition().getX());
        assertEquals(pos.getY(),itemLogical.getPosition().getY());
    }

    @Test
    public void getTexturePathTest(){
        String path="java/item/test.jpg";
        assertEquals(path,itemLogical.getTexturePath());
    }

    @Test
    public void setTexturePathTest(){
        String path="item/test.jpg";
        String newPath="item/test.jpg";
        itemLogical.setTexturePath(newPath);
        assertEquals(path,itemLogical.getTexturePath());
    }

    @Test
    public void isOnCard(){
        assertFalse(itemLogical.isOnCard());
    }

    @Test
    public void setOnCardTest(){
        itemLogical.setOnCard(true);
        assertTrue(itemLogical.isOnCard());
    }

    //TODO: if name builder is implemented, make better Tests
    @Test
    public void getNameTest(){
        itemLogical.setName("test");
        assertEquals("test",itemLogical.getName());
    }




}
