package domain;
import java.awt.*;
import java.io.Serializable;

/**
 * The Plant class represents a base for various types of plants in the game
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public abstract class Plant extends Hitbox implements Serializable {
    public static final Color HITBOX_COLOR= Color.GREEN;
    protected String name;
    protected int sunCost;
    protected double seedRechargeTime;
    protected int healthPoints;
    protected double preparationTime;
    protected int range;
    protected int row;
    protected int column;

    /**
     * Constructs a Plant at the specified row and column.
     *
     * @param row The row where the plant is located.
     * @param column The column where the plant is located.
     */
    public Plant(int row, int column) {
        super(new int[] {32,71},convertPosition(row, column), HITBOX_COLOR);
    }

    /**
     * Gets the sun cost of the plant.
     *
     * @return The sun cost required to place the plant.
     */
    public int getSunCost() {
        return sunCost;
    }

    /**
     * Reduces the plant's health points by a specified amount of damage.
     *
     * @param amount The amount of damage to be taken.
     */
    public void takeDamage(int amount) {
        healthPoints -= amount;
    }

    /**
     * Gets the effective range of the plant.
     *
     * @return The range within which the plant can act.
     */
    public int getRange() {
        return range;
    }

    /**
     * Gets the row position of the plant.
     *
     * @return The row position of the plant in the grid.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column position of the plant.
     *
     * @return The column position of the plant in the grid.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Abstract method for shooting a projectile.
     *
     * @return A projectile created by the plant.
     */
    public abstract Projectile shoot();

    /**
     * Empty implementation of the move method. Plants do not move.
     */
    @Override
    public void move() {
    }
}
