package presentation;

import domain.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;


/**
 * The LevelGUI class represents the graphical user interface of the level in the domain package.
 * It manages the visual display of the game environment, including the game board, plants, zombies, mowers, projectiles, 
 * and other interactive elements.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12 2024
 */
public abstract class LevelGUI extends JPanel {
        protected final JLayeredPane layeredPane = new JLayeredPane();
        protected static Level level;
        protected JPanel board;
        protected JButton menu;
        private Shovel shovel;
        private LevelTimer levelTimer;
        protected ProgressBar progressBar;
        protected static HashMap<Plant, PlantGUI> plants;
        protected static HashMap<Zombie, ZombieGUI> zombies;
        protected static HashMap<Projectile, ProjectileGUI> projectiles;
        protected static HashMap<Mower, MowerGUI> mowers;
        protected static HashMap<Integer, ArrayList<Hitbox>> toPaintOrder;
        protected Sound backgroundMusic;
        protected Sound pickYourSeeds;
        protected BufferedImage lawn;
        protected String gameMode;
        private Image sunBank;
        private double sunTimer = 0;
        private JPanel plantSlot;
        protected Timer mainTimer;
        private final boolean showHitboxes = false;
        protected boolean isProgressBarVisible = false;
        protected boolean isTimerVisible = false;
    
    
        /**
         * Constructs a LevelGUI instance initializing the plants, zombies, projectiles,
         * and mowers
         * 
         * @param level The level instance.
         * @param lawn The string representing the path to the lawn image to be displayed.
         */
        public LevelGUI(Level level, String lawn) {
            LevelGUI.level = level;
            plants = new HashMap<>();
            zombies = new HashMap<>();
            projectiles = new HashMap<>();
            mowers = new HashMap<>();
            layeredPane.setLayout(null);
            SwingUtilities.invokeLater(() -> {
                setLayout(new BorderLayout());
                add(layeredPane, BorderLayout.CENTER);
                getLawn(lawn);
                prepareSeedChooser();
                prepareElements();
            });
        }
    
        /**
         * Constructs a LevelGUI instance initializing the plants, zombies, projectiles,
         * and mowers if the games has an active match.
         * 
         * @param level The level instance.
         */
        public LevelGUI(Level level) {
            LevelGUI.level = level;
            plants = new HashMap<>();
            zombies = new HashMap<>();
            projectiles = new HashMap<>();
            mowers = new HashMap<>();
            layeredPane.setLayout(null);
            SwingUtilities.invokeLater(() -> {
                setLayout(new BorderLayout());
                add(layeredPane, BorderLayout.CENTER);
                getLawn(level.getLawn().getClass().getSimpleName());
                prepareElements();
            });
        }
    
        /**
         * Prepares game elements that need to be display and interactions in the GUI.
         * 
         */
        public void prepareElements() {
            prepareBoard();
            preparePlantSlot();
            prepareSunBank();
            prepareShovelButton();
            prepareMenuButton();
            prepareActions();
            prepareOrder();
            try {
                getInstances();
            } catch (POOBVsZombiesGUIException _) {
            }
        }
    
        /**
         * Initializes and prepares the level timer for the game.
         * 
         */
        public void prepareTimerBank() {
            levelTimer = new LevelTimer(getWidth(), getHeight(), progressBar);
        }
    
        /**
         * Refreshes the visual display of the game by revalidating and repainting the components.
         * This method ensures that any updates to the game state are reflected in the GUI.
         * 
         */
        public void refreshPaint() {
            revalidate();
            repaint();
        }
    

        /*
         * Initializes and prepares the order of elements to be painted on the GUI.
         * This method sets up a map called toPaintOrder, which organizes elements to be painted at specific Y-axis
         * positions representing the hiboxes alignment vertically.
         * 
         */
        private void prepareOrder() {
            toPaintOrder = new HashMap<>();
            for (int i = 0; i < 5; i++) {
                int y = (i * 113) + 98 + (32 / 3);
                toPaintOrder.put(y, new ArrayList<>());
            }
        }
    
        /**
         * Initializes and prepares the shovel button for the game interface.
         * It creates a new shovel object, sets its bounds, and prepares the shovel
         * bank (where shovel-related actions take place).
         * 
         */
        private void prepareShovelButton() {
            int shovelWidth = getWidth() * 69 / 1280;
            int shovelHeight = getHeight() * 75 / 720;
            int shovelXPosition = getWidth() * 1156 / 1280;
            int shovelYPosition = getHeight() * 634 / 720;
            shovel = new Shovel(shovelWidth, shovelHeight);
            shovel.setBounds(shovelXPosition, shovelYPosition, shovelWidth, shovelHeight);
            shovel.setEnabled(false);
            int bankWidth = getWidth() * 99 / 1280;
            int bankHeight = getHeight() * 102 / 720;
            int bankXPosition = getWidth() * 1141 / 1280;
            int bankYPosition = getHeight() * 620 / 720;
            shovel.prepareShovelBank(bankWidth, bankHeight, bankXPosition, bankYPosition);
            shovel.addActionListener(_ -> {
                removePlantActionListener();
                shovel.setVisible(false);
            });
            layeredPane.add(shovel, JLayeredPane.DEFAULT_LAYER);
        }
    
        /**
         * Removes the action listeners from the board buttons and enables the shovel button.
         * This method loops through all the components on the board and checks for buttons.
         * It then adds an action listener to each button to handle the removal of a plant when clicked.
         * Once a plant is removed, the shovel button is made visible and the board is disabled to prevent
         * further interactions until the shovel action is complete.
         * 
         */
        protected void removePlantActionListener() {
            Component[] components = board.getComponents();
            for (int i = 0; i < components.length; i++) {
                if (components[i].getClass().getSimpleName().equals("JButton")) {
                    int finalI = i;
                    ((JButton) components[i]).addActionListener(_ -> {
                        int row = finalI / 9;
                        int column = finalI % 9;
                        removePlant(row, column);
                        disableBoard();
                        shovel.setVisible(true);
                    });
                    components[i].setEnabled(true);
                    components[i].setVisible(true);
                }
            }
        }
    
        /**
         * Retrieves and initializes instances of the game components (mowers, plants, zombies, projectiles, and plant slots).
         * This method calls various methods to retrieve and initialize existing components that are essential for gameplay.
         * 
         */
        public void getInstances() throws POOBVsZombiesGUIException {
            getMowers();
            getPlants();
            getZombies();
            getProjectiles();
            getPlantSlot();
        }
    
        /*
         * Retrieves and initializes the mower instances for the game.
         * This method iterates through all the mowers in the level and checks if they have
         * been added to the mowers map.If not, it creates a new `MowerGUI` instance for each
         * mower and adds it to the map.
         * 
         */
        private void getMowers() {
            for (Mower m : level.getMowers().values()) {
                if (!mowers.containsKey(m)) {
                    MowerGUI mowerGUI = new MowerGUI(m.getHitbox().x, m.getHitbox().y, getWidth(), getHeight());
                    SwingUtilities.invokeLater(() -> {
                        mowers.put(m, mowerGUI);
                        toPaintOrder.get(m.getHitbox().y - 40).add(m);
                    });
                }
            }
        }

        /*
         * Retrieves and initializes all the plants from the current level, creating appropriate GUI representations
         * for each plant and adding them to the plants map.
         * For each plant in the level, this method checks the type of the plant and creates a corresponding GUI component.
         * 
         */
        private void getPlants() throws POOBVsZombiesGUIException {
            for (ArrayList<Plant> array : level.getPlants().values()) {
                for (Plant p : array) {
                    PlantGUI plantGUI;
                    switch (p.getClass().getSimpleName()) {
                        case "Sunflower" -> plantGUI = new SunflowerGUI(p.getHitbox().x, p.getHitbox().y, getWidth(), getHeight());
                        case "Peashooter" -> plantGUI = new PeashooterGUI(p.getHitbox().x, p.getHitbox().y, getWidth(), getHeight());
                        case "WallNut" -> plantGUI = new WallNutGUI(p.getHitbox().x, p.getHitbox().y, getWidth(), getHeight());
                        case "PotatoMine" -> plantGUI = new PotatoMineGUI(p.getHitbox().x, p.getHitbox().y, getWidth(), getHeight());
                        case "ECIPlant" -> plantGUI = new ECIPlantGUI(p.getHitbox().x, p.getHitbox().y, getWidth(), getHeight());
                        case "Evolve" -> plantGUI = new EvolveGUI(p.getHitbox().x, p.getHitbox().y, getWidth(), getHeight());
                        default -> throw new POOBVsZombiesGUIException(POOBVsZombiesGUIException.UNKNOWN_PLANT);
                    }
                    SwingUtilities.invokeLater(() -> {
                        plants.put(p, plantGUI);
                        toPaintOrder.get(p.getHitbox().y).add(p);
                    });
                }
            }
        }
    

