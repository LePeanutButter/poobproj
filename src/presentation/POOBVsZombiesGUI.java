package presentation;
import domain.POOBVsZombies;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import javax.swing.*;

public class POOBVsZombiesGUI {
    private static POOBVsZombies POOBVsZombies;
    private JFrame frame;
    private static CardLayout cardLayout;
    private static JPanel cards;
    private static JPanel currentPanel;
    private static HashMap<String, JPanel> panels;
    private Dimension dimensions;
    public static Font briannesHand;

    public POOBVsZombiesGUI() {
        prepareElements();
        prepareActions();
        POOBVsZombies = new POOBVsZombies();
        try {
            briannesHand = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/imag/Interface/Fonts/Brianne_s_hand.ttf")).deriveFont(24f);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private void prepareElements() {
        prepareFrame();
        prepareCursor();
        panels = new HashMap<>();
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        assert frame != null;
        frame.add(cards, BorderLayout.CENTER);
        prepareLoadingScreen();
        prepareMainMenu();
        frame.pack();
        frame.setVisible(true);
    }

    private void prepareFrame() {
        DisplayMode displayDimensions = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDisplayMode();
        int monitorWidth = displayDimensions.getWidth();
        int frameHeight = 3 * monitorWidth / 8;
        int frameWidth = 2 * monitorWidth / 3;
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

    private void prepareCursor() {
        Image cursorImage = new ImageIcon("src/resources/imag/Interface/cursor.png").getImage();
        Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "custom cursor");
        frame.setCursor(customCursor);
    }

    private void prepareLoadingScreen() {
        LoadingScreen loadingScreen = new LoadingScreen();
        loadingScreen.setPreferredSize(dimensions);
        cards.add(loadingScreen, "LoadingScreen");
        panels.put("LoadingScreen", loadingScreen);
        currentPanel = loadingScreen;
        SwingUtilities.invokeLater(() -> {
            loadingScreen.prepareStartButton();
            loadingScreen.prepareActions();
            loadingScreen.startAudio();
            loadingScreen.startAnimation();
        });
    }

    private void prepareMainMenu() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.setPreferredSize(dimensions);
        cards.add(mainMenu, "MainMenu");
        panels.put("MainMenu", mainMenu);
        SwingUtilities.invokeLater(mainMenu::prepareElements);
    }

    public static void switchToPanel(String panelName) {
        stopAudio();
        cardLayout.show(cards, panelName);
        JPanel newPanel = panels.get(panelName);
        if (newPanel instanceof MainMenu) {
            SwingUtilities.invokeLater(((MainMenu) newPanel):: startAudio);
        }
        currentPanel = newPanel;
    }

    private static void stopAudio() {
        if (currentPanel instanceof LoadingScreen) {
            ((LoadingScreen) currentPanel).stopAudio();
        } else if (currentPanel instanceof MainMenu) {
            ((MainMenu) currentPanel).stopAudio();
        }
    }

    private void prepareActions() {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(POOBVsZombiesGUI::new);
    }
}