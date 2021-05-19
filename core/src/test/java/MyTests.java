import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se_ii.gruppe2.moving_maze.item.Position;

public class MyTests {

    Position position;



    @BeforeEach
    public void setup(){
        position= new Position();
        position.setX(3);
        position.setY(5);
    }

    @Test
    public void setXTest(){
        position.setX(4);
        assertEquals(4,position.getX());
    }

    @Test
    public void setYTest(){
        position.setY(2);
        assertEquals(2,position.getY());
    }

    @Test
    public void getXTest(){
        assertEquals(3,position.getX());
    }

    @Test
    public void getYTest(){
        assertEquals(5,position.getY());
    }

    @Test
    public void setPositionTest(){
        Position pos= new Position();
        pos.setPosition(3,5);
        assertEquals(position.getX(),pos.getX());
        assertEquals(position.getY(),pos.getY());
    }
}
