package presentation;

import java.awt.*;
import javax.swing.*;

/**
 * A class that extends `ZombieGUI` to provide specific GUI functionality for a basic zombie.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 09, 2024
 */
public class BasicGUI extends ZombieGUI {
    /**
     * Constructs a `BasicGUI` object with the specified position and size.
     * This constructor initializes the basic GUI with specific position and dimensions for the zombie on the screen.
     *
     * @param xPosition The x-coordinate of the zombie's position.
     * @param yPosition The y-coordinate of the zombie's position.
     * @param width     The width of the frame.
     * @param height    The height of the frame.
     */
    public BasicGUI(int xPosition, int yPosition, int width, int height) {
        super(xPosition, yPosition, width, height);
    }

    /**
     * Loads the animation frames for the zombie's actions (walking, eating, dying, and mower).
     * This method initializes arrays to hold the images for each animation state: walking, eating, dying, and mower.
     * It loads a sequence of images from the resources folder to represent each action.
     */
    @Override
    protected void loadAnimationFrames() {
        this.walkingAnimation = new Image[93];
        int countW = 0;
        for (int i = 46; i <= 138; i++) {
            String imageName = String.format("src/resources/imag/Zombies/Basic/Zombie%04d.png", i);
            walkingAnimation[countW] = new ImageIcon(imageName).getImage();
            countW++;
        }
        this.eatingAnimation = new Image[40];
        int countE = 0;
        for (int i = 139; i <= 178; i++) {
            String imageName = String.format("src/resources/imag/Zombies/Basic/Zombie%04d.png", i);
            eatingAnimation[countE] = new ImageIcon(imageName).getImage();
            countE++;
        }
        this.deathAnimation = new Image[33];
        int countD = 0;
        for (int i = 218; i <= 250; i++) {
            String imageName = String.format("src/resources/imag/Zombies/Basic/Zombie%04d.png", i);
            deathAnimation[countD] = new ImageIcon(imageName).getImage();
            countD++;
        }
        this.mowerAnimation = new Image[8];
        int countM = 0;
        for (int i = 1; i <= 8; i++) {
            String imageName = String.format("src/resources/imag/Zombies/LawnMoweredZombie/LawnMoweredZombie%04d.png", i);
            mowerAnimation[countM] = new ImageIcon(imageName).getImage();
            countM++;
        }
    }

    /**
     * Returns the current image based on the zombie's current animation state.
     * This method returns the appropriate image for the zombie based on the current animation state
     * (walking, eating, dying, or mower). The image is selected from the corresponding animation array
     * based on the `currentAnimation` state and the `animationIndex`.
     *
     * @return The current image for the zombie based on its animation state.
     */
    @Override
    public Image getImage() {
        return switch (currentAnimation) {
            case "walk" -> walkingAnimation[animationIndex];
            case "eating" -> eatingAnimation[animationIndex];
            case "dying" -> deathAnimation[animationIndex];
            case "mower" -> mowerAnimation[animationIndex];
            default -> null;
        };
    }
}
