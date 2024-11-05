package puzzle;
/**
 * Write a description of class RoughTile here.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 28 2024
 */
public class RoughTile extends Tile {
    public RoughTile(int row, int column, String color, int xPosition, int yPosition) throws BoardExceptions {
        super(row, column, color, xPosition, yPosition);
    }
    
    public RoughTile(Tile tile){
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


