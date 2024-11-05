package puzzle;
import shapes.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The Board class represents a game board that contains tiles and holes.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 28 2024
 */
public class Board {
    public static final char STARTING_BOARD = 'S', ENDING_BOARD = 'E';
    public static int rows;
    public static int columns;
    
    private HashMap<String, Tile> tiles;
    private HashMap<String, Hole> holes;
    private char type;
    private Rectangle rectangle;
    private boolean isVisible;

    /**
     * Constructor for objects of the Board class.
     * 
     * @param h The number of rows in the board.
     * @param w The number of columns in the board.
     * @param xPosition The x position of the board on the interface.
     * @param yPosition The y position of the board on the interface.
     * @param board The type of board ('S' for starting, 'E' for ending).
     * @throws BoardExceptions If there is an error in the position or dimensions of the board.
     */
    public Board(int h, int w, int xPosition, int yPosition, char board) throws BoardExceptions {
        boardVerification(h, w, xPosition, yPosition);
        this.rows = h;
        this.columns = w;
        this.rectangle = new Rectangle((h * 40) + 20, (w * 40) + 20, xPosition, yPosition, "brown", false);
        this.tiles = new HashMap<>();
        this.holes = new HashMap<>();
        this.type = board;
        this.isVisible = false;
    }
    
    /*
     * Verifies that the dimensions and positions of the board are valid.
     * 
     * @param h The number of rows in the board.
     * @param w The number of columns in the board.
     * @param xPosition The x position of the board on the interface.
     * @param yPosition The y position of the board on the interface.
     * @throws BoardExceptions If there is an error in the position or dimensions of the board.
     */
    private void boardVerification(int h, int w, int xPosition, int yPosition) throws BoardExceptions {
        if (xPosition < 0 || yPosition < 0) {
            throw new BoardExceptions(BoardExceptions.INVALID_POSITION);
        }
        if (h <= 0 || w <= 0) {
            throw new BoardExceptions(BoardExceptions.DIMENSION_ERROR);
        }
    }
    
    /**
     * Adds a tile to the board at the specified position.
     * 
     * @param row The row where the tile will be added.
     * @param column The column where the tile will be added.
     * @param color The color of the tile.
     * @param type The type of tile (normal, fixed, rough, etc.).
     * @throws BoardExceptions If the location is invalid or there is already a tile at that position.
     * @throws TileExceptions If the type of tile is invalid.
     */
    public void addTile(int row, int column, String color, String type) throws BoardExceptions, TileExceptions {
        addTileVerification(row, column);
        String tuple = Arrays.toString(new int[] {row, column});
        switch (type.toLowerCase()) {
            case "normal":
                this.tiles.put(tuple, new NormalTile(row, column, color, this.rectangle.getXPosition(), this.rectangle.getYPosition()));
                break;
            case "fixed":
                this.tiles.put(tuple, new FixedTile(row, column, color, this.rectangle.getXPosition(), this.rectangle.getYPosition()));
                break;
            case "rough":
                this.tiles.put(tuple, new RoughTile(row, column, color, this.rectangle.getXPosition(), this.rectangle.getYPosition()));
                break;
            case "freelance":
                this.tiles.put(tuple, new FreelanceTile(row, column, color, this.rectangle.getXPosition(), this.rectangle.getYPosition()));
                break;
            case "flying":
                this.tiles.put(tuple, new FlyingTile(row, column, color, this.rectangle.getXPosition(), this.rectangle.getYPosition()));
                break;
            case "sticky":
                this.tiles.put(tuple, new StickyTile(row, column, color, this.rectangle.getXPosition(), this.rectangle.getYPosition()));
                break;
            default:
                throw new TileExceptions(TileExceptions.INVALID_TYPE);
        }
        if (this.isVisible) {
            this.tiles.get(tuple).makeVisible();
        }
    }
    
    /*
     * Verifies that the location to add a tile is valid.
     * 
     * @param row The row where the tile will be attempted to be added.
     * @param column The column where the tile will be attempted to be added.
     * @throws BoardExceptions If the location is invalid or there is already a tile at that position.
     */
    private void addTileVerification(int row, int column) throws BoardExceptions {
        if (row < 0 || row > this.rows || column < 0 || column > this.columns) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
        if (this.tiles.containsKey(Arrays.toString(new int[] {row, column}))) {
            throw new BoardExceptions(BoardExceptions.INVALID_LOCATION);
        }
    }
    
    /**
     * Removes a tile from the board at the specified position.
     * 
     * @param row The row where the tile will be removed.
     * @param column The column where the tile will be removed.
     * @throws BoardExceptions If the location is invalid or there is no tile at that position.
     * @throws TileExceptions If the tile cannot be removed.
     */
    public void deleteTile(int row, int column) throws BoardExceptions, TileExceptions {
        deleteTileVerification(row, column);
        String tuple = Arrays.toString(new int[] {row, column});
        this.tiles.get(tuple).makeInvisible();
        this.tiles.remove(tuple);
    }
    
