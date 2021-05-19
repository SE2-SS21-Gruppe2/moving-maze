package tile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import se_ii.gruppe2.moving_maze.tile.ITile;
import se_ii.gruppe2.moving_maze.tile.LTile;
import se_ii.gruppe2.moving_maze.tile.TTile;
import se_ii.gruppe2.moving_maze.tile.TileFactory;
import se_ii.gruppe2.moving_maze.tile.Tile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TileFactoryTest {


    @Test
    public void getLTileTest(){
        assertTrue(TileFactory.getLTile().isOpenTop());
        assertFalse(TileFactory.getLTile().isOpenBottom());
        assertFalse(TileFactory.getLTile().isOpenLeft());
        assertTrue(TileFactory.getLTile().isOpenRight());
        assertEquals("gameboard/ltile.png",TileFactory.getLTile().getTexturePath());
    }

    @Test
    public void getTTileTest(){
        assertTrue(TileFactory.getTTile().isOpenTop());
        assertTrue(TileFactory.getTTile().isOpenBottom());
        assertFalse(TileFactory.getTTile().isOpenLeft());
        assertTrue(TileFactory.getTTile().isOpenRight());
        assertEquals("gameboard/ttile.png",TileFactory.getTTile().getTexturePath());
    }

    @Test
    public void getITileTest(){
        assertTrue(TileFactory.getITile().isOpenTop());
        assertTrue(TileFactory.getITile().isOpenBottom());
        assertFalse(TileFactory.getITile().isOpenLeft());
        assertFalse(TileFactory.getITile().isOpenRight());
        assertEquals("gameboard/itile.png",TileFactory.getITile().getTexturePath());
    }
}
