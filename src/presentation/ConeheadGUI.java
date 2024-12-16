package presentation;

import java.awt.*;
import javax.swing.*;

/**
 * The ConeheadGUI class represents a Conehead zombie in the game, 
 * extending the ZombieGUI class.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12 , 2024
 */
public class ConeheadGUI extends ZombieGUI {
    private boolean lostCone = false;
    private Image[] lostConeWalkAnimation;
    private Image[] lostConeEatAnimation;

    /**
     * Constructs a new ConeheadGUI object with the specified position and screen dimensions.
     * 
     * @param xPosition The initial X-coordinate of the zombie.
     * @param yPosition The initial Y-coordinate of the zombie.
     * @param width     The screen width used to scale the zombie's size.
     * @param height    The screen height used to scale the zombie's size.
     */
    public ConeheadGUI(int xPosition, int yPosition, int width, int height) {
        super(xPosition, yPosition, width, height);
    }

    /**
     * Loads animation frames for all states of the Conehead zombie.
     */
    @Override
    protected void loadAnimationFrames() {
        this.walkingAnimation = new Image[93];
        this.lostConeWalkAnimation = new Image[93];
        int countW = 0;
        for (int i = 46; i <= 138; i++) {
            String imageName = String.format("src/resources/imag/Zombies/Conehead/Zombie%04d.png", i);
            walkingAnimation[countW] = new ImageIcon(imageName).getImage();
            String altImageName = String.format("src/resources/imag/Zombies/Basic/Zombie%04d.png", i);
            lostConeWalkAnimation[countW] = new ImageIcon(altImageName).getImage();
            countW++;
        }
        this.eatingAnimation = new Image[40];
        this.lostConeEatAnimation = new Image[40];
        int countE = 0;
        for (int i = 139; i <= 178; i++) {
            String imageName = String.format("src/resources/imag/Zombies/Conehead/Zombie%04d.png", i);
            eatingAnimation[countE] = new ImageIcon(imageName).getImage();
            String altImageName = String.format("src/resources/imag/Zombies/Basic/Zombie%04d.png", i);
            lostConeEatAnimation[countE] = new ImageIcon(altImageName).getImage();
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
     * Sets the lostCone flag to true, indicating the zombie has lost its cone.
     * This affects the animations used for walking and eating.
     */
    public void lostCone() {
        if (!lostCone) {
            lostCone = true;
        }
    }

    /**
     * Returns the current image representing the zombie based on its animation state and 
     * whether it has lost its cone.
     * 
     * @return The Image object representing the zombie's current animation frame.
     */
    @Override
    public Image getImage() {
        Image image;
        switch (currentAnimation) {
            case "walk" -> {
                if (lostCone) {
                    image = lostConeWalkAnimation[animationIndex];
                } else {
                    image = walkingAnimation[animationIndex];
                }
            }
            case "eating" -> {
                if (lostCone) {
                    image = lostConeEatAnimation[animationIndex];
                } else {
                    image = eatingAnimation[animationIndex];
                }
            }
            case "dying" -> image = deathAnimation[animationIndex];
            case "mower" -> image = mowerAnimation[animationIndex];
            default -> image = null;
        }
        return image;
    }
}
