package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Room {
    private int width;
    private int height;
    private int bottomLeftX;
    private int bottomLeftY;
    private int usage;
    private TETile[][] ourWorld;

    Room(int w, int h, int blx, int bly, TETile[][] world) {
        width = w;
        height = h;
        bottomLeftX = blx;
        bottomLeftY = bly;
        usage = 0;
        ourWorld = world;
        build();
    }

    private void build() {
        for (int x = bottomLeftX; x < bottomLeftX + width; x += 1) {
            for (int y = bottomLeftY; y < bottomLeftY + height; y += 1) {
                if (ourWorld[x][y].equals(Tileset.NOTHING)) {
                    usage += 1;
                }
                if (ourWorld[x][y].equals(Tileset.FLOOR)) {
                    continue;
                } else if (x == bottomLeftX || x == (bottomLeftX + width) - 1
                        || y == bottomLeftY || y == (bottomLeftY + height) - 1) {
                    ourWorld[x][y] = Tileset.WALL;
                } else {
                    ourWorld[x][y] = Tileset.FLOOR;
                }
            }
        }
    }

    public int getUsage() {
        return usage;
    }

    public int getBottomLeftX() {
        return bottomLeftX;
    }

    public int getBottomLeftY() {
        return bottomLeftY;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
