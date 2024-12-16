package presentation;

/**
 * Custom exception class for handling errors related to the graphical user interface (GUI) in the POOBVsZombies game.
 * This exception is thrown when invalid or unknown entities such as plant names, lawn names, or zombie names are encountered
 * within the game's GUI logic.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12, 2024
 */
public class POOBVsZombiesGUIException extends Exception {
    public static final String UNKNOWN_PLANT = "Unknown plant name: ", UNKNOWN_LAWN = "Unknown lawn name: ", UNKNOWN_ZOMBIE = "Unknown zombie name: ", UNKNOWN_GAMEMODE = "Unknown gamemode: ";

    /**
     * Constructs a new POOBVsZombiesGUIException with the specified detail message.
     *
     * @param message The detail message that provides more information about the exception.
     */
    public POOBVsZombiesGUIException(String message) {
        super(message);
    }
}
