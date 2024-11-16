package domain;
import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * The Board class purpose is to define the logic of the game and create an arrangement of tiles. The Serializable
 * interface has to be implemented for the sole purpose of loading custom games.
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Naralia.
 * @version 2024-11-15
 */
public class Board implements Serializable {
    private int rows;
    private int columns;
    private Tile[][] tiles;
    private int moves;
    private int score;

    /**
     * Constructor of the Board class.
     * @param h Rows of the board.
     * @param w Columns of the board.
     * @param percentage Percentage of tiles that will be placed in the board.
     * @throws BoardException If the percentage is not valid or the dimensions are negative.
     */
    public Board(int h, int w, int percentage) throws BoardException{
        if (h <= 0 || w <= 0) {
            throw new BoardException(BoardException.INVALID_DIMENSION);
        }
        if (percentage < 0 || percentage > 100) {
            throw new BoardException(BoardException.INVALID_PERCENTAGE);
        }
        this.tiles = new Tile[h][w];
        this.rows = h;
        this.columns = w;
        this.moves = 0;
        this.score = 0;
        int amountTiles = h * w * percentage / 100;
        fillBoard(amountTiles);
        updateScore();
    }

    /**
     * Constructor made for the sole purpose of running Unit Test making a board with a given arrangement.
     * @param arrangement Matrix of colors in which a tile will be place.
     */
    public Board(Color[][] arrangement) {
        this.rows = arrangement.length;
        this.columns = arrangement[0].length;
        this.tiles = new Tile[rows][columns];
        this.moves = 0;
        this.score = 0;
        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < columns; i++) {
                if (arrangement[i][j] != null) {
                    this.tiles[i][j] = new Tile(arrangement[i][j]);
                }
            }
        }
    }

    /**
     * Creates a board by receiving a clone from a Board object.
     * @param board Board clone.
     */
    public Board(Board board) {
        this.rows = board.rows;
        this.columns = board.columns;
        this.tiles = board.tiles;
        this.moves = board.moves;
        this.score = board.score;
    }

    /*
     * Places an amount of tiles in the board in random positions.
     * @param amount Tiles that will be added.
     */
    private void fillBoard(int amount) {
        Random random = new Random();
        Color[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};
        List<int[]> availablePositions = new ArrayList<>();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                if (this.tiles[i][j] == null) {
                    availablePositions.add(new int[] {i, j});
                }
            }
        }
        Collections.shuffle(availablePositions, random);
        for (int i = 0; i < amount; i++) {
            int[] position = availablePositions.get(i);
            int randomRow = position[0];
            int randomColumn = position[1];
            int colorIndex = random.nextInt(colors.length);
            this.tiles[randomRow][randomColumn] = new Tile(colors[colorIndex]);
        }
    }

    /**
     * Changes the initial configuration of the board which is (4x4),50%.
     * @param h Amount of rows that the board will change.
     * @param w Amount of columns that the board will change.
     * @param percentage Percentage of tiles that the board needs to have.
     */
    public void changeConfiguration(int h, int w, int percentage) {
        if (h <= 0 || w <= 0) {
            throw new BoardException(BoardException.INVALID_DIMENSION);
        }
        if (percentage < 0 || percentage > 100) {
            throw new BoardException(BoardException.INVALID_PERCENTAGE);
        }
        int amountTiles = (h * w * percentage / 100);
        int originalAmount = 0;
        Tile[][] tilesCopy = new Tile[h][w];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                if (this.tiles[i][j] != null && i < h && j < w) {
                    tilesCopy[i][j] = this.tiles[i][j];
                    originalAmount++;
                }
            }
        }
        this.rows = h;
        this.columns = w;
        this.tiles = tilesCopy;
        if (amountTiles > originalAmount) {
            fillBoard(amountTiles - originalAmount);
        } else if (amountTiles < originalAmount) {
            removeTiles(originalAmount - amountTiles);
        }
    }

    /*
     * Removes a set of tiles from the board if the amount of tiles required is lower than the ones displayed.
     * @param amount Tiles that will be deleted;
     */
    private void removeTiles(int amount) {
        Random random = new Random();
        List<int[]> takenPositions = new ArrayList<>();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                if (this.tiles[i][j] != null) {
                    takenPositions.add(new int[]{i, j});
                }
            }
        }
        Collections.shuffle(takenPositions, random);
        for (int i = 0; i < amount; i++) {
            int[] position = takenPositions.get(i);
            int randomRow = position[0];
            int randomColumn = position[1];
            this.tiles[randomRow][randomColumn] = null;
        }
    }

    /**
     * Changes one tile color and updates the score if needed.
     * @param row Row of the tile.
     * @param column Column of the tile.
     * @param color Color that the tile will be changed.
     * @throws BoardException If the row and columns are out of bound.
     */
    public void changeColor(int row, int column, Color color) throws BoardException {
        if (row < 0 || row >= this.rows || column < 0 || column >= this.columns) {
            throw new BoardException(BoardException.OUT_OF_BOUNDS);
        }
        this.tiles[row][column].changeColor(color);
        updateScore();
    }

    /**
     * Moves all tiles one position to the left if possible.
     */
    public void tiltLeft() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                if ((j - 1) >= 0 && this.tiles[i][j - 1] == null) {
                    this.tiles[i][j - 1] = this.tiles[i][j];
                    this.tiles[i][j] = null;
                }
            }
        }
        ++moves;
        updateScore();
    }

    /**
     * Moves all tiles one position up if possible.
     */
    public void tiltUp() {
        for (int j = 0; j < this.columns; j++) {
            for (int i = 0; i < this.rows; i++) {
                if ((i - 1) >= 0 && this.tiles[i - 1][j] == null) {
                    this.tiles[i - 1][j] = this.tiles[i][j];
                    this.tiles[i][j] = null;
                }
            }
        }
        ++moves;
        updateScore();
    }

    /**
     * Moves all tiles one position down if possible.
     */
    public void tiltDown() {
        for (int j = 0; j < this.columns; j++) {
            for (int i = this.rows - 1; i >= 0; i--) {
                if ((i + 1) < this.rows && this.tiles[i + 1][j] == null) {
                    this.tiles[i + 1][j] = this.tiles[i][j];
                    this.tiles[i][j] = null;
                }
            }
        }
        ++moves;
        updateScore();
    }

    /**
     * Moves all tiles one position to the right if possible.
     */
    public void tiltRight() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = this.columns - 1; j >= 0; j--) {
                if ((j + 1) < this.columns && this.tiles[i][j + 1] == null) {
                    this.tiles[i][j + 1] = this.tiles[i][j];
                    this.tiles[i][j] = null;
                }
            }
        }
        ++moves;
        updateScore();
    }

    /*
     * To update the score a Breadth First Search (BFS) must be done in order to find all the clusters of tiles.
     * The score is an addition of all clusters score times a hundred. Each cluster score is calculated by the
     * amount of tiles in the cluster (2 to the power of the amount of tiles in the cluster).
     */
    private void updateScore() {
        int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        boolean[][] visited = new boolean[rows][columns];
        int newScore = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!visited[i][j] && this.tiles[i][j] != null) {
                    Color color = this.tiles[i][j].getColor();
                    Stack<int[]> stack = new Stack<>();
                    stack.push(new int[]{i, j});
                    visited[i][j] = true;
                    int clusters = 0;
                    while (!stack.isEmpty()) {
                        int[] tile = stack.pop();
                        int row = tile[0], column = tile[1];
                        clusters++;
                        for (int d = 0; d < 4; d++) {
                            int newRow = row + directions[d][0];
                            int newColumn = column + directions[d][1];
                            if (newRow >= 0 && newColumn >= 0 && newRow < this.rows && newColumn < this.columns
                                    && !visited[newRow][newColumn] && this.tiles[newRow][newColumn] != null
                                    && Objects.equals(this.tiles[newRow][newColumn].getColor(), color)) {
                                visited[newRow][newColumn] = true;
                                stack.push(new int[]{newRow, newColumn});
                            }
                        }
                    }
                    if (clusters > 1) {
                        newScore += (int) Math.pow(2, clusters);
                    }
                }
            }
        }
        score = newScore * 100;
    }

    /**
     * Gets the score achieved by the player.
     * @return Score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the movements done by the player.
     * @return Moves.
     */
    public int getMoves() {
        return moves;
    }

    /**
     * Gets the arrangement of tiles.
     * @return Tiles arrangement.
     */
    public Tile[][] getTiles() {
        return tiles;
    }
}
