package domain;
import java.awt.Color;
import java.io.Serializable;

/**
 * The Tile class purpose is to create a tile with an assigned color. The Serializable interface
 * has to be implemented for the sole purpose of loading custom games.
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Naralia.
 * @version 2024-11-15
 */
public class Tile implements Serializable {
    private Color color;

    /**
     *  Creates a tile with the given color.
     * @param color Color of the tile.
     */
    public Tile (Color color) {
        this.color = color;
    }

    /**
     * Changes the tile color.
     * @param color Color of the tile.
     */
    public void changeColor(Color color) {
        this.color = color;
    }

    /**
     * Gets the color of the tile.
     * @return Tile color.
     */
    public Color getColor() {
        return color;
    }
}
