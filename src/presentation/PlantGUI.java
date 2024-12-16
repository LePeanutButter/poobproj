package presentation;

import java.awt.*;
import javax.swing.SwingUtilities;

/**
 * An abstract class representing the graphical user interface for plants in the game.
 * This class provides common properties and methods for plant animations and positioning.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12 , 2024
 */
public abstract class PlantGUI {
    protected int xPosition;
    protected int yPosition;
    protected int width;
    protected int height;
    protected Image[] idleAnimation;
    protected String currentAnimation;
    protected int animationIndex;

    /**
     * Constructs a PlantGUI object with the specified position and size.
     * This constructor initializes the plant's position and dimensions on the screen,
     * and sets up its default animation state.
     *
     * @param xPosition The x-coordinate of the plant's position.
     * @param yPosition The y-coordinate of the plant's position.
     * @param width     The width of the screen.
     * @param height    The height of the screen.
     */
    public PlantGUI(int xPosition, int yPosition, int width, int height) {
        this.xPosition = (xPosition - 60) * width / 1280;
        this.yPosition = yPosition * height / 720;
        this.width = width * 101 / 1280;
        this.height = height * 101 / 720;
        this.currentAnimation = "idle";
        this.animationIndex = 0;
        SwingUtilities.invokeLater(() -> loadAnimationFrames());
    }

    /**
     * Loads the animation frames for the plant.
     * Subclasses must provide an implementation to initialize the idleAnimation array.
     */
    protected abstract void loadAnimationFrames();


    /**
     * Changes the current animation of the plant.
     *
     * @param animation The name of the new animation to display.
     */
    public void changeAnimation(String animation) {
        if (!currentAnimation.equals(animation)) {
            currentAnimation = animation;
            animationIndex = 0;
        }
    }

    /**
     * Updates the animation index to display the next frame.
     * Resets the index to 0 if it reaches the end of the idleAnimation array.
     */
    public void updateIndex() {
        if (animationIndex < idleAnimation.length - 1) {
            ++animationIndex;
        } else {
            animationIndex = 0;
        }
    }

    /**
     * Returns the current image frame for the plant.
     * Subclasses must implement this method to provide the appropriate image.
     *
     * @return The current frame of the plant's animation as an Image.
     */
    public abstract Image getImage();

    /**
     * Returns the width of the plant's graphical representation.
     *
     * @return The width of the plant.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the plant's graphical representation.
     *
     * @return The height of the plant.
     */
    public int getHeight() {
        return height;
    }
}
