package puzzle;


/**
 * GlueExceptions class, a custom exception to handle specific errors related to the use of glue
 * in the simulator.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 03 2024
 */
public class GlueExceptions extends Exception {
    public static final String INVALID_GLUE = "Glue cannot be added in this position.", INVALID_TYPE = "The type of glue does not exists.";

    /**
     * Constructor for objects of the GlueExceptions class.
     * 
     * @param message The error message describing the cause of the exception.
     */
    public GlueExceptions(String message) {
        super(message);
    }

}
