package se2.gruppe2.moving_maze.tile;

public class TileFactory {

    public static TileLogical getLTile(){
        return new LTile();
    }

    public static TileLogical getITile(){
        return new ITile();
    }

    public static TileLogical getTTile(){
        return new TTile();
    }

    public TileLogical getTileByType(TileType tileType){
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
