package presentation;

import javax.swing.*;

/**
 * A class that represents the economic system in the game, handling the functionality 
 * for the creation of different types of currencies, such as Sun and Brain.
 * This abstract class serves as the base for different types of currency, each 
 * having its own specific behavior for setting buttons and moving within the economy system.
 * 
 * The economy system allows players to manage resources such as Sun or Brain to purchase plants or other in-game items.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12, 2024
 */
public abstract class Economy extends JButton {
    protected int buttonWidth;
    protected int buttonHeight;
    protected final int frameHeight;
    protected final int fromX;
    protected int actualPosition;
    protected final int toY;
    protected final int economySize;
    protected double timer = 0;
    protected boolean timerRunOut = false;

    /**
     * Constructs an Economy object with the specified positioning and economy size.
     * This constructor initializes the position, size, and frame height for the economy button.
     *
     * @param fromX      The starting x-coordinate of the economy button on the screen.
     * @param toY        The ending y-coordinate of the economy button on the screen.
     * @param economySize The size of the economy system, typically used for scaling.
     * @param width      The width of the screen for scaling.
     * @param height     The height of the screen for scaling.
     */
    public Economy(int fromX, int toY, int economySize, int width, int height) {
        this.economySize = economySize;
        this.frameHeight = height;
        this.fromX = fromX;
        this.toY = toY;
    }

    /**
     * Abstract method that defines the behavior for setting the button's properties.
     *
     * @param width The width of the button to be set.
     * @param height The height of the button to be set.
     */
    public abstract void setButton(int width, int height);

    /**
     * Abstract method that defines the behavior for moving the economy button on the screen.
     */
    public abstract void moveEconomy();

    /**
     * Sets the position of the economy button on the screen using the provided x and y coordinates.
     * 
     * @param x The x-coordinate for positioning the economy button.
     * @param y The y-coordinate for positioning the economy button.
     */
    public void setEconomy(int x, int y) {
        actualPosition = y;
        setBounds(x, actualPosition, buttonWidth, buttonHeight);
    }

    /**
     * Returns the size of the economy system, which is typically used for scaling or determining the relative size of the economy elements.
     *
     * @return The size of the economy system.
     */
    public int getEconomySize() {
        return economySize;
    }

    /**
     * Updates the timer value and checks if it has run out. The timer increments by a fixed value and
     * once it reaches a threshold, the timerRunOut flag is set to true.
     */
    public void updateTimer() {
        timer += (double) 100 / 3;
        if (timer >= 5000) {
            timerRunOut = true;
        }
    }

    /**
     * Returns whether the timer has run out.
     *
     * @return true if the timer has run out, otherwise false.
     */
    public boolean getTimerRunOut() {
        return timerRunOut;
    }
}
