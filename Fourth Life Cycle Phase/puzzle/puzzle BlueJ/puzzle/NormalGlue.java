package puzzle;


/**
 * Represents a common glue type that allows a tile to tilt on the board.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 28 2024
 */
public class NormalGlue extends Glue {
    /**
     * Constructor for objects of class NormalGlue
     */
    public NormalGlue(){
        super();
    }
    
    /**
     * Indicates if a tile with NormalGlue can be tilted.
     * 
     * @return true, as NormalGlue allows free tilting.
     */   
    public boolean canTilt() {
        return true;
    }
}
