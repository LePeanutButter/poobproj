package presentation;

import domain.Level;
import domain.PvM;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.util.Random;

public abstract class LevelGUI extends JPanel {
    protected Level level;
    private JButton menu;
    private JButton speedChange;
    private BufferedImage lawn;
    private JPanel board;
    protected String currentAction = "";
    private HashMap<String, PlantGUI> plantsGUI;
    private List<Sun> sunList;
    private Image sunBank;
    private Timer sunTimer;
    private Sound backgroundMusic;


    public LevelGUI(Level level, String lawn, Dimension dimension) {
        this.level = level;
        this.plantsGUI = new HashMap<>();
        this.sunList = new ArrayList<>();
        setPreferredSize(dimension);
        setLayout(null);
        SwingUtilities.invokeLater(() -> {
            prepareBoard();
            preparePlantSlot();
            getLawn(lawn);
            prepareSunBank();
        });
    }

    public LevelGUI(Level level) {
        this.level = level;
        getLawn(level.getLawn());
    }

    private void prepareBoard() {
        int rows = 5;
        int columns = 9;
        board = new JPanel();
        int width = getWidth() * 885 / 1280;
        int height = getHeight() * 595 / 720;
        int xPosition =  getWidth() * 303 / 1280;
        int yPosition = getHeight() * 89 / 720;
        board.setBounds(xPosition, yPosition, width, height);
        board.setLayout(new GridLayout(rows, columns));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                JButton button = getBoardButton(i, j);
                board.add(button);
                button.setFocusPainted(false);
                button.setBorderPainted(false);
                button.setContentAreaFilled(false);
            }
        }
        board.setOpaque(false);
        add(board);
    }

    private JButton getBoardButton(int row, int column) {
        JButton button = new JButton();
        button.addActionListener(_ -> {
            if (!Objects.equals(currentAction, "remove")) {
                plant(row, column);
            } else {
                removePlant(row, column);
            }
            changeBoardState(false);
            currentAction = "";
            button.setVisible(false);
        });
        button.setEnabled(false);
        button.setVisible(false);
        return button;
    }

    protected void changeBoardState(boolean state) {
        Component[] components = board.getComponents();
        for (Component c: components) {
            if (c instanceof JButton) {
                c.setEnabled(state);
                c.setVisible(state);
            }
        }
    }

    private void plant(int row, int column) {
        try {
            level.placePlant(row, column, currentAction);
            PlantGUI plant = PlantFactory.createClassInstance(currentAction, row, column, getWidth(), getHeight());
            plantsGUI.put(Arrays.toString(new int[]{row, column}), plant);
            if (plant instanceof SunflowerGUI) {
                Timer sunflower  = new Timer(10000, _ -> {
                    int x = plant.getXPosition() + getWidth() * 16 / 1280;
                    int y = plant.getYPosition() - getHeight() * 16 / 720;
                    Sun sun = new Sun(x, plant.getYPosition() + getHeight() * 8 / 720, 25, getWidth(), getHeight());
                    sun.setSunflower(x, y);
                    sun.addActionListener(_ -> {
                        level.collectSun(sun.getSunSize());
                        sunList.remove(sun);
                        remove(sun);
                        new Sound(false, "src/resources/sound/soundeffects/SFX-points.wav");
                    });
                    sunList.add(sun);
                    add(sun);
                });
                sunflower.start();
            }
            new Sound(false, "src/resources/sound/soundeffects/SFX-plant.wav");
        } catch (Exception _) {
            buzzerSound();
        }
    }

    private void removePlant(int row, int column) {
        try {
            ((PvM) level).removePlant(row, column);
            plantsGUI.remove(Arrays.toString(new int[] {row,column}));
            new Sound(false, "src/resources/sound/soundeffects/SFX-shovel.wav");
        } catch (Exception _) {
            buzzerSound();
        }
        PvMGUI.setShovelVisibility(true);
    }

    private void buzzerSound() {
        new Sound(false, "src/resources/sound/soundeffects/SFX-buzzer.wav");
    }

    public void readySetPlant() {
        try {
            new Sound(false, "src/resources/sound/soundeffects/SFX-readysetplant.wav");
            Timer timer = new Timer(2000, _ -> {
                backgroundMusic = new Sound(true, "src/resources/sound/soundtracks/Extraction-B-loop.wav");
            });
            timer.setRepeats(false);
            timer.start();
        } catch (Exception _) {
        }
    }

    private void prepareSunBank() {
        Image originalSunBank = new ImageIcon("src/resources/imag/Level Interface/SunBank.png").getImage();
        int width = getWidth() * 141 / 1280;
        int height = getHeight() * 64 / 720;
        sunBank = originalSunBank.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    private void preparePlantSlot() {
        JPanel plantSlot = new JPanel();
        int height = getHeight() * 355 / 720;
        int width = getWidth() * 112 / 1280;
        int xPosition =  getWidth() * 4 / 1280;
        int yPosition = getHeight() * 94 / 720;
        plantSlot.setBounds(xPosition, yPosition, width, height);
        plantSlot.setLayout(new GridLayout(5, 1, getWidth() * 2 / 1280, getHeight() * 2 / 720));
        String[] plants = {"Sunflower", "Peashooter", "WallNut", "PotatoMine", "ECIPlant"};
        for (String p: plants) {
            JButton button = new JButton();
            button.addActionListener(_ -> {
                if (!Objects.equals(currentAction, "")) {
                    PvMGUI.setShovelVisibility(true);
                }
                changeBoardState(true);
                currentAction = p;
            });
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            Image plantImage = new ImageIcon("src/resources/imag/Plants/Seed Packets/"+p+"_Seed_Packet.png").getImage();
            int seedHeight = plantImage.getHeight(null) * width / plantImage.getWidth(null);
            ImageIcon plantIcon = new ImageIcon(plantImage.getScaledInstance(width, seedHeight, Image.SCALE_SMOOTH));
            button.setIcon(plantIcon);
            plantSlot.add(button);
        }
        plantSlot.setOpaque(false);
        add(plantSlot);
    }

    private void getLawn(String lawnName) {
        switch (lawnName) {
            case ("Day"):
                try {
                    lawn = ImageIO.read(new File("src/resources/imag/Level Interface/background1.png"));
                } catch (Exception _) {
                }
                break;
            default:
                break;
        }
    }

    protected void setSunTimer() {
        sunTimer = new Timer(10000, _ -> {
            Sun sun = getSun();
            sun.setSky(getHeight());
            sun.addActionListener(_ -> {
                level.collectSun(sun.getSunSize());
                sunList.remove(sun);
                remove(sun);
                new Sound(false, "src/resources/sound/soundeffects/SFX-points.wav");
            });
            sunList.add(sun);
            add(sun);
        });
        sunTimer.start();
    }

    private Sun getSun() {
        Random random = new Random();
        int minXPosition = getWidth() * 290 / 1280;
        int maxXPosition = (getWidth() * 964 / 1280);
        int minYPosition = getHeight() * 202 / 720;
        int maxYPosition = getHeight() * 685 / 720;
        int randomXPosition = random.nextInt((maxXPosition - minXPosition) + 1) + minXPosition;
        int randomYPosition = random.nextInt((maxYPosition - minYPosition) + 1) + minYPosition;
        return new Sun(randomXPosition, randomYPosition, 25, getWidth(), getHeight());
    }

    public void refresh(){
        for (Sun s: sunList) {
            s.moveSun();
        }
        repaint();
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setFont(POOBVsZombiesGUI.cafeteriaBold);
        g2d.setColor(Color.WHITE);

        // Lawn
        int backgroundWidth = lawn.getWidth(null);
        int backgroundHeight = lawn.getHeight(null);
        BufferedImage lawnSubimage = lawn.getSubimage(0, 0, backgroundWidth * 16 / 21, backgroundHeight);
        g2d.drawImage(lawnSubimage, 0, 0, getWidth(), getHeight(),null);

        //Plants
        for (PlantGUI plantGUI : plantsGUI.values()) {
            Image plantImage = plantGUI.getImage();
            int plantWidth = plantGUI.getWidth();
            int plantHeight = plantGUI.getHeight();
            int xPosition = plantGUI.getXPosition();
            int yPosition = plantGUI.getYPosition();
            g2d.drawImage(plantImage, xPosition, yPosition, plantWidth, plantHeight,null);
            plantGUI.updateIndex();
        }

        // Sun bank
        int sunBankXPosition = getWidth() * 14 / 1280;
        g2d.drawImage(sunBank, sunBankXPosition, 0, sunBank.getWidth(null), sunBank.getHeight(null), null);

        // Sun collected
        int textXPosition = getWidth() * 86 / 1280;
        int textYPosition = getHeight() * 38 / 720;
        g2d.drawString(String.valueOf(level.getSunCollected()), textXPosition, textYPosition);
    }

    private static class PlantFactory {
        public static PlantGUI createClassInstance(String className, int row, int column, int width, int height) {
            int newColumn = (column * width * 103 / 1280) + (width * 300 / 1280) -  (6 * column * width / 1280);
            int newRow = (row * height * 113 / 720) + (height * 98 / 720) + (6 * width / 720);
            int newWidth = width * 101 / 1280;
            int newHeight = height * 101 / 720;
            return switch (className) {
                case "Peashooter" -> new PeashooterGUI(newColumn, newRow, newWidth, newHeight);
                case "Sunflower" -> new SunflowerGUI(newColumn, newRow, newWidth, newHeight);
                case "WallNut" -> new WallNutGUI(newColumn, newRow, newWidth, newHeight);
                default -> throw new IllegalArgumentException("Unknown class: " + className);
            };
        }
    }
}
