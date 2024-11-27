package domain;

import java.awt.*;

public class POOmBas extends Projectile {
    public static final Color HITBOX_COLOR= Color.MAGENTA;

    /**
     * Constructs a Leave projectile at the specified position with a blue hitbox color.
     *
     */
    public POOmBas(int[] position) {
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
        this.hitbox.translate(-15, 0);
    }
}
