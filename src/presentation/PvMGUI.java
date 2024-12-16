package presentation;

import domain.Level;
import domain.PvM;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * The PvMGUI class represents the graphical user interface for the 
 * Player vs. Machine (PvM).
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12 , 2024
 */
public class PvMGUI extends LevelGUI {

    /**
     * Constructs a new PvMGUI instance with the specified level and lawn type.
     *
     * @param level the Level object associated with the PvM game mode.
     * @param lawn  the lawn type to be displayed.
     */
    public PvMGUI(Level level, String lawn) {
        super(level, lawn);
        gameMode = "PvM";
        SwingUtilities.invokeLater(() -> {
            pickYourSeeds = new Sound("src/resources/sound/soundtracks/PickYourSeeds.wav");
            pickYourSeeds.loop();
            preparePvMElements();
        });
    }

    /**
     * Constructs a new PvMGUI instance with the specified level.
     *
     * @param level the Level object associated with the PvM game mode.
     */
    public PvMGUI(Level level) {
        super(level);
        gameMode = "PvM";
        isTimerVisible = true;
        isProgressBarVisible = true;
        SwingUtilities.invokeLater(() -> {
            preparePvMElements();
            handleStart();
            menu.setEnabled(true);
            menu.doClick();
        });
    }

    /*
     * Prepares the elements specific to the PvM mode.
     */
    private void preparePvMElements() {
        progressBar = new PvMProgressBar(getWidth(), getHeight());
        backgroundMusic = new Sound("src/resources/sound/soundtracks/Graze-the-Roof.wav");
        prepareTimerBank();
        SwingUtilities.invokeLater(this::refreshPaint);
    }

    /**
     * Stops the "Pick Your Seeds" soundtrack and initiates the "Ready Set Plant" phase.
     */
    @Override
    protected void letsRockAction() {
        pickYourSeeds.stopClip();
        readySetPlant();
    }

    /**
     * Handles the enabling or disabling of game buttons.
     */
    @Override
    public void handleButtons() {
    }

    /**
     * Enables the shovel and plant slot buttons for player interactions.
     */
    @Override
    public void enableButtons() {
        enableShovel();
        enablePlantSlot();
    }

    /**
     * Refreshes and updates the PvM-specific elements during gameplay.
     */
    @Override
    protected void refreshSubclasses() {
        ((PvM) level).updateProgress();
        double time = level.updateTime();
        if (time <= 0) {
            pauseTimer();
            handleButtons();
            disablePlantSlot();
            YouWon youWon = new YouWon(getWidth(), getHeight());
            SwingUtilities.invokeLater(() ->
                    youWon.ok.addActionListener(_ -> {
                        mainMenu();
                        removeActiveMatch();
                    }));
            layeredPane.add(youWon, JLayeredPane.PALETTE_LAYER);
        }
        ((PvM) level).zombieSpawn();
        double originalTime = level.getOriginalTime();
        if (originalTime - time >= 20000 && originalTime - time < 20033) {
            Sound awooga = new Sound("src/resources/sound/soundeffects/Voices-awooga.wav");
            awooga.startClip();
        }
    }

    /**
     * Paints the graphical components for the PvM GUI.
     *
     * @param g the Graphics object used for painting.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }

    /**
     * Paints the progress bar specific to the PvM mode, including wave progress and scores.
     *
     * @param g2d the Graphics2D object used for painting.
     */
    @Override
    public void paintProgressBar(Graphics2D g2d) {
        if (isProgressBarVisible) {
            // Progress bar
            g2d.drawImage(((PvMProgressBar) progressBar).getProgressBar(), progressBar.getXPosition(), progressBar.getYPosition(), progressBar.getWidth(), progressBar.getHeight(), null);
            // Wave Progress Bar
            int waveProgress = ((PvM) level).getWaveProgress();
            int bufferWidth = progressBar.getWidth() * waveProgress / 100;
            BufferedImage barProgress = ((PvMProgressBar) progressBar).getProgress(waveProgress);
            if (barProgress != null && waveProgress > 0) {
                g2d.drawImage(barProgress, progressBar.getXPosition(), progressBar.getYPosition(), bufferWidth, progressBar.getHeight(), null);
            } else if (waveProgress > 100) {
                g2d.drawImage(((PvMProgressBar) progressBar).getProgressBar(), progressBar.getXPosition(), progressBar.getYPosition(), progressBar.getWidth(), progressBar.getHeight(), null);
            }
            // Score
            String score = "Score: " + ((PvM) level).getScore();
            g2d.drawString(score, ((PvMProgressBar) progressBar).getScoreXPosition(), ((PvMProgressBar) progressBar).getScoreYPosition());
        }
    }

    /**
     * The YouWon class represents the graphical panel displayed when the player wins the game.
     */
    private static class YouWon extends JPanel {
        private final JLayeredPane wonPane;
        private JButton ok;

        public YouWon(int width, int height) {
            wonPane = new JLayeredPane();
            wonPane.setLayout(null);
            int panelWidth = width * 699 / 1280;
            int panelHeight = height * 517 / 720;
            int xPosition = width * 291 / 1280;
            int yPosition = height * 102 / 720;
            SwingUtilities.invokeLater(() -> {
                setLayout(new BorderLayout());
                add(wonPane, BorderLayout.CENTER);
                setOpaque(false);
                setBounds(xPosition, yPosition, panelWidth, panelHeight);
                prepareBackground();
                setJButton();
                setJLabel(height);
            });
        }

        private void prepareBackground() {
            Image name = new ImageIcon("src/resources/imag/Level Interface/youWon.png").getImage();
            ImageIcon nameScaled = new ImageIcon(name.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
            JLabel nameLabel = new JLabel();
            nameLabel.setBounds(0, 0, getWidth(), getHeight());
            nameLabel.setIcon(nameScaled);
            wonPane.add(nameLabel, JLayeredPane.DEFAULT_LAYER);
        }

        private void setJLabel(int height) {
            JLabel scoreLabel = new JLabel(String.valueOf(((PvM) level).getScore()), SwingConstants.CENTER);
            int scoreWidth = getWidth() * 248 / 699;
            int scoreHeight = getHeight() * 58 / 517;
            scoreLabel.setBounds(getWidth() * 225 / 699, getHeight() * 271 / 517, scoreWidth, scoreHeight);
            float customSize = (float) (height * 58) / 720;
            scoreLabel.setFont(POOBVsZombiesGUI.briannesHand.deriveFont(customSize));
            scoreLabel.setForeground(new Color(219, 188, 103));
            wonPane.add(scoreLabel, JLayeredPane.PALETTE_LAYER);
        }

        private void setJButton() {
            Image okImage = new ImageIcon("src/resources/imag/Menu/OkButton.png").getImage();
            ok = new JButton();
            int newWidth = getWidth() * 398 / 699;
            int newHeight = getHeight() * 88 / 517;
            ImageIcon okScaled = new ImageIcon(okImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
            ok.setBounds(getWidth() * 150 / 699, getHeight() * 401 / 517, okScaled.getIconWidth(), okScaled.getIconHeight());
            ok.setIcon(okScaled);
            ok.setFocusPainted(false);
            ok.setBorderPainted(false);
            ok.setContentAreaFilled(false);
            wonPane.add(ok, JLayeredPane.PALETTE_LAYER);
        }
    }
}
    