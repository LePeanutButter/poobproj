package domain;

import java.awt.*;
import java.io.Serializable;

public abstract class Hitbox implements Serializable {
    protected Rectangle hitbox;
    protected Color color;
    protected int[] position;

    public Hitbox(int[] dimension, int[] position, Color color) {
        hitbox = new Rectangle(position[0], position[1], dimension[0], dimension[1]);
        this.color = color;
        this.position = position;
    }

    public abstract void move();

    public Color getColor() {
        return color;
    }

    public int[] getPosition() {
        return position;
    }

    public static int[] convertPlantPosition(int row, int column) {
        int width = 300 + (column * 58);
        int height = 100 + ((row - 1) * 118);
        return new int[] {width, height};
    }

    public static int[] convertZombiePosition(int row, int column) {
        int[] position = new int[2];
        if (column == 0) {
            position[0] = 1280;
        } else {
            position[0] = 300 + ((column - 1) * 58);
        }
        position[1] = 100 + ((row - 1) * 100);
        return position;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public abstract boolean hitboxCollision(Rectangle other);
}
