package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WallNutGUI extends PlantGUI {
    public WallNutGUI(int row, int column, int width, int height) {
        super(row,column, width, height);
        loadAnimationFrames();
    }

    @Override
    protected void loadAnimationFrames() {
        this.idleAnimation = new ArrayList<>();
        for (int i = 1; i <= 17; i++) {
            String imageName = String.format("src/resources/imag/Plants/WallNut/Wallnut%04d.png", i);
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
