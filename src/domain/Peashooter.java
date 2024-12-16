package domain;

/**
 * The Peashooter class represents a type of plant that can shoot projectiles
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public class Peashooter extends Plant {


    /**
     * Constructs a Peashooter at the specified row and column.
     *
     * @param row    The row where the Peashooter is located.
     * @param column The column where the Peashooter is located.
     */
    public Peashooter(int row, int column) {
        super(row, column);
        sunCost = 100;
        seedRechargeTime = 7500;
        preparationTime = 0;
        range = 9;
        healthPoints = 6;
    }

    /**
     * Shoots a projectile when enough time has passed.
     *
     * @return A new Leave projectile if ready, otherwise null.
     */
    @Override
    public Projectile shoot() {
        Leave projectile = null;
        if (preparationTime >= 1500) {
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
}
