package domain;

/**
 * The Evolve class represents a type of plant that can shoot projectiles depending on
 * the time they were placed on the board. This was made as an extension for the final
 * term exam.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 13 2024
 */
public class Evolve extends Plant {
    private double evolveTimer = 0;
    private double speed = Double.POSITIVE_INFINITY;

    /**
     * Constructs an Evolve plant at the specified row and column.
     *
     * @param row    The row where the Peashooter is located.
     * @param column The column where the Peashooter is located.
     */
    public Evolve(int row, int column) {
        super(row, column);
        sunCost = 200;
        seedRechargeTime = 7500;
        preparationTime = 0;
        range = 0;
        healthPoints = 10;
    }

    /**
     * Shoots a projectile when enough time has passed.
     *
     * @return A new Leave projectile if ready, otherwise null.
     */
    @Override
    public Projectile shoot() {
        Leave projectile = null;
        if (preparationTime >= speed) {
            int x = getHitbox().x + getHitbox().width;
            int y = getHitbox().y + 20;
            projectile = new Leave(new int[]{x, y});
            projectile.setDamage(1);
            preparationTime = 0;
        } else {
            preparationTime += 100 / 3;
        }
        return projectile;
    }

    /**
     * Empty implementation of the move method. Plants do not move.
     */
    @Override
    public void move() {
    }

    /**
     * Updates the evolve timer and adjusts the plant's attributes (speed and range)
     * based on the elapsed time.
     *
     * The evolve timer increases as time passes, and at specific time thresholds,
     * the plant's attributes are modified:
     * - After 15 seconds (15000 milliseconds), the range is set to 9 and speed to 3000.
     * - After 35 seconds (35000 milliseconds), the speed is set to 1000.
     *
     * @return true if the plant's attributes were updated (either range or speed),
     *         false otherwise.
     */
    public boolean updateEvolveTimer() {
        boolean state = false;
        evolveTimer += (double) 100 / 3;
        if (evolveTimer >= 35000) {
            if (speed != 1000) {
                speed = 1000;
                state = true;
            }
        } else if (evolveTimer >= 15000) {
            if (range != 9 && speed != 3000) {
                range = 9;
                speed = 3000;
                state = true;
            }
        }
        return state;
    }
}
