package domain;

/**
 * The Day class represents a type of lawn in the game.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public class Day extends Lawn {

    /**
     * Indicates whether sun can fall on the lawn.
     *
     * @return true to indicate that sun can fall on the lawn.
     */
    @Override
    public boolean canSunFall() {
        return true;
    }
}
