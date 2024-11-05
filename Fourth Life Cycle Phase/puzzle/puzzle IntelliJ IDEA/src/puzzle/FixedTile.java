package puzzle;


/**
 * Write a description of class FixedTile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FixedTile extends Tile {
    public FixedTile(int row, int column, String color, int xPosition, int yPosition) throws BoardExceptions {
        super(row, column, color, xPosition, yPosition);
    }
    
    public FixedTile(Tile tile){
        super(tile);
    }

    @Override
    public boolean canMove() {
        return false;
    }

    @Override
    public boolean canBeDeleted() {
        return false;
    }
    
    @Override
    public boolean canBeTilted() {
        return false;
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

