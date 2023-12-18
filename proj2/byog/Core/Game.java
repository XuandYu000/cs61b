package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.HexWorld;
import byog.lab5.Pos;
import edu.princeton.cs.introcs.StdDraw;
import org.junit.Test;

import java.awt.*;
import java.util.Scanner;

public class Game {
    private TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    private static final int WelcomeWIDTH = 40;
    private static final int WelcomeHEIGHT = 40;
    private static final Font SMALL = new Font("Monaco", Font.PLAIN, 20);
    private static final int ENTRYX = 40;
    private static final int ENTRYY = 5;
    private static final String NORTH = "w";
    private static final String EAST = "d";
    private static final String WEST = "a";
    private static final String SOUTH = "s";

    private TETile[][] world;
    private boolean SetUp = true;
    private boolean NewGameMode = false;
    private int PlayX;
    private int PlayY;
    private String Seed = "";


    public static void main(String args[]) {
        Game game = new Game();
        game.playWithKeyboard();
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        DisplayMainMeau();
        KeyboardEvent();
    }

    private void DisplayMainMeau() {
        MainMeau meau = new MainMeau(WelcomeWIDTH, WelcomeHEIGHT);
        while(true) {
            if(StdDraw.hasNextKeyTyped()) {
                char input = StdDraw.nextKeyTyped();
                DealString(Character.toString(input).toLowerCase());
                if(!SetUp) { break;}
            }

            meau.StartGame();

            if (NewGameMode) {
                StdDraw.text(0.5 * WelcomeWIDTH, 0.2 * WelcomeHEIGHT, "Your Seed: " + Seed);
                StdDraw.text(0.5 * WelcomeWIDTH, 0.15 * WelcomeHEIGHT, "Press S to begin the new game!");
            }
            StdDraw.show();
        }

    }

    private void KeyboardEvent() {
        while (true) {
            if(StdDraw.hasNextKeyTyped()) {
                char input = StdDraw.nextKeyTyped();
                DealString(Character.toString(input).toLowerCase());
                ter.renderFrame(world);
                MouseCheck();
            }
        }
    }

    private void MouseCheck() {
        StdDraw.setPenColor(Color.WHITE);
        int MouseX = (int) StdDraw.mouseX();
        int MOuseY = (int) StdDraw.mouseY();
        StdDraw.text(0.95 * WIDTH, 0.97 * HEIGHT, world[MouseX][MOuseY].description());
        StdDraw.line(0, 0.95 * HEIGHT, WIDTH, 0.95 * HEIGHT);
        StdDraw.show();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        DealString(input);
        return world;
    }

    // Recursively deal with the input.
    private void DealString(String input) {
        if(input == null) {
            System.out.println("No input given!");
            System.exit(0);
        }

        String word = Character.toString(input.charAt(0));
        word = word.toLowerCase();
        DealWord(word);

        if(input.length() > 1) {
            input = input.substring(1);
            DealString(input);
        }
    }

    // Deal with the word.
    private void DealWord(String word) {
        if(SetUp) {
            switch (word) {
                case "n": {
                    SwitchNewGame();
                    break;
                }
                case "s": {
                    SetUpNewGame();
                    break;
                }
                default: {
                    try {
                        // This helps the try-catch grammar
                        Long.parseLong(word);
                        Seed+=word;
                    } catch (NumberFormatException e){
                        System.out.println("Invaild input given: " + word);
                        System.exit(0);
                    }
                }
            }
        } else {
            switch (word) {
                case NORTH:
                case SOUTH:
                case WEST:
                case EAST:
                    Move(word);
                    break;
                default:
            }

        }
    }

    // This flag tells we are generating a new world.
    private void SwitchNewGame() {
        NewGameMode = !NewGameMode;
    }

    // SetUp finished
    private void SwitchSetUp() {
        SetUp = !SetUp;
    }

    // Finish extracting the random seed and generate the world.
    private void SetUpNewGame() {
        if(!NewGameMode) {
            String error = "Input string " + "\"S\" given, but no game has been initialized.\n"
                    + "Please initialize game first by input string \"N\" and following random seed"
                    + "numbers";
            System.out.println(error);
            System.exit(0);
        }
        SwitchNewGame();
        CreatNewWorld();
        // Setup finished
        SwitchSetUp();
    }

    private void CreatNewWorld() {
        // creat the world
        Long seed = Long.parseLong(Seed);
        WorldGenerator wg = new WorldGenerator(WIDTH, HEIGHT - 2, ENTRYX, ENTRYY, seed);
        world = wg.generate();

        // set up the player
        PlayX = ENTRYX;
        PlayY = ENTRYY + 1;
        world[PlayX][PlayY] = Tileset.PLAYER;

        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world);
    }

    private boolean Accessible(int x, int y) {
        if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) { return false;}

        if(world[x][y].equals(Tileset.WALL)) {return false;}
        return true;
    }

    private void Move(String dir) {
        int nextX = PlayX;
        int nextY = PlayY;
        switch (dir) {
            case NORTH: {
                nextY ++;
                break;
            }
            case SOUTH: {
                nextY --;
                break;
            }
            case EAST: {
                nextX ++;
                break;
            }
            case WEST: {
                nextX --;
            }
            default:
        }
        if(Accessible(nextX, nextY)) {
            world[PlayX][PlayY] = Tileset.FLOOR;
            // the next position is accessible so the player moves
            PlayX = nextX;
            PlayY = nextY;
            world[PlayX][PlayY] = Tileset.PLAYER;
        }
    }

    @Test
    public void test() {

        TETile[][] World =playWithInputString("N123SWWWWWWWAA");
        ter.renderFrame(world);
        StdDraw.pause(10000);
    }
}
