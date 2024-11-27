package domain;

/**
 * The Peashooter class represents a type of plant that can shoot projectiles
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public class Peashooter extends Plant {

    /**
     * Constructs a Peashooter at the specified row and column, initializing its attributes
     * with default values specific to this plant type.
     *
     * @param row The row where the Peashooter is located.
     * @param column The column where the Peashooter is located.
     */
    public Peashooter(int row, int column) {
        super(row, column);
        this.name="Peashooter";
        this.sunCost=100;
        this.seedRechargeTime=7500;
        this.healthPoints=6;
        this.preparationTime=0;
        this.range=9;
    }

    /**
     * Shoots a projectile when enough time has passed.
     *
     * @return A new Leave projectile if ready, otherwise null.
     */
    @Override
    public Projectile shoot() {
        Leave projectile = null;
        if (preparationTime == 1500){
            projectile = new Leave(position);
            preparationTime=0;
        } else {
            preparationTime += (double) 100 /3;
        }
        return projectile;
    }
}
