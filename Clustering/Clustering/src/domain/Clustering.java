package domain;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * The Clustering class purpose is to create a connection point between the user interface and the game logic.
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Naralia.
 * @version 2024-11-15
 */
public class Clustering {
    private Board board;

    /**
     * Constructor that creates a board with the initial settings which are (4x4), 50%.
     */
    public Clustering() {
        this.board = new Board(4, 4, 50);
    }

    /**
     * Constructor made for the sole purpose of running Unit Test making a board with a given arrangement of tiles.
     * @param arrangement Jagged array with Colors in which the tiles will be placed.
     */
    public Clustering(Color[][] arrangement) {
        this.board = new Board(arrangement);
    }

    /**
     * Changes the initial configuration of the board.
     * @param rows Amount of rows that the board will change.
     * @param columns Amount of columns that the board will change.
     * @param percentage Percentage of tiles that the board needs to have.
     */
    public void resizeBoard(int rows, int columns, int percentage) {
        board.changeConfiguration(rows, columns, percentage);
    }

    /**
     * Moves all tiles one position to the given direction if possible.
     * @param direction Direction in which the board will be tilted.
     * @throws BoardException If the direction is not valid.
     */
    public void tilt(char direction) throws BoardException {
        List<Character> directions = new ArrayList<>(Arrays.asList('u', 'l', 'd', 'r'));
        if (!directions.contains(direction)) {
            throw new BoardException(BoardException.INVALID_MOVE);
        }
        switch (direction) {
            case 'u':
                board.tiltUp();
                break;
            case 'l':
                board.tiltLeft();
                break;
            case 'd':
                board.tiltDown();
                break;
            case 'r':
                board.tiltRight();
                break;
        }
    }

    /**
     * Gets the score achieved by the player.
     * @return Game score.
     */
    public int getScore() {
        return board.getScore();
    }

    /**
     * Gets the amount of moves done by the player.
     * @return Game moves.
     */
    public int getMoves() {
        return board.getMoves();
    }

    /**
     * Saves a game in a ".ser" file.
     * @param file Name and path in which the game configuration will be saved.
     */
    public void saveGame(File file) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(board);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a custom game after reading a ".ser" file.
     * @param file Name and path of the ".ser" file that will be read.
     */
    public void loadGame(File file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Board readBoard = (Board) in.readObject();
            board = new Board(readBoard);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the colors of each tile placed in the board.
     * @return Arrangement of colors.
     */
    public Color[][] getTilesColors() {
        Tile[][] gameBoard = board.getTiles();
        int rows = gameBoard.length;
        int columns = gameBoard[0].length;
        Color[][] colors = new Color[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (gameBoard[i][j] != null) {
                    colors[i][j] = gameBoard[i][j].getColor();
                }
            }
        }
        return colors;
    }

    /**
     * Changes one tile color and updates the score if needed.
     * @param row Row of the tile.
     * @param column Column of the tile.
     * @param color Color that the tile will be changed.
     */
    public void changeTileColor(int row, int column, Color color) {
        board.changeColor(row, column, color);
    }
}
