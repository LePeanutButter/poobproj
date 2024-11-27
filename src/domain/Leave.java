package domain;

import java.awt.*;
/**
 * The Leave class represents a specific type of projectile that can be fired by plants in the game.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public class Leave extends Projectile {
    public static final Color HITBOX_COLOR= Color.BLUE;

    /**
     * Constructs a Leave projectile at the specified position with a blue hitbox color.
     *
     */
    public Leave(int[] position) {
        super(position, HITBOX_COLOR);
    }

    @Override
    public boolean hitboxCollision(Rectangle other) {
        return false;
    }

    /**
     * Moves the projectile in a specific direction.
     */
    @Override
    public void move() {
        this.hitbox.translate(15, 0);
    }
}
