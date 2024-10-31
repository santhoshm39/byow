package byow.Core;


import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class World {
    private int width;
    private int height;
    private int dimlimit;
    private static final double THRESHOLD = 0.45;

    private TETile[][] worldGrid;
    private Random rand;
    private Room lastRoom;
    private Room currRoom;
    private int usage;

    public World(TETile[][] world, int seed, int w, int h) {
        this.width = w;
        this.height = h - 3;
        dimlimit = width / 8 - 3;
        worldGrid = world;

        for (int x = 0; x < w; x += 1) {
            for (int y = 0; y < h; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        rand = new Random(seed);
        usage = 0;
        int initWidth = 5;
        int initHeight = 5;
        int initBLX = rand.nextInt(width - 5);
        int initBLY = rand.nextInt(height - 5);
        currRoom = new Room(initWidth, initHeight, initBLX, initBLY, worldGrid);
        usage += currRoom.getUsage();
        lastRoom = null;
    }

    public void buildWorld() {

        while (getWorldUsage() < THRESHOLD) {
            int nextWidth = 5 + rand.nextInt(dimlimit);
            int nextHeight = 5 + rand.nextInt(dimlimit);
            int nextBLX = rand.nextInt(width - nextWidth);
            int nextBLY = rand.nextInt(height - nextHeight);

            lastRoom = currRoom;
            currRoom = new Room(nextWidth, nextHeight, nextBLX, nextBLY, worldGrid);
            usage += currRoom.getUsage();
            connectRooms(lastRoom, currRoom);
        }


    }

    private double getWorldUsage() {
        double asDouble = (double) usage;
        int area = width * height;
        return asDouble / area;
    }

    private void connectRooms(Room start, Room end) {
        int startBLX = start.getBottomLeftX();
        int startBLY = start.getBottomLeftY();

        int endBLX = end.getBottomLeftX();
        int endBLY = end.getBottomLeftY();

        int horizWidth = Math.abs(startBLX - endBLX) + 2;
        int horizHeight = 3;
        int horizBLX = Math.min(startBLX, endBLX) + 2;
        int horizBLY;
        if (startBLX < endBLX) {
            horizBLY = startBLY + 2;
        } else {
            horizBLY = endBLY + 2;
        }
        Hallway horizHall =
                new Hallway(horizWidth, horizHeight, horizBLX, horizBLY, worldGrid);
        usage += horizHall.getUsage();

        int vertWidth = 3;
        int vertHeight = Math.abs(startBLY - endBLY) + 5;
        int vertBLX = horizBLX + horizWidth - 2;
        int vertBLY = Math.min(horizBLY, Math.min(endBLY, startBLY));
        Hallway vertHall =
                new Hallway(vertWidth, vertHeight, vertBLX, vertBLY, worldGrid);
        usage += vertHall.getUsage();

    }
}
