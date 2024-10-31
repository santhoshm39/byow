package byow.lab13;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
//      IMPORTANT STUFF
        args = new String[]{"123"};

        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();

    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    /** @Source https://programming.guide/java/generate-random-character.html */
    public String generateRandomString(int n) {
        // TODO: Generate random string of letters of length n
        String randomString = "";

        for (int i = 0; i < n; i += 1) {
            char randomChar = (char) ('a' + rand.nextInt(26));
            randomString += randomChar;
        }

        return randomString;
    }

//  @Source https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
    public void drawFrame(String s) {
        // TODO: Take the string and display it in the center of the screen
        // TODO: If game is not over, display relevant game information at the top of the screen

        StdDraw.clear();
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(width/2.0, height/2.0 , s);

        StdDraw.show();


    }

    public void flashSequence(String letters) {
        // TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i += 1) {
            String character = letters.substring(i, i + 1);
            drawFrame(character);
            StdDraw.pause(200);
            StdDraw.clear();
            StdDraw.show();
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        // TODO: Read n letters of player input
        String playerString = "";
        int keyCount = 0;
        while (keyCount < n) {
            if (StdDraw.hasNextKeyTyped()) {
                playerString += StdDraw.nextKeyTyped();
                drawFrame(playerString);
                keyCount += 1;
            }
        }

        return playerString;
    }

    public void startGame() {
        // TODO: Set any relevant variables before the game starts
        // TODO: Establish Engine loop

        round = 1;
        gameOver = false;
        while (!gameOver) {
            drawFrame("Round: " + round);
            StdDraw.pause(1000);
            StdDraw.clear();
            StdDraw.show();
            StdDraw.pause(500);

            String randomString = generateRandomString(round);
            flashSequence(randomString);
            String playerInput = solicitNCharsInput(round);
            if (playerInput.equals(randomString)) {
                round += 1;
            } else {
                drawFrame("Game Over! You made it to round: " + round);
                gameOver = true;
            }
        }
    }

}
