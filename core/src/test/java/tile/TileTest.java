package tile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import se2.gruppe2.moving_maze.item.ItemLogical;
import se2.gruppe2.moving_maze.tile.LTile;
import se2.gruppe2.moving_maze.tile.TileFactory;
import se2.gruppe2.moving_maze.tile.TileLogical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TileTest {

    TileLogical tileLogical;
    @Mock
    ItemLogical item;



    @BeforeEach
    public void setup(){
        tileLogical = Mockito.mock(
                TileLogical.class,
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
