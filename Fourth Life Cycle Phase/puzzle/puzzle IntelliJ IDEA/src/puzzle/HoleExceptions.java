package puzzle;


/**
 * Write a description of class HoleExceptions here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class HoleExceptions extends Exception {
    public static final String OUT_OF_BOUNDS = "The tile cannot be placed out of bounds.";

    /**
     * Constructor for objects of class HoleExceptions
     */
    public HoleExceptions(String message) {
        super(message);
    }
}
