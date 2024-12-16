package presentation;

import java.awt.*;
import javax.swing.*;

/**
 * The BrainsteinGUI class represents a specific type of zombie, Brainstein, in the game.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12 , 2024
 */
public class BrainsteinGUI extends ZombieGUI {
    private Image idle;

    /**
     * Constructs a new BrainsteinGUI object with the specified parameters.
     * This constructor initializes the position and size of Brainstein, scaling them based on 
     * the screen dimensions.
     * 
     * @param xPosition The initial X-coordinate of Brainstein.
     * @param yPosition The initial Y-coordinate of Brainstein.
     * @param width     The screen width used to scale Brainstein's size.
     * @param height    The screen height used to scale Brainstein's size.
     */
    public BrainsteinGUI(int xPosition, int yPosition, int width, int height) {
        super(xPosition, yPosition, width, height);
        this.xPosition = xPosition * width / 1280;
        this.yPosition = (yPosition - 9) * height / 720;
        this.width = width * 96 / 1280;
        this.height = height * 112 / 720;

    }

    /**
     * Loads the graphical representation of Brainstein by initializing the idle image.
     * This method is invoked during the object creation and loads the image from a specific path.
     */
    @Override
    protected void loadAnimationFrames() {
        idle = new ImageIcon("src/resources/imag/Zombies/Brainstein.png").getImage();
    }

    /**
     * Returns the current image representing Brainstein.
     * Since Brainstein does not have animations, it always returns the idle image.
     * 
     * @return The Image object representing Brainstein's current state.
     */
    @Override
    public Image getImage() {
        return idle;
    }

    /**
     * Updates the animation index for Brainstein. In this implementation, the index is simply incremented,
     * though it has no effect as Brainstein does not have multiple animation frames.
     */
    @Override
    public void updateIndex() {
        animationIndex++;
    }
}
