package presentation;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoadingScreen extends JPanel {
    Image backgroundImage;
    Image gameLogo;
    JButton start;
    Image dirt;
    BufferedImage grass;
    Image sodRoll;
    Clip clip;
    int loadingBarProgress = 0;

    public LoadingScreen() {
        setLayout(null);
        backgroundImage = new ImageIcon("src/resources/imag/Menu/LoadingScreen.png").getImage();
        gameLogo = new ImageIcon("src/resources/imag/Menu/PVZ_LOGO.png").getImage();
        sodRoll = new ImageIcon("src/resources/imag/Menu/SodRollCap.png").getImage();
        try {
            grass = ImageIO.read(new File("src/resources/imag/Menu/LoadBar_grass.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void prepareStartButton() {
        start = new JButton("Cargando...");
        start.setFont(POOBVsZombiesGUI.briannesHand);
        start.setForeground(new Color(212, 182, 58));
        start.setHorizontalTextPosition(JButton.CENTER);
        start.setVerticalTextPosition(JButton.CENTER);
        Image dirtImage = new ImageIcon("src/resources/imag/Menu/LoadBar_dirt.png").getImage();
        int width = dirtImage.getWidth(null);
        int height = dirtImage.getHeight(null);
        int newWidth = getWidth() *  29 / 96;
        int newHeight = height * newWidth / width;
        ImageIcon dirtIcon = new ImageIcon(dirtImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        dirt = dirtIcon.getImage();
        start.setBounds(getWidth() * 679 / 1920, getHeight() * 241 / 270, dirtIcon.getIconWidth(), dirtIcon.getIconHeight());
        start.setFocusPainted(false);
        start.setBorderPainted(false);
        start.setContentAreaFilled(false) ;
        start.setOpaque(false);
        start.setEnabled(false);
        add(start);
    }

    public void startAudio() {
        try {
            AudioInputStream audioStream;
            audioStream = AudioSystem.getAudioInputStream(new File("src/resources/sound/soundtracks/loadingScreen.wav"));
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

    public void prepareActions() {
        start.addActionListener(_ -> {
            POOBVsZombiesGUI.switchToPanel("MainMenu");
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("src/resources/sound/soundeffects/SFX-seedlift.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

        int logoWidth = gameLogo.getWidth(null);
        int logoHeight = gameLogo.getHeight(null);
        int newWidth = (int) (getWidth() * 0.6);
        int newHeight = logoHeight * newWidth / logoWidth;
        int xPosition = (getWidth() - newWidth) / 2;
        int yPosition = (int) (getHeight() * 0.04);
        g2d.drawImage(gameLogo, xPosition, yPosition, newWidth, newHeight,null);

        g2d.drawImage(dirt, getWidth() * 679 / 1920, getHeight() * 241 / 270, dirt.getWidth(null), dirt.getHeight(null), null);

        int xPositionGrass = getWidth() * 673 / 1920;
        int yPositionGrass = getHeight() * 233 / 270;
        int grassWidth = grass.getWidth();
        int grassHeight = grass.getHeight();
        int newGrassWidth = getWidth() * 47 / 160;
        int newGrassHeight = grassHeight * newGrassWidth / grassWidth;
        int bufferWidth = newGrassWidth * loadingBarProgress / 100;
        if (loadingBarProgress > 0 && loadingBarProgress < 100) {
            BufferedImage grassSubimage = grass.getSubimage(0, 0, grassWidth * loadingBarProgress / 100, grassHeight);
            g2d.drawImage(grassSubimage, xPositionGrass, yPositionGrass, bufferWidth, newGrassHeight, null);
        } else if (loadingBarProgress >= 100) {
            g2d.drawImage(grass, xPositionGrass, yPositionGrass, newGrassWidth, newGrassHeight, null);
        }


    }

    public void startAnimation() {
        Timer timer = new Timer(10/3, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loadingBarProgress < 100) {
                    loadingBarProgress += 9 / 2;
                    repaint();
                } else {
                    ((Timer) e.getSource()).stop();
                    start.setText("Click para continuar.");
                    start.setEnabled(true);
                }
            }
        });
        timer.start();
    }
}
