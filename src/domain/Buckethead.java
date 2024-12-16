package domain;

/**
 * The Buckethead class represents a type of zombie in the game.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public class Buckethead extends Zombie {

    /**
     * Constructs a Buckethead zombie at the specified row and column, initializing its attributes
     * with default values specific to this zombie type.
     *
     * @param row    The row where the Buckethead zombie is located.
     * @param column The column where the Buckethead zombie is located.
     */
    public Buckethead(int row, int column) {
        super(row, column);
        brainCost = 100;
        slotRechargeTime = 7500;
        preparationTime = 600;
        range = 0;
        this.healthPoints = 65;
    }

    /**
     * The shoot method for Buckethead zombie. This zombie does not shoot any projectiles, so this method
     * returns null.
     *
     * @return null as Buckethead zombie does not shoot.
     */
    @Override
    public POOmBas shoot() {
        return null;
    }

    /**
     * Handles the attack logic for the Buckethead zombie.
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
