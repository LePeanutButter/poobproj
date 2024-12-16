package presentation;

/**
 * The ProgressBar class provides a blueprint for creating progress bars.
 * It defines the basic properties and methods related to the size and position of a progress bar.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12 , 2024
 */
public abstract class ProgressBar {
    protected int xPosition;
    protected int yPosition;
    protected int width;
    protected int height;

    /**
     * Constructs a new ProgressBar object with the specified dimensions.
     * 
     * @param width  The width of the progress bar.
     * @param height The height of the progress bar.
     */
    public ProgressBar(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the x-coordinate of the progress bar's position.
     * 
     * @return The x-coordinate of the progress bar.
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * Returns the y-coordinate of the progress bar's position.
     * 
     * @return The y-coordinate of the progress bar.
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     * Returns the width of the progress bar.
     * 
     * @return The width of the progress bar.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the progress bar.
     * 
     * @return The height of the progress bar.
     */
    public int getHeight() {
        return height;
    }
}