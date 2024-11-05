package puzzle;


/**
 * Write a description of class FragilTile here.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 28 2024
 */
public class FragilGlue extends Glue {
    
    public FragilGlue(){
        super();
    }
    
    @Override
    public boolean canTilt() {
        return false;
    }
}