         /*
         * Retrieves and initializes all the zombies from the current level, creating appropriate GUI representations
         * for each zombie and adding them to the zombies map.
         * For each zombie in the level, this method checks the type of the zombie and creates a corresponding GUI component.
         * 
         */
        private void getZombies() throws POOBVsZombiesGUIException {
            for (ArrayList<Zombie> array : level.getZombies().values()) {
                for (Zombie z : array) {
                    ZombieGUI zombieGUI;
                    switch (z.getClass().getSimpleName()) {
                        case "Basic" ->
                                zombieGUI = new BasicGUI(z.getHitbox().x, z.getHitbox().y, getWidth(), getHeight());
                        case "Conehead" ->
                                zombieGUI = new ConeheadGUI(z.getHitbox().x, z.getHitbox().y, getWidth(), getHeight());
                        case "Buckethead" ->
                                zombieGUI = new BucketheadGUI(z.getHitbox().x, z.getHitbox().y, getWidth(), getHeight());
                        case "ECIZombie" ->
                                zombieGUI = new ECIZombieGUI(z.getHitbox().x, z.getHitbox().y, getWidth(), getHeight());
                        case "Brainstein" ->
                                zombieGUI = new BrainsteinGUI(z.getHitbox().x, z.getHitbox().y, getWidth(), getHeight());
                        default -> throw new POOBVsZombiesGUIException(POOBVsZombiesGUIException.UNKNOWN_ZOMBIE);
                    }
                    SwingUtilities.invokeLater(() -> {
                        zombies.put(z, zombieGUI);
                        toPaintOrder.get(z.getHitbox().y).add(z);
                    });
                }
            }
        }
    
        /*
         * Retrieves and initializes all the projectiles from the current level, creating appropriate GUI representations
         * for each projectile and adding them to the projectiles map.
         * For each projectile in the level, this method checks the type of the projectile and creates a corresponding GUI component.
         * 
         */
        private void getProjectiles() throws POOBVsZombiesGUIException {
            for (ArrayList<Projectile> array : level.getProjectiles().values()) {
                for (Projectile p : array) {
                    ProjectileGUI projectileGUI;
                    switch (p.getClass().getSimpleName()) {
                        case "Leave" -> projectileGUI = new LeaveGUI(p.getHitbox().x, p.getHitbox().y, getWidth(), getHeight());
                        case "POOmBas" -> projectileGUI = new POOmBasGUI(p.getHitbox().x, p.getHitbox().y, getWidth(), getHeight());
                        default -> throw new POOBVsZombiesGUIException("Invalid projectile.");
                    }
                    SwingUtilities.invokeLater(() -> {
                        projectiles.put(p, projectileGUI);
                        toPaintOrder.get(p.getHitbox().y - 20).add(p);
                    });
                }
            }
        }
    
        /*
         * Initializes the plant slot UI JButtons based on the chosen plants for the current level.
         * This method retrieves the list of chosen plants from the level and prepares a seed packet for each chosen plant.
         * 
         */
        private void getPlantSlot() {
            ArrayList<String> chosenPlants = level.getChosenPlants();
            for (String p : chosenPlants) {
                prepareSeedPacket(p);
            }
        }
    
        /**
         * Places a plant on the board given the type of the plant that will be placed.
         *
         * @param row       Row in which the plant will be placed.
         * @param column    Column in which the plant will be placed.
         * @param plantName Name of the plant that will be placed.
         */
        protected void plant(int row, int column, String plantName) {
            try {
                switch (plantName) {
                    case "Peashooter", "Sunflower", "WallNut", "ECIPlant", "PotatoMine", "Evolve" -> level.placePlant(row, column, plantName);
                    default -> throw new POOBVsZombiesGUIException(POOBVsZombiesGUIException.UNKNOWN_PLANT + plantName);
                }
            } catch (POOBVsZombiesGUIException | POOBVsZombiesException _) {
                POOBVsZombiesGUI.buzzerSound();
            }
        }
    
        /**
         * Removes the plant located at the specified row and column from the level.
         * This method attempts to remove a plant from the level based on the provided coordinates (row and column).
         * If successful, it plays a "shovel" sound effect to indicate the plant removal.
         * If an error occurs, a buzzer sound is played.
         * 
         * @param row The row of the plant to be removed.
         * @param column The column of the plant to be removed.
         */
        protected void removePlant(int row, int column) {
            try {
                level.removePlant(row, column);
                Sound dig = new Sound("src/resources/sound/soundeffects/SFX-shovel.wav");
                dig.startClip();
            } catch (POOBVsZombiesException _) {
                POOBVsZombiesGUI.buzzerSound();
            }
        }
    
        /**
         * Places a zombie on the board at the specified row and column with the specified class name.
         * This method creates a new zombie of the given class name and places it at the given row and column on the game board.
         * The corresponding zombie GUI component is also created and added to the zombies map for further rendering. If an
         * unknown zombie class name is provided, an exception is thrown.
         * 
         * @param row The row to place the zombie.
         * @param column The column to place the zombie.
         * @param className The class name of the zombie to be placed.
         * @param width The width of the frame.
         * @param height The height of the frame.
         * 
         * @throws POOBVsZombiesException If an error occurs while placing the zombie.
         * @throws POOBVsZombiesGUIException If the zombie class name is unknown.
         */
        public void placeZombie(int row, int column, String className, int width, int height) throws POOBVsZombiesException, POOBVsZombiesGUIException {
            switch (className) {
                case "Basic", "Conehead", "Buckethead", "ECIZombie", "Brainstein" -> level.placeZombie(row, 10, className);
                default -> throw new POOBVsZombiesGUIException(POOBVsZombiesGUIException.UNKNOWN_ZOMBIE + className);
            }
        }
    
        protected void loseScreen() {
            disablePlantSlot();
            handleButtons();
            shovel.setEnabled(false);
            backgroundMusic.stopClip();
            ImageIcon[] sequenceImages = new ImageIcon[26];
            for (int i = 1; i <= 25; i++) {
                String imageName = String.format("src/resources/imag/Level Interface/Lose Screen/ZombiesWon%04d.png", i);
                sequenceImages[i] = new ImageIcon(imageName);
            }
            JLabel animation = new JLabel();
            animation.setBounds(240 * getWidth() / 1280, 60 * getHeight() / 720, 800 * getWidth() / 1280, 600 * getHeight() / 720);
            layeredPane.add(animation, JLayeredPane.PALETTE_LAYER);
            int[] animationIndex = {0};
            Sound lose =  new Sound("src/resources/sound/soundeffects/05.-losemusic.wav");
            lose.startClip();
            Timer intro = new Timer(1000 / 12, _ -> {
                animation.setIcon(sequenceImages[animationIndex[0]]);
                animationIndex[0]++;
            });
            TimerTask stopTask = new TimerTask() {
                @Override
                public void run() {
                    intro.stop();
                    Sound scream = new Sound("src/resources/sound/soundeffects/Voices-scream.wav");
                    scream.startClip();
                    Timer backToMenu = new Timer(5000, _ -> {
                        mainMenu();
                        removeActiveMatch();
                    });
                    backToMenu.setRepeats(false);
                    backToMenu.start();
                }
            };
            java.util.Timer stopTimer = new java.util.Timer();
            stopTimer.schedule(stopTask, 6500 / 3);
            intro.start();
    
        }
    
        /**
         * Plays the ready, set, plant animation at the start of each game.
         * 
         */
        protected void readySetPlant() {
            ImageIcon[] sequenceImages = new ImageIcon[23];
            for (int i = 1; i <= 22; i++) {
                String imageName = String.format("src/resources/imag/Level Interface/ReadySetPlant/StartReadySetPlant%04d.png", i);
                sequenceImages[i] = new ImageIcon(imageName);
            }
            JLabel animation = new JLabel();
            animation.setBounds(240 * getWidth() / 1280, 60 * getHeight() / 720, 800 * getWidth() / 1280, 600 * getHeight() / 720);
            layeredPane.add(animation, JLayeredPane.PALETTE_LAYER);
            int[] animationIndex = {0};
            Sound bass = new Sound("src/resources/sound/soundeffects/SFX-readysetplant.wav");
            bass.startClip();
            Timer intro = new Timer(1000 / 12, _ -> {
                animation.setIcon(sequenceImages[animationIndex[0]]);
                animationIndex[0]++;
                refreshPaint();
            });
            TimerTask stopTask = new TimerTask() {
                @Override
                public void run() {
                    intro.stop();
                    layeredPane.remove(animation);
                    isTimerVisible = true;
                    isProgressBarVisible = true;
                    backgroundMusic.loop();
                    enablePlantSlot();
                    enableButtons();
                    shovel.setEnabled(true);
                    handleStart();
                    menu.setEnabled(true);
                }
            };
            java.util.Timer stopTimer = new java.util.Timer();
            stopTimer.schedule(stopTask, 5750 / 3);
            intro.start();
        }
    
