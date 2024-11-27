package domain;

import java.awt.*;

/**
 * The Projectile class represents a projectile object in the game that has a defined hitbox, color, and damage value.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public abstract class Projectile extends Hitbox {
    private int damage;

    /**
     * Constructs a Projectile with a fixed size.
     *
     * @param color The color of the projectile.
     */
    public Projectile(int[] position, Color color){
        super(new int[] {25,25}, position, color);
    }

    /**
     * Returns the amount of damage the projectile causes.
     *
     * @return The damage value of the projectile.
     */
    public int getDamage() {
        return damage;
    }

    public void setDamage(int amount) {
        damage = amount;
    }
}
