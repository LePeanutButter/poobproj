package puzzle;


/**
 * The NormalTile class represents a normal tile
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 23 2024
 */
public class NormalTile extends Tile {
    /**
     * Constructor for NormalTile that initializes a normal tile at a specific position and color.
     * 
     * @param row The row where the normal tile is located.
     * @param column The column where the normal tile is located.
     * @param color The color of the normal tile.
     * @param xPosition The x position of the normal tile in the interface.
     * @param yPosition The y position of the normal tile in the interface.
     * @throws BoardExceptions If the normal tile is out of the board's bounds.
     */
    public NormalTile(int row, int column, String color, int xPosition, int yPosition) throws BoardExceptions {
        super(row, column, color, xPosition, yPosition);
    }
    
    /**
     * Copy constructor for creating a NormalTile from another Tile.
     * 
     * @param tile The tile from which attributes will be copied.
     */
    public NormalTile(Tile tile){
        super(tile);
    }

    /**
     * Indicates whether the normal tile can move.
     * 
     * @return true as a normal tile can be moved.
     */
    @Override
    public boolean canMove() {
        return true;
    }

    /**
     * Indicates whether the normal tile can be deleted.
     * 
     * @return true as a normal tile can be deleted.
     */
    @Override
    public boolean canBeDeleted() {
        return true;
    }

    /**
     * Indicates whether the normal tile can be tilted.
     * 
     * @return true as a normal tile can be tilted.
     */
    @Override
    public boolean canBeTilted() {
        return true;
    }
    
    /**
     * Indicates whether glue can be applied to the normal tile.
     * 
     * @return true as glue can be applied to a normal tile.
     */
    @Override
    public boolean canBeGlued() {
        return true;
    }
    
    /**
     * Indicates whether the normal tile can fall.
     * 
     * @return true as a normal tile can fall.
     */
    @Override
    public boolean canFall() {
        return true;
    }
}
