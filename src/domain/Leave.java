package domain;

import java.awt.*;
import java.io.Serializable;
/**
 * The Leave class represents a specific type of projectile that can be fired by plants in the game.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public class Leave extends Projectile implements Serializable {
    public static final Color HITBOX_COLOR= Color.BLUE;

    /**
     * Constructs a Leave projectile at the specified position with a blue hitbox color.
     *
     * @param row The row where the Leave is initially located.
     * @param column The column where the Leave is initially located.
     */
    public Leave(int row, int column) {
        super(row, column,HITBOX_COLOR);
    }
}
