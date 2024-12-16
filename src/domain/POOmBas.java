package domain;

import java.awt.*;

/**
 * Represents a specific type of projectile, POOmBas, which is used in the Plants vs. Zombies game.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public class POOmBas extends Projectile {
    public static final Color HITBOX_COLOR = Color.MAGENTA;

    /**
     * Constructs a Leave projectile at the specified position with a blue hitbox color.
     *
     * @param position An array representing the x and y coordinates where the projectile will be placed.
     */
    public POOmBas(int[] position) {
        super(position, HITBOX_COLOR);
    }

    /**
     * Moves the projectile in a specific direction.
     */
    @Override
    public void move() {
        this.hitbox.translate(-15, 0);
    }
}
