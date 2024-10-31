package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Engine {
    private TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    private static final int MENUWIDTH = 50;
    private static final int MENUHEIGHT = 30;

    private static final Font BIGFONT = new Font("Monaco", Font.BOLD, 30);
    private static final Font SMALLFONT = new Font("Monaco", Font.BOLD, 20);

    private IOUtils ioUtils = new IOUtils();

    private Avatar avatar = null;


    /**
     * Method used for exploring a fresh world.
     * This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {

        StdDraw.setCanvasSize(MENUWIDTH, MENUHEIGHT);

        StdDraw.setCanvasSize(MENUWIDTH * 16, MENUHEIGHT * 16);
        StdDraw.setXscale(0, MENUWIDTH);
        StdDraw.setYscale(0, MENUHEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();


        StdDraw.setFont(BIGFONT);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(MENUWIDTH / 2.0, (MENUHEIGHT / 2.0) + 10,
                "CS61B: The Game");

        StdDraw.setFont(SMALLFONT);
        StdDraw.text(MENUWIDTH / 2.0, (MENUHEIGHT / 2.0) + 2, "New Game (N)");
        StdDraw.text(MENUWIDTH / 2.0, (MENUHEIGHT / 2.0) + 0, "Load Game (L)");
        StdDraw.text(MENUWIDTH / 2.0, (MENUHEIGHT / 2.0) - 2, "Replay Last Save (R)");
        StdDraw.text(MENUWIDTH / 2.0, (MENUHEIGHT / 2.0) - 4, "Quit (Q)");

        StdDraw.show();

        scanKeyboardMenu();


    }

    private void scanKeyboardMenu() {
        Path proj3Dir = Paths.get("savefile.txt");
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'n' || key == 'N') {
                    String seedString = promptSeed();
                    seedString = "N" + seedString + "S";
                    System.out.println(seedString);
                    TETile[][] world = interactWithInputString(seedString);

                    ter.initialize(Engine.WIDTH, Engine.HEIGHT);
                    ter.renderFrame(world);
                    avatar.scanKeyboardWASDHover();
                    break;
                } else if ((key == 'l' || key == 'L') && Files.exists(proj3Dir)) {

                    TETile[][] world = interactWithInputString("L");
                    ter.initialize(Engine.WIDTH, Engine.HEIGHT);
                    ter.renderFrame(world);
                    avatar.scanKeyboardWASDHover();
                    break;
                } else if (key == 'q' || key == 'Q') {
                    System.exit(0);
                } else if ((key == 'r' || key == 'R') && Files.exists(proj3Dir)) {

                    String[] gameData = ioUtils.loadGame();
                    int loadSeed = Integer.parseInt(gameData[1]);
                    String loadMoves = gameData[0];
                    ter.initialize(Engine.WIDTH, Engine.HEIGHT);
                    World w =
                            new World(finalWorldFrame, loadSeed, WIDTH, HEIGHT);
                    w.buildWorld();
                    Avatar replayAvatar =
                            new Avatar(finalWorldFrame, loadSeed, loadMoves);
                    replayAvatar.initRenderer();
                    System.out.println(loadMoves);
                    for (int i = 0; i < loadMoves.length(); i++) {
                        char thisMove = loadMoves.charAt(i);
                        replayAvatar.moveWithRender(thisMove);
                    }
                    replayAvatar.scanKeyboardWASDHover();
                } else {
                    continue;
                }
            } else if (StdDraw.isMousePressed()) {
                double xMouse = StdDraw.mouseX();
                double yMouse = StdDraw.mouseY();

                if (xMouse > 20 && xMouse < 30 && yMouse > 16.4 && yMouse < 17.6) {
                    // Start New Game
                    String seedString = promptSeed();
                    seedString = "N" + seedString + "S";
                    System.out.println(seedString);
                    TETile[][] world = interactWithInputString(seedString);

                    ter.initialize(Engine.WIDTH, Engine.HEIGHT);
                    ter.renderFrame(world);
                    avatar.scanKeyboardWASDHover();
                    break;

                } else if (xMouse > 20 && xMouse < 30 && yMouse > 14.5 && yMouse < 15.4) {
                    // load game
                    TETile[][] world = interactWithInputString("L");
                    ter.initialize(Engine.WIDTH, Engine.HEIGHT);
                    ter.renderFrame(world);
                    avatar.scanKeyboardWASDHover();
                    break;

                } else if (xMouse > 17.5 && xMouse < 32.5 && yMouse > 12.5 && yMouse < 13.5) {
                    //replay game
                    String[] gameData = ioUtils.loadGame();
                    int loadSeed = Integer.parseInt(gameData[1]);
                    String loadMoves = gameData[0];
                    ter.initialize(Engine.WIDTH, Engine.HEIGHT);
                    World w =
                            new World(finalWorldFrame, loadSeed, WIDTH, HEIGHT);
                    w.buildWorld();
                    Avatar replayAvatar =
                            new Avatar(finalWorldFrame, loadSeed, loadMoves);
                    replayAvatar.initRenderer();
                    System.out.println(loadMoves);
                    for (int i = 0; i < loadMoves.length(); i++) {
                        char thisMove = loadMoves.charAt(i);
                        replayAvatar.moveWithRender(thisMove);
                    }
                    replayAvatar.scanKeyboardWASDHover();

                } else if (xMouse > 22 && xMouse < 28 && yMouse > 10 && yMouse < 11.5) {
                    // quit game
                    System.exit(0);
                } else {
                    continue;
                }
            }
        }
    }

    private String promptSeed() {
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        StdDraw.setFont(SMALLFONT);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(MENUWIDTH / 2.0, (MENUHEIGHT / 2.0) + 10,
                "Please enter a seed number and press 'S' to start");
        StdDraw.show();

        String seedString = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (Character.isDigit(key)) {
                    String keyString = Character.toString(key);
                    seedString += keyString;

                    StdDraw.clear(Color.BLACK);
                    StdDraw.text(MENUWIDTH / 2.0, (MENUHEIGHT / 2.0) + 10,
                            "Please enter a seed number and press 'S' to start");
                    StdDraw.text(MENUWIDTH / 2.0, MENUHEIGHT / 2.0, seedString);
                    StdDraw.show();
                } else if (seedString.length() > 1
                        && (key == 's' || key == 'S')) {
                    break;
                }
            }
        }
        return seedString;
    }


    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        Path proj3Dir = Paths.get("savefile.txt");
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        char[] inputArr = input.toCharArray();
        int lastIndex = input.length() - 1;
        int nextMoveIndex = 0;
        int loadSeed = 0;
        if (input.startsWith("L") || input.startsWith("l")) {
            if (!Files.exists(proj3Dir)) {
                throw new IllegalArgumentException();
            }
            String[] gameData = ioUtils.loadGame();
            loadSeed = Integer.parseInt(gameData[1]);
            String loadMoves = gameData[0];
            World w = new World(finalWorldFrame, loadSeed, WIDTH, HEIGHT);
            w.buildWorld();
            this.avatar = new Avatar(finalWorldFrame, loadSeed, loadMoves);
            for (int i = 0; i < loadMoves.length(); i++) {
                char thisMove = loadMoves.charAt(i);
                this.avatar.move(thisMove);
            }
            if (inputArr.length > 1) {
                nextMoveIndex = 1;
            }
        } else if (input.startsWith("n") || input.startsWith("N")) {
            int seed = 0;
            int i = 1;
            while (inputArr[i] != 's' && inputArr[i] != 'S') {
                char numString = inputArr[i];
                seed = seed * 10 + Integer.parseInt(String.valueOf(numString));
                i += 1;
            }
            if (inputArr.length - 1 > i) {
                nextMoveIndex = i + 1;
            }
            loadSeed = seed;
            World w = new World(finalWorldFrame, seed, WIDTH, HEIGHT);
            w.buildWorld();
            this.avatar = new Avatar(finalWorldFrame, seed, "");
        }
        if (nextMoveIndex != 0) {
            for (int i = nextMoveIndex; i < inputArr.length; i++) {
                char nextMove = inputArr[i];
                this.avatar.move(nextMove);
            }
            if (inputArr[lastIndex - 1] == ':' && (inputArr[lastIndex] == 'q'
                    || inputArr[lastIndex] == 'Q')) {
                String movesSoFar = "";
                for (int i = nextMoveIndex; i < inputArr.length - 2; i++) {
                    movesSoFar += inputArr[i];
                }
                if (Files.exists(proj3Dir) && inputArr[0] != 'n' && inputArr[0] != 'N') {
                    String[] gameData = ioUtils.loadGame();
                    movesSoFar = gameData[0] + movesSoFar;
                }
                ioUtils.saveGame(movesSoFar, loadSeed);
            }
        }
        return finalWorldFrame;
    }

    public static void main(String[] args) {
        Engine e = new Engine();
        e.interactWithInputString("n7313251667695476404sasdw");
    }
}
