package domain;

import java.awt.*;

public abstract class Hitbox {
    protected Rectangle hitbox;
    protected Color color;
    protected int[] position;

    public Hitbox(int[] dimension, int[] position, Color color) {

    }
    public abstract void move();

    public Rectangle getHitbox() {
        return hitbox;
    }

    public Color getColor() {
        return color;
    }

    public int[] getPosition() {
        return position;
    }
    public static int[] convertDimensions(int width, int height){

    }
    public static int[] convertPosition(int row, int column){

    }
}
