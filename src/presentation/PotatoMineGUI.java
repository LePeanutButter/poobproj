package presentation;

import java.awt.*;
import javax.swing.*;

/**
 * A class that represents a specific type of plant in the interface.
 * This class extends PlantGUI and provides specific animations and behavior for a PotatoMine plant,
 * including loading its animation frames and returning the current frame.
 *
 * The animations are loaded from a sequence of images stored in a predefined directory.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12, 2024
 */
public class PotatoMineGUI extends PlantGUI {
    protected Image[] readyAnimation;
    protected Image[] explodeAnimation;
    protected boolean deathAnimationPlaying = false;

    /**
     * Constructs a PotatoMineGUI object with the specified position and size.
     * This constructor initializes the plant's position and dimensions on the screen.
     *
     * @param xPosition The x-coordinate of the plant's position.
     * @param yPosition The y-coordinate of the plant's position.
     * @param width     The width of the screen.
     * @param height    The height of the screen.
     */
    public PotatoMineGUI(int xPosition, int yPosition, int width, int height) {
        super(xPosition, yPosition, width, height);
    }

    /**
     * Loads the animation frames for all states of the PotatoMine plant.
     */
    @Override
    protected void loadAnimationFrames() {
        this.idleAnimation = new Image[10];
        int countI = 0;
        for (int i = 1; i <= 10; i++) {
            String imageName = String.format("src/resources/imag/Plants/PotatoMine/PotatoMine%04d.png", i);
            idleAnimation[countI] = new ImageIcon(imageName).getImage();
            countI++;
        }
        this.readyAnimation = new Image[26];
        int countR = 0;
        for (int i = 11; i <= 36; i++) {
            String imageName = String.format("src/resources/imag/Plants/PotatoMine/PotatoMine%04d.png", i);
            readyAnimation[countR] = new ImageIcon(imageName).getImage();
            countR++;
        }
        this.explodeAnimation = new Image[12];
        for (int i = 0; i <= 11; i++) {
            explodeAnimation[i] = new ImageIcon("src/resources/imag/Plants/PotatoMine/PotatoMine0037.png").getImage();
        }
    }
    /**
     * Changes the current animation of the plant.
     * If the animation is "explode," it marks the death animation as playing.
     *
     * @param animation The name of the new animation to display.
     */
    @Override
    public void changeAnimation(String animation) {
        if (!currentAnimation.equals(animation)) {
            currentAnimation = animation;
            animationIndex = 0;
        }
        if (animation.equals("explode")) {
            deathAnimationPlaying = true;
        }
    }

    /**
     * Returns the current frame of the PotatoMine plant's animation based on the current animation state.
     * The possible states are "idle", "ready", and "explode". The method checks the currentAnimation
     * and returns the corresponding image frame based on the animationIndex.
     *
     * @return The current animation frame as an Image, or null if no valid animation is found.
     */
    @Override
    public Image getImage() {
        Image image = null;
        switch (currentAnimation) {
            case "idle" -> image = idleAnimation[animationIndex];
            case "ready" -> image = readyAnimation[animationIndex];
            case "explode" -> image = explodeAnimation[animationIndex];
            default -> {
            }
        }
        return image;
    }

    /**
     * Updates the animationIndex to the next frame based on the current animation state.
     * The method loops through the frames of the current animation, ensuring the index wraps around
     * correctly when reaching the end of the animation.
     */
    @Override
    public void updateIndex() {
        switch (currentAnimation) {
            case ("idle") -> {
                if (animationIndex < idleAnimation.length - 1) {
                    ++animationIndex;
                } else {
                    animationIndex = 0;
                }
            }
            case ("ready") -> {
                if (animationIndex < readyAnimation.length - 1) {
                    ++animationIndex;
                } else {
                    animationIndex = 15;
                }
            }
            case ("explode") -> {
                if (animationIndex < explodeAnimation.length - 1) {
                    ++animationIndex;
                } else {
                    deathAnimationPlaying = false;
                }
            }
            default -> {
            }
        }
    }

    /**
     * Checks if the death animation is currently playing.
     *
     * @return true if the death animation is playing, otherwise false.
     */
    public boolean isDeathAnimationPlaying() {
        return deathAnimationPlaying;
    }
}
