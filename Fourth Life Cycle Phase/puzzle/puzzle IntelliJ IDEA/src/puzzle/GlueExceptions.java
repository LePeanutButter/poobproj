package puzzle;


/**
 * Write a description of class GlueExceptions here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GlueExceptions extends Exception {
    public static final String INVALID_GLUE = "Glue cannot be added in this position.", INVALID_TYPE = "The type of glue does not exists.";

    /**
     * Constructor for objects of class GlueExceptions
     */
    public GlueExceptions(String message) {
        super(message);
    }

}
