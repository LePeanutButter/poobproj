package domain;

import java.awt.*;

/**
 * The Zombie class represents a zombie in the game. Zombies move across the lawn and attempt to attack plants.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 22, 2024
 */
public abstract class Zombie extends Hitbox {
    public static final Color HITBOX_COLOR = Color.RED;
    protected int brainCost;
    protected int slotRechargeTime;
    protected int healthPoints;
    protected int preparationTime;
    protected int range;
    protected char action;
    protected double xPosition;

    /**
     * Constructs a Zombie at a specific position on the grid.
     *
     * @param row    The row position of the zombie.
     * @param column The column position of the zombie.
     */
    public Zombie(int row, int column) {
        super(new int[]{42, 101}, Hitbox.convertZombiePosition(row, column), HITBOX_COLOR);
        xPosition = hitbox.x;
        action = 'm';
    }

    /**
     * Moves the zombie across the lawn by updating its X position.
     */
    @Override
    public void move() {
        xPosition -= (double) 59 / 90;
        if ((int) xPosition < hitbox.x) {
            hitbox.translate(-1, 0);
        }
    }


    /**
     * Gets the brain cost required to activate this zombie.
     *
     * @return The brain cost of the zombie.
     */
    public int getBrainCost() {
        return brainCost;
    }

    /**
     * Damages the zombie by reducing its health points.
     *
     * @param amount The amount of damage to deal to the zombie.
     */
    public void takeDamage(int amount) {
        healthPoints -= amount;
    }

    /**
     * Gets the range of the zombie's attack.
     *
     * @return The range of the zombie's attack.
     */
    public int getRange() {
        return range;
    }

    /**
     * This method should be implemented by subclasses to define the projectile behavior of the zombie.
     *
     * @return A POOmBas projectile to be fired by the zombie.
     */
    public abstract POOmBas shoot();

    /**
     * This method should be implemented by subclasses to define the zombie's attack behavior.
     *
     * @return A boolean value indicating whether the zombie is attacking.
     */
    public abstract boolean attack();

    /**
     * Gets the current action the zombie is performing.
     *
     * @return The current action of the zombie.
     */
    public char getAction() {
        return action;
    }

    /**
     * Returns the current health points of the zombie.
     *
     * @return The current health points of the zombie.
     */
    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * Sets the action that the zombie is performing.
     * This method updates the zombie's current action. The action can be one of the following:
     * 'a' - Attack action, indicating the zombie is attacking.
     * 'm' - Walk action, indicating the zombie is walking.
     *
     * @param action The action to be set for the zombie. It should be either 'a' for attack or 'w' for walk.
     */
    public void setAction(char action) {
        this.action = action;
    }
}
