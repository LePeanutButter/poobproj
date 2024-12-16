package domain;

/**
 * The Night class represents a type of lawn in the game.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 07 2024
 */
public class Night extends Lawn {

    /**
     * Determines whether the sun can fall on the lawn during the night.
     *
     * @return false, indicating that sun cannot fall during the night.
     */
    @Override
    public boolean canSunFall() {
        return false;
    }
}
