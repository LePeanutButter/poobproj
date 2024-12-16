package presentation;

import java.awt.*;

/**
 * Abstract class that represents the interface of a projectile.
 * This class handles the projectile's position, size, movement, and animation when it hits a target.
 * The animation of the projectile includes a "splat" effect when the projectile hits a target.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12, 2024
 */
public abstract class ProjectileGUI {
    protected int xPosition;
    protected int yPosition;
    protected int width;
    protected int height;
    protected Image projectile;
    protected Image[] splatAnimation;
    protected int animationIndex = -1;
    private boolean isHitting = false;

    /**
     * Constructs a ProjectileGUI object with the specified position and size.
     * The position and size are adjusted to fit the screen resolution based on the provided width and height.
     *
     * @param xPosition The x-coordinate of the projectile's position.
     * @param yPosition The y-coordinate of the projectile's position.
     * @param width     The width of the screen.
     * @param height    The height of the screen.
     */
    public ProjectileGUI(int xPosition, int yPosition, int width, int height) {
        this.xPosition = (xPosition - 5) * width / 1280;
        this.yPosition = (yPosition - 5) * height / 720;
        this.width = width * 35 / 1280;
        this.height = height * 35 / 720;
    }

    /**
     * Abstract method for moving the projectile.
     *
     * @param width     The width of the screen.
     * @param xPosition The new x-coordinate of the projectile's position.
     */
    public abstract void move(int width, int xPosition);

    /**
     * Returns the current image of the projectile. If the projectile is not hitting anything, it returns the projectile image.
     * If the projectile is hitting, it returns the current frame of the splat animation.
     *
     * @return The current image representing the projectile or its splat animation.
     */
    public Image getImage() {
        Image image;
        if (animationIndex < 0) {
            image = projectile;
        } else {
            image = splatAnimation[animationIndex];
        }
        return image;
    }

    /**
     * Updates the animation index for the splat effect. If the animation is still ongoing,
     * the index is incremented to show the next frame. Once the animation completes, the projectile is no longer hitting.
     */
    public void updateIndex() {
        if (animationIndex < splatAnimation.length - 1) {
            ++animationIndex;
        } else {
            isHitting = false;
        }
    }

    /**
     * Returns the height of the projectile.
     *
     * @return The height of the projectile.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the width of the projectile.
     *
     * @return The width of the projectile.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Initiates the "hitting" state, which starts the splat animation from the first frame.
     * Once the projectile hits a target, this method is called to trigger the splat effect.
     */
    public void hit() {
        isHitting = true;
        animationIndex = 0;
    }

    /**
     * Checks if the projectile is currently hitting a target.
     *
     * @return true if the projectile is hitting a target, otherwise false.
     */
    public boolean isHitting() {
        return isHitting;
    }
}
