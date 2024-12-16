package presentation;

import java.awt.*;
import javax.swing.*;

/**
 * The MowerGUI class represents the interface for a lawnmower in the game. 
 * It handles the display and animations of the lawnmower, as well as its movement and associated sounds.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12, 2024
 */
public class MowerGUI {
    protected int xPosition;
    protected int yPosition;
    protected int width;
    protected int height;
    protected Image idleAnimation;
    protected Image[] movingAnimation;
    protected String currentAnimation;
    protected int animationIndex;
    protected double move;
    protected Sound activeMower;

    /**
     * Constructs a MowerGUI object at the specified position with the given width and height.
     *
     * @param xPosition The initial horizontal position of the lawnmower.
     * @param yPosition The initial vertical position of the lawnmower.
     * @param width The width of the lawnmower image (scaled based on screen dimensions).
     * @param height The height of the lawnmower image (scaled based on screen dimensions).
     */
    public MowerGUI(int xPosition, int yPosition, int width, int height) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width * 90 / 1280;
        this.height = height * 90 / 720;
        currentAnimation = "idle";
        animationIndex = 0;
        move = xPosition;
        SwingUtilities.invokeLater(this::loadAnimationFrames);
    }

    /**
     * Loads the animation frames for the lawnmower's idle and moving states.
     */
    protected void loadAnimationFrames() {
        String idleImageName = "src/resources/imag/LawnMower/LawnMower0001.png";
        idleAnimation = new ImageIcon(idleImageName).getImage();
        this.movingAnimation = new Image[16];
        int count = 0;
        for (int i = 2; i <= 17; i++) {
            String imageName = String.format("src/resources/imag/LawnMower/LawnMower%04d.png", i);
            movingAnimation[count] = new ImageIcon(imageName).getImage();
            count++;
        }
    }

    /**
     * Returns the current image to be displayed based on the lawnmower's animation state.
     * If the lawnmower is idle, the idle animation image is returned.
     * If the lawnmower is moving, the appropriate frame from the moving animation is returned.
     *
     * @return The image to be displayed for the lawnmower.
     */
    public Image getImage() {
        Image image = null;
        if (currentAnimation.equals("idle")) {
            image = idleAnimation;
        } else if (currentAnimation.equals("moving")) {
            image = movingAnimation[animationIndex];
        }
        return image;
    }

    /**
     * Moves the lawnmower horizontally by updating its xPosition.
     *
     * @param xPosition The new horizontal position of the lawnmower.
     * @param width The width of the screen, used for scaling the position.
     */
    public void move(int xPosition, int width) {
        this.xPosition = width * xPosition / 1280;
    }

    /**
     * Updates the animation index based on the current animation state.
     * If the lawnmower is idle, the animation index is set to 0.
     * If the lawnmower is moving, the animation index is incremented to display the next frame.
     */
    public void updateIndex() {
        switch (currentAnimation) {
            case ("idle"):
                animationIndex = 0;
            case ("moving"):
                if (animationIndex < movingAnimation.length - 1) {
                    ++animationIndex;
                }
                break;
            default:
                break;
        }
    }

    /**
     * Plays the sound for the active lawnmower.
     * The sound is only loaded once and played repeatedly while the lawnmower is active.
     */
    public void playAudio() {
        if (activeMower == null) {
            activeMower = new Sound("src/resources/sound/soundeffects/SFX-lawnmower.wav");
            activeMower.startClip();
        }
    }

    /**
     * Returns the height of the lawnmower image.
     *
     * @return The height of the lawnmower.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the width of the lawnmower image.
     *
     * @return The width of the lawnmower.
     */
    public int getWidth() {
        return width;
    }
}