    /*
     * Verifies that the location to remove a tile is valid.
     * 
     * @param row The row where the tile will be attempted to be removed.
     * @param column The column where the tile will be attempted to be removed.
     * @throws BoardExceptions If the location is invalid or there is no tile at that position.
     * @throws TileExceptions If the tile cannot be removed.
     */
    private void deleteTileVerification(int row, int column) throws BoardExceptions, TileExceptions {
        if (row > this.rows || row <= 0 || column > this.columns || column <= 0) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
        if (!this.tiles.containsKey(Arrays.toString(new int[] {row, column}))) {
            throw new BoardExceptions(BoardExceptions.EMPTY_POSITION);
        }
        if (!this.tiles.get(Arrays.toString(new int[] {row, column})).canBeDeleted()) {
            throw new TileExceptions(TileExceptions.CANT_DELETE);
        }
    }
    
    /**
     * Moves a tile from a starting position to an ending position.
     * 
     * @param starting The starting position of the tile (row and column).
     * @param ending The ending position of the tile (row and column).
     * @throws BoardExceptions If there is an error in the location.
     * @throws PuzzleExceptions If there is a puzzle-related error.
     * @throws TileExceptions If the tile cannot be moved.
     */
    public void moveTile(int[] starting, int[] ending) throws BoardExceptions, PuzzleExceptions, TileExceptions {
        moveTileVerification(starting, ending);
        Tile tile = this.tiles.get(Arrays.toString(starting));
        boolean canFall = (this.holes.containsKey(Arrays.toString(ending)) && tile.canFall());
        if (canFall) {
            if (tile.hasGlue()) {
                tile.deleteGlue();
            }
            tile.makeInvisible();
            this.tiles.remove(Arrays.toString(starting));
        } else {
            tile.setPosition(ending[0], ending[1]);
            this.tiles.remove(Arrays.toString(starting));
            this.tiles.put(Arrays.toString(ending), tile);
        }
    }
    
    /*
     * Verifies that moving a tile is valid.
     * 
     * @param starting The starting position of the tile (row and column).
     * @param ending The ending position of the tile (row and column).
     * @throws BoardExceptions If there is an error in the location.
     * @throws PuzzleExceptions If there is a puzzle-related error.
     * @throws TileExceptions If the tile cannot be moved.
     */
    private void moveTileVerification(int[] starting, int[] ending) throws BoardExceptions, PuzzleExceptions, TileExceptions {
        if (starting.length !=2 || ending.length != 2){
            throw new PuzzleExceptions(PuzzleExceptions.INVALID_TUPLE);
        }
        if (starting[0] > this.rows || starting[0] <= 0 || starting[1] > this.columns || starting[1] <= 0 || ending[0] > this.rows || ending[0] <= 0 || ending[1] > this.columns || ending[1] <= 0) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
        if (!this.tiles.containsKey(Arrays.toString(starting))) {
            throw new BoardExceptions(BoardExceptions.EMPTY_POSITION);
        }
        if (!this.tiles.get(Arrays.toString(starting)).canMove()) {
            throw new TileExceptions(TileExceptions.CANT_MOVE);
        }
    }

    /**
     * Checks if the tile at the specified position has glue.
     * 
     * @param row The row of the tile.
     * @param column The column of the tile.
     * @return true if the tile has glue, false otherwise.
     * @throws BoardExceptions If the location is invalid or there is no tile at that position.
     */
    public boolean doesTileHaveGlue(int row, int column) throws BoardExceptions {
        if (row > this.rows || row <= 0 || column > this.columns || column <= 0) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
        String tuple = Arrays.toString(new int[] {row, column});
        if (!this.tiles.containsKey(tuple)) {
            throw new BoardExceptions(BoardExceptions.EMPTY_POSITION);
        }
        return this.tiles.get(tuple).hasGlue();
    }

    /**
     * Checks if the tile at the specified position can fall.
     * 
     * @param row The row of the tile.
     * @param column The column of the tile.
     * @return true if the tile can fall, false otherwise.
     * @throws BoardExceptions If the location is invalid or there is no tile at that position.
     */
    public boolean canThisTileFall(int row, int column) throws BoardExceptions {
        if (row > this.rows || row <= 0 || column > this.columns || column <= 0) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
        String tuple = Arrays.toString(new int[] {row, column});
        if (!this.tiles.containsKey(tuple)) {
            throw new BoardExceptions(BoardExceptions.EMPTY_POSITION);
        }
        return this.tiles.get(tuple).canFall();
    }
    
