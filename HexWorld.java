package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    private static int startPoint;
    private static int endPoint;
    private static int row;
    private static TETile[][] world;


    public static void addHexagon(int size) {
        startPoint = 20;
        endPoint = startPoint + size;
        row = 15;

        for (int s = 0; s < size - 1; s += 1) {
            drawOneRow();
            startPoint -= 1;
            endPoint += 1;
            // Check this
            row -= 1;
        }

        drawOneRow();
        row -= 1;
        drawOneRow();
        row -= 1;

        for (int j = 0; j < size - 1; j += 1) {
            startPoint += 1;
            endPoint -= 1;
            drawOneRow();

            // Check this
            row -= 1;
        }



    }

    public static void drawOneRow() {
        for (int x = startPoint; x < endPoint; x += 1) {
            // Check x and y order
            world[x][row] = Tileset.SAND;
        }
    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        world = new TETile[WIDTH][HEIGHT];

        // Fill with nothing tiles
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // addhexagon test
        addHexagon(2);

        ter.renderFrame(world);





        //5 7 9 11 13 * 13 11 9 7 5

        // draws the world to the screen
        ter.renderFrame(world);
    }
}
