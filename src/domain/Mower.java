package domain;

import java.awt.*;

/**
 * Represents a mower in the game, which moves horizontally across the screen
 * and interacts with other game elements.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public class Mower extends Hitbox {
    public static final Color HITBOX_COLOR = Color.GRAY;
    private boolean active = false;

    /**
     * Constructs a new Mower object with the specified row position.
     * The mower starts as inactive.
     *
     * @param row The row in which the mower is placed.
     */
    public Mower(int row) {
        super(new int[]{80, 61}, convertMowerPosition(row), HITBOX_COLOR);
    }

    /**
     * Moves the mower horizontally across the screen.
     */
    @Override
    public void move() {
        if (active) {
            hitbox.translate(20, 0);
        }
    }

    /**
     * Checks if the mower is currently active.
     *
     * @return true if the mower is active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * This method sets the mower's status to active
     */
    public void setActive() {
        active = true;
    }
}
