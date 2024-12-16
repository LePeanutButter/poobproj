package presentation;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * The PvMProgressBar class extends ProgressBar and provides 
 * a graphical representation of a progress bar for a Player vs. Machine (PvM). 
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12 , 2024
 */
public class PvMProgressBar extends ProgressBar {
    private final Image progressBar;
    private BufferedImage barProgress;
    private int scoreXPosition;
    private int scoreYPosition;

    /**
     * Constructs a PvMProgressBar object with the specified width and height.
     * Initializes the progress bar's dimensions, position, and associated images.
     * 
     * @param width  The width of the screen.
     * @param height The height of the screen.
     */
    public PvMProgressBar(int width, int height) {
        super(width * 294 / 1280, height * 45 / 720);
        Image progressBarImage = new ImageIcon("src/resources/imag/Level Interface/FlagMeterEmpty.png").getImage();
        this.xPosition = width * 444 / 1280;
        this.yPosition = height * 8 / 720;
        progressBar = progressBarImage.getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH);
        try {
            barProgress = ImageIO.read(new File("src/resources/imag/Level Interface/FlagMeterFull.png"));
        } catch (IOException _) {
        }
        getScorePositionPvM(height);
    }

    /**
     * Returns a subimage representing the current progress of the progress bar.
     * 
     * @param progress The progress percentage (0-100).
     * @return A BufferedImage representing the filled portion of the progress bar, 
     *         or null if the progress is out of bounds.
     */
    public BufferedImage getProgress(int progress) {
        BufferedImage waveSubimage = null;
        if (progress > 0 && progress < 100) {
            waveSubimage = barProgress.getSubimage(0, 0, barProgress.getWidth() * progress / 100, barProgress.getHeight());
        }
        return waveSubimage;
    }

    /*
     * Calculates the position for displaying the score text based on the progress bar's position 
     * and dimensions. This method ensures the score is centered above the progress bar.
     * 
     * @param height The height of the screen.
     */
    private void getScorePositionPvM(int height) {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        float customSize = (float) (height * 26) / 720;
        Font customFont = POOBVsZombiesGUI.cafeteriaBold.deriveFont(customSize);
        FontMetrics fm = g2d.getFontMetrics(customFont);
        int progressBarMiddle = xPosition + (this.width / 2);
        int textHeight = fm.getHeight();
        int progressBarHeightMiddle = yPosition + (this.height / 2);
        int textHeightMiddle = textHeight / 2;
        scoreYPosition = textHeight + progressBarHeightMiddle - textHeightMiddle - 7 * height / 720;
        String score = "Score: 1000000";
        int textMiddle = g2d.getFontMetrics().stringWidth(score) / 2;
        scoreXPosition = progressBarMiddle - textMiddle;
    }


    /**
     * Returns the background image of the progress bar.
     * 
     * @return The Image of the progress bar.
     */
    public Image getProgressBar() {
        return progressBar;
    }

    /**
     * Returns the x-coordinate for displaying the score text.
     * 
     * @return The x-coordinate of the score position.
     */
    public int getScoreXPosition() {
        return scoreXPosition;
    }

    /**
     * Returns the y-coordinate for displaying the score text.
     * 
     * @return The y-coordinate of the score position.
     */
    public int getScoreYPosition() {
        return scoreYPosition;
    }
}
