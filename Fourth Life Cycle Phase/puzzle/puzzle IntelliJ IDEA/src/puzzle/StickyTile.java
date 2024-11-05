package puzzle;


/**
 * Write a description of class StickyTile here.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 28 2024
 */
public class StickyTile extends Tile {

    /**
     * Constructor for objects of class StickyTile
     */
    public StickyTile(int row, int column, String color, int xPosition, int yPosition) throws BoardExceptions {
        super(row, column, color, xPosition, yPosition);
    }
    
    public StickyTile(Tile tile){
        super(tile);
    }
    
    @Override
    public boolean canMove() {
        return hasGlue();
    }

    @Override
    public boolean canBeDeleted() {
        return true;
    }
    
    @Override
    public boolean canBeTilted() {
        return hasGlue();
    }
    
    @Override
    public boolean canBeGlued() {
        return true;
    }
    
    @Override
    public boolean canFall() {
        return true;
    }
}
