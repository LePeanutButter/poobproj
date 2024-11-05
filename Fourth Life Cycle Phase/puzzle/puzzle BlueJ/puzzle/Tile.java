package puzzle;
import shapes.*;

import java.util.Arrays;

/**
 * Abstract class Tile, representing a tile on the simulator board.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 23 2024
 */

public abstract class Tile {
    public static final int TILE_WIDTH = 40, TILE_HEIGHT = 40;
    
    protected int row;
    protected int column;
    private String color;
    private Rectangle rectangle;
    private Glue glue;

    /**
     * Constructor for Tile that initializes a tile at a specific position and color.
     * 
     * @param row The row where the tile is located.
     * @param column The column where the tile is located.
     * @param color The color of the tile.
     * @param xPosition The x position of the tile in the interface.
     * @param yPosition The y position of the tile in the interface.
     * @throws BoardExceptions If the tile is out of the board's bounds.
     */
    public Tile(int row, int column, String color, int xPosition, int yPosition) throws BoardExceptions {
        if (row <= 0 || column <= 0 || row > Board.rows || column > Board.columns) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
        this.row = row;
        this.column = column;
        this.color = color;
        this.rectangle = new Rectangle(TILE_HEIGHT, TILE_WIDTH, xPosition + 10 + ((column - 1) * 40), yPosition + 10 + ((row - 1) * 40), color, false);
    }
    
    /**
     * Constructor to create a tile from another tile.
     * 
     * @param tile The tile from which attributes will be copied.
     */
    public Tile(Tile tile){
        this.row = tile.row;
        this.column = tile.column;
        this.color = tile.color;
        this.rectangle = new Rectangle(TILE_HEIGHT, TILE_WIDTH, tile.rectangle.getXPosition(), tile.rectangle.getYPosition(), tile.color, tile.rectangle.isVisible());
        this.glue = tile.glue;
    }
    
    public abstract boolean canMove();

    public abstract boolean canBeDeleted();
    
    public abstract boolean canBeTilted();
    
    public abstract boolean canBeGlued();
    
    public abstract boolean canFall();
    
    /**
     * Gets the color of the tile.
     * 
     * @return The color of the tile.
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Makes the tile visible.
     */
    public void makeVisible() {
        this.rectangle.makeVisible();
    }

    /**
     * Makes the tile invisible.
     */
    public void makeInvisible() {
        this.rectangle.makeInvisible();
    }
    
    /**
     * Changes the position of the tile on the board.
     * 
     * @param row The new row of the tile.
     * @param column The new column of the tile.
     * @throws BoardExceptions If the new position is out of the board's bounds.
     */
    public void setPosition(int row, int column) throws BoardExceptions {
        if (row <= 0 || column <= 0 || row > Board.rows || column > Board.columns) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
        rectangle.moveHorizontal((column - this.column) * 40);
        rectangle.moveVertical((row - this.row) * 40);
        this.row = row;
        this.column = column;
    }
    
    /**
     * Adds glue to the tile if it doesn't already have glue applied.
     * 
     * @param type The type of glue to apply.
     * @throws GlueExceptions If the glue is already applied or the type is invalid.
     */
    public void addGlue(String type) throws GlueExceptions {
        if (this.glue == null) {
            switch (type.toLowerCase()){
                case "normal":
                    this.glue = new NormalGlue();
                    break;
                case "super":
                    this.glue = new SuperGlue();
                    break;
                case "fragil":
                    this.glue = new FragilGlue();
                    break;
                default:
                    throw new GlueExceptions(GlueExceptions.INVALID_TYPE);
            }
        } else {
            throw new GlueExceptions(GlueExceptions.INVALID_GLUE);
        }
    }
    
    /**
     * Removes the glue from the tile if glue has been applied.
     * 
     * @throws TileExceptions If there is no glue on the tile.
     */
    public void deleteGlue() throws TileExceptions {
        if (this.glue != null) {
            this.glue = null;
        } else {
            throw new TileExceptions(TileExceptions.NON_EXISTENT_GLUE);
        }
    }
    
    /**
     * Checks if the tile has glue.
     * 
     * @return true if it has glue, false otherwise.
     */
    public boolean hasGlue() {
        if (this.glue != null) {
            return true;
        }
        return false;
    }
    
    /**
     * Moves the tile horizontally in the interface.
     * 
     * @param xPosition Amount of horizontal movement.
     */
    public void moveHorizontal(int xPosition) {
        this.rectangle.moveHorizontal(xPosition);
    }

    /**
     * Gets the glue applied to the tile.
     * 
     * @return The glue of the tile, or null if it has no glue.
     */
    public Glue getGlue() {
        return this.glue;
    }
}
