package domain;

/**
 * The BoardException exception class purpose is to extend the RuntimeException class by making custom messages appropriate for the clustering logic.
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Naralia.
 * @version 2024-11-15
 */
public class BoardException extends RuntimeException {
  public static final String INVALID_DIMENSION = "The board cannot have a negative dimension,", INVALID_PERCENTAGE = "The percentage has to be between 0 and a 100%.",
                            INVALID_MOVE = "The tiles cannot be moved in this direction.", OUT_OF_BOUNDS = "This positions is out of bounds";

  /**
   * Throws an exception to the user with a custom message.
   * @param message Pre-made exception message
   */
  public BoardException(String message) {
    super(message);
  }
}
