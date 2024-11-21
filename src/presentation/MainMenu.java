package presentation;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JPanel {
    Image backgroundImage;
    Image cartelImage;
    JButton quit;
    JButton pvp;
    Clip clip;

    public MainMenu() {
        setLayout(null);
        backgroundImage = new ImageIcon("src/resources/imag/Menu/mainMenu.png").getImage();
        cartelImage = new ImageIcon("src/resources/imag/Menu/Cartel.png").getImage();
    }

    public void prepareElements() {
        prepareExitButton();
        preparePVPButton();
        prepareActions();
    }

    private void prepareExitButton() {
        quit = new JButton();
        Image quit1Image = new ImageIcon("src/resources/imag/Menu/SelectorScreen_Quit1.png").getImage();
        Image quit2Image = new ImageIcon("src/resources/imag/Menu/SelectorScreen_Quit2.png").getImage();
        int width = quit1Image.getWidth(null);
        int height = quit1Image.getHeight(null);
        int newWidth = getWidth() * 17 / 384;
        int newHeight = height * newWidth / width;
        ImageIcon quit1 = new ImageIcon(quit1Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        ImageIcon quit2 = new ImageIcon(quit2Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        quit.setBounds(getWidth() * 1777 / 1920, getHeight() * 116 / 135, quit1.getIconWidth(), quit1.getIconHeight());
        quit.setIcon(quit1);
        quit.setRolloverIcon(quit2);
        quit.setFocusPainted(false);
        quit.setBorderPainted(false);
        quit.setContentAreaFilled(false);
        add(quit);
    }

    private void preparePVPButton() {
        pvp = new JButton();
        Image pvp1Image = new ImageIcon("src/resources/imag/Menu/PvPButton.png").getImage();
        Image pvp2Image = new ImageIcon("src/resources/imag/Menu/PvPButtonHover.png").getImage();

    }

    public void startAudio() {
        try {
            AudioInputStream audioStream;
            audioStream = AudioSystem.getAudioInputStream(new File("src/resources/sound/soundtracks/mainMenu.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopAudio() {
        if (clip != null) {
            clip.stop();
        }
    }

    private void prepareActions() {
        quit.addActionListener(_ -> System.exit(0));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

        int originalWidth = cartelImage.getWidth(null);
        int originalHeight = cartelImage.getHeight(null);
        int newWidth = (int) (getWidth() * (double) 247 / 960);
        int newHeight = originalHeight * newWidth / originalWidth;
        int xPosition = (int) (getWidth() * 0.05);
        g2d.drawImage(cartelImage, xPosition, 0, newWidth, newHeight,null);

    }
}
