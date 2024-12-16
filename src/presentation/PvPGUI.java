package presentation;

import domain.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The PvPGUI class extends LevelGUI and is responsible for managing the graphical 
 * user interface (GUI) for the Player vs Player (PvP) mode of the game.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 15 , 2024
 */
public class PvPGUI extends LevelGUI {
    private JPanel zombieSlot;
    private int letsRock = 0;
    private JButton start;
    private Image brainBank;
    private double brainTimer = 0;
    
    /**
     * Constructor that initializes the PvP GUI for the game, setting up the game mode and necessary graphical elements such as the zombie selector and background music.
     * @param level The level of the game.
     * @param lawn The type of lawn (day or night) for the game.
     */
    public PvPGUI(Level level, String lawn) {
        super(level, lawn);
        gameMode = "PvP";
        SwingUtilities.invokeLater(() -> {
            prepareZombieChooser();
            prepareStart();
            preparePvPElements();
            pickYourSeeds = new Sound("src/resources/sound/soundtracks/zombieChooser.wav");
            pickYourSeeds.loop();
        });
    }

    /**
     * Alternate constructor that initializes the PvP GUI but with a different configuration depending on whether zombies are playing or not.
     * @param level The level of the game.
     */
    public PvPGUI(Level level) {
        super(level);
        gameMode = "PvP";
        isTimerVisible = true;
        isProgressBarVisible = true;
        SwingUtilities.invokeLater(() -> {
            preparePvPElements();
            prepareStart();
            handleStart();
            if (!((PvP) level).getZombiesPlaying()) {
                start.setEnabled(true);
                start.setVisible(true);
            }
            menu.setEnabled(true);
            menu.doClick();
        });
    }

    /**
     * Sets up the essential elements for the PvP mode, including the progress bar, background music, brain bank, and zombie slot.
     */
    public void preparePvPElements() {
        progressBar = new PvPProgressBar(getWidth(), getHeight());
        backgroundMusic = new Sound("src/resources/sound/soundtracks/pvpGameMode.wav");
        ((PvPProgressBar) progressBar).updateBuffer(0, 0);
        prepareTimerBank();
        prepareZombieSlot();
        prepareBrainBank();
        getZombieSlot();
        SwingUtilities.invokeLater(this::refreshPaint);
    }

    /*
     * Returns the selected zombies by the player and prepares their display in the GUI.
     * @return A panel containing the selected zombies.
     */
    private void getZombieSlot() {
        ArrayList<String> chosenPlants = ((PvP) level).getChosenZombies();
        for (String p : chosenPlants) {
            prepareZombiePacket(p);
        }
    }