    /**
     * Checks if there is a hole at the specified position.
     * 
     * @param row The row of the position to check.
     * @param column The column of the position to check.
     * @return true if there is a hole at that position, false otherwise.
     * @throws BoardExceptions If the location is invalid.
     */
    public boolean doesThisPositionHaveHole(int row, int column) throws BoardExceptions {
        if (row > this.rows || row <= 0 || column > this.columns || column <= 0) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
        if (this.holes.containsKey(Arrays.toString(new int[] {row, column}))) {
            return true;
        }
        return false;
    }
    
    /**
     * Gets the color of the tile at the specified position.
     * 
     * @param row The row of the tile.
     * @param column The column of the tile.
     * @return The color of the tile.
     * @throws BoardExceptions If the location is invalid or there is no tile at that position.
     */
    public String getTileColor(int row, int column) throws BoardExceptions {
        if (row > this.rows || row <= 0 || column > this.columns || column <= 0) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
        String tuple = Arrays.toString(new int[] {row, column});
        if (!this.tiles.containsKey(tuple)) {
            throw new BoardExceptions(BoardExceptions.EMPTY_POSITION);
        }
        return this.tiles.get(tuple).getColor();
    }
    
    /**
     * Adds glue to the tile at the specified position.
     * 
     * @param row The row of the tile.
     * @param column The column of the tile.
     * @param type The type of glue to add.
     * @throws BoardExceptions If the location is invalid or there is no tile at that position.
     * @throws GlueExceptions If there is an error related to the glue.
     * @throws TileExceptions If the tile cannot be glued.
     */
    public void addGlue(int row, int column, String type) throws BoardExceptions, GlueExceptions,  TileExceptions {
        if (row > this.rows || row <= 0 || column > this.columns || column <= 0) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
        String tuple = Arrays.toString(new int[] {row, column});
        if (!this.tiles.containsKey(tuple)) {
            throw new BoardExceptions(BoardExceptions.EMPTY_POSITION);
        }
        if (!this.tiles.get(tuple).canBeGlued()) {
            throw new TileExceptions(TileExceptions.CANT_GLUE);
        }
        this.tiles.get(tuple).addGlue(type);
    }
    
    /**
     * Removes the glue from the tile at the specified position.
     * 
     * @param row The row of the tile.
     * @param column The column of the tile.
     * @throws BoardExceptions If the location is invalid or there is no tile at that position.
     * @throws TileExceptions If there is an error when trying to remove the glue.
     */
    public void deleteGlue(int row, int column) throws BoardExceptions, TileExceptions {
        if (row > this.rows || row <= 0 || column > this.columns || column <= 0) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
        String tuple = Arrays.toString(new int[] {row, column});
        if (!this.tiles.containsKey(tuple)) {
            throw new BoardExceptions(BoardExceptions.EMPTY_POSITION);
        }
        this.tiles.get(tuple).deleteGlue();
    }
    
    /**
     * Creates a hole at the specified position.
     * 
     * @param row The row where the hole will be created.
     * @param column The column where the hole will be created.
     * @throws BoardExceptions If the location is invalid or a hole already exists at that position.
     * @throws HoleExceptions If there is an error related to the hole.
     */
    public void makeHole(int row, int column) throws BoardExceptions, HoleExceptions {
        if (row > this.rows || row <= 0 || column > this.columns || column <= 0) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
        String tuple = Arrays.toString(new int[] {row, column});
        if (this.holes.containsKey(tuple)) {
            throw new BoardExceptions(BoardExceptions.EXISTING_HOLE);
        }
        if (this.tiles.containsKey(tuple)) {
            throw new BoardExceptions(BoardExceptions.CANT_HOLE);
        }
        this.holes.put(tuple, new Hole(row, column, this.rectangle.getXPosition(), this.rectangle.getYPosition()));
        if (this.isVisible) {
            this.holes.get(tuple).makeVisible();
        }
    }
    
    /**
     * Exchanges the board type between the starting board and the ending board.
     */
    public void exchange() {
        int change = (this.columns * 40) + 30;
        if (this.type == Board.STARTING_BOARD) {
            this.type = Board.ENDING_BOARD;
            this.rectangle.moveHorizontal(change);
            for (Tile t : this.tiles.values()) {
                t.moveHorizontal(change);
            }
            for (Hole h : this.holes.values()) {
                h.moveHorizontal(change);
            }
        } else if (this.type == Board.ENDING_BOARD) {
            this.type = Board.STARTING_BOARD;
            this.rectangle.moveHorizontal(-change);
            for (Tile t : this.tiles.values()) {
                t.moveHorizontal(-change);
            }
            for (Hole h : this.holes.values()) {
                h.moveHorizontal(-change);
            }
        }
    }
    
