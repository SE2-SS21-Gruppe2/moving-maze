package tile;

import org.junit.jupiter.api.Test;

import se_ii.gruppe2.moving_maze.tile.ITile;
import se_ii.gruppe2.moving_maze.tile.LTile;
import se_ii.gruppe2.moving_maze.tile.TTile;
import se_ii.gruppe2.moving_maze.tile.Tile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LTITileTest {
    Tile tile;

    @Test
    public void openTestL(){
        tile = new LTile();
        assertTrue(tile.isOpenTop());
        assertTrue(tile.isOpenRight());
        assertFalse(tile.isOpenLeft());
        assertFalse(tile.isOpenBottom());
        assertEquals("gameboard/ltile.png", tile.getTexturePath());
    }

    @Test
    public void openTestI(){
        tile = new ITile();
        assertTrue(tile.isOpenTop());
        assertFalse(tile.isOpenRight());
        assertFalse(tile.isOpenLeft());
        assertTrue(tile.isOpenBottom());
        assertEquals("gameboard/itile.png", tile.getTexturePath());
    }

    @Test
    public void openTestT(){
        tile = new TTile();
        assertTrue(tile.isOpenTop());
        assertTrue(tile.isOpenRight());
        assertFalse(tile.isOpenLeft());
        assertTrue(tile.isOpenBottom());
        assertEquals("gameboard/ttile.png", tile.getTexturePath());
    }
}
