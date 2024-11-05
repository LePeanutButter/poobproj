package puzzle;


/**
 * The FlyingTile class represents a tile that does not fall into holes.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 28 2024
 */
public class FlyingTile extends Tile {
    /**
     * Constructor for FlyingTile that initializes a flying tile at a specific position and color.
     * 
     * @param row The row where the flying tile is located.
     * @param column The column where the flying tile is located.
     * @param color The color of the flying tile.
     * @param xPosition The x position of the flying tile in the interface.
     * @param yPosition The y position of the flying tile in the interface.
     * @throws BoardExceptions If the flying tile is out of the board's bounds.
     */
    public FlyingTile(int row, int column, String color, int xPosition, int yPosition) throws BoardExceptions {
        super(row, column, color, xPosition, yPosition);
    }
    
    /**
     * Copy constructor for creating a FlyingTile from another Tile.
     * 
     * @param tile The tile from which attributes will be copied.
     */
    public FlyingTile(Tile tile){
        super(tile);
    }
    /**
     * Indicates whether the flying tile can move.
     * 
     * @return true as a flying tile can be moved.
     */
    @Override
    public boolean canMove() {
        return true;
    }

    /**
     * Indicates whether the flying tile can be deleted.
     * 
     * @return true as a flying tile can be deleted.
     */
    @Override
    public boolean canBeDeleted() {
        return true;
    }
    
    /**
     * Indicates whether the flying tile can be tilted.
     * 
     * @return true as a flying tile can be tilted.
     */
    @Override
    public boolean canBeTilted() {
        return true;
    }
    
    /**
     * Indicates whether glue can be applied to the flying tile.
     * 
     * @return true as glue can be applied to a flying tile.
     */
    @Override
    public boolean canBeGlued() {
        return true;
    }

    /**
     * Indicates whether the flying tile can fall.
     * 
     * @return false as a flying tile cannot fall into holes.
     */
    @Override
    public boolean canFall() {
        return false;
    }
}

