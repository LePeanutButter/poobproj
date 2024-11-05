package puzzle;


/**
 * The FixedTile class represents a type of tile that cannot be moved, deleted, or tilted.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 03 2024
 */
public class FixedTile extends Tile {
    /**
     * Constructor for FixedTile that initializes a fixed tile at a specific position and color.
     * 
     * @param row The row where the fixed tile is located.
     * @param column The column where the fixed tile is located.
     * @param color The color of the fixed tile.
     * @param xPosition The x position of the fixed tile in the interface.
     * @param yPosition The y position of the fixed tile in the interface.
     * @throws BoardExceptions If the fixed tile is out of the board's bounds.
     */   
    public FixedTile(int row, int column, String color, int xPosition, int yPosition) throws BoardExceptions {
        super(row, column, color, xPosition, yPosition);
    }

    /**
     * Copy constructor for creating a FixedTile from another Tile.
     * 
     * @param tile The tile from which attributes will be copied.
     */
    public FixedTile(Tile tile){
        super(tile);
    }

    /**
     * Indicates whether the fixed tile can move.
     * 
     * @return false as a fixed tile cannot be moved.
     */
    @Override
    public boolean canMove() {
        return false;
    }

    /**
     * Indicates whether the fixed tile can be deleted.
     * 
     * @return false as a fixed tile cannot be deleted.
     */
    @Override
    public boolean canBeDeleted() {
        return false;
    }
    
    /**
     * Indicates whether the fixed tile can be tilted.
     * 
     * @return false as a fixed tile cannot be tilted.
     */
    @Override
    public boolean canBeTilted() {
        return false;
    }
    
    /**
     * Indicates whether glue can be applied to the fixed tile.
     * 
     * @return true as glue can be applied to a fixed tile.
     */
    @Override
    public boolean canBeGlued() {
        return true;
    }
    
    /**
     * Indicates whether the fixed tile can fall.
     * 
     * @return true as a fixed tile can fall.
     */
    @Override
    public boolean canFall() {
        return true;
    }
}

