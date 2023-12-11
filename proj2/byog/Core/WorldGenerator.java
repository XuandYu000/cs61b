package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.Pos;
import org.junit.Test;

import java.nio.channels.Pipe;
import java.util.*;

/**
 * @author overlookingview
 * @Description proj2 phase1 create a random world.
 * @create 2023-12-07 下午8:10
 */
public class WorldGenerator {
    private static final int MaxRoomWidth = 6;
    private static final int MaxRoomHeight = 6;
    private static final String North = "N";
    private static final String South = "S";
    private static final String West = "W";
    private static final String East = "E";
    private TETile[][] world;
    private Random random;
    private int Width;
    private int Height;
    private Position initialP;

    WorldGenerator(int w, int h, int initialX, int initialY, long seed) {
        this.Width = w;
        this.Height = h;
        this.initialP = new Position(initialX, initialY);
        this.random = new Random(seed);
    }

    WorldGenerator(int w, int h, int initialX, int initialY) {
        this.Width = w;
        this.Height = h;
        this.initialP = new Position(initialX, initialY);
        this.random = new Random();
    }

    // fill all tiles with  Tile.Nothing.
    private void initial() {
        world = new TETile[Width][Height];
        for(int i = 0; i <  Width; i++) {
            for(int j = 0; j < Height; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    // Returns a world generated with given random seed
    private void generate() {
        initial();
        Position[] positions = RandomPositions(MaxRoomWidth, MaxRoomHeight, initialP, North);
        if(positions == null) return ;
        Position bottomLeft = positions[0], topRight = positions[1];
        makeRoom(bottomLeft, topRight);
        world[initialP.getX()][initialP.getY()] = Tileset.LOCKED_DOOR;

        triExit(bottomLeft, topRight, North);
    }

    /*Returns random positions for a new room on the given direction of entryPosition if available,
    otherwise returns null*/
    private Position[] RandomPositions(int roomWitdth, int roomHeight, Position initialP, String dir) {
        int doorX = initialP.getX();
        int doorY = initialP.getY();
        Position bottomLeft, topRight;
        int offsetX = random.nextInt(roomWitdth);
        int offsetY = random.nextInt(roomHeight);
        switch (dir) {
            case North: {
                bottomLeft = new Position(doorX - offsetX - 1, doorY);
                topRight = new Position(bottomLeft.getX() + roomWitdth + 1, bottomLeft.getY() + roomHeight + 1);
                break;
            }
            case South: {
                bottomLeft = new Position(doorX - offsetX - 1, doorY - roomHeight - 1);
                topRight = new Position(bottomLeft.getX() + roomWitdth + 1, doorY);
                break;
            }
            case West: {
                bottomLeft = new Position(doorX - roomWitdth - 1, doorY - offsetY - 1);
                topRight = new Position(doorX, bottomLeft.getY() + roomHeight + 1);
                break;
            }
            default: {
                bottomLeft = new Position(doorX, doorY - offsetY - 1);
                topRight = new Position(doorX + roomWitdth + 1, bottomLeft.getY() + roomHeight + 1);
                break;
            }
        }
        if(Available(bottomLeft, topRight)) {
            return new Position[]{bottomLeft, topRight};
        } else {
            return null;
        }
    }

    private Object[] RandomExit(Position bl, Position tr, String dir) {
        int blX = bl.getX(), blY = bl.getY();
        int trX = tr.getX(), trY = tr.getY();
        int width = trX - blX - 1, height = trY - blY - 1;

        // find the next direction
        String nextDir;
        List<String> directions = new ArrayList<>();
        directions.add(East);
        directions.add(West);
        directions.add(South);
        directions.add(North);
        directions.remove(GetReverseDir(dir));
        nextDir = directions.get(random.nextInt(directions.size()));

        // find the exit's position
        Position nextPosition;
        switch (nextDir) {
            case North :{
                nextPosition = new Position(trX - random.nextInt(width) - 1, trY);
                break;
            }
            case South :{
                nextPosition = new Position(blX + random.nextInt(width) + 1, blY);
                break;
            }
            case East :{
                nextPosition = new Position(trX, trY - random.nextInt(height) - 1);
                break;
            }
            default :{
                nextPosition = new Position(blX, blY + random.nextInt(height) + 1);
            }
        }

        return new Object[]{nextPosition, nextDir};
    }

    private String GetReverseDir(String dir) {
        switch (dir){
            case North :{
                return South;
            }
            case South :{
                return North;
            }
            case West :{
                return East;
            }
            default :{
                return West;
            }
        }
    }

    private void makeRoom(Position bottomLeft, Position topRight) {
        for (int i = bottomLeft.getX(); i <= topRight.getX(); i++) {
            for (int j = bottomLeft.getY(); j <= topRight.getY(); j++) {
                if(i == bottomLeft.getX() || i == topRight.getX() || j == bottomLeft.getY() || j == topRight.getY()) {
                    world[i][j] = Tileset.WALL;
                } else {
                    world[i][j] = Tileset.FLOOR;
                }
            }
        }
    }

    private void makeEntrance(Position p) {
        world[p.getX()][p.getY()] = Tileset.FLOOR;
    }

    private void makeExit(Position p) {
        world[p.getX()][p.getY()] = Tileset.FLOOR;
    }

    /*
    * Check whether the space the room will occupy is available.
    *
    * @param bl     the bottom-left point
    * @param tr     the top-right point
    * */
    private boolean Available(Position bl, Position tr) {
        if(bl.getX() < 0 || bl.getX() >= Width || tr.getX() < 0 || tr.getX() >= Width ||
           bl.getY() < 0 || bl.getY() >= Height || tr.getY() < 0 || tr.getY() >= Height) {
            return false;
        }
        for(int i = bl.getX(); i <= tr.getX(); i++) {
            for(int j = bl.getY(); j < tr.getY(); j++) {
                if(!world[i][j].equals(Tileset.NOTHING)){
                     return false;
                }
            }
        }
        return true;
    }

    private void biExit(Position bl, Position tr, String dir) {
        Object[] ExitAndDirection1 = RandomExit(bl, tr, dir);
        Position exitPosition1 = (Position) ExitAndDirection1[0];
        String Dir1 = (String) ExitAndDirection1[1];

        Object[] ExitAndDirection2 = RandomExit(bl, tr, dir);
        while (ExitAndDirection2[1].equals(ExitAndDirection1[1])) {
            ExitAndDirection2 = RandomExit(bl, tr, dir);
        }
        Position exitPosition2 = (Position) ExitAndDirection2[0];
        String Dir2 = (String) ExitAndDirection2[1];

        RecursiveAddRandomRoom(exitPosition1, Dir1);
        RecursiveAddRandomRoom(exitPosition2, Dir2);
    }

    private void triExit(Position bl, Position tr, String dir) {
        Object[] ExitAndDirection1 = RandomExit(bl, tr, dir);
        Position exitPosition1 = (Position) ExitAndDirection1[0];
        String Dir1 = (String) ExitAndDirection1[1];

        Object[] ExitAndDirection2 = RandomExit(bl, tr, dir);
        while (ExitAndDirection2[1].equals(ExitAndDirection1[1])) {
            ExitAndDirection2 = RandomExit(bl, tr, dir);
        }
        Position exitPosition2 = (Position) ExitAndDirection2[0];
        String Dir2 = (String) ExitAndDirection2[1];

        Object[] ExitAndDirection3 = RandomExit(bl, tr, dir);
        while (ExitAndDirection3[1].equals(ExitAndDirection2[1]) || ExitAndDirection3[1].equals(ExitAndDirection1[1])) {
            ExitAndDirection3 = RandomExit(bl, tr, dir);
        }
        Position exitPosition3 = (Position) ExitAndDirection3[0];
        String Dir3 = (String) ExitAndDirection3[1];

        RecursiveAddRandomRoom(exitPosition1, Dir1);
        RecursiveAddRandomRoom(exitPosition2, Dir2);
        RecursiveAddRandomRoom(exitPosition3, Dir3);
    }

    private void  RecursiveAddRandomRoom(Position exitPosition, String dir) {
        int exitX = exitPosition.getX(), exitY = exitPosition.getY();
        int w = random.nextInt(MaxRoomWidth) + 1, h = random.nextInt(MaxRoomHeight) + 1;

        Position entryPosition;
        Position[] lrPositions;
        switch (dir) {
            case North :{
                entryPosition = new Position(exitX, exitY + 1);
                break;
            }
            case South :{
                entryPosition = new Position(exitX, exitY - 1);
                break;
            }
            case West :{
                entryPosition = new Position(exitX - 1, exitY);
                break;
            }
            default :{
                entryPosition = new Position(exitX + 1, exitY);
                break;
            }
        }

        lrPositions = RandomPositions(w, h, entryPosition, dir);
        if(lrPositions != null) {
            makeExit(exitPosition);
            Position bottomLeft = lrPositions[0], topRight = lrPositions[1];
            makeRoom(bottomLeft, topRight);
            makeEntrance(entryPosition);
            switch (random.nextInt(3) + 1) {
                case 2:
                    biExit(bottomLeft, topRight, dir);
                    break;
                default:
                    triExit(bottomLeft, topRight, dir);
                    break;
            }
        }
    }

    public static void main(String args[]) {
        int w = 60;
        int h = 60;
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(w, h);
        WorldGenerator wg = new WorldGenerator(w, h, 40, 5, 42);
        wg.generate();

        // draw the world to the screen
        ter.renderFrame(wg.world);
    }
}
