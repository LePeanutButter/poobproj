package puzzle;
import shapes.*;

import java.util.Arrays;

/**
 * Write a description of class Tile here.
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

    public Tile(int row, int column, String color, int xPosition, int yPosition) throws BoardExceptions {
        if (row <= 0 || column <= 0 || row > Board.rows || column > Board.columns) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
        this.row = row;
        this.column = column;
        this.color = color;
        this.rectangle = new Rectangle(TILE_HEIGHT, TILE_WIDTH, xPosition + 10 + ((column - 1) * 40), yPosition + 10 + ((row - 1) * 40), color, false);
    }
    
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
     * Changes the position of the tile.
     * @param x     Column in which the tile will be changed.
     * @param y     Row that the tile will be changed.
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
    
    public void deleteGlue() throws TileExceptions {
        if (this.glue != null) {
            this.glue = null;
        } else {
            throw new TileExceptions(TileExceptions.NON_EXISTENT_GLUE);
        }
    }
    
    public boolean hasGlue() {
        if (this.glue != null) {
            return true;
        }
        return false;
    }
    
    public void moveHorizontal(int xPosition) {
        this.rectangle.moveHorizontal(xPosition);
    }
    
    public Glue getGlue() {
        return this.glue;
    }
}