        protected void handleStart() {
            mainTimer = new Timer(100 / 3, _ -> refresh());
            mainTimer.start();
        }
    
        public void prepareBoard() {
            int rows = 5;
            int columns = 9;
            board = new JPanel();
            int width = getWidth() * 885 / 1280;
            int height = getHeight() * 595 / 720;
            int xPosition = getWidth() * 303 / 1280;
            int yPosition = getHeight() * 89 / 720;
            board.setBounds(xPosition, yPosition, width, height);
            board.setLayout(new GridLayout(rows, columns));
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    JButton button = getBoardButton();
                    board.add(button);
                }
            }
            board.setOpaque(false);
            layeredPane.add(board, JLayeredPane.DEFAULT_LAYER);
        }
    
        /**
         * Sets the default buttons that the board will use.
         *
         * @return JButton instance of the board.
         */
        public JButton getBoardButton() {
            JButton button = new JButton();
            button.setEnabled(false);
            button.setVisible(false);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            return button;
        }
    
        /*
         * Gets the image for the sun bank and adjusts its size depending on the frame size.
         *
         */
        private void prepareSunBank() {
            Image originalSunBank = new ImageIcon("src/resources/imag/Level Interface/sun_bank.png").getImage();
            int width = getWidth() * 184 / 1280;
            int height = getHeight() * 61 / 720;
            sunBank = originalSunBank.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }
    
        private void prepareActions() {
            menu.addActionListener(_ -> prepareMenu());
        }
    
        private void prepareMenu() {
            Sound pauseSFX = new Sound("src/resources/sound/soundeffects/24.-pause.wav");
            pauseSFX.startClip();
            backgroundMusic.pauseClip();
            pauseTimer();
            handleButtons();
            shovel.setEnabled(false);
            disablePlantSlot();
            menu.setEnabled(false);
            PauseMenu pause = new PauseMenu(getWidth(), getHeight());
            SwingUtilities.invokeLater(() -> {
                pause.backToGame.addActionListener(_ -> backToGame(pause));
                pause.restartLevel.addActionListener(_ -> {
                    try {
                        restartLevel();
                    } catch (POOBVsZombiesGUIException _) {
                        POOBVsZombiesGUI.buzzerSound();
                    }
                });
                pause.mainMenu.addActionListener(_ -> {
                    mainMenu();
                    POOBVsZombiesGUI.poobVsZombies.setActiveMatch(level);
                });
            });
            layeredPane.add(pause, JLayeredPane.PALETTE_LAYER);
        }
    
        protected void pauseTimer() {
            mainTimer.stop();
        }
    
        protected void unpauseTimer() {
            mainTimer.start();
        }
    
        protected abstract void handleButtons();
    
        protected abstract void enableButtons();

        private void backToGame(PauseMenu pause) {
            layeredPane.remove(pause);
            backgroundMusic.resumeClip(true);
            unpauseTimer();
            enableButtons();
            menu.setEnabled(true);
            refreshPaint();
        }
    
        private void restartLevel() throws POOBVsZombiesGUIException {
            LevelGUI levelGUI = null;
            int originalSun = LevelGUI.level.getOriginalSun();
            int originalWaves = LevelGUI.level.getOriginalWaves();
            String originalLawn = LevelGUI.level.getLawn().getClass().getSimpleName();
            double originalTime = LevelGUI.level.getOriginalTime();
            switch (gameMode) {
                case ("PvM") -> {
                    try {
                        PvM newLevel = new PvM(originalLawn, originalSun, originalWaves, originalTime / 60000);
                        levelGUI = new PvMGUI(newLevel, originalLawn);
                    } catch (POOBVsZombiesException _) {
                    }
                }
                case ("PvP") -> {
                    try {
                        PvP newLevel = new PvP(originalLawn, originalSun, originalTime / 60000);
                        levelGUI = new PvPGUI(newLevel, originalLawn);
                    } catch (POOBVsZombiesException _) {
                    }
                }
                case ("MvM") -> {
                    try {
                        MvM newLevel = new MvM(originalLawn, originalSun, ((MvM) level).getMachine(), originalTime / 60000);
                        levelGUI = new MvMGUI(newLevel, originalLawn);
                    } catch (POOBVsZombiesException _) {
                    }
                }
                default -> throw new POOBVsZombiesGUIException(POOBVsZombiesGUIException.UNKNOWN_GAMEMODE + gameMode);
            }
            if (levelGUI != null) {
                levelGUI.setSize(POOBVsZombiesGUI.dimensions);
                LevelGUI finalLevelGUI = levelGUI;
                SwingUtilities.invokeLater(() -> {
                    backgroundMusic.stopClip();
                    POOBVsZombiesGUI.switchToPanel(finalLevelGUI);
                });
            }
        }
    
        protected void mainMenu() {
            MainMenu mainMenu = new MainMenu();
            mainMenu.setSize(POOBVsZombiesGUI.dimensions);
            SwingUtilities.invokeLater(() -> {
                mainMenu.prepareElements();
                POOBVsZombiesGUI.switchToPanel(mainMenu);
                backgroundMusic.stopClip();
            });
        }
    
        protected void removeActiveMatch() {
            POOBVsZombiesGUI.poobVsZombies.removeActiveMatch(gameMode);
        }
    
        protected void disablePlantSlot() {
            Component[] components = plantSlot.getComponents();
            for (Component component : components) {
                if (component.getClass().getSimpleName().equals("JButton")) {
                    component.setEnabled(false);
                }
            }
        }
    
        protected void enablePlantSlot() {
            Component[] components = plantSlot.getComponents();
            for (Component component : components) {
                if (component.getClass().getSimpleName().equals("JButton")) {
                    component.setEnabled(true);
                }
            }
        }
    
        protected void disableShovel() {
            shovel.setEnabled(false);
        }
    
        protected void enableShovel() {
            shovel.setEnabled(true);
        }
    
        private void prepareMenuButton() {
            menu = new JButton();
            Image menu1Image = new ImageIcon("src/resources/imag/Level Interface/options_menuButton.png").getImage();
            Image menu2Image = new ImageIcon("src/resources/imag/Level Interface/options_menuButtonDown.png").getImage();
            int width = menu1Image.getWidth(null);
            int height = menu1Image.getHeight(null);
            int newWidth = getWidth() * 159 / 1280;
            int newHeight = height * newWidth / width;
            ImageIcon menu1 = new ImageIcon(menu1Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
            ImageIcon menu2 = new ImageIcon(menu2Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
            menu.setBounds(getWidth() * 1103 / 1280, getHeight() * 5 / 720, menu1.getIconWidth(), menu1.getIconHeight());
            menu.setIcon(menu1);
            menu.setDisabledIcon(menu1);
            menu.setRolloverIcon(menu2);
            menu.setFocusPainted(false);
            menu.setBorderPainted(false);
            menu.setContentAreaFilled(false);
            menu.setEnabled(false);
            layeredPane.add(menu, JLayeredPane.DEFAULT_LAYER);
        }
    
        /*
         * Prepares all plants that will be playable in the game, inspired by the PvZ game feature: Choose your plants.
         *
         */
        private void preparePlantSlot() {
            plantSlot = new JPanel();
            int height = getHeight() * 355 / 720;
            int width = getWidth() * 112 / 1280;
            int xPosition = getWidth() * 4 / 1280;
            int yPosition = getHeight() * 94 / 720;
            plantSlot.setBounds(xPosition, yPosition, width, height);
            plantSlot.setLayout(new GridLayout(5, 1, getWidth() * 2 / 1280, getHeight() * 2 / 720));
            plantSlot.setOpaque(false);
            layeredPane.add(plantSlot, JLayeredPane.DEFAULT_LAYER);
        }
    
        private void prepareSeedPacket(String p) {
            Component[] components = plantSlot.getComponents();
            JButton toRemove = null;
            int count = 0;
            for (Component c : components) {
                if (c.getClass().getSimpleName().equals("JButton")) {
                    ++count;
                    String plant = ((JButton) c).getActionCommand();
                    if (plant.equals(p)) {
                        toRemove = (JButton) c;
                    }
                }
            }
            if (toRemove != null) {
                plantSlot.remove(toRemove);
                revalidate();
                repaint();
            } else if (count < 5) {
                final JButton button = getSeedPacket(p);
                int width = plantSlot.getWidth();
                button.setActionCommand(p);
                button.setFocusPainted(false);
                button.setBorderPainted(false);
                button.setContentAreaFilled(false);
                Image plantImage = new ImageIcon("src/resources/imag/Plants/Seed Packets/" + p + "_Seed_Packet.png").getImage();
                Image disabledImage = new ImageIcon("src/resources/imag/Plants/Seed Packets/" + p + "_Seed_Packet_Disabled.png").getImage();
                int seedHeight = plantImage.getHeight(null) * width / plantImage.getWidth(null);
                ImageIcon plantIcon = new ImageIcon(plantImage.getScaledInstance(width, seedHeight, Image.SCALE_SMOOTH));
                ImageIcon disabledIcon = new ImageIcon(disabledImage.getScaledInstance(width, seedHeight, Image.SCALE_SMOOTH));
                button.setIcon(plantIcon);
                button.setDisabledIcon(disabledIcon);
                button.setEnabled(false);
                plantSlot.add(button);
            }
    
        }
    
        public void prepareSeedChooser() {
            SeedChooserPlants seedChooserPlants = new SeedChooserPlants(getWidth(), getHeight());
            layeredPane.add(seedChooserPlants, JLayeredPane.PALETTE_LAYER);
            layeredPane.revalidate();
            layeredPane.repaint();
            SwingUtilities.invokeLater(() -> {
                Component[] components = seedChooserPlants.seedChooser.getComponents();
                for (Component component : components) {
                    if (component.getClass().getSimpleName().equals("JButton")) {
                        JButton button = (JButton) component;
                        button.addActionListener(_ -> {
                            String p = button.getActionCommand();
                            prepareSeedPacket(p);
                        });
                    }
                }
                seedChooserPlants.letsRock.addActionListener(_ -> {
                    if (plantSlot.getComponentCount() == 5) {
                        layeredPane.remove(seedChooserPlants);
                        ArrayList<String> chosenPlants = new ArrayList<>();
                        Component[] component = plantSlot.getComponents();
                        for (Component c : component) {
                            if (c.getClass().getSimpleName().equals("JButton")) {
                                chosenPlants.add(((JButton) c).getActionCommand());
                            }
                        }
                        level.setChosenPlants(chosenPlants);
                        refreshPaint();
                        letsRockAction();
                    } else {
                        POOBVsZombiesGUI.buzzerSound();
                    }
                });
            });
        }
    
        protected abstract void letsRockAction();
    
        private JButton getSeedPacket(String p) {
            JButton button = new JButton();
            button.addActionListener(_ -> {
                if (level.getAmount(p) <= level.getSunCollected()) {
                    setShovelVisibility(true);
                    placePlantActionListener(p);
                } else {
                    POOBVsZombiesGUI.buzzerSound();
                }
            });
            return button;
        }
    
        protected void placePlantActionListener(String plant) {
            Component[] components = board.getComponents();
            for (int i = 0; i < components.length; i++) {
                if (components[i].getClass().getSimpleName().equals("JButton")) {
                    int finalI = i;
                    ((JButton) components[i]).addActionListener(_ -> {
                        int row = finalI / 9;
                        int column = finalI % 9;
                        plant(row, column, plant);
                        disableBoard();
                    });
                    components[i].setEnabled(true);
                    components[i].setVisible(true);
                }
            }
        }
    
        protected void disableBoard() {
            Component[] components = board.getComponents();
            for (Component component : components) {
                if (component.getClass().getSimpleName().equals("JButton")) {
                    for (ActionListener action : ((JButton) component).getActionListeners()) {
                        ((JButton) component).removeActionListener(action);
                    }
                    component.setEnabled(false);
                    component.setVisible(false);
                }
            }
        }
    
        public void setShovelVisibility(boolean state) {
            if (shovel != null) {
                shovel.setVisible(state);
            }
        }
    
        /**
         * Gets the game background image according to the name of the lawn, which can be: Day, Night, Pool, Fog, Roof.
         *
         * @param lawnName Name of the stage that will be played.
         */
        protected void getLawn(String lawnName) {
            try {
                switch (lawnName) {
                    case ("Day") -> lawn = ImageIO.read(new File("src/resources/imag/Level Interface/background1.png"));
                    case ("Night") -> lawn = ImageIO.read(new File("src/resources/imag/Level Interface/background2.png"));
                default -> throw new POOBVsZombiesGUIException(POOBVsZombiesGUIException.UNKNOWN_LAWN + lawnName);
            }
        } catch (IOException _) {
        } catch (POOBVsZombiesGUIException _) {
            POOBVsZombiesGUI.buzzerSound();
        }
    }

    /**
     * Sets a timer in which a sun falls from the sky every 8 seconds according to the PvZ wiki.
     */
    protected void updateSunTimer() {
        sunTimer += (double) 100 / 3;
        if (sunTimer >= 10000) {
            Sun sun = getSun();
            sun.setSky(getHeight());
            sun.addActionListener(_ -> {
                level.collectSun(sun.getEconomySize());
                layeredPane.remove(sun);
                Sound points = new Sound("src/resources/sound/soundeffects/SFX-points.wav");
                points.startClip();
            });
            layeredPane.add(sun, JLayeredPane.DEFAULT_LAYER);
            sunTimer = 0;
        }
    }

    public void getSunProducers(Plant p) {
        int size = 0;
        if (p.getClass().getSimpleName().equals("Sunflower")) {
            size = ((Sunflower) p).getSun();
        } else if (p.getClass().getSimpleName().equals("ECIPlant")) {
            size = ((ECIPlant) p).getSun();
        }
        if (size != 0) {
            int x = plants.get(p).xPosition + getWidth() * 16 / 1280;
            int y = plants.get(p).yPosition + getHeight() * 16 / 720;
            Sun sun = new Sun(x, y + getHeight() * 8 / 720, size, getWidth(), getHeight());
            sun.setEconomy(x, y);
            sun.addActionListener(_ -> {
                level.collectSun(sun.getEconomySize());
                layeredPane.remove(sun);
                Sound points = new Sound("src/resources/sound/soundeffects/SFX-points.wav");
                points.startClip();
            });
            layeredPane.add(sun, JLayeredPane.DEFAULT_LAYER);
        }
    }

    public void getBrainProducers(Zombie z) {
        int size = 0;
        if (z.getClass().getSimpleName().equals("Brainstein")) {
            size = ((Brainstein) z).getBrain();
        }
        if (size != 0) {
            int x = zombies.get(z).xPosition - getWidth() * 40 / 1280;
            int y = zombies.get(z).yPosition + zombies.get(z).height - getHeight() * 48 / 720;
            Brain brain = new Brain(x, y - getHeight() * 20 / 720, size, getWidth(), getHeight());
            brain.setEconomy(x, y);
            brain.addActionListener(_ -> {
                level.collectSun(brain.getEconomySize());
                layeredPane.remove(brain);
                Sound slurp = new Sound("src/resources/sound/soundeffects/SFX-slurp.wav");
                slurp.startClip();
            });
            layeredPane.add(brain, JLayeredPane.DEFAULT_LAYER);
        }
    }

    /*
     * Gets a sun instance that will fall in a random position on the board.
     * @return Sun instance.
     */
    private Sun getSun() {
        Random random = new Random();
        int minXPosition = getWidth() * 290 / 1280;
        int maxXPosition = getWidth() * 964 / 1280;
        int minYPosition = getHeight() * 202 / 720;
        int maxYPosition = getHeight() * 560 / 720;
        int randomXPosition = random.nextInt((maxXPosition - minXPosition) + 1) + minXPosition;
        int randomYPosition = random.nextInt((maxYPosition - minYPosition) + 1) + minYPosition;
        return new Sun(randomXPosition, randomYPosition, 25, getWidth(), getHeight());
    }

    /**
     * This method handles interactions between all elements in the game, such as moving
     * the zombies, firing projectiles, and handling collisions. It also removes plants
     * and zombies when necessary and updates the game state .
     */
    public void refresh() {
        HashMap<Integer, ArrayList<Plant>> plantMap = level.getPlants();
        HashMap<Integer, ArrayList<Zombie>> zombieMap = level.getZombies();
        HashMap<Integer, ArrayList<Projectile>> projectileMap = level.getProjectiles();
        HashMap<Integer, Mower> mowerMap = level.getMowers();
        ArrayList<Plant> actualPlants = new ArrayList<>();
        ArrayList<Zombie> actualZombies = new ArrayList<>();
        ArrayList<Projectile> actualProjectiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int y = (i * 113) + 98 + (32 / 3);
            ArrayList<Plant> plantLine = plantMap.get(y);
            ArrayList<Zombie> zombieLine = zombieMap.get(y);
            ArrayList<Projectile> projectileLine = projectileMap.get(y + 20);
            Mower mower = mowerMap.get(y + 40);
            ArrayList<Plant> plantsToRemove = new ArrayList<>();
            ArrayList<Zombie> zombiesToRemove = new ArrayList<>();
            ArrayList<Projectile> projectilesToRemove = new ArrayList<>();
            mowerVerification(mower, y);
            plantVerification(plantLine, zombieLine);
            zombieVerification(zombieLine, plantLine, plantsToRemove, zombiesToRemove, mower);
            projectilesVerification(projectileLine, zombieLine, zombiesToRemove, projectilesToRemove, plantLine, plantsToRemove);
            level.removePlants(y, plantsToRemove);
            level.removeZombies(y, zombiesToRemove);
            level.removeProjectiles(y, projectilesToRemove);
            actualPlants.addAll(plantLine);
            actualZombies.addAll(zombieLine);
            actualProjectiles.addAll(projectileLine);
        }
        removePlants(actualPlants);
        removeZombies(actualZombies);
        removeProjectiles(actualProjectiles);
        refreshEconomy();
        refreshSubclasses();
        SwingUtilities.invokeLater(this::refreshPaint);
    }

    private void removePlants(ArrayList<Plant> plantLine) {
        Set<Plant> plantKeys = plants.keySet();
        ArrayList<Plant> plantKeyList = new ArrayList<>(plantKeys);
        plantKeyList.removeAll(plantLine);
        for (Plant p : plantKeyList) {
            String plantName = p.getClass().getSimpleName();
            if (!plantName.equals("PotatoMine") || !((PotatoMineGUI) plants.get(p)).isDeathAnimationPlaying()) {
                int y = p.getHitbox().y;
                toPaintOrder.get(y).remove(p);
                plants.remove(p);
            } else {
                plants.get(p).updateIndex();
            }
        }
    }

    private void removeZombies(ArrayList<Zombie> zombieLine) {
        Set<Zombie> zombieKeys = zombies.keySet();
        ArrayList<Zombie> zombieKeyList = new ArrayList<>(zombieKeys);
        zombieKeyList.removeAll(zombieLine);
        for (Zombie z : zombieKeyList) {
            if (!zombies.get(z).isDeathAnimationPlaying()) {
                int y = z.getHitbox().y;
                toPaintOrder.get(y).remove(z);
                zombies.remove(z);
            } else {
                zombies.get(z).updateIndex();
            }
        }
    }

    private void removeProjectiles(ArrayList<Projectile> projectileLine) {
        Set<Projectile> projectilesKeys = projectiles.keySet();
        ArrayList<Projectile> projectileKeyList = new ArrayList<>(projectilesKeys);
        projectileKeyList.removeAll(projectileLine);
        for (Projectile p : projectileKeyList) {
            if (!projectiles.get(p).isHitting()) {
                int y = p.getHitbox().y;
                toPaintOrder.get(y - 20).remove(p);
                projectiles.remove(p);
            } else {
                projectiles.get(p).updateIndex();
            }
        }
    }

    private void refreshEconomy() {
        Component[] components = layeredPane.getComponents();
        for (Component c : components) {
            String economy = c.getClass().getSimpleName();
            if (economy.equals("Sun") || economy.equals("Brain")) {
                if (((Economy) c).getTimerRunOut()) {
                    layeredPane.remove(c);
                } else {
                    ((Economy) c).moveEconomy();
                }
            }
        }
        if (level.getLawn().canSunFall()) {
            updateSunTimer();
        }
    }

    /**
     * Verifies the movement and state of the mower at a given position on the lawn.
     *
     * @param mower The mower to verify.
     * @param y     The y-coordinate associated with the mower's position.
     */
    private void mowerVerification(Mower mower, int y) {
        if (mower != null) {
            mower.move();
            MowerGUI mowerGUI = mowers.get(mower);
            mowerGUI.updateIndex();
            if (mower.getHitbox().x >= 1280) {
                toPaintOrder.get(y).remove(mower);
                level.removeMower(y);
                mowers.remove(mower);
            }
            if (mower.isActive()) {
                mowerGUI.playAudio();
                mowerGUI.move(mower.getHitbox().x, getWidth());
            }
        }
    }

    /**
     * Verifies the interaction between plants and zombies in a specific line on the lawn. This method checks
     * the properties of each plant in the plant line, determines if any zombies are within the plant's attack
     * range, and then initiates projectiles if necessary. It also handles specific behaviors for certain plant
     * types, like "PotatoMine" which explodes.
     *
     * @param plantLine  A list of plants in the current plant line.
     * @param zombieLine A list of zombies in the current zombie line.
     */
    private void plantVerification(ArrayList<Plant> plantLine, ArrayList<Zombie> zombieLine) {
        for (Plant p : plantLine) {
            String plantName = p.getClass().getSimpleName();
            PlantGUI plantGUI = plants.get(p);
            if (plantGUI != null) {
                plantGUI.updateIndex();
                if (plantName.equals("PotatoMine")) {
                    ((PotatoMine) p).cooldown();
                    if (((PotatoMine) p).isActive()) {
                        plantGUI.changeAnimation("ready");
                    }
                }
                if (plantName.equals("Evolve")) {
                    boolean state = ((Evolve) p).updateEvolveTimer();
                    if (state) {
                        ((EvolveGUI) plantGUI).changeAnimation();
                        Sound grow = new Sound("src/resources/sound/soundeffects/plantgrow.wav");
                        grow.startClip();
                    }
                }
                if (plantName.equals("Sunflower") || plantName.equals("ECIPlant")) {
                    getSunProducers(p);
                }
                if (p.getRange() > 0) {
                    for (Zombie z : zombieLine) {
                        int range = p.getHitbox().x + p.getHitbox().width + p.getRange() * 885 / 9;
                        int zombieXPosition = z.getHitbox().x;
                        if (range >= zombieXPosition) {
                            Projectile projectile = p.shoot();
                            if (projectile != null) {
                                int yPosition = projectile.getHitbox().y;
                                level.addProjectile(yPosition, projectile);
                            }
                            break;
                        }
                    }
                }
            } else {
                int width = getWidth();
                int height = getHeight();
                try {
                    PlantGUI toAdd = switch (plantName) {
                        case "Peashooter" -> new PeashooterGUI(p.getHitbox().x, p.getHitbox().y, width, height);
                        case "Sunflower" -> new SunflowerGUI(p.getHitbox().x, p.getHitbox().y, width, height);
                        case "WallNut" -> new WallNutGUI(p.getHitbox().x, p.getHitbox().y, width, height);
                        case "ECIPlant" -> new ECIPlantGUI(p.getHitbox().x, p.getHitbox().y, width, height);
                        case "PotatoMine" -> new PotatoMineGUI(p.getHitbox().x, p.getHitbox().y, width, height);
                        case "Evolve" -> new EvolveGUI(p.getHitbox().x, p.getHitbox().y, width, height);
                        default -> throw new POOBVsZombiesGUIException(POOBVsZombiesGUIException.UNKNOWN_ZOMBIE + p);
                    };
                    SwingUtilities.invokeLater(() -> {
                        plants.put(p, toAdd);
                        toPaintOrder.get(p.getHitbox().y).add(p);
                        Sound pot = new Sound("src/resources/sound/soundeffects/SFX-plant.wav");
                        pot.startClip();
                    });
                } catch (POOBVsZombiesGUIException _) {
                }
            }

        }
    }

    /**
     * Verifies the interaction between plants and zombies in a specific line on the lawn.
     * This includes checking for collisions, and managing plant actions. It also manages
     * the removal of plants and zombies based on these interactions.
     *
     * @param zombieLine      The list of zombies in the current line.
     * @param plantLine       The list of plants in the current line.
     * @param plantsToRemove  The list of plants to be removed from the game.
     * @param zombiesToRemove The list of zombies to be removed from the game.
     * @param mower           The mower in the current line.
     */
    private void zombieVerification(ArrayList<Zombie> zombieLine, ArrayList<Plant> plantLine, ArrayList<Plant> plantsToRemove, ArrayList<Zombie> zombiesToRemove, Mower mower) {
        for (Zombie z : zombieLine) {
            String zombie = z.getClass().getSimpleName();
            ZombieGUI zombieGUI = zombies.get(z);
            if (zombieGUI != null) {
                if (mower != null && z.hitboxCollision(mower.getHitbox())) {
                    if (!mower.isActive()) {
                        mower.setActive();
                    }
                    if (!zombie.equals("Brainstein")) {
                        zombieGUI.changeAnimation("mower", getWidth(), getHeight());
                    }
                    zombiesToRemove.add(z);
                    break;
                }
                if (!zombie.equals("Brainstein")) {
                    eciZombieVerification(z);
                    zombieCollision(plantLine, z, plantsToRemove, zombiesToRemove);
                    if (z.getAction() == 'm') {
                        z.move();
                        if (!Objects.equals(zombieGUI.currentAnimation, "walk")) {
                            zombieGUI.changeAnimation("walk", getWidth(), getHeight());
                        }
                        zombieGUI.move(getWidth(), z.getHitbox().x);
                    } else if (z.getAction() == 'a') {
                        if (!Objects.equals(zombieGUI.currentAnimation, "eating")) {
                            zombieGUI.changeAnimation("eating", getWidth(), getHeight());
                        }
                    }
                    zombieGUI.updateIndex();
                    if (z.getHitbox().x <= 240) {
                        loseScreen();
                        pauseTimer();
                    }
                } else {
                    getBrainProducers(z);
                }
            } else {
                int width = getWidth();
                int height = getHeight();
                try {
                    ZombieGUI toAdd = switch (zombie) {
                        case "Basic" -> new BasicGUI(z.getHitbox().x, z.getHitbox().y, width, height);
                        case "Conehead" -> new ConeheadGUI(z.getHitbox().x, z.getHitbox().y, width, height);
                        case "Buckethead" -> new BucketheadGUI(z.getHitbox().x, z.getHitbox().y, width, height);
                        case "ECIZombie" -> new ECIZombieGUI(z.getHitbox().x, z.getHitbox().y, width, height);
                        case "Brainstein" -> new BrainsteinGUI(z.getHitbox().x, z.getHitbox().y, width, height);
                        default -> throw new POOBVsZombiesGUIException(POOBVsZombiesGUIException.UNKNOWN_ZOMBIE + zombie);
                    };
                    SwingUtilities.invokeLater(() -> {
                        toPaintOrder.get(z.getHitbox().y).add(z);
                        zombies.put(z, toAdd);
                    });
                } catch (POOBVsZombiesGUIException _) {
                }
            }
        }
    }

    private void eciZombieVerification(Zombie z) {
        if (z.getClass().getSimpleName().equals("ECIZombie")) {
            Projectile p = z.shoot();
            if (p != null) {
                int yPosition = p.getHitbox().y;
                level.addProjectile(yPosition, p);
            }
        }
    }

    /**
     * Checks for collisions between zombies and plants. If a collision occurs,
     * it determines the appropriate actions for both the plant and zombie, including
     * removing the plant or zombie from the game or updating their states.
     *
     * @param z               The zombie involved in the collision.
     * @param plantsToRemove  A list to collect plants that need to be removed.
     * @param zombiesToRemove A list to collect zombies that need to be removed.
     */
    private void zombieCollision(ArrayList<Plant> plantLine, Zombie z, ArrayList<Plant> plantsToRemove, ArrayList<Zombie> zombiesToRemove) {
        boolean isAttacking = false;
        for (Plant p : plantLine) {
            String plantName = p.getClass().getSimpleName();
            if (z.hitboxCollision(p.getHitbox())) {
                if (plantName.equals("PotatoMine") && ((PotatoMine) p).isActive()) {
                    level.updateScore(z);
                    Sound explosion = new Sound("src/resources/sound/soundeffects/SFX-potato-mine.wav");
                    explosion.startClip();
                    plants.get(p).changeAnimation("explode");
                    plantsToRemove.add(p);
                    zombiesToRemove.add(z);
                } else {
                    z.setAction('a');
                    if (z.attack()) {
                        p.takeDamage(1);
                    }
                    if (p.getHealthPoints() <= 0) {
                        Sound gulp = new Sound("src/resources/sound/soundeffects/Voices-gulp.wav");
                        gulp.startClip();
                        level.updateScore(p);
                        plantsToRemove.add(p);
                        z.setAction('m');
                    }
                }
                isAttacking = true;
                break;
            }
        }
        if (!isAttacking) {
            z.setAction('m');
        }
    }

    /**
     * Verifies the projectiles in the game, checking for collisions with zombies. If a projectile
     * collides with a zombie, it applies damage to the zombie and removes the projectile if necessary.
     * It also updates the list of projectiles to remove those that have gone off-screen.
     *
     * @param projectileLine      The list of projectiles in the current line.
     * @param zombieLine          The list of zombies in the current line.
     * @param zombiesToRemove     The list of zombies to remove from the game.
     * @param projectilesToRemove The list of projectiles to remove from the game.
     */
    private void projectilesVerification(ArrayList<Projectile> projectileLine, ArrayList<Zombie> zombieLine,
                                         ArrayList<Zombie> zombiesToRemove, ArrayList<Projectile> projectilesToRemove,
                                         ArrayList<Plant> plantLine, ArrayList<Plant> plantsToRemove) {
        for (Projectile p : projectileLine) {
            String projectileName = p.getClass().getSimpleName();
            if (!projectiles.containsKey(p)) {
                switch (projectileName) {
                    case "Leave" -> {
                        LeaveGUI leaveGUI = new LeaveGUI(p.getHitbox().x, p.getHitbox().y, getWidth(), getHeight());
                        projectiles.put(p, leaveGUI);
                    }
                    case "POOmBas" -> {
                        POOmBasGUI poOmBasGUI = new POOmBasGUI(p.getHitbox().x, p.getHitbox().y, getWidth(), getHeight());
                        projectiles.put(p, poOmBasGUI);
                    }
                }
                toPaintOrder.get(p.getHitbox().y - 20).add(p);
                Sound throwSound = new Sound("src/resources/sound/soundeffects/SFX-throw.wav");
                throwSound.startClip();
            }
            p.move();
            projectiles.get(p).move(getWidth(), p.getHitbox().x);
            if (projectileName.equals("Leave")) {
                leaveVerification(p, projectilesToRemove, zombieLine, zombiesToRemove);
            } else if (projectileName.equals("POOmBas")) {
                poomBasVerification(p, projectilesToRemove, plantLine, plantsToRemove);
            }
        }
    }

    private void leaveVerification(Projectile p, ArrayList<Projectile> projectilesToRemove, ArrayList<Zombie> zombieLine, ArrayList<Zombie> zombiesToRemove) {
        if (p.getHitbox().x >= 1280) {
            projectilesToRemove.add(p);
        }
        for (Zombie z : zombieLine) {
            if (p.hitboxCollision(z.getHitbox())) {
                projectilesToRemove.add(p);
                projectiles.get(p).hit();
                z.takeDamage(p.getDamage());
                projectileHit(z);
                String zombie = z.getClass().getSimpleName();
                int health = z.getHealthPoints();
                if (health <= 0) {
                    level.updateScore(z);
                    zombiesToRemove.add(z);
                    if (!zombie.equals("Brainstein")) {
                        zombies.get(z).changeAnimation("dying", getWidth(), getHeight());
                    }
                }
                break;
            }
        }
    }

    private void poomBasVerification(Projectile pr, ArrayList<Projectile> projectilesToRemove, ArrayList<Plant> plantLine, ArrayList<Plant> plantsToRemove) {
        if (pr.getHitbox().x <= 0) {
            projectilesToRemove.add(pr);
        }
        for (Plant p : plantLine) {
            if (pr.hitboxCollision(p.getHitbox())) {
                projectilesToRemove.add(pr);
                projectiles.get(pr).hit();
                p.takeDamage(pr.getDamage());
                projectileHit(p);
                if (p.getHealthPoints() <= 0) {
                    level.updateScore(p);
                    plantsToRemove.add(p);
                }
                break;
            }
        }
    }

    private void projectileHit(Hitbox h) {
        String entityName = h.getClass().getSimpleName();
        Sound hit;
        if (entityName.equals("Conehead") && ((Conehead) h).getHealthPoints() > 10) {
            hit = new Sound("src/resources/sound/soundeffects/SFX-plastichit.wav");
        } else if (entityName.equals("Buckethead") && ((Buckethead) h).getHealthPoints() > 10) {
            hit = new Sound("src/resources/sound/soundeffects/SFX-shieldhit.wav");
        } else {
            if (entityName.equals("Conehead")) {
                ((ConeheadGUI) zombies.get((Zombie) h)).lostCone();
            } else if (entityName.equals("Buckethead")) {
                ((BucketheadGUI) zombies.get((Zombie) h)).lostBucket();
            }
            hit = new Sound("src/resources/sound/soundeffects/SFX-splat.wav");
        }
        hit.startClip();
    }

    protected abstract void refreshSubclasses();

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        paintLawn(g2d);
        paintSunBank(g2d);
        for (int i = 0; i < 5; i++) {
            int y = (i * 113) + 98 + (32 / 3);
            ArrayList<Hitbox> hitboxes = toPaintOrder.get(y);
            for (Hitbox h : hitboxes) {
                String hitboxType = h.getClass().getSimpleName();
                switch (hitboxType) {
                    case "Mower" -> paintMowers(g2d, (Mower) h);
                    case "Sunflower", "Peashooter", "WallNut", "ECIPlant", "PotatoMine", "Evolve" -> paintPlants(g2d, (Plant) h);
                    case "Basic", "Conehead", "Buckethead", "ECIZombie", "Brainstein" -> paintZombies(g2d, (Zombie) h);
                    case "Leave", "POOmBas" -> paintProjectiles(g2d, (Projectile) h);
                }
            }
        }
        paintShovelBank(g2d);
        g2d.setColor(Color.WHITE);
        paintTimer(g2d);
        paintProgressBar(g2d);
    }

    private void paintLawn(Graphics2D g2d) {
        int backgroundWidth = lawn.getWidth(null);
        int backgroundHeight = lawn.getHeight(null);
        BufferedImage lawnSubimage = lawn.getSubimage(0, 0, backgroundWidth * 16 / 21, backgroundHeight);
        g2d.drawImage(lawnSubimage, 0, 0, getWidth(), getHeight(), null);
    }

    private void paintPlants(Graphics2D g2d, Plant p) {
        Image plantImage = plants.get(p).getImage();
        int plantWidth = plants.get(p).getWidth();
        int plantHeight = plants.get(p).getHeight();
        int xPosition = plants.get(p).xPosition;
        int yPosition = plants.get(p).yPosition;
        g2d.drawImage(plantImage, xPosition, yPosition, plantWidth, plantHeight, null);
        if (showHitboxes) {
            Rectangle rec = p.getHitbox();
            g2d.setColor(p.getColor());
            g2d.fillRect(rec.x, rec.y, rec.width, rec.height);
        }
    }

    private void paintZombies(Graphics2D g2d, Zombie z) {
        Image zombieImage = zombies.get(z).getImage();
        int zombieWidth = zombies.get(z).getWidth();
        int zombieHeight = zombies.get(z).getHeight();
        int xPosition = zombies.get(z).xPosition;
        int yPosition = zombies.get(z).yPosition;
        g2d.drawImage(zombieImage, xPosition, yPosition, zombieWidth, zombieHeight, null);
        if (showHitboxes) {
            Rectangle rec = z.getHitbox();
            g2d.setColor(z.getColor());
            g2d.fillRect(rec.x, rec.y, rec.width, rec.height);
        }
    }

    private void paintProjectiles(Graphics2D g2d, Projectile p) {
        Image projectileImage = projectiles.get(p).getImage();
        int projectileWidth = projectiles.get(p).getWidth();
        int projectileHeight = projectiles.get(p).getHeight();
        int xPosition = projectiles.get(p).xPosition;
        int yPosition = projectiles.get(p).yPosition;
        g2d.drawImage(projectileImage, xPosition, yPosition, projectileWidth, projectileHeight, null);
        if (showHitboxes) {
            Rectangle rec = p.getHitbox();
            g2d.setColor(p.getColor());
            g2d.fillRect(rec.x, rec.y, rec.width, rec.height);
        }
    }

    private void paintMowers(Graphics2D g2d, Mower m) {
        Image mowerImage = mowers.get(m).getImage();
        int mowerWidth = mowers.get(m).getWidth();
        int mowerHeight = mowers.get(m).getHeight();
        int xPosition = mowers.get(m).xPosition;
        int yPosition = mowers.get(m).yPosition;
        g2d.drawImage(mowerImage, xPosition, yPosition, mowerWidth, mowerHeight, null);
        if (showHitboxes) {
            Rectangle rec = m.getHitbox();
            g2d.setColor(m.getColor());
            g2d.fillRect(rec.x, rec.y, rec.width, rec.height);
        }
    }

    private void paintShovelBank(Graphics2D g2d) {
        Image shovelBank = shovel.getShovelBank();
        int[] shovelBankPosition = shovel.getShovelBankPosition();
        int[] shovelBankSize = shovel.getShovelBankSize();
        g2d.drawImage(shovelBank, shovelBankPosition[0], shovelBankPosition[1], shovelBankSize[0], shovelBankSize[1], null);
    }

    private void paintSunBank(Graphics2D g2d) {
        // Sun bank
        int sunBankYPosition = 5 * getHeight() / 720;
        g2d.drawImage(sunBank, 0, sunBankYPosition, sunBank.getWidth(null), sunBank.getHeight(null), null);
        // Sun collected
        float customSize = (float) (getHeight() * 26) / 720;
        Font customFont = POOBVsZombiesGUI.cafeteriaBold.deriveFont(customSize);
        g2d.setFont(customFont);
        g2d.setColor(Color.BLACK);
        int textXPosition = getWidth() * 65 / 1280;
        int textYPosition = getHeight() * 43 / 720;
        g2d.drawString(String.valueOf(level.getSunCollected()), textXPosition, textYPosition);
    }

    private void paintTimer(Graphics2D g2d) {
        if (isTimerVisible) {
            // Time Bank
            g2d.drawImage(levelTimer.timerBank, levelTimer.timerBankXPosition, levelTimer.timerBankYPosition,
                    levelTimer.timerBankWidth, levelTimer.timerBankHeight, null);
            // Elapsed time
            g2d.drawString(levelTimer.getTimeElapsed(), levelTimer.timeXPosition, levelTimer.timeYPosition);
        }
    }

    public abstract void paintProgressBar(Graphics2D g2d);

    private static class PauseMenu extends JPanel {
        private final JLayeredPane menuPane;
        private JButton backToGame;
        private JButton mainMenu;
        private JButton restartLevel;

        public PauseMenu(int width, int height) {
            menuPane = new JLayeredPane();
            menuPane.setLayout(null);
            int panelHeight = height * 498 / 720;
            int panelWidth = width * 423 / 1280;
            int xPosition = width * 429 / 1280;
            int yPosition = height * 111 / 720;
            SwingUtilities.invokeLater(() -> {
                setLayout(new BorderLayout());
                add(menuPane, BorderLayout.CENTER);
                setOpaque(false);
                setBounds(xPosition, yPosition, panelWidth, panelHeight);
                prepareBackground();
                setBackJButton();
                setMenuJButton();
                setRestartJButton();
            });
        }

        private void prepareBackground() {
            Image pause = new ImageIcon("src/resources/imag/Level Interface/menuBack.png").getImage();
            ImageIcon pauseScaled = new ImageIcon(pause.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
            JLabel pauseLabel = new JLabel();
            pauseLabel.setBounds(0, 0, getWidth(), getHeight());
            pauseLabel.setIcon(pauseScaled);
            menuPane.add(pauseLabel, JLayeredPane.DEFAULT_LAYER);
        }

        private void setMenuJButton() {
            Image menuImage = new ImageIcon("src/resources/imag/Level Interface/options_mainMenuButton.png").getImage();
            Image menuImageDown = new ImageIcon("src/resources/imag/Level Interface/options_mainMenuButtonDown.png").getImage();
            mainMenu = new JButton();
            int newWidth = getWidth() * 210 / 423;
            int newHeight = getHeight() * 48 / 498;
            ImageIcon menuScaled = new ImageIcon(menuImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
            ImageIcon menuDownScaled = new ImageIcon(menuImageDown.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
            mainMenu.setBounds(getWidth() * 106 / 423, getHeight() * 312 / 498, newWidth, newHeight);
            mainMenu.setIcon(menuScaled);
            mainMenu.setDisabledIcon(menuScaled);
            mainMenu.setRolloverIcon(menuDownScaled);
            mainMenu.setFocusPainted(false);
            mainMenu.setBorderPainted(false);
            mainMenu.setContentAreaFilled(false);
            menuPane.add(mainMenu, JLayeredPane.PALETTE_LAYER);
        }

        private void setRestartJButton() {
            Image restartImage = new ImageIcon("src/resources/imag/Level Interface/options_restartLevelButton.png").getImage();
            Image restartImageDown = new ImageIcon("src/resources/imag/Level Interface/options_restartLevelButtonDown.png").getImage();
            restartLevel = new JButton();
            int newWidth = getWidth() * 210 / 423;
            int newHeight = getHeight() * 48 / 498;
            ImageIcon restartScaled = new ImageIcon(restartImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
            ImageIcon restartDownScaled = new ImageIcon(restartImageDown.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
            restartLevel.setBounds(getWidth() * 106 / 423, getHeight() * 258 / 498, newWidth, newHeight);
            restartLevel.setIcon(restartScaled);
            restartLevel.setDisabledIcon(restartScaled);
            restartLevel.setRolloverIcon(restartDownScaled);
            restartLevel.setFocusPainted(false);
            restartLevel.setBorderPainted(false);
            restartLevel.setContentAreaFilled(false);
            menuPane.add(restartLevel, JLayeredPane.PALETTE_LAYER);
        }

        private void setBackJButton() {
            Image backToGameImage = new ImageIcon("src/resources/imag/Level Interface/options_backToGameButton.png").getImage();
            Image backToGameDownImage = new ImageIcon("src/resources/imag/Level Interface/options_backToGameButtonDown.png").getImage();
            backToGame = new JButton();
            int newWidth = getWidth() * 360 / 423;
            int newHeight = getHeight() * 100 / 498;
            ImageIcon backToGameScaled = new ImageIcon(backToGameImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
            ImageIcon backToGameDownScaled = new ImageIcon(backToGameDownImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
            backToGame.setBounds(getWidth() * 31 / 423, getHeight() * 382 / 498, newWidth, newHeight);
            backToGame.setIcon(backToGameScaled);
            backToGame.setDisabledIcon(backToGameScaled);
            backToGame.setRolloverIcon(backToGameDownScaled);
            backToGame.setFocusPainted(false);
            backToGame.setBorderPainted(false);
            backToGame.setContentAreaFilled(false);
            menuPane.add(backToGame, JLayeredPane.PALETTE_LAYER);
        }
    }

    private static class SeedChooserPlants extends JPanel {
        private final JLayeredPane seedChooserPane;
        private JPanel seedChooser;
        private JButton letsRock;

        public SeedChooserPlants(int width, int height) {
            seedChooserPane = new JLayeredPane();
            seedChooserPane.setLayout(null);
            int panelHeight = height * 517 / 720;
            int panelWidth = width * 470 / 1280;
            int xPosition = width * 169 / 1280;
            int yPosition = height * 101 / 720;
            SwingUtilities.invokeLater(() -> {
                setLayout(new BorderLayout());
                add(seedChooserPane, BorderLayout.CENTER);
                setOpaque(false);
                setBounds(xPosition, yPosition, panelWidth, panelHeight);
                prepareBackground();
                setJButton();
                setJButtons();
            });
        }

        private void prepareBackground() {
            Image chooser = new ImageIcon("src/resources/imag/Level Interface/SeedChooser_Background.png").getImage();
            ImageIcon chooserScaled = new ImageIcon(chooser.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
            JLabel chooserLabel = new JLabel();
            chooserLabel.setBounds(0, 0, getWidth(), getHeight());
            chooserLabel.setIcon(chooserScaled);
            seedChooserPane.add(chooserLabel, JLayeredPane.DEFAULT_LAYER);
        }

        private void setJButtons() {
            seedChooser = new JPanel();
            int height = getHeight() * 169 / 517;
            int width = getWidth() * 384 / 470;
            int xPosition = getWidth() * 53 / 470;
            int yPosition = getHeight() * 51 / 517;
            seedChooser.setBounds(xPosition, yPosition, width, height);
            seedChooser.setLayout(new GridLayout(2, 3, getWidth() * 26 / 517, getHeight() * 22 / 470));
            String[] plantPackets = {"Sunflower", "Peashooter", "WallNut", "PotatoMine", "ECIPlant", "Evolve"};
            for (String p : plantPackets) {
                JButton button = new JButton();
                button.setActionCommand(p);
                button.setFocusPainted(false);
                button.setBorderPainted(false);
                button.setContentAreaFilled(false);
                Image plantImage = new ImageIcon("src/resources/imag/Plants/Seed Packets/" + p + "_Seed_Packet.png").getImage();
                Image disabledImage = new ImageIcon("src/resources/imag/Plants/Seed Packets/" + p + "_Seed_Packet_Disabled.png").getImage();
                int seedHeight = getHeight() * 70 / 517;
                int seedWidth = getWidth() * 109 / 470;
                ImageIcon plantIcon = new ImageIcon(plantImage.getScaledInstance(seedWidth, seedHeight, Image.SCALE_SMOOTH));
                ImageIcon disabledIcon = new ImageIcon(disabledImage.getScaledInstance(seedWidth, seedHeight, Image.SCALE_SMOOTH));
                button.setIcon(plantIcon);
                button.setDisabledIcon(disabledIcon);
                button.setEnabled(true);
                seedChooser.add(button);
            }
            seedChooser.setOpaque(false);
            seedChooserPane.add(seedChooser, JLayeredPane.PALETTE_LAYER);
        }

        private void setJButton() {
            Image letsRockImage = new ImageIcon("src/resources/imag/Level Interface/SeedChooser_Button.png").getImage();
            Image letsRockDisabledImage = new ImageIcon("src/resources/imag/Level Interface/SeedChooser_ButtonDown.png").getImage();
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
            seedChooserPane.add(letsRock, JLayeredPane.PALETTE_LAYER);
        }
    }

    private static class Shovel extends JButton {
        private Image shovelBank;
        private int[] shovelBankPosition;
        private int[] shovelBankSize;

        public Shovel(int width, int height) {
            Image shovelImage = new ImageIcon("src/resources/imag/Level Interface/Shovel_hi_res.png").getImage();
            ImageIcon shovel = new ImageIcon(shovelImage.getScaledInstance(width, height, Image.SCALE_SMOOTH));
            SwingUtilities.invokeLater(() -> {
                setIcon(shovel);
                setDisabledIcon(shovel);
                setFocusPainted(false);
                setBorderPainted(false);
                setContentAreaFilled(false);
            });
        }

        private void prepareShovelBank(int width, int height, int xPosition, int yPosition) {
            shovelBank = new ImageIcon("src/resources/imag/Level Interface/ShovelBank.png").getImage();
            shovelBankPosition = new int[]{xPosition, yPosition};
            shovelBankSize = new int[]{width, height};
        }

        private Image getShovelBank() {
            return shovelBank;
        }

        private int[] getShovelBankPosition() {
            return shovelBankPosition;
        }

        private int[] getShovelBankSize() {
            return shovelBankSize;
        }
    }

    private static class LevelTimer {
        private final Image timerBank;
        private final int timerBankXPosition;
        private final int timerBankYPosition;
        private final int timerBankWidth;
        private final int timerBankHeight;
        private int timeXPosition;
        private int timeYPosition;

        public LevelTimer(int width, int height, ProgressBar progressBar) {
            Image time = new ImageIcon("src/resources/imag/Level Interface/Timer.png").getImage();
            timerBankWidth = width * 111 / 1280;
            timerBankHeight = height * 36 / 720;
            timerBank = time.getScaledInstance(timerBankWidth, timerBankHeight, Image.SCALE_SMOOTH);
            int progressBarMiddle = progressBar.getXPosition() + (progressBar.getWidth() / 2);
            int timerBankMiddle = timerBankWidth / 2;
            timerBankYPosition = progressBar.getYPosition() + progressBar.getHeight();
            timerBankXPosition = progressBarMiddle - timerBankMiddle;
            SwingUtilities.invokeLater(() -> getTimePosition(width, height, progressBar));
        }

        public void getTimePosition(int width, int height, ProgressBar progressBar) {
            BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            float customSize = (float) (height * 26) / 720;
            Font customFont = POOBVsZombiesGUI.cafeteriaBold.deriveFont(customSize);
            FontMetrics fm = g2d.getFontMetrics(customFont);
            int timeHeight = fm.getHeight();
            int timeBankHeight = progressBar.getYPosition() + progressBar.getHeight() + (this.timerBankHeight / 2) - (6 * height / 720);
            int timeHeightMiddle = timeHeight / 2;
            timeYPosition = timeHeight + timeBankHeight - timeHeightMiddle;
            String timeElapsed = getTimeElapsed();
            int timeMiddle = fm.stringWidth(timeElapsed) / 2;
            int progressBarMiddle = progressBar.getXPosition() + (progressBar.getWidth() / 2);
            timeXPosition = progressBarMiddle - timeMiddle + 6 * width / 1280;
        }

        public String getTimeElapsed() {
            int totalSeconds = (int) level.getTime() / 1000;
            int minutes = totalSeconds / 60;
            int seconds = totalSeconds % 60;
            return String.format("%02d:%02d", minutes, seconds);
        }
    }
}