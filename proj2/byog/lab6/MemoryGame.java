package byog.lab6;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private static final Color NORMAL = Color.BLUE;
    private static final Color GOOD = Color.CYAN;
    private static final Color BAD = Color.red;
    private static final Font BIG = new Font("Arial", Font.BOLD, 30);
    private static final Font SMALL = new Font("Arial", Font.PLAIN, 15);

    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver = false;
    private boolean playerTurn = true;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
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
        this.rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < n; i++) {
            String word = Character.toString(CHARACTERS[rand.nextInt(26)]);
            str.append(word);
        }

        return str.toString();
    }

    public void drawFrame(String s, Color penColor) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen

        // Helpful UI
        StdDraw.setFont(SMALL);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.textLeft(0, 0.98 * height, "Round: " + round);
        if(playerTurn) {
            StdDraw.text(0.5 * width, 0.98 * height, "Type!");
        } else {
            StdDraw.text(0.5 * width, 0.98 * height, "Watch!");
        }
        StdDraw.textRight(0.9 * width, 0.98 * height, ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)]);
        StdDraw.line(0, 0.96 * height, width, 0.96 * height);

        StdDraw.setFont(BIG);
        StdDraw.setPenColor(penColor);
        StdDraw.text(0.5 * width, 0.5 * height, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for(int i = 0, len = letters.length(); i < len; i++) {
            StdDraw.clear(Color.BLACK);
            StdDraw.pause(500);
            String display = letters.substring(i, i + 1);
            drawFrame(display, NORMAL);
            StdDraw.pause(1000);
        }
        StdDraw.clear(Color.BLACK);
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String typed = "";
        while (n > 0) {
            if(StdDraw.hasNextKeyTyped()) {
                char now = StdDraw.nextKeyTyped();
                typed += Character.toString(now);
                StdDraw.clear(Color.BLACK);
                drawFrame(typed, NORMAL);
                n -= 1;
            }
        }
        StdDraw.clear(Color.BLACK);
        return typed;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 1;

        //TODO: Establish Game loop
        while (!gameOver) {
            drawFrame("Round:  " + round, NORMAL);
            StdDraw.pause(1000);
            StdDraw.clear(Color.BLACK);

            playerTurn = !playerTurn;
            String expected = generateRandomString(round);
            flashSequence(expected);

            playerTurn = !playerTurn;
            String Input = solicitNCharsInput(round);

            if(expected.equals(Input)) {
                round ++;
                drawFrame("You passed round: " + round + "!", GOOD);
                StdDraw.clear(Color.BLACK);
                StdDraw.pause(500);
            } else {
                gameOver = !gameOver;
            }
        }
        drawFrame("Game Over! You made it to round: "+ (round - 1), BAD);
    }

}
