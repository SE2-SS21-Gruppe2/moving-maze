package item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se_ii.gruppe2.moving_maze.item.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionTest {
    Position position;

    @BeforeEach
    void setup(){
        position= new Position();
        position.setX(3);
        position.setY(5);
    }

    @Test
    void setXTest(){
        position.setX(4);
        assertEquals(4,position.getX());
    }

    @Test
    void setYTest(){
        position.setY(2);
        assertEquals(2,position.getY());
    }

    @Test
    void getXTest(){
        assertEquals(3,position.getX());
    }

    @Test
    void getYTest(){
        assertEquals(5,position.getY());
    }

    @Test
    void setPositionTest(){
        Position pos= new Position();
        pos.setPosition(3,5);
        assertEquals(position.getX(),pos.getX());
        assertEquals(position.getY(),pos.getY());
    }
}
