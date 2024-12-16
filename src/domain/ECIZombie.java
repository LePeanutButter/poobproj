package domain;

/**
 * The ECIZombie class represents a type of zombie in the game.
 * This zombie has a unique ability to shoot projectiles after a cooldown period.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public class ECIZombie extends Zombie {
    double shootingTime = 3000;

    /**
     * Constructs an ECIZombie zombie at the specified row and column, initializing its attributes
     * with default values specific to this zombie type.
     *
     * @param row    The row where the ECIZombie zombie is located.
     * @param column The column where the ECIZombie zombie is located.
     */
    public ECIZombie(int row, int column) {
        super(row, column);
        brainCost = 100;
        slotRechargeTime = 7500;
        preparationTime = 600;
        range = 0;
        healthPoints = 10;
    }

    /**
     * The shoot method for the ECIZombie. This zombie shoots a projectile
     * after its shooting time reaches the required value.
     *
     * @return The created projectile or null if the zombie is not yet ready to shoot.
     */
    @Override
    public POOmBas shoot() {
        POOmBas projectile = null;
        if (shootingTime >= 3500) {
            int x = hitbox.x + 20;
            int y = hitbox.y + 20;
            projectile = new POOmBas(new int[]{x, y});
            projectile.setDamage(1);
            shootingTime = 0;
        } else {
            shootingTime += (double) 100 / 3;
        }
        return projectile;
    }

    /**
     * Handles the attack logic for the ECIZombie zombie.
     *
     * @return true if the zombie can attack, otherwise false.
     */
    @Override
    public boolean attack() {
        boolean canAttack = false;
        if (preparationTime >= 600) {
            preparationTime = 0;
            canAttack = true;
        } else {
            preparationTime += 100 / 3;
        }
        return canAttack;
    }
}
