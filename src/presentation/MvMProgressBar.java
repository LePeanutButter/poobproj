package presentation;

/**
 * The MvMProgressBar class extends ProgressBar and provides 
 * a graphical representation of a progress bar for a Machine vs. Machine (MvM). 
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12 , 2024
 */
public class MvMProgressBar extends ProgressBar {

    /**
     * Constructor for the MvMProgressBar class.
     * It calculates the size and position of the progress bar based on the given window dimensions.
     *
     * @param width The width of the window or container where the progress bar will be displayed.
     * @param height The height of the window or container where the progress bar will be displayed.
     */
    public MvMProgressBar(int width, int height) {
        super(width * 294 / 1280, height * 2 / 720);
        this.xPosition = width * 444 / 1280;
        this.yPosition = height * 8 / 720;
    }
}
