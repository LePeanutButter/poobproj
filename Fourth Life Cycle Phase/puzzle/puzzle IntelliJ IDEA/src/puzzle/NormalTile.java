package puzzle;


/**
 * Write a description of class NormalTile here.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 23 2024
 */
public class NormalTile extends Tile {
    public NormalTile(int row, int column, String color, int xPosition, int yPosition) throws BoardExceptions {
        super(row, column, color, xPosition, yPosition);
    }
    
    public NormalTile(Tile tile){
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
        return true;
    }
}
