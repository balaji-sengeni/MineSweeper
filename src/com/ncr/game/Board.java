package com.ncr.game;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: balaji
 * Date: 15/12/2013
 * Time: 15:52
 * To change this template use File | Settings | File Templates.
 */
/**
 * “Minesweeper” game
 *  Upon starting, the application prints a grid representing a minefield
 *      The grid contains cells and also a coordinate system to reference individual cells
 *      Cells can be either empty or contain a mine
 *      The placement of mines is random and differs each game
 *  The content of cells is initially hidden & revealed by asking the user to select a cell to reveal
 *  Revealing a cell will:
 *      Display the number of adjacent mines (if any). An adjacent cell is one of the 8 directly neighbouring cells.
 *      Reveal all non-mine adjacent cells.
 *  If a cell containing a mine is revealed the game is lost.
 *  The game is won once the only unrevealed cells contain mines.
 *  The draw() method is abstract and must be implemented in a subclass to give the board
 *  any kind of visual representation.
 */
public abstract class Board {

    // Board size
    protected int width, height;

    // Number of mines on the board
    protected int numMines;

    // Number of cells yet to be revealed
    protected int numUnknown;

    // Indicates where the mines are hidden
    protected boolean[][] mines;

    //current state of the board
    protected int[][] board;

    // Constants for cell contents.
    public static final int UNKNOWN = -1;
    public static final int MINE = -2;


    /**
     * Create a new game board with the given width * height and number of mines.
     * Mines are distributed randomly throughout the grid.
     */
    public Board(int width, int height, int numMines) {

        this.width = width;
        this.height = height;
        this.numMines = numMines;
        this.numUnknown = width * height;

        // Allocate storage for game board and mines
        mines = new boolean[width][height];
        board = new int[width][height];

        // Clear the board
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                mines[i][j] = false;
                board[i][j] = UNKNOWN;
            }
        }

        // Randomly allocate mines. The loop runs until numMines mines have been
        // placed on the board.
        int cells = width * height;
        int temp = 0;
        Random rand = new Random();

        while (temp < numMines) {
            int cell = rand.nextInt();
            cell = (cell < 0 ? -cell : cell)%cells;
            if (!mines[cell%width][cell/width]) {
                mines[cell%width][cell/width] = true;
                temp++;
            }
        }
    }


    /**
     * draw the board .This method must be implemented by subclasses.
     */
    public abstract void draw();

    /**
     * Revealing a cell at position (x, y) on the board will:
     * Display the number of adjacent mines (if any). An adjacent cell is one of the 8 directly neighbouring cells.
     * Reveal all non-mine adjacent cells.
     * special constant MINE indicating that the cell contains a mine.
     */
    public int reveal(int x, int y) {
        switch (board[x][y]) {
            case UNKNOWN:
                // One less unknown cell now
                numUnknown--;
                if (mines[x][y]) {
                    board[x][y] = MINE;
                }
                else {
                    // How many mines in the vicinity?
                    board[x][y] = adjacentMines(x, y);
                }
                break;
        }
        // Return the revealed value
        return board[x][y];
    }


    /**
     * Reveal the contents of more cells around cell (x, y).  For each of the
     * (up to) eight cells surrounding (x, y):
     *    - if the cell contents are currently unknown and the cell does *not*
     *      contain a mine, the contents of the cell are revealed.
     *    - if the revealed cell is empty and has no neighbouring mines,
     *      revealMore() is called recursively on it.
     */
    public void revealMore(int x, int y) {
        int minx, miny, maxx, maxy;
        int result = 0;

        // not to check beyond the edges of the board
        minx = (x <= 0 ? 0 : x - 1);
        miny = (y <= 0 ? 0 : y - 1);
        maxx = (x >= width - 1 ? width : x + 2);
        maxy = (y >= height - 1 ? height : y + 2);

        // Loop over all surrounding cells
        for (int i = minx; i < maxx; i++) {
            for (int j = miny; j < maxy; j++) {
                if (!mines[i][j] && board[i][j] == UNKNOWN) {
                    reveal(i, j);
                    if (board[i][j] == 0) {
                        // Call recursively
                        revealMore(i, j);
                    }
                }
            }
        }
    }

    /**
     * Return the width of the game board.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Return the height of the game board.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Return the number of mines on the board.
     */
    public int getMines() {
        return numMines;
    }

    /**
     * Return the number of 'unknown' (as in not yet revealed) cells on the game
     * board.
     */
    public int getUnknown() {
        return numUnknown;
    }


    /**
     * Work out how many neighbours of cell (x, y) contain mines.
     * Return the number of mine (explosive) neighbours.
     */
    private int adjacentMines(int x, int y) {
        int minx, miny, maxx, maxy;
        int result = 0;

        // not to check beyond the edges of the board
        minx = (x <= 0 ? 0 : x - 1);
        miny = (y <= 0 ? 0 : y - 1);
        maxx = (x >= width - 1 ? width : x + 2);
        maxy = (y >= height - 1 ? height : y + 2);

        // Check all immediate neighbours for mines
        for (int i = minx; i < maxx; i++) {
            for (int j = miny; j < maxy; j++) {
                if (mines[i][j]) {
                    result++;
                }
            }
        }
        return result;
    }
}