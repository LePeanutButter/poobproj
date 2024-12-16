package presentation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * Represents the Sun currency in the game, used to purchase plants. 
 * This class inherits from Economy and provides specific behavior for the Sun currency button.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12, 2024
 */
public class Sun extends Economy {
    private final List<ImageIcon> animation;
    private int animationIndex = 0;

    /**
     * Constructs a Sun object with the specified positioning and size.
     * This constructor loads the animation frames for the Sun button and initializes the button size.
     *
     * @param fromX      The starting x-coordinate of the Sun button on the screen.
     * @param toY        The ending y-coordinate of the Sun button on the screen.
     * @param sunSize    The size of the Sun currency in the game.
     * @param width      The width of the screen for scaling the Sun button.
     * @param height     The height of the screen for scaling the Sun button.
     */
    public Sun(int fromX, int toY, int sunSize, int width, int height) {
        super(fromX, toY, sunSize, width, height);
        this.animation = new ArrayList<>();
        loadAnimation();
        SwingUtilities.invokeLater(() -> setButton(width, height));
    }

    /**
     * Loads the frames for the Sun animation by loading images from the file system.
     */
    public final void loadAnimation() {
        for (int i = 1; i <= 13; i++) {
            String imageName = String.format("src/resources/imag/Level Interface/Sun/Sun%04d.png", i);
            animation.add(new ImageIcon(imageName));
        }
    }

    /**
     * Updates the animation index to display the next frame of the Sun animation.
     * If the last frame is reached, it resets the animation to the first frame.
     */
    public void updateIndex() {
        if (animationIndex < animation.size() - 1) {
            ++animationIndex;
        } else {
            animationIndex = 0;
        }
    }

    /**
     * Sets the Sun button's image and scales it according to the provided screen dimensions.
     * This method is called to initialize the button size and set the default Sun image.
     *
     * @param width  The width of the screen for scaling the button's image.
     * @param height The height of the screen for scaling the button's image.
     */
    @Override
    public void setButton(int width, int height) {
        Image sunImage = animation.getFirst().getImage();
        int newWidth = width * 231 / 1280;
        int newHeight = height * 231 / 720;
        ImageIcon sunIcon = new ImageIcon(sunImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        buttonWidth = width * 128 / 1280;
        buttonHeight = height * 125 / 720;
        setIcon(sunIcon);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
    }

    /**
     * Sets the vertical position of the Sun on the screen, initially placing it off-screen at the top.
     * The Sun starts off-screen and moves downward.
     *
     * @param height The height of the screen used for positioning the Sun button.
     */
    public void setSky(int height) {
        actualPosition = -(height * 125 / 720);
        setBounds(fromX, actualPosition, buttonWidth, buttonHeight);
    }

    /**
     * Updates the Sun button's image to the current animation frame based on the animationIndex.
     */
    public void setImage() {
        setIcon(animation.get(animationIndex));
    }

    /**
     * Moves the Sun button upward and updates the animation frame.
     * The button continues to move upwards until it reaches the target position (`toY`).
     */
    @Override
    public void moveEconomy() {
        updateIndex();
        setImage();
        if (actualPosition < toY) {
            actualPosition += frameHeight * 4 / 720;
            setBounds(fromX, actualPosition, buttonWidth, buttonHeight);
        } else {
            updateTimer();
        }
        setEnabled(true);
    }
}
