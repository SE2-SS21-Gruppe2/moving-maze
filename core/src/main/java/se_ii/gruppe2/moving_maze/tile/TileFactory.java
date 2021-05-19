package se_ii.gruppe2.moving_maze.tile;

public class TileFactory {

    public static Tile getLTile(){
        return new LTile();
    }

    public static Tile getITile(){
        return new ITile();
    }

    public static Tile getTTile(){
        return new TTile();
    }

    public Tile getTileByType(TileType tileType){
        switch (tileType) {
            case T_TILE:
                return getTTile();

            case I_TILE:
                return getITile();

            case L_TILE:
                return getLTile();

            default:
                return null;
        }
    }

}
