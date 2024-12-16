package domain;

/**
 * The Sunflower class represents a type of plant that generates sun points for the player
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public class Sunflower extends Plant {
    private double sunTimer = 0;

    /**
     * Constructs a Sunflower at the specified row and column.
     *
     * @param row    The row where the Sunflower is located.
     * @param column The column where the Sunflower is located.
     */
    public Sunflower(int row, int column) {
        super(row, column);
        sunCost = 50;
        seedRechargeTime = 7500;
        preparationTime = 24000;
        range = 0;
        healthPoints = 6;
    }

    /**
     * The Sunflower does not shoot projectiles, so this method always returns null.
     *
     * @return null as Sunflower does not have shooting capability.
     */
    @Override
    public Leave shoot() {
        return null;
    }

    /**
     * Returns the amount of sun produced by the sunflower after its preparation time has passed.
     * This method checks whether the sunflower has reached the required preparation time to produce sun.
     * If the sunflower is ready, it produces 25 sun and resets its timer. If it is not ready, it increments
     * the sun production timer.
     *
     * @return The amount of sun produced by the sunflower. If the preparation time has been reached,
     * the method returns 25. If not, it returns 0 and increments the sun timer.
     */
    public int getSun() {
        int sun = 0;
        if (sunTimer >= preparationTime) {
            sun = 25;
            sunTimer = 0;
        } else {
            sunTimer += (double) 100 / 3;
        }
        return sun;
    }

    /**
     * Empty implementation of the move method. Plants do not move.
     */
    @Override
    public void move() {
    }
}
