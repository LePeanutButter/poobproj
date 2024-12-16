package domain;

public class Brainstein extends Zombie {
    private double brainTimer = 0;

    /**
     * Constructs an Basic zombie at the specified row and column, initializing its attributes
     * with default values specific to this zombie type.
     *
     * @param row    The row where the Basic zombie is located.
     * @param column The column where the Basic zombie is located.
     */
    public Brainstein(int row, int column) {
        super(row, column);
        brainCost = 50;
        slotRechargeTime = 7500;
        preparationTime = 24000;
        range = 0;
        this.healthPoints = 72;
    }

    /**
     * The shoot method for Basic zombie. This zombie does not shoot any projectiles, so this method
     * returns null.
     *
     * @return null as Basic zombie does not shoot.
     */
    @Override
    public POOmBas shoot() {
        return null;
    }

    /**
     * Handles the attack logic for the Basic zombie.
     *
     * @return true if the zombie can attack, otherwise false.
     */
    @Override
    public boolean attack() {
        return false;
    }

    /**
     * Returns the amount of brain produced by the brainstein after its preparation time has passed.
     * This method checks whether the brainstein has reached the required preparation time to produce brain.
     * If the brainstein is ready, it produces 25 brain and resets its timer. If it is not ready, it increments
     * the brain production timer.
     *
     * @return The amount of brain produced by the brainstein. If the preparation time has been reached,
     * the method returns 25. If not, it returns 0 and increments the brain timer.
     */
    public int getBrain() {
        int brain = 0;
        if (brainTimer >= preparationTime) {
            brain = 25;
            brainTimer = 0;
        } else {
            brainTimer += (double) 100 / 3;
        }
        return brain;
    }
}
