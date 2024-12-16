package presentation;

import java.awt.*;
import javax.swing.*;

/**
 * The ECIZombieGUI class represents a unique zombie type named "ECIZombie" in the game, 
 * extending the ZombieGUI class.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12 , 2024
 */
public class ECIZombieGUI extends ZombieGUI {

    /**
     * Constructs a new ECIZombieGUI object with the specified position and screen dimensions.
     * 
     * @param xPosition The initial X-coordinate of the zombie.
     * @param yPosition The initial Y-coordinate of the zombie.
     * @param width     The screen width used to scale the zombie's size.
     * @param height    The screen height used to scale the zombie's size.
     */
    public ECIZombieGUI(int xPosition, int yPosition, int width, int height) {
        super(xPosition, yPosition, width, height);
    }

    /**
     * Loads animation frames for the ECIZombie's various states.
     */
    @Override
    protected void loadAnimationFrames() {
        this.walkingAnimation = new Image[118];
        int countW = 0;
        for (int i = 0; i <= 117; i++) {
            String imageName = String.format("src/resources/imag/Zombies/ECIZombie/ECIZombie%04d.png", i);
            walkingAnimation[countW] = new ImageIcon(imageName).getImage();
            countW++;
        }
        this.eatingAnimation = new Image[48];
        int countE = 0;
        for (int i = 118; i <= 165; i++) {
            String imageName = String.format("src/resources/imag/Zombies/ECIZombie/ECIZombie%04d.png", i);
            eatingAnimation[countE] = new ImageIcon(imageName).getImage();
            countE++;
        }
        this.deathAnimation = new Image[33];
        int countD = 0;
        for (int i = 218; i <= 250; i++) {
            String imageName = String.format("src/resources/imag/Zombies/Basic/Zombie%04d.png", i);
            deathAnimation[countD] = new ImageIcon(imageName).getImage();
            countD++;
        }
        this.mowerAnimation = new Image[8];
        int countM = 0;
        for (int i = 1; i <= 8; i++) {
            String imageName = String.format("src/resources/imag/Zombies/LawnMoweredZombie/LawnMoweredZombie%04d.png", i);
            mowerAnimation[countM] = new ImageIcon(imageName).getImage();
            countM++;
        }
    }

    /**
     * Returns the current image representing the ECIZombie's animation state. The animation 
     * frame is selected based on the currentAnimation and animationIndex.
     * 
     * @return The Image object representing the current animation frame, 
     *         or null if the animation state is invalid.
     */
    @Override
    public Image getImage() {
        return switch (currentAnimation) {
            case "walk" -> walkingAnimation[animationIndex];
            case "eating" -> eatingAnimation[animationIndex];
            case "dying" -> deathAnimation[animationIndex];
            case "mower" -> mowerAnimation[animationIndex];
            default -> null;
        };
    }
}
