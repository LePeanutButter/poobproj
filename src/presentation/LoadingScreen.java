package presentation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class LoadingScreen extends JPanel {
    private final Image backgroundImage;
    private final Image gameLogo;
    private LoadingButton loadingButton;
    private Sound backgroundSong;

    public LoadingScreen(Dimension dimension) {
        setPreferredSize(dimension);
        setLayout(null);
        backgroundImage = new ImageIcon("src/resources/imag/Menu/LoadingScreen.png").getImage();
        gameLogo = new ImageIcon("src/resources/imag/Menu/PVZ_LOGO.png").getImage();
        SwingUtilities.invokeLater(() -> {
            loadingButton = new LoadingButton(getWidth(), getHeight());
            add(loadingButton);
            loadingButton.setBounds(loadingButton.dirtPosition[0], loadingButton.dirtPosition[1], loadingButton.dirtSize[0],loadingButton.dirtSize[1]);
            prepareActions();
            startAudio();
            startAnimation();
        });
    }

    public void startAudio() {
        backgroundSong = new Sound(true, "src/resources/sound/soundtracks/loadingScreen.wav");
    }

    public void prepareActions() {
        loadingButton.addActionListener(_ -> {
            POOBVsZombiesGUI.switchToPanel(new MainMenu(POOBVsZombiesGUI.dimensions));
            new Sound(false, "src/resources/sound/soundeffects/SFX-buttonclick.wav");
            backgroundSong.stopClip();
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        //backgroundImage
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

        //gameLogo
        int logoWidth = gameLogo.getWidth(null);
        int logoHeight = gameLogo.getHeight(null);
        int newWidth = (int) (getWidth() * 0.6);
        int newHeight = logoHeight * newWidth / logoWidth;
        int xPosition = (getWidth() - newWidth) / 2;
        int yPosition = (int) (getHeight() * 0.04);
        g2d.drawImage(gameLogo, xPosition, yPosition, newWidth, newHeight,null);

        //dirt
        Image dirt = loadingButton.dirt;
        int[] dirtPosition = loadingButton.dirtPosition;
        g2d.drawImage(dirt, dirtPosition[0], dirtPosition[1], dirt.getWidth(null), dirt.getHeight(null), null);

        //grass
        int loadingBarProgress = loadingButton.loadingBarProgress;
        int[] grassSize = loadingButton.grassSize;
        int[] grassPosition = loadingButton.grassPosition;
        int bufferWidth = grassSize[0] * loadingBarProgress / 100;
        if (loadingBarProgress > 0 && loadingBarProgress < 100) {
            g2d.drawImage(loadingButton.getGrass(), grassPosition[0], grassPosition[1], bufferWidth, grassSize[1], null);
        } else if (loadingBarProgress >= 100) {
            g2d.drawImage(loadingButton.grass, grassPosition[0], grassPosition[1], grassSize[0], grassSize[1], null);
        }
    }

    public void startAnimation() {
        Timer timer = new Timer(100 / 3, e -> {
            if (loadingButton.loadingBarProgress < 100) {
                loadingButton.updateProgress();
                repaint();
            } else {
                ((Timer) e.getSource()).stop();
                loadingButton.setText("Click to play!");
                loadingButton.setEnabled(true);
                revalidate();
                repaint();
            }
        });
        timer.start();
    }

    private static class LoadingButton extends JButton {
        private Image dirt;
        private BufferedImage grass;
        private BufferedImage sodRoll;
        private final int[] dirtPosition;
        private int[] dirtSize;
        private int[] grassPosition;
        private int[] sodRollPosition;
        private int[] grassSize;
        private int[] originalGrass;
        private int[] sodRollSize;
        private int loadingBarProgress = 0;

        private LoadingButton(int width, int height) {
            setText("Loading...");
            setFont(POOBVsZombiesGUI.briannesHand);
            setForeground(new Color(212, 182, 58));
            setHorizontalTextPosition(JButton.CENTER);
            setVerticalTextPosition(JButton.CENTER);
            getDirt(width);
            getGrass(width, height);
            int xPosition = width * 679 / 1920;
            int yPosition = height * 241 / 270;
            dirtPosition = new int[]{xPosition, yPosition};
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false) ;
            setOpaque(false);
            setEnabled(false);
        }

        private void getDirt(int width) {
            Image dirtImage = new ImageIcon("src/resources/imag/Menu/LoadBar_dirt.png").getImage();
            int dirtWidth = dirtImage.getWidth(null);
            int dirtHeight = dirtImage.getHeight(null);
            int newWidth = width *  29 / 96;
            int newHeight = dirtHeight * newWidth / dirtWidth;
            dirtSize = new int[] {newWidth, newHeight};
            dirt = dirtImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        }

        private void getGrass(int width, int height) {
            try {
                grass = ImageIO.read(new File("src/resources/imag/Menu/LoadBar_grass.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int xPosition = width * 673 / 1920;
            int yPosition = height * 233 / 270;
            int grassWidth = grass.getWidth();
            int grassHeight = grass.getHeight();
            int newGrassWidth = width * 47 / 160;
            int newGrassHeight = grassHeight * newGrassWidth / grassWidth;
            originalGrass = new int[] {grassWidth, grassHeight};
            grassPosition = new int[] {xPosition, yPosition};
            grassSize = new int[] {newGrassWidth, newGrassHeight};
        }

        private BufferedImage getGrass() {
            return grass.getSubimage(0, 0, originalGrass[0] * loadingBarProgress / 100, originalGrass[1]);
        }

        private void updateProgress() {
            loadingBarProgress += 20 / 3;
        }
    }
}
