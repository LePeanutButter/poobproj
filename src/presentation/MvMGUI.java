package presentation;

import domain.Level;
import domain.MvM;
import java.awt.*;
import javax.swing.*;

/**
 * The MvMGUI class is responsible for the graphical user interface (GUI) for the "MvM" game mode.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12 , 2024
 */
public class MvMGUI extends LevelGUI {
    
    /**
     * Constructs a new MvMGUI instance with the specified level and lawn type.
     *
     * @param level The Level object representing the current level.
     * @param lawn  A string representing the lawn configuration for the game.
     */
    public MvMGUI(Level level, String lawn) {
        super(level, lawn);
        gameMode = "MvM";
        SwingUtilities.invokeLater(this::prepareMvMElements);
    }

    /**
     * Constructor for MvMGUI when only the Level object is provided.
     * It sets the game mode to "MvM" and enables the timer for the level.
     *
     * @param level The Level object representing the current level.
     */
    public MvMGUI(Level level) {
        super(level);
        gameMode = "MvM";
        isTimerVisible = true;
        SwingUtilities.invokeLater(this::prepareMvMElements);
    }

    /*
     * Prepares the elements specific to the MvM game mode, such as background music, progress bar, and timer.
     */
    private void prepareMvMElements() {
        backgroundMusic = new Sound("src/resources/sound/soundtracks/07-Battle-3.wav");
        progressBar = new MvMProgressBar(getWidth(), getHeight());
        prepareTimerBank();
        readySetPlant();
    }

    /**
     * This method is intentionally left empty as the seed chooser preparation is not required in this game mode.
     */
    @Override
    public void prepareSeedChooser() {}

    /**
     * This method is intentionally left empty as button handling is not required in this game mode.
     */
    @Override
    protected void handleButtons() {}

    /**
     * This method is intentionally left empty as button enabling is not required in this game mode.
     */
    @Override
    protected void enableButtons() {}

    /**
     * This method is intentionally left empty as no additional action is needed when the game starts in this mode.
     */
    @Override
    protected void letsRockAction() {}

    /**
     * Refreshes the subclasses, updating the game state by checking the remaining time and handling game logic such as 
     * updating the machines and collecting sun points.
     */
    @Override
    protected void refreshSubclasses() {
        double time = level.updateTime();
        if (time <= 0) {
            pauseTimer();
            backgroundMusic.stopClip();
            mainMenu();
            removeActiveMatch();
        }
        ((MvM) level).updateMachines();
        collectSun();
    }

    /*
     * Collects the sun points by triggering the click action on Sun components present in the game.
     */
    private void collectSun() {
        Component[] components = layeredPane.getComponents();
        for (Component c : components) {
            String economy = c.getClass().getSimpleName();
            if (economy.equals("Sun")) {
                ((Sun) c).doClick();
            }
        }
    }

    /**
     * Paints the progress bar for the game.
     *
     * @param g2d The Graphics2D object used for rendering.
     */
    @Override
    public void paintProgressBar(Graphics2D g2d) {}

    /**
     * Paints the components of the MvMGUI.
     *
     * @param g The Graphics object used for rendering.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
    }
}
