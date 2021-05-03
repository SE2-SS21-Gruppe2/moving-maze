package se2.gruppe2.moving_maze.tile;

public class TileFactory {

    public Tile getLTile(){
        Tile lTile = new Tile(true, true, false, false, TileType.L_TILE);

        return lTile;
    }

    public Tile getITile(){
        return new Tile();
    }

    public Tile getTTile(){
        return new Tile();
    }

    public Tile getTileByType(TileType tileType){
        return new Tile();
    }

}
