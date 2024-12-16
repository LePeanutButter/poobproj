package presentation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The LoadingScreen class represents the loading screen panel of the game.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12 , 2024
 */
public class LoadingScreen extends JPanel {
    private final Image backgroundImage;
    private final Image gameLogo;
    private LoadingButton loadingButton;
    private Sound backgroundSong;

    /**
     * Constructs a LoadingScreen object, initializes the background and logo images,
     * and sets up the loading button and actions.
     */
    public LoadingScreen() {
        backgroundImage = new ImageIcon("src/resources/imag/Menu/LoadingScreen.png").getImage();
        gameLogo = new ImageIcon("src/resources/imag/Menu/PVZ_LOGO.png").getImage();
        SwingUtilities.invokeLater(() -> {
            setLayout(null);
            loadingButton = new LoadingButton(getWidth(), getHeight());
            add(loadingButton);
            loadingButton.setBounds(loadingButton.dirtPosition[0], loadingButton.dirtPosition[1], loadingButton.dirtSize[0], loadingButton.dirtSize[1]);
            prepareActions();
            startAudio();
        });
    }

    /**
     * Starts the background music for the loading screen.
     */
    public void startAudio() {
        backgroundSong = new Sound("src/resources/sound/soundtracks/loadingScreen.wav");
        backgroundSong.loop();
    }

    /**
     * Prepares actions to be performed when the loading button is clicked.
     * When clicked, it transitions to the main menu.
     */
    public void prepareActions() {
        loadingButton.addActionListener(_ -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.setSize(POOBVsZombiesGUI.dimensions);
            SwingUtilities.invokeLater(() -> {
                mainMenu.prepareElements();
                POOBVsZombiesGUI.switchToPanel(mainMenu);
                Sound click = new Sound("src/resources/sound/soundeffects/SFX-buttonclick.wav");
                click.startClip();
            });
            backgroundSong.stopClip();
        });
    }

    /**
     * Starts the animation of the loading bar.
     */
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

    /**
     * Paints the loading screen, including the background image, game logo,
     * loading button with dirt and grass images, and progress bar.
     *
     * @param g the Graphics object used to paint the screen
     */
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
        g2d.drawImage(gameLogo, xPosition, yPosition, newWidth, newHeight, null);

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

    /*
     * A helper class that represents the loading button which includes a dirt and grass image
     * with a progress bar that animates the loading process.
     */
    private static class LoadingButton extends JButton {
        private Image dirt;
        private BufferedImage grass;
        private final int[] dirtPosition;
        private int[] dirtSize;
        private int[] grassPosition;
        private int[] grassSize;
        private int[] originalGrass;
        private int loadingBarProgress = 0;

        public LoadingButton(int width, int height) {
            float textSize = (float) (width * 24) / 1280;
            getDirt(width);
            getGrass(width, height);
            int xPosition = width * 679 / 1920;
            int yPosition = height * 241 / 270;
            dirtPosition = new int[]{xPosition, yPosition};
            SwingUtilities.invokeLater(() -> {
                setText("Loading...");
                setFont(POOBVsZombiesGUI.briannesHand.deriveFont(textSize));
                setForeground(new Color(212, 182, 58));
                setHorizontalTextPosition(JButton.CENTER);
                setVerticalTextPosition(JButton.CENTER);
                setFocusPainted(false);
                setBorderPainted(false);
                setContentAreaFilled(false);
                setOpaque(false);
                setEnabled(false);
            });
        }

        private void getDirt(int width) {
            Image dirtImage = new ImageIcon("src/resources/imag/Menu/LoadBar_dirt.png").getImage();
            int dirtWidth = dirtImage.getWidth(null);
            int dirtHeight = dirtImage.getHeight(null);
            int newWidth = width * 29 / 96;
            int newHeight = dirtHeight * newWidth / dirtWidth;
            dirtSize = new int[]{newWidth, newHeight};
            dirt = dirtImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        }

        private void getGrass(int width, int height) {
            try {
                grass = ImageIO.read(new File("src/resources/imag/Menu/LoadBar_grass.png"));
            } catch (IOException _) {
            }
            int xPosition = width * 673 / 1920;
            int yPosition = height * 233 / 270;
            int grassWidth = grass.getWidth();
            int grassHeight = grass.getHeight();
            int newGrassWidth = width * 47 / 160;
            int newGrassHeight = grassHeight * newGrassWidth / grassWidth;
            originalGrass = new int[]{grassWidth, grassHeight};
            grassPosition = new int[]{xPosition, yPosition};
            grassSize = new int[]{newGrassWidth, newGrassHeight};
        }

        private BufferedImage getGrass() {
            return grass.getSubimage(0, 0, originalGrass[0] * loadingBarProgress / 100, originalGrass[1]);
        }

        private void updateProgress() {
            loadingBarProgress += 20 / 3;
        }
    }
}
