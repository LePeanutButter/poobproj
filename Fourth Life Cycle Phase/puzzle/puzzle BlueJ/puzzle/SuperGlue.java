package puzzle;


/**
 * Write a description of class SuperTile here.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 28 2024
 */
public class SuperGlue extends Glue {
    /**
     * Constructor for objects of class SuperGlue
     */   
    public SuperGlue(){
        super();
    }
    /**
     * Indicates if a tile with SuperGlue can be tilted.
     * 
     * @return true, as SuperGlue allows free tilting.
     */
    @Override
    public boolean canTilt() {
        return true;
    }
}
