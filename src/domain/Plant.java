package domain;

import java.awt.*;

/**
 * The Plant class represents a base for various types of plants in the game
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public abstract class Plant extends Hitbox {
    public static final Color HITBOX_COLOR = Color.GREEN;
    protected int sunCost;
    protected int seedRechargeTime;
    protected int preparationTime;
    protected int range;
    protected int healthPoints;


    /**
     * Constructs a Plant at the specified row and column.
     *
     * @param row    The row where the plant is located.
     * @param column The column where the plant is located.
     */
    public Plant(int row, int column) {
        super(new int[]{42, 101}, Hitbox.convertPlantPosition(row, column), HITBOX_COLOR);
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
     * Abstract method for shooting a projectile.
     *
     * @return A projectile created by the plant.
     */
    public abstract Projectile shoot();

    /**
     * Gets the time in which the plants do an action.
     *
     * @return preparationTime
     */
    public int getPreparationTime() {
        return preparationTime;
    }

    /**
     * Retrieves the seed recharge time for the plant.
     *
     * @return The time in milliseconds required for the plant's seed to recharge.
     */
    public int getSeedRechargeTime() {
        return seedRechargeTime;
    }

    /**
     * Retrieves the health points for the plant.
     *
     * @return The current health points of the plant.
     */
    public int getHealthPoints() {
        return healthPoints;
    }
}
