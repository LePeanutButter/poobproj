package domain;

/**
 * The ECIPlant class represents a special plant that generates a larger sun worth 50 units at its own position.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public class ECIPlant extends Plant {

    /**
     * Constructs an ECIPlant at the specified row and column, initializing its attributes
     * with default values specific to this plant type.
     *
     * @param row The row where the ECIPlant is located.
     * @param column The column where the ECIPlant is located.
     */
    public ECIPlant(int row, int column) {
        super(row, column);
        this.name="ECIPlant";
        this.sunCost=75;
        this.seedRechargeTime=7500;
        this.healthPoints=10;
        this.preparationTime=30000;
        this.range=0;
    }
    /**
     * The shoot method for ECIPlant. This plant does not shoot any projectiles, so this method
     * returns null.
     *
     * @return null as ECIPlant does not have shooting capability.
     */
    @Override
    public Leave shoot() {
        return null;
    }
}
