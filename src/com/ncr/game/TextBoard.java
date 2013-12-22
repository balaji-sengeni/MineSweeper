package com.ncr.game;

/**
 * Created with IntelliJ IDEA.
 * User: balaji
 * Date: 15/12/2013
 * Time: 16:31
 * To change this template use File | Settings | File Templates.
 */

/**
  * Subclass of com.ncr.game.Board which is a Text Based Minesweeper board
 */
public class TextBoard extends Board {

    // Maximum length of string to hold column/row numbers
    private int colLength, rowLength;

    // Printable versions of column and row numbers
    private String[] colNums, rowNums;

    // Spacing for column-header lines
    private String spacer;


    /**
     * Create a new TextBoard of size width * height and containing numMines mines.
     */
    public TextBoard(int width, int height, int numMines) {
        super(width, height, numMines);

        // Allocate storage for column and row number, and work out how long the
        // respective strings need to be.
        colLength = Integer.toString(width-1).length();
        rowLength = Integer.toString(height-1).length();
        colNums = new String[width];
        rowNums = new String[height];
        char colIndex;

        // Generate column Alphabets.
        for (int i = 0; i < width; i++) {
            colIndex = (char)(i+65);
            StringBuffer col = new StringBuffer(Character.toString(colIndex));
            while (col.length() < colLength) {
                col.insert(0, ' ');
            }
            colNums[i] = col.toString();
        }

        // Generate a spacer for column Alphabets.
        StringBuffer spaces = new StringBuffer();
        for (int i = 0; i < rowLength + 2; i++) {
            spaces.append(' ');
        }
        spacer = spaces.toString();

        // Generate row numbers.
        for (int i = 0; i < height; i++) {
            StringBuffer row = new StringBuffer(Integer.toString(i));
            while (row.length() <= rowLength) {
                row.insert(0, ' ');
            }
            row.append(' ');
            rowNums[i] = row.toString();
        }
    }


    /**
     * Draw the current state of the board on System.out, as follows:
     *  “#” represents a hidden/unknown square not yet revealed
     *  “ “ (space character) represents empty square with no adjacent mines
     *  “1”, “2” & “3” represents that the square is adjacent to 1-3 mines.
     *  “*” represents a bomb (displayed once the game is over)
     */
    public void draw() {

        System.out.println();

        // Do column, complete with Alphabet.
        for (int i = 0; i < colLength; i++) {
            System.out.print(spacer);
            for (int j = 0; j < width; j++) {
                System.out.print(colNums[j].charAt(i));
            }
            System.out.println();
        }

        // Do rows, complete with numbers
        for (int i = 0; i < height; i++) {
            // print row number.
            System.out.print(rowNums[i]);

            // print appropriate content for the cell
            for (int j = 0; j < width; j++) {
                switch (board[j][i]) {
                    case UNKNOWN:
                        System.out.print("#");
                        break;
                    case MINE:
                        System.out.print("*");
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        System.out.print(board[j][i]);
                        break;
                    case 0:
                        System.out.print(" ");
                        break;
                }
            }
            System.out.println();
        }

        System.out.println();

    }
}
