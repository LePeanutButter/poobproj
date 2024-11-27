package presentation;
import domain.POOBVsZombies;
import java.awt.*;
import java.io.*;
import javax.swing.*;

public class POOBVsZombiesGUI {
    private static JFrame frame;
    public static POOBVsZombies poobVsZombies;
    public static Dimension dimensions;
    public static Font briannesHand;
    public static Font cafeteriaBold;

    private POOBVsZombiesGUI() {
        prepareElements();
        poobVsZombies = new POOBVsZombies();
        try {
            briannesHand = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/imag/Interface/Fonts/Brianne_s_hand.ttf")).deriveFont(24f);
            cafeteriaBold = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/imag/Interface/Fonts/Cafeteria Bold.otf")).deriveFont(24f);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private void prepareElements() {
        prepareFrame();
        prepareCursor();
        frame.add(new LoadingScreen(dimensions), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

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

    private void prepareCursor() {
        Image cursorImage = new ImageIcon("src/resources/imag/Interface/cursor.png").getImage();
        Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "custom cursor");
        frame.setCursor(customCursor);
    }

    public static void switchToPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(POOBVsZombiesGUI::new);
    }
}