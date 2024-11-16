package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * The TileButton class purpose is to extend the JButton Class by drawing round edge rectangles on the background and change their color.
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Naralia.
 * @version 2024-11-15
 */
public class TileButton extends JButton {
    private Color color;

    /**
     * Constructor for the TileButton class, making the JButton invisible while also assigning the color
     * of the rectangle.
     * @param color     Color of the rectangle.
     */
    public TileButton(Color color) {
        this.color = color;
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
    }

    /**
     * Draws the rectangle on the background of the button.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(color);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
    }

    /**
     * Changes the color of the rectangle and repaints it.
     * @param newColor  Color of the rectangle.
     */
    public void changeColor(Color newColor) {
        this.color = newColor;
        repaint();
    }
}
