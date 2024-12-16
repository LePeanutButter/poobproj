package presentation;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
 
/**
 * The PvPProgressBar class extends ProgressBar and provides a graphical 
 * representation of a progress bar for Player vs. Player (PvP) mode. 
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12 , 2024
 */
public class PvPProgressBar extends ProgressBar {
    private final Image fists;
    private BufferedImage blueBar;
    private BufferedImage redBar;
    private int bufferWidth;

    /**
     * Constructs a PvPProgressBar object with the specified width and height.
     * Initializes the dimensions, position, and associated images for the progress bar.
     *
     * @param width  The width of the screen.
     * @param height The height of the screen.
     */
    public PvPProgressBar(int width, int height) {
        super(width * 511 / 1280, height * 56 / 720);
        Image fistsImage = new ImageIcon("src/resources/imag/Level Interface/PvPProgress/Fists.png").getImage();
        this.xPosition = width * 300 / 1280;
        this.yPosition = height * 15 / 720;
        fists = fistsImage.getScaledInstance(width * 222 / 1280, height * 86 / 720, Image.SCALE_SMOOTH);
        try {
            blueBar = ImageIO.read(new File("src/resources/imag/Level Interface/PvPProgress/PlantsBar.png"));
            redBar = ImageIO.read(new File("src/resources/imag/Level Interface/PvPProgress/ZombiesBar.png"));
        } catch (IOException _) {
        }
    }

    /**
     * Updates the buffer position of the progress bar based on the number of zombies 
     * and plants defeated. The buffer dynamically moves to reflect the balance between 
     * the two scores.
     *
     * @param zombiesDefeated The number of zombies defeated.
     * @param plantsDefeated  The number of plants defeated.
     */
    public void updateBuffer(int zombiesDefeated, int plantsDefeated) {
        int total = zombiesDefeated + plantsDefeated;
        if (total == 0) {
            bufferWidth = width / 2;
        } else {
            double percentage = (double) zombiesDefeated / total;
            double toMove = 126 + 261 * width * percentage / 511;
            if (toMove > bufferWidth && (double) (387 * width) / 511 > bufferWidth) {
                int factor = width * 8 / 511;
                if (bufferWidth + factor > toMove) {
                    int missing = (int) toMove - bufferWidth;
                    bufferWidth += missing;
                } else {
                    bufferWidth += factor;
                }
            } else if (toMove < bufferWidth && (double) (126 * width) / 511 < bufferWidth) {
                int factor = width * 8 / 511;
                if (bufferWidth - factor < toMove) {
                    int missing = bufferWidth - (int) toMove;
                    bufferWidth -= missing;
                } else {
                    bufferWidth -= factor;
                }
            }
        }
    }

    /**
     * Returns the current buffer width between the two progress bars.
     *
     * @return The current buffer position as an integer.
     */
    public int getBuffer() {
        return bufferWidth;
    }

    /**
     * Returns a subimage of the blue (plants) progress bar up to the current buffer position.
     *
     * @return A BufferedImage representing the blue progress bar portion.
     */
    public BufferedImage getBlueBar() {
        return blueBar.getSubimage(0, 0, bufferWidth, blueBar.getHeight());
    }

    /**
     * Returns a subimage of the red (zombies) progress bar from the current buffer position 
     * to the end of the bar.
     *
     * @return A BufferedImage representing the red progress bar portion.
     */
    public BufferedImage getRedBar() {
        return redBar.getSubimage(bufferWidth, 0, redBar.getWidth() - bufferWidth, redBar.getHeight());
    }

    /**
     * Returns the icon image of the fists displayed at the center of the progress bar.
     *
     * @return The Image of the fists icon.
     */
    public Image getFists() {
        return fists;
    }
}
