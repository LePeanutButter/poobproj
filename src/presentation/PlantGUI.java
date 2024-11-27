package presentation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class PlantGUI {
    int xPosition;
    int yPosition;
    int width;
    int height;
    List<Image> idleAnimation;
    String currentAnimation;
    int animationIndex;

    public PlantGUI(int xPosition, int yPosition, int width, int height) {
        idleAnimation = new ArrayList<>();
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        currentAnimation = "idle";
        animationIndex = 0;
    }

    protected abstract void loadAnimationFrames();

    public void changeAnimation(String animation) {
        switch (animation) {
            case ("idle"):
                currentAnimation = "idle";
                break;
            default:
                break;
        }
        animationIndex = 0;
    }

    public void updateIndex(){
        switch (currentAnimation) {
            case ("idle"):
                if (animationIndex < idleAnimation.size() - 1) {
                    ++animationIndex;
                } else {
                    animationIndex = 0;
                }
                break;
            default:
                break;
        }
    }
    
    public abstract Image getImage();

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public int getWidth() {return width;}

    public int getHeight() {return height;}
}
