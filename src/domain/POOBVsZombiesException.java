package domain;

/**
 * Custom exception class for handling specific game-related errors in the Plants vs. Zombies game.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public class POOBVsZombiesException extends Exception {
    public static final String UNKNOWN_PLANT = "Unknown plant name: ", UNKNOWN_LAWN = "Unknown lawn name: ",
    INVALID_POSITION = "Plant cannot be placed in this position.", NOT_ENOUGH_SUN = "Not enough sun.",
    INVALID_GAMEMODE = "Invalid Game Mode: ", INVALID_SHOVEL = "There is no plant in this position.",
    UNKNOWN_ZOMBIE = "Unknown zombie name: ", INVALID_TIME = "The time should be at least 3 minutes and less than 10 minutes.",
    NOT_ENOUGH_BRAIN = "Not enough brain.", UNKNOWN_MACHINE = "Unknown machine: ";

    /**
     * Constructs a new POOBVsZombiesException with the specified detail message.
     *
     * @param message The detail message that provides more information about the exception.
     */
    public POOBVsZombiesException(String message) {
        super(message);
    }
}