    /**
     * Gets the positions of the tiles.
     * 
     * @return A map of the tile positions.
     */
    public HashMap<String, Tile> tilesPositions() {
        HashMap<String, Tile> tilesPositions = new HashMap<>();
        for (Map.Entry<String, Tile> entry : this.tiles.entrySet()) {
            if (entry.getValue() instanceof NormalTile) {
                tilesPositions.put(entry.getKey(), new NormalTile(entry.getValue()));
            } else if (entry.getValue() instanceof FlyingTile) {
                tilesPositions.put(entry.getKey(), new FlyingTile(entry.getValue()));
            } else if (entry.getValue() instanceof RoughTile) {
                tilesPositions.put(entry.getKey(), new RoughTile(entry.getValue()));
            } else if (entry.getValue() instanceof FixedTile) {
                tilesPositions.put(entry.getKey(), new FixedTile(entry.getValue()));
            } else if (entry.getValue() instanceof FreelanceTile) {
                tilesPositions.put(entry.getKey(), new FreelanceTile(entry.getValue()));
            } else if (entry.getValue() instanceof StickyTile) {
                tilesPositions.put(entry.getKey(), new StickyTile(entry.getValue()));
            }
        }
        return tilesPositions;
    }
    
    /**
     * Replaces the current tiles in the board with a new set of tiles.
     * 
     * @param tileMap A map of the new tiles to add to the board.
     */
    public void changeTiles(HashMap<String, Tile> tileMap) {
        for (Tile t : this.tiles.values()) {
            t.makeInvisible();
        }
        this.tiles.clear();
        for (Map.Entry<String, Tile> entry : tileMap.entrySet()) {
            if (entry.getValue() instanceof NormalTile) {
                this.tiles.put(entry.getKey(), new NormalTile(entry.getValue()));
            } else if (entry.getValue() instanceof FlyingTile) {
                this.tiles.put(entry.getKey(), new FlyingTile(entry.getValue()));
            } else if (entry.getValue() instanceof RoughTile) {
                this.tiles.put(entry.getKey(), new RoughTile(entry.getValue()));
            } else if (entry.getValue() instanceof FixedTile) {
                this.tiles.put(entry.getKey(), new FixedTile(entry.getValue()));
            } else if (entry.getValue() instanceof FreelanceTile) {
                this.tiles.put(entry.getKey(), new FreelanceTile(entry.getValue()));
            } else if (entry.getValue() instanceof StickyTile) {
                this.tiles.put(entry.getKey(), new StickyTile(entry.getValue()));
            }
        }
        if (isVisible) {
            for (Tile t : this.tiles.values()) {
                t.makeVisible();
            }
        }
    }
    
    /**
     * Makes the board visible.
     */
    public void makeVisible() {
        this.rectangle.makeVisible();
        for (Tile t : tiles.values()) {
            t.makeVisible();
        }
        for (Hole h : holes.values()) {
            h.makeVisible();
        }
        this.isVisible = true;
    }

    /**
     * Makes the board invisible.
     */
    public void makeInvisible() {
        for (Tile t : tiles.values()) {
            t.makeInvisible();
        }
        for (Hole h : holes.values()) {
            h.makeInvisible();
        }
        this.rectangle.makeInvisible();
        this.isVisible = false;
    }
    
    /**
     * Changes the visibility of the tiles in the board.
     * 
     * @param state The new visibility state (true for visible, false for invisible).
     */
    public void changeTilesVisibility(boolean state) {
        this.isVisible = state;
        for (Tile t : tiles.values()) {
            if (state == true) {
                t.makeVisible();
            } else if (state == false) {
                t.makeInvisible();
            }
        }
    }
    
    /**
     * Removes glue from tiles that cannot tilt after a tilt action.
     * 
     * @throws puzzle.TileExceptions If there is an error related to the tile.
     */
    public void deleteGlueAfterTilt() throws puzzle.TileExceptions {
        for (Tile t : this.tiles.values()) {
            if (t.hasGlue() && !t.getGlue().canTilt()) {
                t.deleteGlue();
            }
        }
    }
    
    /**
     * Checks if the tile at the specified position can be tilted.
     * 
     * @param row The row of the tile.
     * @param column The column of the tile.
     * @return true if the tile can be tilted, false otherwise.
     * @throws puzzle.BoardExceptions If the location is invalid or there is no tile at that position.
     */
    public boolean canThisTileBeTilted(int row, int column) throws puzzle.BoardExceptions {
        if (row > this.rows || row <= 0 || column > this.columns || column <= 0) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
        String tuple = Arrays.toString(new int[] {row, column});
        if (!this.tiles.containsKey(tuple)) {
            throw new BoardExceptions(BoardExceptions.EMPTY_POSITION);
        }
        return this.tiles.get(tuple).canBeTilted();
    }
}
