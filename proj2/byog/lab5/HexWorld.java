package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 27;
    private static final int HEIGHT = 30;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    private static class Position {
        private int x;
        private int y;
        private Position(int x, int y) {
            this.x =  x;
            this.y = y;
        }
    }

    /**
     * compute the length of each row
     * @param size the half size of hex
     * @param i ith row (from bottom)
     * @return
     */
    private static int hexRowWidth(int size, int i) {
        if (i < size) {
            return size + 2 * i;
        } else {
            int toTop = 2 * size - i - 1;
            return  size + 2 * toTop;
        }
    }

    /**
     * compute the offset of each row (the offset of 0th is 0)
     */
    private static int hexRowOffset(int size, int i) {
        if (i < size) {
            return -i;
        } else {
            int toTop = 2 * size - i - 1;
            return -toTop;
        }
    }
    /**
     * Adds a row of the same tile.
     * @param world the world to draw on
     * @param p the leftmost position of the row
     * @param width the number of tiles wide to draw
     * @param t the tile to draw
     */
    private static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int i = 0; i < width; i++) {
            int xP = p.x + i;
            int yP = p.y;
            world[xP][yP] = t;
        }
    }

    /**
     * Adds a hexagon to the world.
     * @param world the world to draw on
     * @param p the bottom left coordinate of the hexagon
     * @param size the size of the hexagon
     * @param t the tile to draw
     */
    private static void addHexagon(TETile[][] world, Position p, int size, TETile t) {
        if (size < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        for (int i = 0; i < 2 * size; i++) {
            int yP = p.y + i;
            int xP = p.x + hexRowOffset(size, i);

            Position currP = new Position(xP, yP);

            int width = hexRowWidth(size, i);
            addRow(world, currP, width, t);
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(4);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.WATER;
            case 3: return Tileset.GRASS;
            case 4: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }

    /**
     * draw a column of hexagons
     * @param number the number of hexagons we'll draw
     */
    private static void addVerticalHexagon(TETile[][] world, Position p, int size, int number) {
        if (size < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        for (int i = 0; i < number; i++) {
            addHexagon(world, p, size, randomTile());
            p.y = p.y + 2 * size;
        }
    }

    /**
     * draw a tesselation of hexagons
     */
    public static void addTesselation(TETile[][] world, Position p, int size, int number) {
        int xP = p.x;
        int yP = p.y;
        addVerticalHexagon(world, p, size, number);
        for (int i = 0; i < 2 ; i++) {
            p.x = xP - (i + 1) * (2 * size - 1);
            p.y = yP + size * (i + 1);
            addVerticalHexagon(world, p, size, number - i - 1);
        }
        for (int i = 0; i < 2 ; i++) {
            p.x = xP + (i + 1) * (2 * size - 1);
            p.y = yP + size * (i + 1);
            addVerticalHexagon(world, p, size, number - i - 1);
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Position p = new Position(12, 0);
        addTesselation(world, p, 3, 5);
        ter.renderFrame(world);
    }
}
