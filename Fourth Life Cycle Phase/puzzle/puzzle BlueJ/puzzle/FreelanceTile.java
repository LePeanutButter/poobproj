package puzzle;


/**
 * FreelanceTile class, representing a tile that does not adhere to other tiles and does not 
 * allow glue application.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 28 2024
 */
public class FreelanceTile extends Tile {
    /**
     * Constructor for objects of class FreelanceTile.
     * 
     * @param row The row where the tile is located.
     * @param column The column where the tile is located.
     * @param color The color of the tile.
     * @param xPosition The x position of the tile in the interface.
     * @param yPosition The y position of the tile in the interface.
     * @throws BoardExceptions If the tile cannot be created in the specified position.
     */
    public FreelanceTile(int row, int column, String color, int xPosition, int yPosition) throws BoardExceptions {
        super(row, column, color, xPosition, yPosition);
    }
        
    /**
     * Constructor to create a FreelanceTile from another tile.
     * 
     * @param tile The tile from which attributes will be copied.
     */
    public FreelanceTile(Tile tile){
        super(tile);
    }
    
    /**
     * Determines if the tile can move.
     * 
     * @return true always, as FreelanceTile allows movement.
     */
    @Override
    public boolean canMove() {
        return true;
    }
    
    /**
     * Determines if the tile can be removed.
     * 
     * @return true always, as FreelanceTile allows removal.
     */
    @Override
    public boolean canBeDeleted() {
        return true;
    }

    /**
     * Determines if the tile can be tilted.
     * 
     * @return true always, as FreelanceTile allows tilting.
     */
    @Override
    public boolean canBeTilted() {
        return true;
    }
    
    /**
     * Determines if glue can be applied to the tile.
     * 
     * @return false always, as FreelanceTile does not allow glue application.
     */
    @Override
    public boolean canBeGlued() {
        return false;
    }
    
    /**
     * Determines if the tile can fall.
     * 
     * @return true always, as FreelanceTile allows falling.
     */
    @Override
    public boolean canFall() {
        return true;
    }
}
