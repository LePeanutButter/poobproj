package presentation;

import java.awt.*;
import javax.swing.SwingUtilities;

/**
 * An abstract class representing the graphical user interface for plants in the game.
 * The ZombieGUI class provides the graphical representation of a zombie in the game.
 * This abstract class defines the behavior and attributes of a zombie's animations, movement, 
 * and interaction with the interface.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12 , 2024
 */
public abstract class ZombieGUI {
    protected int xPosition;
    protected int yPosition;
    protected int width;
    protected int height;
    protected Image[] walkingAnimation;
    protected Image[] eatingAnimation;
    protected Image[] deathAnimation;
    protected Image[] mowerAnimation;
    protected String currentAnimation;
    protected int animationIndex;
    protected double chomp = 1000;
    protected boolean deathAnimationPlaying = false;
    protected double move;

    /**
     * Constructs a new ZombieGUI object with the specified parameters.
     * Initializes the zombie's position, size, and animation settings.
     * 
     * @param xPosition The initial X-coordinate of the zombie.
     * @param yPosition The initial Y-coordinate of the zombie.
     * @param width     The screen width used to scale the zombie's size.
     * @param height    The screen height used to scale the zombie's size.
     */
    public ZombieGUI(int xPosition, int yPosition, int width, int height) {
        this.xPosition = (xPosition - 130) * width / 1280;
        this.yPosition = (yPosition - 100) * height / 720;
        this.width = width * 252 / 1280;
        this.height = height * 227 / 720;
        currentAnimation = "walk";
        move = xPosition;
        animationIndex = 0;
        SwingUtilities.invokeLater(this::loadAnimationFrames);
    }

    /**
     * Loads the frames for the zombie's animations. This method must be implemented by subclasses
     * to define specific animation frames for each type of zombie.
     */
    protected abstract void loadAnimationFrames();

    /**
     * Updates the zombie's X-coordinate to a new position, recalculated based on the screen width.
     * 
     * @param width     The screen width used for scaling.
     * @param xPosition The new X-coordinate of the zombie.
     */
    public void move(int width, int xPosition) {
        this.xPosition = (xPosition - 130) * width / 1280;
    }

    /**
     * Changes the current animation of the zombie and resets the animation index.
     * Special adjustments are made for "dying" and "mower" animations.
     * 
     * @param animation The new animation to play.
     * @param width     The screen width used for scaling.
     * @param height    The screen height used for scaling.
     */
    public void changeAnimation(String animation, int width, int height) {
        currentAnimation = animation;
        animationIndex = 0;
        if (animation.equals("dying")) {
            deathAnimationPlaying = true;
        } else if (animation.equals("mower")) {
            deathAnimationPlaying = true;
            xPosition += 96 * width / 1280;
            yPosition += 34 * height / 720;
        }
    }

    /**
     * Updates the current animation frame index based on the animation being played.
     * Resets the index if it reaches the end of the animation. 
     */
    public void updateIndex() {
        switch (currentAnimation) {
            case ("walk") -> {
                if (animationIndex < walkingAnimation.length - 1) {
                    ++animationIndex;
                } else {
                    animationIndex = 0;
                }
            }
            case ("eating") -> {
                if (animationIndex < eatingAnimation.length - 1) {
                    ++animationIndex;
                } else {
                    animationIndex = 0;
                }
                if (chomp >= 600) {
                    Sound chompSFX = new Sound("src/resources/sound/soundeffects/SFX-chomp.wav");
                    chompSFX.startClip();
                    chomp = 0;
                } else {
                    chomp += (double) 100 / 3;
                }
            }
            case ("dying") -> {
                if (animationIndex < deathAnimation.length - 1) {
                    ++animationIndex;
                } else {
                    deathAnimationPlaying = false;
                }
                if (animationIndex == 22) {
                    Sound falling = new Sound("src/resources/sound/soundeffects/SFX-zombie-falling-1.wav");
                    falling.startClip();
                }
            }
            case ("mower") -> {
                if (animationIndex < mowerAnimation.length - 1) {
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
     * Gets the current image frame for the zombie's animation.
     * Subclasses must implement this method to return the specific frame of the current animation.
     * 
     * @return The current Image frame for the zombie.
     */
    public abstract Image getImage();

    /**
     * Gets the width of the zombie's representation.
     * 
     * @return The width of the zombie.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the zombie's representation.
     * 
     * @return The height of the zombie.
     */
    public int getHeight() {
        return height;
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
