package puzzle;


/**
 * StickyTile class, representing a tile that can only move if glue is applied.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 28 2024
 */
public class StickyTile extends Tile {

    /**
     * Constructor for objects of class StickyTile.
     * 
     * @param row The row where the tile is located.
     * @param column The column where the tile is located.
     * @param color The color of the tile.
     * @param xPosition The x position of the tile in the interface.
     * @param yPosition The y position of the tile in the interface.
     * @throws BoardExceptions If the tile cannot be created in the specified position.
     */
    public StickyTile(int row, int column, String color, int xPosition, int yPosition) throws BoardExceptions {
        super(row, column, color, xPosition, yPosition);
    }
        
    /**
     * Constructor to create a StickyTile from another tile.
     * 
     * @param tile The tile from which attributes will be copied.
     */
    public StickyTile(Tile tile){
        super(tile);
    }
        
    /**
     * Determines if the tile can move.
     * 
     * @return true if it has glue, false otherwise.
     */
    @Override
    public boolean canMove() {
        return hasGlue();
    }
    
    /**
     * Determines if the tile can be removed.
     * 
     * @return true always, as StickyTile allows removal.
     */
    @Override
    public boolean canBeDeleted() {
        return true;
    }
        
    /**
     * Determines if the tile can be tilted.
     * 
     * @return true if it has glue, false otherwise.
     */
    @Override
    public boolean canBeTilted() {
        return hasGlue();
    }
    
    /**
     * Determines if glue can be applied to the tile.
     * 
     * @return {@code true} always, as StickyTile allows glue application.
     */
    @Override
    public boolean canBeGlued() {
        return true;
    }
        
    /**
     * Determines if the tile can fall.
     * 
     * @return true always, as StickyTile allows falling.
     */
    @Override
    public boolean canFall() {
        return true;
    }
}
