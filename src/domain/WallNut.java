package domain;

/**
 * The Wall-nut class represents a defensive plant with high health points, designed to block enemies.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public class WallNut extends Plant {

    /**
     * Constructs a Wall-nut at the specified row and column.
     *
     * @param row    The row where the Wall-nut is located.
     * @param column The column where the Wall-nut is located.
     */
    public WallNut(int row, int column) {
        super(row, column);
        sunCost = 50;
        preparationTime = 0;
        range = 0;
        healthPoints = 72;
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

    /**
     * Empty implementation of the move method. Plants do not move.
     */
    @Override
    public void move() {
    }
}

