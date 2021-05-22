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

    public static Tile getYellowStartTile() {
        return new StartTile("gameboard/yellowtile.png");
    }

    public static Tile getBlueStartTile() {
        return new StartTile("gameboard/bluetile.png");
    }

    public static Tile getRedStartTile() {
        return new StartTile("gameboard/redtile.png");
    }

    public static Tile getGreenStartTile() {
        return new StartTile("gameboard/greentile.png");
    }

}
