package presentation;

import java.awt.*;
import javax.swing.*;

/**
 * Represents the Brain currency in the game, used to purchase zombies. 
 * This class inherits from Economy and provides specific behavior for the Brain currency button.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12, 2024
 */
public class Brain extends Economy {

    /**
     * Constructs a Brain object with the specified position, size, and screen dimensions.
     * It initializes the Brain button and sets its position and appearance on the screen.
     *
     * @param fromX   The starting horizontal position for the Brain on the screen.
     * @param toY     The target vertical position for the Brain.
     * @param brainSize The size of the Brain object.
     * @param width   The width of the screen, used for scaling the size of the Brain.
     * @param height  The height of the screen, used for scaling the size of the Brain.
     */
    public Brain(int fromX, int toY, int brainSize, int width, int height) {
        super(fromX, toY, brainSize, width, height);
        SwingUtilities.invokeLater(() -> setButton(width, height));
    }

    /**
     * Moves the Brain object on the screen by decreasing its vertical position until it reaches the target position.
     */
    @Override
    public void moveEconomy() {
        if (actualPosition > toY) {
            actualPosition -= frameHeight * 4 / 720;
            setBounds(fromX, actualPosition, buttonWidth, buttonHeight);
        } else {
            updateTimer();
        }
        setEnabled(true);
    }

    /**
     * Sets the position of the Brain object at the specified height on the screen.
     *
     * @param height The height of the screen, used to position the Brain at the correct location.
     */
    public void setGround(int height) {
        actualPosition = height;
        setBounds(fromX, actualPosition, buttonWidth, buttonHeight);
    }

    /**
     * Sets the appearance and size of the Brain button.
     *
     * @param width  The width of the screen, used to calculate the button size.
     * @param height The height of the screen, used to calculate the button size.
     */
    @Override
    public void setButton(int width, int height) {
        int newWidth = width * 52 / 1280;
        int newHeight = height * 48 / 720;
        Image brainImage = new ImageIcon("src/resources/imag/Level Interface/brain.png").getImage();
        ImageIcon sunIcon = new ImageIcon(brainImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        buttonWidth = width * 52 / 1280;
        buttonHeight = height * 48 / 720;
        setIcon(sunIcon);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
    }
}