    /*
     * Sets up the start button that appears when the player is ready to begin the game.
     */
    private void prepareStart() {
        start = new JButton();
        Image onslaught = new ImageIcon("src/resources/imag/Level Interface/Start OnSlaught.png").getImage();
        int height = getHeight() * 48 / 720;
        int width = getWidth() * 261 / 1280;
        start.setBounds(getWidth() * 510 / 1280, getHeight() * 672 / 720, width, height);
        ImageIcon onslaughtIcon = new ImageIcon(onslaught.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        start.setIcon(onslaughtIcon);
        start.setDisabledIcon(onslaughtIcon);
        start.setFocusPainted(false);
        start.setBorderPainted(false);
        start.setContentAreaFilled(false);
        start.setEnabled(false);
        start.setVisible(false);
        start.addActionListener(_ -> {
            playingZombies();
            start.setEnabled(false);
            start.setVisible(false);
        });
        layeredPane.add(start, JLayeredPane.DEFAULT_LAYER);
    }

    /**
     * Prepares the zombie slot, where the zombies selected by the player will be displayed.
     */
    public void prepareZombieSlot() {
        zombieSlot = new JPanel();
        int height = getHeight() * 355 / 720;
        int width = getWidth() * 112 / 1280;
        int xPosition = getWidth() * 1167 / 1280;
        int yPosition = getHeight() * 94 / 720;
        zombieSlot.setBounds(xPosition, yPosition, width, height);
        zombieSlot.setLayout(new GridLayout(5, 1, getWidth() * 2 / 1280, getHeight() * 2 / 720));
        zombieSlot.setOpaque(false);
        layeredPane.add(zombieSlot, JLayeredPane.DEFAULT_LAYER);
    }

    /*
     * Sets up the image of the brain bank that will be displayed in the GUI.
     */
    private void prepareBrainBank() {
        Image originalBrainBank = new ImageIcon("src/resources/imag/Level Interface/brain_bank.png").getImage();
        int width = getWidth() * 184 / 1280;
        int height = getHeight() * 61 / 720;
        brainBank = originalBrainBank.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    /*
     * Adds a zombie packet to the zombie slot, displaying its corresponding image and enabling or disabling the button as necessary.
     * @param p The name of the zombie packet to be added.
     */
    private void prepareZombiePacket(String p) {
        Component[] components = zombieSlot.getComponents();
        JButton toRemove = null;
        int count = 0;
        for (Component c : components) {
            if (c.getClass().getSimpleName().equals("JButton")) {
                ++count;
                String zombie = ((JButton) c).getActionCommand();
                if (zombie.equals(p)) {
                    toRemove = (JButton) c;
                }
            }
        }
        if (toRemove != null) {
            zombieSlot.remove(toRemove);
            revalidate();
            repaint();
        } else if (count < 5) {
            final JButton button = getZombiePacket(p);
            int width = zombieSlot.getWidth();
            button.setActionCommand(p);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            Image zombieImage = new ImageIcon("src/resources/imag/Zombies/Zombie Packets/" + p + "_Zombie_Packet.png").getImage();
            Image disabledImage = new ImageIcon("src/resources/imag/Zombies/Zombie Packets/" + p + "_Zombie_Packet_Disabled.png").getImage();
            int zombieHeight = zombieImage.getHeight(null) * width / zombieImage.getWidth(null);
            ImageIcon zombieIcon = new ImageIcon(zombieImage.getScaledInstance(width, zombieHeight, Image.SCALE_SMOOTH));
            ImageIcon disabledIcon = new ImageIcon(disabledImage.getScaledInstance(width, zombieHeight, Image.SCALE_SMOOTH));
            button.setIcon(zombieIcon);
            button.setDisabledIcon(disabledIcon);
            button.setEnabled(false);
            zombieSlot.add(button);
        }
    }

    /**
     * Prepares the zombie chooser, allowing the player to select which zombies they want to place on the field. The selected zombies are added to the zombie slot.
     */
    public void prepareZombieChooser() {
        ZombiesChooser zombiesChooser = new ZombiesChooser(getWidth(), getHeight());
        layeredPane.add(zombiesChooser, JLayeredPane.PALETTE_LAYER);
        layeredPane.revalidate();
        layeredPane.repaint();
        SwingUtilities.invokeLater(() -> {
            Component[] components = zombiesChooser.zombieChooser.getComponents();
            for (Component component : components) {
                if (component.getClass().getSimpleName().equals("JButton")) {
                    JButton button = (JButton) component;
                    button.addActionListener(_ -> {
                        String p = button.getActionCommand();
                        prepareZombiePacket(p);
                    });
                }
            }
            zombiesChooser.letsRock.addActionListener(_ -> {
                if (zombieSlot.getComponentCount() == 5) {
                    layeredPane.remove(zombiesChooser);
                    ArrayList<String> chosenZombies = new ArrayList<>();
                    Component[] component = zombieSlot.getComponents();
                    for (Component c : component) {
                        if (c.getClass().getSimpleName().equals("JButton")) {
                            chosenZombies.add(((JButton) c).getActionCommand());
                        }
                    }
                    ((PvP) level).setChosenZombies(chosenZombies);
                    isProgressBarVisible = true;
                    isTimerVisible = true;
                    revalidate();
                    repaint();
                    letsRockAction();
                } else {
                    POOBVsZombiesGUI.buzzerSound();
                }
            });
        });
    }

    /**
     * Starts the game when both players have selected all zombies and are ready to begin the match.
     */
    @Override
    protected void letsRockAction() {
        ++letsRock;
        if (letsRock == 2) {
            pickYourSeeds.stopClip();
            enableShovel();
            playingPlants();
            handleStart();
            menu.setEnabled(true);
            backgroundMusic.loop();
        }
    }

    /**
     * Controls the main game timer, updating the game state at regular intervals and handling transitions between the plant and zombie turns.
     */
    @Override
    protected void handleStart() {
        mainTimer = new Timer(100 / 3, _ -> {
            refresh();
            level.updateTime();
            if (level.getTime() <= 0 && !((PvP) level).getZombiesPlaying()) {
                playingZombies();
            } else if (level.getTime() <= 0 && ((PvP) level).getZombiesPlaying()) {
                level.decreaseWaves();
                playingPlants();
            }
            refreshPaint();
        });
        mainTimer.start();
    }

    /*
     * Configures the interface for the zombie's turn, disabling plant selection and enabling zombie actions.
     */
    private void playingZombies() {
        disablePlantSlot();
        enableZombieSlot();
        disableShovel();
        ((PvP) level).setZombiesPlaying(true);
        ((PvP) level).setZombiesTimer();
    }

    /*
     * Configures the interface for the plant's turn, disabling zombie selection and enabling plant actions.
     */
    private void playingPlants() {
        disableZombieSlot();
        enablePlantSlot();
        enableShovel();
        ((PvP) level).setZombiesPlaying(false);
        ((PvP) level).setStrategyTimer();
        start.setVisible(true);
        start.setEnabled(true);
    }

    /*
     * Enables the zombie slot buttons in the GUI, allowing the player to select zombies for placement on the field.
     */
    private void enableZombieSlot() {
        Component[] components = zombieSlot.getComponents();
        for (Component component : components) {
            if (component.getClass().getSimpleName().equals("JButton")) {
                component.setEnabled(true);
            }
        }
    }

    /*
     * Disables the zombie slot buttons in the GUI, preventing the player from selecting zombies during gameplay.
     */
    private void disableZombieSlot() {
        Component[] components = zombieSlot.getComponents();
        for (Component component : components) {
            if (component.getClass().getSimpleName().equals("JButton")) {
                component.setEnabled(false);
            }
        }
    }

    /**
     * Retrieves a button for a specific zombie that can be placed on the game field.
     * @param z The name of the zombie.
     * @return The button for the specified zombie.
     */
    private JButton getZombiePacket(String z) {
        JButton button = new JButton();
        button.addActionListener(_ -> placeZombieActionListener(z));
        return button;
    }

    /**
     * Handles the action of placing a zombie on the field when the player clicks on a cell in the game grid.
     * @param zombie The name of the zombie being placed.
     */
    protected void placeZombieActionListener(String zombie) {
        Component[] components = board.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i].getClass().getSimpleName().equals("JButton")) {
                int finalI = i;
                ((JButton) components[i]).addActionListener(_ -> {
                    int row = finalI / 9;
                    int column = finalI % 9;
                    if (column >= 6) {
                        placeZombie(row, column, zombie, getWidth(), getHeight());
                    } else {
                        POOBVsZombiesGUI.buzzerSound();
                    }
                    disableBoard();
                });
                components[i].setEnabled(true);
                components[i].setVisible(true);
            }
        }
    }

    /**
     * Handles the action listener for placing a plant on the board.
     * 
     * @param plant The name of the plant to be placed.
     */
    @Override
    protected void placePlantActionListener(String plant) {
        Component[] components = board.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i].getClass().getSimpleName().equals("JButton")) {
                int finalI = i;
                ((JButton) components[i]).addActionListener(_ -> {
                    int row = finalI / 9;
                    int column = finalI % 9;
                    if (column < 6) {
                        plant(row, column, plant);
                    } else {
                        POOBVsZombiesGUI.buzzerSound();
                    }
                    disableBoard();
                });
                components[i].setEnabled(true);
                components[i].setVisible(true);
            }
        }
    }

    /**
     * Places a zombie at a specific location on the game field.
     * @param row The row where the zombie is placed.
     * @param column The column where the zombie is placed.
     * @param className The class name of the zombie.
     * @param width The width of the zombie.
     * @param height The height of the zombie.
     */
    @Override
    public void placeZombie(int row, int column, String className, int width, int height) {
        try {
            switch (className) {
                case "Basic", "Conehead", "Buckethead", "ECIZombie", "Brainstein" -> level.placeZombie(row, column, className);
                default -> throw new POOBVsZombiesGUIException(POOBVsZombiesGUIException.UNKNOWN_ZOMBIE + className);
            };
            Sound rise = new Sound("src/resources/sound/soundeffects/dirt_rise.wav");
            rise.startClip();
        } catch (POOBVsZombiesException | POOBVsZombiesGUIException _) {
            POOBVsZombiesGUI.buzzerSound();
        }
    }

    /**
     * Retrieves the background image for the game field depending on the lawn type.
     * @param lawnName The name of the lawn type.
     * @return The background image for the specified lawn type.
     */
    @Override
    protected void getLawn(String lawnName) {
        try {
            switch (lawnName) {
                case ("Day") ->
                        lawn = ImageIO.read(new File("src/resources/imag/Level Interface/background1Stripe.png"));
                case ("Night") ->
                        lawn = ImageIO.read(new File("src/resources/imag/Level Interface/background2Stripe.png"));
                default -> throw new POOBVsZombiesGUIException(POOBVsZombiesGUIException.UNKNOWN_LAWN + lawnName);
            }
        } catch (IOException _) {
        } catch (POOBVsZombiesGUIException _) {
            POOBVsZombiesGUI.buzzerSound();
        }
    }

    /*
     * Updates the brain timer and places a brain on the field when appropriate. The brain is collected by the player once it appears.
     */
    private void updateBrainTimer() {
        brainTimer += (double) 100 / 3;
        if (brainTimer >= 10000) {
            Brain brain = getBrain();
            brain.setGround(getHeight());
            brain.addActionListener(_ -> {
                ((PvP) level).collectBrain(brain.getEconomySize());
                layeredPane.remove(brain);
                Sound slurp = new Sound("src/resources/sound/soundeffects/SFX-slurp.wav");
                slurp.startClip();
            });
            layeredPane.add(brain, JLayeredPane.DEFAULT_LAYER);
            brainTimer = 0;
        }
    }

    /**
     * Generates a new random brain with a random position on the game field.
     * 
     * @return A new Brain object with random X and Y positions within the defined bounds.
     */
    private Brain getBrain() {
        Random random = new Random();
        int minXPosition = getWidth() * 290 / 1280;
        int maxXPosition = getWidth() * 1080 / 1280;
        int minYPosition = getHeight() * 202 / 720;
        int maxYPosition = getHeight() * 560 / 720;
        int randomXPosition = random.nextInt((maxXPosition - minXPosition) + 1) + minXPosition;
        int randomYPosition = random.nextInt((maxYPosition - minYPosition) + 1) + minYPosition;
        return new Brain(randomXPosition, randomYPosition, 25, getWidth(), getHeight());
    }

    /**
     * Disables the start button and the zombie slot.
     */
    @Override
    protected void handleButtons() {
        start.setEnabled(false);
        disableZombieSlot();
    }

    /**
     * Enables the appropriate buttons depending on whether zombies are currently playing.
     */
    @Override
    protected void enableButtons() {
        boolean areZombiesPlaying = ((PvP) level).getZombiesPlaying();
        if (areZombiesPlaying) {
            enableZombieSlot();
        } else {
            start.setEnabled(true);
            enableShovel();
            enablePlantSlot();
        }
    }

    @Override
    protected void updateSunTimer() {
    }

    @Override
    public void getSunProducers(Plant p) {
    }


    /**
     * Refreshes subclasses by handling conditions such as winning the game or updating the progress bar.
     */
    @Override
    protected void refreshSubclasses() {
        if (level.getWaves() == 0) {
            pauseTimer();
            YouWon youWon = new YouWon(getWidth(), getHeight());
            SwingUtilities.invokeLater(() -> 
                youWon.ok.addActionListener(_ -> {
                    mainMenu();
                    removeActiveMatch();
                })
            );
            layeredPane.add(youWon, JLayeredPane.PALETTE_LAYER);
        }
        if (((PvP) level).getZombiesPlaying()) {
            updateBrainTimer();
            int plantsDefeated = ((PvP) level).getPlantsDefeated();
            int zombiesDefeated = ((PvP)level).getZombiesDefeated();
            ((PvPProgressBar) progressBar).updateBuffer(zombiesDefeated, plantsDefeated);
        }
    }


    /**
     * Paints the component with high-quality rendering settings and includes the brain bank image.
     * 
     * @param g The Graphics object used to draw the components.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        paintBrainBank(g2d);
    }

    /**
     * Paints the progress bar for the game, showing the wave progress for both the blue (player) and red (zombie) sides.
     * 
     * @param g2d The Graphics2D object used for drawing the progress bar.
     */
    @Override
    public void paintProgressBar(Graphics2D g2d) {
        if (isProgressBarVisible) {
            // Wave Progress Bar
            BufferedImage blueBar = ((PvPProgressBar) progressBar).getBlueBar();
            BufferedImage redBar = ((PvPProgressBar) progressBar).getRedBar();
            int xPosition = ((PvPProgressBar) progressBar).getBuffer() + progressBar.getXPosition();
            g2d.drawImage(blueBar, progressBar.getXPosition(), progressBar.getYPosition(), blueBar.getWidth(), blueBar.getHeight(), null);
            g2d.drawImage(redBar, xPosition, progressBar.getYPosition(), redBar.getWidth(), redBar.getHeight(), null);
            // Fists
            Image fists = ((PvPProgressBar) progressBar).getFists();
            int fistsXPosition = ((PvPProgressBar) progressBar).getBuffer() + progressBar.getXPosition() - 110 * getWidth() / 1280;
            int fistsYPosition = progressBar.getYPosition() - 14 * getHeight() / 720;
            g2d.drawImage(fists, fistsXPosition, fistsYPosition, fists.getWidth(null), fists.getHeight(null), null);
        }
    }


    /**
     * Paints the brain bank image on the game screen and displays the amount of brains collected by the player.
     * 
     * @param g2d The Graphics2D object used for drawing the brain bank and text.
     */
    private void paintBrainBank(Graphics2D g2d) {
        // Brain bank
        int brainBankXPosition = 907 * getWidth() / 1280;
        int brainBankYPosition = 5 * getHeight() / 720;
        g2d.drawImage(brainBank, brainBankXPosition, brainBankYPosition, brainBank.getWidth(null), brainBank.getHeight(null), null);
        // Sun collected
        g2d.setColor(Color.BLACK);
        float customSize = (float) (getHeight() * 26) / 720;
        Font customFont = POOBVsZombiesGUI.cafeteriaBold.deriveFont(customSize);
        g2d.setFont(customFont);
        int textXPosition = getWidth() * 940 / 1280;
        int textYPosition = getHeight() * 43 / 720;
        g2d.drawString(String.valueOf(((PvP) level).getBrainCollected()), textXPosition, textYPosition);
    }

    /**
     * The ZombiesChooser class represents the panel for selecting zombie packets in the game.
     * It displays a background image, buttons for selecting zombie packs, and a confirmation button.
     */
    private static class ZombiesChooser extends JPanel {
        private final JLayeredPane zombieChooserPane;
        private JPanel zombieChooser;
        private JButton letsRock;

        public ZombiesChooser(int width, int height) {
            zombieChooserPane = new JLayeredPane();
            zombieChooserPane.setLayout(null);
            int panelHeight = height * 517 / 720;
            int panelWidth = width * 470 / 1280;
            int xPosition = width * 663 / 1280;
            int yPosition = height * 101 / 720;
            SwingUtilities.invokeLater(() -> {
                setLayout(new BorderLayout());
                add(zombieChooserPane, BorderLayout.CENTER);
                setOpaque(false);
                setBounds(xPosition, yPosition, panelWidth, panelHeight);
                prepareBackground();
                setJButton();
                setJButtons();
            });
        }

        private void prepareBackground() {
            Image chooser = new ImageIcon("src/resources/imag/Level Interface/ZombiesChooser_Background.png").getImage();
            ImageIcon chooserScaled = new ImageIcon(chooser.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
            JLabel chooserLabel = new JLabel();
            chooserLabel.setBounds(0, 0, getWidth(), getHeight());
            chooserLabel.setIcon(chooserScaled);
            zombieChooserPane.add(chooserLabel, JLayeredPane.DEFAULT_LAYER);
        }

        private void setJButtons() {
            zombieChooser = new JPanel();
            int height = getHeight() * 169 / 517;
            int width = getWidth() * 384 / 470;
            int xPosition = getWidth() * 53 / 470;
            int yPosition = getHeight() * 51 / 517;
            zombieChooser.setBounds(xPosition, yPosition, width, height);
            zombieChooser.setLayout(new GridLayout(2, 3, getWidth() * 26 / 517, 0));
            String[] zombiePackets = {"Basic", "Brainstein", "Conehead", "Buckethead", "ECIZombie"};
            for (String p : zombiePackets) {
                JButton button = new JButton();
                button.setActionCommand(p);
                button.setFocusPainted(false);
                button.setBorderPainted(false);
                button.setContentAreaFilled(false);
                Image zombieImage = new ImageIcon("src/resources/imag/Zombies/Zombie Packets/" + p + "_Zombie_Packet.png").getImage();
                Image disabledImage = new ImageIcon("src/resources/imag/Zombies/Zombie Packets/" + p + "_Zombie_Packet_Disabled.png").getImage();
                int seedHeight = getHeight() * 70 / 517;
                int seedWidth = getWidth() * 109 / 470;
                ImageIcon plantIcon = new ImageIcon(zombieImage.getScaledInstance(seedWidth, seedHeight, Image.SCALE_SMOOTH));
                ImageIcon disabledIcon = new ImageIcon(disabledImage.getScaledInstance(seedWidth, seedHeight, Image.SCALE_SMOOTH));
                button.setIcon(plantIcon);
                button.setDisabledIcon(disabledIcon);
                button.setEnabled(true);
                zombieChooser.add(button);
            }
            zombieChooser.setOpaque(false);
            zombieChooserPane.add(zombieChooser, JLayeredPane.PALETTE_LAYER);
        }

        private void setJButton() {
            Image letsRockImage = new ImageIcon("src/resources/imag/Level Interface/ZombieChooser_Button.png").getImage();
            Image letsRockDisabledImage = new ImageIcon("src/resources/imag/Level Interface/ZombieChooser_ButtonDown.png").getImage();
            letsRock = new JButton();
            int newWidth = getWidth() * 157 / 470;
            int newHeight = getHeight() * 42 / 517;
            ImageIcon letsRockScaled = new ImageIcon(letsRockImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
            ImageIcon letsRockDisabledScaled = new ImageIcon(letsRockDisabledImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
            letsRock.setBounds(getWidth() * 169 / 470, getHeight() * 445 / 517, letsRockScaled.getIconWidth(), letsRockScaled.getIconHeight());
            letsRock.setIcon(letsRockScaled);
            letsRock.setDisabledIcon(letsRockScaled);
            letsRock.setRolloverIcon(letsRockDisabledScaled);
            letsRock.setFocusPainted(false);
            letsRock.setBorderPainted(false);
            letsRock.setContentAreaFilled(false);
            zombieChooserPane.add(letsRock, JLayeredPane.PALETTE_LAYER);
        }
    }

    /**
     * The YouWon class represents the panel displayed when the player wins the game.
     * It shows a victory background, the winner's name, and a confirmation button.
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
                setJLabel1(height);
                setJLabel2(height);
            });
        }

        private void prepareBackground() {
            Image name = new ImageIcon("src/resources/imag/Level Interface/youWonPvP.png").getImage();
            ImageIcon nameScaled = new ImageIcon(name.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
            JLabel nameLabel = new JLabel();
            nameLabel.setBounds(0, 0, getWidth(), getHeight());
            nameLabel.setIcon(nameScaled);
            wonPane.add(nameLabel, JLayeredPane.DEFAULT_LAYER);
        }

        private void setJLabel1(int height) {
            JLabel scoreLabel = new JLabel(((PvP) level).getWinnerByDefeats(), SwingConstants.CENTER);
            int scoreWidth = getWidth() * 174 / 699;
            int scoreHeight = getHeight() * 31 / 517;
            scoreLabel.setBounds(getWidth() * 262 / 699, getHeight() * 161 / 517, scoreWidth, scoreHeight);
            float customSize = (float) (height * 31) / 720;
            scoreLabel.setFont(POOBVsZombiesGUI.dwarvenStonecraft.deriveFont(customSize));
            scoreLabel.setForeground(new Color(219, 188, 103));
            wonPane.add(scoreLabel, JLayeredPane.PALETTE_LAYER);
        }

        private void setJLabel2(int height) {
            JLabel scoreLabel = new JLabel(((PvP) level).getDetailedWinnerByDefeats(), SwingConstants.CENTER);
            int scoreWidth = getWidth() * 245 / 699;
            int scoreHeight = getHeight() * 45 / 517;
            scoreLabel.setBounds(getWidth() * 227 / 699, getHeight() * 281 / 517, scoreWidth, scoreHeight);
            float customSize = (float) (height * 45) / 720;
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
