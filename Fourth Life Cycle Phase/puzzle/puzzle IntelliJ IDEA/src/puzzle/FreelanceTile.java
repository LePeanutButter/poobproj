package puzzle;


/**
 * Write a description of class FreelanceTile here.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 28 2024
 */
public class FreelanceTile extends Tile {
    public FreelanceTile(int row, int column, String color, int xPosition, int yPosition) throws BoardExceptions {
        super(row, column, color, xPosition, yPosition);
    }
    
    public FreelanceTile(Tile tile){
        super(tile);
    }

    @Override
    public boolean canMove() {
        return true;
    }

    @Override
    public boolean canBeDeleted() {
        return true;
    }
    
    @Override
    public boolean canBeTilted() {
        return true;
    }
    
    @Override
    public boolean canBeGlued() {
        return false;
    }
    
    @Override
    public boolean canFall() {
        return true;
    }
}
