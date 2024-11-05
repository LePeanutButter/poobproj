package puzzle;


/**
 * Write a description of class BoardExceptions here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BoardExceptions extends Exception {
    public static String INVALID_POSITION = "The board position must be greater than zero.", DIMENSION_ERROR = "The dimensions of the board must be greater than zero.",
                        OUT_OF_BOUNDS = "Position out of bounds.", EMPTY_POSITION = "There is no tile place in this position.",
                        INVALID_LOCATION = "The tile cannot be placed in this location.", INVALID_MOVE = "The tile cannot be moved to this position.",
                        EXISTING_HOLE = "A hole already exists in this tile.", CANT_HOLE = "Cannot create a hole on a non-empty tile.";

    /**
     * Constructor for objects of class BoardExceptions
     */
    public BoardExceptions(String message) {
        super(message);
    }
}
