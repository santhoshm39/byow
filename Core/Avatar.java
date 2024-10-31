package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class Avatar {
    // Check error at N1123S
    private int currX;
    private int currY;
    private TETile avatar = Tileset.AVATAR;
    private TETile[][] ourWorld;
    private String currentMoves = "";
    private String oldMoves;
    private int worldSeed;
    private TERenderer ourTer;

    public Avatar(TETile[][] world, int seed, String prevMoves) {
        ourWorld = world;
        findStart();
        ourWorld[currX][currY] = avatar;
        worldSeed = seed;
        oldMoves = prevMoves;
    }

    public String getCurrentMoves() {
        return currentMoves;
    }

    public void move(char dir) {
        if (!validMove(dir)) {
            return;
        }
        ourWorld[currX][currY] = Tileset.FLOOR;
        if (dir == 'w' || dir == 'W') {
            currY += 1;
        } else if (dir == 'a' || dir == 'A') {
            currX -= 1;
        } else if (dir == 's' || dir == 'S') {
            currY -= 1;
        } else if (dir == 'd' || dir == 'D') {
            currX += 1;
        }
        ourWorld[currX][currY] = avatar;
        currentMoves += Character.toString(dir);
    }

    public void initRenderer() {
        ourTer = new TERenderer();
        ourTer.initialize(Engine.WIDTH, Engine.HEIGHT);
    }

    public void moveWithRender(char dir) {
        if (!validMove(dir)) {
            return;
        }
        ourWorld[currX][currY] = Tileset.FLOOR;
        if (dir == 'w' || dir == 'W') {
            currY += 1;
        } else if (dir == 'a' || dir == 'A') {
            currX -= 1;
        } else if (dir == 's' || dir == 'S') {
            currY -= 1;
        } else if (dir == 'd' || dir == 'D') {
            currX += 1;
        }
        ourWorld[currX][currY] = avatar;
        //currentMoves += Character.toString(dir);

        ourTer.renderFrame(ourWorld);
        StdDraw.pause(100);
    }

    private boolean validMove(char dir) {
        if (dir == 'w' || dir == 'W') {
            return ourWorld[currX][currY + 1].equals(Tileset.FLOOR);
        } else if (dir == 'a' || dir == 'A') {
            return ourWorld[currX - 1][currY].equals(Tileset.FLOOR);
        } else if (dir == 's' || dir == 'S') {
            return ourWorld[currX][currY - 1].equals(Tileset.FLOOR);
        } else if (dir == 'd' || dir == 'D') {
            return ourWorld[currX + 1][currY].equals(Tileset.FLOOR);
        } else {
            return false;
        }
    }

    public void scanKeyboardWASDHover() {
        ourTer = new TERenderer();
        ourTer.initialize(Engine.WIDTH, Engine.HEIGHT);
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == ':') {
                    while (true) {
                        if (StdDraw.hasNextKeyTyped()) {
                            char nextkey = StdDraw.nextKeyTyped();
                            if (nextkey == 'q' || nextkey == 'Q') {
                                // save progress
                                IOUtils ioUtils = new IOUtils();
                                ioUtils.saveGame(oldMoves + currentMoves, worldSeed);
                                // remove for autograder
                                System.exit(0);
                            } else {
                                key = nextkey;
                                break;
                            }
                        }
                    }
                }
                move(key);
                ourTer.renderFrame(ourWorld);
            }

            double mouseX = StdDraw.mouseX();
            double mouseY = StdDraw.mouseY();
            if (mouseY < Engine.HEIGHT) {
                TETile thisTile = getTileType(mouseX, mouseY);
                if (!thisTile.equals(Tileset.NOTHING)) {
                    displayInfo(thisTile, StdDraw.WHITE);
                } else {
                    displayInfo(thisTile, StdDraw.BLACK);
                }
            }
        }
    }

    private void displayInfo(TETile tile, Color color) {
        StdDraw.clear();

        Font font = new Font("Monaco", Font.BOLD, 14);

        StdDraw.setFont(font);

//        ourTer.initialize(Engine.WIDTH, Engine.HEIGHT);
        ourTer.renderFrame(ourWorld);
        StdDraw.setPenColor(color);
        StdDraw.text(2, Engine.HEIGHT - 1, tile.description());
        StdDraw.show();

    }

    private TETile getTileType(double x, double y) {
        int realX = (int) Math.floor(x);
        int realY = (int) Math.floor(y);
        TETile res = ourWorld[realX][realY];
        return res;
    }

    private void findStart() {
        for (int x = 0; x < Engine.WIDTH; x += 1) {
            for (int y = Engine.HEIGHT - 1; y > 0; y -= 1) {
                if (ourWorld[x][y].equals(Tileset.FLOOR)) {
                    currX = x;
                    currY = y;
                    return;
                }
            }
        }
    }

}
