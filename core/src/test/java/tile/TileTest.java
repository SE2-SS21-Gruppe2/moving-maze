package tile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import se2.gruppe2.moving_maze.item.ItemLogical;
import se2.gruppe2.moving_maze.tile.Tile;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TileTest {

    Tile tileLogical;
    @Mock
    ItemLogical item;



    @BeforeEach
    public void setup(){
        tileLogical = Mockito.mock(
                Tile.class,
                Mockito.CALLS_REAL_METHODS
        );
    }

    @Test
    public void appleyRotationTest(){
        assertEquals(tileLogical,tileLogical.applyRotation(90f));
        assertEquals(90f,tileLogical.getRotationDegrees());
    }

    @Test
    public void getTexturePathTest(){
        tileLogical.setTexturePath("test");
        assertEquals("test",tileLogical.getTexturePath());
    }

    @Test
    public void getItemTest(){
         item = new ItemLogical();
    }
}
