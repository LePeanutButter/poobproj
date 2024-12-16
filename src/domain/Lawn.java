package domain;

import java.io.Serializable;

/**
 * The Lawn class represents a lawn in the game
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public abstract class Lawn implements Serializable {

    /**
     * Abstract method that indicates whether sun can fall on the lawn.
     */
    public abstract boolean canSunFall();
}
