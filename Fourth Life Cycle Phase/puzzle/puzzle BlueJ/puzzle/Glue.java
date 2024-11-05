package puzzle;


/**
 * Abstract class representing glue in the puzzle game.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 03 2024
 */
public abstract class Glue {
    /**
     * Constructor for objects of class Glue
     */
    public Glue() {
    }
    /**
     * Abstract method that indicates if a tile can be tilted.
     * @return true if the tile can be tilted, false otherwise.
     */
    public abstract boolean canTilt();
}
