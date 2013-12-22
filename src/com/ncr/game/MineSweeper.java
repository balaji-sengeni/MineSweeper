package com.ncr.game;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: balaji
 * Date: 15/12/2013
 * Time: 16:49
 * To change this template use File | Settings | File Templates.
 */

/**
 * MineSweeper game, using a TextBoard for
 * display purposes and a Scanner to parse commands entered at the
 * keyboard.
 * Grids should be 10 cells wide by 5 cells high.
 * Each game should contain 3 mines distributed randomly throughout the grid.
 */
public class MineSweeper {

    // The game board
    private Board board;

    // Various flags used by play() and doCommand()
    private boolean done, quit, win;

    // Contents of last cell revealed
    private int lastCell;

    //Constants for width, height and no. of mines in the board
    private static int height = 5;
    private static int width = 10;
    private static int noOfMines = 3;

    //Co-ordinate input pattern : e.g.: A0, J2, ...
    private static String coordinatePattern = "^[A-J]{1}[0-4]+";

    /**
     * Create and initialise a new game of the given dimensions.
     */
    public MineSweeper(int width, int height, int mines) {
        board = new TextBoard(width, height, mines);
        done = win = quit = false;
    }

    /**
     * start the game.
     */
    public void play() throws IOException {
        Scanner console = new Scanner(System.in);// Java console Scanner

        // Loop until game over, for whatever reason
        while (!done) {

            // Redraw board
            board.draw();
            System.out.print("Enter guess (e.g. A2):");
            String inputCoordinates = console.next().trim();
            if (isInValidCoordinates(inputCoordinates)) {
                System.out.println("** Invalid Coordinate - Try Again **  "+inputCoordinates);
                continue;
            } else {
                int x = ((int)inputCoordinates.charAt(0) - 65);
                int y = Character.getNumericValue(inputCoordinates.charAt(1));
                doCommand(x, y);
            }

            // Check whether the game is up.  If the number of unknown cells = the
            // number of mines, you must have won (by finding all the mines).  If,
            // on the other hand, you revealed a mine then lost the game
            if (board.getUnknown() == board.getMines()) {
                done = win = true;
            }
            else if (lastCell == Board.MINE) {
                done = true;
            }

            // Clean any remaining crap out of the input stream.
            System.in.skip(System.in.available());
        }

        // Reveal everything, just so the stats are correct
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                board.reveal(i, j);
            }
        }

        // Re-draw the board and print out messages
        board.draw();
        if (win) {
            System.out.println("You win!");
        }
        else if (!quit) {
            System.out.println("You hit a mine! Game Over.");
        }
    }

    //check invalid co-ordinate entered by the user
    private boolean isInValidCoordinates(String inputCoordinates) {
        return (inputCoordinates==null
                || !inputCoordinates.matches(coordinatePattern)
                || inputCoordinates.isEmpty()
                || inputCoordinates.length() != 2);
    }

    /**
     * Reveal a cell on the board with the user entered co-ordinates
     * @param x
     * @param y
     */
    private void doCommand(int x, int y) {
        System.out.flush();
        // Reveal a cell
        lastCell = board.reveal(x, y);
        if (lastCell == 0) {
            board.revealMore(x, y);
        }
    }

    public static void main(String[] args) throws IOException {
        MineSweeper game = new MineSweeper(width, height, noOfMines);
        game.play();
    }
}
