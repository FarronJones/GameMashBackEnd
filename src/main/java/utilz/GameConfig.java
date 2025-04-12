package utilz;

public class GameConfig {
    public static final float SCALE = 2.0f;
    public static final int TILE_DEFAULT_SIZE = 32;
    public static final int TILES_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE);

    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;

    public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
}