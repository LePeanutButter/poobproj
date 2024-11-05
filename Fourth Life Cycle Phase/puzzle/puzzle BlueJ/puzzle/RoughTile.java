package puzzle;
/**
 * The RoughTile class represents a tile that does not slide.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 28 2024
 */
public class RoughTile extends Tile {
    /**
     * Constructor for RoughTile that initializes a rough tile at a specific position and color.
     * 
     * @param row The row where the rough tile is located.
     * @param column The column where the rough tile is located.
     * @param color The color of the rough tile.
     * @param xPosition The x position of the rough tile in the interface.
     * @param yPosition The y position of the rough tile in the interface.
     * @throws BoardExceptions If the rough tile is out of the board's bounds.
     */
    public RoughTile(int row, int column, String color, int xPosition, int yPosition) throws BoardExceptions {
        super(row, column, color, xPosition, yPosition);
    }

    /**
     * Copy constructor for creating a RoughTile from another Tile.
     * 
     * @param tile The tile from which attributes will be copied.
     */
    public RoughTile(Tile tile){
        super(tile);
    }

    /**
     * Indicates whether the rough tile can move.
     * 
     * @return true as a rough tile can be moved.
     */
    @Override
    public boolean canMove() {
        return true;
    }

    /**
     * Indicates whether the rough tile can be deleted.
     * 
     * @return true as a rough tile can be deleted.
     */
    @Override
    public boolean canBeDeleted() {
        return true;
    }
    
    /**
     * Indicates whether the rough tile can be tilted.
     * 
     * @return false as a rough tile cannot be tilted.
     */
    @Override
    public boolean canBeTilted() {
        return false;
    }
    
    /**
     * Indicates whether glue can be applied to the rough tile.
     * 
     * @return true as glue can be applied to a rough tile.
     */
    @Override
    public boolean canBeGlued() {
        return true;
    }
    
    /**
     * Indicates whether the rough tile can fall.
     * 
     * @return true as a rough tile can fall.
     */
    @Override
    public boolean canFall() {
        return true;
    }
}


