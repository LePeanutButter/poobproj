package puzzle;


/**
 * The BoardExceptions class represents exceptions that can occur while 
 * interacting with the game board.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 03 2024
 */
public class BoardExceptions extends Exception {
    public static String INVALID_POSITION = "The board position must be greater than zero.", DIMENSION_ERROR = "The dimensions of the board must be greater than zero.",
                        OUT_OF_BOUNDS = "Position out of bounds.", EMPTY_POSITION = "There is no tile place in this position.",
                        INVALID_LOCATION = "The tile cannot be placed in this location.", INVALID_MOVE = "The tile cannot be moved to this position.",
                        EXISTING_HOLE = "A hole already exists in this tile.", CANT_HOLE = "Cannot create a hole on a non-empty tile.";

    /**
     * Constructs a BoardExceptions instance with a specified error message.
     *
     * @param message The detail message explaining the exception.
     */
    public BoardExceptions(String message) {
        super(message);
    }
}
