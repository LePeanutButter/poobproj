package presentation;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class PeashooterGUI extends PlantGUI {

    public PeashooterGUI(int row, int column, int width, int height) {
        super(row,column, width, height);
        loadAnimationFrames();
    }

    @Override
    protected void loadAnimationFrames() {
        this.idleAnimation = new ArrayList<>();
        for (int i = 80; i <= 104; i++) {
            String imageName = String.format("src/resources/imag/Plants/Peashooter/PeaShooterSingle%04d.png", i);
            idleAnimation.add(new ImageIcon(imageName).getImage());
        }
    }

    @Override
    public Image getImage(){
        Image image = null;
        if (currentAnimation.equals("idle")){
            image = idleAnimation.get(animationIndex);
        }
        return image;
    }
}