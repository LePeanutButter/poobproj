package puzzle;


/**
 * Class that represents specific exceptions related to tiles
 * in the puzzle.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 03 2024
 */
public class TileExceptions extends Exception{
    public static final String  INVALID_TYPE = "The type of tile does not exists.", CANT_DELETE = "The tile cannot be deleted.",
                                CANT_MOVE = "The tile cannot be moved.", CANT_GLUE = "The tile cannot be glued.",
                                NON_EXISTENT_GLUE = "Glue cannot be deleted from this tile.";

    /**
     * Constructor for objects of class TileExceptions.
     * @param message Error message to be displayed when the exception is thrown.
     */
    public TileExceptions(String message) {
        super(message);
    }

}
