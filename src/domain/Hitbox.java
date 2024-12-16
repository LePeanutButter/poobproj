package domain;

import java.awt.*;
import java.io.Serializable;

/**
 * The Hitbox class marks the area where game objects can collide.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 22 2024
 */
public abstract class Hitbox implements Serializable {
    protected Rectangle hitbox;
    protected Color color;

    /**
     * Constructs a Hitbox with specified dimensions, position, and color.
     *
     * @param dimension An array where dimension[0] is the width and dimension[1] is the height of the hitbox.
     * @param position  An array representing the x and y position of the hitbox.
     * @param color     The color of the hitbox.
     */
    public Hitbox(int[] dimension, int[] position, Color color) {
        hitbox = new Rectangle(position[0], position[1], dimension[0], dimension[1]);
        this.color = color;
    }

    /**
     * Abstract method to define movement for specific types of hitboxes.
     */
    public abstract void move();

    /**
     * Gets the color of the hitbox.
     *
     * @return The color of the hitbox.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Converts a plant's position to pixel coordinates.
     *
     * @param row    The row in the grid where the plant is located.
     * @param column The column in the grid where the plant is located.
     * @return An array representing the pixel coordinates of the plant's position.
     */
    public static int[] convertPlantPosition(int row, int column) {
        int[] position = new int[2];
        position[0] = (column * 103) + 360 - (6 * column);
        position[1] = (row * 113) + 98 + (32 / 3);
        return position;
    }

    /**
     * Converts a zombie's position to pixel coordinates.
     *
     * @param row    The row in the grid where the zombie is located.
     * @param column The column in the grid where the zombie is located.
     * @return An array representing the pixel coordinates of the zombie's position.
     */
    public static int[] convertZombiePosition(int row, int column) {
        int[] position = new int[2];
        if (column == 10) {
            position[0] = 1280;
        } else {
            position[0] = (column * 103) + 300 - (6 * column);
        }
        position[1] = (row * 113) + 98 + (32 / 3);
        return position;
    }

    /**
     * Converts a mower's position to pixel coordinates.
     *
     * @param row The row in the grid where the mower is located.
     * @return An array representing the pixel coordinates of the mower's position.
     */
    public static int[] convertMowerPosition(int row) {
        int[] position = new int[2];
        position[0] = 230;
        position[1] = (row * 113) + 138 + (32 / 3);
        return position;
    }

    /**
     * Gets the hitbox as a Rectangle object.
     *
     * @return The hitbox Rectangle.
     */
    public Rectangle getHitbox() {
        return hitbox;
    }

    /**
     * Method to determine collision with another rectangle.
     *
     * @param other The Rectangle to check for a collision with this hitbox.
     * @return True if this hitbox collides with the other rectangle, false otherwise.
     */
    public boolean hitboxCollision(Rectangle other) {
        return hitbox.intersects(other);
    }
}
