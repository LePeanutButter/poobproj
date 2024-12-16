package domain;

/**
 * The Conehead class represents a type of zombie in the game.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public class Conehead extends Zombie {

    /**
     * Constructs a Conehead zombie at the specified row and column, initializing its attributes
     * with default values specific to this zombie type.
     *
     * @param row    The row where the Conehead zombie is located.
     * @param column The column where the Conehead zombie is located.
     */
    public Conehead(int row, int column) {
        super(row, column);
        brainCost = 75;
        slotRechargeTime = 30000;
        preparationTime = 600;
        range = 0;
        this.healthPoints = 28;
    }

    /**
     * The shoot method for Conehead zombie. This zombie does not shoot any projectiles, so this method
     * returns null.
     *
     * @return null as Conehead zombie does not shoot.
     */
    @Override
    public POOmBas shoot() {
        return null;
    }

    /**
     * Handles the attack logic for the Conehead zombie.
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
