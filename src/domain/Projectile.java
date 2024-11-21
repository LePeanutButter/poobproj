package domain;

import java.awt.*;
import java.io.Serializable;

/**
 * The Projectile class represents a projectile object in the game that has a defined hitbox, color, and damage value.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public abstract class Projectile extends Hitbox implements Serializable {
    private int damage;

    /**
     * Constructs a Projectile with a fixed size.
     *
     * @param row The row where the projectile is initially located.
     * @param column The column where the projectile is initially located.
     * @param color The color of the projectile.
     */
    public Projectile(int row, int column, Color color){
        super(new int[] {25,25},Hitbox.convertPosition(row, column), color);
    }

    /**
     * Moves the projectile in a specific direction.
     */
    @Override
    public void move() {
    }

    /**
     * Returns the amount of damage the projectile causes.
     *
     * @return The damage value of the projectile.
     */
    public int getDamage() {
        return damage;
    }
}
