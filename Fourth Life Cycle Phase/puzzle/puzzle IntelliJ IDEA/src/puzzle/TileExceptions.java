package puzzle;


/**
 * Write a description of class TileExceptions here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TileExceptions extends Exception{
    public static final String  INVALID_TYPE = "The type of tile does not exists.", CANT_DELETE = "The tile cannot be deleted.",
                                CANT_MOVE = "The tile cannot be moved.", CANT_GLUE = "The tile cannot be glued.",
                                NON_EXISTENT_GLUE = "Glue cannot be deleted from this tile.";

    /**
     * Constructor for objects of class TileExceptions
     */
    public TileExceptions(String message) {
        super(message);
    }

}
