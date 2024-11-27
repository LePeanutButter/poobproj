package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Sun extends JButton {
    private final List<ImageIcon> animation;
    private int animationIndex = 0;
    private int buttonWidth;
    private int buttonHeight;
    private final int frameHeight;
    private final int fromX;
    private int actualPosition;
    private final int toY;
    private final int sunSize;

    public Sun(int fromX, int toY, int sunSize, int width, int height) {
        this.animation = new ArrayList<>();
        this.sunSize = sunSize;
        this.frameHeight = height;
        this.fromX = fromX;
        this.toY = toY;
        loadAnimation();
        setButton(width, height);
    }

    public void loadAnimation() {
        for (int i = 1; i <= 13; i++) {
            String imageName = String.format("src/resources/imag/Level Interface/Sun/Sun%04d.png", i);
            animation.add(new ImageIcon(imageName));
        }
    }

    public void updateIndex() {
        if (animationIndex < animation.size() - 1) {
            ++animationIndex;
        } else {
            animationIndex = 0;
        }
    }

    private void setButton(int width, int height) {
        Image sunImage = animation.getFirst().getImage();
        int newWidth = width * 231 / 1280;
        int newHeight = height * 231 / 720;
        ImageIcon sunIcon = new ImageIcon(sunImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        buttonWidth = width * 128 / 1280;
        buttonHeight = height * 125 / 720;
        setIcon(sunIcon);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
    }

    public void setSky(int height) {
        actualPosition = -(height * 125 / 720);
        setBounds(fromX, actualPosition, buttonWidth, buttonHeight);
    }

    public void setSunflower(int x, int y) {
        actualPosition = y;
        setBounds(x, actualPosition, buttonWidth, buttonHeight);
    }

    public void setImage(){
        setIcon(animation.get(animationIndex));
    }

    public int getSunSize() {
        return sunSize;
    }

    public void moveSun() {
        updateIndex();
        setImage();
        if (actualPosition < toY) {
            actualPosition += frameHeight * 4 / 720;
            setBounds(fromX, actualPosition, buttonWidth, buttonHeight);
        }
        setEnabled(true);
    }
}
