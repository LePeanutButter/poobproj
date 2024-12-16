package domain;

/**
 * Represents a Potato Mine, a type of plant in the game.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 07 2024
 */

public class PotatoMine extends Plant {
    private boolean active = false;
    private double cooldown = 0;

    /**
     * Constructs a new Potato Mine at the specified row and column.
     *
     * @param row    The row in the grid where the Potato Mine is placed.
     * @param column The column in the grid where the Potato Mine is placed.
     */
    public PotatoMine(int row, int column) {
        super(row, column);
        sunCost = 25;
        seedRechargeTime = 30000;
        preparationTime = 15000;
        range = 0;
        healthPoints = 6;
    }

    /**
     * Potato Mine does not shoot projectiles, so this method always returns null.
     *
     * @return null as the Potato Mine does not shoot.
     */
    @Override
    public Projectile shoot() {
        return null;
    }

    /**
     * Handles the Potato Mine's explosion logic.
     */
    public void cooldown() {
        if (cooldown >= preparationTime) {
            active = true;
        } else {
            cooldown += (double) 100 / 3;
        }
    }

    /**
     * Checks whether the Potato Mine is active.
     *
     * @return true if the Potato Mine is active, otherwise false.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Empty implementation of the move method. Plants do not move.
     */
    @Override
    public void move() {
    }
}