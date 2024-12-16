package presentation;

import domain.POOBVsZombies;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

/**
 * The POOBVsZombiesGUI class is responsible for setting up the graphical user interface (GUI)
 * for the "POOB vs. Zombies" game. It handles the creation and configuration of the main game window,
 * custom cursor, fonts, and game elements such as loading screens and sound effects.
 * It also provides methods to switch between different panels in the game interface.
 * 
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12, 2024
 */
public class POOBVsZombiesGUI {
    public static JFrame frame;
    public static POOBVsZombies poobVsZombies;
    public static Dimension dimensions;
    public static Font briannesHand;
    public static Font cafeteriaBold;
    public static Font dwarvenStonecraft;

    /**
     * Constructs the POOBVsZombiesGUI object and prepares the game elements.
     * This includes initializing fonts, setting up the main frame, and preparing the cursor.
     */
    public POOBVsZombiesGUI() {
        prepareElements();
        poobVsZombies = new POOBVsZombies();
        try {
            briannesHand = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/imag/Interface/Fonts/Brianne_s_hand.ttf"));
            cafeteriaBold = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/imag/Interface/Fonts/Cafeteria Bold.otf"));
            dwarvenStonecraft = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/imag/Interface/Fonts/DWARVESC.ttf"));
        } catch (IOException | FontFormatException _) {
        }
    }

    /*
     * Prepares the game elements, including setting up the frame, cursor, and loading screen.
     * The loading screen is displayed while the game is initializing.
     */
    private void prepareElements() {
        prepareFrame();
        prepareCursor();
        LoadingScreen loadingScreen = new LoadingScreen();
        loadingScreen.setPreferredSize(dimensions);
        frame.add(loadingScreen, BorderLayout.CENTER);
        frame.pack();
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
            loadingScreen.startAnimation();
        });
    }

    /*
     * Prepares the main game window JFrame, including setting its size, title, and icon.
     */
    private void prepareFrame() {
        DisplayMode displayDimensions = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDisplayMode();
        int monitorWidth = displayDimensions.getWidth();
        int frameWidth = 2 * monitorWidth / 3;
        int frameHeight = 9 * frameWidth / 16;
        frame = new JFrame();
        frame.setTitle("POOB vs. Zombies");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon("src/resources/imag/Menu/Plants-Vs-Zombies.png").getImage());
        frame.setLayout(new BorderLayout());
        dimensions = new Dimension(frame.getWidth(), frame.getHeight());
    }

    /*
     * Prepares the custom cursor for the game. The cursor is replaced with a custom image.
     */
    private void prepareCursor() {
        Image cursorImage = new ImageIcon("src/resources/imag/Interface/cursor.png").getImage();
        Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "custom cursor");
        frame.setCursor(customCursor);
    }

    /**
     * Switches to the specified panel in the game interface.
     * This method is used to change the main content of the game window.
     * 
     * @param panel The JPanel to display in the game window.
     */
    public static void switchToPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Plays the buzzer sound effect, this will always be played whenever the user does an invalid action.
     */
    public static void buzzerSound() {
        Sound buzzer = new Sound("src/resources/sound/soundeffects/SFX-buzzer.wav");
        buzzer.startClip();
    }

    /**
     * The main method that runs the POOBVsZombiesGUI application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(POOBVsZombiesGUI::new);
    }
}