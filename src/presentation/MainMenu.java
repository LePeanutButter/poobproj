package presentation;
import domain.PvM;

import java.awt.*;
import javax.swing.*;

public class MainMenu extends JPanel {
    private String playerName;
    private Image backgroundImage;
    private Image cartelImage;
    private JButton quit;
    private JButton pvp;
    private JButton pvm;
    private Sound backgroundMusic;

    public MainMenu(Dimension dimension) {
        setPreferredSize(dimension);
        setLayout(null);
        SwingUtilities.invokeLater(() -> {
            prepareElements();
            startAudio();}
        );
        playerName = POOBVsZombiesGUI.poobVsZombies.getPlayerName();
        if (playerName == null || playerName.isEmpty()) {
            //preparePlayerName();
        }
    }

    public void preparePlayerName() {
        NewPlayer name = new NewPlayer(getWidth(), getHeight());
        JButton ok = name.getButton();
        ok.addActionListener(_ -> {
            String text = name.getText();
            if (text != null && !text.isEmpty()) {
                POOBVsZombiesGUI.poobVsZombies.setPlayerName(text);
                remove(name);
                repaint();
            } else {
                new Sound(false, "src/resources/sound/soundeffects/SFX-buzzer.wav");
            }
        });
        add(name);
        repaint();
    }

    public void prepareElements() {
        backgroundImage = new ImageIcon("src/resources/imag/Menu/mainMenu.png").getImage();
        cartelImage = new ImageIcon("src/resources/imag/Menu/Cartel.png").getImage();
        prepareExitButton();
        preparePVPButton();
        preparePVMButton();
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
        Image pvp1Image = new ImageIcon("src/resources/imag/Menu/SelectorScreen_StartAdventure_Button1.png").getImage();
        Image pvp2Image = new ImageIcon("src/resources/imag/Menu/SelectorScreen_StartAdventure_Highlight.png").getImage();
        int width = pvp1Image.getWidth(null);
        int height = pvp1Image.getHeight(null);
        int newWidth = getWidth() * 97 / 320;
        int newHeight = height * newWidth / width;
        ImageIcon pvp1 = new ImageIcon(pvp1Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        ImageIcon pvp2 = new ImageIcon(pvp2Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        pvp.setBounds(getWidth() * 51 / 80, getHeight() * 119 / 1080, pvp1.getIconWidth(), pvp1.getIconHeight());
        pvp.setIcon(pvp1);
        pvp.setRolloverIcon(pvp2);
        pvp.setFocusPainted(false);
        pvp.setBorderPainted(false);
        pvp.setContentAreaFilled(false);
        add(pvp);
    }

    private void preparePVMButton() {
        pvm = new JButton();
        Image pvm1Image = new ImageIcon("src/resources/imag/Menu/SelectorScreen_Survival_button.png").getImage();
        Image pvm2Image = new ImageIcon("src/resources/imag/Menu/SelectorScreen_Survival_highlight.png").getImage();
        int width = pvm1Image.getWidth(null);
        int height = pvm1Image.getHeight(null);
        int newWidth = getWidth() * 277 / 960;
        int newHeight = height * newWidth / width;
        ImageIcon pvm1 = new ImageIcon(pvm1Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        ImageIcon pvm2 = new ImageIcon(pvm2Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        pvm.setBounds(getWidth() * 51 / 80, getHeight() * 163 / 540, pvm1.getIconWidth(), pvm1.getIconHeight());
        pvm.setIcon(pvm1);
        pvm.setRolloverIcon(pvm2);
        pvm.setFocusPainted(false);
        pvm.setBorderPainted(false);
        pvm.setContentAreaFilled(false);
        add(pvm);
    }

    public void startAudio() {
        backgroundMusic = new Sound(true, "src/resources/sound/soundtracks/mainMenu.wav");
    }

    private void prepareActions() {
        pvm.addActionListener(_ -> addPvM());
        quit.addActionListener(_ -> System.exit(0));
    }

    private void addPvM() {
        if (POOBVsZombiesGUI.poobVsZombies.hasActiveMatch("PvM")) {
            backgroundMusic.stopClip();
            POOBVsZombiesGUI.switchToPanel(new PvMGUI((PvM) POOBVsZombiesGUI.poobVsZombies.play("PvM"), POOBVsZombiesGUI.dimensions));
        } else {
            backgroundMusic.stopClip();
            POOBVsZombiesGUI.switchToPanel(new PvMGUI((PvM) POOBVsZombiesGUI.poobVsZombies.play("PvM","day"), "Day", POOBVsZombiesGUI.dimensions));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

        //Cartel
        int originalWidth = cartelImage.getWidth(null);
        int originalHeight = cartelImage.getHeight(null);
        int newWidth = getWidth() *  247 / 960;
        int newHeight = originalHeight * newWidth / originalWidth;
        int xPosition = (int) (getWidth() * 0.05);
        g2d.drawImage(cartelImage, xPosition, 0, newWidth, newHeight,null);
    }
}
