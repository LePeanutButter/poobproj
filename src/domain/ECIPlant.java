package domain;

/**
 * The ECIPlant class represents a special plant that generates a larger sun worth 50 units at its own position.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public class ECIPlant extends Plant {
    private double sunTimer = 0;

    /**
     * Constructs an ECIPlant at the specified row and column.
     *
     * @param row    The row where the ECIPlant is located.
     * @param column The column where the ECIPlant is located.
     */
    public ECIPlant(int row, int column) {
        super(row, column);
        sunCost = 75;
        seedRechargeTime = 7500;
        preparationTime = 30000;
        range = 0;
        healthPoints = 10;
    }

    /**
     * The shoot method for ECIPlant. This plant does not shoot any projectiles, so this method
     * returns null.
     *
     * @return null as ECIPlant does not shoot.
     */
    @Override
    public Leave shoot() {
        return null;
    }

    /**
     * Returns the amount of sun produced by the ECIPlant after its preparation time has passed.
     * This method checks whether the ECIPlant has reached the required preparation time to produce sun.
     * If the plant is ready, it produces 50 sun and resets its timer. If it is not ready, it increments
     * the sun production timer, moving it closer to the next production cycle.
     *
     * @return The amount of sun produced by the ECIPlant. If the preparation time has been reached,
     * the method returns 50. If not, it returns 0 and increments the sun timer.
     */
    public int getSun() {
        int sun = 0;
        if (sunTimer >= preparationTime) {
            sun = 50;
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
