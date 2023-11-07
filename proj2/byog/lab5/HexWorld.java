package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.text.Position;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 80;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);
    public static int hexRowWidth(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }

        return s + 2 * effectiveI;
    }
    public static int hexRowOffset(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return -effectiveI;
    }
    public static void addRow(TETile[][] world, Pos p, int width, TETile t) {
        for (int xi = 0; xi < width; xi ++) {
            int xCoord = p.x + xi;
            int yCoord = p.y;
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }
    public static void addHexagon(TETile[][] world, Pos p, int s, TETile t) {
        if(s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        // hexagon have 2 * s rows. this code iterates up from the bottom row,
        // which we call row 0.
        for(int yi = 0; yi < 2 * s; yi ++) {
            int thisRowY = p.y + yi;

            int xRowStart = p.x + hexRowOffset(s, yi);
            Pos rowStartP = new Pos(xRowStart, thisRowY);

            int rowWidth = hexRowWidth(s, yi);

            addRow(world, rowStartP, rowWidth, t);
        }
    }


    @Test
    public void testHexRowWidth() {
        assertEquals(3, hexRowWidth(3, 5));
        assertEquals(5, hexRowWidth(3, 4));
        assertEquals(7, hexRowWidth(3, 3));
        assertEquals(7, hexRowWidth(3, 2));
        assertEquals(5, hexRowWidth(3, 1));
        assertEquals(3, hexRowWidth(3, 0));
        assertEquals(2, hexRowWidth(2, 0));
        assertEquals(4, hexRowWidth(2, 1));
        assertEquals(4, hexRowWidth(2, 2));
        assertEquals(2, hexRowWidth(2, 3));
    }

    @Test
    public void testHexRowOffset() {
        assertEquals(0, hexRowOffset(3, 5));
        assertEquals(-1, hexRowOffset(3, 4));
        assertEquals(-2, hexRowOffset(3, 3));
        assertEquals(-2, hexRowOffset(3, 2));
        assertEquals(-1, hexRowOffset(3, 1));
        assertEquals(0, hexRowOffset(3, 0));
        assertEquals(0, hexRowOffset(2, 0));
        assertEquals(-1, hexRowOffset(2, 1));
        assertEquals(-1, hexRowOffset(2, 2));
        assertEquals(0, hexRowOffset(2, 3));
    }
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            default: return Tileset.SAND;
        }
    }

    public static void add_hexagons(TETile[][] hexagons, Pos p, int s) {
        int val_x = (s << 1) - 1;
        int val_y = s;
        int[] num = {3, 4, 5, 4, 3};
        int[] offSet = {-2, -1, 0, 1, 2};
        for(int i = 0; i < 5; i++) {
            Pos top = new Pos(p.x + offSet[i] * val_x, p.y - Math.abs(offSet[i] * val_y));
            addHexagon(hexagons, top, s, randomTile());
            for(int j = 0; j < num[i]; j++) {
                Pos now = new Pos(top.x, top.y - j * (val_y << 1));
                addHexagon(hexagons, now, s, randomTile());
            }
        }
    }

    public static void main(String[] args) {
        int s = 3;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] hexagons = new TETile[WIDTH][HEIGHT];
        for(int i = 0; i < WIDTH; i++) {
            for(int j = 0; j < HEIGHT; j++) {
                hexagons[i][j] = Tileset.NOTHING;
            }
        }

        Pos p = new Pos(40, 70);
        add_hexagons(hexagons, p, s);
//        addHexagon(hexagons, p, s, randomTile());
        ter.renderFrame(hexagons);
    }
}

