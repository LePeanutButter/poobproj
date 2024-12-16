package presentation;

import java.awt.*;
import javax.swing.*;

/**
 * A class that represents the interface for a specific type of projectile, the Leave projectile.
 * This class extends ProjectileGUI and defines specific behavior and images for the Leave projectile,
 * including its movement and "splat" animation when it hits a target.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12, 2024
 */
public class LeaveGUI extends ProjectileGUI {

    /**
     * Constructs a Leave object with the specified position and size.
     * This constructor initializes the projectile's image and the frames for its splat animation.
     *
     * @param xPosition The x-coordinate of the projectile's initial position.
     * @param yPosition The y-coordinate of the projectile's initial position.
     * @param width     The width of the screen.
     * @param height    The height of the screen.
     */
    public LeaveGUI(int xPosition, int yPosition, int width, int height) {
        super(xPosition, yPosition, width, height);
        projectile = new ImageIcon("src/resources/imag/Projectiles/ProjectilePea.png").getImage();
        splatAnimation = new Image[4];
        int count = 0;
        for (int i = 1; i <= 4; i++) {
            String imageName = String.format("src/resources/imag/Projectiles/pea_splats%04d.png", i);
            splatAnimation[count] = new ImageIcon(imageName).getImage();
            count++;
        }
    }

    /**
     * Moves the Leave projectile horizontally based on the provided xPosition.
     *
     * @param width     The width of the screen.
     * @param xPosition The new x-coordinate of the projectile's position.
     */
    @Override
    public void move(int width, int xPosition) {
        this.xPosition = (xPosition - 5) * width / 1280;
    }
}