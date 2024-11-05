package puzzle;

/**
 * FragilGlue class, a special type of glue that supports only one attempt of tilting.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 28 2024
 */
public class FragilGlue extends Glue {
    /**
     * Default constructor for FragilGlue.
     * Initializes a new instance of FragilGlue using the superclass constructor.
     */
    public FragilGlue(){
        super();
    }
    
    /**
     * Determines if the tile can be tilted.
     * @return false always, as FragilGlue does not allow additional tilts.
     */
    @Override
    public boolean canTilt() {
        return false;
    }
}
