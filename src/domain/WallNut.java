package domain;

/**
 * The Wall-nut class represents a defensive plant with high health points, designed to block enemies.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public class WallNut extends Plant {

    /**
     * Constructs a Wall-nut at the specified row and column, initializing its attributes
     * with default values specific to this plant type.
     *
     * @param row The row where the Wall-nut is located.
     * @param column The column where the Wall-nut is located.
     */
    public WallNut(int row, int column) {
        super(row, column);
        this.name="Wall-nut";
        this.sunCost=50;
        this.seedRechargeTime=30000;
        this.healthPoints=72;
        this.preparationTime=0;
        this.range=0;
    }
    /**
     * The Wall_nut does not shoot projectiles, so this method always returns null.
     *
     * @return null as Wall_nut does not have shooting capability.
     */
    @Override
    public Leave shoot() {
        return null;
    }

}

