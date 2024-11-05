package puzzle;


/**
 * Write a description of class FlyingTile here.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 28 2024
 */
public class FlyingTile extends Tile {
    public FlyingTile(int row, int column, String color, int xPosition, int yPosition) throws BoardExceptions {
        super(row, column, color, xPosition, yPosition);
    }
    
    public FlyingTile(Tile tile){
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
        return true;
    }
    
    @Override
    public boolean canFall() {
        return false;
    }
}

