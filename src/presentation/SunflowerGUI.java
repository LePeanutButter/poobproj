package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SunflowerGUI extends PlantGUI {

    public SunflowerGUI(int row, int column, int width, int height) {
        super(row,column, width, height);
        loadAnimationFrames();
    }

    @Override
    protected void loadAnimationFrames() {
        this.idleAnimation = new ArrayList<>();
        for (int i = 5; i <= 29; i++) {
            String imageName = String.format("src/resources/imag/Plants/SunFlower/SunFlower%04d.png", i);
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
