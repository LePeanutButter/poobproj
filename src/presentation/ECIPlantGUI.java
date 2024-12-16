package presentation;

import java.awt.*;
import javax.swing.*;

/**
 * A class that represents a specific type of plant in the interface.
 * This class extends PlantGUI and provides specific animations and behavior
 * for an ECIPlant, including loading its animation frames and retrieving the current frame.
 *
 * The animations are loaded from a sequence of images stored in a predefined directory.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12, 2024
 */
public class ECIPlantGUI extends PlantGUI {

    /**
     * Constructs an ECIPlantGUI object with the specified position and size.
     * This constructor initializes the plant's position and dimensions on the screen.
     *
     * @param xPosition The x-coordinate of the plant's position.
     * @param yPosition The y-coordinate of the plant's position.
     * @param width     The width of the screen.
     * @param height    The height of the screen.
     */
    public ECIPlantGUI(int xPosition, int yPosition, int width, int height) {
        super(xPosition, yPosition, width, height);
    }

    /**
     * Loads the animation frames for the "idle" state of the plant.
     */
    @Override
    protected void loadAnimationFrames() {
        this.idleAnimation = new Image[93];
        int count = 0;
        for (int i = 1; i <= 93; i++) {
            String imageName = String.format("src/resources/imag/Plants/PrimalSunflower/PrimalSunflower%04d.png", i);
            idleAnimation[count] = new ImageIcon(imageName).getImage();
            count++;
        }
    }

    /**
     * Returns the current frame of the plant's animation.
     *
     * @return The current animation frame as an Image, or null if no animation is found.
     */
    @Override
    public Image getImage() {
        Image image = null;
        if (currentAnimation.equals("idle")) {
            image = idleAnimation[animationIndex];
        }
        return image;
    }
}
