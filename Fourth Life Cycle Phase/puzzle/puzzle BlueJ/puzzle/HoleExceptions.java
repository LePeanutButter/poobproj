package puzzle;


/**
 * The HoleExceptions class represents exceptions related to holes on the board.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 03 2024
 */
public class HoleExceptions extends Exception {
    public static final String OUT_OF_BOUNDS = "The tile cannot be placed out of bounds.";

    /**
     * Constructor for objects of class HoleExceptions
     * @param message The message that describes the exception
     */
    public HoleExceptions(String message) {
        super(message);
    }
}
