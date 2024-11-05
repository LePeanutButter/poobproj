package puzzle;


/**
 * Write a description of class PuzzleExceptions here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PuzzleExceptions extends Exception {
    public static final String  NULL_BOARD = "The board cannot be null.",
                                STARTING_BOARD = "The number of columns in starting board does not match.", ENDING_BOARD = "The number of columns in ending board does not match.",
                                DIFFERENT_BOARDS = "The boards do not match.", INVALID_COLOR = "Invalid color character: ",
                                INVALID_GLUE = "Glue cannot be added in this position.", NON_EXISTENT_GLUE = "Glue cannot be deleted from this position.",
                                INVALID_TUPLE = "The position must be a tuple.";

    /**
     * Constructor for objects of class PuzzleExceptions
     */
    public PuzzleExceptions(String message) {
        super(message);
    }
}
