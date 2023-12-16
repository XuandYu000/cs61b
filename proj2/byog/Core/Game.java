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
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
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

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        Start();
    }

    private void Start() {
        MainMeau meau = new MainMeau(WIDTH, HEIGHT);
        String option = meau.StartGame();
        option = option.toLowerCase();

        // TODO: Choose one model to begin the game.
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

        // creat the world
        Long seed = Long.parseLong(Seed);
        WorldGenerator wg = new WorldGenerator(WIDTH, HEIGHT, ENTRYX, ENTRYY, seed);
        world = wg.generate();

        // set up the player
        PlayX = ENTRYX;
        PlayY = ENTRYY + 1;
        world[PlayX][PlayY] = Tileset.PLAYER;

        // Setup finished
        SwitchSetUp();
    }

    private boolean Accessible(int x, int y) {
        if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) { return false;}
        if(world[x][y].equals(Tileset.WALL)) {return false;}
        return true;
    }

    private void Move(String dir) {
        Position last = new Position(PlayX, PlayY);

        switch (dir){
            case NORTH :{
                PlayY ++;
                break;
            }
            case SOUTH: {
                PlayY --;
                break;
            }
            case EAST: {
                PlayX ++;
                break;
            }
            case WEST: {
                PlayX --;
                break;
            }
            default:
        }
        if(Accessible(PlayX, PlayY)) {
            world[last.getX()][last.getY()] = Tileset.FLOOR;
            world[PlayX][PlayY] = Tileset.PLAYER;
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

    @Test
    public void test() {

        TETile[][] World =playWithInputString("N200306Swwwwwwwwwwwwwwwwwwwwwww");

        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        ter.renderFrame(World);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // 等待用户按Enter键

    }
}
