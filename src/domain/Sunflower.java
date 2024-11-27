package domain;

/**
 * The Sunflower class represents a type of plant that generates sun points for the player
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public class Sunflower extends Plant {
    /**
     * Constructs a Sunflower at the specified row and column, initializing its attributes
     * with default values specific to this plant type.
     *
     * @param row The row where the Sunflower is located.
     * @param column The column where the Sunflower is located.
     */
    public Sunflower(int row, int column) {
        super(row, column);
        this.name="Sunflower";
        this.sunCost=50;
        this.seedRechargeTime=7500;
        this.healthPoints=6;
        this.preparationTime=24000;
        this.range=0;
    }
    /**
     * The Sunflower does not shoot projectiles, so this method always returns null.
     *
     * @return null as Sunflower does not have shooting capability.
     */
    @Override
    public Leave shoot() {
        return null;
    }
}
