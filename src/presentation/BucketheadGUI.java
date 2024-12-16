package presentation;

import java.awt.*;
import javax.swing.*;

/**
 * The BucketheadGUI class represents a Buckethead zombie in the game, 
 * extending the ZombieGUI class
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12 , 2024
 */
public class BucketheadGUI extends ZombieGUI {
    private boolean lostBucket = false;
    private Image[] lostBucketWalkAnimation;
    private Image[] lostBucketEatAnimation;

    /**
     * Constructs a new BucketheadGUI object with the specified position and screen dimensions.
     * 
     * @param xPosition The initial X-coordinate of the zombie.
     * @param yPosition The initial Y-coordinate of the zombie.
     * @param width     The screen width used to scale the zombie's size.
     * @param height    The screen height used to scale the zombie's size.
     */
    public BucketheadGUI(int xPosition, int yPosition, int width, int height) {
        super(xPosition, yPosition, width, height);
    }

    /**
     * Loads animation frames for all states of the Buckethead zombie.
     */
    @Override
    protected void loadAnimationFrames() {
        this.walkingAnimation = new Image[93];
        int countW = 0;
        this.lostBucketWalkAnimation = new Image[93];
        for (int i = 46; i <= 138; i++) {
            String imageName = String.format("src/resources/imag/Zombies/Buckethead/Zombie%04d.png", i);
            walkingAnimation[countW] = new ImageIcon(imageName).getImage();
            String altImageName = String.format("src/resources/imag/Zombies/Basic/Zombie%04d.png", i);
            lostBucketWalkAnimation[countW] = new ImageIcon(altImageName).getImage();
            countW++;
        }
        this.eatingAnimation = new Image[40];
        this.lostBucketEatAnimation = new Image[40];
        int countE = 0;
        for (int i = 139; i <= 178; i++) {
            String imageName = String.format("src/resources/imag/Zombies/Buckethead/Zombie%04d.png", i);
            eatingAnimation[countE] = new ImageIcon(imageName).getImage();
            String altImageName = String.format("src/resources/imag/Zombies/Basic/Zombie%04d.png", i);
            lostBucketEatAnimation[countE] = new ImageIcon(altImageName).getImage();
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
     * Sets the lostBucket flag to true, indicating the zombie has lost its bucket.
     */
    public void lostBucket() {
        if (!lostBucket) {
            lostBucket = true;
        }
    }

    /**
     * Returns the current image representing the zombie based on its animation state and 
     * whether it has lost its bucket.
     * 
     * @return The Image object representing the zombie's current animation frame.
     */
    @Override
    public Image getImage() {
        Image image;
        switch (currentAnimation) {
            case "walk" -> {
                if (lostBucket) {
                    image = lostBucketWalkAnimation[animationIndex];
                } else {
                    image = walkingAnimation[animationIndex];
                }
            }
            case "eating" -> {
                if (lostBucket) {
                    image = lostBucketEatAnimation[animationIndex];
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
