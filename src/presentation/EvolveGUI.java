package presentation;

import java.awt.*;
import javax.swing.*;

/**
 * A class that represents a specific type of plant in the interface.
 * This class extends PlantGUI and provides specific animations and behavior for an Evolve plant,
 * including loading its animation frames and returning the current frame.
 * The animations are loaded from a sequence of images stored in a predefined directory.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 13, 2024
 */
public class EvolveGUI extends PlantGUI {
    private Image[] firstEvolution;
    private Image[] secondEvolution;

    /**
     * Constructs a PeashooterGUI object with the specified position and size.
     * This constructor initializes the plant's position and dimensions on the screen.
     *
     * @param xPosition The x-coordinate of the plant's position.
     * @param yPosition The y-coordinate of the plant's position.
     * @param width     The width of the screen.
     * @param height    The height of the screen.
     */
    public EvolveGUI(int xPosition, int yPosition, int width, int height) {
        super(xPosition, yPosition, width, height);
    }

    /**
     * Loads the animation frames for the "idle" state of the plant.
     */
    @Override
    protected void loadAnimationFrames() {
        this.idleAnimation = new Image[25];
        this.firstEvolution = new Image[25];
        this.secondEvolution = new Image[25];
        int count = 0;
        for (int i = 80; i <= 104; i++) {
            String imageName = String.format("src/resources/imag/Plants/Peashooter/PeaShooterSingle%04d.png", i);
            String firstEvolutionName = String.format("src/resources/imag/Plants/Evolve/Repeater/PeaShooter%04d.png", i);
            String secondEvolutionName = String.format("src/resources/imag/Plants/Evolve/GatlingPea/GatlingPea%04d.png", i - 79);
            idleAnimation[count] = new ImageIcon(imageName).getImage();
            firstEvolution[count] = new ImageIcon(firstEvolutionName).getImage();
            secondEvolution[count] = new ImageIcon(secondEvolutionName).getImage();
            count++;
        }
    }

    /**
     * Updates the current animation of the plant.
     *
     */
    public void changeAnimation() {
        if (currentAnimation.equals("idle")) {
            currentAnimation = "firstEvolution";
        } else if (currentAnimation.equals("firstEvolution")) {
            currentAnimation = "secondEvolution";
        }
    }

    /**
     * Returns the current frame of the plant's animation.
     *
     * @return The current animation frame as an Image, or null if no animation is found.
     */
    @Override
    public Image getImage() {
        Image image = null;
        switch (currentAnimation) {
            case "idle" -> image = idleAnimation[animationIndex];
            case "firstEvolution" -> image = firstEvolution[animationIndex];
            case "secondEvolution" -> image = secondEvolution[animationIndex];
            default -> {
            }
        }
        return image;
    }
}
